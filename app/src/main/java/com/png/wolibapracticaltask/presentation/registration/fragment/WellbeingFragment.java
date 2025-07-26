package com.png.wolibapracticaltask.presentation.registration.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.png.wolibapracticaltask.core.common.ProgressBarDialog;
import com.png.wolibapracticaltask.data.remote.RetrofitInstance;
import com.png.wolibapracticaltask.data.remote.api.AuthApi;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSource;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSourceImpl;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;
import com.png.wolibapracticaltask.data.repository.RegistrationRepositoryImpl;
import com.png.wolibapracticaltask.databinding.FragmentWellbeingBinding;
import com.png.wolibapracticaltask.data.model.response.WellbeingItem;
import com.png.wolibapracticaltask.domain.usecase.WellbeingPillarUseCase;
import com.png.wolibapracticaltask.presentation.common.factory.WellbeingPillarFactory;
import com.png.wolibapracticaltask.presentation.registration.adapter.WellbeingAdapter;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.ValidationViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WellbeingFragment extends Fragment implements WellbeingAdapter.WellbeingListener {

    private FragmentWellbeingBinding binding;
    ValidationViewModel viewModel;
    RegistrationViewModel registrationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWellbeingBinding.inflate(inflater, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        viewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        viewModel.setCurrentStep(RegistrationStep.WELLBEING);

        AuthApi api = RetrofitInstance.getInstance().create(AuthApi.class);
        RegistrationRemoteDataSource remote = new RegistrationRemoteDataSourceImpl(api);
        RegistrationRepository registrationRepository = new RegistrationRepositoryImpl(remote);
        WellbeingPillarUseCase wellbeingInterestUseCase = new WellbeingPillarUseCase(registrationRepository);
        WellbeingPillarFactory factory = new WellbeingPillarFactory(wellbeingInterestUseCase);
        registrationViewModel = new ViewModelProvider(this, factory).get(RegistrationViewModel.class);

        ProgressBarDialog.show(requireContext());
        registrationViewModel.getWellbeingPillars();
        registrationViewModel.getWellbeingPillarsResponse().observe(getViewLifecycleOwner(), response -> {
            ProgressBarDialog.hide();
            if (response != null && response.status.equals("success")) {
                binding.rvWellbeing.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.rvWellbeing.setAdapter(new WellbeingAdapter(
                        response.data,
                        requireContext(),
                        this)
                );
            } else {
                Toast.makeText(requireActivity(), "Bad Request", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }

    @Override
    public void onItemClick(ArrayList<WellbeingItem> wellbeingItems) {
        StringBuilder interest = new StringBuilder();
        for (WellbeingItem item : wellbeingItems) {
            interest.append(item.getId()).append(",");
        }
        Map<String, String> inputs = new HashMap<>();
        if (!wellbeingItems.isEmpty()) {
            inputs.put("wellbeingItem", interest.substring(0, interest.length() - 1));
            inputs.put("count", String.valueOf(wellbeingItems.size()));
        }else {
            inputs.put("count", "");
        }
        viewModel.validate(RegistrationStep.WELLBEING, inputs);
    }
}
