package com.png.wolibapracticaltask.domain.model;

import java.util.List;

public class RegistrationData {
    // REGISTER
    public String firstName;
    public String lastName;
    public String email;
    // OTP
    public String otp;
    // PROFILE
    public String password;
    public String confirmPassword;
    public String birthDay;
    public String contactNo;
    public boolean isConditionValid;
    // WELLBEING
    public List<String> selectedWellbeingOptions;
    // INTEREST
    public List<String> selectedInterests;
}
