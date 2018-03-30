package set2;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<Object> implements Iterable<Object> {

	public LinkedList() {
		length = 0;
		first = last = null;
	}
	
	public void add(Object e) {
		Node<Object> newNode = new Node<Object>(e, first);
		
		first = newNode;
		length++;
		
		if (length == 1) {
			last = first;
		}
	}
	
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
	
	public int length() {
		return length;
	}
	
	public boolean isEmpty() {
		return (length <= 0);
	}
	
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