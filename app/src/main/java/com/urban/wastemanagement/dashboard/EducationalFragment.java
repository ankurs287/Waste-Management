package com.urban.wastemanagement.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.urban.wastemanagement.databinding.FragmentEducationalBinding;

public class EducationalFragment extends Fragment {


    private FragmentEducationalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEducationalBinding.inflate(inflater, container, false);

        getLifecycle().addObserver(binding.youtubePlayerView);


        return binding.getRoot();
    }
}
