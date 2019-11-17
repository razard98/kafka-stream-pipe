package james.kafka.stream.pipe;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

@Slf4j
public class KafkaStreamPipeApplication {

    public static void main(String[] args) {
        //SpringApplication.run(KafkaStreamPipeApplication.class, args);
        Properties props = new Properties();
        //어플리케이션 id
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-pipe");
        //카프카 브로커 정보
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.102:9092,192.168.56.102:9093,192.168.56.102:9094");
        //데이터를 어떠한 형식으로 Read/Write할지를 설정(키/값의 데이터 타입을 지정) - 문자열
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        //stream의 input 에서 output 으로 흐름을 정의
        builder.stream("stream-input").to("stream-output");
        final Topology topology = builder.build();
        log.info("======================== Topology info = {}", topology.describe());

        final KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start();
        log.info("topology start");

        //streams.close();
        //System.exit(0);

    }

}
