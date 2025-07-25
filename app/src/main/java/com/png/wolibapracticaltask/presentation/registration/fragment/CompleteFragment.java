package com.png.wolibapracticaltask.presentation.registration.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.png.wolibapracticaltask.R;
import com.png.wolibapracticaltask.databinding.FragmentCompleteBinding;
import com.png.wolibapracticaltask.presentation.registration.viewmodel.ValidationViewModel;
import com.png.wolibapracticaltask.presentation.state.RegistrationStep;

public class CompleteFragment extends Fragment {

    private FragmentCompleteBinding binding;
    ValidationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCompleteBinding.inflate(inflater, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        viewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        viewModel.setCurrentStep(RegistrationStep.COMPLETE);

        /*Glide.with(this)
                .asGif()
                .load(R.raw.loader)
                .into(binding.imageView);*/

        binding.videoView.setVideoURI(Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.raw.loader_video));
        binding.videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            binding.videoView.start();
        });
    }
}
