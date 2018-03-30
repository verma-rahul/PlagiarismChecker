package server.plagiarism.engine;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import org.junit.Test;
import server.plagiarism.engine.parsetreebased.NodeTypeBasedComparator;
import server.plagiarism.engine.parsetreebased.parser.ConcreteParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for NodeTypeBasedComparator
 *
* @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class NodeTypeBasedComparatorTest {

    // test for two trees which are null
    @Test
    public void calculateSimilarityInvalidTwoTrees(){
        NodeTypeBasedComparator comparator = new NodeTypeBasedComparator();
        assertEquals(comparator.calculateSimilarity(null, null), 0.0f, 0.0002);
    }

    // test if on tree to compared is null
    @Test
    public void calculateSimilarityInvalidTree1(){
        NodeTypeBasedComparator comparator = new NodeTypeBasedComparator();
        CompilationUnit cu = new CompilationUnit();
        ClassOrInterfaceDeclaration c = new ClassOrInterfaceDeclaration();
        c.setParentNode(cu);
        VariableDeclarator v = new VariableDeclarator();
        v.setParentNode(cu);
        assertEquals(comparator.calculateSimilarity(null, cu), 0.0f, 0.0002);
    }

    // test if on tree to compared is null
    @Test
    public void calculateSimilarityInvalidTree2(){
        NodeTypeBasedComparator comparator = new NodeTypeBasedComparator();
        CompilationUnit cu = new CompilationUnit();
        ClassOrInterfaceDeclaration c = new ClassOrInterfaceDeclaration();
        c.setParentNode(cu);
        VariableDeclarator v = new VariableDeclarator();
        v.setParentNode(cu);

        assertEquals(comparator.calculateSimilarity(cu, null), 0.0f, 0.0002);
    }

    // test if the trees are the same
    @Test
    public void calculateSimilaritySameTree(){
        ConcreteParser parser = new ConcreteParser();
        CompilationUnit cu = parser.generateAST("public class JavaTest{int a;}");

        NodeTypeBasedComparator comparator = new NodeTypeBasedComparator();
        assertEquals(comparator.calculateSimilarity(cu, cu), 1.0f, 0.0002);
    }

    // test if the trees are the same, but the node type interested has 0 count
    @Test
    public void calculateSimilaritySameTreeZeroCount(){
        ConcreteParser parser = new ConcreteParser();
        CompilationUnit cu = parser.generateAST("package node; \n public class JavaTest{}");

        NodeTypeBasedComparator comparator = new NodeTypeBasedComparator();
        comparator.calculateSimilarity(cu, cu);
        assertEquals(comparator.calculateSimilarity(cu, cu), 0.0f, 0.0002);
    }

    // test if the trees are the same, complicated content
    @Test
    public void calculateSimilaritySameTree1() throws IOException{
        String filePath = "src/test/resources/PlagiarismChecker-Data/set01/Sample1/LinkedList.java";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        br.close();

        ConcreteParser parser = new ConcreteParser();
        CompilationUnit cu = parser.generateAST(sb.toString());

        NodeTypeBasedComparator comparator = new NodeTypeBasedComparator();
        comparator.calculateSimilarity(cu, cu);
        assertEquals(comparator.calculateSimilarity(cu, cu), 1.0f, 0.0002);
    }

    // test if the trees are different
    @Test
    public void calculateSimilarityDifferentTree(){
        ConcreteParser parser = new ConcreteParser();

        // 1 Block, 1 Exp, 1 Assgn, 1 Var, 1 Param
        String code1 = "package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v;}}";
        CompilationUnit t1 = parser.generateAST(code1);

        // 1 Block, 1 Param
        String code2 = "public interface Variable{ \n" +
                "public Variable(String v){}; }\n" ;
        CompilationUnit t2 = parser.generateAST(code2);
        NodeTypeBasedComparator comparator = new NodeTypeBasedComparator();
        assertEquals(comparator.calculateSimilarity(t1, t2), 2.0/5.0, 0.0002);
    }

    // test if the trees have same type of nodes
    @Test
    public void calculateSimilarityDifferentTreeSameType(){
        ConcreteParser parser = new ConcreteParser();

        String code1 = "package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v;}}";
        CompilationUnit t1 = parser.generateAST(code1);

        String code2 = "public class Variable{ public String v;}\n" ;
        CompilationUnit t2 = parser.generateAST(code2);

        NodeTypeBasedComparator comparator = new NodeTypeBasedComparator();
        assertEquals(comparator.calculateSimilarity(t1, t2), 0.2f, 0.0002);
    }

    // test if the trees are different, and one tree has 0 counts for all types of nodes interested
    @Test
    public void calculateSimilarityDifferentTree1(){
        ConcreteParser parser = new ConcreteParser();

        // 1 Block, 1 Exp, 1 Assgn, 1 Var, 1 Param
        String code1 = "package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v;}}";
        CompilationUnit t1 = parser.generateAST(code1);

        String code2 = "package node; \n public interface Variable{ }\n" ;
        CompilationUnit t2 = parser.generateAST(code2);
        NodeTypeBasedComparator comparator = new NodeTypeBasedComparator();
        assertEquals(comparator.calculateSimilarity(t1, t2), 0.0f , 0.0002);
    }

    // test if the trees are different, and one tree has 0 counts for all types of nodes interested
    @Test
    public void calculateSimilarityDifferentTree2(){
        ConcreteParser parser = new ConcreteParser();

        // 1 Block, 1 Exp, 1 Assgn, 1 Var, 1 Param
        String code1 = "package astNode;\n" +
                "public class Variable{ \n" +
                "private String variable; \n" +
                "public Variable(String v){ \n" +
                "variable = v;}}";
        CompilationUnit t1 = parser.generateAST(code1);

        String code2 = "package node; \n public interface Variable{ }\n" ;
        CompilationUnit t2 = parser.generateAST(code2);
        NodeTypeBasedComparator comparator = new NodeTypeBasedComparator();
        assertEquals(comparator.calculateSimilarity(t2, t1), 0.0f , 0.0002);
    }

}
