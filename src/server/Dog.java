/**
 * The class that include all dog's attributes and methods.
 */

package server;
/**
 * This class provides all the information about a dog.
 */
public class Dog {

    /**
     * The dog's name.
     */
    private String name;

    /**
     * The dog's breed.
     */
    private String breed;

    /**
     * The dog's size.
     */
    private DogSize size;

    /**
     * The dog's age.
     */
    private int age;

    /**
     * The dog's weight.
     */
    private double weight;

    /**
     * The dog's ID.
     */
    private int ID;

    /**
     * The dog's state. True if dogs is alive.
     */
    private boolean isEnabled;



    /**
     * Create a new dog.
     * @param name the dog's name.
     * @param breed the dog's breed.
     * @param size the dog's size.
     * @param age the dog's age.
     * @param weight the dog's weight.
     * @param ID the dog's ID.
     * @param isEnabled true if the dog is alive.
     */
    public Dog(String name, String breed, DogSize size, int age, double weight, int ID, boolean isEnabled) {
        this.name = name;
        this.breed = breed;
        this.size = size;
        this.age = age;
        this.weight = weight;
        this.ID = ID;
        this.isEnabled = isEnabled;
    }


    /**
     * Set the dog's age.
     * @param age the dog's age.
     * @return the dog's age.
     */
    public boolean setAge(int age) {
        if (age > 0){
            this.age = age;
            return true;
        } else {
            return false;
        }

    }


    /**
     * Set the dog's name.
     * @param name the dog's name.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Set the dog's breed.
     * @param breed the dog's breed.
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }


    /**
     * Set the dog's size.
     * @param size the dog's size.
     */
    public void setSize(DogSize size) {
        this.size = size;
    }


    /**
     * Set the dog's weight.
     * @param weight the dog's weight.
     * @return true if the weight is a positive number.
     */
    public boolean setWeight(int weight) {
        if(weight > 0){
            this.weight = weight;
            return true;
        } else {
            return false;
        }
    }


    /**
     * Get the dog's name.
     * @return the dog's name.
     */
    public String getName() {
        return name;
    }


    /**
     * Get the dog's breed.
     * @return the dog's breed.
     */
    public String getBreed() {
        return breed;
    }


    /**
     * Get the dog's size.
     * @return the dog's size.
     */
    public DogSize getSize() {
        return size;
    }


    /**
     * Get the dog's age.
     * @return the dog's age.
     */
    public int getAge() {
        return age;
    }


    /**
     * Get the dog's weight.
     * @return the dog's weight.
     */
    public double getWeight() {
        return weight;
    }


    /**
     * Get the dog's ID.
     * @return the dog's ID.
     */
    public int getID() {
        return ID;
    }


    /**
     * Get the dog's state.
     * @return true if the dog is alive.
     */
    public boolean isEnabled() {
        return isEnabled;
    }
}
