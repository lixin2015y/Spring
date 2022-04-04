package com.lee.strategy;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Arrays;
import java.util.Collection;

public class MyRangeStrategy implements RangeShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Integer> shardingValue) {
        System.out.println(shardingValue.getValueRange().lowerEndpoint());
        System.out.println(shardingValue.getValueRange().upperEndpoint());
        return Arrays.asList("t_" + shardingValue.getLogicTableName() + "_0", "t_" + shardingValue.getLogicTableName() + "_1");
    }
}
