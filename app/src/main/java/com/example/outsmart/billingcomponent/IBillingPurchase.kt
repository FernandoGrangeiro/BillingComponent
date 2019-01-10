package com.example.outsmart.billingcomponent

import com.android.billingclient.api.Purchase

interface IBillingPurchase {
    fun onSuccessConsume()
    fun onFailConsume(error: String)
    fun onPurchaseSuccess(purchase: Purchase)

}