import com.lee.App;
import com.lee.service.StudentService;
import com.lee.service.TeacherService;
import com.lee.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


public class MySpringBootTest {

    class Solution {
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            int m = obstacleGrid.length;
            int n = obstacleGrid[0].length;
            if (m == 1 && n == 1) return obstacleGrid[m - 1][n - 1] == 1 ? 0 : 1;
            int[][] dp = new int[m][n];
            int minGridIndex = m;
            for (int i = 0; i < m; i++) {
                if (obstacleGrid[i][0] == 1 || i >= minGridIndex) {
                    minGridIndex = i;
                    dp[i][0] = 0;
                } else {
                    dp[i][0] = 1;
                }
            }
            minGridIndex = n;
            for (int i = 0; i < n; i++) {
                if (obstacleGrid[0][i] == 1 || i >= minGridIndex) {
                    minGridIndex = i;
                    dp[0][i] = 0;
                } else {
                    dp[0][i] = 1;
                }
            }
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    if (obstacleGrid[i][j] == 1) continue;
                    // 这里有可能是堵死了
                    if (obstacleGrid[i][j - 1] == 0) {
                        // 上方未堵死
                        dp[i][j] = dp[i][j] + dp[i][j - 1];
                    }
                    // 左边未堵死
                    if (obstacleGrid[i - 1][j] == 0 ) {
                        dp[i][j] = dp[i][j] + dp[i - 1][j];
                    }

                }
            }
            return dp[m - 1][n - 1];
        }

    }

    @Test
    public void test() {
        Solution solution = new Solution();
        int[][] ints = new int[][]{{0, 0, 0, 0, 0}, {0, 0, 0, 0, 1}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 0}};

        int i = solution.uniquePathsWithObstacles(ints);
        System.out.println(i);

    }

}
