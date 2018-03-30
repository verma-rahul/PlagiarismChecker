package server.plagiarism.engine.parsetreebased;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import server.plagiarism.engine.parsetreebased.nodes.Class;
import server.plagiarism.engine.parsetreebased.nodes.ClassDeclaration;
import server.plagiarism.engine.parsetreebased.nodes.NumberExpression;
import server.plagiarism.engine.parsetreebased.nodes.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * A visitor that returns nothing, and has a default implementation for all its visit
 * methods that simply visit their children in an unspecified order.
 *
 */

public class NodeCountVisitor extends VoidVisitorAdapter<List<server.plagiarism.engine.parsetreebased.nodes.Node>> {


    private HashMap<String, Integer> nodeTypeCounts; // HashMap for storing nodeCounts

    private CustomNodeFactory customNodeFactory; // Factory instance for creating nodes

    private ClassDeclaration classDeclaration; // Starting point for our AST

    //  Constructor for NodeCountVisitor

    public NodeCountVisitor() {
        super();

        customNodeFactory = CustomNodeFactory.create();

        this.nodeTypeCounts = new HashMap<>();

        this.nodeTypeCounts.put("AssignExpr", 0);

        this.nodeTypeCounts.put("BinaryExpr", 0);

        this.nodeTypeCounts.put("BlockStmt", 0);

        this.nodeTypeCounts.put("BooleanLiteralExpr", 0);

        this.nodeTypeCounts.put("ConditionalExpr", 0);

        this.nodeTypeCounts.put("DoubleLiteralExpr", 0);

        this.nodeTypeCounts.put("ExpressionStmt", 0);

        this.nodeTypeCounts.put("ForStmt", 0);

        this.nodeTypeCounts.put("ForeachStmt", 0);

        this.nodeTypeCounts.put("IfStmt", 0);

        this.nodeTypeCounts.put("IntegerLiteralExpr", 0);

        this.nodeTypeCounts.put("UnaryExpr", 0);

        this.nodeTypeCounts.put("LongLiteralExpr", 0);

        this.nodeTypeCounts.put("MethodCallExpr", 0);

        this.nodeTypeCounts.put("MethodDeclaration", 0);

        this.nodeTypeCounts.put("Parameter", 0);

        this.nodeTypeCounts.put("StringLiteralExpr", 0);

        this.nodeTypeCounts.put("VariableDeclarationExpr", 0);

        this.nodeTypeCounts.put("VariableDeclarator", 0);

        this.nodeTypeCounts.put("WhileStmt", 0);

    }

    /**
     * Updates the node count for type of Node n
     *
     * @param n Given Node
     */

    private void updateHashMap(Node n) {
        String simpleName = n.getClass().getSimpleName();
        Integer integer = nodeTypeCounts.get(simpleName);
        nodeTypeCounts.put(simpleName, ++integer);
    }

    /**
     * @return the HashMap Containing the values of node counts
     */
    public HashMap<String, Integer> getHashMap() {
        return this.nodeTypeCounts;
    }

    /**
     * To get the entry point of our AST
     *
     * @return the ClassDecalartion representing root node of Our AST
     */
    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }

    // Visitor Method for AssignExpr Statement

    @Override
    public void visit(AssignExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;
//        if (level == 1)
//            collector.add(customNodeFactory.makeAssignment(new Variable(n.getTarget().toString()), n.getValue()));
        updateHashMap(n);
    }

    // Visitor Method for BinaryExpr Statement

    @Override
    public void visit(BinaryExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;

//        if (level == 1)
//            collector.add(customNodeFactory.makeBinaryOperation(n.getOperator(), n.getLeft(), n.getRight()));
        updateHashMap(n);
    }

    // Visitor Method for BlockStmt Statement

    @Override
    public void visit(BlockStmt n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);

        if (level == 1)
            collector.add(customNodeFactory.makeBlockStatement(n));
        updateHashMap(n);
    }

    // Visitor Method for BooleanLiteralExpr Statement

    @Override
    public void visit(BooleanLiteralExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);

//        if (level == 1)
//            collector.add(customNodeFactory.makeBooleanExpression(n.getValue()));
        updateHashMap(n);
    }

    // Visitor Method for ConditionalExpr Statement

    @Override
    public void visit(ConditionalExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);

//        if (level == 1)
//            collector.add(customNodeFactory.makeConditional(n.getCondition(), n.getThenExpr(), n.getElseExpr()));
        updateHashMap(n);
    }

    // Visitor Method for Compilation Unit

    @Override
    public void visit(CompilationUnit n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);

        String className = "";

        if (n.getTypes().size() >= 1)
            className = n.getType(0).getName().toString();

        collect.remove(0);
        classDeclaration = customNodeFactory.makeClassDeclaration(new Class(className), collect);

    }

    // Visitor Method for DoubleLiteralExpr Statement

    @Override
    public void visit(DoubleLiteralExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;

//        if (level == 1)
//            collector.add(customNodeFactory.makeNumberExpression(n.asDouble()));
        updateHashMap(n);
    }

    // Visitor Method for ExpressionStmt Statement

    @Override
    public void visit(ExpressionStmt n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        super.visit(n, collector);
        updateHashMap(n);
    }

    // Visitor Method for ForStmt Statement

    @Override
    public void visit(ForStmt n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;

//        if (level == 1)
//            collector.add(customNodeFactory.makeFor(n.getCompare(), n.getInitialization(), n.getUpdate(), n.getBody()));
        updateHashMap(n);
    }

    // Visitor Method for IfStmt Statement

    @Override
    public void visit(IfStmt n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;

//        if (level == 1)
//            collector.add(customNodeFactory.makeIf(n.getCondition(), n.getThenStmt(), n.getElseStmt()));
        updateHashMap(n);
    }

    // Visitor Method for IntegerLiteralExpr

    @Override
    public void visit(IntegerLiteralExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;

//        if (level == 1)
//            collector.add(customNodeFactory.makeNumberExpression(n.asInt()));
        updateHashMap(n);
    }

    // Visitor Method for LongLiteralExpr

    @Override
    public void visit(LongLiteralExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;

//        if (level == 1)
//            collector.add(customNodeFactory.makeNumberExpression(n.asLong()));
        updateHashMap(n);
    }

    // Visitor Method for MethodCallExpr Statement

    @Override
    public void visit(MethodCallExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
//        if (level == 1)
//            collector.add(customNodeFactory.makeFunctionCall(n.getScope(), n.getName(), n.getArguments()));
        updateHashMap(n);
    }

    // Visitor Method for MethodDeclaration

    @Override
    public void visit(MethodDeclaration n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;

        if (level == 1)
            collector.add(customNodeFactory.makeFunctionDeclaration(n.getName().asString(), n.getParameters(), n.getType(), n.getBody()));
        updateHashMap(n);
    }

    // Visitor Method for Parameter

    @Override
    public void visit(Parameter n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        super.visit(n, collector);
        updateHashMap(n);
    }

    // Visitor Method for StringLiteralExpr

    @Override
    public void visit(StringLiteralExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        updateHashMap(n);
    }

    // Visitor Method for UnaryExpr

    @Override
    public void visit(UnaryExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;

//        if (level == 1)
//            collector.add(customNodeFactory.makeUnaryOperation(n.getOperator(), n.getExpression()));
        updateHashMap(n);
    }

    // Visitor Method for VariableDeclarationExpr

    @Override
    public void visit(VariableDeclarationExpr n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();

        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;

//        if (level == 1)
//            collector.add(customNodeFactory.makeVariableExpression(new Variable(n.asVariableDeclarationExpr().toString())));
        updateHashMap(n);
    }

    // Visitor Method for VariableDeclarator Statement

    @Override
    public void visit(VariableDeclarator n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;
        if (level == 1)
            collector.add(customNodeFactory.makeVarDeclaration(new Variable(n.getName().asString())));
        updateHashMap(n);
    }

    // Visitor Method for WhileStmt Statement

    @Override
    public void visit(WhileStmt n, List<server.plagiarism.engine.parsetreebased.nodes.Node> collector) {
        NumberExpression numberExpression = (NumberExpression) collector.get(0);
        int level = (int) numberExpression.getNum();
        List<server.plagiarism.engine.parsetreebased.nodes.Node> collect = collector.stream().collect(Collectors.toList());
        collect.set(0, new NumberExpression<>(level + 1, 0));
        super.visit(n, collect);
        ;

//        if (level == 1)
//            collector.add(customNodeFactory.makeWhile(n.getCondition(), n.getBody()));
        updateHashMap(n);
    }


}
