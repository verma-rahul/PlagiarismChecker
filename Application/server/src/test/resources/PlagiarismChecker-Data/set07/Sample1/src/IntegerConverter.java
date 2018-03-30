

import java.util.HashMap;
import java.util.Map;

public class IntegerConverter {
	
	int number;
	private static String[] tens = {"",
			    " ten",
			    " twenty",
			    " thirty",
			    " forty",
			    " fifty",
			    " sixty",
			    " seventy",
			    " eighty",
			    " ninety"
			  };

	private static String[] digits = {
			    "",
			    " one",
			    " two",
			    " three",
			    " four",
			    " five",
			    " six",
			    " seven",
			    " eight",
			    " nine",
			    " ten",
			    " eleven",
			    " twelve",
			    " thirteen",
			    " fourteen",
			    " fifteen",
			    " sixteen",
			    " seventeen",
			    " eighteen",
			    " nineteen"
			  };
	
	public static void main(String[] args){
		
		int number = 0;
		
		 String ste;
		 String numberString;

		    if (number % 100 < 20){
		      ste = digits[number % 100];
		      number /= 100;
		    }
		    else {
		      ste = digits[number % 10];
		      number /= 10;

		      ste = tens[number % 10] + ste;
		      number /= 10;
		    }
		    if (number == 0) {
		    	numberString = ste;
		    }
		    
		    numberString = digits[number] + " hundred" + ste;
		    System.out.println(numberString); 
	}
	
}
