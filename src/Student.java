public class Student {

    private Integer id;
    private String name;
    private String name2;
    private String matrikel;
    private Integer[] satisfactions;    // Individuelle Beurteilung der Projekte:   (ProjektIndex  -> Beurteilung)
    private Integer[] projectIds;       // Individuelle Reihenfolge der Projekte:   (Position      -> ProjektNummer)
                                        //                                          (ProjektNummer = ProjektIndex+1)


    public Student(Integer id, String name, String name2, String matrikel,
                   Integer[] satisfactions, Integer[] projectIds) {
       this.id = id;
       this.name = name;
       this.name2 = name2;
       this.matrikel = matrikel;
       this.satisfactions = satisfactions;
       this.projectIds = projectIds;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getName2() { return name2; }
    public String getMatrikel() { return matrikel; }
    public Integer[] getSatisfactions() { return satisfactions; }
    public Integer getSatisfaction(int projectID) { return satisfactions[projectID-1]; }
    public Integer[] getProjectIds() { return projectIds; }
    public String toString() {return name; }

}
