+ 1: ShardingJDBC 常见分片策略 有两个维度： 
  + （1）分库策略（DatabaseShardingStrategy）：数据被分配的目标数据源。
  + （2）分表策略（TableShardingStrategy）：数据被分配的目标表。
  +  两种分片策略API完全相同，但是分表策略是依赖于分库策略的（即：先分库，然后才有分表）
  
+ 2： Sharding分片策略继承自ShardingStrategy，提供了5种分片策略。
```
io.shardingsphere.core.routing.strategy.ShardingStrategy
       # 标准分片策略:
                提供对SQL语句中的=, IN和BETWEEN AND的分片操作支持
                StandardShardingStrategy 只支持单分片键，提供PreciseShardingAlgorithm（精准分片）和 RangeShardingAlgorithm（范围分片）两个分片算法
                PreciseShardingAlgorithm 是必选的，用于处理=和IN的分片
                RangeShardingAlgorithm 是可选的，用于处理BETWEEN AND分片，如果不配置RangeShardingAlgorithm，SQL中的BETWEEN AND将按照全库路由处理
                如果需要使用RangeShardingAlgorithm，必须和PreciseShardingAlgorithm配套使用
       --io.shardingsphere.core.routing.strategy.standard.StandardShardingStrategy

       # 复核分片策略:
                提供对SQL语句中的=, IN和BETWEEN AND的分片操作支持
                ComplexShardingStrategy支持多字段分片，由于多字段分片之间的关系复杂，因此Sharding-JDBC并未做过多的封装，而是直接将分片键值组合以及分片操作符交于算法接口，完全由应用开发者实现，提供最大的灵活度
       --io.shardingsphere.core.routing.strategy.standard.ComplexShardingStrategy

       # 行内(Inline表达式)分片策略 只支持单字段分片:
                使用Groovy的Inline表达式，提供对SQL语句中的=和IN的分片操作支持。
                InlineShardingStrategy只支持单分片键，对于简单的分片算法，可以通过简单的配置使用，从而避免繁琐的Java代码开发，如: tuser${user_id % 8} 表示t_user表按照user_id按8取模分成8个表，表名称为t_user_0 到 t_user_7
       --io.shardingsphere.core.routing.strategy.standard.InlineShardingStrategy
       
       # Hint分片 策略 HintShardingStrategy：
                在分库分区中，有些特定的SQL，Sharding-jdbc、Mycat、Vitess都不支持（可以查看相关文档各自对哪些SQL不支持）,例如：insert into table1 select * from table2 where ....这种SQL 路由很麻烦，需要解析table2的路由（是在ds0 /ds1 table2_0/table_1）,结果集归并，insert 语句也需要同样的路由解析。这种情况Sharding-jdbc可以使用Hint分片策略来实现各种Sharding-jdbc不支持语法的限制
                
                通过Hint而非SQL解析的方式分片的策略。对于分片字段非SQL决定，而由其他外置条件决定的场景，可使用SQL Hint灵活的注入分片字段
                Hint分片策略是绕过SQL解析的，所以对于这些比较复杂的需要分片的查询，采用Hint分片策略性能可能会更好
                在读写分离数据库中，Hint 可以通过HintManager.setMasterRouteOnly()方法，强制读主库（主从复制存在一定延时，但在某些特定的业务场景中，可能更需要保证数据的实时性）。
       --io.shardingsphere.core.routing.strategy.standard.HintShardingStrategy

        # 不分片的策略。和直接不使用Sharding-JDBC 效果相同
       --io.shardingsphere.core.routing.strategy.standard.NoneShardingStrategy
 ```

+ 3： Mysql主从复制流程：
# 复制方式区分
1. 异步复制
　　My SQL复制默认是异步复制, Master:将事件写入 bingo,提交事务,自身并不知道save是否接收是否处理;这样就会有一个问题,主如果 crash掉了,此时主上已经提交的事务可能并没有传到从上,如果此时强行将从提升为主,可能导致新主上的数据丢失
　　缺点:牺牲了强一致性，不能保证所有事务都被所有save接收。

２. 同步复制
　　所有的从库都执行了该事务才返回给客户端,也就是说 Maste提交事务,直到事务在所有save都已提交,才会返回客户端事务执行完毕信息;
　　缺点:需要等待所有从库执行完该事务才能返回,所以全同步复制的性能必然会受到严重的影响。

３. 半同步复制
　　当 Master上开启半同步复制功前时,至少有一个save开启其功能。当 Master向 slave提交事务,且事务已写入 relay-og中并刷新到磁盘上,Save才会告知 Maste已收到;若 Master提交事务受到阻塞,出现等待超时,在一定时间内 Master没被告知已收到,此时 Master自动转换为异步复制机制。
　　介于异步复制和全同步复制之间,主库在执行完客户端提交的事务后不是立刻返回给套户端,而是等待至少一个从库接收到并写到 relay log中才返回给客户端。

　　缺点：
　　此时,客户端会收到事务提交失败的信息,客户端会重新提交该事务到新的主上,当岩机的主库重新
启动后,以从库的身份重新加入到该主从结构中,会发现,该事务在从库中被提交了两次,一次是之前作为主的时候，一次是被新主同步过来
　　此时,从库已经收到并应用了该事务,但是客户端仍然会收到事务提交失败的信息,重新提交该事务到新的主机上　
半同步复制（mysql5.5以上支持），5.7 中半同步复制已经解决了2次写入的缺点

![主从复制](https://img-blog.csdnimg.cn/20200508160524551.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dvbWVueWlxaWxhbGFsYQ==,size_16,color_FFFFFF,t_70)

+ 4：