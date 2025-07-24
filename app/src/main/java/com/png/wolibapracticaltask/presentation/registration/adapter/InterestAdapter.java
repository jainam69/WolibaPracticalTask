package com.png.wolibapracticaltask.presentation.registration.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.png.wolibapracticaltask.R;
import com.png.wolibapracticaltask.data.model.Interest;
import com.png.wolibapracticaltask.databinding.ItemInterestBinding;

import java.util.ArrayList;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {

    private final ArrayList<Interest> interestTypeList;

    public InterestAdapter(ArrayList<Interest> interestTypeList) {
        this.interestTypeList = interestTypeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_interest, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setInterest(interestTypeList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.getRoot().setSelected(interestTypeList.get(position).isSelected());
        holder.binding.tvInterestName.setSelected(interestTypeList.get(position).isSelected());

        holder.binding.getRoot().setOnClickListener(v -> {
            interestTypeList.get(position).setSelected(!interestTypeList.get(position).isSelected());
            notifyItemChanged(position);
        });

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return interestTypeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemInterestBinding binding;

        public ViewHolder(@NonNull ItemInterestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
