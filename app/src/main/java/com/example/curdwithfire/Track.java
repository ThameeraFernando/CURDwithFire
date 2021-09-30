package com.example.curdwithfire;

public class Track {
  private  String trackId;
  private String trackName;
  private int trackRatings;

  public Track(){

  }

    public Track(String trackId, String trackName, int trackRatings) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackRatings = trackRatings;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public int getTrackRatings() {
        return trackRatings;
    }

    public void setTrackRatings(int trackRatings) {
        this.trackRatings = trackRatings;
    }
}
