package com.png.wolibapracticaltask.presentation.registration.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.png.wolibapracticaltask.core.WellbeingMockData;
import com.png.wolibapracticaltask.databinding.FragmentWellbeingBinding;
import com.png.wolibapracticaltask.data.model.response.WellbeingItem;
import com.png.wolibapracticaltask.presentation.registration.adapter.WellbeingAdapter;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.ValidationViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WellbeingFragment extends Fragment implements WellbeingAdapter.WellbeingListener {

    private FragmentWellbeingBinding binding;
    ValidationViewModel viewModel;

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

        binding.rvWellbeing.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvWellbeing.setAdapter(new WellbeingAdapter(
                WellbeingMockData.getWellbeingMockData(),
                requireContext(),
                this)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }

    @Override
    public void onItemClick(ArrayList<WellbeingItem> wellbeingItems) {
        Map<String, String> inputs = new HashMap<>();
        inputs.put("count", String.valueOf(wellbeingItems.size()));
        viewModel.validate(RegistrationStep.WELLBEING, inputs);
    }
}
