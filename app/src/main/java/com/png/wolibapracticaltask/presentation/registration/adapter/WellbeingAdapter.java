package com.png.wolibapracticaltask.presentation.registration.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.png.wolibapracticaltask.R;
import com.png.wolibapracticaltask.databinding.ItemWellbeingBinding;
import com.png.wolibapracticaltask.data.model.response.WellbeingItem;

import java.util.ArrayList;

public class WellbeingAdapter extends RecyclerView.Adapter<WellbeingAdapter.ViewHolder> {

    private final ArrayList<WellbeingItem> wellbeingList;
    private final Context context;
    private final WellbeingListener listener;

    public WellbeingAdapter(ArrayList<WellbeingItem> wellbeingList, Context context, WellbeingListener listener) {
        this.wellbeingList = wellbeingList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_wellbeing, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setItem(wellbeingList.get(position));
        holder.binding.setClickListener(v -> {
            if (!wellbeingList.get(position).isSelected()) {
                int selectedCount = getSelectedCount();
                if (selectedCount >= 3) {
                    Toast.makeText(context, "You can select up to 3 items only", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            wellbeingList.get(position).setSelected(!wellbeingList.get(position).isSelected());
            getSelectedItems();
            notifyItemChanged(position);
        });

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return wellbeingList.size();
    }

    private int getSelectedCount() {
        int count = 0;
        for (WellbeingItem item : wellbeingList) {
            if (item.isSelected()) count++;
        }
        return count;
    }

    public void getSelectedItems() {
        ArrayList<WellbeingItem> selected = new ArrayList<>();
        for (WellbeingItem item : wellbeingList) {
            if (item.isSelected()) selected.add(item);
        }
        listener.onItemClick(selected);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemWellbeingBinding binding;

        ViewHolder(ItemWellbeingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface WellbeingListener {
        void onItemClick(ArrayList<WellbeingItem> wellbeingItems);
    }
}

