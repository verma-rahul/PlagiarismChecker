package server.plagiarism.engine;
import server.plagiarism.engine.parsetreebased.LineMatcher;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import org.junit.Test;
import server.plagiarism.engine.parsetreebased.parser.ConcreteParser;

/**
 * The test cases for LineMatcher
 *
* @author Rahul Verma [verma.rah@husky.neu.edu]
 *
 */
public class LineMatcherTest {
	// file paths for examples of test
    private final String filePath_1_1="src/test/resources/PlagiarismChecker-Data/set01/Sample1/LinkedList.java",
                        filePath_1_2="src/test/resources/PlagiarismChecker-Data/set01/Sample2/SimpleLinkedList.java",
                        filePath_hw="src/test/resources/PlagiarismChecker-Data/HelloWorld/HelloWorld.java"
    ;
    // parser used to convert from string to parse tree
    private static ConcreteParser parser=new ConcreteParser();

    // test to check line match returned by different but similar files
    @Test
    public void lineMatchDifferentFiles() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(filePath_1_1));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        br.close();
        BufferedReader br1 = new BufferedReader(new FileReader(filePath_1_2));
        StringBuilder sb1 = new StringBuilder();
        String line1 = br1.readLine();
        while (line1 != null) {
            sb1.append(line1);
            sb1.append("\n");
            line1 = br1.readLine();
        }
        br1.close();
        Node parsedTree1 = parser.generateAST(sb.toString()),
                parsedTree2 = parser.generateAST(sb1.toString());
        LineMatcher linematch = new LineMatcher();
        List<String> similarity = linematch.matchLineNumbers(parsedTree1, parsedTree2);

        assertEquals(similarity.size(), 19);

    }

    // test to check line match returned by null root nodes
    @Test
    public void lineMatchNullNodes() throws IOException {
        LineMatcher linematch=new LineMatcher();
        List<String> similarity=linematch.matchLineNumbers(null,null);
        assertEquals(similarity.size(), 0);

    }

    // test to check line match returned by one null root node
    @Test
    public void lineMatchOneNullNode() throws IOException {
    	BufferedReader br = new BufferedReader(new FileReader(filePath_hw));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        br.close();
        Node parsedTree1 = parser.generateAST(sb.toString());
        LineMatcher linematch = new LineMatcher();
        List<String> similarity=linematch.matchLineNumbers(parsedTree1, null);
        assertEquals(similarity.size(), 0);
    }

    // test to check line match returned by one null root node
    @Test
    public void lineMatchOneNullNode1() throws IOException {
    	BufferedReader br = new BufferedReader(new FileReader(filePath_hw));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        br.close();
        Node parsedTree1 = parser.generateAST(sb.toString());
        LineMatcher linematch = new LineMatcher();
        List<String> similarity=linematch.matchLineNumbers(null, parsedTree1);
        assertEquals(similarity.size(), 0);
    }

    // test to check line match returned by same file
    @Test
    public void lineMatchSameFiles() throws IOException {
    	BufferedReader br = new BufferedReader(new FileReader(filePath_1_1));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        br.close();
        Node parsedTree1 = parser.generateAST(sb.toString()),
                parsedTree2 = parser.generateAST(sb.toString());
        LineMatcher linematch = new LineMatcher();
        List<String> similarity = linematch.matchLineNumbers(parsedTree1, parsedTree2);

        assertEquals(similarity.size(), 8);

    }

    // test to check line match returned by same file by simple file content
    @Test
    public void lineMatchSameSimpleFiles() throws IOException {

        String sampleContent="package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v;}}";
        Node parsedTree1 = parser.generateAST(sampleContent),
                parsedTree2 = parser.generateAST(sampleContent);
        LineMatcher linematch = new LineMatcher();
        List<String> similarity = linematch.matchLineNumbers(parsedTree1, parsedTree2);

        assertEquals(similarity.size(), 1);
    }

    // test to check line match returned by different file by simple file content
    @Test
    public void lineMatchdifferentSimpleFiles() {
        String sampleContent="package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v;}}";

        String sampleContent1="package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                    " }";
        Node parsedTree1 = parser.generateAST(sampleContent),
                parsedTree2 = parser.generateAST(sampleContent1);
        LineMatcher linematch = new LineMatcher();
        List<String> similarity = linematch.matchLineNumbers(parsedTree1, parsedTree2);

        assertEquals(similarity.size(), 0);

    }

    // test to check line match returned by different file by simple file content
    @Test
    public void lineMatchdifferentSimpleFilesLoop() {
        String sampleContent="package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v; \n" +
                "for (int i = 0; i < 10; i++) System.out.println(\"print\");}}";

        String sampleContent1="package astNode;\n" +
                "public class Variable{ \n" +
                "private int variable; \n" +
                "public Variable(String v){ \n" +
                "variable = 1; \n" +
                "for (int i = 0; i < 10; i++) variable += 1;}}";
        Node parsedTree1 = parser.generateAST(sampleContent),
                parsedTree2 = parser.generateAST(sampleContent1);
        LineMatcher linematch = new LineMatcher();
        List<String> similarity = linematch.matchLineNumbers(parsedTree1, parsedTree2);

        assertEquals(similarity.size(), 0);
    }

    // test to check line match returned by different file by simple file content
    @Test
    public void lineMatchdifferentSimpleFilesSameLoop() {
        String sampleContent="package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v; \n" +
                "for (int i = 0; i < 10; i++) System.out.println(\"print\");}}";

        String sampleContent1="package astNode;\n" +
                "public class Variable{ \n" +
                "private int variable; \n" +
                "public Variable(String v){ \n" +
                "variable = 1; \n" +
                "for (int i = 0; i < 10; i++) System.out.println(\"print\");}}";
        Node parsedTree1 = parser.generateAST(sampleContent),
                parsedTree2 = parser.generateAST(sampleContent1);
        LineMatcher linematch = new LineMatcher();
        List<String> similarity = linematch.matchLineNumbers(parsedTree1, parsedTree2);
        assertEquals(similarity.size(), 1);
    }

    // test to check summary for files with only the same loop
    @Test
    public void lineMatchSummarySimpleFilesSameLoop() {
        String sampleContent="package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v; \n" +
                "for (int i = 0; i < 10; i++) System.out.println(\"print\");}}";

        String sampleContent1="package astNode;\n" +
                "public class Variable{ \n" +
                "private int variable; \n" +
                "public Variable(String v){ \n" +
                "variable = 1; \n" +
                "for (int i = 0; i < 10; i++) System.out.println(\"print\");}}";
        Node parsedTree1 = parser.generateAST(sampleContent),
                parsedTree2 = parser.generateAST(sampleContent1);
        LineMatcher linematch = new LineMatcher();
        List<String> similarity = linematch.matchLineNumbers(parsedTree1, parsedTree2);
        assertEquals(similarity.get(0), "ForStmt @line 6~6 matches to ForStmt @line 6~6");
    }

    // test to check line match summary returned by same file by simple file content
    @Test
    public void lineMatchSummary() {
    	String sampleContent="package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v;}}";
        Node parsedTree1 = parser.generateAST(sampleContent),
                parsedTree2 = parser.generateAST(sampleContent);
        LineMatcher linematch = new LineMatcher();
        List<String> similarity = linematch.matchLineNumbers(parsedTree1, parsedTree2);

        assertEquals(similarity.get(0),
        		"ClassOrInterfaceDeclaration @line 2~5 matches to ClassOrInterfaceDeclaration @line 2~5");

    }

    // test to check line match summary returned by same file by simple file content
    @Test
    public void lineMatchMultiLineSummary() {
    	String sampleContent="package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v; \n" +
                "for(int i=0; i< 10; i++) {int a;} \n" +
                "}}";
        Node parsedTree1 = parser.generateAST(sampleContent),
                parsedTree2 = parser.generateAST(sampleContent);
        LineMatcher linematch = new LineMatcher();
        List<String> similarity = linematch.matchLineNumbers(parsedTree1, parsedTree2);

        assertEquals(similarity.get(0),
        		"ClassOrInterfaceDeclaration @line 2~7 matches to ClassOrInterfaceDeclaration @line 2~7");

    }

    // test to check line match summary returned by same file by simple file content
    @Test
    public void setNodeLoopsTest() {
    	String sampleContent="package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v; \n" +
                "for(int i=0; i< 10; i++) {int a;} \n" +
                "}}";
        Node parsedTree1 = parser.generateAST(sampleContent),
                parsedTree2 = parser.generateAST(sampleContent);
        LineMatcher linematch = new LineMatcher();

        List<String> nodeLoops = new ArrayList<String>();
        nodeLoops.add("ClassOrInterfaceDeclaration");
        linematch.setNodeLoops(nodeLoops);
        List<String> similarity = linematch.matchLineNumbers(parsedTree1, parsedTree2);

        assertEquals(similarity.get(0),
        		"ClassOrInterfaceDeclaration @line 2~7 matches to ClassOrInterfaceDeclaration @line 2~7");

    }

}
