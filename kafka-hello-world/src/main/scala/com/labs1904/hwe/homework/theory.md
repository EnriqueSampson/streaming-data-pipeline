# Overview

Kafka has many moving pieces, but also has a ton of helpful resources to learn available online. In this homework, your
challenge is to write answers that make sense to you, and most importantly, **in your own words!**
Two of the best skills you can get from this class are to find answers to your questions using any means possible, and to
reword confusing descriptions in a way that makes sense to you. 

### Tips
* You don't need to write novels, just write enough that you feel like you've fully answered the question
* Use the helpful resources that we post next to the questions as a starting point, but carve your own path by searching on Google, YouTube, books in a library, etc to get answers!
* We're here if you need us. Reach out anytime if you want to ask deeper questions about a topic 
* This file is a markdown file. We don't expect you to do any fancy markdown, but you're welcome to format however you like

### Your Challenge
1. Create a new branch for your answers 
2. Complete all of the questions below by writing your answers under each question
3. Commit your changes and push to your forked repository

## Questions
#### What problem does Kafka help solve? Use a specific use case in your answer 
* Helpful resource: [Confluent Motivations and Use Cases](https://youtu.be/BsojaA1XnpM)
Kafka basically solves the problem of streaming multiple applications to a main data store.
In a way its like the stream between applications to a main data store.
Maybe a mobile app that generates a large amount of data, such as user events like clicks and searches. You also have a backend system that needs to process this data in real-time.
Maybe receive the user events from the mobile app, store them in a topic, and deliver them to the backend system for processing. The backend system can then consume the events from Kafka, process them in real-time, and produce new events that can be sent back to Kafka or other systems.

#### What is Kafka?
* Helpful resource: [Kafka in 6 minutes](https://youtu.be/Ch5VhJzaoaI) 
Kafka is basically a real time data processing stream between applications and a data store.
* in kafka terms a broker.

#### Describe each of the following with an example of how they all fit together: 
 * Topic
 * In Kafka, a topic is a category or stream of messages. In our example, we might create a topic called "user-activity" to represent the stream of user activity data that we're collecting from the website.
 * Producer
 * A producer in Kafka is a system that generates messages and publishes them to a topic. In our example, the website would act as the producer, generating user activity messages and publishing them to the "user-activity" topic.
 * Consumer 
 * A consumer in Kafka is a system that subscribes to a topic and reads messages from it. In our example, we might have a backend system that acts as the consumer, subscribing to the "user-activity" topic and processing the user activity messages in real-time.
 * Broker
 * A broker in Kafka is a server that acts as a hub for producers and consumers to exchange messages. In our example, we might have a cluster of Kafka brokers that store and manage the "user-activity" topic, allowing producers to publish messages and consumers to read them.
 * Partition
 *  A partition in Kafka is a way of splitting a topic into smaller, more manageable chunks of data. Each partition is an ordered, immutable sequence of messages that can be stored on a separate broker within a Kafka cluster. In our example, we might use partitions to split the "user-activity" topic into smaller chunks, allowing us to scale our processing and storage independently.

#### Describe Kafka Producers and Consumers
A Kafka Producer is a client application that generates and publishes data to Kafka topics. Producers are responsible for sending messages to Kafka brokers, which then store and distribute the messages to the appropriate partitions. Producers can send messages in different formats, such as plain text, JSON, or binary, and they can also specify additional metadata such as message keys and timestamps. Producers can publish messages to one or more topics, and they can send messages synchronously or asynchronously.

On the other hand, a Kafka Consumer is a client application that subscribes to one or more Kafka topics and reads data from them. Consumers are responsible for reading messages from the Kafka brokers, processing them, and acknowledging that they have been received. Consumers can read messages in different formats, such as plain text, JSON, or binary, and they can specify which partitions and topics they want to read from. Consumers can read messages in different ways, such as one message at a time or in batches, and they can also control the rate at which they read messages.

#### How are consumers and consumer groups different in Kafka? 
* Helpful resource: [Consumers](https://youtu.be/lAdG16KaHLs)
* Helpful resource: [Confluent Consumer Overview](https://youtu.be/Z9g4jMQwog0)
  In summary, while a single consumer reads all messages from all partitions in a Kafka topic, a consumer group consists of multiple consumers that collectively consume all messages from all partitions in a topic. Consumer groups provide a scalable and fault-tolerant way to process high volumes of data in real-time, making them a critical component of many Kafka-based applications.

#### How are Kafka offsets different than partitions? 
A partition is a unit of parallelism in Kafka, which is used to split a topic into multiple ordered and immutable chunks of data. Each partition is stored on a separate broker within a Kafka cluster and can be consumed by one or more consumers. Partitions provide scalability and fault tolerance by allowing Kafka to distribute data across multiple brokers and consumers.

On the other hand, an offset is a unique identifier that is assigned to each message within a partition. Offsets are used to keep track of the position of a consumer within a partition and determine which messages have already been processed. When a consumer reads messages from a partition, it keeps track of the offset of the last message it read, and uses this offset to resume reading from where it left off in the event of a failure or restart.

In summary, partitions are used to split a Kafka topic into multiple ordered and immutable chunks of data, while offsets are used to keep track of the position of a consumer within a partition. Partitions provide scalability and fault tolerance, while offsets enable reliable message consumption and processing. Together, partitions and offsets form the foundation of Kafka's distributed and scalable data processing capabilities.

Example of a partition:

Partition 0: Contains user activity data from users with usernames starting with letters A to F.
Partition 1: Contains user activity data from users with usernames starting with letters G to M.
Partition 2: Contains user activity data from users with usernames starting with letters N to Z.
In this example, the user-activity topic is partitioned based on the usernames of the users. This allows for parallel processing of data and ensures that the data is evenly distributed across the brokers in the Kafka cluster.

Example of an offset:

Partition 0: Message with offset 1000 - "User clicked on product X"
Partition 1: Message with offset 500 - "User searched for keyword Y"
Partition 2: Message with offset 200 - "User added item Z to cart"
In this example, each partition has a unique set of offsets that correspond to the messages within that partition. For example, the first message in Partition 0 has an offset of 1000, the first message in Partition 1 has an offset of 500, and the first message in Partition 2 has an offset of 200. When a consumer reads messages from a partition, it keeps track of the offset of the last message it read, and uses this offset to resume reading from where it left off in the event of a failure or restart.

Overall, partitions and offsets are fundamental concepts in Apache Kafka that enable the distributed and scalable processing of data streams.

#### How is data assigned to a specific partition in Kafka? 
In Apache Kafka, the assignment of data to a specific partition is determined by a partitioning strategy. Kafka supports two types of partitioning strategies:

Hash-based partitioning: In this strategy, Kafka uses the message key to determine which partition a message should be assigned to. Kafka applies a hash function to the message key to generate a hash value, and then uses this value to determine the partition. The idea is that messages with the same key will always be assigned to the same partition, which ensures that related messages are processed in the correct order.

Round-robin partitioning: In this strategy, Kafka assigns messages to partitions in a round-robin fashion, cycling through all available partitions one by one. This ensures an even distribution of messages across all partitions, but it doesn't take into account the content of the messages.

The partitioning strategy is specified when the producer sends a message to Kafka. If a key is provided, Kafka will use the hash-based partitioning strategy to determine which partition the message should be assigned to. If no key is provided, Kafka will use the round-robin partitioning strategy to distribute the message across all available partitions.

It's important to note that the number of partitions for a topic is specified when the topic is created and cannot be changed without re-creating the topic. This means that the partitioning strategy must be carefully chosen to ensure even distribution of messages across partitions, while also taking into account any constraints or requirements for message ordering.

Overall, the partitioning strategy is a critical component of Apache Kafka that ensures the correct distribution and processing of data across partitions. The choice of partitioning strategy depends on the specific requirements of the application, such as message ordering, data distribution, and scalability.

#### Describe immutability - Is data on a Kafka topic immutable? 
Immutability means that once a piece of data has been created or stored, it cannot be changed or modified. In other words, it is a property of data that guarantees that it will remain unchanged over time.

In Apache Kafka, data that is stored in a topic is typically immutable. Once a message is published to a topic, it cannot be modified or deleted. Instead, new messages can be published to the topic to update or augment the existing data. This is because Kafka stores data in a log-like structure, where messages are appended to the end of the log in the order in which they were received. This allows for efficient and scalable storage of large amounts of data, while also ensuring that the data is immutable.

The immutability of data in Kafka is an important characteristic that enables a variety of use cases, such as log aggregation, stream processing, and event-driven architectures. By ensuring that data is immutable, Kafka provides a reliable and scalable platform for processing and analyzing real-time data streams, while also guaranteeing that the integrity of the data is maintained over time.

Overall, immutability is a fundamental concept in Kafka that ensures the reliability and integrity of data in distributed and scalable systems.

#### How is data replicated across brokers in kafka? If you have a replication factor of 3 and 3 brokers, explain how data is spread across brokers
* Helpful resource [Brokers and Replication factors](https://youtu.be/ZOU7PJWZU9w)
  In Apache Kafka, data is replicated across brokers to provide fault tolerance and high availability. Replication ensures that if a broker fails, the data can still be accessed and processed by other brokers in the Kafka cluster.

When a topic is created in Kafka, a replication factor is specified. The replication factor determines the number of copies of each partition that will be created in the Kafka cluster. For example, if a topic has a replication factor of 3, then each partition will be replicated three times across the brokers in the Kafka cluster.

When a message is published to a partition, it is first written to the leader replica for that partition. The leader replica is responsible for handling all reads and writes for that partition, and it is the only replica that can be written to directly by producers or read from directly by consumers.

Once the message has been written to the leader replica, it is replicated to the other replicas for that partition. The replicas are stored on different brokers in the Kafka cluster to ensure fault tolerance and high availability. Each replica is assigned a unique replica ID, and they are stored on different brokers than the leader replica to prevent data loss in the event of a broker failure.

To ensure data is evenly distributed across the brokers, Kafka uses a partition assignment algorithm called the "replica assignment algorithm". This algorithm ensures that each broker in the Kafka cluster hosts an even number of replicas for each topic.

For example, if we have a replication factor of 3 and 3 brokers in the Kafka cluster, then each partition will be replicated three times across the brokers. The replica assignment algorithm will ensure that each broker hosts one replica of each partition. This means that each broker will have two replicas that are leaders for their respective partitions, and one replica that is a follower for another partition.

Overall, data is replicated across brokers in Kafka to provide fault tolerance and high availability. The replication factor determines the number of copies of each partition that will be created, and the replica assignment algorithm ensures that the replicas are evenly distributed across the brokers in the Kafka cluster.

#### What was the most fascinating aspect of Kafka to you while learning? 
Honestly, just the idea of integrating this into my current data pipeline. I wonder what I could do with AWS MSK and Firehose. Basically, I wonder if I could push firehose data into MSK and use MSK as the data source instead of storing data in S3. I wonder if that would make read writes better? The safety and replication value is already great. I also wonder if the storage cost of keeping it kafka is better than s3?

Whether to add Kafka as an intermediate buffer between Kinesis Data Firehose and S3 depends on the specific needs of your use case.

If your primary requirement is to store data in S3 for long-term analysis and archival, then using Kinesis Data Firehose to write data directly to S3 can be a simple and cost-effective solution. With this approach, you can store data in S3 and use various analytics tools to perform batch processing or analysis on that data.

On the other hand, if you have additional requirements such as real-time processing, data transformation, or streaming analytics, then adding Kafka as an intermediate buffer can provide additional benefits. Kafka can act as a highly available, low-latency buffer that enables real-time processing of data streams. It also allows you to perform additional processing and transformations on the data before it is written to S3, which can help to simplify downstream analytics or reduce storage costs.

In summary, whether to add Kafka as an intermediate buffer depends on your specific requirements and use case. If you only need to store data in S3 for long-term analysis, then using Kinesis Data Firehose to write data directly to S3 can be a simple and cost-effective solution. However, if you require additional capabilities such as real-time processing, data transformation, or streaming analytics, then adding Kafka as an intermediate buffer can provide additional benefits.

Yes, you can use Amazon MSK and Amazon Kinesis Data Firehose together to stream data in real-time and process it using Apache Kafka.

With this setup, you can use Kinesis Data Firehose to ingest and transform data from various sources such as streaming services, databases, and log files, and then stream that data directly into an Amazon MSK cluster. The data can then be processed and analyzed in real-time using Kafka's stream processing capabilities.

Using Kafka instead of storing data in Amazon S3 can have several advantages. Firstly, Kafka provides a low-latency and real-time data processing capability that can enable faster access to data than S3. Secondly, Kafka's partitioning and replication capabilities ensure high availability and fault tolerance, which can make it a more reliable and resilient data storage solution than S3 in certain use cases.

Regarding storage cost, the cost of storing data in Kafka depends on the size of the Kafka cluster and the number of messages being stored. While Kafka can be less expensive than S3 in some cases, it is important to note that it is designed primarily for real-time data processing and may not be as cost-effective as S3 for long-term data storage.

In summary, using Amazon MSK and Amazon Kinesis Data Firehose together can provide a powerful real-time data processing and storage solution that can be more efficient, reliable, and scalable than using S3 alone. However, the optimal solution depends on the specific requirements of your use case, such as data storage duration, access patterns, and cost considerations.
