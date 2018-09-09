import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by LISHUAI on 2018/9/8.
 */
public class HelloWorldBolt extends BaseRichBolt {

    int myCount ;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    public void execute(Tuple tuple) {
        String test = tuple.getStringByField("sentence");
        if("Hello world!".equals(test)){
            myCount ++ ;
            System.out.println("Found a Hello World ! myCount is now :" + Integer.toString(myCount));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
