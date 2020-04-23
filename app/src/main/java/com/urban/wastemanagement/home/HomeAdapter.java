package com.urban.wastemanagement.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.urban.wastemanagement.databinding.ItemReportBinding;
import com.urban.wastemanagement.entity.Report;

import java.io.File;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    ArrayList<Report> reports;
    private LayoutInflater layoutInflater;
    ArrayList<File> wasteImages;

    public HomeAdapter(ArrayList<Report> reports, ArrayList<File> wasteImages) {
        this.reports = reports;
        this.wasteImages = wasteImages;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new HomeViewHolder(ItemReportBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }


    class HomeViewHolder extends RecyclerView.ViewHolder {

        private ItemReportBinding binding;

        HomeViewHolder(ItemReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            binding.tvAddress.setText(reports.get(position).getAddress());
            binding.tvResolved.setText(
                    HomeAdapter.this.reports.get(position).isResolved() ?
                            "Resolved" :
                            "Pending");

            if (wasteImages.get(position) != null) {
                Bitmap myBitmap = BitmapFactory.decodeFile(
                        wasteImages.get(position).getAbsolutePath());
                binding.imvWasteImage.setImageBitmap(myBitmap);
            }
        }
    }
}
