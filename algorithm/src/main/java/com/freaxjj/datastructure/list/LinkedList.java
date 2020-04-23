package com.freaxjj.datastructure.list;

import com.freaxjj.util.CopyUtils;

import java.io.Serializable;
import java.util.*;

/**
 * @author 刘亚林
 * @description 单链表
 * @create 2020/4/1 15:33
 **/
public class LinkedList {
    private static Random random = new Random(100);
    private static String[] strs = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};

    /**
     * 创建单链表
     * @param clazz
     * @param n
     * @param <T>
     * @return
     */
    public static <T> Node createOne(Class<T> clazz, int n){
        //尾结点，从后往前创建节点
        Node next = null;
        for (int i = 0; i < n; i++) {
            if(clazz == Integer.class){
                Node tail = new Node<>(random.nextInt(10), next);
                next = tail;
            }else if(clazz == String.class){
                String tmp = strs[random.nextInt(10)];
                Node tail = new Node<>(tmp, next);
                next = tail;
            }
        }
        return next;
    }

    /**
     * 根据list创建单链表
     * @param list
     * @return
     */
    public static Node<Integer> createOne(List<Integer> list){
        Node<Integer> head = null;
        Node<Integer> prev = null;
        for (Integer item: list) {
            if(head == null){
                head = new Node<>(item, null);
                prev = head;
            }else {
                prev.next = new Node<>(item, null);
                prev = prev.next;
            }
        }
        return head;
    }


    /**
     * 翻转单链表
     * @param node
     * @return
     */
    public static Node reverse(Node node){
        //反转后的链表尾部
        Node tail = null;

        //node 当前节点
        while (node != null){
            //记录当前节点的下一个节点
            Node prev = node.next;
            //最后一个待处理节点(原链表只有一个节点时,第一个节点就是待处理节点)
            if(prev == null){
                node.next = tail;
                return node;
            }
            //当前节点改变方向
            node.next = tail;
            //移动指针到当前节点（反转后的链表尾部加长）
            tail = node;

            //移动到下一个待处理节点
            node = prev;
        }
        return node;
    }

    /**
     * 翻转单链表，从第m个节点到第n个节点
     * 1 <= m,n <= length
     * @param node
     * @return
     */
    public static Node reverse(final Node node, int m, int n){
        if(m + n < 2 || m >= n){
            return node;
        }

        int i = 1;
        //m节点前的节点
        Node head = null;
        //第m个节点
        Node mNode = node;
        if(mNode != null){
            //循环到第m个节点
            while(i < m){
                head = mNode;
                mNode = mNode.next;
                i++;
                //m > length
                if(mNode == null){
                    return node;
                }
            }
            //保存第m个节点
            Node mTail = mNode;

            //m-n链表 反转后的头节点，最后应该是n节点
            Node reverse = null;
            for (int j = m; j <= n; j++) {
                //n > length
                if(mNode == null){
                    return node;
                }
                //保存next节点
                Node next = mNode.next;
                //当前节点指向 反转好的链表头节点
                mNode.next = reverse;

                reverse = mNode;
                mNode = next;
            }

            //反转后的尾巴连下个（剩下的）节点
            mTail.next = mNode;
            //head接上n节点
            if(head != null){
                head.next = reverse;
            }else {
                return reverse;
            }
        }

        return node;
    }

    /**
     * 划分链表使得所有小于x的节点排在大于等于x的节点之前（保留两部分内链表节点原有的相对顺序。）
     * 创建新链表
     * @param head
     * @param x
     * @return
     */
    public static Node<Integer> partition(Node<Integer> head, int x) {
        Node<Integer> node = head;
        Node<Integer> tail = null;

        //new list
        Node<Integer> small = null;
        Node<Integer> prev = null;

        for(;node != null; node = node.next){
            if(node.value < x){
                Node<Integer> tmp = new Node<>(node.value, null);
                if(small == null){
                    small = tmp;
                    prev = small;
                }else {
                    prev.next = tmp;
                    prev = tmp;
                }

                if(tail != null){
                    tail.next = null;
                }
            }else {
                if(tail == null){
                    head = node;
                    tail = node;
                }else {
                    tail.next = node;
                    tail = node;
                }
            }
        }

        if(prev != null){
            if(head != null){
                prev.next = head;
                tail.next = null;
            }
            return small;
        }

        return head;
    }

    /**
     * 划分链表使得所有小于x的节点排在大于等于x的节点之前（保留两部分内链表节点原有的相对顺序。）
     * 原理类似冒泡
     * @param head
     * @param x
     * @return
     */
    public static Node<Integer> partition2(Node<Integer> head, int x) {
        //<x的元素尾节点
        Node<Integer> prev = null;
        Node<Integer> node = head;

        if(node != null){
            //node循环到第一个>=x的元素A
            while(node != null && node.value < x){
                prev = node;
                node = node.next;
            }

            while (node != null){
                //移动node到最后一个>=x的元素
                while(node.next != null && node.next.value >= x){
                    node = node.next;
                }
                if(node.next == null){
                    return head;
                }

                //将小于x的元素移到前面来
                Node<Integer> small = node.next;
                node.next = small.next;
                if(prev == null){//前面没有小于x的元素
                    small.next = head;
                    head = small;
                }else {
                    small.next = prev.next;
                    prev.next = small;
                }

                prev = small;
            }
        }

        return head;
    }

    private static void swapNode(Node<Integer> one, Node<Integer> another){
        if(one == another){
            return;
        }
        int tmp = one.value;
        one.value = another.value;
        another.value = tmp;
    }

    /**
     * 判断两单链表是否互逆（使用java包装类）
     * @param one
     * @param another
     * @return
     */
    private static boolean isReverse(Node one, final Node another){
        //使用java包装类逆序
        List list = new java.util.LinkedList();
        while (one != null){
            list.add(one.value);
            one = one.next;
        }
        Collections.reverse(list);

        Node node = another;
        int j = 0;
        for (; j < list.size(); j++) {
            if(!list.get(j).equals(node.value)){
                return false;
            }
            node = node.next;
        }
        if(j < list.size() || node != null){
            return false;

        }
        return true;
    }

    /**
     * 打印单链表
     * @param head
     */
    public static void print(final Node head){
        Node node = head;
        System.out.print("链表序列为：");
        while (node != null){
            System.out.print(node.value + " ");
            node = node.next;
        }
    }

    private static class Node<T> implements Serializable {
        Node<T> next;
        T value;

        public Node(T value, Node next){
            this.next = next;
            this.value = value;
        }
    }

    public static void main(String[] args) throws Exception {
        //-----------------------随机创建单链表------------------------
/*        Node original = LinkedList.createOne(Integer.class, 10);
        LinkedList.print(original);*/


        //-----------------------反转单链表------------------------
/*        Node clone = CopyUtils.deepClone(original);

        //reverse
        //System.out.println();
        Node newHead = LinkedList.reverse(original);
        //LinkedList.print(newHead);

        System.out.println("两单链表互逆：" + LinkedList.isReverse(clone, newHead));
        //System.out.println("两单链表互逆：" + LinkedList.isReverse(original, newHead));*/

        //-----------------------反转链表m-n------------------------
/*        Node list = reverse(original, 1, 4);
        LinkedList.print(list);*/

        //-----------------------单链表分区------------------------
        Node<Integer> node = createOne(Arrays.asList(3,3,1,2,4));
        LinkedList.print(node);
        //Node partition = partition(node, 3);
        Node partition = partition2(node, 3);
        LinkedList.print(partition);

    }

}
