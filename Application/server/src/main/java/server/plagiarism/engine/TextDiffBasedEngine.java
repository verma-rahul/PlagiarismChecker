package server.plagiarism.engine;

import java.util.ArrayList;
import java.util.List;

import server.plagiarism.engine.parsetreebased.parser.ConcreteParser;
import server.plagiarism.engine.parsetreebased.parser.Parser;
import server.plagiarism.entity.File;

/**
 * The plagiarism checker engine based on text difference.
 *
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class TextDiffBasedEngine implements PlagiarismEngine{
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
	 * The unique instance of the engine.
	 */
	private static TextDiffBasedEngine uniqueInstance = null;

	/**
	 * Returns the unique instance of text diff based engine.
	 *
	 * @return  the unique instance of text diff based engine
	 */
	public static TextDiffBasedEngine instance(){
		if (uniqueInstance == null){
			uniqueInstance = new TextDiffBasedEngine();
		}
		return uniqueInstance;
	}

	/**
	 * Constructs a text diff based Engine.
	 */
	private TextDiffBasedEngine() { }

	/**
	 * Initialize the engine, by reseting the similarity score and result summary.
	 */
	private void initializeEngine(){
		similarityScore = 0.0f;
		resultSummary = new ArrayList<String>();
	}

	/**
	 * Calculate similarity based on test difference of 2 files
	 *
	 * @param  f1  the first file
	 * @param  f2  the second file
	 */
	@Override
	public void calculateSimilarity(File f1, File f2) {
		initializeEngine();

		if (f1 != null && f2 != null) {
			String[] tokens1 = parser.tokenizer(f1.getContent());
			String[] tokens2 = parser.tokenizer(f2.getContent());

			if (tokens1.length > 0 && tokens2.length > 0)
				similarityScore = calculateTokensSimilarity(tokens1, tokens2);
		}
	}

	/**
	 * Calculate the similarity score between two token lists.
	 *
	 * @param  tokens1  the first token list
	 * @param  tokens2  the second token list
	 * @return  the similarity score
	 */
	private Float calculateTokensSimilarity(String[] tokens1, String[] tokens2){
		Tokens t1 = new Tokens(tokens1);
		Tokens t2 = new Tokens(tokens2);
		Float cosineSimilarity = t1.calculateCosineSimilarity(t2);
		return cosineSimilarity;
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
