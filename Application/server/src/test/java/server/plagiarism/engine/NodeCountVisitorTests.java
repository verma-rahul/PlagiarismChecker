package server.plagiarism.engine;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.junit.Test;
import server.plagiarism.engine.parsetreebased.NodeCountVisitor;
import server.plagiarism.engine.parsetreebased.SyntaxTreeCountVisitor;
import server.plagiarism.engine.parsetreebased.nodes.NumberExpression;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
* @author Rahul Verma [verma.rah@husky.neu.edu]
 *
 */


// Test Class for NodeCountVisitor
public class NodeCountVisitorTests {

    // Testing IntegerLiteralExpr
    @Test
    public void Test1() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer integerLiteralExpr = nodeCountVisitor.getHashMap().get("IntegerLiteralExpr");
            assertTrue(integerLiteralExpr == 3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    // Testing VariableDeclarationExpr
    @Test
    public void Test2() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            methodNames.add(0, new NumberExpression<>(0, 0));
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer variableDeclarationExpr = nodeCountVisitor.getHashMap().get("VariableDeclarationExpr");
            assertTrue(variableDeclarationExpr == 4);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing MethodDeclaration
    @Test
    public void Test3() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            methodNames.add(0, new NumberExpression<>(0, 0));
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer methodDeclaration = nodeCountVisitor.getHashMap().get("MethodDeclaration");
            assertTrue(methodDeclaration == 4);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing MethodDeclaration
    @Test
    public void Test4() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer binaryExpr = nodeCountVisitor.getHashMap().get("BinaryExpr");
            assertTrue(binaryExpr == 4);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing BlockStmt
    @Test
    public void Test5() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer blockStmt = nodeCountVisitor.getHashMap().get("BlockStmt");
            assertTrue(blockStmt == 5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing StringLiteralExpr
    @Test
    public void Test6() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer stringLiteralExpr = nodeCountVisitor.getHashMap().get("StringLiteralExpr");
            assertTrue(stringLiteralExpr == 5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    // Testing ExpressionStmt
    @Test
    public void Test7() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer expressionStmt = nodeCountVisitor.getHashMap().get("ExpressionStmt");
            assertTrue(expressionStmt == 20);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing AssignExpr
    @Test
    public void Test8() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer assignExpr = nodeCountVisitor.getHashMap().get("AssignExpr");
            assertTrue(assignExpr == 10);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing VariableDeclarator
    @Test
    public void Test9() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer variableDeclarator = nodeCountVisitor.getHashMap().get("VariableDeclarator");
            assertTrue(variableDeclarator == 6);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing Parameter
    @Test
    public void Test10() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer parameter = nodeCountVisitor.getHashMap().get("Parameter");
            assertTrue(parameter == 3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing MethodCallExpr
    @Test
    public void Test11() {

        String FILE_PATH = "src//test//resources//ReversePolishNotation.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer methodCallExpr = nodeCountVisitor.getHashMap().get("MethodCallExpr");
            assertTrue(methodCallExpr == 18);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing UnaryExpr
    @Test
    public void Test12() {

        String FILE_PATH = "src//test//resources//HeapSorter.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer unaryExpr = nodeCountVisitor.getHashMap().get("UnaryExpr");
            assertTrue(unaryExpr == 3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing IfStmt
    @Test
    public void Test13() {

        String FILE_PATH = "src//test//resources//HeapSorter.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer ifStmt = nodeCountVisitor.getHashMap().get("IfStmt");
            assertTrue(ifStmt == 6);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing ForStmt
    @Test
    public void Test14() {

        String FILE_PATH = "src//test//resources//HeapSorter.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer ForStmt = nodeCountVisitor.getHashMap().get("ForStmt");
            assertTrue(ForStmt == 2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing WhileStmt
    @Test
    public void Test15() {

        String FILE_PATH = "src//test//resources//QuickSorter.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer whileStmt = nodeCountVisitor.getHashMap().get("WhileStmt");
            assertTrue(whileStmt == 3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing ConditionalExpr
    @Test
    public void Test16() {

        String FILE_PATH = "src//test//resources//Sample.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer conditionalExpr = nodeCountVisitor.getHashMap().get("ConditionalExpr");
            assertTrue(conditionalExpr == 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing ForeachStmt
    @Test
    public void Test17() {

        String FILE_PATH = "src//test//resources//Sample.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer foreachStmt = nodeCountVisitor.getHashMap().get("ForeachStmt");
            assertTrue(foreachStmt == 0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    // Testing DoubleLiteralExpr
    @Test
    public void Test18() {

        String FILE_PATH = "src//test//resources//Sample.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer doubleLiteralExpr = nodeCountVisitor.getHashMap().get("DoubleLiteralExpr");
            assertTrue(doubleLiteralExpr == 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing BooleanLiteralExpr
    @Test
    public void Test19() {

        String FILE_PATH = "src//test//resources//Sample.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer BooleanLiteralExpr = nodeCountVisitor.getHashMap().get("BooleanLiteralExpr");
            assertTrue(BooleanLiteralExpr == 2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    // Testing LongLiteralExpr
    @Test
    public void Test21() {

        String FILE_PATH = "src//test//resources//Sample.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer longLiteralExpr = nodeCountVisitor.getHashMap().get("LongLiteralExpr");
            assertTrue(longLiteralExpr == 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    // Testing AssignExpr
    @Test
    public void Test22() {

        String FILE_PATH = "src//test//resources//Sample2.java";

        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(new FileInputStream(FILE_PATH));
            List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
            methodNames.add(0, new NumberExpression<>(0, 0));
            NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
            nodeCountVisitor.visit(cu, methodNames);
            SyntaxTreeCountVisitor stc = new SyntaxTreeCountVisitor(new HashMap<>());
            nodeCountVisitor.getClassDeclaration().accept(stc);
            Integer longLiteralExpr = nodeCountVisitor.getHashMap().get("LongLiteralExpr");
            assertTrue(longLiteralExpr == 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Testing Compliation Unit
    @Test
    public void Test23() {

        CompilationUnit cu = new CompilationUnit();
        NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
        methodNames.add(0, new NumberExpression<>(0, 0));
        nodeCountVisitor.visit(cu, methodNames);
        assertTrue(nodeCountVisitor.getHashMap().get("LongLiteralExpr")==0);

    }

    // testing of Method Function declaration
    @Test
    public void test24() {
        MethodDeclaration methodDeclaration = new MethodDeclaration();
        NodeCountVisitor nodeCountVisitor = new NodeCountVisitor();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> methodNames = new ArrayList<>();
        methodNames.add(0, new NumberExpression<>(0, 0));
        nodeCountVisitor.visit(methodDeclaration, methodNames);
        assertTrue(nodeCountVisitor.getHashMap().get("LongLiteralExpr")==0);
    }

}
