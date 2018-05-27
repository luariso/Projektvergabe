package demo_ruelling;

import java.util.*;

public class SNode {                           // Anonymisierte Studenten-Daten
   private Integer id;                         // Identifikationsnummer
   private ArrayList<Integer> satisfactions;   // Individuelle Beurteilung der Projekte    

   public SNode(Integer id, ArrayList<Integer> satisfactions) {
      this.id = id;
      this.satisfactions = satisfactions;
   }

   public Integer getId() { return id; }
   public ArrayList<Integer> getSatisfactions() { return satisfactions; }
   public Integer getSatisfaction(int projectID) { return satisfactions.get(projectID); }
   public String toString() {return id.toString();}

}
