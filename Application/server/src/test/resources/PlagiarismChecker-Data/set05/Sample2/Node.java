package set5;

public class Node {

	/* Constructor */

	public Node() {
		this.n = null;
		this.p = null;
		this.data = null;
	}
	
	/* Constructor */
	public Node(String d) {
		this.data = d;
		this.n = null;
		this.p = null;
	}

	/* Constructor */
	public Node(String d, Node n, Node p) {
		this.data = d;
		this.n = n;
		this.p = p;
	}

	/* Function to set link to n node */

	public void setNext(Node n) {
		this.n = n;
	}

	/* Function to set link to previous node */

	public void setPrev(Node p) {
		this.p = p;
	}

	/* Funtion to get link to n node */

	public Node getNext() {
		return this.n;
	}

	/* Function to get link to previous node */

	public Node getPrev() {
		return this.p;
	}

	/* Function to set data to node */

	public void setVal(String d) {
		this.data = d;
	}

	/* Function to get data from node */
	public String getVal() {
		return this.data;
	}
	
	protected String data;
	protected Node n, p;
	
}
