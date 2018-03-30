package server.plagiarism.engine.parsetreebased;

import com.github.javaparser.ast.CompilationUnit;
import server.plagiarism.engine.parsetreebased.nodes.Node;
import server.plagiarism.engine.parsetreebased.nodes.NumberExpression;

import java.util.*;

/**
 * A parse tree comparator based on the count of specific type of node
 *
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 */
public class NodeTypeBasedComparator implements ParseTreeComparator{

	/**
	 * The HashMap recording the count of pre defined type of node for the first tree
	 */
	Map<String, Integer> count1 = new HashMap<String, Integer>();

	/**
	 * The HashMap recording the count of pre defined type of node for the second tree
	 */
	Map<String, Integer> count2 = new HashMap<String, Integer>();

	/**
	 * Construct a node type based comparator.
	 */
	public NodeTypeBasedComparator() { }

	/**
	 * Calculate the similarity score based on the count of type of nodes.
	 *
	 * @param  t1  the first tree
	 * @param  t2  the second tree
	 * @return  the similarity score
	 */
	@Override
	public Float calculateSimilarity(CompilationUnit t1, CompilationUnit t2) {
		if (t1 == null || t2 == null) return 0.0f;

		NodeCountVisitor visitor1 = new NodeCountVisitor();
		NodeCountVisitor visitor2 = new NodeCountVisitor();
		List<Node> nodeNames1 = new ArrayList<>();
		nodeNames1.add(0, new NumberExpression<>(0, 0));
		List<Node> nodeNames2 = new ArrayList<>();
		nodeNames2.add(0, new NumberExpression<>(0, 0));
		visitor1.visit(t1, nodeNames1);
		visitor2.visit(t2, nodeNames2);
		count1 = visitor1.getHashMap();
		count2 = visitor2.getHashMap();

		Float score = 0.0f;
		int typeCount = 0;
		for (String key : count1.keySet()){
			if (count1.get(key) > 0 || count2.get(key) > 0){
				typeCount += 1;
				score += (float) Math.min(count1.get(key), count2.get(key)) /
									Math.max(count1.get(key), count2.get(key));
			}

		}
		if (typeCount == 0) {
			return 0.0f;
		} else {
			return score/typeCount;
		}
	}

}
