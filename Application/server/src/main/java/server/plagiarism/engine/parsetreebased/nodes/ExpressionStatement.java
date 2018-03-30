package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 *
 */
public class ExpressionStatement extends Statement {
    /**
     * Constructor for Expression Statement
     *
     * @param expression expression part of the Expression Statement
     * @param id         denotes unique id for identifying this node
     */
    public ExpressionStatement(Expression expression, int id) {
        this.expression = expression;
        this.id = id;
    }

    @Override
    public String textualRepresentation() {
        return "\"" + expression.textualRepresentation() + "\"";
    }

    /**
     * Returns unique identifier for this node.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Define accept method for Node
     *
     * @param v
     */
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
        expression.accept(v);
    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        childrenNodes.add(expression);

        return childrenNodes;
    }

    /**
     * Routine to get name of this  node type
     *
     * @return a String representing the name of the class
     */

    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    private Expression expression;
    private int id;
}
