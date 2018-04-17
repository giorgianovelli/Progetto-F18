public class Dog {
    private String name;
    private String breed;
    private String size;
    private int age;
    private int weight;
    private String ownerName;
    private String ownerSurname;
    private int ID;

    public Dog(String name, String breed, String size, int age, int weight, String ownerName, String ownerSurname, int ID) {
        this.name = name;
        this.breed = breed;
        this.size = size;
        this.age = age;
        this.weight = weight;
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.ID = ID;
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

    public void setSize(String size) {
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

    public String getSize() {
        return size;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public int getID() {
        return ID;
    }}
