package server.places;

import java.util.HashSet;

/**
 * This class shows the work area of the dog sitter.
 */
public class Area {

    /**
     * The HashSet of place where a dog sitter can work.
     */
    HashSet<String> places;



    /**
     * Creare a new Area object.
     */
    public Area() {
        this.places = new HashSet<>();
    }


    /**
     * Add a new city where a dog sitter can work.
     * @param city
     * @return
     */
    public HashSet<String> addPlace(String city){
        places.add(city);
        return places;
    }


    /**
     * Remove a city where the dog sitter will not work longer.
     * @param city
     * @return
     */
    public HashSet<String>removePlaces(String city){
        if (places.contains(city)){
            places.remove(city);
            return places;
        } else {
            return null;
        }
    }


    /**
     * Check if Area object already contains the specified city.
     * @param place the city to check.
     * @return true if the city is already contained in Area.
     */
    public boolean contains(String place){
        if (places.contains(place)){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Print the list of place where a dog sitter can work.
     */
    public void printPlaces(){
        for (String p : places) {
            System.out.println(p);
        }
    }


    /**
     * Get the HashSet of cities where a dog sitters can work.
     * @return the HashSet of cities where a dog sitters can work.
     */
    public HashSet<String> getPlaces() {
        return places;
    }
}
