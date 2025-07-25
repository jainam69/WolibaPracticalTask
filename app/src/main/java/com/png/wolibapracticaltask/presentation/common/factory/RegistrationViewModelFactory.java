package com.png.wolibapracticaltask.presentation.common.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.png.wolibapracticaltask.domain.usecase.SendOtpUseCase;
import com.png.wolibapracticaltask.domain.usecase.VerifyEmailUseCase;
import com.png.wolibapracticaltask.domain.usecase.VerifyOtpUseCase;
import com.png.wolibapracticaltask.domain.usecase.WellbeingInterestUseCase;
import com.png.wolibapracticaltask.domain.usecase.WellbeingPillarUseCase;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;

public class RegistrationViewModelFactory implements ViewModelProvider.Factory {
    private final SendOtpUseCase sendOtpUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final VerifyOtpUseCase verifyOtpUseCase;
    private final WellbeingInterestUseCase wellbeingInterestUseCase;
    private final WellbeingPillarUseCase wellbeingPillarUseCase;

    public RegistrationViewModelFactory(SendOtpUseCase sendOtpUseCase, VerifyEmailUseCase verifyEmailUseCase,
                                        VerifyOtpUseCase verifyOtpUseCase, WellbeingInterestUseCase wellbeingInterestUseCase,
                                        WellbeingPillarUseCase wellbeingPillarUseCase) {
        this.sendOtpUseCase = sendOtpUseCase;
        this.verifyEmailUseCase = verifyEmailUseCase;
        this.verifyOtpUseCase = verifyOtpUseCase;
        this.wellbeingInterestUseCase = wellbeingInterestUseCase;
        this.wellbeingPillarUseCase = wellbeingPillarUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            return (T) new RegistrationViewModel(sendOtpUseCase, verifyEmailUseCase, verifyOtpUseCase, wellbeingInterestUseCase, wellbeingPillarUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}