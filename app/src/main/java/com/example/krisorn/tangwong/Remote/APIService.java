package com.example.krisorn.tangwong.Remote;

import retrofit2.Call;

import com.example.krisorn.tangwong.Model.MyResponse;
import com.example.krisorn.tangwong.Model.Sender;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers ({
            "Content-Type:application/json",
            "Authorization:key=AAAAkKyvaok:APA91bEOdUWE-jw5jaJvxKS3NS6iV_ANv1TbNio89YZtXRqFijcOyh_PAR1LgUdmeqvk2Ktm6ZvET3VmpZpBbnWEwHOP1ZgMNSoGGRngUkR7PsHUdiiSC3dCQWDHRKFZ3i39-Y0-i-8v"
    })
    @POST("fcm/send")
   Call<MyResponse> sendNotification(@Body Sender body);
}
