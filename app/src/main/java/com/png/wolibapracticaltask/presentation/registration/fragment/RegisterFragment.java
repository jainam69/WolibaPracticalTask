package com.png.wolibapracticaltask.presentation.registration.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.png.wolibapracticaltask.R;
import com.png.wolibapracticaltask.core.common.Common;
import com.png.wolibapracticaltask.data.model.request.SendOtpRequest;
import com.png.wolibapracticaltask.data.remote.RetrofitInstance;
import com.png.wolibapracticaltask.data.remote.api.AuthApi;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSource;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSourceImpl;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;
import com.png.wolibapracticaltask.data.repository.RegistrationRepositoryImpl;
import com.png.wolibapracticaltask.databinding.FragmentRegisterBinding;
import com.png.wolibapracticaltask.domain.usecase.VerifyEmailUseCase;
import com.png.wolibapracticaltask.presentation.common.factory.VerifyEmailFactory;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.ValidationViewModel;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private ValidationViewModel viewModel;
    RegistrationViewModel registrationViewModel;
    public String isEmailVerify = "false";
    Map<String, String> inputs = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        initView();
        actionListener();

        return binding.getRoot();
    }

    private void initView() {
        viewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        viewModel.setCurrentStep(RegistrationStep.REGISTER);

        AuthApi api = RetrofitInstance.getInstance().create(AuthApi.class);
        RegistrationRemoteDataSource remote = new RegistrationRemoteDataSourceImpl(api);
        RegistrationRepository registrationRepository = new RegistrationRepositoryImpl(remote);
        VerifyEmailUseCase verifyEmailUseCase = new VerifyEmailUseCase(registrationRepository);
        VerifyEmailFactory factory = new VerifyEmailFactory(verifyEmailUseCase);
        registrationViewModel = new ViewModelProvider(this, factory).get(RegistrationViewModel.class);

        registrationViewModel.getVerifyEmailResponse().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.status.equals("failed")) {
                    Toast.makeText(requireContext(), response.error, Toast.LENGTH_LONG).show();
                } else if (response.status.equals("success") && !response.data.is_verified) {
                    isEmailVerify = response.data.is_verified.toString();
                    binding.txtEmailError.setVisibility(View.VISIBLE);
                    binding.txtEmailError.setText(getString(R.string.email_not_invited));
                } else {
                    isEmailVerify = response.data.is_verified.toString();
                    inputs.put("isEmailValid", isEmailVerify);
                    viewModel.validate(RegistrationStep.REGISTER, inputs);
                    binding.etEmail.setEnabled(false);
                    binding.txtEmailError.setVisibility(View.GONE);
                    binding.tvCompanyName.setVisibility(View.VISIBLE);
                    binding.etCompanyName.setVisibility(View.VISIBLE);
                    binding.etCompanyName.setText(response.data.company_name);
                    binding.txtVerify.setText(getString(R.string.verified));
                    binding.txtVerify.setTextColor(requireContext().getColor(R.color._75b084));
                }
            } else {
                Toast.makeText(requireContext(), "Bad Request", Toast.LENGTH_LONG).show();
            }
        });

        binding.etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.txtFirstNameError.setVisibility(View.GONE);
                } else {
                    binding.txtFirstNameError.setVisibility(View.VISIBLE);
                }
                inputs.put("firstName", charSequence.toString());
                viewModel.validate(RegistrationStep.REGISTER, inputs);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.txtLastNameError.setVisibility(View.GONE);
                } else {
                    binding.txtLastNameError.setVisibility(View.VISIBLE);
                }
                inputs.put("lastName", charSequence.toString());
                viewModel.validate(RegistrationStep.REGISTER, inputs);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()) {
                    binding.txtEmailError.setText(getString(R.string.enter_email));
                    binding.txtEmailError.setVisibility(View.VISIBLE);
                } else if (!Common.isValidEmail(charSequence.toString().trim())) {
                    binding.txtEmailError.setText(getString(R.string.enter_valid_email));
                    binding.txtEmailError.setVisibility(View.VISIBLE);
                } else {
                    binding.txtEmailError.setVisibility(View.GONE);
                }
                inputs.put("email", charSequence.toString());
                viewModel.validate(RegistrationStep.REGISTER, inputs);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void actionListener() {
        binding.txtVerify.setOnClickListener(view -> {
            if (binding.txtVerify.getText().toString().equals(getString(R.string.verify))) {
                if (binding.etEmail.getText().toString().trim().isBlank()) {
                    Toast.makeText(getContext(), getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                } else if (!Common.isValidEmail(binding.etEmail.getText().toString().trim())) {
                    Toast.makeText(getContext(), getString(R.string.enter_valid_email), Toast.LENGTH_SHORT).show();
                } else {
                    Common.hideKeyPad(requireContext(), binding.txtVerify);
                    registrationViewModel.verifyEmail(new SendOtpRequest(binding.etEmail.getText().toString()));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }
}
