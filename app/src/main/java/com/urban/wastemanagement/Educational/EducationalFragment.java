package com.urban.wastemanagement.Educational;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.urban.wastemanagement.R;
import com.urban.wastemanagement.databinding.FragmentEducationalBinding;

public class EducationalFragment extends Fragment {
    private FragmentEducationalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEducationalBinding.inflate(inflater, container, false);
        getLifecycle().addObserver(binding.youtubePlayerView);

        Integer [] images = {R.drawable.f1,R.drawable.f2,R.drawable.f3,R.drawable.f4,R.drawable.f5};

        for (int image:images){flipperImages(image);}

        return binding.getRoot();
    }

    public void flipperImages(int image)
    {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);

        binding.viewFlipper.addView(imageView);
        binding.viewFlipper.setFlipInterval(4000);
        binding.viewFlipper.setAutoStart(true);

        binding.viewFlipper.setInAnimation(getActivity(),android.R.anim.slide_in_left);
        //binding.viewFlipper.setInAnimation(getActivity(),android.R.anim.slide_out_right);
    }


}

