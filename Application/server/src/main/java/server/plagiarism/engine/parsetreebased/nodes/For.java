package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 *
 * Representing the For Node
 */
public class For extends Loop {

    /**
     * Constructor for For Node
     *
     * @param test   represents the test expression of the While Statement
     * @param body   represents the body of the While Statement
     * @param intial represents the intial expression for the For Node
     * @param increm represent the increment expression for the For Node
     * @param id     denotes unique id for identifying this node
     */
    public For(Expression test, Statement body, List<Expression> intial, List<Expression> increm, int id) {
        super(test, body, id);
        this.intial = intial;
        this.increm = increm;
    }


    /**
     * Generate textual representation for subtree rooted at this node.
     */
    @Override
    public String textualRepresentation() {
        return "For : " + this.getTest().textualRepresentation();
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
        for (Expression s : this.increm)
            s.accept(v);
        for (Expression s : this.intial)
            s.accept(v);
        this.getBody().accept(v);
    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        childrenNodes.add(this.getTest());
        childrenNodes.add(this.getBody());
        for (Expression s : this.increm)
            childrenNodes.add(s);
        for (Expression s : this.intial)
            childrenNodes.add(s);
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

    private final List<Expression> increm;
    private final List<Expression> intial;
}
