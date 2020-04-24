package com.urban.wastemanagement.homecollection;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shivtechs.maplocationpicker.LocationPickerActivity;
import com.shivtechs.maplocationpicker.MapUtility;
import com.urban.wastemanagement.databinding.FragmentHomeCollectionBinding;
import com.urban.wastemanagement.entity.User;
import com.urban.wastemanagement.utils.Utils;

public class HomeCollectionFragment extends Fragment {

    private static final int ADDRESS_PICKER_REQUEST = 9012;
    private FragmentHomeCollectionBinding binding;
    private String address;
    private double lat, lng;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeCollectionBinding.inflate(inflater, container, false);

        binding.btnLocation.setOnClickListener(this::onTagLocationClicked);
        binding.btnOkay.setOnClickListener(this::onOkayClicked);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    final User user = documentSnapshot.toObject(User.class);
                    if (user != null) {
                        binding.tvAddress.setText(user.getAddress());
                    }
                })
                .addOnFailureListener(e -> {
                });

        return binding.getRoot();
    }

    private void onOkayClicked(View view) {
        if (address == null) {
            Utils.showToast(getContext(), "Tag your location");
        } else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .set(new User(binding.tvAddress.getText().toString(), lat, lng))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Utils.showToast(getContext(), "Address updated");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Utils.showToast(getContext(), "Failed to update the address.");
                        }
                    });
        }
    }

    private void onTagLocationClicked(View view) {
        Intent i = new Intent(getActivity(), LocationPickerActivity.class);
        startActivityForResult(i, ADDRESS_PICKER_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == ADDRESS_PICKER_REQUEST) {
            try {
                if (data != null && data.getStringExtra(MapUtility.ADDRESS) != null) {
                    address = data.getStringExtra(MapUtility.ADDRESS);
                    double selectedLatitude = data.getDoubleExtra(MapUtility.LATITUDE, 0.0);
                    double selectedLongitude = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0);

                    lat = selectedLatitude;
                    lng = selectedLongitude;

                    binding.tvAddress.setText("Address: " + address);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
