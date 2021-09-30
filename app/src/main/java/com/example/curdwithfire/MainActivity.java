package com.example.curdwithfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
    EditText editText,updateName;
    Button button,updatebtn;
    Spinner spinner;
    Context context;
    DatabaseReference databaseReference;
    ListView listView;
    List<Artist> artistList;
//    TextView upName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.etArtistName);
        button=findViewById(R.id.btnSubmit);
        spinner=findViewById(R.id.spinnerGenres);
        listView =findViewById(R.id.ArtistList);
        updateName=findViewById(R.id.editTextName);



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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Artist artist=artistList.get(i);

                showUpdateDialog(artist.getArtistID(),artist.getArtistName());



                return false;
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

    private void showUpdateDialog(String Id,String Name){

        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialodView=inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialodView);

        final EditText editText=(EditText)dialodView.findViewById(R.id.editTextName);
        final Button button=(Button)dialodView.findViewById(R.id.btnupdate);
        final Spinner spinner=(Spinner)dialodView.findViewById(R.id.spinnerupdate);
        final Button button1=(Button)dialodView.findViewById(R.id.btnDelete);

        dialogBuilder.setTitle("Update Artist "+Name);

        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editText.getText().toString().trim();
                String genre=spinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();

                }else{
                    UpdateArtist(Id,name,genre);
                    alertDialog.dismiss();
                }



            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleleteArtist(Id);
                alertDialog.dismiss();
            }
        });



    }

    private void deleleteArtist(String id) {

        DatabaseReference databaseReferenceArtist=FirebaseDatabase.getInstance().getReference("artist").child(id);
        DatabaseReference databaseReferenceTracks=FirebaseDatabase.getInstance().getReference("tracks").child(id);

        databaseReferenceArtist.removeValue();
        databaseReferenceTracks.removeValue();

        Toast.makeText(context, "Atrist is deleted", Toast.LENGTH_SHORT).show();


    }

    private boolean UpdateArtist(String id,String name,String genre){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("artist").child(id);

        Artist artist=new Artist(id,name,genre);

        databaseReference.setValue(artist);
        Toast.makeText(context, "Artist Updated", Toast.LENGTH_SHORT).show();
        return true;
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