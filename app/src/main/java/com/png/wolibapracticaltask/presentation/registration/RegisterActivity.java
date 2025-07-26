package com.png.wolibapracticaltask.presentation.registration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.png.wolibapracticaltask.R;
import com.png.wolibapracticaltask.core.common.Common;
import com.png.wolibapracticaltask.core.common.ProgressBarDialog;
import com.png.wolibapracticaltask.data.model.request.RegistrationRequest;
import com.png.wolibapracticaltask.data.model.request.SendOtpRequest;
import com.png.wolibapracticaltask.data.model.request.VerifyOtpRequest;
import com.png.wolibapracticaltask.data.remote.RetrofitInstance;
import com.png.wolibapracticaltask.data.remote.api.AuthApi;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSource;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSourceImpl;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;
import com.png.wolibapracticaltask.data.repository.RegistrationRepositoryImpl;
import com.png.wolibapracticaltask.databinding.ActivityRegistrationBinding;
import com.png.wolibapracticaltask.domain.model.RegistrationData;
import com.png.wolibapracticaltask.domain.usecase.RegistrationUseCase;
import com.png.wolibapracticaltask.domain.usecase.SendOtpUseCase;
import com.png.wolibapracticaltask.domain.usecase.VerifyEmailUseCase;
import com.png.wolibapracticaltask.domain.usecase.VerifyOtpUseCase;
import com.png.wolibapracticaltask.domain.usecase.WellbeingInterestUseCase;
import com.png.wolibapracticaltask.domain.usecase.WellbeingPillarUseCase;
import com.png.wolibapracticaltask.presentation.common.factory.RegistrationViewModelFactory;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.ValidationViewModel;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;
import com.png.wolibapracticaltask.presentation.welcome.WelcomeActivity;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private NavController navController;
    private RegistrationStep stepCount = RegistrationStep.REGISTER;
    RegistrationViewModel registrationViewModel;
    private String token;
    RegistrationData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        actionListener();
    }

    private void initView() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        AuthApi api = RetrofitInstance.getInstance().create(AuthApi.class);
        RegistrationViewModelFactory factory = getRegistrationViewModelFactory(api);
        registrationViewModel = new ViewModelProvider(this, factory).get(RegistrationViewModel.class);

        // Enable/disable button based on validation state
        ValidationViewModel viewModel = new ViewModelProvider(this).get(ValidationViewModel.class);
        viewModel.isCurrentStepValid().observe(this, isValid -> {
            binding.appCompatButton.setEnabled(isValid != null && isValid);
        });
        data = viewModel.getRegistrationData();

        registrationViewModel.sendOtpResponse().observe(this, response -> {
            ProgressBarDialog.hide();
            if (response.status.equals("failed")) {
                Toast.makeText(this, response.error, Toast.LENGTH_LONG).show();
            } else if (response.status.equals("success")) {
                token = response.data.token;
                navController.navigate(R.id.action_to_otpFragment);
                binding.appCompatButton.setText(getString(R.string.submit));
                binding.appCompatButton.setEnabled(false);
                stepCount = RegistrationStep.OTP;
                Toast.makeText(this, response.data.message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Bad Request", Toast.LENGTH_LONG).show();
            }
        });

        registrationViewModel.getVerifyOtpResponse().observe(this, response -> {
            ProgressBarDialog.hide();
            if (response.status.equals("failed")) {
                viewModel.setOTPValid(false);
                Toast.makeText(this, response.error, Toast.LENGTH_LONG).show();
            } else if (response.status.equals("success")) {
                navController.navigate(R.id.action_to_profileFragment);
                binding.appCompatButton.setText(getString(R.string.next));
                binding.appCompatButton.setEnabled(false);
                stepCount = RegistrationStep.PROFILE;
                Toast.makeText(this, response.data.get(0), Toast.LENGTH_LONG).show();
            } else {
                viewModel.setOTPValid(false);
                Toast.makeText(this, "Incorrect OTP", Toast.LENGTH_LONG).show();
            }
        });

        registrationViewModel.getRegistrationUserResponse().observe(this, response -> {
            if (response.status.equals("failed")) {
                Toast.makeText(this, response.error, Toast.LENGTH_LONG).show();
            } else if (response.status.equals("success")) {
                Intent intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Bad Request", Toast.LENGTH_LONG).show();
            }
        });
    }

    @NonNull
    private static RegistrationViewModelFactory getRegistrationViewModelFactory(AuthApi api) {
        RegistrationRemoteDataSource remote = new RegistrationRemoteDataSourceImpl(api);
        RegistrationRepository registrationRepository = new RegistrationRepositoryImpl(remote);
        SendOtpUseCase sendOtpUseCase = new SendOtpUseCase(registrationRepository);
        VerifyEmailUseCase verifyEmailUseCase = new VerifyEmailUseCase(registrationRepository);
        VerifyOtpUseCase verifyOtpUseCase = new VerifyOtpUseCase(registrationRepository);
        WellbeingInterestUseCase wellbeingInterestUseCase = new WellbeingInterestUseCase(registrationRepository);
        WellbeingPillarUseCase wellbeingPillarUseCase = new WellbeingPillarUseCase(registrationRepository);
        RegistrationUseCase registrationUseCase = new RegistrationUseCase(registrationRepository);
        return new RegistrationViewModelFactory(sendOtpUseCase, verifyEmailUseCase, verifyOtpUseCase, wellbeingInterestUseCase, wellbeingPillarUseCase, registrationUseCase);
    }

    private void actionListener() {
        binding.appCompatButton.setOnClickListener(view -> {
            switch (stepCount) {
                case REGISTER:
                    ProgressBarDialog.show(this);
                    registrationViewModel.sendOtp(new SendOtpRequest(data.email));
                    break;
                case OTP:
                    ProgressBarDialog.show(this);
                    registrationViewModel.verifyOTp(new VerifyOtpRequest(data.otp, token));
                    break;
                case PROFILE:
                    navController.navigate(R.id.action_to_interestFragment);
                    binding.appCompatButton.setText(getString(R.string.next));
                    binding.appCompatButton.setEnabled(false);
                    stepCount = RegistrationStep.INTEREST;
                    break;
                case INTEREST:
                    navController.navigate(R.id.action_to_wellbeingFragment);
                    binding.appCompatButton.setText(getString(R.string.next));
                    binding.appCompatButton.setEnabled(false);
                    stepCount = RegistrationStep.WELLBEING;
                    break;
                case WELLBEING:
                    binding.appCompatButton.setVisibility(View.GONE);
                    navController.navigate(R.id.action_to_completeFragment);
                    stepCount = RegistrationStep.COMPLETE;
                    registrationViewModel.getRegistrationUser(new RegistrationRequest(data.firstName,
                            data.lastName, data.email, data.confirmPassword, "Asia/Kolkata", token,
                            1, Common.convertDateToIso(data.birthDay), data.contactNo, 0,
                            data.isConditionValid, data.selectedInterests, data.selectedWellbeingOptions)
                    );
                    break;
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}
