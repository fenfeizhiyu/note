package solution;

import java.util.*;

public class SubString {


    public static void main(String[] args){
        String s = "13aabbcc";
        s = preHandleString(s);
        System.out.println(s);
        Queue<String> queue = new PriorityQueue<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length()-o1.length();
            }
        });
        for(int i=0;i<s.length();i++){
            int res=centerSubString(i, s,queue);
            System.out.println(res);
        }
        System.out.println("res:"+queue.poll());


    }

    public static int centerSubString(int center,String s,Queue<String> queue){
        int i=1;
        while ((center - i)>=0&&(center + i+1)<=s.length()){
            String sub=s.substring(center - i, center + i+1);
            if (!isPlalindrome(sub)) {
                queue.add(sub);
                return getStringLength(sub,i);
            }
            i++;
        }
        i--;
        queue.add(s.substring(center - i, center + i+1));
        return getStringLength(s.substring(center - i, center + i+1),i);
    }

    public static int getStringLength(String sub,int i){
         return (sub.length()-1);
    }


    public static String findLongestPlalindromeString(){
        String s = "";
        // 先预处理字符串
        String str = preHandleString(s);
        int len = str.length();
        // 右边界
        int rightSide = 0;
        // 右边界对应的回文串中心
        int rightSideCenter = 0;
        // 保存以每个字符为中心的回文长度一半（向下取整）
        int[] halfLenArr = new int[len];
        int center = 0;
        int longestHalf = 0;
        return "";

    }


    public static String preHandleString(String s){
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        sb.append('#');
        for(int i = 0; i < len; i++) {
            sb.append(s.charAt(i));
            sb.append('#');
        }
        return sb.toString();
    }

    /**
     * 是否是回文字符串
     * @return
     */
    public static boolean isPlalindrome(String s ){
        int len = s.length();
        for(int i = 0; i < len / 2; i++) {
            if(s.charAt(i) != s.charAt(len - 1- i)) {
                return false;
            }
        }
        return true;
    }



    public static void test2(){
        int[][] c = new int[5][4];
        System.out.println(c.length);
    }

    public  static int test(){
        String s = "dvdf";
        int maxNum=0;
        int start=0,end=0;
        HashSet<Character> set = new HashSet<>();
        while (start < s.length()) {
            char c=s.charAt(end);
            if(set.contains(c)){
                int l=(end-start);
                maxNum=(l>maxNum)?l:maxNum;
                start++;
                end=start;
                set.clear();
            }else{
                set.add(c);
                end++;
                if (end >= s.length()) {
                    int l=(end-start);
                    maxNum=(l>maxNum)?l:maxNum;
                    break;
                }
            }
        }
        return maxNum;
    }










    public static void printNum(){
        int[] nums=new int[]{3,2,13,41,44,22,42,1,5,6};
       int max= getMaxChildAddNum(nums,3);
        System.out.println("max:"+max);
    }

    /**
     * 取连续的字串和
     * @param nums
     * @param n
     * @return
     */
    public static int getMaxChildAddNum(int[] nums,int n){
        int maxNum=0;
        for(int i=0;i<nums.length;i++){
            int m=i+n-1;
            if (m >= nums.length) {
                break;
            }
            int temp=sumNum(subNumArray(nums, i, m));
            if (temp > maxNum) {
                maxNum=temp;
            }
        }
        return maxNum;
    }

    public static int sumNum(int[] nums){
        int sum=0;
        for(int n:nums){
            sum=sum+n;
        }
        return sum;
    }


    public static int[] subNumArray(int[] nums,int start,int end){
        int[] sub=new int[(end-start)+1];
        for(int i=0;i<sub.length;i++){
            sub[i] = nums[start];
            start++;
        }
        StringBuilder sb = new StringBuilder();
        for (int k : sub) {
            sb.append(k).append(";");
        }
        System.out.println(sb.toString());
        return sub;
    }


    /**
     * 打印所有
     */
    public static void printSubStr(){
        String s = "pwwkew";
        for(int i=0;i<s.length();i++){
            for(int k=i+1;k<=s.length();k++){
                System.out.println(s.substring(i, k));
            }
        }
    }
}
