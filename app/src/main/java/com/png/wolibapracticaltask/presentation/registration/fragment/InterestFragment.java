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

import com.png.wolibapracticaltask.core.InterestMockData;
import com.png.wolibapracticaltask.databinding.FragmentInterestBinding;
import com.png.wolibapracticaltask.presentation.registration.adapter.InterestTypeAdapter;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;
import com.png.wolibapracticaltask.presentation.viewmodel.RegistrationViewModel;

public class InterestFragment extends Fragment {

    private FragmentInterestBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInterestBinding.inflate(inflater, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        RegistrationViewModel viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        viewModel.setCurrentStep(RegistrationStep.INTEREST);

        binding.rvInterestType.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvInterestType.setAdapter(new InterestTypeAdapter(InterestMockData.getMockCategoryList()));
    }
}
