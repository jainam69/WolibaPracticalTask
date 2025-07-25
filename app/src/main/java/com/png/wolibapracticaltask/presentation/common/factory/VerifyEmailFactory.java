package com.png.wolibapracticaltask.presentation.common.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.png.wolibapracticaltask.domain.usecase.VerifyEmailUseCase;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;

public class VerifyEmailFactory implements ViewModelProvider.Factory {
    private final VerifyEmailUseCase verifyOtpUseCase;

    public VerifyEmailFactory(VerifyEmailUseCase verifyOtpUseCase) {
        this.verifyOtpUseCase = verifyOtpUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            return (T) new RegistrationViewModel(verifyOtpUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}