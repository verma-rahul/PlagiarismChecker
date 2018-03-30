package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a If declaration
 *
  * @author Rahul Verma [verma.rah@husky.neu.edu]
  *
  */
public class ConditionalExpression extends Expression {
    /**
     * Constructor for Conditional Expression
     * @param exp represents the test part of Conditional expression
     * @param then represents the then part of Conditional expression
     * @param els represents the else part of conditional expression
     * @param id represents the unique id of this conditional expression node
     */
    public ConditionalExpression(Expression exp, Expression then, Expression els, int id) {
        this.exp = exp;
        this.then = then;
        this.els = els;
        this.id = id;
    }

    @Override
    public String textualRepresentation() {
        return "If " + exp.textualRepresentation();
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
        exp.accept(v);
        then.accept(v);
        els.accept(v);

    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        childrenNodes.add(exp);
        childrenNodes.add(els);
        childrenNodes.add(then);

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

    private Expression exp;
    private Expression then;
    private Expression els;
    private int id;
}
