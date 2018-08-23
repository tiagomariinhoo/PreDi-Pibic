/*
package app.com.example.wagner.meupredi.Database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseReadHelper<T extends Object> {
    //talvez englobar a chamada das activities no listener funcione
    DatabaseReference myRef;
    Class<T> type;

    FirebaseReadHelper(DatabaseReference myRef, Class<T> type){
        this.myRef = myRef;
        this.type = type;
    }

    public void readSingleData(FirebaseCallback firebaseCallback){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                T obj;
                obj = dataSnapshot.getValue(type);
                firebaseCallback.onCallback(obj);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });
    }

    public void readMultipleData(FirebaseCallback firebaseCallback){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> list = new ArrayList<>();
                for(DataSnapshot paciente : dataSnapshot.getChildren()){
                    list.add(paciente.getValue(type));
                }
                Log.d("sizeDAO", Integer.toString(list.size()));
                list.forEach(paciente -> {
                    Log.d("NameDAO: ", paciente.toString());
                });
                firebaseCallback.onCallback(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });
    }
}
*/
