package com.example.outsmart.billingcomponent


internal interface IBillingService {
    fun onSuccessConnect()
    fun onFailConnect(error: String)
    fun onDisconnect(disconnectMessage: String)

}