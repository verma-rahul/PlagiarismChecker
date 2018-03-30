package server.plagiarism.engine.parsetreebased.nodes;

import server.plagiarism.engine.parsetreebased.ASTVisitor;

import java.util.Arrays;
import java.util.NoSuchElementException;

/*
  * @author Rahul Verma [verma.rah@husky.neu.edu]
  */
public abstract class Node {
    /**
     * Generate textual representation for subtree rooted at this node.
     */
    public abstract String textualRepresentation();

    /**
     * Returns unique identifier for each node.
     */
    public abstract int getId();

    /**
     * Reverse mapping from Nodes to their unique identifiers
     */
    public static Node getNode(int id) throws NoSuchElementException {

        if(id<0 || id >= size)
            throw new NoSuchElementException(id + ": Not Found");
        else
            return mapping[id];
    }

    /**
     * Adds Node n to the mapping
     * @param n
     */

    public static void addNode(Node n){
        ensureCapacity(counter+1);
        mapping[counter++] = n;

    }

    /**
     * Returns the current counter/number of Nodes at particular state of AST
     * @return
     */

    public static int getCounter() {
        return counter;
    }

    /**
     *
     * @param minCapacity
     * Ensures arrays is size of minCapacity
     */

    private static void ensureCapacity(int minCapacity) {
        mapping = Arrays.copyOf(mapping, minCapacity);
        size = minCapacity;

    }
    /**
     * Define accept method for Node
     * @param v
     */
    public abstract void accept(ASTVisitor v);

    /**
     * Size of the Array buffer
     */

    private static int size;

    /**
     * Counter for mapping array
     */
    private static int counter =0;

    /**
     * Resets the mapping for  Node Class
     */
    public static void reset() {
        mapping = new Node[1];
        counter = 0;
    }
    /**
     * Array buffer into which elements of Node are stored.
     */

    private static Node[] mapping ={};
}
