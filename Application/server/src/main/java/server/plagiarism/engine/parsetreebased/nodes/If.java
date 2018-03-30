package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * Represents a If declaration
 */
public class If extends Statement {
    /**
     * Constructor for If Expression
     *
     * @param exp  represents the condition of the If expression
     * @param then represents the then part of the If expression
     * @param els  represents the else parth of the If expression
     * @param id   denotes unique id for identifying this node
     */
    public If(Expression exp, Statement then, Statement els, int id) {
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
        childrenNodes.add(then);
        childrenNodes.add(els);
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

    private Expression exp;
    private Statement then;
    private Statement els;
    private int id;
}
