package Proto;

public class PNode{

    private Integer id; 
    private Integer capacity;
    private Projekt projekt;

    public PNode(Integer id, Integer capacity) {
       this.id = id; 
       this.capacity = capacity;
    }   

    public Integer getId() { return id; }
    public Integer getCapacity() { return capacity; }
    public String toString() {return id.toString(); }
    
    public Projekt getProjekt(){
        return projekt;
    }
    
    public void setProjekt(Projekt projekt){
        this.projekt = projekt;
    }
}

