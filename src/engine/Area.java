package engine;

import java.util.HashSet;


public class Area {
    HashSet<String> places;

    public Area( HashSet<String> places) {
        this.places = new HashSet<String>();
    }

    public HashSet<String> addPlaces(String city){
        places.add(city);
        return places;
    }

    public HashSet<String>removePlaces(String city){
        if (places.contains(city)){
            places.remove(city);
            return places;
        } else {
            return null;
        }
    }

}
