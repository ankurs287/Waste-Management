package com.urban.wastemanagement.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.urban.wastemanagement.databinding.ItemReportBinding;
import com.urban.wastemanagement.entity.Report;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    ArrayList<Report> reports = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public HomeAdapter(ArrayList<Report> reports) {
        this.reports = reports;
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

    void setReports(ArrayList<Report> reports) {
        this.reports = reports;
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
        }
    }
}
