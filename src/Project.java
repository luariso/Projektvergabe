public class Project {

    private Integer id;
    private Integer capacity;
    private String name;

    public Project(Integer id, Integer capacity, String name) {
       this.id = id;
       this.capacity = capacity;
       this.name = new String(name);
    }

    public Integer getId() { return id; }
    public Integer getCapacity() { return capacity; }
    public String getName() { return name; }
    public String toString() { return name; }

}
