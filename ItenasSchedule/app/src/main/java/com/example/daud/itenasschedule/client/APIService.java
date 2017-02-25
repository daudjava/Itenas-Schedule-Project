package com.example.daud.itenasschedule.client;

//import com.ptdam.gcmtofcm.client.response.RegisterResponse;


import com.example.daud.itenasschedule.client.response.GetNamaResponse;
import com.example.daud.itenasschedule.client.response.RegisterResponse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Debam on 7/29/2016.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("http://kpta.itenas.ac.id/gcmtofcm/v1/login")
    public Call<RegisterResponse> registermember      (@Field("NIP") String msisdn,
                                                       @Field("token") String gcmtoken);

    @FormUrlEncoded
    @POST("http://kpta.itenas.ac.id/gcmtofcm/v1/requestOTPNew")
    public Call<RegisterResponse> requestOTP          (@Field("NIP") String msisdn,
                                                       @Field("nama") String nama);

    @FormUrlEncoded
    @POST("http://kpta.itenas.ac.id/gcmtofcm/v1/nama")
    public Call<GetNamaResponse> requestNama          (@Field("NIP") String msisdn);

}

