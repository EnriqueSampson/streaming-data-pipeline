# Overview

By now you've seen some different Big Data frameworks such as Kafka and Spark. Now we'll be focusing in on HBase. In this homework, your
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
#### What is a NoSQL database? 
* A NoSQL database is a non-relational database that stores and retrieves data using non-tabular structures. Unlike traditional relational databases that store data in tables with strict schema definitions, NoSQL databases use a variety of data models such as key-value, document, column-family, and graph. NoSQL databases are highly scalable and flexible, making them ideal for handling large amounts of unstructured or semi-structured data, and for use cases such as real-time analytics, content management, and social networking.


#### In your own words, what is Apache HBase? 
* Apache HBase is an open-source, distributed, column-oriented database modeled after Google's Bigtable. It is built on top of Apache Hadoop and provides a fault-tolerant way of storing large amounts of sparse data in a distributed environment. HBase is designed to handle big data workloads and supports random read/write access to petabytes of data. It has a flexible schema model that allows for dynamic column addition or removal without requiring a table schema change. HBase is widely used in various applications, such as online serving of big data, Internet of Things (IoT), fraud detection, and social media analytics.


#### What are some strengths and limitations of HBase? 
* [HBase By Examples](https://sparkbyexamples.com/apache-hbase-tutorial/)
* HBase has several strengths, including its ability to handle large amounts of sparse data, its scalability, fault tolerance, and high availability. It is designed to be highly performant, and its column-oriented storage model makes it efficient for querying and retrieving specific columns or rows of data. HBase also provides support for advanced features like data versioning, atomic writes, and distributed transactions.

* However, HBase also has some limitations. One of the main limitations is that it is not as flexible as other NoSQL databases when it comes to querying data. HBase's data model is optimized for efficient retrieval of data by row and column keys, but it doesn't support complex queries as well as document-oriented databases like MongoDB or Couchbase. Additionally, HBase is not well-suited for handling transactions that involve multiple rows or tables, and it can be complex to set up and manage in a distributed environment. Finally, HBase is not a good fit for use cases that require real-time data analysis or machine learning, as it lacks built-in support for these features.

#### Explain the following concepts: 
* Rowkey
* In HBase, a Rowkey is a unique identifier for a row of data, consisting of one or more bytes that are used to determine the physical location of the row in the storage system. The Rowkey is used to identify the data that needs to be retrieved or updated, and it is also used to sort data in ascending or descending order.
* Column Qualifier
* A Column Qualifier, also known as a Column Name, is used to identify a specific column within a row. The Column Qualifier is combined with the Column Family to create a unique identifier for each column in a table. The Column Qualifier can be any string of bytes, and it can be used to store different types of data, such as timestamps, integers, or strings.
* Column Family
* A Column Family is a grouping of one or more related columns in an HBase table. Column Families are used to organize data in a way that is efficient for storage and retrieval. All columns within a Column Family share the same prefix in their names, which allows them to be stored together on disk and retrieved more efficiently. Column Families are defined when a table is created and cannot be modified afterwards without recreating the table.


#### What are the differences between Get and Put commands in HBase? 
* [HBase commands](https://www.tutorialspoint.com/hbase/hbase_create_data.htm)
* The Get command retrieves data from a table based on the Rowkey and any optional parameters, such as column qualifiers or timestamps. It returns the data as a Result object, which contains all of the column values for the specified row. The Get command does not modify the data in the table, and it can be used for both single-row and multi-row queries.
* The Put command, on the other hand, is used to insert or update data in a table. It takes a Rowkey and a list of Column Family/Qualifier/Value tuples and writes the data to the specified columns in the table. If the Rowkey already exists in the table, the Put command will update the existing data with the new values. If the Rowkey does not exist, the Put command will create a new row in the table with the specified data.
* In summary, the main difference between the Get and Put commands in HBase is that Get retrieves data from a table without modifying it, while Put inserts or updates data in a table.


#### What is the HBase Scan command for? 
* [HBase Scan](https://www.tutorialspoint.com/hbase/hbase_scan.htm)
* In HBase, the Scan command is used to read a range of data from a table. It allows you to specify a start and stop rowkey, as well as any additional filter parameters, to retrieve a subset of the data in the table. The Scan command returns a ResultScanner object, which can be used to iterate over the results of the query in batches.
* The Scan command is useful for retrieving large amounts of data from a table, or for performing range-based queries that cannot be accomplished using a single Get command. By specifying start and stop rowkeys, you can retrieve a specific subset of the data in a table, or you can use additional filter parameters to further narrow down the results. The Scan command can also be used to specify additional options, such as the maximum number of rows to return, the columns to retrieve, and the order in which the results should be sorted.
* Overall, the Scan command provides a powerful way to read and process large amounts of data in HBase, and it is a key tool for building scalable, distributed data processing applications.

#### What was the most interesting aspect of HBase when went through all the questions? 
* I suppose that the instresting aspect for me is that is write heavy preferred while mongo is read heavy preferred.

Seperation of concerns via clusters

