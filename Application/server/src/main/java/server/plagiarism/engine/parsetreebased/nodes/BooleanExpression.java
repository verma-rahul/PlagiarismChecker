package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 *
 */
public class BooleanExpression extends Expression {

    /**
     * Constructor for the Boolean Expression
     * @param value the value of the Boolean Expression

     * @param id represents the unique id of this BooleanExpression
     */
    public BooleanExpression(Boolean value, int id) {
        this.value = value;
        this.id = id;
    }

    @Override
    public String textualRepresentation() {
        return this.value + "";
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

    private boolean value;
    private int id;
}
