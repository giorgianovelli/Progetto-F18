package server.places;

public class Address {

    /**
     * The country.
     */
    private String country;

    /**
     * The city.
     */
    private String city;

    /**
     * The street.
     */
    private String street;

    /**
     * The civic number.
     */
    private String number;

    /**
     * The cap of the city.
     */
    private String cap;


    /**
     * Create a new Address object.
     * @param country the country.
     * @param city the city.
     * @param street the street.
     * @param number the civic number.
     * @param cap the cap of the city.
     */
    public Address(String country, String city, String street, String number, String cap) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.cap = cap;
    }


    /**
     * Get the city.
     * @return the city.
     */
    public String getCity() {
        return city;
    }


    /**
     * Get the country.
     * @return the country.
     */
    public String getCountry() {
        return country;
    }


    /**
     * Get the street.
     * @return the street.
     */
    public String getStreet() {
        return street;
    }


    /**
     * Get the civic number.
     * @return the civic number.
     */
    public String getNumber() {
        return number;
    }


    /**
     * Get the cap of the city.
     * @return the cap of the city.
     */
    public String getCap() {
        return cap;
    }


    /**
     * Create a string with all informations about the address.
     * @return a string with all informations about the address.
     */
    public String toString(){
        return street + ", " + number + "; " + cap + " " + city + ", " + country;
    }
}
