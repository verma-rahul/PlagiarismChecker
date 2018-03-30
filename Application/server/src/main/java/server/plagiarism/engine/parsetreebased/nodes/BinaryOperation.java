package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * Represents a Binary Operation
 */

public class BinaryOperation extends Expression {

    private Operator op;
    private Expression exp1;
    private Expression exp2;
    private int id;

    /**
     * Constructor for BinaryOperation
     *
     * @param op   operator for given BinaryOperation
     * @param exp1 left hand side of the Expression
     * @param exp2 right hand side of the Expression
     * @param id   represents the unique id of this Node
     */
    public BinaryOperation(Operator op, Expression exp1, Expression exp2, int id) {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.id = id;

    }

    /**
     * Generate textual representation for subtree rooted at this node.
     */
    @Override
    public String textualRepresentation() {
        return exp1.textualRepresentation() + op.getOperator() + exp2.textualRepresentation();

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
     * @param v is an ASTVisitor
     */
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
        exp1.accept(v);
        exp2.accept(v);
    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        childrenNodes.add(exp1);
        childrenNodes.add(exp2);
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
