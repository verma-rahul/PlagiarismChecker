package server.plagiarism.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The tokens with term frequency information.
 *
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 *
 */
public class Tokens {

	/**
	 * Term frequency information of tokens.
	 */
	private Map<String, Integer> tf;

	/**
	 * Constructs a new tokens class.
	 *
	 * @param  l  a list of tokens
	 */
	public Tokens(String[] l) {
		tf = generateTermFrequency(l);
	}

	/**
	 * Generate the term frequency for each word in tokens.
	 *
	 * @param  tokens  the list of words
	 * @return  the HashMap with word as key and term frequency as value.
	 */
	private Map<String, Integer> generateTermFrequency(String[] l){
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String t: l){
			if (t != null){
				map.put(t, map.containsKey(t) ? map.get(t) + 1 : 1);
			}
		}
		return map;
	}

	/**
	 * Calculate the cosine similarity score between this tokens and the given one.
	 *
	 * @param  tokens1  the token to be compared with
	 * @return  the cosine similarity score
	 */
	public Float calculateCosineSimilarity(Tokens tokens1){
		if (getCorpus().size() == 0 || tokens1.getCorpus().size() == 0){
			return 0.0f;
		}

		Set<String> corpus = new HashSet<String>();
		corpus.addAll(getCorpus());
		corpus.addAll(tokens1.getCorpus());

		int[] vector1 = new int[corpus.size()];
		int[] vector2 = new int[corpus.size()];

		int index = 0;
		for(String word: corpus){
			vector1[index] = getTF(word);
			vector2[index] = tokens1.getTF(word);
			index += 1;
		}

		Float cosineSimilarity = cosineSimilarity(vector1, vector2);
		return cosineSimilarity;

	}

	/**
	 * Calculate the cosine similarity between the given vectors.
	 *
	 * @param  v1  the first vector
	 * @param  v2  the second vector
	 * @return  the similarity score
	 */
	private Float cosineSimilarity(int[] v1, int[] v2){
		int denorm = 0;
		int norm1 = 0;
		int norm2 = 0;
		for (int i = 0; i < v1.length; i++){
			denorm += v1[i] * v2[i];
			norm1 += v1[i] * v1[i];
			norm2 += v2[i] * v2[i];
		}
		return (float) (denorm / (Math.sqrt((float)  norm1) * Math.sqrt((float) norm2)));
	}

	/**
	 * Return the set of words for the tokens.
	 *
	 * @return  the set of words
	 */
	public Set<String> getCorpus(){
		return tf.keySet();
	}

	/**
	 * Return the term frequency of the given word in the tokens.
	 *
	 * @param  word  the given word
	 * @return  the term frequency of the word
	 */
	public int getTF(String word){
		return tf.containsKey(word) ? tf.get(word) : 0;
	}

}
