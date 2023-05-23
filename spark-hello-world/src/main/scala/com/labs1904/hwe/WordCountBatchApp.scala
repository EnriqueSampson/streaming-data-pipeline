package com.labs1904.hwe

import org.apache.log4j.Logger
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object WordCountBatchApp {
  lazy val logger: Logger = Logger.getLogger(this.getClass)
  val jobName = "WordCountBatchApp"

  def main(args: Array[String]): Unit = {
    logger.info(s"$jobName starting...")
    try {
      val spark = SparkSession.builder()
        .appName(jobName)
        .config("spark.sql.shuffle.partitions", "3")
        .master("local[*]")
        .getOrCreate()
      import spark.implicits._

      val sentences = spark.read.csv("src/main/resources/sentences.txt").as[String]
      //for each would be more of an action




      // TODO: implement me
      val counts  = sentences.flatMap(sentence =>{
        (splitSentenceIntoWords(sentence))
      })

//      counts.printSchema()

      val wordsDF = counts.groupBy("value").count()
      wordsDF.printSchema()
      wordsDF.show()
      wordsDF.foreach(wordCount=>println(wordCount))
//      val wordsDF = spark.sql(
//        """
//          |CREATE TABLE
//          |""".stripMargin)

    } catch {
      case e: Exception => logger.error(s"$jobName error in main", e)
    }
  }

  // TODO: implement this function
  // HINT: you may have done this before in Scala practice...
  def splitSentenceIntoWords(value: String): Array[String] = {
    value.split(" ")
  }

}
