package set3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListIterator<Object> implements Iterator<Object> {
	
	public ListIterator(Node<Object> start) {
		thisNode = start;
	}
	
	@Override
	public boolean hasNext() {
		return (thisNode != null);
	}

	@Override
	public Object next() {
		if (!hasNext()) {
			throw new NoSuchElementException("Empty list");
		}
		
		Object ret = thisNode.val;
		thisNode = thisNode.next;
		return ret;
	}
	
	private Node<Object> thisNode;
	
}
