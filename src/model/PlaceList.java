package model;

import java.util.ArrayList;

public class PlaceList {
    private ArrayList<Place> places;

    public PlaceList() {
        places = new ArrayList(){};
    }

    public void add(Place place) {
        if (!places.contains(place)) {
            places.add(place);
        }
    }

    public void remove(int i) {
        places.remove(i);
    }

    public Place getPlace(int i) {
        return places.get(i);
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public int size() {
        return places.size();
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }
}
