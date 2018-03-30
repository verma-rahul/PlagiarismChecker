package set3;

import java.util.NoSuchElementException;

/*
 *  Java Program to Implement Doubly Linked List
 */

/* Class DLL */
public class DLL {

	/* Constructor */
	public DLL() {
		first = last = null;
		length = 0;
	}

	/* Function to check if list is empty */
	public boolean isEmpty() {
		return first == null;
	}

	/* Function to get length of list */
	public int length() {
		return length;
	}

	/* Function to insert element at beginning */
	public void add(Object val) {
		DLLNode<Object> nptr = new DLLNode<Object>(val, null, null);
		if (first == null) {
			first = nptr;
			last = first;
		} else {
			first.setLinkPrev(nptr);
			nptr.setLinkNext(first);
			first = nptr;
		}
		length++;
	}

	/* Function to insert element at last */
	public void append(int val)	{

		DLLNode<Object> nptr = new DLLNode<Object>(val, null, null);

		if (first == null) {
			first = nptr;
			last = first;
		} else {
			nptr.setLinkPrev(last);
			last.setLinkNext(nptr);
			last = nptr;
		}
		length++;
	}

	/* Function to delete node at position */
	public void deleteAtPos(int pos) {
		if (pos == 1) {
			if (length == 1) {
				first = null;
				last = null;
				length = 0;
				return;
			}

			first = first.getLinkNext();
			first.setLinkPrev(null);
			length--;
			return;
		}

		if (pos == length) {
			last = last.getLinkPrev();
			last.setLinkNext(null);
			length--;
		}

		DLLNode<Object> ptr = first.getLinkNext();

		for (int i = 2; i <= length; i++) {
			if (i == pos) {
				DLLNode<Object> p = ptr.getLinkPrev();
				DLLNode<Object> n = ptr.getLinkNext();
				p.setLinkNext(n);
				n.setLinkPrev(p);
				length--;
				return;
			}

			ptr = ptr.getLinkNext();
		}
	}
	
	public Object first() {
		if (isEmpty()) {
			throw new NoSuchElementException("Empty list");
		}
		return first.val;
	}
	
	public Object last() {
		if (isEmpty()) {
			throw new NoSuchElementException("Empty list");
		}
		return last.val;
	}

	/* Function to display status of list */
	public String toString() {
		String ret = "";		
		DLLNode<Object> current = first;
		
		while (current!= null ) {
			ret += current.val + " -> ";
			current = current.next;
		}
		
		return ret;
	}
	
	protected DLLNode<Object> first;
	protected DLLNode<Object> last;
	public int length;
}