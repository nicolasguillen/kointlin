package com.nicolasguillen.kointlin.usecases

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.errors.ApiException
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Single

interface AccountUseCase {

    fun fetchAllMyAssets(): Single<FetchMyAssetsResult>

    fun getPriceFromAllMyAssets(): Single<GetPriceResult>
}

class AccountUseCaseImpl(private val apiRepository: ApiRepository,
                         private val walletRepository: WalletRepository) : AccountUseCase {

    override fun fetchAllMyAssets(): Single<FetchMyAssetsResult> {
        return Single.create { observer ->
            this.walletRepository.getAllAssets()
                    .subscribe { list -> observer.onSuccess(FetchMyAssetsResult.Success(list)) }
        }
    }

    override fun getPriceFromAllMyAssets(): Single<GetPriceResult> {
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

    private fun calculateValueFromWallet(assetList: List<Asset>): Single<Double> {
        return if(assetList.isEmpty()){
            Single.just(0.0)
        } else {
            Single.concat(assetList.map { asd(it) })
                    .scan { t1, t2 -> t1 + t2 }
                    .lastElement()
                    .toSingle()
        }
    }

    private fun asd(asset: Asset): Single<Double>{
        return this.apiRepository.getCoinFromId(asset.id)
                .map { it.first() }
                .map { it.priceUsd.toDouble() * asset.amount }
    }

}

sealed class FetchMyAssetsResult {
    class Success(val assetList: List<Asset>): FetchMyAssetsResult()
}

sealed class GetPriceResult {
    class Success(val price: Double): GetPriceResult()
    class ApiError(val errorCode: Int?): GetPriceResult()
    object UnknownError: GetPriceResult()
}
