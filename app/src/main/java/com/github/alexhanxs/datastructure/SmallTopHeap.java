package com.github.alexhanxs.datastructure;

import java.util.Arrays;

/**
 * Created by Alexhanxs on 2018/4/12.
 */

public class SmallTopHeap<E extends Comparable> {
    Object[] queue;

    int growStep = 8;

    public SmallTopHeap() {
        queue = new Object[8];
    }

    private void grow() {
        int newLength = queue.length;
        if (queue.length < 32) {
            newLength += growStep;
        } else {
            newLength += newLength >>> 1;
        }

        queue = Arrays.copyOf(queue, newLength);
    }

    public void add(E e) {
        int realSize = getRealSize();

        if (realSize == queue.length) {
            grow();
        }
        if (realSize == 0) {
            queue[0] = e;
        } else {
            fitUP(realSize, e);
        }
    }

    public void fitUP(int fitIndex, E e) {
        while (fitIndex > 0) {
            int parent = (fitIndex - 1) >>> 1;
            if (e.compareTo(queue[parent]) > 0) {
                break;
            }
            queue[fitIndex] = queue[parent];
            fitIndex = parent;
        }

        queue[fitIndex] = e;
    }

    public E pop() {
        int realSize = getRealSize();

        int s = realSize - 1;
        E e = (E) queue[0];
        if (s == 0) {
            queue[0] = null;
        } else {
            E last = (E) queue[s];
            queue[s] = null;
            fitDown(0, last);
        }

        return e;
    }


    public void fitDown(int fitIndex, E e) {
        int half = (getRealSize() - 1) >>> 1;
        while (fitIndex < half) {
            int child = fitIndex * 2 + 1;
            int right = child + 1;
            E c = (E) queue[child];
            if (right < getRealSize() && c.compareTo(queue[right]) > 0) {
                child = right;
                c = (E) queue[child];
            }

            if (e.compareTo(c) < 0) {
                break;
            }
            queue[fitIndex] = c;
            fitIndex = child;
        }

        queue[fitIndex] = e;
    }

    public int getItemIndex(E e) {
        for (int i = 0; i < getRealSize(); i++) {
            if (e.compareTo(queue[i]) == 0)
                return i;
        }
        return -1;
    }

    public void remove(E e) {
        int deleteIndex = getItemIndex(e);
        if (deleteIndex == getRealSize() - 1) {
            queue[deleteIndex] = null;
        } else {
            E last = (E) queue[getRealSize() - 1];
            queue[getRealSize() - 1] = null;
            fitDown(deleteIndex, last);
        }
    }

    public int getRealSize() {
        int i = 0;
        for (; i < queue.length; i++) {
            Object o = queue[i];
            if (o == null)
                break;
        }
        return i;
    }

    public void floorSee(StringBuilder sb) {
        int curFloor = 0;
        int curFloorStart = (int) Math.pow(2, curFloor) - 1;
        int curFloorEnd = curFloorStart + (int) Math.pow(2, curFloor) - 1;

        for (int i = curFloorStart; i <= curFloorEnd && i < getRealSize(); i++) {

            Object o = queue[i];
            if (queue != null) {
                sb.append(o).append("_");
            } else {
                break;
            }
            if (i == curFloorEnd) {
                sb.append("\n");
                curFloor ++;
                curFloorStart = (int) Math.pow(2, curFloor) - 1;
                curFloorEnd = curFloorStart + (int) Math.pow(2, curFloor) - 1;
            }
        }
    }

    public static void main(String[] args) {
        SmallTopHeap<Integer> heap = new SmallTopHeap<>();
        heap.add(20);
        heap.add(24);
        heap.add(19);
        heap.add(5);
        heap.add(25);
        heap.add(30);
        heap.add(1);

//        heap.pop();
//        heap.pop();
        heap.remove(19);

        StringBuilder builder = new StringBuilder();
        heap.floorSee(builder);

        System.out.print(builder.toString());
     }
}
