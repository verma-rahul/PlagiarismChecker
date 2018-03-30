package server.plagiarism.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import server.plagiarism.engine.parsetreebased.TEDBasedComparator;

/**
 * Test cases for TEDBasedComparator
 ** @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class TEDBasedComparatorTest {
	// test if the input are two invalid trees
	@Test
	public void calculateSimilarityInvalidTwoTrees(){
		TEDBasedComparator comparator = new TEDBasedComparator();
		assertEquals(comparator.calculateSimilarity(null, null), -1.0f, 0.0002);
	}

	// test if the input has one invalid tree
	@Test
	public void calculateSimilarityInvalidTree1(){
		TEDBasedComparator comparator = new TEDBasedComparator();
		assertEquals(comparator.calculateSimilarity(null, new CompilationUnit()), -1.0f, 0.0002);
	}

	// test if the input has one invalid tree
	@Test
	public void calculateSimilarityInvalidTree2(){
		TEDBasedComparator comparator = new TEDBasedComparator();
		assertEquals(comparator.calculateSimilarity(new CompilationUnit(), null), -1.0f, 0.0002);
	}

	// test if the input are the same tree
	@Test
	public void calculateSimilaritySameTree(){
		CompilationUnit cu = new CompilationUnit();
		ClassOrInterfaceDeclaration dec = new ClassOrInterfaceDeclaration();
		ClassOrInterfaceDeclaration dec1 = new ClassOrInterfaceDeclaration();
		dec.setParentNode(cu);
		dec1.setParentNode(cu);
		TEDBasedComparator comparator = new TEDBasedComparator();
		assertEquals(comparator.calculateSimilarity(cu, cu), 1.0f, 0.0002);
	}

	// test if the input are the same tree
	@Test
	public void calculateSimilarityTreeDist2(){
		CompilationUnit cu1 = new CompilationUnit();
		CompilationUnit cu2 = new CompilationUnit();

		ClassOrInterfaceDeclaration dec = new ClassOrInterfaceDeclaration();
		ClassOrInterfaceDeclaration dec1 = new ClassOrInterfaceDeclaration();
		dec.setParentNode(cu1);
		dec1.setParentNode(cu1);

		ClassOrInterfaceDeclaration dec2 = new ClassOrInterfaceDeclaration();
		dec2.setParentNode(cu2);

		TEDBasedComparator comparator = new TEDBasedComparator();
		assertEquals(comparator.calculateSimilarity(cu1, cu2), 0.75f, 0.0002);
	}

}
