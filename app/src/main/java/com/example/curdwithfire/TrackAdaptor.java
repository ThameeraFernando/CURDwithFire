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

public class TrackAdaptor extends ArrayAdapter<Track> {

    private Activity context;
    private List<Track>  tracks;

    public TrackAdaptor(Activity context,List<Track>  tracks){
        super(context,R.layout.singletrack,tracks);
        this.context=context;
        this.tracks=tracks;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.singletrack,null,true);
        TextView textViewTrack=(TextView) listViewItem.findViewById(R.id.tvTrackName);
        TextView textViewRating=(TextView) listViewItem.findViewById(R.id.tvTrackrating);

        Track track=tracks.get(position);
        textViewTrack.setText(track.getTrackName());
        textViewRating.setText(String.valueOf(track.getTrackRatings()));

        return listViewItem;

    }
}
