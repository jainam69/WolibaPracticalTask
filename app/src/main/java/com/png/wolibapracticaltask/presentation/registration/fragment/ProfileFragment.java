package com.png.wolibapracticaltask.presentation.registration.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.png.wolibapracticaltask.R;
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
    Map<String, String> inputs = new HashMap<>();

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

            DatePickerDialog datePicker = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                String date = String.format(Locale.US, "%02d/%02d/%04d", month + 1, dayOfMonth, year);
                binding.etBirthDay.setText(date);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            // Set limits
            datePicker.getDatePicker().setMinDate(minDate.getTimeInMillis());
            datePicker.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
            datePicker.show();
        });
    }

    private void initView() {
        viewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        viewModel.setCurrentStep(RegistrationStep.PROFILE);

        binding.chkPolicy.setText(Html.fromHtml(getString(R.string.agree_text), Html.FROM_HTML_MODE_LEGACY));
        binding.chkPolicy.setSupportButtonTintList(null);

        binding.chkPolicy.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                inputs.put("isConditionValid", "true");
            } else {
                inputs.put("isConditionValid", "false");
            }
            viewModel.validate(RegistrationStep.PROFILE, inputs);
        });

        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()) {
                    binding.txtPasswordError.setText(getString(R.string.please_enter_password));
                    binding.txtPasswordError.setVisibility(View.VISIBLE);
                } else if (charSequence.toString().length() < 6) {
                    binding.txtPasswordError.setText(getString(R.string.password_six_digit));
                    binding.txtPasswordError.setVisibility(View.VISIBLE);
                } else if (!binding.etConfirmPassword.getText().toString().trim().isEmpty() && !binding.etConfirmPassword.getText().toString().trim().equals(charSequence.toString().trim())) {
                    binding.txtConfirmPasswordError.setText(getString(R.string.password_not_match));
                    binding.txtConfirmPasswordError.setVisibility(View.VISIBLE);
                } else if (binding.etConfirmPassword.getText().toString().trim().equals(charSequence.toString().trim())) {
                    binding.txtConfirmPasswordError.setVisibility(View.GONE);
                } else {
                    binding.txtPasswordError.setVisibility(View.GONE);
                }
                inputs.put("password", binding.etPassword.getText().toString());
                viewModel.validate(RegistrationStep.PROFILE, inputs);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()) {
                    binding.txtConfirmPasswordError.setText(getString(R.string.please_enter_confirm_password));
                    binding.txtConfirmPasswordError.setVisibility(View.VISIBLE);
                } else if (!binding.etPassword.getText().toString().trim().equals(charSequence.toString().trim())) {
                    binding.txtConfirmPasswordError.setText(getString(R.string.password_not_match));
                    binding.txtConfirmPasswordError.setVisibility(View.VISIBLE);
                } else {
                    binding.txtConfirmPasswordError.setVisibility(View.GONE);
                }
                inputs.put("confirmPassword", binding.etConfirmPassword.getText().toString());
                viewModel.validate(RegistrationStep.PROFILE, inputs);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etBirthDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputs.put("birthDay", binding.etBirthDay.getText().toString());
                viewModel.validate(RegistrationStep.PROFILE, inputs);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etContactNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()) {
                    binding.txtContactNoError.setText(getString(R.string.please_enter_contact_no));
                    binding.txtContactNoError.setVisibility(View.VISIBLE);
                } else if (charSequence.toString().trim().length() != 10) {
                    binding.txtContactNoError.setText(getString(R.string.please_enter_valid_contact_no));
                    binding.txtContactNoError.setVisibility(View.VISIBLE);
                } else {
                    binding.txtContactNoError.setVisibility(View.GONE);
                }
                inputs.put("contactNo", binding.etContactNo.getText().toString());
                viewModel.validate(RegistrationStep.PROFILE, inputs);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
