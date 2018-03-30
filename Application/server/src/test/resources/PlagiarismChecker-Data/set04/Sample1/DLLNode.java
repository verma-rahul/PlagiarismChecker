package set4;

public class DLLNode<Object> {
	
	protected Object val;
	protected DLLNode<Object> next, prev;

	/* Constructor */

	public DLLNode() {
		next = null;
		prev = null;
		val = null;
	}

	/* Constructor */
	public DLLNode(Object d, DLLNode<Object> n, DLLNode<Object> p) {
		val = d;
		next = n;
		prev = p;
	}

	/* Function to set link to next node */

	public void setLinkNext(DLLNode<Object> n) {
		next = n;
	}

	/* Function to set link to previous node */

	public void setLinkPrev(DLLNode<Object> p) {
		prev = p;
	}

	/* Funtion to get link to next node */

	public DLLNode<Object> getLinkNext() {
		return next;
	}

	/* Function to get link to previous node */

	public DLLNode<Object> getLinkPrev() {
		return prev;
	}

	/* Function to set val to node */

	public void setData(Object d) {
		val = d;
	}

	/* Function to get val from node */
	public Object getData() {
		return val;
	}
	
}
