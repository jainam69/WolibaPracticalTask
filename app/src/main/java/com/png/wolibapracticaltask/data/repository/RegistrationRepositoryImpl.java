package com.png.wolibapracticaltask.data.repository;

import com.png.wolibapracticaltask.data.model.request.SendOtpRequest;
import com.png.wolibapracticaltask.data.model.request.VerifyOtpRequest;
import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.model.response.InterestResponse;
import com.png.wolibapracticaltask.data.model.response.SendOtpResponse;
import com.png.wolibapracticaltask.data.model.response.VerifyEmailResponse;
import com.png.wolibapracticaltask.data.model.response.WellbeingItem;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSource;

import java.util.ArrayList;

import retrofit2.Call;

public class RegistrationRepositoryImpl implements RegistrationRepository {
    private final RegistrationRemoteDataSource remoteDataSource;

    public RegistrationRepositoryImpl(RegistrationRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Call<ApiResponse<VerifyEmailResponse>> verifyEmail(SendOtpRequest request) {
        return remoteDataSource.verifyEmail(request);
    }

    @Override
    public Call<ApiResponse<SendOtpResponse>> sendOtp(SendOtpRequest request) {
        return remoteDataSource.sendOtp(request);
    }

    @Override
    public Call<ApiResponse<ArrayList<String>>> verifyOtp(VerifyOtpRequest request) {
        return remoteDataSource.verifyOtp(request);
    }

    @Override
    public Call<ApiResponse<ArrayList<ArrayList<InterestResponse>>>> wellnessInterest() {
        return remoteDataSource.wellnessInterest();
    }

    @Override
    public Call<ApiResponse<ArrayList<WellbeingItem>>> wellbeingPillars() {
        return remoteDataSource.wellbeingPillars();
    }
}
