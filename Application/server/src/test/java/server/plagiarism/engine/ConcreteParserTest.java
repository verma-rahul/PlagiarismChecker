package server.plagiarism.engine;

import server.plagiarism.engine.parsetreebased.parser.ConcreteParser;
import server.plagiarism.entity.EntityFactory;
import server.plagiarism.entity.File;
import server.plagiarism.entity.Project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;

/**
 * Test cases for ConcreteParser
 *
* @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class ConcreteParserTest {

	// test for generateAST with simple tree
	@Test
	public void generateASTTest(){
		ConcreteParser parser = new ConcreteParser();
		String code = "public class JavaTest{}";
		CompilationUnit cu = parser.generateAST(code);
		assertEquals(cu.getClass().getName(), "com.github.javaparser.ast.CompilationUnit");
	}

	// test for generateAST with a bigger tree
	@Test
	public void generateASTSimpleClass(){
		ConcreteParser parser = new ConcreteParser();
		String code = "package astNode;\n" +
						"public class Variable{ \n" +
						"private String variable; \n" +
						"public Variable(String v){ \n" +
						"variable = v;}}";
		CompilationUnit cu = parser.generateAST(code);
		assertEquals(cu.getPackageDeclaration().get().getName().toString(), "astNode");
	}

	// test if the given string is empty
	@Test
	public void generateASTEmpty(){
		ConcreteParser parser = new ConcreteParser();
		String code = " ";
		CompilationUnit cu = parser.generateAST(code);
		assertEquals(cu, new CompilationUnit());
	}

	// test if the given string is not a valid java file
	@Test
	public void generateASTInvalid(){
		ConcreteParser parser = new ConcreteParser();
		String code = "public class JavaTest{}1";
		CompilationUnit cu = parser.generateAST(code);
		assertEquals(cu, null);
	}

	// test if the given string is not a valid java file
	@Test
	public void generateASTInvalidClass(){
		ConcreteParser parser = new ConcreteParser();
		String code = "int a;";
		CompilationUnit cu = parser.generateAST(code);
		assertEquals(cu, null);
	}

	// test whether tokenizer generates correct tokens without removing keywords
	@Test
	public void tokenizerNoKeywords(){
		ConcreteParser parser = new ConcreteParser();
		String code = "public CLASS JavaTest{}";
		parser.setKeywords("file");
		String[] tokens = parser.tokenizer(code);
		assertEquals(tokens.length, 3);
		assertEquals(tokens[0], "public");
		assertEquals(tokens[1], "class");
		assertEquals(tokens[2], "javatest");
	}

	// test whether tokenizer generates correct tokens without removing keywords
	@Test
	public void tokenizerNoKeywords1(){
		ConcreteParser parser = new ConcreteParser();
		String code = "public class JavaTest { cls1.method1; \n " +
				"int a = 1+2;  \n" +
				"}";
		parser.setKeywords("file");
		String[] tokens = parser.tokenizer(code);
		assertEquals(tokens.length, 8);
		assertEquals(tokens[0], "public");
		assertEquals(tokens[1], "class");
		assertEquals(tokens[2], "javatest");
		assertEquals(tokens[3], "cls1.method1");
		assertEquals(tokens[4], "int");
		assertEquals(tokens[5], "a");
		assertEquals(tokens[6], "1");
		assertEquals(tokens[7], "2");
	}

	// test whether tokenizer generates correct tokens removing keywords
	@Test
	public void tokenizerWithKeywords(){
		ConcreteParser parser = new ConcreteParser();
		String code = "public class JavaTest{}";
		String[] tokens = parser.tokenizer(code);
		assertEquals(tokens.length, 1);
		assertEquals(tokens[0], "javatest");
	}

	// test whether tokenizer generates correct tokens removing keywords
	// no matter the original string has upper case or not
	@Test
	public void tokenizerWithKeywordsCheckLowerCase(){
		ConcreteParser parser = new ConcreteParser();
		String code = "Public Class JavaTest{}";
		String[] tokens = parser.tokenizer(code);
		assertEquals(tokens.length, 1);
		assertEquals(tokens[0], "javatest");
	}

	// test whether tokenizer generates correct tokens removing keywords
	@Test
	public void tokenizerWordSet1(){
		ConcreteParser parser = new ConcreteParser();
		String code = "public class JavaTest { cls1.method1; \n " +
					"int a = 1+2;  \n" +
					"}";
		String[] tokens = parser.tokenizer(code);
		assertEquals(tokens.length, 5);
		assertEquals(tokens[0], "javatest");
		assertEquals(tokens[1], "cls1.method1");
		assertEquals(tokens[2], "a");
		assertEquals(tokens[3], "1");
		assertEquals(tokens[4], "2");
	}

	// test whether tokenizer generates correct tokens removing keywords
	@Test
	public void tokenizerWordSet2(){
		ConcreteParser parser = new ConcreteParser();
		String code = "public class JavaTest { float f = 1.234f; float f1 = .5; \n" +
					"}";
		String[] tokens = parser.tokenizer(code);
		assertEquals(tokens.length, 5);
		assertEquals(tokens[0], "javatest");
		assertEquals(tokens[1], "f");
		assertEquals(tokens[2], "1.234f");
		assertEquals(tokens[3], "f1");
		assertEquals(tokens[4], ".5");
	}

	// test if the path of the keyword file is incorrect
	@Test
	public void getJavaKeyWordsWrongFile() throws IOException{
		ConcreteParser parser = new ConcreteParser();
		parser.setKeywords("file");
	}

	// test if keywords are correctly retrieved
	@Test
	public void getJavaKeyWordsTest(){
		ConcreteParser parser = new ConcreteParser();
		String filePath = "src/main/resources/JavaKeywords.txt";
		parser.setKeywords(filePath);
		Set<String> keywords = parser.getKeywords();
		assertEquals(keywords.size(), 51);
		assertTrue(keywords.contains("class"));
		assertTrue(keywords.contains("abstract"));
	}

	// test for preProcessDocument
	@Test
	public void preProcessDocumentTest(){
		File f = EntityFactory.getInstance().makeFile();
		f.setContent("public class JavaTest{}");
		List<File> list = new ArrayList<File>();
		list.add(f);
		Project p = EntityFactory.getInstance().makeProject();
		p.setFiles(list);

		ConcreteParser parser = new ConcreteParser();
		parser.preProcessDocument(p);
		assertEquals(p.getFiles().get(0).getContent(), "public class JavaTest{}");
	}
}
