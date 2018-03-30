package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 *

 * Represents a class declaration
 */
public class ClassDeclaration extends Statement {

    /**
     * Constructor for Class Declaration
     * @param cls representing the class of this Class Declaration Node
     * @param body representing the body of the class
     * @param id  denotes unique id for identifying this node

     */
    public ClassDeclaration(Class cls, List<Node> body, int id){
        this.cls = cls; this.id = id; this.statementList = body;
    }

    @Override
    public String textualRepresentation() {
        return "cls " + this.cls.getName();
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
        for(Node s: statementList)
            s.accept(v);

    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        for (Node statement : statementList)
            childrenNodes.add(statement);

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

    private Class cls;
    private int id;
    private List<Node> statementList;
}
