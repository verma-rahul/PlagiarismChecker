package server.plagiarism.engine.parsetreebased;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import costmodel.StringUnitCostModel;
import distance.APTED;
import node.StringNodeData;
import parser.BracketStringInputParser;

/**
 * Parse tree comparator generating similarity based on tree edit distance.
 * Similarity = 1 - TED/(sourceTreeSize + destinationTreeSize)
 *
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class TEDBasedComparator implements ParseTreeComparator{

	/**
	 * Construct a TEDBasedComparator.
	 */
	public TEDBasedComparator() { }

	/**
	 * Calculate the normed tree edit distance for two trees.
	 *
	 * @param  source  the source tree
	 * @param  destination  the destination tree
	 * @return  the normed tree edit distance
	 */
	@Override
	public Float calculateSimilarity(CompilationUnit source, CompilationUnit destination){
		if (source == null || destination == null) return -1.0f;

		String sourceString = generateStringRepresentation(source);
		String destinationString = generateStringRepresentation(destination);

		BracketStringInputParser tedParser = new BracketStringInputParser();
		StringUnitCostModel costModel = new StringUnitCostModel();
		APTED<StringUnitCostModel,StringNodeData> apted = new APTED<StringUnitCostModel, StringNodeData>(costModel);
		Float result = apted.computeEditDistance(tedParser.fromString(sourceString),
													tedParser.fromString(destinationString));
		return 1 - result/(getTreeSize(source) + getTreeSize(destination));
	}

	/**
	 * Get the size of the tree.
	 *
	 * @param  tree  the root node of the tree
	 * @return  the size of the tree
	 */
	private int getTreeSize(Node tree){
		int cnt = 1;

		if (tree.getChildNodes().size() == 0) return cnt;
		for (Node n : tree.getChildNodes()){
			cnt += getTreeSize(n);
		}
		return cnt;
	}

	/**
	 * Generate the string representation of the given tree.
	 *
	 * @param  tree  the syntax tree
	 * @return  the string representation
	 */
	private String generateStringRepresentation(CompilationUnit tree){
		StringBuilder sb = new StringBuilder();
		generateStringRepresentationWithBracket(tree, sb);
		return sb.toString();
	}

	/**
	 * Generate the string representation with bracket of the given tree node.
	 *
	 * @param  tree  the syntax tree
	 * @param  sb  the string builder to record the string
	 */
	private void generateStringRepresentationWithBracket(Node tree, StringBuilder sb){
		sb.append("{");
		sb.append(tree.getClass().getSimpleName());
		for(Node n : tree.getChildNodes()){
			generateStringRepresentationWithBracket(n, sb);
		}
		sb.append("}");
	}
}
