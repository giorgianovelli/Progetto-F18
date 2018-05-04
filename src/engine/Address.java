package engine;

public class Address {
    private String country;
    private String city;
    private String street;
    private String number;
    private String cap;

    public Address(String country, String city, String street, String number, String cap) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.cap = cap;
    }

    public String getCity() {
        return city;
    }

    public String toString(){
        return street + ", " + number + "; " + cap + " " + city + ", " + country;
    }
}
