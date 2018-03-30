

import java.util.Arrays;

public class Words {

	String sentence = "Lorem Ipsum Sit Dolors";
	int N = 100;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Words w = new Words();
		w.sentence = "This is a sentence";
		System.out.println(Arrays.toString(w.getWordsFromSentence()));
		
		
	}

	private String[] getWordsFromSentence(){
		String[] words = new String[N];
		int count = 0;
		while(sentence.contains(" ") & sentence.length()>0){
			
			System.out.println(sentence.charAt(sentence.indexOf(" ")));
			String temp = sentence.substring(0, sentence.indexOf(" "));
			sentence = sentence.substring(sentence.indexOf(' ')+1, sentence.length());
			words[count] = temp;
			count++;
		}
		words[count]=sentence; //append the last word
		return words;
	}
	
}
