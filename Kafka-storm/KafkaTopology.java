import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by LISHUAI on 2018/9/9.
 */
public class KafkaTopology {
    public static void main(String[] args){
        //zk连接串
        ZkHosts zh = new ZkHosts("60.24.65.179:2181");
        //kafka主题信息，第一个是zookeeper连接信息，第二个是主题，第三个是zk中的根路径，第四个是主题的路径，前后都没有/
        SpoutConfig kafkaConfig = new SpoutConfig(zh,"lishuai","","brokers/topics/lishuai");
        //主题消息选择以字符串的形式
        kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

//         kafkaConfig.forceFromStart = true;
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafkaSpout",new KafkaSpout(kafkaConfig),1);
        builder.setBolt("sentenceBolt",new SentenceBolt(),1).globalGrouping("kafkaSpout");
        builder.setBolt("PrinterBolt",  new PrinterBolt(),1).globalGrouping("sentenceBolt");

        Config conf = new Config();
        LocalCluster lc = new LocalCluster();
        lc.submitTopology("KafkaTopogy",conf,builder.createTopology());

        try {
//            StormSubmitter.submitTopology("KS",conf,builder.createTopology());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            System.out.println("Waiting to consume from kafka");
//            Thread.sleep(60000);
//        } catch (Exception exception) {
//            System.out.println("Thread interrupted exception : " + exception);
//        }
        //cluster.killTopology("KafkaToplogy");
        //cluster.shutdown();
    }
}
