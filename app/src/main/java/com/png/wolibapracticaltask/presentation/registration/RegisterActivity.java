package com.png.wolibapracticaltask.presentation.registration;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.png.wolibapracticaltask.R;
import com.png.wolibapracticaltask.databinding.ActivityRegistrationBinding;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;
import com.png.wolibapracticaltask.presentation.viewmodel.RegistrationViewModel;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private NavController navController;
    private RegistrationStep stepCount = RegistrationStep.INTEREST;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        actionListener();
    }

    private void initView() {
        RegistrationViewModel viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        // Enable/disable button based on validation state
        viewModel.isCurrentStepValid().observe(this, isValid -> binding.appCompatButton.setEnabled(isValid != null && isValid));
    }

    private void actionListener() {
        binding.appCompatButton.setOnClickListener(view -> {
            switch (stepCount){
                case REGISTER:
                    navController.navigate(R.id.action_to_otpFragment);
                    stepCount = RegistrationStep.OTP;
                    break;
                case OTP:
                    navController.navigate(R.id.action_to_profileFragment);
                    stepCount = RegistrationStep.PROFILE;
                    break;
                case PROFILE:
                    navController.navigate(R.id.action_to_interestFragment);
                    stepCount = RegistrationStep.INTEREST;
                    break;
                case INTEREST:
                    navController.navigate(R.id.action_to_wellbeingFragment);
                    stepCount = RegistrationStep.WELLBEING;
                    break;
                case WELLBEING:
                    navController.navigate(R.id.action_to_completeFragment);
                    break;
            }
        });
    }
}
