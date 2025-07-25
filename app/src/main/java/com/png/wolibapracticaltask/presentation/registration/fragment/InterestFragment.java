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

import com.png.wolibapracticaltask.data.model.response.InterestResponse;
import com.png.wolibapracticaltask.data.remote.ApiConstants;
import com.png.wolibapracticaltask.data.remote.RetrofitInstance;
import com.png.wolibapracticaltask.data.remote.api.AuthApi;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSource;
import com.png.wolibapracticaltask.data.remote.datasource.RegistrationRemoteDataSourceImpl;
import com.png.wolibapracticaltask.data.repository.RegistrationRepository;
import com.png.wolibapracticaltask.data.repository.RegistrationRepositoryImpl;
import com.png.wolibapracticaltask.databinding.FragmentInterestBinding;
import com.png.wolibapracticaltask.domain.model.Interest;
import com.png.wolibapracticaltask.domain.model.InterestType;
import com.png.wolibapracticaltask.domain.usecase.WellbeingInterestUseCase;
import com.png.wolibapracticaltask.presentation.common.factory.WellbeingInterestFactory;
import com.png.wolibapracticaltask.presentation.registration.adapter.InterestTypeAdapter;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.RegistrationViewModel;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.ValidationViewModel;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InterestFragment extends Fragment implements InterestTypeAdapter.InterestTypeListener {

    private FragmentInterestBinding binding;
    ValidationViewModel viewModel;
    RegistrationViewModel registrationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInterestBinding.inflate(inflater, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        viewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        viewModel.setCurrentStep(RegistrationStep.INTEREST);

        AuthApi api = RetrofitInstance.getInstance().create(AuthApi.class);
        RegistrationRemoteDataSource remote = new RegistrationRemoteDataSourceImpl(api);
        RegistrationRepository registrationRepository = new RegistrationRepositoryImpl(remote);
        WellbeingInterestUseCase wellbeingInterestUseCase = new WellbeingInterestUseCase(registrationRepository);
        WellbeingInterestFactory factory = new WellbeingInterestFactory(wellbeingInterestUseCase);
        registrationViewModel = new ViewModelProvider(this, factory).get(RegistrationViewModel.class);

        registrationViewModel.getWellbeingInterest();
        registrationViewModel.getInterestResponse().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.status.equals("success")) {
                ArrayList<InterestResponse> interestsList = response.data.get(0);
                ArrayList<InterestType> interestTypes = new ArrayList<>();
                for (String item : getStrings(interestsList)) {
                    ArrayList<Interest> interest = new ArrayList<>();
                    for (InterestResponse item2 : interestsList) {
                        if (item.equals(item2.interest_type)) {
                            interest.add(new Interest(item2.id, item2.name, ApiConstants.IMAGE_BASE_URL + item2.interest_color_icon, ApiConstants.IMAGE_BASE_URL + item2.interest_white_icon));
                        }
                    }
                    interestTypes.add(new InterestType(item, interest));
                }
                binding.rvInterestType.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.rvInterestType.setAdapter(new InterestTypeAdapter(interestTypes, this));
            } else {
                Toast.makeText(requireActivity(), "Bad Request", Toast.LENGTH_LONG).show();
            }
        });
    }

    @NonNull
    private static ArrayList<String> getStrings(ArrayList<InterestResponse> interestsList) {
        ArrayList<String> interestType = new ArrayList<>();
        for (InterestResponse item : interestsList) {
            if (!interestType.contains(item.interest_type)) {
                interestType.add(item.interest_type);
            }
        }
        return interestType;
    }

    @Override
    public void onItemClick(ArrayList<Interest> interestsItems) {
        StringBuilder interest = new StringBuilder();
        for (Interest item : interestsItems) {
            interest.append(item.getName()).append(",");
        }
        Map<String, String> inputs = new HashMap<>();
        inputs.put("interest", interest.substring(0, interest.length() - 1));
        inputs.put("count", String.valueOf(interestsItems.size()));
        viewModel.validate(RegistrationStep.INTEREST, inputs);
    }
}
