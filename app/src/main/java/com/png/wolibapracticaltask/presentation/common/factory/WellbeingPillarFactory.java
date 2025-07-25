package com.png.wolibapracticaltask.presentation.common.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.png.wolibapracticaltask.domain.usecase.WellbeingPillarUseCase;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;

public class WellbeingPillarFactory implements ViewModelProvider.Factory {
    private final WellbeingPillarUseCase wellbeingPillarUseCase;

    public WellbeingPillarFactory(WellbeingPillarUseCase wellbeingPillarUseCase) {
        this.wellbeingPillarUseCase = wellbeingPillarUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            return (T) new RegistrationViewModel(wellbeingPillarUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}