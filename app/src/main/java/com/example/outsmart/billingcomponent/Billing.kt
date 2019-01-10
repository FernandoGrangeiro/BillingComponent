package com.example.outsmart.billingcomponent

import android.app.Activity
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase

class Billing : IBillingService, IBillingPurchase {

    var billingType: String? = null
    private val billingService by lazy { BillingService() }
    var consumePurchase = false

    private val billingCallback: BillingCallback? = null


    fun initBillingClient(activity: Activity) {
        billingService.initBillingClient(activity)
    }

    fun startBillingFlow(activity: Activity, productId: String) {
        billingService.startBillingFlow(activity, getBillingFlowParams(productId))
    }

    fun getBillingFlowParams(productId: String) = BillingFlowParams.newBuilder()
        .setSku(productId)
        .setType(billingType)
        .build()

    fun setInApp() {
        billingType = BillingClient.SkuType.INAPP
    }

    fun setSubscription() {
        billingType = BillingClient.SkuType.SUBS
    }


    override fun onSuccessConnect() {
        billingCallback?.onSuccessConnect()
    }

    override fun onFailConnect(error: String) {
        billingCallback?.onFailConnect(error)
    }

    override fun onDisconnect(disconnectMessage: String) {
        billingCallback?.onDisconnect(disconnectMessage)
    }

    override fun onSuccessConsume() {
        billingCallback?.onSuccessConsume()
    }

    override fun onFailConsume(error: String) {
        billingCallback?.onFailConsume(error)
    }

    override fun onPurchaseSuccess(purchase: Purchase) {
        if (consumePurchase) {
            billingService.consumeProduct(purchase.purchaseToken)
        }
        billingCallback?.onPurchaseSuccess(purchase)
    }
}