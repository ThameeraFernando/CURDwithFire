package com.example.curdwithfire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTracks extends AppCompatActivity {

    TextView arName;
    EditText ArTrackName;
    SeekBar seekBar;
    ListView listView;
    Button addtracks;
    Context context;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracks);

        ArTrackName=findViewById(R.id.etArtistsTrackName);
        arName=findViewById(R.id.ArtistName2);
        seekBar=findViewById(R.id.seekBar);
        listView=findViewById(R.id.TrackList);
        addtracks=findViewById(R.id.btnSubmitTrack);
        context=this;



        Intent intent=getIntent();
       String name= intent.getStringExtra(MainActivity.ARTIST_NAME);
        String id=intent.getStringExtra(MainActivity.ARTIST_ID);

        arName.setText(name);
        databaseReference= FirebaseDatabase.getInstance().getReference("tracks").child(id);

        addtracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTracks();
            }
        });

    }

    private void saveTracks() {
            String TrackName=ArTrackName.getText().toString().trim();
            int rating=seekBar.getProgress();

            if (!TextUtils.isEmpty(TrackName)){

                String id=databaseReference.push().getKey();
                Track track=new Track(id,TrackName,rating);
                databaseReference.child(id).setValue(track);
                Toast.makeText(context, "Track added successfully", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(context, "please enter track name", Toast.LENGTH_SHORT).show();
            }

    }
}