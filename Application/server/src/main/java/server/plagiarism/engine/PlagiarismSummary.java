package server.plagiarism.engine;

import server.plagiarism.entity.Project;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 *PlagiarismSummary Interface
 */
public interface PlagiarismSummary {

    /**
     * Summarizes the similarity score result of different PlagiarismEngines
     */
    public void summarizeScore();

    /**
    * generates Report from summarized score of the given Project
    * */
    public void generateReport(Project pid1,Project pid2);


}
