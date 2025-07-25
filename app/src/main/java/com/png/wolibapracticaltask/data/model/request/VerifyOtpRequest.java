package com.png.wolibapracticaltask.data.model.request;

public class VerifyOtpRequest {
    private String otp;
    private String token;

    public VerifyOtpRequest(String otp, String token) {
        this.otp = otp;
        this.token = token;
    }
}
