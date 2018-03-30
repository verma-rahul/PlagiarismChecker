package server.plagiarism.engine;

import server.plagiarism.entity.EntityFactory;
import server.plagiarism.entity.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Test cases for MD5BasedEngine class.
* @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class MD5BasedEngineTest {
	// test for instance method
	@Test
	public void instanceTest(){
		MD5BasedEngine engine = MD5BasedEngine.instance();
		MD5BasedEngine engine1 = MD5BasedEngine.instance();
		assertEquals(engine, engine1);
	}

	// test if one of the input file is null
	@Test
	public void calculateSimilarityScoreNullFile() {
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");

		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(f1, null);
		assertEquals(engine.getSimilarityScore(), 0.0f, 0.0002);
	}
	// test if one of the input file is null
	@Test
	public void calculateSimilarityScoreNullFile1() {
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");

		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(null, f1);
		assertEquals(engine.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if two of the input files are null
	@Test
	public void calculateSimilarityScoreTwoNullFiles() {
		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(null, null);
		assertEquals(engine.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test if the two files are the same
	@Test
	public void getSimilarityScoreSameFile() {
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("public class JavaTest{}");
		File f2 = EntityFactory.getInstance().makeFile();
		f2.setName("JavaTest");
		f2.setContent("public class JavaTest{}");

		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 1.0f, 0.0002);

	}

	// test if the two files are the same, complicated content
	@Test
	public void getSimilarityScoreSameFile1() throws IOException {
		String filePath = "src/test/resources/PlagiarismChecker-Data/set01/Sample1/LinkedList.java";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();
	    String line = br.readLine();
	    while (line != null) {
	    	sb.append(line);
	        line = br.readLine();
	    }
	    br.close();

	  File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("LinkedList");
		f1.setContent(sb.toString());

		File f2 = EntityFactory.getInstance().makeFile();
		f2.setName("LinkedList");
		f2.setContent(sb.toString());

		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 1.0f, 0.0002);
	}

	// test for one file is empty
	@Test
	public void getSimilarityScoreOneEmptyFile1() {
		File f1 = EntityFactory.getInstance().makeFile();
		File f2 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("");
		f2.setName("JavaTest");
		f2.setContent("public class JavaTest1{}");

		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 0.0f, 0.0002);
	}

	// test for one file is empty
	@Test
	public void getSimilarityScoreOneEmptyFile2() {
		File f1 = EntityFactory.getInstance().makeFile();
		File f2 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("public class JavaTest1{}");
		f2.setName("JavaTest");
		f2.setContent("");

		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 0.0f, 0.0002);
	}

	// test for different files
	@Test
	public void getSimilarityScoreDifferentFile() {
		File f1 = EntityFactory.getInstance().makeFile();
		File f2 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("public class JavaTest{}");
		f2.setName("JavaTest1");
		f2.setContent("public class JavaTest1{}");

		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 0.0f, 0.0002);
	}

	// test for different files
	@Test
	public void getSimilarityScoreDifferentFile1() throws IOException {
		String filePath = "src/test/resources/PlagiarismChecker-Data/set01/Sample1/LinkedList.java";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();
	    String line = br.readLine();
	    while (line != null) {
	    	sb.append(line);
	        line = br.readLine();
	    }
	    br.close();

	  File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("LinkedList");
		f1.setContent(sb.toString());

		filePath = "src/test/resources/PlagiarismChecker-Data/set01/Sample2/SimpleLinkedList.java";
		br = new BufferedReader(new FileReader(filePath));
		sb = new StringBuilder();
	    line = br.readLine();
	    while (line != null) {
	    	sb.append(line);
	        line = br.readLine();
	    }
	    br.close();

		File f2 = EntityFactory.getInstance().makeFile();
		f2.setName("SimpleLinkedList");
		f2.setContent(sb.toString());

		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 0.0f, 0.0002);
	}

	// test for calculateMD5
	@Test
	public void calculateMD5Test(){
		String s = "public class JavaTest{private int a;}";
		MD5BasedEngine engine = MD5BasedEngine.instance();
		assertTrue(engine.calculateMD5(s, "MD5").equals("db3329a6f562cbb841236bd3120d3b93"));
	}

	// test for calculateMD5 with not valid encoding method
	@Test
	public void calculateMD5TestNoSuchAlgo() throws NoSuchAlgorithmException{
		String s = "public class JavaTest{private int a;}";
		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateMD5(s, "");
	}

	// test for result summary
	@Test
	public void getResultSummaryTest(){
		File f1 =  EntityFactory.getInstance().makeFile();
		File f2 =  EntityFactory.getInstance().makeFile();;
		f1.setName("JavaTest");
		f1.setContent("public class JavaTest{}");
		f2.setName("JavaTest1");
		f2.setContent("public class JavaTest1{}");

		MD5BasedEngine engine = MD5BasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		engine.getSimilarityScore();
		assertEquals(engine.getResultSummary(), new ArrayList<String>());
	}
}
