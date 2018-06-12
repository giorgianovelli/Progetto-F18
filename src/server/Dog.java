package server;

public class Dog {
    private String name;
    private String breed;
    private DogSize size;
    private int age;
    private double weight;
    private int ID;
    private boolean isEnabled;

    public Dog(String name, String breed, DogSize size, int age, double weight, int ID, boolean isEnabled) {
        this.name = name;
        this.breed = breed;
        this.size = size;
        this.age = age;
        this.weight = weight;
        this.ID = ID;
        this.isEnabled = isEnabled;
    }

    public boolean setAge(int age) {
        if (age > 0){
            this.age = age;
            return true;
        } else {
            return false;
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setSize(DogSize size) {
        this.size = size;
    }

    public boolean setWeight(int weight) {
        if(weight > 0){
            this.weight = weight;
            return true;
        } else {
            return false;
        }
    }


    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public DogSize getSize() {
        return size;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public int getID() {
        return ID;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
