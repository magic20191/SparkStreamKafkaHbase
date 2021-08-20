package org.kin.kafka.producer;


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Kafka java API:Producer
 */
public class KafkaProducer extends Thread{

//    public static void main(String[] args) throws IOException {
//        KafkaProducer a = new KafkaProducer();
//
//        for(Iterator<String> it = a.getMsg().iterator(); it.hasNext();)
//        {
//            System.out.println(it.next());
//        }
//
//    }


    public String topic;

    public Producer<Integer,String> producer;

    public KafkaProducer(){}

    public KafkaProducer(String topic){
        this.topic=topic;

        Properties properties=new Properties();

        properties.put("metadata.broker.list",KafkaProperties.BROKER_LIST);
        properties.put("serializer.class","kafka.serializer.StringEncoder");
        //设置生产者与消费者的生产握手机制：0代表不需要Broker回复消息
        //1表示等到Broker回复消息之后继续生产
        //-1表示需要所有的Broker都回复消息之后才继续，这种更严格，数据不会丢失，持久性更好
        properties.put("request.required.acks","1");

        ProducerConfig config=new ProducerConfig(properties);

        producer=new Producer<Integer, String>(config);
    }

    @Override
    public void run() {
        int messageId=1;

        try {
            for(Iterator<String> it = this.getMsg().iterator(); it.hasNext();)
            {
                String message=it.next();
                producer.send(new KeyedMessage<Integer, String>(topic,message));
//                System.out.println(message);
                messageId++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMsg() throws IOException {

        FileReader fr=new FileReader("D:\\code\\SparkStreamKafkaHbase\\src\\main\\java\\org\\kin\\kafka\\producer\\msg2.txt");
        BufferedReader br=new BufferedReader(fr);
        String line="";
        List<String> list=new ArrayList<String>();

        String[] arrs=null;
        while ((line=br.readLine())!=null) {
//            arrs=line.split(",");
//            System.out.println(arrs[0] + " : " + arrs[1] + " : " + arrs[2]);
            list.add(line);
//            System.out.println(line);
        }
        br.close();
        fr.close();
        return list;

    }

}
