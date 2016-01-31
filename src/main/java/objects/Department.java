package objects;
//Created by mva on 28.01.2016.

public class Department extends Model{


    private String name;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }



    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
