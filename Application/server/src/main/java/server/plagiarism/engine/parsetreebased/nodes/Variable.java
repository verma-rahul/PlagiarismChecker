package server.plagiarism.engine.parsetreebased.nodes;

/**
* @author Rahul Verma [verma.rah@husky.neu.edu]
    Constructor template for Variable:
       new Variable (n)
    Interpretation:
       n is the name of the variable
*/


public class Variable {

    // Constructor for Variable

    public Variable(String name){
        this.name = name;
    }

    // Returns the name of the variable
    public String getName(){
        return name;
    }

    private String name;
}
