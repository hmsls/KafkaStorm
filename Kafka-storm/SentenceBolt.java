import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SentenceBolt extends BaseBasicBolt {
    private List<String> words = new ArrayList<String>();

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    public void execute(Tuple tuple, BasicOutputCollector collector) {
        String word = tuple.getString(0);
        if(StringUtils.isBlank(word)){
            return;
        }
        System.out.println("Received Word:" + word);
//        words.add(word);
//        if(word.endsWith(".")){
//            collector.emit(ImmutableList.of((Object)StringUtils.join(words,' ')));
//            words.clear();
//        }
        collector.emit(new Values(word));
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }
}
