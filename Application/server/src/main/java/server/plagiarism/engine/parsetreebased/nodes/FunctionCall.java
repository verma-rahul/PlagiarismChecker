package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * Represents a Function declaration
 */
public class FunctionCall extends Expression {
    /**
     * Constructor for Function Call expression
     *
     * @param exp     name of the function being called
     * @param argList arguments passed to the function call
     */
    public FunctionCall(String exp, List<Expression> argList, int id) {
        this.exp = exp;
        this.id = id;
        this.argList = argList;
    }

    @Override
    public String textualRepresentation() {
        return "Function call: " + this.exp;
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
        for (Expression expression : argList)
            expression.accept(v);

    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        for (Expression expression : argList)
            childrenNodes.add(expression);
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

    private String exp;
    private List<Expression> argList;
    private int id;
}
