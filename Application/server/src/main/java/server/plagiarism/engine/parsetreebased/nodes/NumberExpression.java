package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
* @author Rahul Verma [verma.rah@husky.neu.edu]
* Represents a numeric expression
   *
   */
public class NumberExpression<N extends Number> extends Expression {

    /**
     * Constructor for NumberExpression
     *
     * @param num represents the number part of the NumberExpression
     * @param id  denotes unique id for identifying this node
     */
    public NumberExpression(N num, int id) {
        this.num = num;
        this.id = id;
    }

    @Override
    public String textualRepresentation() {
        return this.num.toString();
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
    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
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

    public N getNum() {
        return num;
    }

    private N num;
    private int id;
}
