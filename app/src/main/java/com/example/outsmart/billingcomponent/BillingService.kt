package com.example.outsmart.billingcomponent

import android.app.Activity
import com.android.billingclient.api.*

internal class BillingService : PurchasesUpdatedListener {

    private var billingClient: BillingClient? = null
    var iBillingService: IBillingService? = null
    var iBillingPurchase: IBillingPurchase? = null


    fun initBillingClient(activity: Activity) {
        billingClient = BillingClient.newBuilder(activity)
            .setListener(this)
            .build()
            .apply {
                startConnection(getBillingClientStateListener())
            }
    }

    private fun getBillingClientStateListener(): BillingClientStateListener {
        return object : BillingClientStateListener {
            override fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    iBillingService?.onSuccessConnect()
                } else {
                    iBillingService?.onFailConnect("onBillingSetupFinished() error code: $billingResponseCode")
                }
            }

            override fun onBillingServiceDisconnected() {
                iBillingService?.onDisconnect("")
            }
        }
    }

    fun startBillingFlow(activity: Activity, billingFlowParams: BillingFlowParams) {
        if (billingClient?.isReady == true) {
            billingClient?.launchBillingFlow(activity, billingFlowParams)
        }
    }

    fun consumeProduct(purchaseToken: String) {
        billingClient?.consumeAsync(purchaseToken) { responseCode, _ ->
            if (responseCode == BillingClient.BillingResponse.OK) {
                iBillingPurchase?.onSuccessConsume()
            } else {
                iBillingPurchase?.onFailConsume("")
            }

        }
    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        purchases?.let {
            for (purchase in it) {
                iBillingPurchase?.onPurchaseSuccess(purchase)
            }
        }
    }

    fun disconnectBillingService() {
        billingClient?.endConnection()
    }

}