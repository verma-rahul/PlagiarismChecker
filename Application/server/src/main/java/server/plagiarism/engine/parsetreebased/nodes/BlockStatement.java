package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 *
 */
public class BlockStatement extends Statement {
    /**
     * Constructor for BlockStatement
     *
     * @param text list containing all the statements inside the block statement
     * @param id   represents the id of this Node
     */
    public BlockStatement(List<Statement> text, int id) {
        this.text = text;
        this.id = id;
    }

    @Override
    public String textualRepresentation() {

        StringBuilder sb = new StringBuilder();
        for(Statement s : text){
            sb.append("\n" + s.textualRepresentation());
        }

        return "\"" + sb.toString() + "\"";
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
     * @param v is an visitor
     */
    @Override
    public void accept(ASTVisitor v) {
        v.visit(this);
        for (Statement statement : text)
            statement.accept(v);
    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        for (Statement statement : text)
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

    private List<Statement> text;
    private int id;
}
