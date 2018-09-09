import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * Created by LISHUAI on 2018/9/9.
 */
public class ReadKafka {
    private final ConsumerConnector consumer;
    private final String topic;
    public ReadKafka(String zookeeper, String groupId, String topic){
        Properties prop = new Properties();
        prop.put("zookeeper.connect", zookeeper);
        prop.put("group.id", groupId);
        prop.put("zookeeper.session.timeout.ms", "5000");
        prop.put("zookeeper.sync.time.ms", "2500");
        prop.put("auto.commit.interval.ms", "10000");
        consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(prop));
        this.topic = topic;
    }
    public void testConsumer(){
        Map<String,Integer> topicCount = new HashMap<String,Integer>();
        topicCount.put(topic, new Integer(1));
        Map<String,List<KafkaStream<byte[],byte[]>>> consumerStream = consumer.createMessageStreams(topicCount);
        List<KafkaStream<byte[],byte[]>> streams = consumerStream.get(topic);
        for(final KafkaStream<byte[],byte[]> stream : streams) {
            ConsumerIterator<byte[],byte[]> it = stream.iterator();
            while(it.hasNext()){
                System.out.println("Message from Single Topic :: " +new String(it.next().message()));
            }
        }
        if(consumer !=null){
            consumer.shutdown();
        }
    }
    public static void main(String[] args) {
        String topic = "lishuai";
        ReadKafka sc = new ReadKafka("60.24.65.179:2181", "testgrout", topic);
        sc.testConsumer();
    }
}
