package com.example.curdwithfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME="artistname";
    public static final String ARTIST_ID="artistid";
    EditText editText;
    Button button;
    Spinner spinner;
    Context context;
    DatabaseReference databaseReference;
    ListView listView;
    List<Artist> artistList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.etArtistName);
        button=findViewById(R.id.btnSubmit);
        spinner=findViewById(R.id.spinnerGenres);
        listView =findViewById(R.id.ArtistList);
        artistList=new ArrayList<>();
        context =this;
        databaseReference= FirebaseDatabase.getInstance().getReference("artist");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist=artistList.get(i);

                Intent intent=new Intent(context,AddTracks.class);
                intent.putExtra(ARTIST_ID,artist.getArtistID());
                intent.putExtra(ARTIST_NAME,artist.getArtistName());

                startActivity(intent);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                artistList.clear();
                for (DataSnapshot artistSnapshot: snapshot.getChildren()){
                    Artist artist=artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);

                }
                ArtistAdaptor artistAdaptor=new ArtistAdaptor(MainActivity.this,artistList);
                listView.setAdapter(artistAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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