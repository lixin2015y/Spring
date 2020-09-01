import java.util.*;

class A {

    public static void main(String[] args) {
        int n = 3;
        int[][] arr = new int[n][n];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = ++n;
            }
        }

        System.out.println(searchMatrix(arr, 6));
    }

    public static boolean searchMatrix(int[][] nums, int x) {
        int i = 0;
        int j = nums.length - 1;
        return search(nums, i, j, x);
    }

    private static boolean search(int[][] nums, int i, int j, int x) {
        if (nums[i][j] == x) {
            return true;
        } else if (nums[i][j] < x) {
            if (++j > nums.length - 1) {
                return false;
            }
            return search(nums, i, j, x);
        } else {
            if (--i < 0) {
                return false;
            }
            return search(nums, i, j, x);
        }

    }
}
