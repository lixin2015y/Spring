package com.lee.strategy;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExprGroup;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

public class MyPreciseStrategy implements PreciseShardingAlgorithm<Long> {

    /**
     *
     * @param collection 可用数据源和表的名称
     * @param preciseShardingValue 分片键还有逻辑表
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        System.out.println("preciseShardingValue.getColumnName() = " + preciseShardingValue.getColumnName());
        System.out.println("preciseShardingValue.getLogicTableName() = " + preciseShardingValue.getLogicTableName());
        System.out.println("preciseShardingValue.getValue() = " + preciseShardingValue.getValue());
        System.out.println("collection = " + collection);
        BigInteger bigDecimal = BigInteger.valueOf(preciseShardingValue.getValue());
        BigInteger mod = bigDecimal.mod(new BigInteger("4"));
        String key = "t_" +  preciseShardingValue.getLogicTableName() + "_" + mod;
        if (collection.contains(key)) {
            return key;
        }
        throw new UnsupportedOperationException("no route " + key + "impl!");
    }
}
