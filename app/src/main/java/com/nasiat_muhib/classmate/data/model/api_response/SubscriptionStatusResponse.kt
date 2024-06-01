package com.nasiat_muhib.classmate.data.model.api_response

data class SubscriptionStatusResponse(
    val statusCode: String,
    val statusDetail: String,
    val subscriptionStatus: String,
    val version: String
)