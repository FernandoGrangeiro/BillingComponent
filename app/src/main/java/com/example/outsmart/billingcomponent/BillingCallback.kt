package com.example.outsmart.billingcomponent

import com.android.billingclient.api.Purchase

interface BillingCallback {
    fun onSuccessConnect()
    fun onFailConnect(error: String)
    fun onDisconnect(disconnectMessage: String)
    fun onSuccessConsume()
    fun onFailConsume(error: String)
    fun onPurchaseSuccess(purchase: Purchase)

}