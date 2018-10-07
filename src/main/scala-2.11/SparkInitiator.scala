
package main.scala

import com.satyae0962.SparkUtil.StreamingUtils
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by satyanarayana on 30/09/18.
  *
  * Job Launch Signature
  * spark-submit --master local --jars /usr/local/Cellar/sparkjars/twitter4j-core-4.0.4.jar,/usr/local/Cellar/sparkjars/kafka-clients-1.1.0.jar,
  * /usr/local/Cellar/sparkjars/spark-streaming-twitter_2.11-2.2.0.jar,/usr/local/Cellar/sparkjars/twitter4j-stream-3.0.3.jar
  * --class main.scala.SparkInitiator sparkconsumer_2.11-1.0.jar @amazonIN
  *
  */
object SparkInitiator extends StreamingUtils with Serializable{

  final val consumerKey="CANz4Sfe3gMNCF4w0aRPYtXPZ"
  final val consumerSecret="Optb6Bwt06HqjyGtqWU92HsaC7ovsGm1J9Fpjv7Uc9zBARSE58"
  final val accessToken="3757067713-c0kX4qZBPmav5DjBpwTmiCAkMihIoZZMMi5h192"
  final val accessTokenSecret="dsu3QhKFrnJAbKo6ufqxfghJO4nRurroDF6Pw5rqr6EZW"

  def SparkInit(): StreamingContext ={
    val conf = new SparkConf().setAppName("Spark App Twitter Sentiment")
    val ssc = new StreamingContext(conf, Seconds(2))
    return ssc
  }

  def main(args: Array[String]): Unit = {
    val hashtag = args(0)
    val ssc = SparkInit()
    super.StreamProcessor(ssc,consumerKey,consumerSecret,accessToken,accessTokenSecret)

    ssc.start()
    ssc.awaitTermination()
    println("Stoping the Spark Streaming Context")
  }


}
