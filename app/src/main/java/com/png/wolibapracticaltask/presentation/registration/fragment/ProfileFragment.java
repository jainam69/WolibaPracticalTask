package com.png.wolibapracticaltask.presentation.registration.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ValidationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        initView();
        actionListener();

        return binding.getRoot();
    }

    private void actionListener() {
        binding.etBirthDay.setInputType(InputType.TYPE_NULL); // Disable keyboard

        binding.etBirthDay.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();

            // Calculate max date = today - 13 years
            Calendar maxDate = Calendar.getInstance();
            maxDate.add(Calendar.YEAR, -18);

            // Calculate min date = today - 100 years
            Calendar minDate = Calendar.getInstance();
            minDate.add(Calendar.YEAR, -100);

            DatePickerDialog datePicker = new DatePickerDialog(requireContext(),
                    (view, year, month, dayOfMonth) -> {
                        String date = String.format(Locale.US, "%02d/%02d/%04d", month + 1, dayOfMonth, year);
                        binding.etBirthDay.setText(date);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            // Set limits
            datePicker.getDatePicker().setMinDate(minDate.getTimeInMillis());
            datePicker.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
            datePicker.show();
        });
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
