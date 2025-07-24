package com.png.wolibapracticaltask.presentation.registration.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.png.wolibapracticaltask.R;
import com.png.wolibapracticaltask.data.model.InterestType;
import com.png.wolibapracticaltask.databinding.ItemInterestTypeBinding;

import java.util.ArrayList;

public class InterestTypeAdapter extends RecyclerView.Adapter<InterestTypeAdapter.ViewHolder> {

    private final ArrayList<InterestType> interestTypeList;

    public InterestTypeAdapter(ArrayList<InterestType> interestTypeList) {
        this.interestTypeList = interestTypeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_interest_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setInterestType(interestTypeList.get(position));
        holder.binding.executePendingBindings();

        // Setup child RecyclerView
        holder.binding.rvChips.setLayoutManager(new FlexboxLayoutManager(holder.itemView.getContext()));
        holder.binding.rvChips.setAdapter(new InterestAdapter(interestTypeList.get(position).getInterests()));

        holder.binding.tvInterestType.setOnClickListener(v -> {
            interestTypeList.get(position).setExpanded(!interestTypeList.get(position).isExpanded());
            notifyItemChanged(position);
        });

        holder.binding.ivArrow.setOnClickListener(v -> {
            interestTypeList.get(position).setExpanded(!interestTypeList.get(position).isExpanded());
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return interestTypeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemInterestTypeBinding binding;

        public ViewHolder(@NonNull ItemInterestTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
