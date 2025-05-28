package com.org.isdb62.medicinecrud.util;

import androidx.recyclerview.widget.DiffUtil;

import com.org.isdb62.medicinecrud.model.Medicine;

import java.util.List;
import java.util.Objects;

public class MedicineDiffCallback extends DiffUtil.Callback{


    private final List<Medicine> oldList;
    private final List<Medicine> newList;

    public MedicineDiffCallback(List<Medicine> oldList, List<Medicine> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // Assuming 'id' uniquely identifies an employee
        return Objects.equals(oldList.get(oldItemPosition).getId(), newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

}
