package model;

/**
 *
 * @Author yu.yang
 * @Date 2018/10/15 10:02
 */
public class BinaryNode<T> implements TreeNode {

    private T data;

    private BinaryNode<T> left;

    private BinaryNode<T> right;

    private BinaryNode<T> parent;


    public boolean hasAnyChildren() {
        return this.left != null || this.right != null;
    }

    public boolean hasBothChildren() {
        return this.left!=null&&this.right!=null;
    }

    public boolean isRoot() {
        return parent==null;
    }

    public boolean hasLeftChild() {
        return left!=null;
    }

    public boolean hasRightChild() {
        return right!=null;
    }

    public boolean isLeftChild() {
        if (parent != null) {
            return this==parent.left;
        }
        return false;
    }

    public boolean isRigthChild() {
        if (parent != null) {
            return this == parent.right;
        }
        return false;
    }

    public int getMaxHeight() {
        if (!hasAnyChildren()) {
            return 0;
        }else {
            int leftMaxHeight=1;
            int rightMaxHeight =1;
            if (hasLeftChild()) {
                leftMaxHeight = leftMaxHeight + this.left.getMaxHeight();
            }
            if (hasRightChild()) {
                rightMaxHeight = rightMaxHeight + this.right.getMaxHeight();
            }
            return leftMaxHeight >= rightMaxHeight ? leftMaxHeight : rightMaxHeight;
        }

    }





    public BinaryNode(T data, BinaryNode<T> parent) {
        this.data = data;
        this.parent = parent;
    }



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }

    public BinaryNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }

    public BinaryNode<T> getParent() {
        return parent;
    }

    public void setParent(BinaryNode<T> parent) {
        this.parent = parent;
    }
}
