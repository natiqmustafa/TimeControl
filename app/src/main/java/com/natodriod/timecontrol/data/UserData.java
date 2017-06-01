package com.natodriod.timecontrol.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.natodriod.timecontrol.common.DATE;
import com.natodriod.timecontrol.config.FirebaseConfig;
import com.natodriod.timecontrol.model.User;

/**
 * Created by natiqmustafa on 06.04.2017.
 */

public class UserData {
    private static final String TAG = "UserData";
    private DatabaseReference mRootRef;

    public UserData() {
        mRootRef = FirebaseDatabase.getInstance().getReference().child(FirebaseConfig.FB_USERS);
    }

    public void addNewUser(final User user) {


        Log.d(TAG, "addNewUser: " + user.getFbUUID());
        mRootRef.orderByChild("fbUUID").equalTo(user.getFbUUID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChildren()){
                    mRootRef.push().setValue(user.getHashMap());
                } else {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User temp = snapshot.getValue(User.class);
                        Log.d(TAG, "onDataChange: " + temp.toString() + " key = " + snapshot.getRef().getKey());
                        temp.setLastLogin(DATE.now());
                        mRootRef.child(snapshot.getKey()).updateChildren(temp.getHashMap());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "cancel ");
            }
        });


    }
}
