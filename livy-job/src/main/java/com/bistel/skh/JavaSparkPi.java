package com.bistel.skh;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

import java.util.ArrayList;
import java.util.List;

/**
 * Computes an approximation to pi
 * Usage: JavaSparkPi [partitions]
 */
public final class JavaSparkPi {

	public static void main(String[] args) throws Exception {
		SparkConf conf = new SparkConf().setAppName("JavaSparkPi");
		JavaSparkContext jsc = new JavaSparkContext(conf);

		int slices = (args.length == 1) ? Integer.parseInt(args[0]) : 2;
		int n = 1000000 * slices;
		List<Integer> l = new ArrayList<Integer>(n);
		for (int i = 0; i < n; i++) {
			l.add(i);
		}

		JavaRDD<Integer> dataSet = jsc.parallelize(l, slices);

		int count = dataSet.map(new Function<Integer, Integer>() {
					@Override
					public Integer call(Integer integer) throws Exception {
						double x = Math.random() * 2 - 1;
						double y = Math.random() * 2 - 1;
						return (x * x + y * y < 1) ? 1 : 0;
					}
				}).reduce(new Function2<Integer, Integer, Integer>() {
					@Override
					public Integer call(Integer integer, Integer integer2) throws Exception {
						return integer + integer2;
					}
				});

		System.out.println("Pi is roughly " + 4.0 * count / n);

		jsc.stop();
	}

}
