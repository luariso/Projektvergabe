import java.util.*;

public class Stud {

    protected Integer id;
    protected ArrayList<Integer> satisfactions;   // Individuelle Beurteilung der Projekte


    public Stud(Integer id, ArrayList<Integer> satisfactions) {
       this.id = id;
       this.satisfactions = satisfactions;
    }

    public Integer getId() { return id; }
    public ArrayList<Integer> getSatisfactions() { return satisfactions; }
    public Integer getSatisfaction(int projectID) { return satisfactions.get(projectID); }

}
