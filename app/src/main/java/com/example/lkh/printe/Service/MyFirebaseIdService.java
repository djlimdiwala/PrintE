package com.example.lkh.printe.Service;

import com.example.lkh.printe.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by DJL on 4/10/2018.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {

    private FirebaseAuth firebaseAuth;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        firebaseAuth = FirebaseAuth.getInstance();
        String tokenRefreshed= FirebaseInstanceId.getInstance().getToken();
        if(firebaseAuth.getCurrentUser()!=null)
            updateTokenToFirebase(tokenRefreshed);
    }

    private void updateTokenToFirebase(String tokenRefreshed) {

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference tokens=db.getReference("Tokens");
        Token token=new Token(tokenRefreshed,false);//false bcauz this token is sent from client
        tokens.child(firebaseAuth.getCurrentUser().getUid()).setValue(token);
    }
}
