package com.png.wolibapracticaltask.presentation.registration;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.png.wolibapracticaltask.R;
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
import com.png.wolibapracticaltask.domain.usecase.SendOtpUseCase;
import com.png.wolibapracticaltask.domain.usecase.VerifyEmailUseCase;
import com.png.wolibapracticaltask.domain.usecase.VerifyOtpUseCase;
import com.png.wolibapracticaltask.domain.usecase.WellbeingInterestUseCase;
import com.png.wolibapracticaltask.domain.usecase.WellbeingPillarUseCase;
import com.png.wolibapracticaltask.presentation.common.factory.RegistrationViewModelFactory;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.ValidationViewModel;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;

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

        initView();
        actionListener();
    }

    private void initView() {
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
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
            if (response != null && response.status.equals("success")) {
                token = response.data.token;
                navController.navigate(R.id.action_to_otpFragment);
                binding.appCompatButton.setText(getString(R.string.submit));
                stepCount = RegistrationStep.OTP;
                Toast.makeText(this, response.data.message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Bad Request", Toast.LENGTH_LONG).show();
            }
        });

        registrationViewModel.getVerifyOtpResponse().observe(this, response -> {
            if (response != null && response.status.equals("success")) {
                navController.navigate(R.id.action_to_profileFragment);
                binding.appCompatButton.setText(getString(R.string.next));
                stepCount = RegistrationStep.PROFILE;
                Toast.makeText(this, response.data.get(0), Toast.LENGTH_LONG).show();
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
        return new RegistrationViewModelFactory(sendOtpUseCase, verifyEmailUseCase, verifyOtpUseCase, wellbeingInterestUseCase, wellbeingPillarUseCase);
    }

    private void actionListener() {
        binding.appCompatButton.setOnClickListener(view -> {
            switch (stepCount) {
                case REGISTER:
                    registrationViewModel.sendOtp(new SendOtpRequest(data.email));
                    break;
                case OTP:
                    registrationViewModel.verifyOTp(new VerifyOtpRequest(data.otp, token));
                    break;
                case PROFILE:
                    navController.navigate(R.id.action_to_interestFragment);
                    binding.appCompatButton.setText(getString(R.string.next));
                    stepCount = RegistrationStep.INTEREST;
                    break;
                case INTEREST:
                    navController.navigate(R.id.action_to_wellbeingFragment);
                    binding.appCompatButton.setText(getString(R.string.next));
                    stepCount = RegistrationStep.WELLBEING;
                    break;
                case WELLBEING:
                    binding.appCompatButton.setText(getString(R.string.next));
                    navController.navigate(R.id.action_to_completeFragment);
                    break;
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}
