package com.bistel.skh;

import org.apache.livy.Job;
import org.apache.livy.JobContext;
import org.apache.livy.LivyClient;
import org.apache.livy.LivyClientBuilder;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class PiJob implements Job<Double>, Function<Integer, Integer>, Function2<Integer, Integer, Integer> {

	private final int samples;

	public PiJob(int samples) {
		this.samples = samples;
	}

	@Override
	public Double call(JobContext ctx) throws Exception {
		List<Integer> sampleList = new ArrayList<Integer>();
		for (int i = 0; i < samples; i++) {
			sampleList.add(i + 1);
		}

		return 4.0d * ctx.sc().parallelize(sampleList).map(this).reduce(this) / samples;
	}

	@Override
	public Integer call(Integer v1) {
		double x = Math.random();
		double y = Math.random();
		return (x*x + y*y < 1) ? 1 : 0;
	}

	@Override
	public Integer call(Integer v1, Integer v2) {
		return v1 + v2;
	}
}

public class PiApp {
	public static void main(String[] args) throws Exception {
		final String livyServerURI = "http://localhost:8998";

		LivyClient client = new LivyClientBuilder()
			.setURI(new URI(livyServerURI))
			.build();

		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(10);
		System.out.println(System.currentTimeMillis() - start);
		try {
			for (int i = 0; i < 50; i++) {
				int slices = (int)(Math.random() * 100) + 1;

				// Thread 사용할 경우
				//new Thread(new AsyncJobThread(client, slices, i)).start();

				// ExecutorService 사용할 경우
				executor.execute(new AsyncJobThread(client, slices, i));

				//double pi = client.submit(new PiJob(slices)).get();
				//System.out.printf("[%s :: %s] Pi is roughly %s\n", i, slices, pi);
			}
			executor.shutdown();
			while (!executor.isTerminated()){ }
		} finally {
			client.stop(true);
			System.out.println((System.currentTimeMillis() - start) + " ms");
		}
	}
}

class AsyncJobThread implements Runnable {
	private LivyClient client;
	private int slices;
	private int index;
	public AsyncJobThread(LivyClient client, int slices, int index) {
		this.client = client;
		this.slices = slices;
		this.index  = index;
	}
	public void run() {
		try {
			double pi = this.client.submit(new PiJob(this.slices)).get();
			System.out.printf("[%s :: %s :: %s] Pi is roughly %s\n", Thread.currentThread().getName(), index, slices, pi);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}