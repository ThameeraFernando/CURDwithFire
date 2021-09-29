package com.example.curdwithfire;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ArtistAdaptor extends ArrayAdapter<Artist> {

    private Activity context;
    private List<Artist> artistList;

    public ArtistAdaptor(Activity context,List<Artist> artistList){
        super(context,R.layout.single_artist,artistList);
        this.context=context;
        this.artistList=artistList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.single_artist,null,true);
        TextView textViewName=(TextView) listViewItem.findViewById(R.id.tvArtistname);
        TextView textViewGenre=(TextView) listViewItem.findViewById(R.id.tvArtistGer);

        Artist artist=artistList.get(position);
        textViewName.setText(artist.getArtistName());
        textViewGenre.setText(artist.getArtistGenre());

        return listViewItem;
    }
}
