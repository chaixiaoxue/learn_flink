package com.cxx.flink.wc;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author chaixiaoxue
 * @version 1.0
 * @date 2020/12/11 10:59
 */
public class WorldCount {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> stringDataStreamSource = env
            .readTextFile("D:\\code\\learn_flink\\dataStreamApi\\src\\main\\resources\\hello.txt");
        SingleOutputStreamOperator<Tuple2<String, Integer>> map = stringDataStreamSource
            .flatMap(new FlatMapFunction<String, String>() {
                @Override
                public void flatMap(String s, Collector<String> collector) throws Exception {
                    for (String s1 : s.split(" ")) {
                        collector.collect(s1);
                    }
                    ;
                }
            }).map(new MapFunction<String, Tuple2<String, Integer>>() {
                @Override
                public Tuple2<String, Integer> map(String s) throws Exception {
                    return new Tuple2<>(s, 1);
                }
            });
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = map.keyBy(0).sum(1);
        sum.print();
        env.execute("a");
    }
}
