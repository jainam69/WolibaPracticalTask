package com.png.wolibapracticaltask.domain.usecase;

import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.model.response.InterestResponse;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;

import java.util.ArrayList;

import retrofit2.Call;

public class WellbeingInterestUseCase {
    private final RegistrationRepository repository;

    public WellbeingInterestUseCase(RegistrationRepository repository) {
        this.repository = repository;
    }

    public Call<ApiResponse<ArrayList<ArrayList<InterestResponse>>>> execute() {
        return repository.wellnessInterest();
    }
}
