package com.example.iot_aptrung;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView a, b, c, d;
    int count = 0;
    int dem = 0;
    Button batden, tatden, batquat, tatquat, xem;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a = (TextView) findViewById(R.id.tempView);
        b = (TextView) findViewById(R.id.humidityView);
        c = (TextView) findViewById(R.id.denView);
        d = (TextView) findViewById(R.id.quatView);

        xem = (Button) findViewById(R.id.xemView);
        batden = (Button) findViewById(R.id.batdenView);

        batquat = (Button) findViewById(R.id.batquatView);


        xem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference();
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String temp = snapshot.child("Temperature").getValue().toString();
                            String humidity = snapshot.child("Humidity").getValue().toString();
                            String led = snapshot.child("relay").getValue().toString();
                            String fan = snapshot.child("Fan").getValue().toString();
                            a.setText(temp);
                            b.setText(humidity);
                            c.setText(led);
                            d.setText(fan);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        batden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference();
                count+=1;
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            if (count % 2 == 0) {
                                reff.child("relay").setValue("ON");
                                batden.setText("ON");
                                batden.setBackgroundColor(Color.parseColor("#00FF00"));
                            } else if (count %2 !=0){
                                reff.child("relay").setValue("OFF");
                                batden.setText("OFF");
                                batden.setBackgroundColor(Color.parseColor("#FF0000"));
                            }

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        batquat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference();
                dem+=1;
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            if (dem % 2 == 0) {
                                reff.child("Fan").setValue("ON");
                                batquat.setText("ON");
                                batquat.setBackgroundColor(Color.parseColor("#00FF00"));
                            } else if (dem %2 !=0){
                                reff.child("Fan").setValue("OFF");
                                batquat.setText("OFF");
                                batquat.setBackgroundColor(Color.parseColor("#FF0000"));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
//        tatden.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                reff = FirebaseDatabase.getInstance().getReference();
//                reff.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            reff.child("LED").setValue("OFF");
//
//                        }
//
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                    }
//                });
//            }
//        });


    }
}