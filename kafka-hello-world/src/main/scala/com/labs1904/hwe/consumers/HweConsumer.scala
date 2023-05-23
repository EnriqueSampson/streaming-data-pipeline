package com.labs1904.hwe.consumers

import com.labs1904.hwe.util.Constants._
import com.labs1904.hwe.util.Util
import net.liftweb.json.DefaultFormats
import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.slf4j.LoggerFactory

import java.time.Duration
import java.util.Arrays


case class RawUser(id:Int, name:String, email:String,birthday:String)

case class EnrichedUser(id:Int, name:String, email:String,birthday:String,numberAsWord:String,hweDeveloper:String)

object HweConsumer {

  private val logger = LoggerFactory.getLogger(getClass)


  val consumerTopic: String = "question-1"
  val producerTopic: String = "question-1-output"

  implicit val formats: DefaultFormats.type = DefaultFormats

  def main(args: Array[String]): Unit = {

    // Create the KafkaConsumer
    val consumerProperties = Util.getConsumerProperties(BOOTSTRAP_SERVER)
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](consumerProperties)

    // Create the KafkaProducer
    val producerProperties = Util.getProperties(BOOTSTRAP_SERVER)
    val producer = new KafkaProducer[String, String](producerProperties)

//    var messageToSend = ""
    //    Create a record producer


    // Subscribe to the topic
    consumer.subscribe(Arrays.asList(consumerTopic))



    while ( {
      true
    }) {
      // poll for new data
      val duration: Duration = Duration.ofMillis(100)
      val records: ConsumerRecords[String, String] = consumer.poll(duration)


      records.forEach((record: ConsumerRecord[String, String]) => {
        // Retrieve the message from each record
        val message = record.value()
//        logger.info(s"Message Received: $message")
        val splitMessage = message.split("\t")
//          print(splitMessage(0))
//        logger.info(splitMessage(0))
//        RawUser(splitMessage(0),)
        val sample = RawUser(splitMessage(0).toInt, splitMessage(1), splitMessage(2),splitMessage(3))
        val enrichedSample = EnrichedUser(sample.id,sample.name,sample.email,sample.birthday,Util.mapNumberToWord(sample.id),"Enrique Sampson")
        val  toSend = enrichedSample.id + "," +  enrichedSample.name + "," + enrichedSample.email + "," + enrichedSample.birthday + "," + enrichedSample.numberAsWord + "," + enrichedSample.hweDeveloper

        val record2 = new ProducerRecord[String, String](producerTopic, toSend)
        logger.info(toSend)
        producer.send(record2)
      })
    }
  }
}