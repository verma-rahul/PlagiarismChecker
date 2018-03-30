package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
* @author Rahul Verma [verma.rah@husky.neu.edu]
 * Represents a Binary expression
 */

public class UnaryOperation extends Expression {

    private Operator op;
    private Expression exp1;
    private int id;

    /**
     * Constructor for UnaryOperation
     *
     * @param op   Operator for the given UnaryOperation
     * @param exp1 Expression part of the UnaryOperation
     * @param id   unique id for this node
     */

    public UnaryOperation(Operator op, Expression exp1, int id) {
        this.op = op;
        this.exp1 = exp1;
        this.id = id;

    }

    /**
     * Generate textual representation for subtree rooted at this node.
     */
    @Override
    public String textualRepresentation() {
        return exp1.textualRepresentation() + op.getOperator();

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
        exp1.accept(v);
    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        childrenNodes.add(exp1);
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
}
