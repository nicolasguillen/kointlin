package com.nicolasguillen.kointlin.usecases

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.errors.ApiException
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.ui.views.DisplayableAsset
import io.reactivex.Single

interface AccountUseCase {

    fun fetchAllMyAssets(): Single<FetchMyAssetsResult>

    fun getDisplayableAssets(): Single<GetPriceResult>
}

class AccountUseCaseImpl(private val apiRepository: ApiRepository,
                         private val walletRepository: WalletRepository) : AccountUseCase {

    override fun fetchAllMyAssets(): Single<FetchMyAssetsResult> {
        return Single.create { observer ->
            this.walletRepository.getAllAssets()
                    .subscribe { list -> observer.onSuccess(FetchMyAssetsResult.Success(list)) }
        }
    }

    override fun getDisplayableAssets(): Single<GetPriceResult> {
        return Single.create { observer ->
            this.walletRepository.getAllAssets()
                    .flatMap { this.calculateValueFromWallet(it) }
                    .subscribe(
                            { value -> observer.onSuccess(GetPriceResult.Success(value)) },
                            { error -> when(error){
                                is ApiException ->
                                    observer.onSuccess(GetPriceResult.ApiError(error.errorEnvelope.status))
                                else ->
                                    observer.onSuccess(GetPriceResult.UnknownError)
                            } }
                    )
        }
    }

    private fun calculateValueFromWallet(assetList: List<Asset>): Single<List<DisplayableAsset>> {
        return if(assetList.isEmpty()){
            Single.just(emptyList())
        } else {
            Single.concat(assetList
                    .filter { it.amount > 0 }
                    .map { fetchAndMapToDisplayable(it) })
                    .toList()
        }
    }

    private fun fetchAndMapToDisplayable(asset: Asset): Single<DisplayableAsset>{
        return this.apiRepository.getCoinFromId(asset.id)
                .map { it.first() }
                .map { coin ->
                    val current = coin.priceUsd.toDouble() * asset.amount
                    val variant = coin.percentChange24h.toDouble() * current / 100
                    DisplayableAsset(asset, current, variant, "USD")
                }
    }

}

sealed class FetchMyAssetsResult {
    class Success(val assetList: List<Asset>): FetchMyAssetsResult()
}

sealed class GetPriceResult {
    class Success(val list: List<DisplayableAsset>): GetPriceResult()
    class ApiError(val errorCode: Int?): GetPriceResult()
    object UnknownError: GetPriceResult()
}
