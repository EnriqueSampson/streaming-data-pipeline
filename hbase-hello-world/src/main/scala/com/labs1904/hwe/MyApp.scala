package com.labs1904.hwe

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Delete, Get, HBaseAdmin, Put, Row, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.logging.log4j.{LogManager, Logger}
import scala.collection.JavaConverters._

import scala.List
import scala.language.postfixOps

object MyApp {
  lazy val logger: Logger = LogManager.getLogger(this.getClass)


  def main(args: Array[String]): Unit = {
    logger.info("MyApp starting...")
    var connection: Connection = null
    try {
      val conf = HBaseConfiguration.create()
      conf.set("hbase.zookeeper.quorum", "hbase01.labs1904.com:2181")
      connection = ConnectionFactory.createConnection(conf)
      // Example code... change me
//      logger.debug(connection.getAdmin().listTableNames().mkString("\n"))

      val table =  connection.getTable(TableName.valueOf("esampson:users"))
      logger.debug(table)
      val scanner = new Scan().withStartRow(Bytes.toBytes("10000001")).withStopRow(Bytes.toBytes("10006001"))
      var one = new Get(Bytes.toBytes("9005729"))
      var two = new Get(Bytes.toBytes("500600"))
      var three = new Get(Bytes.toBytes("30059640"))
      var four = new Get(Bytes.toBytes("6005263"))
      var five = new Get(Bytes.toBytes("800182"))
      var arr:List[Get] = List[Get](one, two, three, four, five)
//      val result = table.get(get)
      val scannerTable = table.getScanner(scanner)
      var count = 0
//      val delete = new Delete(Bytes.toBytes("99"))
//      table.delete(delete)

//      for (rows <- arr) {
//        table.batch(arr, null)
//      }
     arr.foreach(obj =>{
       table.get(obj)
       logger.debug(table.get(obj).toString)
     })



//      logger.debug(table.batch(arr.asJava,newarray))

//      scannerTable.iterator().forEachRemaining(obj =>{
//        count+=1
//        logger.debug(Bytes.toString(obj.getValue(Bytes.toBytes("f1"),Bytes.toBytes("name"))))
//      })
//      logger.debug(count)

//      401 count


//      logger.debug(scanner.getStartRow())
//      tab
//      logger.debug(result)
//      logger.debug(table.getScanner(scanner).next())
//      result.getValue(Bytes.toBytes("f1"),Bytes.toBytes("mail"))
//      logger.debug(Bytes.toString(result.getValue(Bytes.toBytes("f1"),Bytes.toBytes("mail"))))
//      logger.debug(result)

//      val p = new Put(Bytes.toBytes("99"))
//      p.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("username"),Bytes.toBytes("DE-HWE"))
//      p.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"),Bytes.toBytes("The Panther"))
//      p.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("sex"),Bytes.toBytes("F"))
//      p.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("favorite_color"),Bytes.toBytes("pink"))
//      logger.debug(p)
//      table.put(p)

    } catch {
      case e: Exception => logger.error("Error in main", e)
    } finally {
      if (connection != null) connection.close()
    }
  }
}
