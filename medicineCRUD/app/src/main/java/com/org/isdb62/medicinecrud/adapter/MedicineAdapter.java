package com.org.isdb62.medicinecrud.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.org.isdb62.medicinecrud.R;
import com.org.isdb62.medicinecrud.activity.AddMedicineActivity;
import com.org.isdb62.medicinecrud.model.Medicine;
import com.org.isdb62.medicinecrud.service.ApiService;
import com.org.isdb62.medicinecrud.util.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private Context context;
    private List<Medicine> medicineList;
    private ApiService apiService;

    public MedicineAdapter(Context context, List<Medicine> medicineList) {
        this.context = context;
        this.medicineList = medicineList;
        this.apiService = ApiClient.getApiService();
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.medicineNameText.setText(medicine.getMedicineName());
        holder.genericText.setText(medicine.getGeneric());
        holder.typeText.setText(medicine.getType());
        holder.quantityNumber.setText(String.valueOf(medicine.getQuantity()));
        holder.priceDecimal.setText(String.valueOf(medicine.getPrice()));

        holder.updateButton.setOnClickListener(v -> {
            Log.d("Update", "Update clicked for " + medicine.getMedicineName());
            Intent intent = new Intent(context, AddMedicineActivity.class);
            intent.putExtra("medicine", new Gson().toJson(medicine));
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            Log.d("Delete", "Delete clicked for " + medicine.getMedicineName());
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete " + medicine.getMedicineName() + "?")
                    .setPositiveButton("Yes",
                            (dialog, which) -> apiService.deleteMedicine(medicine.getId())
                                    .enqueue(new Callback<>() {
                                        @Override
                                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                            if (response.isSuccessful()) {
                                                int adapterPosition = holder.getAdapterPosition();
                                                if (adapterPosition != RecyclerView.NO_POSITION) {
                                                    medicineList.remove(adapterPosition);
                                                    notifyItemRemoved(adapterPosition);
                                                    notifyItemRangeChanged(adapterPosition, medicineList.size());
                                                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }))
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }


    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView medicineNameText, genericText, quantityNumber, priceDecimal, typeText;
        Button updateButton, deleteButton;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineNameText = itemView.findViewById(R.id.textMedicineName);
            genericText = itemView.findViewById(R.id.textGeneric);
            typeText = itemView.findViewById(R.id.textType);
            quantityNumber = itemView.findViewById(R.id.numberQuantity);
            priceDecimal = itemView.findViewById(R.id.decimalPrice);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
