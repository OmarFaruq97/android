package com.org.isdb62.medicinecrud.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.org.isdb62.medicinecrud.R;
import com.org.isdb62.medicinecrud.adapter.MedicineAdapter;
import com.org.isdb62.medicinecrud.model.Medicine;
import com.org.isdb62.medicinecrud.service.ApiService;
import com.org.isdb62.medicinecrud.util.ApiClient;
import com.org.isdb62.medicinecrud.util.MedicineDiffCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineListActivity extends AppCompatActivity {

    private static final String TAG = "MedicineListActivity";

    private RecyclerView recyclerview;
    private MedicineAdapter medicineAdapter;
    private final List<Medicine> medicineList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medicine_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Enable the Up button (back arrow) in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        medicineAdapter = new MedicineAdapter(this, medicineList);
        recyclerview = findViewById(R.id.medicineRecyclerVIew);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(medicineAdapter);


        fetchMedicine();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void fetchMedicine() {
        ApiService apiService = ApiClient.getApiService();
        Call<List<Medicine>> call = apiService.getAllMedicine();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<Medicine>> call, @NonNull Response<List<Medicine>> response) {
                if (response.isSuccessful()) {
                    List<Medicine> medicines = response.body();
                    assert medicines != null;
                    for (Medicine med : medicines) {
                        Log.d(TAG, "ID: " + med.getId() + ",Name: "
                                + med.getMedicineName() + ", Generic: " + med.getType());
                    }

                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MedicineDiffCallback(medicineList, medicines));
                    medicineList.clear();
                    medicineList.addAll(medicines);
                    result.dispatchUpdatesTo(medicineAdapter);
                } else {
                    Log.e(TAG, "API Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Medicine>> call, @NonNull Throwable t) {
                Log.e(TAG, "API Call Failed: " + t.getMessage());
            }
        });
    }
}