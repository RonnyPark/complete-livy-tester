package com.bistel.skh;

public class PiApp {
	public static void main(String[] args) throws Exception {
		final String livyServerURI = "http://localhost:8998";

		StringBuffer requestBody = new StringBuffer()
				.append("{")
				.append("\"file\": \"/Users/ronny/Bistel/Test/SKH/complete-livy-tester/livy-job/target/livy-job-1.0-SNAPSHOT.jar\",")
				.append("\"className\": \"com.bistel.skh.JavaSparkPi\",")
				.append("\"args\": [\"10\"]")
				.append("}");

		HttpClient.post(livyServerURI, "/batches", requestBody.toString());

//
//		LivyClient client = new LivyClientBuilder()
//			.setURI(new URI(livyServerURI))
//			.build();
//
//		long start = System.currentTimeMillis();
//		ExecutorService executor = Executors.newFixedThreadPool(10);
//		System.out.println(System.currentTimeMillis() - start);
//		try {
//			for (int i = 0; i < 50; i++) {
//				int slices = (int)(Math.random() * 100) + 1;
//
//				// Thread 사용할 경우
//				//new Thread(new AsyncJobThread(client, slices, i)).start();
//
//				// ExecutorService 사용할 경우
//				executor.execute(new AsyncJobThread(client, slices, i));
//
//				//double pi = client.submit(new PiJob(slices)).get();
//				//System.out.printf("[%s :: %s] Pi is roughly %s\n", i, slices, pi);
//			}
//			executor.shutdown();
//			while (!executor.isTerminated()){ }
//		} finally {
//			client.stop(true);
//			System.out.println((System.currentTimeMillis() - start) + " ms");
//		}
	}
}


//class AsyncJobThread implements Runnable {
//	private LivyClient client;
//	private int slices;
//	private int index;
//	public AsyncJobThread(LivyClient client, int slices, int index) {
//		this.client = client;
//		this.slices = slices;
//		this.index  = index;
//	}
//	public void run() {
//		try {
//			double pi = this.client.submit(new JavaSparkPi(this.slices)).get();
//			System.out.printf("[%s :: %s :: %s] Pi is roughly %s\n", Thread.currentThread().getName(), index, slices, pi);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}