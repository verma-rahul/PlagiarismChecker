package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
* @author Rahul Verma [verma.rah@husky.neu.edu]
 * Represents a variable declaration
 */
public class VarDeclaration extends Statement {

    /**
     * Constructor for the VarDeclaration
     *
     * @param var is the Variable representing this Variable Declaration
     * @param id  denotes unique id for identifying this node
     */
    public VarDeclaration(Variable var, int id) {
        this.var = var;
        this.id = id;
    }

    @Override
    public String textualRepresentation() {
        return "var " + var.getName();
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

    private Variable var;
    private int id;
}
