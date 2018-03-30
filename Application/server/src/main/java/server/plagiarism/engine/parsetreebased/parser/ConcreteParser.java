package server.plagiarism.engine.parsetreebased.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;


import server.plagiarism.entity.Project;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * The concrete parser to parse the code to the syntax tree
 *
 */
public class ConcreteParser implements Parser{
	/**
	 * The list of Java keywords.
	 */
	Set<String> keywords;

	/**
	 * Constructs a parser.
	 */
	public ConcreteParser() {
		String filePath = "src/main/resources/JavaKeywords.txt";
		setKeywords(filePath);
	}

	/**
	 * Generate the syntax tree for the given file. Return null if the
	 * input string of java file is invalid.
	 *
	 * @param  file  the string of file to be parsed
	 * @return  the root node of the syntax tree
	 */
	public CompilationUnit generateAST(String file){
		try {
			return JavaParser.parse(file);
		} catch (ParseProblemException e){
			return null;
		}
	}

	/**
	 * Convert the string to a list of tokens.
	 *
	 * @param  lines  the input string
	 * @return  the list of tokens
	 */
	public String[] tokenizer(String lines) {
		//String regex = "(\\.?\\w+)";
		String regex = "[\\W&&[^.]]";
		String[] tokens = lines.split(regex);
		List<String> tokensWithoutKeywords = new ArrayList<String>();

		if (keywords == null){
			for (String token: tokens){
				if (token.replaceAll("\\s+","").length() > 0)
					tokensWithoutKeywords.add(token.toLowerCase());
			}
		} else {
			for (String token: tokens){
				if (!keywords.contains(token.toLowerCase()) &&
							token.replaceAll("\\s+","").length() > 0){
					tokensWithoutKeywords.add(token.toLowerCase());
				}
			}
		}
		String[] tokensArray = new String[tokensWithoutKeywords.size()];
		return tokensWithoutKeywords.toArray(tokensArray);
	}

	/**
	 * Get Java key words from an external file.
	 *
	 * @param  filePath  the file path of the keyword file
	 * @return  the list of java keywords
	 */
	private Set<String> readJavaKeywords(String filePath) {
		try {
			Set<String> l = new HashSet<String>();

			BufferedReader br = new BufferedReader(new FileReader(filePath));
		    String line = br.readLine();
		    while (line != null) {
		    	l.add(line.replaceAll("\\s+","").toLowerCase());
		        line = br.readLine();
		    }
		    br.close();
		    return l;
		} catch (IOException e) {
			System.out.println("keywords.txt file does not extis!");
			return null;
		}
	}

	/**
	 * Set keywords with the words read from the given file path.
	 *
	 * @param  filePath  the file path of the keyword file
	 */
	public void setKeywords(String filePath){
		keywords = readJavaKeywords(filePath);
	}

	/**
	 * Get keywords.
	 */
	public Set<String> getKeywords(){
		return keywords;
	}

	/**
	 * Pre-process the project, no treatment now.
	 *
	 * @param  prj  the project to be parsed
	 */
	public void preProcessDocument(Project prj) {
	}

}
