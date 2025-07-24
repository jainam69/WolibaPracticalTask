package com.png.wolibapracticaltask.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.png.wolibapracticaltask.presentation.state.RegistrationStep;

import java.util.Map;
import java.util.Objects;

public class RegistrationViewModel extends ViewModel {
    private final MutableLiveData<RegistrationStep> currentStep = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isCurrentStepValid = new MutableLiveData<>(false);

    // Call this to update which step you're on
    public void setCurrentStep(RegistrationStep step) {
        currentStep.setValue(step);
    }

    public LiveData<RegistrationStep> getCurrentStep() {
        return currentStep;
    }

    public LiveData<Boolean> isCurrentStepValid() {
        return isCurrentStepValid;
    }

    // Fragments call this when their fields change
    public void validate(RegistrationStep step, Map<String, String> inputs) {
        boolean isValid = false;

        switch (step) {
            case REGISTER:
                String firstName = inputs.get("firstName");
                String lastName = inputs.get("lastName");
                String email = inputs.get("email");
                String isEmailValid = inputs.get("isEmailValid");
                isValid = email != null && email.contains("@") &&
                        !(firstName != null && firstName.isEmpty()) &&
                        !(lastName != null && lastName.isEmpty()) &&
                        Objects.equals(isEmailValid, "true");
                break;

            case OTP:
                String otp = inputs.get("otp");
                isValid = otp != null && otp.length() == 6;
                break;

            case PROFILE:
                String password = inputs.get("password");
                String confirmPassword = inputs.get("confirmPassword");
                String birthDay = inputs.get("birthDay");
                String contactNo = inputs.get("contactNo");
                String isConditionValid = inputs.get("isConditionValid");
                isValid = !(password != null && password.isEmpty()) &&
                        !(confirmPassword != null && confirmPassword.isEmpty()) &&
                        Objects.equals(password, confirmPassword) &&
                        !(birthDay != null && birthDay.isEmpty()) &&
                        !(contactNo != null && contactNo.isEmpty()) &&
                        Objects.equals(isConditionValid, "true");
                break;

            // Add cases for INTEREST, WELLBEING, etc.

            default:
                isValid = true;
        }

        isCurrentStepValid.setValue(isValid);
    }
}
