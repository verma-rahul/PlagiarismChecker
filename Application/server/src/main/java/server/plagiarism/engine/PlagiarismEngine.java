package server.plagiarism.engine;

import server.plagiarism.entity.File;

import java.util.List;


/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 *PlagiarismEngine Interface
 */
public interface PlagiarismEngine {
    /**
     * Calculates Similarity given two  projects
     */
    public void calculateSimilarity(File f1, File f2);

    /**
    * get Similarity Score  of two projects which were processed
    */
    public Float getSimilarityScore();

    /**
     * get the result summary based on plagiarism engine.
     */
    public List<String> getResultSummary();
}
