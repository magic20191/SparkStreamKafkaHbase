package org.kin.kafka.producer;

/**
 * 配置属性常量
 */
public class KafkaProperties {
    public static final String ZK="node1:2181,node2:2181,node3:2181";      //Zookeeper地址
    public static final String TOPIC="data";                          //topic名称
    public static final String BROKER_LIST="node1:9092,node2:9092,node3:9092";    //Broker列表
    public static final String GROUP_ID="StreamingJob";                 //消费者使用
}
