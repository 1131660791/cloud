package com.base.api.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 精准分库PreciseShardingDBAlgorithm
 * 范围分库RangeShardingDBAlgorithm
 * <p>
 * <p>
 * 精准分表PreciseShardingTableAlgorithm
 * 范围分表RangeShardingTableAlgorithm：
 * # 标准分片策略 StandardShardingStrategy
 * StandardShardingStrategy 只支持单分片键，提供 PreciseShardingAlgorithm（精准分片）和 RangeShardingAlgorithm（范围分片）两个分片算法
 * PreciseShardingAlgorithm是必选的，用于处理=和IN的分片
 * RangeShardingAlgorithm是可选的，用于处理BETWEEN AND分片，如果不配置RangeShardingAlgorithm，SQL中的BETWEEN AND将按照全库路由处理
 * 如果需要使用RangeShardingAlgorithm，必须和PreciseShardingAlgorithm配套使用
 *
 * 分片算法
 * Sharding提供了以下4种算法接口：
 *
 * PreciseShardingAlgorithm:精确分片算法，用于处理使用单一键作为分片键的=与IN进行分片的场景。需要配合 StandardShardingStrategy 使用。
 * RangeShardingAlgorithm:范围分片算法用于处理使用单一键作为分片键的BETWEEN AND进行分片的场景。需要配合 StandardShardingStrategy 使用。如果需要使用RangeShardingAlgorithm，必须和PreciseShardingAlgorithm配套使用，否则会报错
 * HintShardingAlgorithm:Hint分片算法
 * ComplexKeysShardingAlgorithm:复合分片算法
 *
 * @author hzw
 */
@Component
public class DataTypeRoutingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> tableNames,
                             PreciseShardingValue<Long> shardingValue) {
        for (String key : tableNames) {
            if (key.endsWith(String.valueOf(shardingValue.getValue() % tableNames.size()))) {
                System.out.println("table key：" + key);
                return key;
            }
        }
        throw new UnsupportedOperationException();
    }
}
