package com.example.curdwithfire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    Spinner spinner;
    Context context;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.etArtistName);
        button=findViewById(R.id.btnSubmit);
        spinner=findViewById(R.id.spinnerGenres);
        context =this;
        databaseReference= FirebaseDatabase.getInstance().getReference("artist");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });



    }
    private void addArtist(){
        String name=editText.getText().toString().trim();
        String genre=spinner.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){
            String id=databaseReference.push().getKey();

            Artist artist=new Artist(id,name,genre);
            databaseReference.child(id).setValue(artist);
            Toast.makeText(context, "Artist Added", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(context, "Enter a Name", Toast.LENGTH_SHORT).show();
        }
    }
}