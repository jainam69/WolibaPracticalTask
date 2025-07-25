package com.png.wolibapracticaltask.data.remote.api;

import com.png.wolibapracticaltask.data.model.request.SendOtpRequest;
import com.png.wolibapracticaltask.data.model.request.VerifyOtpRequest;
import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.model.response.InterestResponse;
import com.png.wolibapracticaltask.data.model.response.SendOtpResponse;
import com.png.wolibapracticaltask.data.model.response.VerifyEmailResponse;
import com.png.wolibapracticaltask.data.model.response.WellbeingItem;
import com.png.wolibapracticaltask.data.remote.ApiConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthApi {
    @POST(ApiConstants.VERIFY_EMAIL)
    Call<ApiResponse<VerifyEmailResponse>> verifyEmail(@Body SendOtpRequest request);

    @POST(ApiConstants.SEND_OTP)
    Call<ApiResponse<SendOtpResponse>> sendOtp(@Body SendOtpRequest request);

    @POST(ApiConstants.VERIFY_OTP)
    Call<ApiResponse<ArrayList<String>>> verifyOtp(@Body VerifyOtpRequest request);

    @GET(ApiConstants.WELLNESS_INTEREST)
    Call<ApiResponse<ArrayList<ArrayList<InterestResponse>>>> wellnessInterest();

    @GET(ApiConstants.WELLBEING_PILLARS)
    Call<ApiResponse<ArrayList<WellbeingItem>>> wellbeingPillars();


}
