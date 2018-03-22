package com.nicolasguillen.kointlin.usecases

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.errors.ApiException
import com.nicolasguillen.kointlin.storage.AppSettingsRepository
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.storage.entities.AppSettings
import com.nicolasguillen.kointlin.ui.views.DisplayableAsset
import io.reactivex.Single

interface AccountUseCase {
    fun getDisplayableAssets(): Single<GetPriceResult>
}

class AccountUseCaseImpl(private val apiRepository: ApiRepository,
                         private val walletRepository: WalletRepository,
                         private val appSettingsRepository: AppSettingsRepository) : AccountUseCase {

    override fun getDisplayableAssets(): Single<GetPriceResult> {
        return Single.create { observer ->
            this.fetchLocalData()
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

    private fun fetchLocalData(): Single<WalletData>{
        return this.walletRepository.getAllAssets()
                .flatMap { list ->
                    this.appSettingsRepository.getAppSettings()
                            .map { WalletData(it, list) }
                }
    }

    private fun calculateValueFromWallet(walletData: WalletData): Single<List<DisplayableAsset>> {
        return if(walletData.listAllAssets.isEmpty()){
            Single.just(emptyList())
        } else {
            Single.concat(walletData.listAllAssets
                    .filter { it.amount > 0 }
                    .map { fetchAndMapToDisplayable(it, walletData.appSettings.currencyCode) })
                    .toList()
        }
    }

    private fun fetchAndMapToDisplayable(asset: Asset, currency: String): Single<DisplayableAsset>{
        return this.apiRepository.getCoinFromId(asset.id, currency)
                .map { it.first() }
                .map { coin ->
                    val current = coin.price.toDouble() * asset.amount
                    val variant = coin.percentChange24h.toDouble() * current / 100
                    DisplayableAsset(asset, current, variant, currency)
                }
    }

    internal class WalletData(val appSettings: AppSettings, val listAllAssets: List<Asset>)

}

sealed class GetPriceResult {
    class Success(val list: List<DisplayableAsset>): GetPriceResult()
    class ApiError(val errorCode: Int?): GetPriceResult()
    object UnknownError: GetPriceResult()
}
