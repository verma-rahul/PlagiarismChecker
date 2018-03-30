package server.plagiarism.engine.parsetreebased;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
import server.plagiarism.engine.parsetreebased.nodes.*;
import server.plagiarism.engine.parsetreebased.nodes.Class;

import java.util.List;
import java.util.Optional;


/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * Interface for creating AST nodes
 */

public interface AbstractNodeFactory {

    /**
     * factory method for creating an BinaryOperation node
     */
    public BinaryOperation makeBinaryOperation(BinaryExpr.Operator op, Expression exp1, Expression exp2);

    /**
     * factory method for creating an UnaryOperation node
     */

    public UnaryOperation makeUnaryOperation(UnaryExpr.Operator op, Expression exp1);

    /**
     * factory method for creating a StringExpression node
     */
    public server.plagiarism.engine.parsetreebased.nodes.StringExpression makeStringExpression(String text);

    /**
     * factory method for creating a VariableExpression node
     */

    public VariableExpression makeVariableExpression(Variable var);


    /**
     * factory method for creating a NumberExpression node
     */
    public <N extends Number> NumberExpression makeNumberExpression(N num);

    /**
     * factory method for creating an Assignment node
     */
    public Assignment makeAssignment(Variable var, Expression exp);

    /**
     * factory method for creating a Declaration node
     */
    public VarDeclaration makeVarDeclaration(Variable var);

    /**
     * factory method for creating a Sequence node
     */
    //public Sequence makeSequence(Statement[] statements);

    /**
     * factory method for creating a ClassDeclaration node
     */
    public ClassDeclaration makeClassDeclaration(Class cls, List<server.plagiarism.engine.parsetreebased.nodes.Node> body);

    /**
     * factory method for creating a FunctionDeclaration node
     */
    public FunctionDeclaration makeFunctionDeclaration(String f, NodeList<Parameter> al, Type re, Optional<BlockStmt> body);

    /**
     * factory method for creating a FunctionCall node
     */
    public FunctionCall makeFunctionCall(Optional<Expression> scope, SimpleName name, NodeList<Expression> arguments);


    /**
     * factory method for creating a If node
     */
    public If makeIf(Expression exp, Statement then, Optional<Statement> els);

    /**
     * factory method for creating a While node
     */
    public While makeWhile(Expression test, Statement body);


    /**
     * factory method for creating a For node
     */
    public For makeFor(Optional<Expression> test, NodeList<Expression> intial, NodeList<Expression> incrm, Statement body);

    /**
     * factory method for creating a Conditional Expression node
     */

    ConditionalExpression makeConditional(Expression condition, Expression thenExpr, Expression elseExpr);

    /**
     * factory method for creating a Boolean Expression node
     */

    BooleanExpression makeBooleanExpression(boolean value);

    /**
     * factory method for creating a Block Statement node
     */

    BlockStatement makeBlockStatement(Statement s);

    /**
     * factory method for creating a Expression Statement
     */

    ExpressionStatement makeExpressionStatement(Expression expression);
}
