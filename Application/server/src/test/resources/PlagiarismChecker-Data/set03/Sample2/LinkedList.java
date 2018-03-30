package set3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 *  Java Program to Implement Linked List
 */

public class LinkedList<Object> implements Iterable<Object> {

	/* Constructor */
	public LinkedList() {
		length = 0;
		first = last = null;
	}
	
	/* Function to insert element at begining */
	public void add(Object e) {
		Node<Object> newNode = new Node<Object>(e, first);
		
		first = newNode;
		length++;
		
		if (length == 1) {
			last = first;
		}
	}	
	
	/* Function to insert element at last */
	public void append(Object e) {
		if (isEmpty()) {
			add(e);
		}
		else {
			Node<Object> newNode = new Node<Object>(e, null);
			last.next = newNode;
			last = newNode;
		}
		length++;
	}
	
	/* Function to get length of list */
	public int length() {
		return length;
	}
	
	/* Function to check if list is empty */
	public boolean isEmpty() {
		return (length <= 0);
	}
	
	/* Function to delete front node */
	public Object removeFromFront() {
		if (isEmpty()) {
			throw new NoSuchElementException("Empty list");
		}
		
		Object ret = first.val;
		first = first.next;
		length--;
		if (length <= 1 ) {
			last = first;
		}
		return ret;
	}
	
	/* Function to delete the last node */
	public Object removeFromEnd() {
		if (isEmpty()) {
			throw new NoSuchElementException("Empty list");
		}
		
		Object ret = last.val;
		if (length == 1) {
			ret = removeFromFront();
		}
		else {
			Node<Object> newLast = first;
			while (newLast.next != last) {
				newLast = newLast.next;
			}
			last = newLast;
			last.next = null;
			length--;
		}
		return ret;
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
		Node<Object> current = first;
		
		while (current!= null ) {
			ret += current.val + " -> ";
			current = current.next;
		}
		
		return ret;
	}

	@Override
	public Iterator<Object> iterator() {
		return new ListIterator<Object>(first);
	}
	
	private int length;
	private Node<Object> first;
	private Node<Object> last;
}