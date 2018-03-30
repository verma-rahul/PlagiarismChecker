package server.plagiarism.engine.parsetreebased.nodes;

/**
* @author Rahul Verma [verma.rah@husky.neu.edu]
 * Represents an expression operator such as + or -.
 */
public class Operator {

    /**
     * Constructor for Operator
     *
     * @param op String representation of the opertor
     */
    public Operator(String op){
        this.op = op;
    }

    /**
     *
     * @return helper method to the operator
     */

    public String getOperator(){
        return op;
    }

    private String op; // Represents the operator
}
