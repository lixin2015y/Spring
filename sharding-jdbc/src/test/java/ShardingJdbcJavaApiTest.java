import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ShardingJdbcJavaApiTest {

    protected static final String USER_LOGIC_TB = "t_user";

    DataSource dataSource;

    //    @Before
    public void buildShardingDataSource() throws SQLException {
        /*
         * 1. 数据源集合：dataSourceMap
         * 2. 分片规则：shardingRuleConfig
         *
         */

        DataSource druidDs1 = buildDruidDataSource(
                "jdbc:mysql://localhost:3306/db1?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=true&serverTimezone=UTC",
                "root", "123456");

        DataSource druidDs2 = buildDruidDataSource(
                "jdbc:mysql://localhost:3306/db2?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=true&serverTimezone=UTC",
                "root", "123456");
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 添加数据源.
        // 两个数据源ds_0和ds_1
        dataSourceMap.put("db0", druidDs1);
        dataSourceMap.put("db1", druidDs2);

        /**
         * 需要构建表规则
         * 1. 指定逻辑表.
         * 2. 配置实际节点》
         * 3. 指定主键字段.
         * 4. 分库和分表的规则》
         *
         */
        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        //消息表分片规则
        TableRuleConfiguration userShardingRuleConfig = userShardingRuleConfig();
        shardingRuleConfig.getTableRuleConfigs().add(userShardingRuleConfig);
        // 多数据源一定要指定默认数据源
        // 只有一个数据源就不需要
        shardingRuleConfig.setDefaultDataSourceName("db0");

        Properties p = new Properties();
        //打印sql语句，生产环境关闭
        p.setProperty("sql.show", Boolean.TRUE.toString());

        dataSource = ShardingDataSourceFactory.createDataSource(
                dataSourceMap, shardingRuleConfig, p);

    }

    public DataSource buildDruidDataSource(String url, String username, String password) {
        DruidDataSource druidDataSource = new DruidDataSource();

//        druidDataSource.setDriverClassName(Driver.class.getName());
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);

        return druidDataSource;
    }

    protected TableRuleConfiguration userShardingRuleConfig() {
        String logicTable = USER_LOGIC_TB;

        //获取实际的 ActualDataNodes
        String actualDataNodes = "db$->{0..1}.t_user_$->{0..1}";
        TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration(logicTable, actualDataNodes);
        // 设置分表策略
        // inline 模式
        ShardingStrategyConfiguration tableShardingStrategy =
                new InlineShardingStrategyConfiguration("user_id", "t_user_$->{user_id % 2}");
        tableRuleConfig.setTableShardingStrategyConfig(tableShardingStrategy);

        // 配置分库策略（Groovy表达式配置db规则）
        ShardingStrategyConfiguration dsShardingStrategy = new InlineShardingStrategyConfiguration("user_id", "db${user_id % 2}");
        tableRuleConfig.setDatabaseShardingStrategyConfig(dsShardingStrategy);
        tableRuleConfig.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "user_id"));
        return tableRuleConfig;
    }


    @Test
    public void test() throws SQLException {
        // 测试插入用户
        /*
         * 1. 需要到DataSource
         * 2. 通过DataSource获取Connection
         * 3. 定义一条SQL语句.
         * 4. 通过Connection获取到PreparedStament.
         *  5. 执行SQL语句.
         *  6. 关闭连接.
         */


        // * 2. 通过DataSource获取Connection
        Connection connection = dataSource.getConnection();
        // * 3. 定义一条SQL语句.
        // 注意：******* sql语句中 使用的表是 上面代码中定义的逻辑表 *******
        String sql = "insert into t_user(name) values('name-0001')";

        // * 4. 通过Connection获取到PreparedStament.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // * 5. 执行SQL语句.
        preparedStatement.execute();

        sql = "insert into t_user(name) values('name-0002')";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();

        // * 6. 关闭连接.
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void test4() throws SQLException {
        DataSource ebsDb = buildDruidDataSource(
                "jdbc:mysql://172.16.2.204:3306/ebs?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&rewriteBatchedStatements=true&serverTimezone=UTC&allowMultiQueries=true",
                "root", "byxf1qaz");
        Connection connection = ebsDb.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("insert into ebs.user(id, out_id, filed1, filed2) values (?, ?, ?, ?)");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 30000000; i < 60000000; i++) {
            preparedStatement.setString(1, "AAAAAAAAAAAAAAAAAAAAAAAAAAA" + String.format("%08d", i));
            preparedStatement.setString(2, "BBBBBBBBBBBBBBBBBBBBBBBBBBB" + String.format("%08d", i));
            preparedStatement.setString(3, "CCCCCCCCCCCCCCCCCCCCCCCCCCC" + String.format("%08d", i));
            preparedStatement.setString(4, "DDDDDDDDDDDDDDDDDDDDDDDDDDD" + String.format("%08d", i));
            preparedStatement.addBatch();
            if (i % 500000 == 0) {
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
                connection.commit();
                stopWatch.stop();
                System.out.println(String.format("%08d", i) + "耗时：" + stopWatch.getLastTaskTimeMillis() / 1000 + "s");
                stopWatch.start();
            }
        }
        int[] ints = preparedStatement.executeBatch();
        preparedStatement.clearBatch();
        connection.commit();
        System.out.println("完成" + stopWatch.getTotalTimeSeconds() + "s");

    }

}
