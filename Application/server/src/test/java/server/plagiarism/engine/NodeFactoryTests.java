package server.plagiarism.engine;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import server.plagiarism.engine.parsetreebased.ASTVisitor;
import server.plagiarism.engine.parsetreebased.CustomNodeFactory;
import server.plagiarism.engine.parsetreebased.SyntaxTreeCountVisitor;
import server.plagiarism.engine.parsetreebased.nodes.*;
import server.plagiarism.engine.parsetreebased.nodes.Class;

import java.util.*;

import static com.github.javaparser.ast.type.PrimitiveType.doubleType;
import static com.github.javaparser.ast.type.PrimitiveType.intType;
import static org.junit.Assert.assertEquals;

/**
* @author Rahul Verma [verma.rah@husky.neu.edu]
 */
@FixMethodOrder(MethodSorters.JVM)
public class NodeFactoryTests {

    // Make Variable Expression
    private CustomNodeFactory customNodeFactory;

    @Before
    public void beforeTest() {
        Node.reset();
        customNodeFactory = CustomNodeFactory.create();

    }

    @After
    public void afterTest() {
        Node.reset();
        customNodeFactory = null;

    }
    // Testing Assignment

    @Test
    public void test1() {

        Assignment assignment = customNodeFactory.makeAssignment(new Variable("x"), new BinaryExpr(new StringLiteralExpr("y"),
                new StringLiteralExpr("z"),
                BinaryExpr.Operator.PLUS));

        assertEquals(assignment.getChilrenNodes().size(), 1);
        assertEquals(assignment.getClassName(), "Assignment");
        assertEquals(assignment.textualRepresentation(), "x = \"\"y\"\"+\"\"z\"\"");
        ASTVisitor astVisitor  = new SyntaxTreeCountVisitor(new HashMap<>());
        assignment.accept(astVisitor);
        assertEquals(assignment.getId(), 3);
    }

    // Testing Binary Operation

    @Test
    public void test2() {

        BinaryOperation binaryOperation = customNodeFactory.makeBinaryOperation(BinaryExpr.Operator.PLUS, new StringLiteralExpr("x"), new StringLiteralExpr("y"));
        assertEquals(binaryOperation.getChilrenNodes().size(), 2);
        assertEquals(binaryOperation.getClassName(), "BinaryOperation");
        assertEquals(binaryOperation.getId(), 2);
        assertEquals(binaryOperation.textualRepresentation(), "\"\"x\"\"+\"\"y\"\"");


    }

    // Testing Block Statement

    @Test
    public void test3() {
        BlockStmt blockStmt = new BlockStmt();
        Expression exp = new NameExpr("x");
        MethodCallExpr expression = new MethodCallExpr(exp, "y");

        blockStmt.addStatement(expression);
        blockStmt.addStatement(expression.clone());
        BlockStatement n = customNodeFactory.makeBlockStatement(blockStmt);
        assertEquals(n.getChilrenNodes().size(),2);
        assertEquals(n.getClassName(),"BlockStatement");
        assertEquals(n.getId(),4);
        assertEquals(n.textualRepresentation(),"\"\n" +
                "\"Function call: y\"\n" +
                "\"Function call: y\"\"");

    }

    // Testing Boolean Expression
    @Test
    public void test4() {
        BooleanExpression n = customNodeFactory.makeBooleanExpression(true);
        assertEquals(n.getChilrenNodes().size(), 0);
        assertEquals(n.getClassName(), "BooleanExpression");
        assertEquals(n.getId(), 0);
        ASTVisitor astVisitor  = new SyntaxTreeCountVisitor(new HashMap<>());
        n.accept(astVisitor);
        assertEquals(n.textualRepresentation(), "true");
    }

    // Testing Class

    @Test
    public void test5() {
        server.plagiarism.engine.parsetreebased.nodes.Class n = new server.plagiarism.engine.parsetreebased.nodes.Class("myNode");
        assertEquals(n.getChilrenNodes().size(), 0);
        assertEquals(n.getClassName(), "Class");
        assertEquals(n.getName(), "myNode");
    }

    // Testing Class Declaration
    @Test
    public void test6() {
        Node stringExp = (Node) customNodeFactory.makeStringExpression("x");

        ClassDeclaration n = customNodeFactory.makeClassDeclaration(new Class("myNode"), new ArrayList<Node>(Arrays.asList(stringExp)));
        assertEquals(n.getChilrenNodes().size(), 1);
        assertEquals(n.getClassName(), "ClassDeclaration");
        assertEquals(n.getId(), 1);
        assertEquals(n.textualRepresentation(), "cls myNode");
    }


    // Testing Conditional Expression
    @Test
    public void test7() {

        ConditionalExpression n = customNodeFactory.makeConditional(new StringLiteralExpr("x>y"), new StringLiteralExpr("x"), new StringLiteralExpr("y"));
        assertEquals(n.getChilrenNodes().size(), 3);
        assertEquals(n.getClassName(), "ConditionalExpression");
        assertEquals(n.getId(), 3);
        SyntaxTreeCountVisitor astVisitor  = new SyntaxTreeCountVisitor(new HashMap<>());
        n.accept(astVisitor);
        assertEquals(n.textualRepresentation(), "If \"\"x>y\"\"");
        assertEquals(astVisitor.getCount("ConditionalExpression"),1);

    }

    // Testing Expression Statement
    @Test
    public void test8() {

        ExpressionStatement n = customNodeFactory.makeExpressionStatement(new StringLiteralExpr("x"));
        assertEquals(n.getChilrenNodes().size(), 1);
        assertEquals(n.getClassName(), "ExpressionStatement");
        assertEquals(n.getId(), 1);
        assertEquals(n.textualRepresentation(), "\"\"\"x\"\"\"");
    }

    // Testing For Statement
    @Test
    public void test9() {
        NodeList<Expression> expressions = new NodeList<>();
        expressions.add(new StringLiteralExpr("x"));
        For n = customNodeFactory.makeFor(Optional.of(new StringLiteralExpr("x>y")), expressions, expressions, new ExpressionStmt(new StringLiteralExpr("x")));
        assertEquals(n.getChilrenNodes().size(), 4);
        assertEquals(n.getClassName(), "For");
        assertEquals(n.getId(), 5);
        assertEquals(n.textualRepresentation(), "For : \"\"x>y\"\"");
    }


    // Testing for Function

    @Test
    public void test10() {
        Function n = new Function("myMethod");
        assertEquals(n.getName(), "myMethod");
    }

    // Testing Function Call

    @Test
    public void test11() {
        NodeList<Expression> expressions = new NodeList<>();
        expressions.add(new StringLiteralExpr("x"));
        FunctionCall n = customNodeFactory.makeFunctionCall(Optional.of(new StringLiteralExpr("x>y")), new SimpleName("func"), expressions);
        assertEquals(n.getChilrenNodes().size(), 1);
        assertEquals(n.getClassName(), "FunctionCall");
        assertEquals(n.getId(), 1);
        assertEquals(n.textualRepresentation(), "Function call: func");
    }

    // Testing Function Declaration

    @Test
    public void test12() {
        Parameter p = new Parameter(intType(), "3");
        // Taken from JavaParser docs
        BlockStmt blockStmt = new BlockStmt();
        Expression exp = new NameExpr("x");
        MethodCallExpr expression = new MethodCallExpr(exp, "y");

        blockStmt.addStatement(expression);
        blockStmt.addStatement(expression.clone());
        FunctionDeclaration n = customNodeFactory.makeFunctionDeclaration("myNode", new NodeList<Parameter>(p), doubleType(), Optional.of(blockStmt));
        assertEquals(n.getChilrenNodes().size(), 2);
        assertEquals(n.getClassName(), "FunctionDeclaration");
        assertEquals(n.getId(), 4);
        assertEquals(n.textualRepresentation(), "Function myNode");
    }

    // Testing If Node

    @Test
    public void test13() {

        If n = customNodeFactory.makeIf(new StringLiteralExpr("x>y"), new ExpressionStmt(new StringLiteralExpr("x")), Optional.of(new ExpressionStmt(new StringLiteralExpr("x"))));


        assertEquals(n.getChilrenNodes().size(), 3);
        assertEquals(n.getClassName(), "If");
        assertEquals(n.getId(), 5);
        assertEquals(n.textualRepresentation(), "If \"\"x>y\"\"");
    }

    // Testing NumberExpression

    @Test
    public void test14() {
        NumberExpression n = customNodeFactory.makeNumberExpression(5);
        assertEquals(n.getClassName(), "NumberExpression");
        assertEquals(n.getId(), 0);
        assertEquals(n.textualRepresentation(), "5");
        assertEquals(n.getChilrenNodes().size(),0);

    }

    // Testing Operator

    @Test
    public void test15() {
        Operator n = new Operator("+");
        assertEquals(n.getOperator(), "+");


    }

    // Testing StringExpression

    @Test
    public void test16() {
        StringExpression n = customNodeFactory.makeStringExpression("suzy");
        assertEquals(n.getClassName(), "StringExpression");
        assertEquals(n.getId(), 0);
        assertEquals(n.textualRepresentation(), "\"suzy\"");
        assertEquals(n.getChilrenNodes().size(),0);
    }

    // Testing Unary Operation

    @Test
    public void test17() {

        UnaryOperation n = customNodeFactory.makeUnaryOperation(UnaryExpr.Operator.PLUS, new StringLiteralExpr("x"));
        assertEquals(n.getChilrenNodes().size(), 1);
        assertEquals(n.getClassName(), "UnaryOperation");
        assertEquals(n.getId(), 1);
        assertEquals(n.textualRepresentation(), "\"\"x\"\"+");


    }

    // Testing VarDeclaration

    @Test
    public void test18(){
        VarDeclaration n = customNodeFactory.makeVarDeclaration(new Variable("x"));
        assertEquals(n.getChilrenNodes().size(), 0);
        assertEquals(n.getClassName(), "VarDeclaration");
        assertEquals(n.getId(), 0);
        assertEquals(n.textualRepresentation(), "var x");
    }

    // Testing Variable

    @Test
    public void test19(){
        Variable n = new Variable("x");
        assertEquals(n.getName(),"x");
    }

    // Testing VariableExpression
    @Test
    public void test20(){
        VariableExpression n = customNodeFactory.makeVariableExpression(new Variable("x"));
        assertEquals(n.getChilrenNodes().size(), 0);
        assertEquals(n.getClassName(), "VariableExpression");
        assertEquals(n.getId(), 0);
        ASTVisitor astVisitor  = new SyntaxTreeCountVisitor(new HashMap<>());
        n.accept(astVisitor);
        assertEquals(n.textualRepresentation(), "x");
    }

    // Testing while thing

    @Test
    public void test21(){
        While n = customNodeFactory.makeWhile(new StringLiteralExpr("i>=0"), new ExpressionStmt(new StringLiteralExpr("x")));
        assertEquals(n.getChilrenNodes().size(), 2);
        assertEquals(n.getClassName(), "While");
        assertEquals(n.getId(), 3);
        assertEquals(n.textualRepresentation(), "while(\"\"i>=0\"\"){\n" +
                "\"\"\"x\"\"\"}");
        assertEquals(Node.getNode(3).getClass().getSimpleName(),"While");
    }

    @Test(expected = NoSuchElementException.class)
    public void test22() {
        While n = customNodeFactory.makeWhile(new StringLiteralExpr("i>=0"), new ExpressionStmt(new StringLiteralExpr("x")));
        assertEquals(n.getChilrenNodes().size(), 2);
        assertEquals(n.getClassName(), "While");
        assertEquals(n.getId(), 3);
        assertEquals(n.textualRepresentation(), "while(\"\"i>=0\"\"){\n" +
                "\"\"\"x\"\"\"}");
        assertEquals(Node.getNode(-1), n);

    }

    // Testing Node Factory
    @Test
    public void test23(){

        server.plagiarism.engine.parsetreebased.nodes.Expression expression = customNodeFactory.makeExpression(new BooleanLiteralExpr());
        server.plagiarism.engine.parsetreebased.nodes.Expression expression2 = customNodeFactory.makeExpression(new ConditionalExpr());
        server.plagiarism.engine.parsetreebased.nodes.Expression expression3 = customNodeFactory.makeExpression(new DoubleLiteralExpr(1.0));
        server.plagiarism.engine.parsetreebased.nodes.Expression expression4= customNodeFactory.makeExpression(new LongLiteralExpr(1L));
        server.plagiarism.engine.parsetreebased.nodes.Expression expression5 = customNodeFactory.makeExpression(null);

        server.plagiarism.engine.parsetreebased.nodes.Expression expression6 = customNodeFactory.makeExpression(new MethodCallExpr());

        assertEquals(expression.getClass().getSimpleName(), "BooleanExpression");
        assertEquals(expression2.getClass().getSimpleName(), "ConditionalExpression");
        assertEquals(expression3.getClass().getSimpleName(),"NumberExpression");
        assertEquals(expression4.getClass().getSimpleName(),"NumberExpression");
        assertEquals(expression5.getClass().getSimpleName(),"StringExpression");
        assertEquals(expression6.getClass().getSimpleName(),"FunctionCall");

    }

    // Testing For Statement
    @Test
    public void test24() {
        NodeList<Expression> expressions = new NodeList<>();
        expressions.add(new StringLiteralExpr("x"));
        For n = customNodeFactory.makeFor(Optional.empty(), expressions, expressions, new ExpressionStmt(new StringLiteralExpr("x")));
        assertEquals(n.getChilrenNodes().size(), 4);
        assertEquals(n.getClassName(), "For");
        assertEquals(n.getId(), 5);
        assertEquals(n.textualRepresentation(), "For : \"Empty Expression\"");
    }

    // testing of functional Function declaration
    @Test
    public void test25() {
        Parameter p = new Parameter(intType(), "3");

        FunctionDeclaration n = customNodeFactory.makeFunctionDeclaration("myNode", new NodeList<Parameter>(p), doubleType(), Optional.empty());
        assertEquals(n.getChilrenNodes().size(), 0);
        assertEquals(n.getClassName(), "FunctionDeclaration");
        assertEquals(n.getId(), 0);
        assertEquals(n.textualRepresentation(), "Function myNode");
    }



}
