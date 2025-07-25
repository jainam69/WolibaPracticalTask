package com.png.wolibapracticaltask.domain.usecase;

import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.model.request.SendOtpRequest;
import com.png.wolibapracticaltask.data.model.response.SendOtpResponse;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;

import retrofit2.Call;

public class SendOtpUseCase {
    private final RegistrationRepository repository;

    public SendOtpUseCase(RegistrationRepository repository) {
        this.repository = repository;
    }

    public Call<ApiResponse<SendOtpResponse>> execute(SendOtpRequest request) {
        return repository.sendOtp(request);
    }
}
