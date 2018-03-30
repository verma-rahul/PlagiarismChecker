package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * Represents an assignment statement
 */
public class Assignment extends Statement {

    /**
     * Constructor for Assignment statement
     *
     * @param var represents the variable part of the Assignment
     * @param exp represents the expression part of the Assignment
     * @param id  denotes unique id for identifying this node
     */
    public Assignment(Variable var, Expression exp, int id) {
        this.var = var;
        this.exp = exp;
        this.id = id;
    }

    @Override
    public String textualRepresentation() {
        return var.getName() + " = " + exp.textualRepresentation();
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
     * @param v v is an ASTVisitor
     */
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
        exp.accept(v);
    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        childrenNodes.add(exp);
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

    private Variable var; // Variable of the assignment
    private Expression exp; // Expression assigned to the variable
    private int id; // id of the this node
}
