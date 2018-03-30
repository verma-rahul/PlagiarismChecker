package server.plagiarism.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test cases for Tokens.
 *
* @author Rahul Verma [verma.rah@husky.neu.edu]
 *
 */
public class TokensTest {

	// test if the string list is null
	@Test
	public void getCorpusTestNull() {
		String[] tokens = new String[1];
		Tokens t = new Tokens(tokens);
		assertEquals(t.getCorpus().size(), 0);
	}

	// test calculateSimilarity if the input tokens is null
	@Test
	public void calculateSimilarityTestNull1() {
		String[] tokens = new String[1];
		Tokens t = new Tokens(tokens);
		assertEquals(t.calculateCosineSimilarity(t), 0.0f, 0.0002);
	}

	// test calculateSimilarity if one of the input tokens is null
	@Test
	public void calculateSimilarityTestNull2() {
		String[] tokens = new String[1];
		Tokens t = new Tokens(tokens);

		tokens = new String[5];
		tokens[0] = "dog";
		tokens[1] = "cat";
		tokens[2] = "cow";
		tokens[3] = "dog";
		tokens[4] = "dog";

		Tokens t1 = new Tokens(tokens);
		assertEquals(t.calculateCosineSimilarity(t1), 0.0f, 0.0002);
	}

	// test calculateSimilarity if one of the input tokens is null
	@Test
	public void calculateSimilarityTestNull3() {
		String[] tokens = new String[1];
		Tokens t = new Tokens(tokens);

		tokens = new String[5];
		tokens[0] = "dog";
		tokens[1] = "cat";
		tokens[2] = "cow";
		tokens[3] = "dog";
		tokens[4] = "dog";

		Tokens t1 = new Tokens(tokens);
		assertEquals(t1.calculateCosineSimilarity(t), 0.0f, 0.0002);
	}

	// test getCorpus for valid tokens
	@Test
	public void getCorpusTestStrings() {
		String[] tokens = new String[5];
		tokens[0] = "dog";
		tokens[1] = "cat";
		tokens[2] = "cow";
		tokens[3] = "dog";
		tokens[4] = "dog";

		Tokens t = new Tokens(tokens);
		assertEquals(t.getCorpus().size(), 3);
		assertTrue(t.getCorpus().contains("dog"));
		assertTrue(t.getCorpus().contains("cat"));
		assertTrue(t.getCorpus().contains("cow"));
	}

	// test getTF for words in valid tokens
	@Test
	public void getTFTestStrings() {
		String[] tokens = new String[5];
		tokens[0] = "dog";
		tokens[1] = "cat";
		tokens[2] = "cow";
		tokens[3] = "dog";
		tokens[4] = "dog";

		Tokens t = new Tokens(tokens);
		assertEquals(t.getTF("dog"), 3);
		assertEquals(t.getTF("cat"), 1);
		assertEquals(t.getTF("cow"), 1);
	}

	// test getTF for missing word in valid tokens
	@Test
	public void getTFTestStringsMissingWord() {
		String[] tokens = new String[5];
		tokens[0] = "dog";
		tokens[1] = "cat";
		tokens[2] = "cow";
		tokens[3] = "dog";
		tokens[4] = "dog";

		Tokens t = new Tokens(tokens);
		assertEquals(t.getTF("pig"), 0);
	}

	// test calculateCosineSimilarity for the same tokens
	@Test
	public void calculateCosineSimilaritySame() {
		String[] tokens = new String[5];
		tokens[0] = "dog";
		tokens[1] = "cat";
		tokens[2] = "cow";
		tokens[3] = "dog";
		tokens[4] = "dog";
		Tokens t = new Tokens(tokens);
		assertEquals(t.calculateCosineSimilarity(t), 1.0f, 0.0002);
	}

	// test calculateCosineSimilarity for different tokens
	@Test
	public void calculateCosineSimilarityDifferent1() {
		String[] tokens1 = new String[4];
		tokens1[0] = "dog";
		tokens1[1] = "dog";
		tokens1[2] = "dog";
		tokens1[3] = "dog";

		String[] tokens2 = new String[4];
		tokens2[1] = "cat";
		tokens2[2] = "cat";
		tokens2[0] = "cat";
		tokens2[3] = "cat";

		Tokens t1 = new Tokens(tokens1);
		Tokens t2 = new Tokens(tokens2);
		assertEquals(t1.calculateCosineSimilarity(t2), 0.0f, 0.0002);
	}

	// test calculateCosineSimilarity for different tokens
	@Test
	public void calculateCosineSimilarityDifferentCorpus() {
		String[] tokens1 = new String[4];
		tokens1[0] = "dog";
		tokens1[1] = "cat";

		String[] tokens2 = new String[4];
		tokens2[1] = "cow";
		tokens2[2] = "pig";

		Tokens t1 = new Tokens(tokens1);
		Tokens t2 = new Tokens(tokens2);
		assertEquals(t1.calculateCosineSimilarity(t2), 0.0f, 0.0002);
	}

	// test calculateCosineSimilarity for different tokens with overlapping words
	@Test
	public void calculateCosineSimilarityOverlapCorpus1() {
		String[] tokens1 = new String[4];
		tokens1[0] = "dog";
		tokens1[1] = "cat";
		tokens1[2] = "pig";

		String[] tokens2 = new String[4];
		tokens2[1] = "cow";
		tokens2[2] = "pig";

		Tokens t1 = new Tokens(tokens1);
		Tokens t2 = new Tokens(tokens2);
		assertEquals(t1.calculateCosineSimilarity(t2), Math.sqrt(1.0/6.0), 0.0002);
	}

	// test calculateCosineSimilarity for different tokens with overlapping words
	@Test
	public void calculateCosineSimilarityOverlapCorpus2() {
		String[] tokens1 = new String[4];
		tokens1[0] = "dog";
		tokens1[1] = "cat";
		tokens1[2] = "pig";

		String[] tokens2 = new String[4];
		tokens2[1] = "cow";
		tokens2[2] = "pig";
		tokens2[3] = "cat";

		Tokens t1 = new Tokens(tokens1);
		Tokens t2 = new Tokens(tokens2);
		assertEquals(t1.calculateCosineSimilarity(t2), 2.0/3.0, 0.0002);
	}

	// test calculateCosineSimilarity for different tokens with overlapping words
	@Test
	public void calculateCosineSimilarityOverlapCorpus3() {
		String[] tokens1 = new String[4];
		tokens1[0] = "dog";
		tokens1[1] = "cat";
		tokens1[2] = "pig";

		String[] tokens2 = new String[6];
		tokens2[0] = "cat";
		tokens2[1] = "cow";
		tokens2[2] = "pig";
		tokens2[3] = "cat";
		tokens2[4] = "cat";
		tokens2[5] = "lamb";

		Tokens t1 = new Tokens(tokens1);
		Tokens t2 = new Tokens(tokens2);
		assertEquals(t1.calculateCosineSimilarity(t2), 2.0/3.0, 0.0002);
	}

	// test calculateCosineSimilarity for different tokens with all words overlapping
	@Test
	public void calculateCosineSimilarityFullLengthTokens() {
		String[] tokens1 = new String[4];
		tokens1[0] = "dog";
		tokens1[1] = "cat";
		tokens1[2] = "pig";
		tokens1[3] = "lamb";

		String[] tokens2 = new String[6];
		tokens2[0] = "cat";
		tokens2[1] = "cow";
		tokens2[2] = "pig";
		tokens2[3] = "cat";
		tokens2[4] = "cat";
		tokens2[5] = "lamb";

		Tokens t1 = new Tokens(tokens1);
		Tokens t2 = new Tokens(tokens2);
		assertEquals(t1.calculateCosineSimilarity(t2), 5/(4*Math.sqrt(3.0)), 0.0002);
	}
}
