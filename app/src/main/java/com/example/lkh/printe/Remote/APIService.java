package com.example.lkh.printe.Remote;

import com.example.lkh.printe.MyResponse;
import com.example.lkh.printe.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by DJL on 4/10/2018.
 */

public interface APIService {

    @Headers(

            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAgKndXU4:APA91bHY6wwOhU-0fbAuVThCvuAV4b-DbVfwiEev__mgcgfW4YRdMZpNYrpszCCx6GgzJQODKfb21_mExCRHyMNUG8T8PI2OH0eerHPwDdEK9kJLL8lRX6UuT62I6HfkSjUrnBSY0uJu"

            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
