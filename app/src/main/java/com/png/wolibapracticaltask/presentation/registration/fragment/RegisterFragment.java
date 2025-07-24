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
import com.png.wolibapracticaltask.databinding.FragmentRegisterBinding;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;
import com.png.wolibapracticaltask.presentation.viewmodel.RegistrationViewModel;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegistrationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        viewModel.setCurrentStep(RegistrationStep.REGISTER);

        TextWatcher watcher = new SimpleTextWatcher(() -> {
            Map<String, String> inputs = new HashMap<>();
            inputs.put("firstName", binding.etFirstName.getText().toString());
            inputs.put("lastName", binding.etLastName.getText().toString());
            inputs.put("email", binding.etEmail.getText().toString());
            inputs.put("isEmailValid", "false");
            viewModel.validate(RegistrationStep.REGISTER, inputs);
        });

        binding.etFirstName.addTextChangedListener(watcher);
        binding.etLastName.addTextChangedListener(watcher);
        binding.etEmail.addTextChangedListener(watcher);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }
}
