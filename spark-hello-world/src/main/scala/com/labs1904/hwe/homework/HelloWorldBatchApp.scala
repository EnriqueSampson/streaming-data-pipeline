package com.labs1904.hwe.homework

import org.apache.log4j.Logger
import org.apache.spark.sql.{Dataset, SparkSession}

object HelloWorldBatchApp {
  lazy val logger: Logger = Logger.getLogger(this.getClass)
  val jobName = "HelloWorldBatchApp"

  def main(args: Array[String]): Unit = {
    try {
      logger.info(s"$jobName starting...")
      //TODO: What is a spark session - Why do we need this line?
//      A SparkSession is the entry point to use Spark's DataFrame and Dataset APIs, as well as SQL functionality.
//      It allows you to access Spark functionality with a single object and simplifies the usage of various Spark components.
//      You need this line to create a SparkSession, which initializes the Spark application
//      and provides the necessary context to interact with Spark's core functionalities.
      val spark = SparkSession.builder()
        .appName(jobName)
        .config("spark.sql.shuffle.partitions", "3").master("local[*]").getOrCreate()
        //TODO- What is local[*] doing here?
//  local[*] is a configuration setting for the master in Spark,
//    specifying that the application should run locally using all available cores on the machine.
//    The asterisk (*) is a wildcard that represents the number of available cores.
//    This is useful for development and testing purposes,
//    as it allows you to run Spark applications on your local machine without needing a separate Spark cluster.

        //TODO- What does Get or Create do?
//  The getOrCreate() method is used to obtain an existing SparkSession or create a new one if none exists.
  //  It checks whether a SparkSession with the same configurations already exists in the current context.
  //  If it exists, it returns the existing SparkSession; otherwise, it creates a new one with the specified configurations.
  //  This is useful to ensure that only a single SparkSession is active in the application,
  //  which helps manage resources and maintain application state.

      import spark.implicits._
      val sentences: Dataset[String] = spark.read.csv("src/main/resources/sentences.txt").as[String]
      // print out the names and types of each column in the dataset
      sentences.printSchema
      // display some data in the console, useful for debugging
      //TODO- Make sure this runs successfully
      sentences.show(truncate = false)
    } catch {
      case e: Exception => logger.error(s"$jobName error in main", e)
    }
  }
}
