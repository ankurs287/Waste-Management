package com.urban.wastemanagement.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.urban.wastemanagement.databinding.FragmentHomeBinding;
import com.urban.wastemanagement.entity.Report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeAdapter homeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.rvReported.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ArrayList<Report> reports = new ArrayList<>();
        ArrayList<File> wasteImages = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reports").whereEqualTo("reportedBy",
                FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                        wasteImages.add(null);
                    }

                    for (int index = 0; index < queryDocumentSnapshots.getDocuments().size();
                            index++) {
                        final Report report = queryDocumentSnapshots.getDocuments()
                                .get(index).toObject(Report.class);
                        reports.add(report);

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        File localFile = null;
                        try {
                            localFile = File.createTempFile(report.getReportId(), "jpg");
                        } catch (IOException ignored) {
                        }
                        File finalLocalFile = localFile;
                        int finalIndex = index;
                        storageRef.child("report_images/" + report.getReportId() + ".jpg")
                                .getFile(localFile).addOnSuccessListener(
                                new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(
                                            FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        wasteImages.set(finalIndex, finalLocalFile);
                                        homeAdapter.notifyDataSetChanged();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });

                    }

                    homeAdapter = new HomeAdapter(reports, wasteImages);

                    binding.rvReported.setAdapter(homeAdapter);

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("xz", e.toString());
                    }
                });

        return binding.getRoot();
    }
}
