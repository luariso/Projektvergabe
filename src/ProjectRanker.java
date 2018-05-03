
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Helper class to create test data of students.
 */
public class ProjectRanker {

    private int zeroDeviationProbability;
    private int oneDeviationProbability;
    private int twoDeviationProbability;
    private int threeDeviationProbability;

    /**
     * Constructor.
     * The parameters define the probability of a deviation from the preferred ranking.
     */
    public ProjectRanker(int zeroDeviationProbability, int oneDeviationProbability, int twoDeviationProbability,
                         int threeDeviationProbability, int fourDeviationProbability) {
        if ((zeroDeviationProbability + oneDeviationProbability + twoDeviationProbability
                + threeDeviationProbability + fourDeviationProbability) != 100) {
            throw new IllegalArgumentException("The sum of the deviation probabilities must be 100.");
        }
        this.zeroDeviationProbability = zeroDeviationProbability;
        this.oneDeviationProbability = oneDeviationProbability;
        this.twoDeviationProbability = twoDeviationProbability;
        this.threeDeviationProbability = threeDeviationProbability;
    }

    /**
     * Reconfigures the deviation probabilities.
     */
    public void setDeviationProbabilities(int zeroDeviationProbability, int oneDeviationProbability, int twoDeviationProbability,
                         int threeDeviationProbability, int fourDeviationProbability) {
        this.zeroDeviationProbability = zeroDeviationProbability;
        this.oneDeviationProbability = oneDeviationProbability;
        this.twoDeviationProbability = twoDeviationProbability;
        this.threeDeviationProbability = threeDeviationProbability;
    }

    /**
     * Creates a String with students and ranks the Projects considering the preferred ranking
     * and the deviation probabilities.
     *
     * @param students          number of students to be created.
     * @param preferredRankings A Map containing the Projects to be ranked mapped to their preferred rank.
     */
    public String distributeRankings(int students, Map<Project, Integer> preferredRankings) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < students; ++i) {
            builder.append(i + 1).append(" ")
                    .append("Student")/*.append(i + 1)*/.append(" ")
                    .append("Test").append(" ")
                    .append((i + 1) * 10000).append(" ");

            builder.append(rankProjects(preferredRankings));
            builder.append("\n");
        }
        return builder.toString();
    }

    private StringBuilder rankProjects(Map<Project, Integer> preferredRankings) {
        StringBuilder builder = new StringBuilder();
        int rankingDeviation;
        for (Project project : preferredRankings.keySet()) {
            rankingDeviation = calculateRankingDeviation();
            int actualRanking = calculateRanking(rankingDeviation, preferredRankings.get(project));

            String projectRanking = "(" + project.getId() + "," + actualRanking + ")";
            builder.append(projectRanking).append(" ");
        }
        return builder;
    }

    private int calculateRankingDeviation() {
        Random r = new Random();
        int i = r.nextInt(100) + 1;
        int rankingDeviation;
        int percentCounter;
        if (i <= (percentCounter = zeroDeviationProbability)) {
            rankingDeviation = 0;
        } else if (i <= (percentCounter += oneDeviationProbability)) {
            rankingDeviation = 1;
        } else if (i <= (percentCounter += twoDeviationProbability)) {
            rankingDeviation = 2;
        } else if (i <= (percentCounter + threeDeviationProbability)) {
            rankingDeviation = 3;
        } else {
            rankingDeviation = 4;
        }
        return rankingDeviation;
    }

    private int calculateRanking(int rankingDeviation, int preferredRanking) {
        Random r = new Random();
        int actualRanking;
        if (((preferredRanking - rankingDeviation) < 1) && ((preferredRanking + rankingDeviation) > 5)) {
            if (r.nextInt(2) == 0) {
                actualRanking = 1;
            } else {
                actualRanking = 5;
            }
        } else if ((preferredRanking - rankingDeviation) < 1) {
            actualRanking = preferredRanking + rankingDeviation;
        } else if ((preferredRanking + rankingDeviation) > 5) {
            actualRanking = preferredRanking - rankingDeviation;
        } else {
            if (r.nextInt(2) == 0) {
                actualRanking = preferredRanking - rankingDeviation;
            } else {
                actualRanking = preferredRanking + rankingDeviation;
            }
        }
        return actualRanking;
    }
}
