package Proto.dijkstra;

import Proto.domain.Project;

public class PNode{

    private Integer id; 
    private Integer capacity;
    private Project project;

    public PNode(Integer id, Integer capacity) {
       this.id = id; 
       this.capacity = capacity;
    }   

    public Integer getId() { return id; }
    public Integer getCapacity() { return capacity; }
    public String toString() {return id.toString(); }
    
    public Project getProject(){
        return project;
    }
    
    public void setProject(Project project){
        this.project = project;
    }
}

