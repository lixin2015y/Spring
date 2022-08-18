package com.lee.algorithm.mianshi;

public class GunDongShuZu {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        int i = find(arr, 0, arr.length - 1, target);
        System.out.println(i);
    }

    public static int find(int[] arr, int start, int end, int target) {
        if (start > end) {
            return -1;
        }
        int middle = (start + end) / 2;

        if (arr[middle] == target) return middle;

        if (arr[middle] > arr[0]) {
            // 在第一个平台
            if (arr[middle] < target) {
                int resultTmp = findInOrderArr(arr, start, target - 1, target);
                if (resultTmp == -1) {
                    return find(arr, middle + 1, end, target);
                } else {
                    return resultTmp;
                }
            } else {
                return find(arr, middle + 1, end, target);
            }
        } else {
            // 第二个平台, 第二个平台右边是有序的
            if (arr[middle] < target) {
                int resultTmp = findInOrderArr(arr, middle + 1, end, target);
                if (resultTmp == -1) {
                    return find(arr, start, middle - 1, target);
                } else {
                    return resultTmp;
                }
            } else {
                return find(arr, start, middle - 1, target);
            }
        }
    }

    public static int findInOrderArr(int[] arr, int start, int end, int target) {
        while (start < end) {
            int middle = (start + end) / 2;
            if (arr[middle] == target) {
                return middle;
            } else if (arr[middle] > target) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }
        return -1;
    }
}
