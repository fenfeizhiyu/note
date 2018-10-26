package model;

import data.constant.RedisConstant;
import io.github.benas.randombeans.randomizers.number.IntegerRandomizer;

import java.util.Random;

/**
 * @Author yu.yang
 * @Date 2018/10/26 9:48
 *
 * 节点
 */
public class ZskiplistNode<T> {


    /**
     * 节点数据
     */
    private T obj;

    /**
     * 评分
     */
    private double score;
    /**
     * 后退节点
     */
    private ZskiplistNode<T> preNode;

    /**
     * 层数
     */
    private ZskiplistLevel[] levels;



    public ZskiplistNode(int level, double score, T obj){
            this.score=score;
            this.obj=obj;
            levels = new ZskiplistLevel[level];
    }


    public int randRomLevel(){
        int level=0;
        while (level<= RedisConstant.maxLevel) {
            level++;
            int num=IntegerRandomizer.aNewIntegerRandomizer().getRandomValue();
            if (num %2 != 0) {
                break;
            }
        }
        return level;
    }


    /**
     * 层
     */
    class ZskiplistLevel<T>{
        /**
         * 前进
         */
        ZskiplistNode<T> nextNode;

        /**
         * 层数跨度
         */
        int span;

        public ZskiplistNode<T> getNextNode() {
            return nextNode;
        }

        public void setNextNode(ZskiplistNode<T> nextNode) {
            this.nextNode = nextNode;
        }

        public int getSpan() {
            return span;
        }

        public void setSpan(int span) {
            this.span = span;
        }
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public ZskiplistNode<T> getPreNode() {
        return preNode;
    }

    public void setPreNode(ZskiplistNode<T> preNode) {
        this.preNode = preNode;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ZskiplistLevel[] getLevels() {
        return levels;
    }

    public void setLevels(ZskiplistLevel[] levels) {
        this.levels = levels;
    }
}
