import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * Created by LISHUAI on 2018/9/8.
 */
public class HelloWorldSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private int referenceRandom;
    private static final int MAX_RANDOM=10;

    public HelloWorldSpout(){
        final Random rand = new Random();
        referenceRandom = rand.nextInt(MAX_RANDOM);
    }

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    public void nextTuple() {
        Utils.sleep(100);
        final Random rand = new Random();
        int instanceRandom = rand.nextInt(MAX_RANDOM);
        if(instanceRandom == referenceRandom){
            collector.emit(new Values("Hello world!"));
        }else {
            collector.emit(new Values("Other Random Word"));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }
}
