package com.png.wolibapracticaltask.presentation.registration.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.png.wolibapracticaltask.R;
import com.png.wolibapracticaltask.databinding.ItemInterestBinding;
import com.png.wolibapracticaltask.domain.model.Interest;

import java.util.ArrayList;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {

    private final ArrayList<Interest> interestTypeList;
    private final InterestListener listener;
    private final Context context;

    public InterestAdapter(ArrayList<Interest> interestTypeList, InterestListener listener, Context context) {
        this.interestTypeList = interestTypeList;
        this.listener = listener;
        this.context = context;
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

        if (interestTypeList.get(position).isSelected()) {
            Glide.with(holder.itemView.getContext()).load(interestTypeList.get(position).getInterest_white_icon()).into(holder.binding.ivIcon);
        } else {
            Glide.with(holder.itemView.getContext()).load(interestTypeList.get(position).getInterest_color_icon()).into(holder.binding.ivIcon);
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            interestTypeList.get(position).setSelected(!interestTypeList.get(position).isSelected());
            getSelectedItems();
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

    public void getSelectedItems() {
        ArrayList<Interest> selected = new ArrayList<>();
        for (Interest item : interestTypeList) {
            if (item.isSelected()) selected.add(item);
        }
        listener.onItemClick(selected);
    }

    public interface InterestListener {
        void onItemClick(ArrayList<Interest> interestsItems);
    }
}
