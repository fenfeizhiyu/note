package model;

/**
 * @Author yu.yang
 * @Date 2018/10/15 10:07
 */
public interface TreeNode {

    /**
     * 是否有子节点 （是否叶子节点）
     * @return
     */
    boolean hasAnyChildren();

    /**
     * 是否有双子节点
     * @return
     */
    boolean hasBothChildren();

    /**
     * 是否根节点
     * @return
     */
    boolean isRoot();


    /**
     * 是否有左子节点
     * @return
     */
    boolean hasLeftChild();


    /**
     * 是否有右子节点
     * @return
     */
    boolean hasRightChild();


    /**
     * 是否左节点
     * @return
     */
    boolean isLeftChild();

    /**
     * 是否右节点
     * @return
     */
    boolean isRigthChild();

    /**
     * 获取树的高度
     * @return
     */
    int getMaxHeight();

}
