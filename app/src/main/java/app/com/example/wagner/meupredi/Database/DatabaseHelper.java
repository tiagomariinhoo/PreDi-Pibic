package app.com.example.wagner.meupredi.Database;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class DatabaseHelper {

    static boolean succeeded;

    static OnSuccessListener<Void> success = new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            succeeded = true;
        }
    };

    static OnFailureListener failure = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            succeeded = false;
        }
    };
}
