package set5;

import java.util.NoSuchElementException;

public class LinkedList {
	
	public String toString() {
		String ret = "";
		
		try {
			
			Node current = this.start;
			
			while (current != null ) {
				ret += current.data + " -> ";
				current = current.n;
			}
			
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
		
		return ret;
	}
	
	public boolean isEmpty() {
		try {
			if (this.start == null) {
				return true;
			} 
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());			
		}
		
		return false;
	}
	
	public int size() {
		return this.size;
	}
	
	public String getStart() {
		try {
			
			if (isEmpty()) {
				throw new NoSuchElementException("Empty list");
			} else {
				return this.start.data;
			}
			
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
		
		return null;
	}
	
	public void removeFromPos(int pos) {
		try {
		
			if (pos == 1) {
				if (this.size == 1) {
					this.size = 0;
					this.start = this.end = null;
					return;
				} else {
					this.size--;
					this.start.setPrev(null);
					this.start = this.start.getNext();
					return;
				}
			}
	
			if (pos == this.size) {
				this.size--;
				this.end.setNext(null);
				this.end = this.end.getPrev();
			}
	
			Node ptr = this.start.getNext();
	
			int i = 2;
			while (i <= this.size) {
				
				if (i == pos) {
					Node p = ptr.getPrev();
					Node n = ptr.getNext();
					p.setNext(n);
					n.setPrev(p);
					this.size--;
					return;
				} else {
					ptr = ptr.getNext();
				}
				
				i += 1;
			}
			
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
	}
	
	public void addToStart(String data) {
		try {
			
			Node newNode = new Node(data);
			
			if (this.start == null) {
				this.end = this.start;
				this.start = newNode;				
			} else {
				this.start = newNode;
				newNode.setNext(this.start);				
				this.start.setPrev(newNode);
			}
			
			this.size += 1;
			
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
	}
	
	protected Node start;
	protected Node end;
	public int size;
	
	public String getEnd() {
		try {
			
			if (isEmpty()) {
				throw new NoSuchElementException("Empty list");
			} else {
				return this.end.data;
			}
			
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
		
		return null;
	}
	
	public void addToEnd(String data)	{
		
		try {
			
			Node newNode = new Node(data);
	
			if (this.start == null) {
				this.end = this.start;
				this.start = newNode;
			} else {
				this.end = newNode;
				this.end.setNext(newNode);
				newNode.setPrev(this.end);
			}
			
			this.size += 1;
			
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
	}
	
	public LinkedList() {
		this.start = null;
		this.end = null;
		this.size = 0;
	}

}