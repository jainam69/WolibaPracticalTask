package com.png.wolibapracticaltask.presentation.common.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.png.wolibapracticaltask.domain.usecase.WellbeingInterestUseCase;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;

public class WellbeingInterestFactory implements ViewModelProvider.Factory {
    private final WellbeingInterestUseCase wellbeingInterestUseCase;

    public WellbeingInterestFactory(WellbeingInterestUseCase wellbeingInterestUseCase) {
        this.wellbeingInterestUseCase = wellbeingInterestUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            return (T) new RegistrationViewModel(wellbeingInterestUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}