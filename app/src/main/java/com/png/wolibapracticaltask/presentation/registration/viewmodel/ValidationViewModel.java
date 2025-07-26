package com.png.wolibapracticaltask.presentation.registration.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.png.wolibapracticaltask.core.common.Common;
import com.png.wolibapracticaltask.domain.model.RegistrationData;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ValidationViewModel extends ViewModel {
    private final MutableLiveData<RegistrationStep> currentStep = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isOtpValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isCurrentStepValid = new MutableLiveData<>(false);
    private final RegistrationData registrationData = new RegistrationData();

    public void setCurrentStep(RegistrationStep step) {
        currentStep.setValue(step);
    }

    public void setOTPValid(boolean step) {
        isOtpValid.setValue(step);
    }

    public LiveData<RegistrationStep> getCurrentStep() {
        return currentStep;
    }

    public LiveData<Boolean> isCurrentStepValid() {
        return isCurrentStepValid;
    }

    public LiveData<Boolean> isOTPValid() {
        return isOtpValid;
    }

    public RegistrationData getRegistrationData() {
        return registrationData;
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

                isValid = Common.isValidEmail(email) &&
                        !(firstName != null && firstName.isEmpty()) &&
                        !(lastName != null && lastName.isEmpty()) &&
                        Objects.equals(isEmailValid, "true");
                if (isValid) {
                    registrationData.firstName = firstName;
                    registrationData.lastName = lastName;
                    registrationData.email = email;
                }
                break;

            case OTP:
                String otp = inputs.get("otp");
                isValid = otp != null && otp.length() == 6;
                if (isValid) {
                    registrationData.otp = otp;
                }
                break;

            case PROFILE:
                String password = inputs.get("password");
                String confirmPassword = inputs.get("confirmPassword");
                String birthDay = inputs.get("birthDay");
                String contactNo = inputs.get("contactNo");
                String isConditionValid = inputs.get("isConditionValid");
                isValid = !(password != null && password.isEmpty()) &&
                        (password != null && password.length() == 6) &&
                        !(confirmPassword != null && confirmPassword.isEmpty()) &&
                        Objects.equals(password, confirmPassword) &&
                        !(birthDay != null && birthDay.isEmpty()) &&
                        !(contactNo != null && contactNo.isEmpty()) &&
                        Objects.requireNonNull(contactNo).length() == 10 &&
                        Objects.equals(isConditionValid, "true");
                if (isValid) {
                    registrationData.password = password;
                    registrationData.confirmPassword = confirmPassword;
                    registrationData.birthDay = birthDay;
                    registrationData.contactNo = contactNo;
                    registrationData.isConditionValid = true;
                }
                break;

            case INTEREST:
                String count = inputs.get("count");
                isValid = !Objects.equals(count, "");
                if (isValid) {
                    String selected = inputs.get("interest");
                    registrationData.selectedInterests = Arrays.stream(selected.split(","))
                            .map(String::trim)
                            .mapToInt(Integer::parseInt)
                            .toArray();
                }
                break;

            case WELLBEING:
                String wellbeingCount = inputs.get("count");
                isValid = Objects.equals(wellbeingCount, "3");
                if (isValid) {
                    String selected = inputs.get("wellbeingItem");
                    registrationData.selectedWellbeingOptions = Arrays.stream(selected.split(","))
                            .map(String::trim)
                            .mapToInt(Integer::parseInt)
                            .toArray();
                }
                break;

            default:
                isValid = true;
        }

        isCurrentStepValid.setValue(isValid);
    }
}
