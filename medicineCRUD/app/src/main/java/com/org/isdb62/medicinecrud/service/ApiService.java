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

    @POST("medicine")
    Call<Medicine> saveMedicine(@Body Medicine medicine);

    @GET("medicine")
    Call<List<Medicine>> getAllMedicine();

    @PUT("employee/{id}")
    Call<Medicine> updateMedicine(@Path("id") int id, @Body Medicine medicine);

    @DELETE("medicine/{id}")
    Call<Void> deleteMedicine(@Path("id") int id);
}
