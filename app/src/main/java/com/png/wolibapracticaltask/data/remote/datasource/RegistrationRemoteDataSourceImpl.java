package com.png.wolibapracticaltask.data.remote.datasource;

import com.png.wolibapracticaltask.data.model.request.SendOtpRequest;
import com.png.wolibapracticaltask.data.model.request.VerifyOtpRequest;
import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.model.response.InterestResponse;
import com.png.wolibapracticaltask.data.model.response.SendOtpResponse;
import com.png.wolibapracticaltask.data.model.response.VerifyEmailResponse;
import com.png.wolibapracticaltask.data.model.response.WellbeingItem;
import com.png.wolibapracticaltask.data.remote.api.AuthApi;

import java.util.ArrayList;

import retrofit2.Call;

public class RegistrationRemoteDataSourceImpl implements RegistrationRemoteDataSource {
    private final AuthApi authApi;

    public RegistrationRemoteDataSourceImpl(AuthApi authApi) {
        this.authApi = authApi;
    }

    @Override
    public Call<ApiResponse<VerifyEmailResponse>> verifyEmail(SendOtpRequest request) {
        return authApi.verifyEmail(request);
    }

    @Override
    public Call<ApiResponse<SendOtpResponse>> sendOtp(SendOtpRequest request) {
        return authApi.sendOtp(request);
    }

    @Override
    public Call<ApiResponse<ArrayList<String>>> verifyOtp(VerifyOtpRequest request) {
        return authApi.verifyOtp(request);
    }

    @Override
    public Call<ApiResponse<ArrayList<ArrayList<InterestResponse>>>> wellnessInterest() {
        return authApi.wellnessInterest();
    }

    @Override
    public Call<ApiResponse<ArrayList<WellbeingItem>>> wellbeingPillars() {
        return authApi.wellbeingPillars();
    }
}
