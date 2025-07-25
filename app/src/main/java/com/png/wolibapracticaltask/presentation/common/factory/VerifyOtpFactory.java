package com.png.wolibapracticaltask.presentation.common.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.png.wolibapracticaltask.domain.usecase.SendOtpUseCase;
import com.png.wolibapracticaltask.domain.usecase.VerifyOtpUseCase;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;

public class VerifyOtpFactory implements ViewModelProvider.Factory {
    private final VerifyOtpUseCase verifyOtpUseCase;
    private final SendOtpUseCase sendOtpUseCase;

    public VerifyOtpFactory(SendOtpUseCase sendOtpUseCase, VerifyOtpUseCase verifyOtpUseCase) {
        this.sendOtpUseCase = sendOtpUseCase;
        this.verifyOtpUseCase = verifyOtpUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            return (T) new RegistrationViewModel(sendOtpUseCase, verifyOtpUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}