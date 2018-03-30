package server.plagiarism.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.Node;

import server.plagiarism.engine.parsetreebased.LineMatcher;
import server.plagiarism.engine.parsetreebased.ParseTreeBasedEngine;
import server.plagiarism.entity.EntityFactory;
import server.plagiarism.engine.parsetreebased.parser.ConcreteParser;
import server.plagiarism.entity.File;
import server.plagiarism.entity.Project;
import server.plagiarism.entity.Report;

/**
 * The class to generate summary for plagiarism detection
 *
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class ConcretePlagiarismSummary implements PlagiarismSummary{
	/**
	 * The report for the input projects.
	 */
	private Report report = EntityFactory.getInstance().makeReport();

	/**
	 * The cumulative similarity score for the report.
	 */
	private Float cumulativeScore = 0.0f;

	/**
	 * The result summary for the report.
	 */
	private String summary;

	/**
	 * The threshold of similarity score. When the similarity score between
	 * files is above the threshold, they are considered as similar files.
	 */
	private Float similarityThreshold = 0.7f;

	/**
	 * The unique instance for the ConcretePlagiarismSummary.
	 */
	private static ConcretePlagiarismSummary uniqueInstance = null;

	/**
	 * Constructs a ConcretePlagiarismSummary.
	 */
	private ConcretePlagiarismSummary() { }

	/**
	 * Returns the unique instance of ConcretePlagiarismSummary.
	 *
	 * @return  the unique instance
	 */
	public static ConcretePlagiarismSummary instance(){
		if (uniqueInstance == null){
			uniqueInstance = new ConcretePlagiarismSummary();
		}
		return uniqueInstance;
	}

	/**
	 * Set the similarityThreshold.
	 *
	 * @param  threshold  the value of similarityThreshold
	 */
	public void setSimilarityThreshold(Float threshold){
		similarityThreshold = threshold;
	}

	/**
	 * Get the similarityThreshold.
	 *
	 * @return  threshold  the value of similarityThreshold
	 */
	public Float getSimilarityThreshold(){
		return similarityThreshold;
	}

	/**
	 * Get the set of files in the project.
	 *
	 * @param  prj  the project
	 * @return  the HashSet of the files
	 */
	private Set<File> getProjectContent(Project prj){
		Set<File> files = new HashSet<File>();
		for (File f: prj.getFiles()){
			files.add(f);
		}
		return files;
	}

	/**
	 * Get the similarity score for the given files based on MD5BasedEngine
	 *
	 * @param  f1  the first file
	 * @param  f2  the second file
	 * @return  the similarity score based on MD5BasedEngine
	 */
	private Float getMD5SimilarityScore(File f1, File f2){
		PlagiarismEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		return engine.getSimilarityScore();
	}

	/**
	 * Get the similarity score for the given files based on TextDiffBasedEngine
	 *
	 * @param  f1  the first file
	 * @param  f2  the second file
	 * @return  the similarity score based on TextDiffBasedEngine
	 */
	private Float getTextDiffSimilarityScore(File f1, File f2){
		PlagiarismEngine engine = TextDiffBasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		return engine.getSimilarityScore();
	}

	/**
	 * Get the similarity score for the given files based on ParseTreeBasedEngine
	 *
	 * @param  f1  the first file
	 * @param  f2  the second file
	 * @return  the similarity score based on ParseTreeBasedEngine
	 */
	private Float getParseTreeSimilarityScore(File f1, File f2){
		PlagiarismEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		return engine.getSimilarityScore();
	}

	/**
	 * Calculate the similarity score for the plagiarism checker engines,
	 * and generate the cumulative score.
	 */
	public void summarizeScore() {
		if (! validateProjects()) return;

		Set<File> fileSet1 = getProjectContent(report.getProject1());
		Set<File> fileSet2 = getProjectContent(report.getProject2());
		matchFiles(fileSet1, fileSet2);
	}

	/**
	 * Check whether the projects are valid.
	 *
	 * @return  true iff the projects are valid.
	 */
	private boolean validateProjects(){
		if (report.getProject1() == null || report.getProject2() == null){
			List<String> summaryList = new ArrayList<String>();
			summaryList.add("There exists null project.");
			return false;
		} else if (report.getProject1().getFiles() == null ||
					report.getProject2().getFiles() == null ||
					report.getProject1().getFiles().size() == 0 ||
					report.getProject2().getFiles().size() == 0){
			List<String> summaryList = new ArrayList<String>();
			summaryList.add("There exists project with no files.");
			generateResultSummary(summaryList);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Match files from two sets of files.
	 *
	 * @param  fileSet1  the first set of files
	 * @param  fileSet2  the second set of files
	 */
	private void matchFiles(Set<File> fileSet1, Set<File> fileSet2){
		Float newCumScore = 0.0f;
		List<String> summaryList = new ArrayList<String>();

		for (File file1: fileSet1){
			for (File file2: fileSet2){
				if (Math.abs( getMD5SimilarityScore(file1, file2) - 1.0) < 0.0002f){
					summaryList.add("- " + file1.getName() + " in Project " + report.getProject1().getName() +
							" and " + file2.getName() + " in Project " + report.getProject2().getName() +
							" have the same MD5.");
					// if two files have same MD5, we will not compare the files with the rest
					fileSet2.remove(file2);
					newCumScore += 1.0f;
					break;
				} else {
					Float weightedScore = 0.2f * getTextDiffSimilarityScore(file1, file2) +
							0.8f * getParseTreeSimilarityScore(file1, file2);
					if (weightedScore >= similarityThreshold){
						summaryList.add("- " + file1.getName() + " in Project " + report.getProject1().getName() +
								" and " + file2.getName() + " in Project " + report.getProject2().getName() +
								" have similar content. Similarity score = " +
								String.valueOf(Math.round(weightedScore * 10000.0) / 100.0) + "%.");
						addLineMatchingSummary(summaryList, file1, file2);
						// if two files have high weighted similarity score, we will not compare the
						// files with the rest
						fileSet2.remove(file2);
						newCumScore += weightedScore;
						break;
					}
				}
			}
		}
		cumulativeScore = newCumScore / (float) Math.max(fileSet1.size(), fileSet2.size());
		if (Math.abs(cumulativeScore - 0.0) < 0.0002f){
			summaryList.add("No similar files are detected.");
		}
		generateResultSummary(summaryList);
	}

	/**
	 * Add summary of line matching to the given summary list.
	 *
	 * @param summaryList  the summary list to be added
	 * @param file1  the first file
	 * @param file2  the second file
	 */
	private void addLineMatchingSummary(List<String> summaryList, File file1, File file2){
		LineMatcher matcher = new LineMatcher();
		ConcreteParser parser = new ConcreteParser();
		Node node1 = parser.generateAST(file1.getContent());
		Node node2 = parser.generateAST(file2.getContent());
		summaryList.addAll(matcher.matchLineNumbers(node1, node2));
	}

	/**
	 * Generate result summary given the list of string for summary.
	 *
	 * @param  summaryList  the list of string for summary
	 */
	private void generateResultSummary(List<String> summaryList){
		StringBuilder sb = new StringBuilder();
		if (report.getProject1().getFiles() != null
				&& report.getProject2().getFiles() != null ){
			sb.append("Project " + report.getProject1().getName() + " has " +
					String.valueOf(report.getProject1().getFiles().size()) + " files, and project " +
					report.getProject2().getName() + " has " +
					String.valueOf(report.getProject2().getFiles().size()) + " files.\n");
		}
		sb.append("Summary: average similarity score for the projects is " +
					String.valueOf(Math.round(cumulativeScore * 10000.0) / 100.0) + "%\n");
		sb.append("Threshold to test similar files is " +
					String.valueOf(Math.round(similarityThreshold * 10000.0) / 100.0) + "%\n");
		sb.append("--------Details for similar files--------\n");
		for (String s: summaryList){
			sb.append(s);
			sb.append("\n");
		}
		summary = sb.toString();
	}

	/**
	 * Generate the plagiarism detection report for the given projects.
	 *
	 * @param  prj1  the project 1 of the report
	 * @param  prj2  the project 2 of the report
	 */
	public void generateReport(Project prj1, Project prj2) {
		initializeSummary();

		report.setProject1(prj1);
		report.setProject2(prj2);

		summarizeScore();
		report.setSimilarityScore(cumulativeScore);
		report.setResultSummary(summary);
	}

	/**
	 * Initialize the plagiarism summary.
	 */
	private void initializeSummary(){
		cumulativeScore = 0.0f;
		summary = "";
		report = EntityFactory.getInstance().makeReport();
	}

	/**
	 * Get report.
	 *
	 * @return  the report
	 */
	public Report getReport(){
		return report;
	}
}
