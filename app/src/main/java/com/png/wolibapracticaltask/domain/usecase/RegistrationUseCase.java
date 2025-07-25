package com.png.wolibapracticaltask.domain.usecase;

import com.png.wolibapracticaltask.data.model.request.RegistrationRequest;
import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.model.response.RegistrationResponse;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;

import retrofit2.Call;

public class RegistrationUseCase {
    private final RegistrationRepository repository;

    public RegistrationUseCase(RegistrationRepository repository) {
        this.repository = repository;
    }

    public Call<ApiResponse<RegistrationResponse>> execute(RegistrationRequest request) {
        return repository.userRegistration(request);
    }
}
