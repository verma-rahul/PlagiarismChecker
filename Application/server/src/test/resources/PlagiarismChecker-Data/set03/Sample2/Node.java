/**
 * 
 */
package set3;

/**
 * @author ABC
 *
 */
public class Node<Object> {
	
	public Node(Object e, Node<Object> n) {
		val = e;
		next = n;
	}
	
	protected Object val;
	protected Node<Object> next;
}
