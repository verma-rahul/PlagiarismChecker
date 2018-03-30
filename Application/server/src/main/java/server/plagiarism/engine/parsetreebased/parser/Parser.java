package server.plagiarism.engine.parsetreebased.parser;

import com.github.javaparser.ast.CompilationUnit;
import server.plagiarism.entity.Project;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 *Parser Interface
 */
public interface Parser {
    /**
    *  Parse a Document class object depending upon the Plagiarism Engine Class
    */
    public CompilationUnit generateAST(String file);

    /**
    *  Tokenizes a Document class object depending upon the Plagiarism Engine Class
    */
    public String[] tokenizer(String lines);

    /**
    *  pre Process a Document class object depending upon the Plagiarism Engine Class
    */
    public void preProcessDocument(Project prj);

}
