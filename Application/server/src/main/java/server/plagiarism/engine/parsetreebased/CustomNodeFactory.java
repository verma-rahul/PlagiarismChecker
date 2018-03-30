package server.plagiarism.engine.parsetreebased;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
import server.plagiarism.engine.parsetreebased.nodes.*;
import server.plagiarism.engine.parsetreebased.nodes.Class;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * This class implements the AbstractNodeFactory which can used to create our own nodes.
 */
public class CustomNodeFactory implements AbstractNodeFactory {

    /**
     * Method for retrieving or creating the instance. Also initializes/resets the counters.
     *
     * @return static instance of CustomNodeFactory
     */
    public static CustomNodeFactory create() {
        if (instance == null) {
            instance = new CustomNodeFactory();
        }

        return instance;
    }

    // constructor private, so clients cannot call it directly
    private CustomNodeFactory() {
    }

    // field for storing the unique instance
    private static CustomNodeFactory instance;

    /**
     * factory method for creating an BinaryOperation node
     *
     * @param op   Operator for the BinaryOperation
     * @param exp1 Left hand side Expression
     * @param exp2 right hand side Expres
     */
    @Override
    public BinaryOperation makeBinaryOperation(BinaryExpr.Operator op, Expression exp1, Expression exp2) {
        server.plagiarism.engine.parsetreebased.nodes.Expression left = null;
        server.plagiarism.engine.parsetreebased.nodes.Expression right = null;


        left = makeExpression(exp1);
        right = makeExpression(exp2);

        BinaryOperation binaryOperation = new BinaryOperation(new Operator(op.asString()), left, right, Node.getCounter());
        Node.addNode(binaryOperation);
        return binaryOperation;
    }

    /**
     * factory method for creating an UnaryOperation node
     *
     * @param op   Operator for the given UnaryOperation
     * @param exp1 Expression part of the UnaryOperation
     */

    @Override
    public UnaryOperation makeUnaryOperation(UnaryExpr.Operator op, Expression exp1) {
        server.plagiarism.engine.parsetreebased.nodes.Expression first = null;

        first = makeExpression(exp1);
        UnaryOperation unaryOperation = new UnaryOperation(new Operator(op.asString()), first, Node.getCounter());
        Node.addNode(unaryOperation);
        return unaryOperation;
    }

    /**
     * factory method for creating a StringExpression node
     *
     * @param text String representing the StringExpression
     */
    @Override
    public StringExpression makeStringExpression(String text) {
        StringExpression stringExpression = new StringExpression(text, Node.getCounter());
        Node.addNode(stringExpression);
        return stringExpression;
    }

    /**
     * factory method for creating a VariableExpression node
     *
     * @param var is the Variable representing this Variable Expression
     */
    @Override
    public VariableExpression makeVariableExpression(Variable var) {
        VariableExpression variableExpression = new VariableExpression(var, Node.getCounter());
        Node.addNode(variableExpression);
        return variableExpression;
    }

    /**
     * factory method for creating a NumberExpression node
     *
     * @param num represents the number part of the NumberExpression
     */
    @Override
    public <N extends Number> NumberExpression makeNumberExpression(N num) {
        NumberExpression numberExpression = new NumberExpression(num, Node.getCounter());
        Node.addNode(numberExpression);
        return numberExpression;
    }

    /**
     * factory method for creating an Assignment node
     *
     * @param var represents the variable part of the Assignment
     * @param exp represents the expression part of the Assignment
     */
    @Override
    public Assignment makeAssignment(Variable var, Expression exp) {
        server.plagiarism.engine.parsetreebased.nodes.Expression exp1 = null;

        exp1 = makeExpression(exp);
        Assignment assignment = new Assignment(var, exp1, Node.getCounter());
        Node.addNode(assignment);
        return assignment;
    }

    /**
     * factory method for creating a Declaration node
     *
     * @param var is the Variable representing this Variable Declaration
     */
    @Override
    public VarDeclaration makeVarDeclaration(Variable var) {

        VarDeclaration varDeclaration = new VarDeclaration(var, Node.getCounter());
        Node.addNode(varDeclaration);
        return varDeclaration;

    }

    /**
     * factory method for creating a ClassDeclaration node
     *
     * @param cls  representing the class of this Class Declaration Node
     * @param body representing the body of the class
     */
    @Override
    public ClassDeclaration makeClassDeclaration(Class cls, List<Node> body) {
//        List<server.plagiarism.engine.parsetreebased.nodes.Node> nodes = new ArrayList<>();
//        for (com.github.javaparser.ast.Node s : body) {
//            if (s instanceof Statement)
//                nodes.add(makeStatement((Statement) s));
//            else if (s instanceof Expression)
//                nodes.add(makeExpression((Expression) s));
//
//        }


        ClassDeclaration classDeclaration = new ClassDeclaration(cls, body, Node.getCounter());
        Node.addNode(classDeclaration);
        return classDeclaration;
    }

    /**
     * factory method for creating a FunctionDeclaration node
     *
     * @param f    represents the Function part of the this Function Declaration
     * @param al   represents the arguments of the Function Declaration
     * @param re   represents the return Type of the Function Declaration
     * @param body represents the body of the Function Declaration
     */
    @Override
    public FunctionDeclaration makeFunctionDeclaration(String f, NodeList<Parameter> al, Type re, Optional<BlockStmt> body) {
        // To Do : Conversion
        List<server.plagiarism.engine.parsetreebased.nodes.Statement> argumentStatements = new ArrayList<>();
        List<server.plagiarism.engine.parsetreebased.nodes.Statement> statementsBlock = new ArrayList<>();

//        for (Parameter p : al) {
//            argumentStatements.add(makeBlockStatement(p.toString()));
//        }

        if (body.isPresent()) {

            NodeList<Statement> statements = body.get().getStatements();
            for (Statement st : statements) {
                statementsBlock.add(makeStatement(st));
            }
        }


        FunctionDeclaration functionDeclaration = new FunctionDeclaration(new Function(f), argumentStatements, new Class(re.asString()), statementsBlock, Node.getCounter());
        Node.addNode(functionDeclaration);
        return functionDeclaration;
    }

    /**
     * factory method for creating a If node
     *
     * @param exp  represents the condition of the If expression
     * @param then represents the then part of the If expression
     * @param els  represents the else part of the If expression
     */
    @Override
    public If makeIf(Expression exp, Statement then, Optional<Statement> els) {
        server.plagiarism.engine.parsetreebased.nodes.Statement statementEls;
        statementEls = els.isPresent() ? makeStatement(els.get()) : makeStatement(null);
        If anIf = new If(makeExpression(exp), makeStatement(then), statementEls, Node.getCounter());
        Node.addNode(anIf);
        return anIf;

    }

    /**
     * factory method for creating a While node
     *
     * @param test represents the test expression of the While Statement
     * @param body represents the body of the While Statement
     */
    @Override
    public While makeWhile(Expression test, Statement body) {
        While aWhile = new While(makeExpression(test), makeStatement(body), Node.getCounter());
        Node.addNode(aWhile);
        return aWhile;
    }

    /**
     * factory method for creating a For node
     *
     * @param test   represents the test expression of the While Statement
     * @param body   represents the body of the While Statement
     * @param intial represents the intial expression for the For Node
     * @param incrm  represent the increment expression for the For Node
     */
    @Override
    public For makeFor(Optional<Expression> test, NodeList<Expression> intial, NodeList<Expression> incrm, Statement body) {
        // To Do Conversion
        server.plagiarism.engine.parsetreebased.nodes.Expression testExp = null;
        List<server.plagiarism.engine.parsetreebased.nodes.Expression> intialList = new ArrayList<>();
        List<server.plagiarism.engine.parsetreebased.nodes.Expression> incrmList = new ArrayList<>();


        ;

        testExp = test.isPresent() ? makeExpression(test.get()) : makeExpression(null);

        for (Expression exp : intial) {
            intialList.add(makeExpression(exp));
        }

        for (Expression exp : incrm) {
            incrmList.add(makeExpression(exp));
        }


        For aFor = new For(testExp, makeStatement(body), intialList, incrmList, Node.getCounter());
        Node.addNode(aFor);
        return aFor;


    }

    /**
     * factory method for Conditional Expression
     * @param condition
     * @param  thenExpr
     * @param  elseExpr
     */
    @Override
    public ConditionalExpression makeConditional(Expression condition, Expression thenExpr, Expression elseExpr) {
        ConditionalExpression conditionalExpression = new ConditionalExpression(makeExpression(condition), makeExpression(thenExpr), makeExpression(elseExpr), Node.getCounter());
        Node.addNode(conditionalExpression);
        return conditionalExpression;
    }

    /**
     * factory method for creating Boolean Expression
     *
     * @param value the value of the Boolean Expression
     */
    @Override
    public BooleanExpression makeBooleanExpression(boolean value) {
        BooleanExpression booleanExpression = new BooleanExpression(value, Node.getCounter());
        Node.addNode(booleanExpression);
        return booleanExpression;
    }

    /**
     * factory method creating Function Call
     *
     * @param scope     scope of the Function call
     * @param name      name of the function being called
     * @param arguments arguments passed to the function call
     */
    @Override
    public FunctionCall makeFunctionCall(Optional<Expression> scope, SimpleName name, NodeList<Expression> arguments) {
        List<server.plagiarism.engine.parsetreebased.nodes.Expression> expressionArrayList = new ArrayList<>();
        for (Expression exp : arguments)
            expressionArrayList.add(makeExpression(exp));

        FunctionCall functionCall = new FunctionCall(name.asString(), expressionArrayList, Node.getCounter());
        Node.addNode(functionCall);
        return functionCall;
    }

    /**
     * factory method for creating the Block Statement
     *
     * @param s given statement need to converted to Block statement
     */

    @Override
    public BlockStatement makeBlockStatement(Statement s) {
        List<server.plagiarism.engine.parsetreebased.nodes.Statement> allStatements = new ArrayList<>();
        for (Statement statement : s.asBlockStmt().getStatements())
            allStatements.add(makeStatement(statement));
        BlockStatement blockStatement = new BlockStatement(allStatements, Node.getCounter());
        Node.addNode(blockStatement);
        return blockStatement;
    }

    /**
     * factory method for creating Expression Statement
     * @param expression expression part of the Expression Statement
     */
    @Override
    public ExpressionStatement makeExpressionStatement(Expression expression) {
        ExpressionStatement expressionStatement = new ExpressionStatement(makeExpression(expression), Node.getCounter());
        Node.addNode(expressionStatement);
        return expressionStatement;
    }

    /**
     * Helper methods to convert JavaParser node to Our Node convention
     *
     * @param exp1 JavaParser Expression
     * @return our AST expression
     */

    public server.plagiarism.engine.parsetreebased.nodes.Expression makeExpression(Expression exp1) {

        if (exp1 instanceof BinaryExpr) {
            BinaryExpr n = (BinaryExpr) exp1;
            return makeBinaryOperation(n.getOperator(), n.getLeft(), n.getRight());
        } else if (exp1 instanceof BooleanLiteralExpr) {
            BooleanLiteralExpr n = (BooleanLiteralExpr) exp1;
            return makeBooleanExpression(n.getValue());
        } else if (exp1 instanceof ConditionalExpr) {
            ConditionalExpr n = (ConditionalExpr) exp1;
            return makeConditional(n.getCondition(), n.getThenExpr(), n.getElseExpr());
        } else if (exp1 instanceof DoubleLiteralExpr) {
            DoubleLiteralExpr n = (DoubleLiteralExpr) exp1;
            return makeNumberExpression(n.asDouble());
        } else if (exp1 instanceof IntegerLiteralExpr) {
            IntegerLiteralExpr n = (IntegerLiteralExpr) exp1;
            return makeNumberExpression(n.asInt());
        } else if (exp1 instanceof LongLiteralExpr) {
            LongLiteralExpr n = (LongLiteralExpr) exp1;
            return makeNumberExpression(n.asLong());
        } else if (exp1 instanceof MethodCallExpr) {
            MethodCallExpr n = (MethodCallExpr) exp1;
            return makeFunctionCall(n.getScope(), n.getName(), n.getArguments());
        } else if (exp1 instanceof UnaryExpr) {
            UnaryExpr n = (UnaryExpr) exp1;
            return makeUnaryOperation(n.getOperator(), n.getExpression());
        } else {
            if (exp1 == null)
                return makeStringExpression("Empty Expression");
            return makeStringExpression(exp1.toString());
        }
    }

    /**
     * Helper methods to convert JavaParser node to Our Node convention
     *
     * @param statement JavaParser Expression
     * @return our AST expression
     */

    public server.plagiarism.engine.parsetreebased.nodes.Statement makeStatement(Statement statement) {

        if (statement instanceof ForStmt) {
            ForStmt n = statement.asForStmt();
            return makeFor(n.getCompare(), n.getInitialization(), n.getUpdate(), n.getBody());
        } else if (statement instanceof IfStmt) {
            IfStmt n = statement.asIfStmt();
            return makeIf(n.getCondition(), n.getThenStmt(), n.getElseStmt());
        } else if (statement instanceof WhileStmt) {
            WhileStmt n = statement.asWhileStmt();
            return makeWhile(n.getCondition(), n.getBody());
        } else if (statement instanceof ExpressionStmt) {
            ExpressionStmt expressionStmt = statement.asExpressionStmt();
            return makeExpressionStatement(expressionStmt.getExpression());
        } else if (statement instanceof BlockStmt) {
            BlockStmt blockStmt = statement.asBlockStmt();
//            NodeList<Statement> statements = blockStmt.getStatements();
//            List<server.plagiarism.engine.parsetreebased.nodes.Statement> statementList = new ArrayList<>();
//            for(Statement st: statements)
//                statementList.add(makeStatement(st));

            return makeBlockStatement(blockStmt);
        } else {
            ExpressionStatement stmt;
            if (statement == null)
                stmt = makeExpressionStatement(new StringLiteralExpr("Empty"));
            else
                stmt = makeExpressionStatement(new StringLiteralExpr(statement.toString()));
            return new BlockStatement(new ArrayList<server.plagiarism.engine.parsetreebased.nodes.Statement>(Arrays.asList(stmt)), Node.getCounter());
        }
    }


}
