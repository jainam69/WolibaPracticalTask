package com.png.wolibapracticaltask.presentation.registration.viewmodel;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.png.wolibapracticaltask.data.model.request.RegistrationRequest;
import com.png.wolibapracticaltask.data.model.request.VerifyOtpRequest;
import com.png.wolibapracticaltask.data.model.response.ApiResponse;
import com.png.wolibapracticaltask.data.model.request.SendOtpRequest;
import com.png.wolibapracticaltask.data.model.response.InterestResponse;
import com.png.wolibapracticaltask.data.model.response.RegistrationResponse;
import com.png.wolibapracticaltask.data.model.response.SendOtpResponse;
import com.png.wolibapracticaltask.data.model.response.VerifyEmailResponse;
import com.png.wolibapracticaltask.data.model.response.WellbeingItem;
import com.png.wolibapracticaltask.data.remote.ErrorData;
import com.png.wolibapracticaltask.domain.usecase.RegistrationUseCase;
import com.png.wolibapracticaltask.domain.usecase.SendOtpUseCase;
import com.png.wolibapracticaltask.domain.usecase.VerifyEmailUseCase;
import com.png.wolibapracticaltask.domain.usecase.VerifyOtpUseCase;
import com.png.wolibapracticaltask.domain.usecase.WellbeingInterestUseCase;
import com.png.wolibapracticaltask.domain.usecase.WellbeingPillarUseCase;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationViewModel extends ViewModel {
    private SendOtpUseCase sendOtpUseCase;
    private VerifyEmailUseCase verifyEmailUseCase;
    private VerifyOtpUseCase verifyOtpUseCase;
    private WellbeingInterestUseCase wellbeingInterestUseCase;
    private WellbeingPillarUseCase wellbeingPillarUseCase;
    private RegistrationUseCase registrationUseCase;
    private final MutableLiveData<ApiResponse<SendOtpResponse>> sendOtpResponse = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse<VerifyEmailResponse>> verifyEmailResponse = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse<ArrayList<String>>> verifyOtpResponse = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse<ArrayList<ArrayList<InterestResponse>>>> interestResponse = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse<ArrayList<WellbeingItem>>> wellbeingPillars = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse<RegistrationResponse>> registrationResponse = new MutableLiveData<>();

    public LiveData<ApiResponse<SendOtpResponse>> sendOtpResponse() {
        return sendOtpResponse;
    }

    public LiveData<ApiResponse<VerifyEmailResponse>> getVerifyEmailResponse() {
        return verifyEmailResponse;
    }

    public LiveData<ApiResponse<ArrayList<String>>> getVerifyOtpResponse() {
        return verifyOtpResponse;
    }

    public LiveData<ApiResponse<ArrayList<ArrayList<InterestResponse>>>> getInterestResponse() {
        return interestResponse;
    }

    public LiveData<ApiResponse<ArrayList<WellbeingItem>>> getWellbeingPillarsResponse() {
        return wellbeingPillars;
    }

    public LiveData<ApiResponse<RegistrationResponse>> getRegistrationUserResponse() {
        return registrationResponse;
    }

    public RegistrationViewModel(SendOtpUseCase sendOtpUseCase, VerifyEmailUseCase verifyEmailUseCase,
                                 VerifyOtpUseCase verifyOtpUseCase, WellbeingInterestUseCase wellbeingInterestUseCase,
                                 WellbeingPillarUseCase wellbeingPillarUseCase, RegistrationUseCase registrationUseCase) {
        this.sendOtpUseCase = sendOtpUseCase;
        this.verifyEmailUseCase = verifyEmailUseCase;
        this.verifyOtpUseCase = verifyOtpUseCase;
        this.wellbeingInterestUseCase = wellbeingInterestUseCase;
        this.wellbeingPillarUseCase = wellbeingPillarUseCase;
        this.registrationUseCase = registrationUseCase;
    }

    public RegistrationViewModel(VerifyEmailUseCase verifyEmailUseCase) {
        this.verifyEmailUseCase = verifyEmailUseCase;
    }

    public RegistrationViewModel(SendOtpUseCase sendOtpUseCase, VerifyOtpUseCase verifyOtpUseCase) {
        this.verifyOtpUseCase = verifyOtpUseCase;
        this.sendOtpUseCase = sendOtpUseCase;
    }

    public RegistrationViewModel(WellbeingInterestUseCase wellbeingInterestUseCase) {
        this.wellbeingInterestUseCase = wellbeingInterestUseCase;
    }

    public RegistrationViewModel(WellbeingPillarUseCase wellbeingPillarUseCase) {
        this.wellbeingPillarUseCase = wellbeingPillarUseCase;
    }

    public void verifyEmail(SendOtpRequest request) {
        verifyEmailUseCase.execute(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<VerifyEmailResponse>> call, @NonNull Response<ApiResponse<VerifyEmailResponse>> response) {
                if (response.isSuccessful()) {
                    verifyEmailResponse.setValue(response.body());
                } else {
                    Gson gson = new Gson();
                    ApiResponse<ErrorData> errorResponse = gson.fromJson(
                            Objects.requireNonNull(response.errorBody()).charStream(),
                            TypeToken.getParameterized(ApiResponse.class, ErrorData.class).getType()
                    );
                    ApiResponse<VerifyEmailResponse> error = new ApiResponse<>();
                    error.status = "failed";
                    error.error = errorResponse.data.getMessage();
                    verifyEmailResponse.setValue(error);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<VerifyEmailResponse>> call, @NonNull Throwable t) {
                ApiResponse<VerifyEmailResponse> error = new ApiResponse<>();
                error.status = "failed";
                error.error = t.getMessage();
                verifyEmailResponse.setValue(error);
            }
        });
    }

    public void sendOtp(SendOtpRequest request) {
        sendOtpUseCase.execute(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<SendOtpResponse>> call, @NonNull Response<ApiResponse<SendOtpResponse>> response) {
                if (response.isSuccessful()) {
                    sendOtpResponse.setValue(response.body());
                } else {
                    Gson gson = new Gson();
                    ApiResponse<ErrorData> errorResponse = gson.fromJson(
                            Objects.requireNonNull(response.errorBody()).charStream(),
                            TypeToken.getParameterized(ApiResponse.class, ErrorData.class).getType()
                    );
                    ApiResponse<SendOtpResponse> error = new ApiResponse<>();
                    error.status = "failed";
                    error.error = errorResponse.data.getMessage();
                    sendOtpResponse.setValue(error);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<SendOtpResponse>> call, @NonNull Throwable t) {
                ApiResponse<SendOtpResponse> error = new ApiResponse<>();
                error.status = "failed";
                error.error = t.getMessage();
                sendOtpResponse.setValue(error);
            }
        });
    }

    public void verifyOTp(VerifyOtpRequest request) {
        verifyOtpUseCase.execute(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<ArrayList<String>>> call, @NonNull Response<ApiResponse<ArrayList<String>>> response) {
                if (response.isSuccessful()) {
                    verifyOtpResponse.setValue(response.body());
                } else {
                    Gson gson = new Gson();
                    ApiResponse<ErrorData> errorResponse = gson.fromJson(
                            Objects.requireNonNull(response.errorBody()).charStream(),
                            TypeToken.getParameterized(ApiResponse.class, ErrorData.class).getType()
                    );
                    ApiResponse<ArrayList<String>> error = new ApiResponse<>();
                    error.status = "failed";
                    error.error = errorResponse.data.getMessage();
                    verifyOtpResponse.setValue(error);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<ArrayList<String>>> call, @NonNull Throwable t) {
                ApiResponse<ArrayList<String>> error = new ApiResponse<>();
                error.status = "failed";
                error.error = t.getMessage();
                verifyOtpResponse.setValue(error);
            }
        });
    }

    public void getWellbeingInterest() {
        wellbeingInterestUseCase.execute().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<ArrayList<ArrayList<InterestResponse>>>> call, @NonNull Response<ApiResponse<ArrayList<ArrayList<InterestResponse>>>> response) {
                interestResponse.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<ArrayList<ArrayList<InterestResponse>>>> call, @NonNull Throwable t) {
                ApiResponse<ArrayList<ArrayList<InterestResponse>>> error = new ApiResponse<>();
                error.status = "failed";
                error.error = t.getMessage();
                interestResponse.setValue(error);
            }
        });
    }

    public void getWellbeingPillars() {
        wellbeingPillarUseCase.execute().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<ArrayList<WellbeingItem>>> call, @NonNull Response<ApiResponse<ArrayList<WellbeingItem>>> response) {
                wellbeingPillars.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<ArrayList<WellbeingItem>>> call, @NonNull Throwable t) {
                ApiResponse<ArrayList<WellbeingItem>> error = new ApiResponse<>();
                error.status = "failed";
                error.error = t.getMessage();
                wellbeingPillars.setValue(error);
            }
        });
    }

    public void getRegistrationUser(RegistrationRequest request) {
        registrationUseCase.execute(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<RegistrationResponse>> call, @NonNull Response<ApiResponse<RegistrationResponse>> response) {
                if (response.isSuccessful()) {
                    registrationResponse.setValue(response.body());
                } else {
                    Gson gson = new Gson();
                    ApiResponse<ErrorData> errorResponse = gson.fromJson(
                            Objects.requireNonNull(response.errorBody()).charStream(),
                            TypeToken.getParameterized(ApiResponse.class, ErrorData.class).getType()
                    );
                    ApiResponse<RegistrationResponse> error = new ApiResponse<>();
                    error.status = "failed";
                    error.error = errorResponse.data.getMessage();
                    registrationResponse.setValue(error);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<RegistrationResponse>> call, @NonNull Throwable t) {
                ApiResponse<RegistrationResponse> error = new ApiResponse<>();
                error.status = "failed";
                error.error = t.getMessage();
                registrationResponse.setValue(error);
            }
        });
    }

}
