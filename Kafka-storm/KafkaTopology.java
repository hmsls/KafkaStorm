import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
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
        //kafka主题信息
        SpoutConfig kafkaConfig = new SpoutConfig(zh,"lishuai","","");
        //
        kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

//         kafkaConfig.fforceFromStart = true;
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafkaSpout",new KafkaSpout(kafkaConfig),1);
        builder.setBolt("sentenceBolt",new SentenceBolt(),1).globalGrouping("kafkaSpout");
        builder.setBolt("PrinterBolt",  new PrinterBolt(),1).globalGrouping("sentenceBolt");

        LocalCluster lc = new LocalCluster();
        Config conf = new Config();
        lc.submitTopology("KafkaTopogy",conf,builder.createTopology());
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
