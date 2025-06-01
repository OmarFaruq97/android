package com.org.isdb62.medicinecrud.service;

import com.org.isdb62.medicinecrud.model.Medicine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // Correct endpoint for saving medicine
    @POST("medicine/add")
    Call<Medicine> saveMedicine(@Body Medicine medicine);

    // Correct endpoint for fetching all medicines
    @GET("medicine/all")
    Call<List<Medicine>> getAllMedicine();

    // Correct endpoint for updating medicine
    @PUT("medicine/update/{id}")
    Call<Medicine> updateMedicine(@Path("id") int id, @Body Medicine medicine);

    // Correct endpoint for deleting medicine
    @DELETE("medicine/delete/{id}")
    Call<Void> deleteMedicine(@Path("id") int id);
}
