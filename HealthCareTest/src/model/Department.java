package model;

public class Department {
    private int depID;
    private String name;

    public Department(int depID, String name) {
        this.depID = depID;
        this.name = name;
    }

    public int getDepID() { return depID; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Department{" +
               "depID=" + depID +
               ", name='" + name + '\'' +
               '}';
    }
}
