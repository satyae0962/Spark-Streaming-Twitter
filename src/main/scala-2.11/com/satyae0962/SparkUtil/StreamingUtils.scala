package com.satyae0962.SparkUtil

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import twitter4j.auth.OAuthAuthorization
import twitter4j.conf.ConfigurationBuilder

import com.satyae0962.kafkaUtils.JavaKafkaProducer;


/**
  * Created by satyanarayana on 19/09/18.
  */
class StreamingUtils {

  val kafkaLoader = new JavaKafkaProducer {}
  val sentiAnalyze = new Sentiment


    def StreamProcessor(ssc: StreamingContext,consumerKey: String, consumerSecret: String, accessToken: String,accessTokenSecret: String) : Unit ={
      val cb = new ConfigurationBuilder()
      cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)
      val hashtag = Array("apple")
      val auth = new OAuthAuthorization(cb.build())
      val dstreamRDD = TwitterUtils.createStream(ssc,Some(auth),hashtag)
      val tweetRDD = dstreamRDD.filter(_.getLang== "en")
      tweetRDD.foreachRDD(x => {TweetProcessor(x)})
      println(tweetRDD)
      tweetRDD.saveAsTextFiles("/usr/local/Cellar")

    }

    def TweetProcessor(tweetRDD: RDD[twitter4j.Status]): Unit ={
    val output  = tweetRDD.filter(_.getLang == "en").map(_.getText).map(x => {sentiAnalyze.detectSentiment(x).toString}).countByValue()

      val record = output.toArray.map(a => a._1 + ":" + a._2.toString).mkString("|")
      /*redisLoader.JedisConnection(record)*/
      kafkaLoader.KafkaTweetProducer(record)
      print("Tweet Record Sentiment :" +record)


    }
}
