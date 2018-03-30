package server.plagiarism.engine.parsetreebased;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import server.plagiarism.engine.PlagiarismEngine;
import server.plagiarism.engine.parsetreebased.parser.ConcreteParser;
import server.plagiarism.engine.parsetreebased.parser.Parser;
import server.plagiarism.entity.File;

/**
 * The plagiarism checker engine based on parse tree.
 *
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class ParseTreeBasedEngine implements PlagiarismEngine {
	/**
	 * The similarity score between two files.
	 */
	private Float similarityScore;

	/**
	 * The result summary of the difference between 2 files.
	 */
	private List<String> resultSummary;

	/**
	 * The parser used to parse the trees.
	 */
	private Parser parser = new ConcreteParser();

	/**
	 * The parse tree comparator based on tree edit distance.
	 */
	private TEDBasedComparator tedComparator = new TEDBasedComparator();

	/**
	 * The parse tree comparator based on the count of type of nodes.
	 */
	private NodeTypeBasedComparator typeComparator = new NodeTypeBasedComparator();

	/**
	 * The unique instance of the engine.
	 */
	private static ParseTreeBasedEngine uniqueInstance = null;

	/**
	 * Returns the unique instance of parse tree based engine.
	 *
	 * @return  the unique instance of parse tree based engine
	 */
	public static ParseTreeBasedEngine instance(){
		if (uniqueInstance == null){
			uniqueInstance = new ParseTreeBasedEngine();
		}
		return uniqueInstance;
	}

	/**
	 * Constructs a parse tree based Engine.
	 */
	private ParseTreeBasedEngine() { }

	/**
	 * Initialize the engine, by reseting the similarity score and result summary.
	 */
	private void initializeEngine(){
		similarityScore = 0.0f;
		resultSummary = new ArrayList<String>();
	}

	/**
	 * Calculate the similarity score between the input files.
	 *
	 * @param  f1  the first file
	 * @param  f2  the second file
	 */
	@Override
	public void calculateSimilarity(File f1, File f2) {
		initializeEngine();

		if (f1 != null && f2 != null) {
			CompilationUnit tree1 = parser.generateAST(f1.getContent());
			CompilationUnit tree2 = parser.generateAST(f2.getContent());

			similarityScore = calculateWeightedSimilarity(tree1, tree2);
		}
	}

	/**
	 * Calculate the weighted similarity score between the trees, based on tree edit distance
	 * and statistics of nodes in the tree.
	 *
	 * @param  tree1  the first tree to be compared
 	 * @param  tree2  the second tree to be compared
	 * @return  the weighted simialrty score
	 */
	private Float calculateWeightedSimilarity(CompilationUnit tree1, CompilationUnit tree2){
		Float weightedSimilarity = 0.0f;

		if (tree1 == null || tree2 == null) return weightedSimilarity;

		weightedSimilarity = tedComparator.calculateSimilarity(tree1, tree2) * 0.8f +
									typeComparator.calculateSimilarity(tree1, tree2) * 0.2f;
		return weightedSimilarity;
	}

	/**
	 * Get the similarity score between the files.
	 *
	 * @return  the similarity score
	 */
	@Override
	public Float getSimilarityScore() {
		return similarityScore;
	}

	/**
	 * Get the result summary for the plagiarism detection for the files.
	 *
	 * @return  the result summary
	 */
	@Override
	public List<String> getResultSummary(){
		return resultSummary;
	}

}
