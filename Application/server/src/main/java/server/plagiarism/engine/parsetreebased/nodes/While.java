package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
* @author Rahul Verma [verma.rah@husky.neu.edu]
 * Representation of the While node
 */
public class While extends Loop {

    /**
     * Constructor for the while statement
     *
     * @param test represents the test expression of the While Statement
     * @param body represents the body of the While Statement
     * @param id   denotes unique id for identifying this node
     */
    public While(Expression test, Statement body, int id) {
        super(test, body, id);
    }


    /**
     * Generate textual representation for subtree rooted at this node.
     */
    @Override
    public String textualRepresentation() {
        return "while(" + getTest().textualRepresentation() + "){\n" + getBody().textualRepresentation() + "}";
    }

    /**
     * Define accept method for Node
     *
     * @param v
     */
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
        this.getTest().accept(v);
        this.getBody().accept(v);
    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        childrenNodes.add(this.getBody());
        childrenNodes.add(this.getTest());
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
