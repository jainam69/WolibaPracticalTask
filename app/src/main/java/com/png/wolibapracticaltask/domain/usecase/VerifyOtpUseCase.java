package com.png.wolibapracticaltask.domain.usecase;

import com.png.wolibapracticaltask.data.model.request.VerifyOtpRequest;
import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;

import java.util.ArrayList;

import retrofit2.Call;

public class VerifyOtpUseCase {
    private final RegistrationRepository repository;

    public VerifyOtpUseCase(RegistrationRepository repository) {
        this.repository = repository;
    }

    public Call<ApiResponse<ArrayList<String>>> execute(VerifyOtpRequest request) {
        return repository.verifyOtp(request);
    }
}
