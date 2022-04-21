package com.geohot.aplikasiarisan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private AnggotaModel anggotaModel;
    private EditText et_nama, et_alamat,et_nohp;
    private Button button_simpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        anggotaModel=new AnggotaModel();
//        Log.e("TAG", "onCreate: "+getIntent().getStringExtra("body"));
        TextView judul=findViewById(R.id.judul);
        judul.setText(getIntent().getStringExtra("title"));
        TextView message=findViewById(R.id.message);
        message.setText(getIntent().getStringExtra("body"));

        readData();
        et_nama=findViewById(R.id.et_nama);
        et_alamat=findViewById(R.id.et_alamat);
        et_nohp=findViewById(R.id.et_nohp);
        button_simpan=findViewById(R.id.button_simpan);

    }

    private void readData() {
        databaseReference.child("AnggotaArisan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("TAG", "onCancelled: "+snapshot.getValue() );
                if (snapshot.getValue()==null){
                    button_simpan.setOnClickListener(view -> {
                        String id = databaseReference.push().getKey();
                        String nama = et_nama.getText().toString();
                        String alamat = et_alamat.getText().toString();
                        String nohp = et_nohp.getText().toString();
                        writeData(id,nama,alamat,nohp);

                    });
                }else {

                    for (DataSnapshot anggotaSnapshot : snapshot.getChildren()) {
                        String namaa = anggotaSnapshot.child("nohp").getValue(String.class);
                        button_simpan.setOnClickListener(view -> {
                            String id = databaseReference.push().getKey();
                            String nama = et_nama.getText().toString();
                            String alamat = et_alamat.getText().toString();
                            String nohp = et_nohp.getText().toString();
                            if (namaa.equalsIgnoreCase(nohp)) {
                                Toast.makeText(MainActivity.this, "Anggota sudah terdaftar!", Toast.LENGTH_SHORT).show();
                            } else {

                                writeData(id, nama, alamat, nohp);
                            }

                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: "+error.getMessage() );
            }
        });
    }

    private void writeData(String id, String nama, String alamat, String nohp) {
        anggotaModel.setId(id);
        anggotaModel.setNama(nama);
        anggotaModel.setAlamat(alamat);
        anggotaModel.setNohp(nohp);
        databaseReference.child("AnggotaArisan").push().setValue(anggotaModel).addOnSuccessListener(this,new OnSuccessListener<Void>(){

            @Override
            public void onSuccess(Void unused) {
               et_nama.setText("");
               et_alamat.setText("");
               et_nohp.setText("");
                Toast.makeText(MainActivity.this, "Anggota berhasil ditambahkan.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}