import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Creates Student.txt files for different test cases.
 * These test cases are based on the original projects.txt!
 */
public class TestStudentFileProvider {
    private static final String projectFileName = "data/projects.txt";
    private static final String outputFilePrefix = "data/testfiles/students";
    private static String outputFileName;

    public static void main(String[] args) {
        createFile_worstCase();
        createFile_veryBadCase();
        createFile_badCase();
        createFile_neutralCase();
        createFile_randomCase_1();
    }

    public static void createFile_worstCase() {
        outputFileName = outputFilePrefix + "_worst_case.txt";
        Project[] projects = Projektvergabe.readProjectsFromFile(projectFileName);

        Map<Project, Integer> preferredRankings = new LinkedHashMap<>();
        preferredRankings.put(projects[0], 5);
        preferredRankings.put(projects[1], 5);
        preferredRankings.put(projects[2], 5);
        preferredRankings.put(projects[3], 1);
        preferredRankings.put(projects[4], 5);
        preferredRankings.put(projects[5], 5);
        preferredRankings.put(projects[6], 5);
        preferredRankings.put(projects[7], 5);
        preferredRankings.put(projects[8], 5);
        preferredRankings.put(projects[9], 5);

        ProjectRanker ranker = new ProjectRanker(100, 0,
                0, 0, 0);

        createFile(ranker, 33, preferredRankings);
    }

    public static void createFile_veryBadCase() {
        outputFileName = outputFilePrefix + "_very_bad_case.txt";
        Project[] projects = Projektvergabe.readProjectsFromFile(projectFileName);

        Map<Project, Integer> preferredRankings = new LinkedHashMap<>();
        preferredRankings.put(projects[0], 5);
        preferredRankings.put(projects[1], 1);
        preferredRankings.put(projects[2], 5);
        preferredRankings.put(projects[3], 1);
        preferredRankings.put(projects[4], 5);
        preferredRankings.put(projects[5], 5);
        preferredRankings.put(projects[6], 5);
        preferredRankings.put(projects[7], 5);
        preferredRankings.put(projects[8], 5);
        preferredRankings.put(projects[9], 5);

        ProjectRanker ranker = new ProjectRanker(85, 10,
                5, 0, 0);

        createFile(ranker, 33, preferredRankings);
    }

    public static void createFile_badCase() {
        outputFileName = outputFilePrefix + "_bad_case.txt";
        Project[] projects = Projektvergabe.readProjectsFromFile(projectFileName);

        Map<Project, Integer> preferredRankings = new LinkedHashMap<>();
        preferredRankings.put(projects[0], 5);
        preferredRankings.put(projects[1], 1);
        preferredRankings.put(projects[2], 5);
        preferredRankings.put(projects[3], 1);
        preferredRankings.put(projects[4], 2);
        preferredRankings.put(projects[5], 3);
        preferredRankings.put(projects[6], 5);
        preferredRankings.put(projects[7], 5);
        preferredRankings.put(projects[8], 5);
        preferredRankings.put(projects[9], 5);

        ProjectRanker ranker = new ProjectRanker(80, 15,
                5, 0, 0);

        createFile(ranker, 33, preferredRankings);
    }

    public static void createFile_neutralCase() {
        outputFileName = outputFilePrefix + "_neutral_case.txt";
        Project[] projects = Projektvergabe.readProjectsFromFile(projectFileName);

        Map<Project, Integer> preferredRankings = new LinkedHashMap<>();
        preferredRankings.put(projects[0], 2);
        preferredRankings.put(projects[1], 1);
        preferredRankings.put(projects[2], 5);
        preferredRankings.put(projects[3], 1);
        preferredRankings.put(projects[4], 2);
        preferredRankings.put(projects[5], 3);
        preferredRankings.put(projects[6], 4);
        preferredRankings.put(projects[7], 4);
        preferredRankings.put(projects[8], 5);
        preferredRankings.put(projects[9], 5);

        ProjectRanker ranker = new ProjectRanker(70, 15,
                10, 5, 0);

        createFile(ranker, 33, preferredRankings);
    }

    public static void createFile_randomCase_1() {
        outputFileName = outputFilePrefix + "_random_case_1.txt";
        Project[] projects = Projektvergabe.readProjectsFromFile(projectFileName);

        Map<Project, Integer> preferredRankings = new LinkedHashMap<>();
        preferredRankings.put(projects[0], 1);
        preferredRankings.put(projects[1], 1);
        preferredRankings.put(projects[2], 2);
        preferredRankings.put(projects[3], 2);
        preferredRankings.put(projects[4], 3);
        preferredRankings.put(projects[5], 4);
        preferredRankings.put(projects[6], 4);
        preferredRankings.put(projects[7], 5);
        preferredRankings.put(projects[8], 5);
        preferredRankings.put(projects[9], 5);

        ProjectRanker ranker = new ProjectRanker(55, 20,
                13, 7, 5);

        createFile(ranker, 33, preferredRankings);
    }

    private static void createFile(ProjectRanker ranker, int students, Map<Project, Integer> preferredRankings) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(outputFileName, "UTF-8");
        } catch (Exception e) {
            System.out.println("Error while creating File: " + e.getMessage());
        }
        writer.print(ranker.distributeRankings(students, preferredRankings));
        writer.close();
    }
}
