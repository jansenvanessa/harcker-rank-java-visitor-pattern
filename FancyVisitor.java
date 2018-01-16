class FancyVisitor extends TreeVis {
	
	private int evenNodes = 0;
	private int greenNodes = 0;
	
    public int getResult() {
    	return Math.abs( evenNodes - greenNodes );
    }

    public void visitNode(TreeNode node) {
    	if(node.getDepth() %2 == 0)
    		evenNodes += node.getValue();
    }

    public void visitLeaf(TreeLeaf leaf) {
    	if(leaf.getColor() == Color.GREEN)
    		greenNodes += leaf.getValue();
    }
}