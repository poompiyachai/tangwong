package com.example.krisorn.tangwong.Service;

import com.example.krisorn.tangwong.Remote.APIService;
import com.example.krisorn.tangwong.Remote.RetrofitClient;

public class Common {
    public static  String currentToken ="";

    private static String baseUrl = "https://fcm.googleapis.com/";

    public  static APIService getFCMClient()
    {
        return RetrofitClient.getClient (baseUrl).create(APIService.class);
    }
}
