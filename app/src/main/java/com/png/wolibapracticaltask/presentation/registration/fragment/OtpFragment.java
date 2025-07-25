package com.png.wolibapracticaltask.presentation.registration.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.png.wolibapracticaltask.R;
import com.png.wolibapracticaltask.data.model.request.SendOtpRequest;
import com.png.wolibapracticaltask.data.remote.RetrofitInstance;
import com.png.wolibapracticaltask.data.remote.api.AuthApi;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSource;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSourceImpl;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;
import com.png.wolibapracticaltask.data.repository.RegistrationRepositoryImpl;
import com.png.wolibapracticaltask.databinding.FragmentOtpBinding;
import com.png.wolibapracticaltask.domain.model.RegistrationData;
import com.png.wolibapracticaltask.domain.usecase.SendOtpUseCase;
import com.png.wolibapracticaltask.domain.usecase.VerifyOtpUseCase;
import com.png.wolibapracticaltask.presentation.common.factory.VerifyOtpFactory;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.ValidationViewModel;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OtpFragment extends Fragment {

    private FragmentOtpBinding binding;
    private ValidationViewModel viewModel;
    private long mTimeLeftInMillis;
    private CountDownTimer countDownTimer;
    RegistrationViewModel registrationViewModel;
    RegistrationData data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOtpBinding.inflate(inflater, container, false);

        initView();
        otpInputHandle();
        actionListener();

        return binding.getRoot();
    }

    private void actionListener() {
        binding.txtTimer.setOnClickListener(view -> {
            if (getString(R.string.resend_otp).equals(binding.txtTimer.getText().toString())) {
                registrationViewModel.sendOtp(new SendOtpRequest(data.email));
            }
        });
    }

    private void initView() {
        viewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        viewModel.setCurrentStep(RegistrationStep.OTP);
        data = viewModel.getRegistrationData();

        AuthApi api = RetrofitInstance.getInstance().create(AuthApi.class);
        RegistrationRemoteDataSource remote = new RegistrationRemoteDataSourceImpl(api);
        RegistrationRepository registrationRepository = new RegistrationRepositoryImpl(remote);
        SendOtpUseCase sendOtpUseCase = new SendOtpUseCase(registrationRepository);
        VerifyOtpUseCase verifyOtpUseCase = new VerifyOtpUseCase(registrationRepository);
        VerifyOtpFactory factory = new VerifyOtpFactory(sendOtpUseCase, verifyOtpUseCase);
        registrationViewModel = new ViewModelProvider(this, factory).get(RegistrationViewModel.class);

        registrationViewModel.sendOtpResponse().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.status.equals("success")) {
                binding.txtTimer.setTextColor(requireContext().getColor(R.color._184a61));
                countDownTimer.start();
                Toast.makeText(requireActivity(), response.data.message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(requireActivity(), "Bad Request", Toast.LENGTH_LONG).show();
            }
        });

        countDownTimer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                binding.txtTimer.setText(getString(R.string.resend_otp));
                binding.txtTimer.setTextColor(requireContext().getColor(R.color.froly));
            }
        }.start();
    }

    private void otpInputHandle() {
        binding.edtRegisterOtp1.addTextChangedListener(
                new GenericTextWatcher(requireActivity(), binding.edtRegisterOtp1, binding.edtRegisterOtp2)
        );
        binding.edtRegisterOtp2.addTextChangedListener(
                new GenericTextWatcher(requireActivity(), binding.edtRegisterOtp2, binding.edtRegisterOtp3)
        );
        binding.edtRegisterOtp3.addTextChangedListener(
                new GenericTextWatcher(requireActivity(), binding.edtRegisterOtp3, binding.edtRegisterOtp4)
        );
        binding.edtRegisterOtp4.addTextChangedListener(
                new GenericTextWatcher(requireActivity(), binding.edtRegisterOtp4, binding.edtRegisterOtp5)
        );
        binding.edtRegisterOtp5.addTextChangedListener(
                new GenericTextWatcher(requireActivity(), binding.edtRegisterOtp5, binding.edtRegisterOtp6)
        );
        binding.edtRegisterOtp6.addTextChangedListener(
                new GenericTextWatcher(requireActivity(), binding.edtRegisterOtp6, null)
        );

        binding.edtRegisterOtp1.setOnKeyListener(new GenericKeyEvent(binding.edtRegisterOtp1, null));
        binding.edtRegisterOtp2.setOnKeyListener(new GenericKeyEvent(binding.edtRegisterOtp2, binding.edtRegisterOtp1));
        binding.edtRegisterOtp3.setOnKeyListener(new GenericKeyEvent(binding.edtRegisterOtp3, binding.edtRegisterOtp2));
        binding.edtRegisterOtp4.setOnKeyListener(new GenericKeyEvent(binding.edtRegisterOtp4, binding.edtRegisterOtp3));
        binding.edtRegisterOtp5.setOnKeyListener(new GenericKeyEvent(binding.edtRegisterOtp5, binding.edtRegisterOtp4));
        binding.edtRegisterOtp6.setOnKeyListener(new GenericKeyEvent(binding.edtRegisterOtp6, binding.edtRegisterOtp5));
    }

    public class GenericKeyEvent implements View.OnKeyListener {

        private final EditText currentView;
        private final EditText previousView;

        public GenericKeyEvent(EditText currentView, EditText previousView) {
            this.currentView = currentView;
            this.previousView = previousView;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_DEL &&
                    currentView.getId() != R.id.edtRegisterOtp1 &&
                    currentView.getText().toString().isEmpty()) {

                if (previousView != null) {
                    previousView.setText("");
                    previousView.requestFocus();
                }
                return true;
            }
            return false;
        }
    }

    public class GenericTextWatcher implements TextWatcher {

        private final Context context;
        private final View currentView;
        private final View nextView;

        public GenericTextWatcher(Context context, View currentView, View nextView) {
            this.context = context;
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();

            if (text.length() == 1 && nextView != null) {
                nextView.requestFocus();
            }

            if (currentView.getId() == R.id.edtRegisterOtp6 && text.length() == 1) {
                hideKeyPad(context, currentView);
            }

            Activity activity = (Activity) context;
            String otp = collectOtpFromInputs(activity);
            Map<String, String> inputs = new HashMap<>();
            inputs.put("otp", otp);
            viewModel.validate(RegistrationStep.OTP, inputs); // Enable button or send OTP
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        private String collectOtpFromInputs(Activity activity) {
            return getText(activity, R.id.edtRegisterOtp1) +
                    getText(activity, R.id.edtRegisterOtp2) +
                    getText(activity, R.id.edtRegisterOtp3) +
                    getText(activity, R.id.edtRegisterOtp4) +
                    getText(activity, R.id.edtRegisterOtp5) +
                    getText(activity, R.id.edtRegisterOtp6);
        }

        private String getText(Activity activity, int id) {
            EditText editText = activity.findViewById(id);
            return editText.getText().toString().trim();
        }
    }

    public static void hideKeyPad(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void updateCountDownText() {
        long minutes = (mTimeLeftInMillis / 1000) / 60;
        long seconds = (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), " %02d:%02d", minutes, seconds);
        binding.txtTimer.setText(getString(R.string.resend_otp_in, timeLeftFormatted));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }
}
