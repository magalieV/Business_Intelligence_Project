package BI.Totally_Spies.database;

public class Interest {
    public String className;
    public String name;

    public Interest(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getClassName() {return className;}
    public String getName() {return name;}

    public void setClassName(String className) {this.className = className;}

    public void setName(String name) {this.name = name;}
}
