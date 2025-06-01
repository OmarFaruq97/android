package com.org.isdb62.medicinecrud;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.org.isdb62.medicinecrud.activity.AddMedicineActivity;
import com.org.isdb62.medicinecrud.activity.MedicineListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAddMedicine = findViewById(R.id.btnAddMedicine);
        Button btnListMedicine = findViewById(R.id.btnListMedicine);

        btnAddMedicine.setOnClickListener(v -> navigateToAddMedicinePage());
        btnListMedicine.setOnClickListener(v -> navigateToMedicineListPage());
    }

    private void navigateToAddMedicinePage() {
        Intent intent = new Intent(MainActivity.this, AddMedicineActivity.class);
        startActivity(intent);
    }

    private void navigateToMedicineListPage() {
        Intent intent = new Intent(MainActivity.this, MedicineListActivity.class);
        startActivity(intent);
    }
}