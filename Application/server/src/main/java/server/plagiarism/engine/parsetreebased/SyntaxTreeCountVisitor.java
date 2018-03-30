package server.plagiarism.engine.parsetreebased;


import server.plagiarism.engine.parsetreebased.nodes.*;

import java.util.HashMap;
import java.util.Map;

/**
 * A visitor that returns counts of our type of Nodes defined in nodes Folder, and has a default implementation
 * for all its visit methods that simply visit their children in an unspecified order.
 *
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class SyntaxTreeCountVisitor implements ASTVisitor {

    private final Map<String, Integer> counts;

    public SyntaxTreeCountVisitor(Map<String, Integer> counts) {
        this.counts = new HashMap<>();
    }

    /*        * Visits Assignments*/
    public void visit(Assignment assn) {
        updateHashMap(assn);
    }


    /*
    * Visits  VarDeclaration Declaration*/
    public void visit(VarDeclaration decl) {
        updateHashMap(decl);
    }


    /*
    * Visits BinaryOperation*/
    public void visit(BinaryOperation bo) {
        updateHashMap(bo);
    }


    /*
    * Visits Unary Operation*/
    public void visit(UnaryOperation preFix) {
        updateHashMap(preFix);
    }


    /*
    * Visits NumberExpression*/
    public void visit(NumberExpression numExp) {
        updateHashMap(numExp);
    }


    /*
    * Visits StringExpression*/
    public void visit(StringExpression strExp) {
        updateHashMap(strExp);
    }

    /*
    * Visits VariableExpression*/
    public void visit(VariableExpression varExp) {
        updateHashMap(varExp);
    }


    /*
    * Visits ClassDeclaration*/
    public void visit(ClassDeclaration cls) {
        updateHashMap(cls);
    }


    /*
    * Visits FunctionDeclaration*/
    public void visit(FunctionDeclaration strExp) {
        updateHashMap(strExp);
    }

    /*
    * Visits FunctionCall*/
    public void visit(FunctionCall functionCall) {
        updateHashMap(functionCall);
    }

    /*
    * Visits If*/
    public void visit(If seq) {
        updateHashMap(seq);
    }

    /*
     * Visits While*/
    public void visit(While varExp) {
        updateHashMap(varExp);
    }


    /* Visits For*/
    public void visit(For varExp) {
        updateHashMap(varExp);
    }

    @Override
    public void visit(BooleanExpression booleanExpression) {
        updateHashMap(booleanExpression);
    }

    @Override
    public void visit(BlockStatement blockStatement) {
        updateHashMap(blockStatement);
    }

    @Override
    public void visit(ConditionalExpression conditionalExpression) {
        updateHashMap(conditionalExpression);
    }

    @Override
    public void visit(ExpressionStatement expressionStatement) {
        updateHashMap(expressionStatement);
    }


    public int getCount(String nodeName) {
        return this.counts.getOrDefault(nodeName, 0);

    }

    private void updateHashMap(Node n) {
        String simpleName = n.getClass().getSimpleName();
        Integer integer = counts.getOrDefault(simpleName, 0);
        counts.put(simpleName, ++integer);
    }
}
