class SumInLeavesVisitor extends TreeVis {
	
	private int result = 0;
	
    public int getResult() {
        return result;
    }

    public void visitNode(TreeNode node) {
      	//implement this
    }

    public void visitLeaf(TreeLeaf leaf) {
    	result += leaf.getValue();
    }
}