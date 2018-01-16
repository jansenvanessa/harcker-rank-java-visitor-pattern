import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Solution {
	
	static int [] values;
	static Color [] colors;
	static Map<Integer, Set<Integer>> nodesMap = new HashMap<>();
	
	public static Tree solve() {
		
		Scanner in = new Scanner(System.in);
		int totalNodes = in.nextInt();
		
		initTreeNodes(totalNodes, in);
		buildNodesMap(totalNodes, in);
		in.close();
		
		if (isRoot(totalNodes))
			return new TreeLeaf(values[0],colors[0],0);
		
		return createTree(new TreeNode(values[0],colors[0],0));
    }
	
	public static void main(String[] args) {
		
      	Tree root = solve();
      	
		SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
      	ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
      	FancyVisitor vis3 = new FancyVisitor();

      	root.accept(vis1);
      	root.accept(vis2);
      	root.accept(vis3);

      	int res1 = vis1.getResult();
      	int res2 = vis2.getResult();
      	int res3 = vis3.getResult();

      	System.out.println(res1);
     	System.out.println(res2);
    	System.out.println(res3);
	}
	
	private static void buildNodesMap(int totalNodes, Scanner in) {
		
		int nEdges = totalNodes-1;
		
		for (int i = 0 ; i < nEdges ; i++) {
			int parent = in.nextInt();
			int child = in.nextInt();
			mapNodeIndexes(parent,child);
		}
	}
	
	private static void mapNodeIndexes(int parent, int child) {
		addChildIndexes(parent, child);
		addParentIndexes(child, parent);
	}
	
	private static void addChildIndexes(int parent, int child) {
		
		Set<Integer> childIndexes = nodesMap.get(parent);
		
		if (childIndexes == null)
			childIndexes = new HashSet<>();
		
		childIndexes.add(child);
		nodesMap.put(parent, childIndexes);
	}

	private static void addParentIndexes(int child, int parent) {
		
		Set<Integer> parentIndexes = nodesMap.get(child);
		
		if(parentIndexes == null)
			parentIndexes = new HashSet<>();
		
		parentIndexes.add(parent);
		nodesMap.put(child, parentIndexes);
	}

	private static Tree createTree(Tree rootNode) {
		buildEdge(rootNode,1);
		return rootNode;
	}
	
	private static void buildEdge(Tree node, int nodeIndex) {
		for(int nodeChildId : getChildNodes(nodeIndex)){
			nodesMap.get(nodeChildId).remove(nodeIndex);
			createEdge(node, nodeChildId);
		}
	}
	
	private static void createEdge(Tree parent,int nodeIndex){
		
		TreeNode nodeParent = (TreeNode) parent;
		
		if (nodeHasChild(nodeIndex)){
			TreeNode node = new TreeNode(getNodeValue(nodeIndex),getNodeColor(nodeIndex),getNodeDepth(parent));
			nodeParent.addChild(node);
			buildEdge(node, nodeIndex);
		} else{
			TreeLeaf leaf = new TreeLeaf(getNodeValue(nodeIndex),getNodeColor(nodeIndex),getNodeDepth(parent));
			nodeParent.addChild(leaf);
		}
	}
	
	private static Color getNodeColor(int nodeIndex) {
		return colors[nodeIndex - 1];
	}
	
	private static int getNodeValue(int nodeIndex) {
		return values[nodeIndex - 1];
	}
	
	private static int getNodeDepth(Tree parent) {
		return parent.getDepth() + 1;
	}
	
	private static boolean nodeHasChild(int nodeIndex) {
		return getChildNodes(nodeIndex) != null && !getChildNodes(nodeIndex).isEmpty();
	}
	
	private static Set<Integer> getChildNodes(int nodeIndex) {
		return nodesMap.get(nodeIndex);
	}
	
	private static boolean isRoot(int totalNodes) {
		return totalNodes == 1;
	}
	
	private static void initTreeNodes(int totalNodes, Scanner in) {
		initNodeValues(totalNodes, in);
		initNodeColors(totalNodes, in);
	}

	private static void initNodeValues(int totalNodes, Scanner in) {
		initNodeValuesArray(totalNodes);
		setNodeValues(totalNodes, in);
	}
	
	private static void initNodeColors(int totalNodes, Scanner in) {
		initNodeColorsArray(totalNodes);
		setNodeColorsValues(totalNodes, in);
	}

	private static void initNodeValuesArray(int totalNodes) {
		values = new int[totalNodes];
	}
	
	private static void setNodeValues(int totalNodes, Scanner in) {
		for(int i = 0; i < totalNodes; i++)
			values[i] = in.nextInt();
	}
	
	private static void initNodeColorsArray(int totalNodes) {
		colors = new Color[totalNodes];
	}
	
	private static void setNodeColorsValues(int totalNodes, Scanner in) {
		for(int i=0;i< totalNodes;i++) 
			colors[i] = in.nextInt() == 0 ? Color.RED : Color.GREEN;
	}
	
	
}
