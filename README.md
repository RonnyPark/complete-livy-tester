livy-env.sh

SPARK_HOME=/Users/ronny/Bistel/Test/SKH/spark-1.6.3-bin-hadoop2.3

SPARK_CONF_DIR=${SPARK_HOME}/conf

livy.conf

livy.spark.master = spark://localhost:7077

livy.file.local-dir-whitelist = /Users/ronny/Bistel/Test/SKH/complete-livy-tester/livy-job/target/

${LIVY_HOME}/rsc-jars
-rw-r--r--@  1 ronny  staff    14163  1 25  2018 livy-api-0.5.0-incubating.jar
-rw-r--r--   1 ronny  staff  3594859  7 27 18:23 livy-client-http-0.5.0-incubating.jar
-rw-r--r--   1 ronny  staff     8890  7 27 21:56 livy-job-1.0-SNAPSHOT.jar
-rw-r--r--@  1 ronny  staff   497227  1 25  2018 livy-rsc-0.5.0-incubating.jar
-rw-r--r--@  1 ronny  staff  2204062  1 25  2018 netty-all-4.0.37.Final.jar
