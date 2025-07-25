package com.png.wolibapracticaltask.data.repository;

import com.png.wolibapracticaltask.data.model.request.RegistrationRequest;
import com.png.wolibapracticaltask.data.model.request.SendOtpRequest;
import com.png.wolibapracticaltask.data.model.request.VerifyOtpRequest;
import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.model.response.InterestResponse;
import com.png.wolibapracticaltask.data.model.response.RegistrationResponse;
import com.png.wolibapracticaltask.data.model.response.SendOtpResponse;
import com.png.wolibapracticaltask.data.model.response.VerifyEmailResponse;
import com.png.wolibapracticaltask.data.model.response.WellbeingItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;

public interface RegistrationRepository {
    Call<ApiResponse<VerifyEmailResponse>> verifyEmail(@Body SendOtpRequest request);
    Call<ApiResponse<SendOtpResponse>> sendOtp(@Body SendOtpRequest request);
    Call<ApiResponse<ArrayList<String>>> verifyOtp(@Body VerifyOtpRequest request);
    Call<ApiResponse<ArrayList<ArrayList<InterestResponse>>>> wellnessInterest();
    Call<ApiResponse<ArrayList<WellbeingItem>>> wellbeingPillars();
    Call<ApiResponse<RegistrationResponse>> userRegistration(@Body RegistrationRequest request);
}
