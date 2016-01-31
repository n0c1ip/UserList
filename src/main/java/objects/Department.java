package objects;
//Created by mva on 28.01.2016.

public class Department {

    private int id;
    private String name;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
