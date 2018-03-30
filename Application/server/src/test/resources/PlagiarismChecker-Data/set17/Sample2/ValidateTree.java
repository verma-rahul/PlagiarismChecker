package File2;

class TreeNode {
    public int val;
    public TreeNode left, right;
    public TreeNode(int val) {
        this.val = val;
        this.left = this.right = null;
    }
}

public class ValidateTree {
    public boolean isValidBST(TreeNode root) {
        return isBSTTraversal(root) && isBSTDivideAndConquer(root);
    }

    private int lastValue = 0; 
    private boolean firstNode = true;
    public boolean isBSTTraversal(TreeNode root) {
        if (root == null) {
            return true;
        }

        if (!isValidBST(root.left)) {
            return false;
        }

        if (!firstNode && lastValue >= root.val) {
            return false;
        }

        firstNode = false;
        lastValue = root.val;

        if (!isValidBST(root.right)) {
            return false;
        }

        return true;

    }

    private class Result {
        int min;
        int max;
        boolean isBST;
        Result(int min, int max, boolean isBST) {
            this.min = min;
            this.max = max;
            this.isBST = isBST;
        }
    }

    public boolean isBSTDivideAndConquer(TreeNode root) {
        return isBSTHelper(root).isBST;
    }

    public Result isBSTHelper(TreeNode root) {
       if (root == null) {
            return new Result(Integer.MAX_VALUE, Integer.MIN_VALUE, true);
        }

        Result left = isBSTHelper(root.left);
        Result right = isBSTHelper(root.right);

        if (!left.isBST || !right.isBST) {
            return new Result(0,0, false);
        }

        if (root.left != null && left.max >= root.val
                && root.right != null && right.min <= root.val) {
            return new Result(0, 0, false);
        }

        return new Result(Math.min(left.min, root.val),
                Math.max(right.max, root.val), true);
    }
}
