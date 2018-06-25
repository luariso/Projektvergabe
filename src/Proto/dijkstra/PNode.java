package Proto.dijkstra;

import Proto.domain.Project;

public class PNode{

    private Integer id; 
    private Integer capacity;
    private Project projekt;

    public PNode(Integer id, Integer capacity) {
       this.id = id; 
       this.capacity = capacity;
    }   

    public Integer getId() { return id; }
    public Integer getCapacity() { return capacity; }
    public String toString() {return id.toString(); }
    
    public Project getProjekt(){
        return projekt;
    }
    
    public void setProjekt(Project project){
        this.projekt = project;
    }
}

