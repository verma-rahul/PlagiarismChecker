

import java.util.Arrays;

/**
 * @author AnkitaNallana
 *
 */
public class Tokenize {

	String s ;
	int SIZE;

	/* Constructor to initialise */
	Tokenize(String str, int size){
		this.s = str;
		this.SIZE = size;
	}

	/* This function tokenizes a sentence into words */
	private String[] tokenize(){

		String[] array = this.s.split(" ");
		return array;

	}

	/* Main function */
	public static void main(String[] args) {
		
		Tokenize w = new Tokenize("Lorem Ipsum Dolor Si Amet",5);
		System.out.println(Arrays.toString(w.tokenize()));

	}



}
