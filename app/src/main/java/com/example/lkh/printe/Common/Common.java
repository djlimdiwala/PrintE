package com.example.lkh.printe.Common;

import com.example.lkh.printe.Remote.APIService;
import com.example.lkh.printe.Remote.RetrofitClient;

/**
 * Created by DJL on 4/10/2018.
 */

public class Common {


    private static final String BASE_URL="https://fcm.googleapis.com/";


    public static APIService getFCMService()
    {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
