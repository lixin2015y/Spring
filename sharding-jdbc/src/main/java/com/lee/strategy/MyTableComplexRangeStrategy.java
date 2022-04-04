package com.lee.strategy;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.rmi.MarshalledObject;
import java.util.Arrays;
import java.util.Collection;

public class MyTableComplexRangeStrategy implements ComplexKeysShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Integer> shardingValue) {
        System.out.println("availableTargetNames = " + availableTargetNames);
        Range<Integer> userIdRange = shardingValue.getColumnNameAndRangeValuesMap().get("userId");
        System.out.println("userIdRange = " + userIdRange);
        Collection<Integer> userIdColumnNamesShardingValues = shardingValue.getColumnNameAndShardingValuesMap().get("userId");
        System.out.println("userIdColumnNamesShardingValues = " + userIdColumnNamesShardingValues);
        return Arrays.asList("t_user_0", "t_user_1");
    }
}
