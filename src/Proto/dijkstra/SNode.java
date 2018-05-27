package Proto.dijkstra;

import Proto.domain.Student;

import java.util.ArrayList;

public class SNode{                           // Anonymisierte Studenten-Daten
   private Integer id;                         // Identifikationsnummer
   private ArrayList<Integer> satisfactions;   // Individuelle Beurteilung der Projekte
   private Student student;

   public SNode(Integer id, ArrayList<Integer> satisfactions) {
      this.id = id;
      this.satisfactions = satisfactions;
   }

   public Integer getId() { return id; }
   public ArrayList<Integer> getSatisfactions() { return satisfactions; }
   public Integer getSatisfaction(int projectID) { return satisfactions.get(projectID); }
   public String toString() {
      return id.toString();
   }
   
   
   public Student getStudent(){
      return student;
   }
   
   public void setStudent(Student student){
      this.student = student;
   }
   
//   @Override
//   public String toString(){
//      return "dSNode{" +
//              "id=" + id +
//              ", satisfactions=" + satisfactions +
//              "}\n";
//   }
}
