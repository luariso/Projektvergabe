package demo_ruelling;

import java.util.*;

public class Graph {
        // Interface zwischen Anwenderprogramm und Graphenanwendung
        ArrayList<SNode> studentData;
        ArrayList<Job>     jobData;
     
     
        // Daten für Graphenalgorithmen
        Node source,target;
        ArrayList<Node> sourceNodes;
        ArrayList<Node> targetNodes;
        Matching matching;
    
     
    public Graph(ArrayList<SNode> sList, ArrayList<PNode> pList){   
        boolean debugmode=false;

        studentData = sList;
        if (debugmode) {
          System.out.print("demo_ruelling.Graph: studentData=");
          for (SNode s:studentData)
             System.out.print(" " + s.getId());
          System.out.println();
        }

        jobData = new ArrayList<Job>();
        // Für jedes Projekt Arbeitsplätze anlegen
        for (int ip=0; ip<pList.size(); ++ip) {
           PNode p= pList.get(ip);    
           for (int i=1; i<= p.getCapacity(); ++i) {
                  Job j= new Job(p, p.getId());
                  jobData.add(j);
           }   
        }   
        if (debugmode) {
          System.out.print("demo_ruelling.Graph: Jobs=");
          for (Job j:jobData)
             System.out.print(" " + j.getProjectId());
          System.out.println();
        }
     
        // Anlegen der Knoten des Graphen
        source = new Node();

        sourceNodes= new ArrayList<Node>();
        for (int i=0; i< studentData.size(); ++i)
                sourceNodes.add(new Node(i));   
     
        targetNodes= new ArrayList<Node>();
        for (int i=0; i< jobData.size(); ++i)
                targetNodes.add(new Node(i));
     
        target = new Node();
     
     
        // Anlegen eines leeren Matchings
        matching = new Matching();
     
        // Eintragen möglicher Paarbildungen
        for (int s=0; s<sourceNodes.size(); ++s)
           for (int t=0; t<targetNodes.size(); ++t){
                  int projectId= jobData.get(t).projectId; 
                  int weight= studentData.get(s).getSatisfaction(projectId-1);    
                  if (weight>=0) {
                    Node sn= sourceNodes.get(s);
                    Node tn= targetNodes.get(t);
                    sn.addEdge(tn, weight -tn.dist + sn.dist);
                  }   
           }   
        
        // Vervollständigung des Graphen um source und target
        for (Node s:sourceNodes) 
                source.addEdge(s, 0);   
        for (Node t:targetNodes)
            t.addEdge(target, 0); 
    }   
     


    public int Dijkstra(boolean debugMode) {
        // Dijkstra Algorithmus zur Berechnung der kürzesten Wege
        // von source aus
        // Rückgabe der Entfernung von source nach target

        if (source.edges.size()==0)
          return Integer.MAX_VALUE;

        // Initialisierung der Distanzwerte
        source.dist=0;
        source.prev=null;
        for (Node s:sourceNodes){
           s.dist=Integer.MAX_VALUE;
           s.prev=null;
        }
        for (Node s:targetNodes){
           s.dist=Integer.MAX_VALUE;
           s.prev=null;
        }
        
        target.dist=Integer.MAX_VALUE;
        target.prev=null;

        TreeSet<Node> nodes= new TreeSet<Node>();
        nodes.add(source);
        nodes.addAll(sourceNodes);
        nodes.addAll(targetNodes);
        nodes.add(target);

        
        if (debugMode) System.out.println("Darstellung des Graphen vor Dijkstra:" + this.toString());

        while (!nodes.isEmpty()) {
                Node u= nodes.first();
                nodes.remove(u);
                if (debugMode) System.out.println(" u={" + u.uniqueId + "} u.dist=" + u.dist
                                                          + "#edges=" + u.edges.size());
                for (Edge e:u.edges) {
                   if (debugMode) System.out.println("Edge: {"+ u.uniqueId + "}{"+e.to.uniqueId+"} weight=" + e.weight);
                   Node v= e.to;
                   if (u.dist+e.weight < v.dist) {
                         // Änderung von v.dist  
                         nodes.remove(v);
                         if (debugMode) System.out.print("     v{"+v.uniqueId+"}.dist=" + v.dist);
                         v.dist=u.dist+e.weight;
                         v.prev=u;
                         v.prevEdge=e;
                         if (debugMode) System.out.println(" --> " + v.dist);
                         nodes.add(v);
                   }
                }
        }
        if (debugMode) System.out.println("Darstellung des Graphen nach Dijkstra:" + this.toString());
        int result= target.dist;

        // kürzesten Pfad von source nach target rückwärts durchlaufen
        // dabei erste und letzt Kante entfernen und die anderen
        // umdrehen und Kantengewichte anhand der dist-Werte konvertieren 

        Node v=target;
        Edge currentEdge= v.prevEdge;
        Node u= v.prevEdge.from;
        u.edges.remove(currentEdge);

        v=u;
        boolean addMode=true;
        while ((v.prev !=null) && (v.prev.id>=0)) {
           u=v.prev;
           currentEdge= v.prevEdge;
           u.edges.remove(currentEdge);
           if (addMode) {
              if (debugMode)
            	System.out.println("neue Matchingkante: " + studentData.get(u.id).toString()
                                                 + " -> " + jobData.get(v.id).toString());
              matching.add(u, v);
           }
           else {
              if (debugMode)
            	 System.out.println("zu löschende Matchingkante: " + jobData.get(u.id).toString()
                                                          + " -> " + studentData.get(v.id).toString());
              matching.delete(u);
           }
           
           // Kante umdrehen
           if (debugMode)
              System.out.println("           Kante: {" + currentEdge.from.uniqueId + "} -> {"
                                                       + currentEdge.to.uniqueId + "}  weight= "
                                                       + currentEdge.weight);
           Node tmp=currentEdge.to;
           currentEdge.to=currentEdge.from;
           currentEdge.from=tmp;
           currentEdge.weight= -currentEdge.weight;
           if (debugMode)
              System.out.println("Umgedrehte Kante: {" + currentEdge.from.uniqueId + "} -> {"
                                                       + currentEdge.to.uniqueId + "}  weight= "
                                                       + currentEdge.weight);
           v.edges.add(currentEdge);
           addMode=!addMode;
           v=u;
        }
        u=v.prev;
        currentEdge= v.prevEdge;
        if (debugMode)
          System.out.println(" erste Kante: {" + currentEdge.from.uniqueId + "} -> {"
        	                   	               + currentEdge.to.uniqueId + "}  weight= "
        	                 	               + currentEdge.weight);
        	                 	          
        u.edges.remove(currentEdge);


        // Normieren sämtlicher Kantengewichte um negative Gewichte zu vermeiden
        nodes= new TreeSet<Node>();
        nodes.add(source);
        nodes.addAll(sourceNodes);
        nodes.addAll(targetNodes);
        nodes.add(target);
        
        for (Node x:nodes) {
           for (Edge e:x.edges)
                 e.weight = e.weight -e.to.dist + e.from.dist;
        }


        if (debugMode) System.out.println("Darstellung des Graphen nach Kanten-Normierung" + this.toString());

        return result;
    }



    TreeMap<Integer,Integer> getMatching() {
    	boolean debugMode=false;
        TreeMap<Integer,Integer> result= new TreeMap<Integer,Integer>();
        for (Node u:matching.pairs.keySet()) {
           result.put(studentData.get(u.id).getId(),
                      jobData.get(matching.pairs.get(u).id).getProjectId());
        }
        return result;
    }



    public String toString() {
       String text= "\n-------------------------------------\n";
       text += "{" + source.uniqueId + "}: dist=" + source.dist + " edges: ";
       for (Edge e: source.edges) {
         int s= e.to.id;
         text += "(" + studentData.get(s).toString();  //studentId; 
         text += " " + e.weight + ")";
       }
       text += "\n\n";
       for (Node s:sourceNodes) {
         text += "{" + s.uniqueId + "}" + (studentData.get(s.id).toString())
                         + ": dist=" + s.dist + " edges: ";
          for (Edge e: s.edges) {
                 int t= e.to.id;
                 text += "(" + jobData.get(t).toString();
                 text += " " + e.weight + ")";
          }
          text += "\n";
       }
       text += "\n";
       for (Node t:targetNodes) {
          text += "{" + t.uniqueId + "}" + (jobData.get(t.id).toString())
                          + ": dist=" + t.dist + " edges: ";
          for (Edge e: t.edges) {
                 int s= e.to.id;
                 if (s>=0) {
                   text += "(" + studentData.get(s).toString();
                   text += " " + e.weight + ")";
                 }
                 else
                   text += "({" + e.to.uniqueId + "} " + e.weight + ")";
          }
          text += "\n";
       }
      text += "\n";
      text += "{" + target.uniqueId + "}: dist=" + target.dist + "\n";

      text += "-------------------------------------\n";
      return text;
    }


    private static int count=0;

    class Node implements Comparable<Node> {
       int uniqueId;    // Unterscheidbarkeit aller Knoten bei Sortierungen
       int id;          // Identifikationsnummer des Knotens 
       LinkedList<Edge> edges;
       int dist;        // Entfernung von source
       Node prev;       // Vorgänger auf kürzesten Weg mit Wurzel source
       Edge prevEdge;   // Kange, die auf kürzesten Weg mit Wurzel source zu diesem Knoten führt

       void addEdge(Node d, int w) {
            edges.add(new Edge(this,d,w));
       }

       Node() {
            ++count;
            uniqueId=count;
            this.id = -1;
            edges=new LinkedList<Edge>();
            dist=0;
            prev=null;
           prevEdge=null;
       }

       Node(int id) {
          ++count;
          uniqueId=count;
          this.id= id;
          edges= new LinkedList<Edge>();
          dist=0;
          prev=null;
          prevEdge=null;
       }

       public int compareTo(Node o) {
            if (dist<o.dist)
              return -1;
            if (dist > o.dist)
              return +1;
            return uniqueId-o.uniqueId;
       }
   }

class UniqueComparator implements Comparator<Node> {
    public int compare(Node a, Node b){
      return (a.uniqueId - b.uniqueId);
    }
}


   class Edge {
      Node from, to;
      int weight;

      Edge(Node a, Node b, int w) {
         from=a;
         to = b;
         weight =w;
      }
   }


   class Matching {
      TreeMap<Node, Node> pairs;
      Matching() {
         pairs= new TreeMap<Node, Node>(new UniqueComparator());
      }

      boolean containsPair(Node s, Node t) {
         return (pairs.containsKey(s) && pairs.get(s).equals(t));
      }

      void add(Node x, Node y){
            pairs.put(x,y);
            Set<Node> keys= pairs.keySet();
      }

      void delete(Node x){
         pairs.remove(x);
      }

   }


   class Job {
      private PNode    p;
      private Integer  projectId; // Nummer des Projekts

      Job(PNode p, int id) {
         this.p = p;
         projectId=id;
      }

      public String toString() {
         return p.toString();
      }

      int getProjectId() {
         return projectId;
      }

   };


}


  
