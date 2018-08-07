package app.com.example.wagner.meupredi.Database;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

public class PacienteDAO {

    private static String status;

    private static long last_id = 0;

    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("paciente");

    private static OnSuccessListener<Void> success = new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            last_id++;
            status = "success";
        }
    };

    private static OnFailureListener failure = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            status = "fail";
        }
    };

    public static String savePaciente(Paciente paciente){

        DataSnapshot snapshot;
        myRef.child(paciente.getEmail())
            .setValue(paciente)
            .addOnSuccessListener(success)
            .addOnFailureListener(failure);

        return status;
    }

    public static Paciente getPaciente(String email){

        final Paciente[] paciente = new Paciente[1];

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return paciente[0];
    }



}
