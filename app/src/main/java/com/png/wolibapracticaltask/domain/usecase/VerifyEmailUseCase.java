package com.png.wolibapracticaltask.domain.usecase;

import com.png.wolibapracticaltask.data.model.request.SendOtpRequest;
import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.model.response.VerifyEmailResponse;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;

import retrofit2.Call;

public class VerifyEmailUseCase {
    private final RegistrationRepository repository;

    public VerifyEmailUseCase(RegistrationRepository repository) {
        this.repository = repository;
    }

    public Call<ApiResponse<VerifyEmailResponse>> execute(SendOtpRequest request) {
        return repository.verifyEmail(request);
    }
}
