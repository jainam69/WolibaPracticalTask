package com.png.wolibapracticaltask.presentation.welcome;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.png.wolibapracticaltask.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWelcomeBinding binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
