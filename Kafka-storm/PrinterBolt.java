import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.List;
import java.util.Map;

class PrinterBolt extends BaseBasicBolt {
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String sentence = tuple.getString(0);
        sentence = "{" + sentence.substring(1,sentence.length()-1) + "}";
        Map m = GetJsonFieldAndValue.json2Map(sentence);
        GetJsonFieldAndValue.map2List(m);
        for(List l:GetJsonFieldAndValue.fieldsList){
            System.out.println(l);
        }
//        System.out.println("Received Sentence: " + sentence);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
