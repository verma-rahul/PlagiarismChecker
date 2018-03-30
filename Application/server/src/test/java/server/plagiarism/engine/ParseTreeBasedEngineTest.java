package server.plagiarism.engine;

import server.plagiarism.engine.parsetreebased.ParseTreeBasedEngine;
import server.plagiarism.entity.EntityFactory;
import server.plagiarism.entity.File;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Test cases for ParseTreeBasedEngine
* @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class ParseTreeBasedEngineTest {
	// test for instance designed by singleton
	@Test
	public void instanceTest(){
		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		ParseTreeBasedEngine engine1 = ParseTreeBasedEngine.instance();
		assertEquals(engine, engine1);
	}

	// test to calculate similarity for one null file
	@Test
	public void calculateSimilarityScoreNullFile() {
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");

		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(f1, null);
		assertEquals(engine.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test to calculate similarity for one null file
	@Test
	public void calculateSimilarityScoreNullFile1() {
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("f1");
		f1.setContent("public class JavaTest{}");

		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(null, f1);
		assertEquals(engine.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test to calculate similarity for two null files
	@Test
	public void calculateSimilarityScoreTwoNullFiles() {
		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(null, null);
		assertEquals(engine.getSimilarityScore(), 0.0f, 0.0002);
	}

	// test to calculate similarity for same files
	@Test
	public void getSimilarityScoreSameFile() {
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("public class JavaTest{}");

		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(f1, f1);
		float score = engine.getSimilarityScore();
		assertEquals(score, 0.8f, 0.0002);
	}

	// test to calculate similarity for same files, complex content
	@Test
	public void getSimilarityScoreSameFile1() {
		File f1 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("package astNode;\n" +
				"public class Variable{ \n" +
				"private String variable; \n" +
				"public Variable(String v){ \n" +
				"variable = v;}}");

		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(f1, f1);
		float score = engine.getSimilarityScore();
		assertEquals(score, 1.0f, 0.0002);
	}

	// test if the two files are the same, complicated content
	@Test
	public void getSimilarityScoreSameFile2() throws IOException {
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

		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 1.0f, 0.0002);
	}

	// test to calculate similarity for different files
	@Test
	public void getSimilarityScoreDifferentFile() {
		File f1 = EntityFactory.getInstance().makeFile();
		File f2 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("public class JavaTest{}");
		f2.setName("JavaTest1");
		f2.setContent("public class JavaTest1{}");

		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 0.8f, 0.0002);
	}

	// test to calculate similarity for different files, with empty tree
	@Test
	public void getSimilarityScoreDifferentFileWithEmptyTree1() {
		File f1 = EntityFactory.getInstance().makeFile();
		File f2 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("public class JavaTest{}");
		f2.setName("JavaTest");
		f2.setContent("int a;");

		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 0.0f, 0.0002);
	}

	// test to calculate similarity for different files, with empty tree
	@Test
	public void getSimilarityScoreDifferentProjectWithEmptyTree2() {
		File f1 = EntityFactory.getInstance().makeFile();
		File f2 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("int a;");
		f2.setName("JavaTest");
		f2.setContent("public class JavaTest{}");

		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 0.0f, 0.0002);
	}

	// test to calculate similarity for different files, with 2 empty trees
	@Test
	public void getSimilarityScoreDifferentProjectWithTwoEmptyTrees() {
		File f1 = EntityFactory.getInstance().makeFile();
		File f2 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("int a;");
		f2.setName("JavaTest");
		f2.setContent("int a;");

		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		float score = engine.getSimilarityScore();
		assertEquals(score, 0.0f, 0.0002);
	}

	// test for result summary
	@Test
	public void getResultSummaryTest(){
		File f1 = EntityFactory.getInstance().makeFile();
		File f2 = EntityFactory.getInstance().makeFile();
		f1.setName("JavaTest");
		f1.setContent("int a;");
		f2.setName("JavaTest");
		f2.setContent("int a;");

		ParseTreeBasedEngine engine = ParseTreeBasedEngine.instance();
		engine.calculateSimilarity(f1, f2);
		assertEquals(engine.getResultSummary(), new ArrayList<String>());
	}
}
