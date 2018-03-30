package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * Represents a Function declaration
 */
public class FunctionDeclaration extends Statement {
    /**
     * Constructor for Function Declaration
     * @param function represents the Function part of the this Function Declaration
     * @param argList represents the arguments of the Function Declaration
     * @param returnType represents the return Type of the Function Declaration
     * @param body represents the body of the Function Declaration
     * @param id  denotes unique id for identifying this node
     */

    public FunctionDeclaration(Function function,List<Statement> argList, Class returnType, List<Statement> body, int id){
        this.function = function;
        this.id = id;
        this.argList = argList;
        this.returnType = returnType;
        this.body = body;
    }

    @Override
    public String textualRepresentation() {
        return "Function " + this.function.getName();
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
       for(Statement st : body)
           st.accept(v);

    }

    /**
     * Routine to get children nodes of current node
     *
     * @return a list containing all children nodes
     */

    public List<Node> getChilrenNodes() {
        List<Node> childrenNodes = new ArrayList<>();
        for(Statement st : body)
            childrenNodes.add(st);
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

    private Function function;
    private List<Statement> argList;
    private Class returnType;
    private List<Statement> body;
    private int id;
}
