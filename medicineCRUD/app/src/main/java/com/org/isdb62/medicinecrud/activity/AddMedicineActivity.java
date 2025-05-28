package com.org.isdb62.medicinecrud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.org.isdb62.medicinecrud.R;
import com.org.isdb62.medicinecrud.model.Medicine;
import com.org.isdb62.medicinecrud.service.ApiService;
import com.org.isdb62.medicinecrud.util.ApiClient;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMedicineActivity extends AppCompatActivity {

    private EditText textMedicineName, textGeneric, textType, numberQuantity, decimalPrice;

    private Button btnSave;

    private ApiService apiService = ApiClient.getApiService();
    private boolean isEditMode = false;
    private int medicineId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_medicine);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        textMedicineName = findViewById(R.id.textMedicineName);
        textGeneric = findViewById(R.id.textGeneric);
        textType = findViewById(R.id.textType);
        numberQuantity = findViewById(R.id.numberQuantity);
        decimalPrice = findViewById(R.id.decimalPrice);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        if (getIntent().hasExtra("medicine")) {
            Medicine medicine = new Gson()
                    .fromJson(intent.getStringExtra("medicine"),
                            Medicine.class);
            medicineId = medicine.getId();

            textMedicineName.setText(medicine.getMedicineName());
            textGeneric.setText(medicine.getGeneric());
            textType.setText(medicine.getType());
//            numberQuantity.setText(medicine.getQuantity());
            numberQuantity.setText(String.valueOf(medicine.getQuantity()));
            decimalPrice.setText(String.valueOf(medicine.getPrice()));

            btnSave.setText(R.string.update);
            isEditMode = true;
        }

        btnSave.setOnClickListener(v -> saveOrUpdateMedicine());

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void saveOrUpdateMedicine() {
        String name = textMedicineName.getText().toString().trim();
        String generic = textGeneric.getText().toString().trim();
        String type = textType.getText().toString().trim();
        int quantity = Integer.parseInt(numberQuantity.getText().toString().trim());
        BigDecimal price = new BigDecimal(decimalPrice.getText().toString().trim());

        Medicine medicine = new Medicine();
        if (isEditMode) {
            medicine.setId(medicineId);
        }

        medicine.setMedicineName(name);
        medicine.setGeneric(generic);
        medicine.setType(type);
        medicine.setQuantity(quantity);
        medicine.setPrice(price);

        Call<Medicine> call;
        if (isEditMode) {
            call = apiService.updateMedicine(medicineId, medicine);

        } else {
            call = apiService.saveMedicine(medicine);
        }

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Medicine> call, Response<Medicine> response) {
                if (response.isSuccessful()) {
                    String message;
                    if (isEditMode)
                        message = "Medicine Updated Successfully!";
                    else
                        message = "Medicine Saved Successfully!";

                    Toast.makeText(AddMedicineActivity.this, message, Toast.LENGTH_SHORT).show();
                    clearForm();
                    Intent intent = new Intent(AddMedicineActivity.this, MedicineListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddMedicineActivity.this, "Failed to save Medicine "
                            + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Medicine> call, Throwable t) {
                Toast.makeText(AddMedicineActivity.this, "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        textMedicineName.setText("");
        textGeneric.setText("");
        textType.setText("");
        numberQuantity.setText("");
        decimalPrice.setText("");
    }
}