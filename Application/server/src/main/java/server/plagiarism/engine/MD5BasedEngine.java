package server.plagiarism.engine;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import server.plagiarism.entity.File;

/**
 * The plagiarism checker engine based on MD5 hash.
 *
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 *
 */
public class MD5BasedEngine implements PlagiarismEngine{

	/**
	 * The similarity score between two files.
	 */
	private Float similarityScore;

	/**
	 * The result summary of the difference between 2 files.
	 */
	private List<String> resultSummary;

	/**
	 * The unique instance of the engine.
	 */
	private static MD5BasedEngine uniqueInstance = null;

	/**
	 * Returns the unique instance of MD5 based engine.
	 *
	 * @return  the unique instance of MD5 based engine
	 */
	public static MD5BasedEngine instance(){
		if (uniqueInstance == null){
			uniqueInstance = new MD5BasedEngine();
		}
		return uniqueInstance;
	}

	/**
	 * Constructs a MD5 based Engine.
	 */
	private MD5BasedEngine() { }

	/**
	 * Initialize the engine, by reseting the similarity score and result summary.
	 */
	private void initializeEngine(){
		similarityScore = 0.0f;
		resultSummary = new ArrayList<String>();
	}

	/**
	 * Calculate the similarity of MD5 for the given 2 files
	 *
	 * @param  f1  the first file
	 * @param  f2  the second file
	 */
	@Override
	public void calculateSimilarity(File f1, File f2) {
		initializeEngine();

		if (f1 != null && f2 != null) {
			if (calculateMD5(f1.getContent(), "MD5").equals(calculateMD5(f2.getContent(), "MD5"))){
				similarityScore = 1.0f;
			}
		}
	}

	/**
	 * Calculate MD5 for the given string.
	 *
	 * @param  s  the string
	 * @param  method  the method to calculate the MD5 hash
	 * @return  the MD5 hash
	 */
	public String calculateMD5(String s, String method) {
		try {
			MessageDigest md = MessageDigest.getInstance(method);
	        byte[] array = md.digest(s.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	       }
	        return sb.toString();
		} catch (NoSuchAlgorithmException e){
			System.out.println("Calculate MD5 Hash:");
			System.out.println(e);
		}
		return null;
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
