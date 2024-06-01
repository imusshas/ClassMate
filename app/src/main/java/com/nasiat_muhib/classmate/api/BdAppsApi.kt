package com.nasiat_muhib.classmate.api

import com.nasiat_muhib.classmate.data.model.api_response.RequestOTPResponse
import com.nasiat_muhib.classmate.data.model.api_response.VerifyOTPResponse
import com.nasiat_muhib.classmate.strings.OTP
import com.nasiat_muhib.classmate.strings.REFERENCE_NO
import com.nasiat_muhib.classmate.strings.SUBSCRIBER_ID
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface BdAppsApi {

    @POST(REQUEST_OTP_END_POINT)
    suspend fun requestOTP(@Query(value = SUBSCRIBER_ID) subscriberId: String): Response<RequestOTPResponse>

    @POST(VERIFY_OTP_END_POINT)
    suspend fun verifyOTP(
        @Query(value = REFERENCE_NO) referenceNo: String,
        @Query(value = OTP) otp: String
    ): Response<VerifyOTPResponse>


    companion object {
        private const val REQUEST_OTP_END_POINT = "request_otp"
        private const val VERIFY_OTP_END_POINT = "verify_otp"
    }
}