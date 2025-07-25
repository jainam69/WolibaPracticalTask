package com.png.wolibapracticaltask.domain.usecase;

import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.model.response.WellbeingItem;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;

import java.util.ArrayList;

import retrofit2.Call;

public class WellbeingPillarUseCase {
    private final RegistrationRepository repository;

    public WellbeingPillarUseCase(RegistrationRepository repository) {
        this.repository = repository;
    }

    public Call<ApiResponse<ArrayList<WellbeingItem>>> execute() {
        return repository.wellbeingPillars();
    }
}
