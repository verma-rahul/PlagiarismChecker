package server.plagiarism.engine.parsetreebased;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.ast.Node;

/**
 * The class matching line numbers for node types with same tree structure.
 *
 * @author Rahul Verma
 *
 */
public class LineMatcher {
    /**
     * Node type interested for matching line numbers. The type with smaller index has
     * higher hierarchy.
     */
    private List<String> nodeTypeHierarchy;

    /**
     * All the type names of the loops to match.
     */
    private List<String> nodeLoops;

    /**
     * The summary generated.
     */
    private Set<String> summary;

    /**
     * Constructs a LineMatcher.
     */
    public LineMatcher() {
        initializeNodeType();
        initializeSummary();
    }

    /**
     * Initialize the interested array of node types. The type with smaller index has
     * higher hierarchy.
     */
    private void initializeNodeType(){
        nodeTypeHierarchy = new ArrayList<String>();
        nodeTypeHierarchy.add("ClassOrInterfaceDeclaration");
        nodeTypeHierarchy.add("ConstructorDeclaration");
        nodeTypeHierarchy.add("MethodDeclaration");

        nodeLoops = new ArrayList<String>();
        nodeLoops.add("ForeachStmt");
        nodeLoops.add("ForStmt");
        nodeLoops.add("WhileStmt");
        nodeLoops.add("SwitchStmt");
        nodeLoops.add("IfStmt");
    }

    /**
     * Set nodeLoop by input the list of types
     *
     * @param l  the list of node types
     */
    public void setNodeLoops(List<String> l){
    	nodeLoops = l;
    }

    /**
     * Initialize the summary.
     */
    private void initializeSummary(){
        summary = new HashSet<String>();
    }

    /**
     * Match line numbers for the similar subtrees between two given trees.
     * The similar subtrees is defined as the subtrees in the trees with the same structure
     * whose root node type is the interested type pre-defined.
     *
     * @param  root1  the root node of the first tree
     * @param  root2  the root node of the first tree
     * @return  the summary generate, with line number matching information
     */
    public List<String> matchLineNumbers(Node root1, Node root2){
        initializeSummary();
        if (validateInput(root1, root2)){
            Map<String, Node> stringToNode1 = new HashMap<String, Node>();
            Map<String, Node> stringToNode2 = new HashMap<String, Node>();

            generateSubtreeRepresentation(root1, stringToNode1);
            generateSubtreeRepresentation(root2, stringToNode2);

            Map<Node, String> nodeToString1 = generateNodeToString(stringToNode1);
            Map<Node, String> nodeToString2 = generateNodeToString(stringToNode2);

            // check for mapping of interested type
            matchNodeType(root1, stringToNode1, stringToNode2, nodeToString1, nodeToString2);

            // check for loop mapping
            Map<String,String> loopmap1=new HashMap<>(),loopmap2=new HashMap<>();
            generateLoops(root1, loopmap1, nodeToString1);
            generateLoops(root2, loopmap2, nodeToString2);
            loopSummary(loopmap1,loopmap2);
        }
        List<String> summaryList = new ArrayList<String>(summary);
        Collections.sort(summaryList);
        return summaryList;
    }

    /**
     * Check whether the input trees are valid.
     *
     * @param  root1  the root node of the first tree
     * @param  root2  the root node of the first tree
     * @return  iff the two trees are both valid
     */
    private boolean validateInput(Node root1, Node root2){
        if (root1 == null || root2 == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Generate Node to String mapping given String to Node mapping.
     *
     * @param  stringToNode  String to Node mapping
     * @return  Node to String mapping
     */
    private Map<Node, String> generateNodeToString(Map<String, Node> stringToNode){
        Map<Node, String> nodeToString = new HashMap<Node, String>();
        for (String subtree: stringToNode.keySet()){
            nodeToString.put(stringToNode.get(subtree), subtree);
        }
        return nodeToString;
    }

    /**
     * Generate tree representation in String for the given tree, and add the node to the
     * given map with key as string and value as node.
     *
     * @param  node  the root node of the given tree
     * @param  stringToNode  the map for String representation to Node
     * @return  the tree representation in string
     */
    private String generateSubtreeRepresentation(Node node, Map<String, Node> stringToNode){
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(node.getClass().getSimpleName());
        for (Node child: node.getChildNodes()){
            sb.append(generateSubtreeRepresentation(child, stringToNode));
        }
        sb.append("}");

        // only keep the interested type in the hash map
        for (String type: nodeTypeHierarchy){
            if (type.equals(node.getClass().getSimpleName())){
                stringToNode.put(sb.toString(), node);
                break;
            }
        }

        return sb.toString();
    }

    /**
     * Match the similar subtrees for tree1 and tree2. Tree 1 and Tree2 are represented
     * by 2 maps with string representation to node map and node to string representation
     * map.
     *
     * @param root  the root node of the tree 1
     * @param stringToNode1  the string to node map for tree 1
     * @param stringToNode2  the string to node map for tree 2
     * @param nodeToString1  the node to string map for tree 1
     * @param nodeToString2  the node to string map for tree 2
     */
    private void matchNodeType(Node root,
                               Map<String, Node> stringToNode1,
                               Map<String, Node> stringToNode2,
                               Map<Node, String> nodeToString1,
                               Map<Node, String> nodeToString2){
        for (Node node1: root.getChildNodes()){
            for (String nodeType: nodeTypeHierarchy){
                // when there is matching for the type with higher hierarchy, do not match
                // for lower hierarchy
                if (node1.getClass().getSimpleName().equals(nodeType)){
                    String subtree1 = nodeToString1.get(node1);
                    if (stringToNode2.containsKey(subtree1)){
                        Node node2 = stringToNode2.get(subtree1);
                        String result1 = nodeType + " @line " +
                                String.valueOf(node1.getBegin().get().line) + "~" +
                                String.valueOf(node1.getEnd().get().line);
                        String result2 = nodeType + " @line " +
                                String.valueOf(node2.getBegin().get().line) + "~" +
                                String.valueOf(node2.getEnd().get().line);

                        summary.add(result1 +" matches to "+ result2);
                        break;
                    }
                } else {
                    // if the node is not the interested type, recursively check the
                	// child nodes
                    matchNodeType(node1, stringToNode1, stringToNode2,
                    		nodeToString1, nodeToString2);
                }
            }
        }
    }

    /**
     * Generates Map of concerned Loops for a root Node and their Line Numbers
     *
     * @param node  the root node of a tree 1
     * @param loopmap1  Map of Node representation as key and type, line numbers as value
     * @param nodeToString  the map from node to string representation
     * @Return the node to string map for tree 2
     */
    private String generateLoops(Node node, Map<String,String> loopmap1,
    												Map<Node, String> nodeToString) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(node.getClass().getSimpleName());
        for (Node child: node.getChildNodes()){
        	sb.append(generateLoops(child, loopmap1, nodeToString));
        }
        sb.append("}");

        if (nodeLoops.contains(node.getClass().getSimpleName())) {
        	if (! nodeToString.containsKey(node)){
	            loopmap1.put(sb.toString(),
	                    node.getClass().getSimpleName() +" @line " +
	                            String.valueOf(node.getBegin().get().line) + "~" +
	                            String.valueOf(node.getEnd().get().line));
        	}
        }
        return sb.toString();
    }


    /**
     * checks the similar type of loop of nodes1List and nodes2List and add there line
     * numbers in summary.
     *
     * @param nodes1List  Map of Node representation as key and type, line numbers as
     * 			value for node 1
     * @param nodes1List  Map of Node representation as key and type, line numbers as
     * 			value for node 2
     */
    private void loopSummary(Map<String,String> nodes1List, Map<String,String>nodes2List) {
        for (Map.Entry<String, String> e :nodes1List.entrySet()){
            if (nodes2List.containsKey(e.getKey())){
                summary.add(e.getValue()  +" matches to "+nodes2List.get(e.getKey()));
            }
        }

    }

}
