package model;

/**
 * @Author yu.yang
 * @Date 2018/10/26 9:48
 */
public class Zskiplist<T> {

    private ZskiplistNode<T> head;

    private ZskiplistNode<T> tail;

    private int size;

    /**
     * 节点中的最大层数
     */
    private int maxLevel;

    Zskiplist(){
        head = new ZskiplistNode<T>(32, 0.0, null);
        tail = new ZskiplistNode<T>(32, 0.0, null);
        this.size=0;
        this.maxLevel=0;
    }




    public ZskiplistNode<T> getHead() {
        return head;
    }

    public void setHead(ZskiplistNode<T> head) {
        this.head = head;
    }

    public ZskiplistNode<T> getTail() {
        return tail;
    }

    public void setTail(ZskiplistNode<T> tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}
