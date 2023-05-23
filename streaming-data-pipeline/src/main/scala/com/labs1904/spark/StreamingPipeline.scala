package com.labs1904.spark

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Get}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.log4j.Logger
import org.apache.spark.sql.{Dataset, ForeachWriter, SparkSession}
import org.apache.hadoop.hbase.TableName
import org.apache.spark.sql.streaming.{OutputMode, Trigger}


/**
 * Spark Structured Streaming app
 *
 */
object StreamingPipeline {
  case class Review(marketplace: String, customer_id: String, review_id: String,
                    product_id: String, product_parent: String, product_title: String, product_category: String,
                    star_rating: Int, helpful_votes: Int, total_votes: Int, vine: String, verified_purchase: String,
                    review_headline: String, review_body: String, review_date: String)
//  TODO: probably change review_date to date TYPE in hive

  case class User(birthdate:String,mail:String,name:String,sex:String,username:String)

  case class UserWithReview(birthdate:String,mail:String,name:String,Sex:String,username:String,marketplace: String, customer_id: String,
                            review_id: String, product_id: String, product_parent: String, product_title: String, product_category: String,
                            star_rating: Int, helpful_votes: Int, total_votes: Int, vine: String, verified_purchase: String,
                            review_headline: String, review_body: String, review_date: String)


  lazy val logger: Logger = Logger.getLogger(this.getClass)

  val jobName = "StreamingPipeline"

  val hdfsUrl = "hdfs://hbase01.labs1904.com:8020"
  val bootstrapServers = "b-3-public.hwekafkacluster.6d7yau.c16.kafka.us-east-1.amazonaws.com:9196,b-2-public.hwekafkacluster.6d7yau.c16.kafka.us-east-1.amazonaws.com:9196,b-1-public.hwekafkacluster.6d7yau.c16.kafka.us-east-1.amazonaws.com:9196"
  val username: String = "1904labs"
  val password: String = "1904labs"
  val hdfsUsername = "esampson" // TODO: set this to your handle

  //Use this for Windows
//  val trustStore: String = "src\\main\\resources\\kafka.client.truststore.jks"
  //Use this for Mac
  val trustStore: String = "src/main/resources/kafka.client.truststore.jks"

  def main(args: Array[String]): Unit = {
    try {
      val spark = SparkSession.builder()
        .config("spark.sql.shuffle.partitions", "3")
        .config("spark.hadoop.dfs.client.use.datanode.hostname", "true")
        .config("spark.hadoop.fs.defaultFS", hdfsUrl)
        .appName(jobName)
        .master("local[*]")
        .getOrCreate()
      import spark.implicits._

      val ds = spark
        .readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", bootstrapServers)
        .option("subscribe", "reviews")
        .option("startingOffsets", "earliest")
        .option("maxOffsetsPerTrigger", "20")
        .option("startingOffsets","earliest")
        .option("kafka.security.protocol", "SASL_SSL")
        .option("kafka.sasl.mechanism", "SCRAM-SHA-512")
        .option("kafka.ssl.truststore.location", trustStore)
        .option("kafka.sasl.jaas.config", getScramAuthString(username, password))
        .load()
        .selectExpr("CAST(value AS STRING)").as[String]

      // TODO: implement logic here
      val result = ds

      val reviews = result.map(value => {
        val columns = value.split("\t")
        Review(columns(0), columns(1), columns(2), columns(3), columns(4), columns(5), columns(6), columns(7).toInt, columns(8).toInt, columns(9).toInt, columns(10), columns(11), columns(12), columns(13), columns(14))
      })

      val hBaseReviews:Dataset[UserWithReview] = reviews.mapPartitions(partition =>{
        val hbaseConf = HBaseConfiguration.create()
        hbaseConf.set("hbase.zookeeper.quorum", "hbase01.labs1904.com:2181")
        val connection = ConnectionFactory.createConnection(hbaseConf)
        val table =  connection.getTable(TableName.valueOf("esampson:users"))

        val iter = partition.map(review =>{
          val get = new Get(Bytes.toBytes(review.customer_id)).addFamily(Bytes.toBytes("f1"))
          val result = table.get(get)

          val birthdate = Bytes.toString(result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("birthdate")))
          val mail = Bytes.toString(result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("mail")))
          val name = Bytes.toString(result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("name")))
          val sex = Bytes.toString(result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("sex")))
          val username = Bytes.toString(result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("username")))

          val user = User(birthdate, mail, name, sex, username)
          UserWithReview(user.birthdate,user.mail,user.name,user.sex,user.username,review.marketplace,review.customer_id,
            review.review_id,review.product_id,review.product_parent,review.product_title,review.product_category,
            review.star_rating,review.helpful_votes,review.total_votes,review.vine,review.verified_purchase,
            review.review_headline,review.review_body,review.review_date)
        }).toList.iterator
        connection.close()

        iter
      })
//      hBaseReviews.foreach(hBaseReview =>println(hBaseReview))

      // Write output to console
//      val query = hBaseReviews.writeStream
//        .outputMode(OutputMode.Append())
//        .format("console")
//        .option("truncate", false)
//        .trigger(Trigger.ProcessingTime("5 seconds"))
//        .start()

      // Write output to HDFS
      val query = hBaseReviews.writeStream
        .outputMode(OutputMode.Append())
        .format("csv")
        .option("delimiter", "\t")
        .option("path", s"/user/${hdfsUsername}/reviews_csv")
        .option("checkpointLocation", s"/user/${hdfsUsername}/reviews_checkpoint")
        .partitionBy("star_rating")
        .trigger(Trigger.ProcessingTime("5 seconds"))
        .start()

      query.awaitTermination()
    } catch {
      case e: Exception => logger.error(s"$jobName error in main", e)
    }
  }

  def getScramAuthString(username: String, password: String) = {
    s"""org.apache.kafka.common.security.scram.ScramLoginModule required
   username=\"$username\"
   password=\"$password\";"""
  }
}
