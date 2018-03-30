package server.plagiarism.engine.parsetreebased.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 *

 Constructor template for Class:
 new Class (n)
 Interpretation:
 n is the name of the Class
 */

/**
 * Represents a Class
 */
public class Class {

    // Constructor for Class

    public Class(String name){
        this.name = name;
    }

    // Returns the name of the Class

    public String getName(){
        return name;
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

    private String name;
}
