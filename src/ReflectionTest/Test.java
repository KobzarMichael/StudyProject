package ReflectionTest;

public class Test {

    private int id;
    private String name;

    public Test(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public Test() {
        this.id = 0;
        this.name = "name";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void testMethod () {
        System.out.println("Test successful");
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
