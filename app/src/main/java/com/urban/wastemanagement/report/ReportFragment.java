package com.urban.wastemanagement.report;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shivtechs.maplocationpicker.LocationPickerActivity;
import com.shivtechs.maplocationpicker.MapUtility;
import com.urban.wastemanagement.databinding.FragmentReportBinding;
import com.urban.wastemanagement.entity.Report;
import com.urban.wastemanagement.utils.Utils;

import java.io.File;

public class ReportFragment extends Fragment {

    private static final int ADDRESS_PICKER_REQUEST = 901;
    private FragmentReportBinding binding;
    private Image wasteImage;
    private Double wasteLat;
    private Double wasteLng;
    private String wasteAddress;

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);

        binding.btnWasteImage.setOnClickListener(this::onSelectImageClicked);
        binding.btnReport.setOnClickListener(this::onReportClicked);
        binding.btnLocation.setOnClickListener(this::onChooseLocationClicked);

        return binding.getRoot();
    }

    private void onChooseLocationClicked(View view) {
        Intent i = new Intent(getActivity(), LocationPickerActivity.class);
        startActivityForResult(i, ADDRESS_PICKER_REQUEST);
    }

    private void onSelectImageClicked(View view) {
        ImagePicker.create(this).single().start();
    }

    private void onReportClicked(View view) {
        if (binding.rbgWasteType.getCheckedRadioButtonId() == -1) {
            Utils.showToast(getContext(), "Choose Waste Type");
        } else if (binding.rbgOdour.getCheckedRadioButtonId() == -1) {
            Utils.showToast(getContext(), "Choose Odour");
        } else if (wasteImage == null) {
            Utils.showToast(getContext(), "Select Waste Photo");
        } else if (wasteLng == null || wasteLat == null || wasteAddress == null
                || wasteAddress.trim().isEmpty()) {
            Utils.showToast(getContext(), "Provide Dumping Site Location");
        } else {
            // all okay. send the report
            sendReport();
        }
    }

    private void sendReport() {
        String description = binding.etDescription.getText().toString().trim();
        String wasteType = ((RadioButton) binding.getRoot().findViewById(
                binding.rbgWasteType.getCheckedRadioButtonId())).getText().toString();
        String odour = ((RadioButton) binding.getRoot().findViewById(
                binding.rbgOdour.getCheckedRadioButtonId())).getText().toString();
        boolean critical = binding.cbCritical.isChecked();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference reportRef = db.collection("reports").document();

        Uri file = Uri.fromFile(new File(wasteImage.getPath()));
        StorageReference imageRef = storageRef.child("report_images/" + reportRef.getId() + ".jpg");
        UploadTask uploadTask = imageRef.putFile(file);
        uploadTask
                .addOnFailureListener(exception -> {
                    Utils.showToast(getContext(), "Unable to send the report. Retry.");
                })
                .addOnSuccessListener(taskSnapshot -> {
                    Report report = new Report(
                            reportRef.getId(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            false,
                            description,
                            wasteType,
                            odour,
                            critical,
                            wasteLat,
                            wasteLng,
                            wasteAddress);

                    reportRef.set(report).addOnSuccessListener(
                            aVoid -> Utils.showToast(getContext(),
                                    "Reported Successfully")).addOnFailureListener(
                            e -> Utils.showToast(getContext(),
                                    "Unable to send the report. Retry."));
                });
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            wasteImage = ImagePicker.getFirstImageOrNull(data);
            if (wasteImage != null) {
                binding.btnWasteImage.setBackground(new BitmapDrawable(getResources(),
                        BitmapFactory.decodeFile(wasteImage.getPath())));
            }
        } else if (requestCode == ADDRESS_PICKER_REQUEST) {
            try {
                if (data != null && data.getStringExtra(MapUtility.ADDRESS) != null) {
                    String address = data.getStringExtra(MapUtility.ADDRESS);
                    double selectedLatitude = data.getDoubleExtra(MapUtility.LATITUDE, 0.0);
                    double selectedLongitude = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0);

                    wasteAddress = address;
                    wasteLat = selectedLatitude;
                    wasteLng = selectedLongitude;

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
