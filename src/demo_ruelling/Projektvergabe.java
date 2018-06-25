package demo_ruelling;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.List;


public class Projektvergabe {
   static Label       labelStudentId;
   static Label       labelStudentName;

   static Label       labelProjects;
   static Label[]     labelProjectId;
   static Label[]     labelProjectName;
   static TextField[] textfieldProjectValue;

   static Label[] labelResultCountStudents;
   static Label[] labelResultText;
   static Label[] labelResultSat;
   static Label   labelFinalText;
   static Label   labelFinalSat;


   static int currentStudent=0;
   static boolean sortedDisplayMode=false;

   static Integer MatchingResult[] = null;
      
   static Integer[] countMappedStudents = {0,0,0,0,0};
   static Integer[] satMatchedStudents   = {1,2,3,4,5};
   static Float     satMatchedAll        = new Float(0.0);
   static boolean   matchingAvailable    = false;


   // -----------------------------------------------------------------------
   // Einlesen aller Projekte
   // -----------------------------------------------------------------------
   public static Project[] readProjectsFromFile(String filename) {
      // Read available projects from file and return them in an array

      TreeMap<Integer, Project> tm= new TreeMap<Integer, Project>();

      Scanner scanner;
      try {
           scanner = new Scanner(new File(filename));
           int projectId;
           int projectCapacity;
           String projectName;

           System.out.println("\nAngebotene Projekte:");
           while (scanner.hasNext()) {
              projectId = 0;
              projectCapacity = 0;
              projectName = "";

              if (scanner.hasNextInt()) {
                projectId = scanner.nextInt();
                if (scanner.hasNextInt()) {
                  projectCapacity = scanner.nextInt();
                  if (scanner.hasNextLine()) {
                    projectName = scanner.nextLine();
                    int i=0;
                    while ((i<projectName.length()) && (projectName.charAt(i)==' '))
                        ++i;
                    projectName = projectName.substring(i,projectName.length());

                    System.out.println( "id=" + projectId + " capacity=" + projectCapacity + ":    " + projectName);
                    if (tm.containsKey(projectId))
                      System.out.println("Fehler: Die Projektnummer " + projectId + " wurde mehrfach verwendet!");
                    else
                      tm.put(projectId, new Project(projectId, projectCapacity, projectName));
                  }
                }
              }
              else {
                scanner.next();
              }
           }
           scanner.close();
      }
      catch (IOException e) {
          System.out.println("error: " + e.getMessage());
      }
      Project[] help= new Project[tm.values().size()];
      tm.values().toArray(help);
      return help;
   }


   // -----------------------------------------------------------------------
   // Einlesen aller Studenten
   // -----------------------------------------------------------------------
   static Student[] readStudentsFromFile(String filename, int number_of_projects) {
      // Read available students from file and return them in an array
      boolean debugmode=false;

      if (debugmode)
        System.out.println("Kontrollausgabe: Reading students");
      TreeMap<Integer, Student> tm= new TreeMap<Integer, Student>();

      Scanner scanner;
      try {
           scanner = new Scanner(new File(filename));

           System.out.println("\nAngemeldete Studenten:");
           while (scanner.hasNext()) {
              Integer studentId;
              String studentName, studentName2, matrikel;
              Integer[] satisfactions = new Integer[number_of_projects];
              Integer[] projectIds = new Integer[number_of_projects];

              studentId = null;
              studentName=null;
              studentName2=null;
              matrikel=null;
              for (int i=0; i< number_of_projects; ++i)
                 satisfactions[i]=0;

              while (!scanner.hasNextInt())
                scanner.next();
              if (scanner.hasNextInt()) {
                studentId = scanner.nextInt();
                if (scanner.hasNext()) {
                  studentName = scanner.next();
                  if (scanner.hasNext()) {
                    studentName2 = scanner.next();
                    if (scanner.hasNext()) {
                      matrikel = scanner.next();
                    }
                  }
                }

                System.out.println(studentId + " " + studentName + " " + studentName2 + " " + matrikel);

                String help=scanner.nextLine();            // String mit Bewertungen  
                Scanner scanner2= new Scanner(help);       // Scanner für Bewertungen   
                scanner2.findInLine("\\([D]+,D+\\)");
                int pos=0;
                System.out.print(" \t");
                while (scanner2.hasNext()) {
                    String pair= scanner2.next();         // gefundenes Datenpaar
                    Scanner scanner3=new Scanner(pair);   // scanner für Subkomponenten
                    Integer a=new Integer(scanner3.findInLine("[0-9]+"));
                    Integer b=new Integer(scanner3.findInLine("[0-9]+"));
                    satisfactions[a-1]=b;                    // Verbuchung im array
                    projectIds[pos]= a;
                    System.out.print(" ("+a+","+b+")");
                    scanner3.close();
                    ++pos;
                }
                System.out.println();
                scanner2.close();

                if (tm.containsKey(studentId))
                  System.out.println("Fehler: Die Studentennummer " + studentId + " wurde mehrfach verwendet!");
                else {
                  tm.put(studentId, new Student(studentId, studentName, studentName2, matrikel, satisfactions, projectIds));
                }
              }
           }
           scanner.close();
           System.out.println();
      }
      catch (IOException e) {
          System.out.println("error: " + e.getMessage());
      }
      Student[] help= new Student[tm.values().size()];
      tm.values().toArray(help);

      return help;
   }



   // -----------------------------------------------------------------------
   // Aktualisierung der angezeigten Daten
   // -----------------------------------------------------------------------
   static void actualizeDisplay(int currentStudent, Project[] projects, Student[] students) {
      // Anzeige der Projektbewertungen
      System.out.println("Kontrollausgabe: currentStudent=" + currentStudent);


      // Defaulteinstellung der Farben
      for (int i=0; i<projects.length; ++i) {
         labelProjectId[i].setForeground(Color.BLACK);
         labelProjectId[i].setBackground(Color.WHITE);
         labelProjectName[i].setForeground(Color.BLACK);
         labelProjectName[i].setBackground(Color.WHITE);
         textfieldProjectValue[i].setForeground(Color.BLACK);
         textfieldProjectValue[i].setBackground(Color.WHITE);
      }

      if (currentStudent>0) {
        Integer[] projectIds = students[currentStudent-1].getProjectIds();

        System.out.print("Kontrollausgabe: projectIds=    ");
        for (int i=0; i<projectIds.length; ++i)
           System.out.print(" " +projectIds[i]);
        System.out.println();

        Integer[] satisfactions= students[currentStudent-1].getSatisfactions();
        System.out.print("Kontrollausgabe: satisfactions= ");
        for (int i=0; i<satisfactions.length; ++i)
           System.out.print(" " +satisfactions[projectIds[i]-1]);
        System.out.println();

        for (int i=0; i<projectIds.length; ++i) {
         if (projectIds[i] != null) {
           Integer p= projectIds[i];
           labelProjectId[i].setText(p.toString());
           labelProjectName[i].setText(projects[p-1].getName());
         }
        }
           
        for (int i=0; i<projects.length; ++i) {
           Integer p= projectIds[i];
           textfieldProjectValue[i].setText("   ");   // Defaultwert
           if ((satisfactions[p-1] != null) && (satisfactions[p-1]>0))
             textfieldProjectValue[i].setText(satisfactions[p-1].toString());
           textfieldProjectValue[i].setForeground(Color.BLACK);
           textfieldProjectValue[i].setBackground(Color.WHITE);

           if (matchingAvailable && (MatchingResult[currentStudent-1] != null)) {
             int mp=MatchingResult[currentStudent-1];
             if (projectIds[i]==mp) {
               labelProjectId[i].setForeground(Color.BLUE);
               labelProjectId[i].setBackground(Color.YELLOW);
               labelProjectName[i].setForeground(Color.BLUE);
               labelProjectName[i].setBackground(Color.YELLOW);
               textfieldProjectValue[i].setForeground(Color.BLUE);
               textfieldProjectValue[i].setBackground(Color.YELLOW);
             }
           }   

        }
     
        for (int i=0; i<5; ++i) {
           labelResultCountStudents[i].setText(" ");
           labelResultText[i].setText(" ");
           labelFinalText.setText(" ");
           labelResultSat[i].setText(" ");
           labelFinalSat.setText(" ");
        }
    
      }
      else {

        for (int i=0; i<projects.length; ++i) {
           labelProjectId[i].setText(((Integer)(i+1)).toString());   // Defaultwert
           labelProjectName[i].setText(projects[i].getName());
           textfieldProjectValue[i].setText("   ");   // Defaultwert
        }

        if (matchingAvailable && (satMatchedAll>0)) {
           for (int i=0; i<5; ++i) {
              labelResultCountStudents[i].setText(countMappedStudents[i].toString()); 
              labelResultText[i].setText("Projektvergaben mit Rating");
              labelResultSat[i].setText(((Integer)(i+1)).toString());
              labelFinalText.setText("Durchschnittliche Rating:");
              labelFinalSat.setText(satMatchedAll.toString());
           }
        }
      }  

      if (currentStudent>0) {
        labelStudentId.setText(students[currentStudent-1].getId().toString());
        labelStudentName.setText(students[currentStudent-1].getName().toString() + ", "
                               + students[currentStudent-1].getName2() + " ("
                               + students[currentStudent-1].getMatrikel() +")");
      }
      else {
         labelStudentId.setText(" ");
         labelStudentName.setText(" ");
      }
   }




   // -----------------------------------------------------------------------
   // Expandieren eines Strings auf die gewünschte Länge
   // -----------------------------------------------------------------------
   static String upTo(String s, int len) {
     String result=s;
     while (result.length()<len)
       result += " ";
     return result;
   }



   // -----------------------------------------------------------------------
   // Matching-Algorithmus
   // -----------------------------------------------------------------------
   static TreeMap<Integer,Integer> performMatching(ArrayList<SNode> sNodes, ArrayList<PNode> pNodes) {
      final boolean debugmode=false;
      if (debugmode)
        System.out.println ("Kontrollausgabe: sNodes.size=" + sNodes.size() + " pNodes.size()=" + pNodes.size());
      Graph g= new Graph(sNodes, pNodes);
      TreeMap<Integer,Integer> matching = new TreeMap<Integer,Integer>();

      // ==========================================================================
      // Ausführen des Matching-Algorithmus (basierend auf Dijkstra-Algorithmus)
      // ==========================================================================
      int dist=0;
      for (int k=1; dist!=Integer.MAX_VALUE; ++k) {
          // ======================================================
          // Berechnung eines kostenoptimalen Matchings der Größe k
          // ======================================================

          // Dijkstra-Algorithmus ausführen, um kürzeste Wege zu berechnen
          dist=g.Dijkstra(false);
          //System.out.println("Dijkstra: dist=" + dist);
          matching = g.getMatching();

          if (dist!=Integer.MAX_VALUE) {
            System.out.print("Verbesserung des Matchings auf k=" + k + ":");
            for (Integer s:matching.keySet())
               System.out.print(" " + s + ":" + matching.get(s));
            System.out.println();
          }
      }
      return matching;
   }




   public static void main (String args[]) {

/*
      final String projectsFilename=    "C:/Users/ruelling/Desktop/ProjektvergabeProjekt/projects.txt";
      final String studentsFilename=    "C:/Users/ruelling/Desktop/ProjektvergabeProjekt/students.txt";
      final String studentsFilename_out="C:/Users/ruelling/Desktop/ProjektvergabeProjekt/students_out.txt";
*/
      final String projectsFilename=    "data/projects.txt";
      final String studentsFilename=    "data/testfiles/students_bad_case.txt";
      final String studentsFilename_out="out/students_out.txt";
   
      final Project[] projects= readProjectsFromFile(projectsFilename);
      final int number_of_projects = projects.length;

      final Student[] students= readStudentsFromFile(studentsFilename, number_of_projects);
      final int number_of_students = students.length;


      // Ergebnisse des Vergabeverfahrens
      MatchingResult = new Integer[number_of_students];   // zugewiesenes Project pro Student
      // Die dazugehörige studentische Bewertungen sat eines Studenten s erhält man durch
      // p = MatchingResult[s-1];
      // sat = students[s-1].getSatisfaction[p-1];
      


      // Ermittlung der maximalen Länge von Namen (für formatierte Ausgaben)
      int help1=0;
      int help2=0;
      int help3=0;
      for (int i=0; i< students.length; ++i) {
         if (students[i].getName().length()     > help1) help1= students[i].getName().length();
         if (students[i].getName2().length()    > help2) help2= students[i].getName2().length();
         if (students[i].getMatrikel().length() > help3) help3= students[i].getMatrikel().length();
      }
      final int maxLengthName=help1;
      final int maxLengthName2=help2;
      final int maxLengthMatrikel=help3;






      final Frame frame = new Frame ("Projektvergabe");
      frame.setLayout(null);


      if ((currentStudent>0) && (students[currentStudent] != null)) {
         labelStudentId    = new Label(students[currentStudent].getId().toString(),0);
         labelStudentName  = new Label(students[currentStudent].getName().toString() + ", "
                                          + students[currentStudent].getName2() + " ("
                                          + students[currentStudent].getMatrikel() +")", 0);
      }
      else {
         labelStudentId     = new Label(" ", 0);
         labelStudentName   = new Label(" ", 0);
      }
    
      labelProjects     = new Label("Projekte:");
      labelProjectId    = new Label[number_of_projects];
      labelProjectName  = new Label[number_of_projects];
      textfieldProjectValue = new TextField[number_of_projects];

      labelResultCountStudents = new Label[5];
      labelResultText          = new Label[5];
      labelResultSat           = new Label[5];
      labelFinalText           = new Label();
      labelFinalSat            = new Label();

      for (int i=0; i<number_of_projects; ++i) {
         if (projects[i] != null) {
           labelProjectId[i] =     new Label(projects[i].getId().toString());
           labelProjectName[i] =   new Label(projects[i].getName());

           //System.out.println("Kontrollausgabe: curentStudent=" + currentStudent + "  i=" + i);
           textfieldProjectValue[i] =  new TextField("   ",2);  // Defaultwert
           if (currentStudent>0) {
             Integer[] satisfactions = students[currentStudent-1].getSatisfactions();
             Integer[] projectIds    = students[currentStudent-1].getProjectIds();
             Integer p= projectIds[i];
             if ((satisfactions[p-1]>0) && (satisfactions[p-1]<=5))
               textfieldProjectValue[i] =  new TextField(satisfactions[p-1].toString());
           }
         }
      }



      final Button btnIncr =   new Button("++student");
      final Button btnDecr =   new Button("--student");
      final Button btnMode =   new Button("displayMode");
      //-------------------------------------------------------------------
      final Button btnSave =   new Button("save");
      final Button btnExport = new Button("export");
      final Button btnMatch =  new Button("match");
      final Button btnExit =   new Button("exit");

      int yOffset=20;
      //-------------------------------------------------------------------
      btnIncr.setBounds( 20,   yOffset+(number_of_projects*25+80),  80, 50);
      btnDecr.setBounds(120,   yOffset+(number_of_projects*25+80),  80, 50);
      btnMode.setBounds(220,   yOffset+(number_of_projects*25+80),  80, 50);
      //-------------------------------------------------------------------
      btnSave.setBounds( 20,   yOffset+(number_of_projects*25+160), 50, 50);
      btnExport.setBounds( 80, yOffset+(number_of_projects*25+160), 50, 50);
      btnMatch.setBounds(140,  yOffset+(number_of_projects*25+160), 50, 50);
      btnExit.setBounds(200,   yOffset+(number_of_projects*25+160), 50, 50);
      //-------------------------------------------------------------------


      labelStudentId.setBounds(   20,yOffset+20,   20,25);
      labelStudentName.setBounds( 50,yOffset+20,  400,25);

      labelProjects.setBounds(    20,yOffset+45,  400,25);
      for (int i=0; i<number_of_projects; ++i) {
         if (projects[i] != null) {
            labelProjectId[i].setBounds(   20,yOffset+70+i*20,  20,25);
            labelProjectName[i].setBounds( 50,yOffset+70+i*20, 400,25);
            textfieldProjectValue[i].setBounds(460,yOffset+70+i*20,  20,25);
         }
      }



      for (int i=0; i<5; ++i) {
         labelResultCountStudents[i] = new Label(" ");
         labelResultText[i]          = new Label(" ");
         labelResultSat[i] =           new Label(" ");
      }  


      for (int i=0; i<5; ++i) {
         labelResultCountStudents[i].setBounds(20, yOffset+350+number_of_projects*20 +i*20,  30,20);
         labelResultText[i].setBounds(         50, yOffset+350+number_of_projects*20 +i*20, 180,20);
         labelResultSat[i].setBounds(         240, yOffset+350+number_of_projects*20 +i*20,  30,20);
      }
      labelFinalText.setBounds( 50, yOffset+350+number_of_projects*20 + 5*20,  180,20);
      labelFinalSat.setBounds(235,  yOffset+350+number_of_projects*20 + 5*20,  30,20);


      frame.add(labelStudentId);
      frame.add(labelStudentName);

      frame.add(labelProjects);
      for (int i=0; i<number_of_projects; ++i) {
        if (projects[i] != null) {
           frame.add(labelProjectId[i]);
           frame.add(labelProjectName[i]);
           frame.add(textfieldProjectValue[i]);
        }
      }

      frame.add(btnIncr); frame.add(btnDecr);   frame.add(btnMode);
      frame.add(btnSave); frame.add(btnExport); frame.add(btnMatch); frame.add(btnExit);

      for (int i=0; i<5; ++i) {
         frame.add(labelResultCountStudents[i]);
         frame.add(labelResultText[i]);
         frame.add(labelResultSat[i]);
      }
      frame.add(labelFinalText);
      frame.add(labelFinalSat);


      //actualizeDisplay(currentStudent, projects, students);


      // -----------------------------------------------------------------------
      // Wirkung des Buttons "++student"
      // -----------------------------------------------------------------------
      btnIncr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                  System.out.println("button \"++student\" pressed");
                  if ((currentStudent+1 <=students.length) && (students[currentStudent] != null))
                    ++currentStudent;
                  actualizeDisplay(currentStudent, projects, students);
            }
      });

      // -----------------------------------------------------------------------
      // Wirkung des Buttons "--student"
      // -----------------------------------------------------------------------
      btnDecr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                  System.out.println("button \"--student\" pressed");
                  if (currentStudent>0)
                    --currentStudent;
                  actualizeDisplay(currentStudent, projects, students);
            }
      });

      // -----------------------------------------------------------------------
      // Wirkung des Buttons "save"
      // -----------------------------------------------------------------------
      btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                  System.out.print("button \"save\" pressed: ");
                  if (currentStudent>0) {
                    // aktuelle Input-Daten
                    Integer[] satisfactions= students[currentStudent-1].getSatisfactions();
                    Integer[] projectIds=    students[currentStudent-1].getProjectIds();

                    for (int i=0; i<number_of_projects; ++i) {
                       Scanner scanner= new Scanner(textfieldProjectValue[i].getText());
                       if (scanner.hasNextInt()) {
                         Integer help= scanner.nextInt();
                         Integer p = projectIds[i];
                         satisfactions[p-1] = help;
                         System.out.print(" sat[" + (currentStudent-1) + "][" + p + "]=" + help);
                       }
                       scanner.close();
                    }
                
                    // zu berechnende Output-Daten
                    Integer[] localProjectIds= new Integer[number_of_projects];
                    Integer[] localProjectSat= new Integer[number_of_projects];


                    // Neue interne Sortierung
                    if (sortedDisplayMode) { 
                      // Sortierung der Projekte nach der satisfaction-Komponente
                      int pos=0;
                      for (int k=1; k<=5; ++k) {
                         // Bewertungen: 1,2,3,4,5
                         for (int j=0; j<number_of_projects; ++j) {
                            Integer p = projectIds[j];
                            Integer s = satisfactions[p-1];
                            if (s != null)
                              if (s==k) {
                                localProjectIds[pos]= p;
                                localProjectSat[pos]= s;
                              }
                         }
                      }
                      // Zurückschreiben der neuen Positionsdaten
                      projectIds=    localProjectIds;
                      satisfactions= localProjectSat;
                    }
                    else {
                      System.out.println("Wiederherstellung der natürlichen Sortierung");
                      // Wiederherstellung der natürlichen Sortierung 
                      for (int j=0; j<number_of_projects; ++j) {
                         Integer p = projectIds[j];
                         Integer s = satisfactions[p-1];
                         localProjectIds[p-1] = p;
                         localProjectSat[p-1] = s;
                      }
                      // Zurückschreiben der neuen Positionsdaten
                      projectIds=    localProjectIds;
                      satisfactions= localProjectSat;
                    }
                    
                    matchingAvailable=false;  // nach der Änderung von Daten ist das Matching ungültig

                    // Kontrollausgabe:
                    for (int j=0; j<number_of_projects; ++j) {
                       Integer p = projectIds[j];
                       Integer s = satisfactions[j];
                       if ((s != null) && (p!=null)) {
                         System.out.print("  (" + p + "," + s + ")");
                       }
                    }

                  }
                  System.out.println();
                  actualizeDisplay(currentStudent, projects, students);
            }
      });


      // -----------------------------------------------------------------------
      // Wirkung des Buttons "export"
      // -----------------------------------------------------------------------
      btnExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                  System.out.print("button \"export\" pressed: ");

                  if (currentStudent>0) {
                    // Aktualisierung der Projektbewertungen
                    Integer[] satisfactions= students[currentStudent-1].getSatisfactions();
                    Integer[] projectIds=    students[currentStudent-1].getProjectIds();
                    for (int i=0; i<number_of_projects; ++i) {
                       Scanner scanner= new Scanner(textfieldProjectValue[i].getText());
                       if (scanner.hasNextInt()) {
                         Integer help= scanner.nextInt();
                         Integer p = projectIds[i];
                         satisfactions[p-1] = help;
                         System.out.print(" sat[" + (currentStudent-1) + "][" + p + "]=" + help);
                       }
                       scanner.close();
                    }
                  }
                  System.out.println();
                  actualizeDisplay(currentStudent, projects, students);


                  // Schreiben der students-Datei (in der natürlichen Anordnung
                  PrintWriter f=null;
                  try {
                       f= new PrintWriter(new BufferedWriter(new FileWriter(studentsFilename_out)));
                       for (int i=0; i<number_of_students; ++i) {
                           System.out.print((i+1) + " \t" + upTo(students[i].getName(),maxLengthName)
                                         + " "   + upTo(students[i].getName2(),maxLengthName2)
                                         + " "   + upTo(students[i].getMatrikel(),maxLengthMatrikel)
                                         + "   \t");
                           f.print((i+1) + " \t" + upTo(students[i].getName(),maxLengthName)
                                         + " "   + upTo(students[i].getName2(),maxLengthName2)
                                         + " "   + upTo(students[i].getMatrikel(),maxLengthMatrikel)
                                         + "   \t");
                           Integer[] satisfactions= students[i].getSatisfactions();
                           //Integer[] projectIds=    students[i].getProjectIds();
                           // ---------------------------------------------------------------------------------
                           System.out.print(" students[" + i +"]: ");
                           for (int j=0; j<number_of_projects; ++j) {
                              System.out.print(" (" + (j+1) + "," + satisfactions[j] + ")");
                              f.print(        "  (" + (j+1) + "," + satisfactions[j] + ")");
                           }
                           System.out.println();
                           f.println();
                           // ---------------------------------------------------------------------------------
                       }
                       f.close();
                  }
                  catch (IOException e) {
                      System.out.println("error: " + e.getMessage());
                  }

            }
      });



      // -----------------------------------------------------------------------
      // Wirkung des Buttons "exit"
      // -----------------------------------------------------------------------
      btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                  System.exit (0);
            }
      });



      // -----------------------------------------------------------------------
      // Wirkung des Buttons "match"
      // -----------------------------------------------------------------------
      btnMatch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               // Umkopieren der Studenten in anonymisierte Objekte
               boolean debugmode=false;
               ArrayList<SNode> sNodes = new ArrayList<SNode>();
               List<Student> randomizedStudents = randomizeList(Arrays.asList(students));
               for (Student s: randomizedStudents){
                  ArrayList<Integer> satisfactions= new ArrayList<Integer>(s.getSatisfactions().length);
                  for (int i=0; i<s.getSatisfactions().length; ++i) {
                     if (debugmode) {
                        System.out.println("Kontrollausgabe: i=" + i + " length=" + s.getSatisfactions().length);
                        System.out.println("Kontrollausgabe: satisfactions.size=" + satisfactions.size());
                     }
                     satisfactions.add(s.getSatisfaction(i+1));
                     if (debugmode) {
                       System.out.print("Kontrollausgabe: satisfactions: ");
                       for (Integer x:satisfactions)
                          System.out.print(" "+ x);
                       System.out.println();
                     }
                  }
                  System.out.println("Kontrollausgabe: Student=" + s.getId() + " sat=" + satisfactions);
                  sNodes.add(/* s.getId(),*/  new SNode(s.getId(), satisfactions));
               }

               // Umkopieren der Projeke in anonymisierte Objekte
               ArrayList<PNode> pNodes = new ArrayList<PNode>(); 
               for (Project p: projects) {
                  //System.out.println("Kontrollausgabe: Project=" + p.getId() + " capacity=" + p.getCapacity());
                  pNodes.add(new PNode(p.getId(), p.getCapacity()));
                  //System.out.println("Kontrollausgabe: pNodes.size=" + pNodes.size());
               }

               // ==================================
               // Ausführen des Matching-Algorithmus
               // ==================================
               TreeMap<Integer,Integer> matching = performMatching(sNodes, pNodes);


               System.out.println("\nOptimales maximales Matching:");
               for (Integer s:matching.keySet()) {
                  Integer p= matching.get(s); 
                  System.out.println("   " + s + "  -->  " + p + "  (Rating: " +  students[s-1].getSatisfaction(p) +")");
                  MatchingResult[s-1]= p;
               }

               System.out.println("\nProjektvergabe:");
               for (Integer s:matching.keySet())
                  System.out.println(students[s-1].getName() + ", " +students[s-1].getName2() + "  -->   " + projects[matching.get(s)-1].toString());  


               { // Verbuchung des Ergebnis für die graphische Ausgabe
                 int[] count = {0,0,0,0,0};
                 float sum = 0;
                 int total = 0;
                 for (int s=0; s<number_of_students; ++s) {
                    int p= MatchingResult[s];
                    int sat = students[s].getSatisfaction(p); 
                    ++count[sat-1];
                    ++total;
                    sum += sat;
                 }
                 for (int i=0; i<5; ++i) {
                    countMappedStudents[i]=count[i];
                 } 
                 int help= (int)((10*sum)/total+0.5);
                 satMatchedAll = ((float)help)/10;
                 matchingAvailable=true;
                 actualizeDisplay(currentStudent, projects, students);
               }

            }
      });



      // -----------------------------------------------------------------
      // Wirkung des Buttons "displayMode
      // -----------------------------------------------------------------
      btnMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                  System.out.println("button 6 pressed");
                  sortedDisplayMode=!sortedDisplayMode;

                  if (currentStudent>0) {
                    // aktuelle Daten
                    Integer[] satisfactions= students[currentStudent-1].getSatisfactions();
                    Integer[] projectIds=    students[currentStudent-1].getProjectIds();

                    if (sortedDisplayMode) {
                      System.out.println("Sortierung der Projekte nach der satisfaction-Komponente");
                      // Sortierung der Projekte nach der satisfaction-Komponente
                 
                      int pos=0;
                      for (int k=1; k<=5; ++k) {
                         // Bewertungen: 1,2,3,4,5
                         for (int j=0; j<number_of_projects; ++j) {
                            Integer s = satisfactions[j];
                            if (s != null)
                              if (s==k) {
                                projectIds[pos]= j+1;
                                ++pos;
                              }
                         }
                      }
                    }
                    else {
                      System.out.println("Wiederherstellung der natürlichen Sortierung");
                      // Wiederherstellung der natürlichen Sortierung 
                      for (int j=0; j<number_of_projects; ++j) {
                         projectIds[j] = j+1;
                      }
                    }

                    // Kontrollausgabe:
                    for (int j=0; j<number_of_projects; ++j) {
                       Integer p = projectIds[j];
                       Integer s = satisfactions[p-1];
                       if ((s != null) && (p!=null)) {
                         System.out.print("  (" + p + "," + s + ")");
                       }
                    }

                  }

                  System.out.println("sortedDisplayMode=" + sortedDisplayMode);

                  actualizeDisplay(currentStudent, projects, students);
            }
      });





      frame.setSize(500, 800);
      frame.setVisible(true);
      frame.addWindowListener ( new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                  System.out.println("Close Window");

                  // --------------------------------------
                  long Time0 = System.currentTimeMillis();
                  long Time1;
                  long runTime = 0;
                  while(runTime<4000){  // wait for 4 seconds
                      Time1 = System.currentTimeMillis();
                      runTime = Time1 - Time0;
                  }
                  // --------------------------------------
                  System.exit (0);
            }
      });
  }

    private static <T> List<T>  randomizeList(List<T> list) {
        List<T> copy = new ArrayList<>(list);
        List<T> result = new ArrayList<>(list.size());
        Random random = new Random();
        int nextInt;

        for (int i = 0; i < list.size(); ++i) {
            nextInt = random.nextInt(copy.size());
            result.add(i, copy.get(nextInt));
            copy.remove(nextInt);
        }
        return result;
    }
}
