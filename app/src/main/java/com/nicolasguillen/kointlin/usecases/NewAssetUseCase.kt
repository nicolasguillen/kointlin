package com.nicolasguillen.kointlin.usecases

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.errors.ApiException
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Single

interface NewAssetUseCase {

    fun loadAllTopCoins(): Single<LoadTopAssetsResult>

    fun storeNewAsset(newAsset: Asset): Single<StoreAssetResult>
}

class NewAssetUseCaseImpl(private val apiRepository: ApiRepository,
                          private val walletRepository: WalletRepository) : NewAssetUseCase {

    override fun loadAllTopCoins(): Single<LoadTopAssetsResult> {
        return Single.create { observer ->
            this.apiRepository.getTopCoins()
                    .subscribe(
                            { coins -> observer.onSuccess(LoadTopAssetsResult.Success(coins)) },
                            { error -> when(error){
                                is ApiException ->
                                    observer.onSuccess(LoadTopAssetsResult.ApiError(error.errorEnvelope.status))
                                else ->
                                    observer.onSuccess(LoadTopAssetsResult.UnknownError)
                            } }
                    )
        }
    }

    override fun storeNewAsset(newAsset: Asset): Single<StoreAssetResult> {
        return Single.create { observer ->
            this.walletRepository.insertAsset(newAsset)
            observer.onSuccess(StoreAssetResult.Success)
        }
    }

}

sealed class LoadTopAssetsResult {
    class Success(val assetList: List<TopCoin>): LoadTopAssetsResult()
    class ApiError(val errorCode: Int?): LoadTopAssetsResult()
    object UnknownError: LoadTopAssetsResult()
}

sealed class StoreAssetResult {
    object Success: StoreAssetResult()
}
