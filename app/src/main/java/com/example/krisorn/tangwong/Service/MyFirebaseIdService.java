package com.example.krisorn.tangwong.Service;

import com.example.krisorn.tangwong.MyFirebaseInstanceIDService;
import com.google.firebase.iid.FirebaseInstanceId;

public class MyFirebaseIdService extends MyFirebaseInstanceIDService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Common.currentToken = refreshedToken;
    }
}
