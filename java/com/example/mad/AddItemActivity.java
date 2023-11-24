package com.example.mad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddItemActivity extends AppCompatActivity {

    //firebase object
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodi-5f9d1-default-rtdb.firebaseio.com/");
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private String sessionUNText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);

        final EditText foodname = findViewById(R.id.foodname);
        final EditText foodprice = findViewById(R.id.foodprice);
        final EditText foodweight = findViewById(R.id.foodwieght);

        final Button btnadd = findViewById(R.id.btnadd);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String foodnametxt = foodname.getText().toString();
                final String foodpricetxt = foodprice.getText().toString();
                final String foodweighttxt = foodweight.getText().toString();
                loadData();

                boolean isCheck = foodnametxt.isEmpty() || foodpricetxt.isEmpty() || foodweighttxt.isEmpty();

                if(isCheck){
                    Toast.makeText(AddItemActivity.this , "Please Fill All The Details" , Toast.LENGTH_SHORT).show();
                }
                else {
                    dbref.child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            dbref.child("Food").child(sessionUNText).child(foodnametxt).child("FoodName").setValue(foodnametxt);
                            dbref.child("Food").child(sessionUNText).child(foodnametxt).child("FoodPrice").setValue(foodpricetxt);
                            dbref.child("Food").child(sessionUNText).child(foodnametxt).child("FoodWeight").setValue(foodweighttxt);

                            Toast.makeText(AddItemActivity.this , "New Address Added Successfully" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddItemActivity.this , RestaurantDashboardActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    public void loadData(){
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS , MODE_PRIVATE);
        sessionUNText = sp.getString(TEXT , "");
    }
}
