package com.png.wolibapracticaltask.presentation.registration.fragment;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.png.wolibapracticaltask.core.SimpleTextWatcher;
import com.png.wolibapracticaltask.databinding.FragmentProfileBinding;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.ValidationViewModel;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ValidationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        viewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        viewModel.setCurrentStep(RegistrationStep.PROFILE);

        binding.chkPolicy.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Map<String, String> inputs = new HashMap<>();
                inputs.put("password", binding.etPassword.getText().toString());
                inputs.put("confirmPassword", binding.etConfirmPassword.getText().toString());
                inputs.put("birthDay", binding.etBirthDay.getText().toString());
                inputs.put("contactNo", binding.etContactNo.getText().toString());
                inputs.put("isConditionValid", "true");
                viewModel.validate(RegistrationStep.PROFILE, inputs);
            } else {
                Map<String, String> inputs = new HashMap<>();
                inputs.put("password", binding.etPassword.getText().toString());
                inputs.put("confirmPassword", binding.etConfirmPassword.getText().toString());
                inputs.put("birthDay", binding.etBirthDay.getText().toString());
                inputs.put("contactNo", binding.etContactNo.getText().toString());
                inputs.put("isConditionValid", "false");
                viewModel.validate(RegistrationStep.PROFILE, inputs);
            }
        });

        TextWatcher watcher = new SimpleTextWatcher(() -> {
            Map<String, String> inputs = new HashMap<>();
            inputs.put("password", binding.etPassword.getText().toString());
            inputs.put("confirmPassword", binding.etConfirmPassword.getText().toString());
            inputs.put("birthDay", binding.etBirthDay.getText().toString());
            inputs.put("contactNo", binding.etContactNo.getText().toString());
            inputs.put("isConditionValid", binding.chkPolicy.isChecked() ? "true" : "false");
            viewModel.validate(RegistrationStep.PROFILE, inputs);
        });

        binding.etPassword.addTextChangedListener(watcher);
        binding.etConfirmPassword.addTextChangedListener(watcher);
        binding.etBirthDay.addTextChangedListener(watcher);
        binding.etContactNo.addTextChangedListener(watcher);
    }
}
