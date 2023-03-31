package org.example

/**
 * @author ${user.name}
 */

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions.col

object Practice {

  def divide(x: Int, y: Int): Int = {
    x / y

  }
}

 object App {

  def main(args: Array[String]): Unit = {

        println("After Dividing the result is: " + Practice.divide(30, 10))

        val spark = SparkSession.builder().master("local").appName("ScalaSpark").getOrCreate()

    import spark.implicits._
    // **** Reading File in CSV ****** //

       val dataset1 = spark.read.option("header", true).csv("/Users/A200194659/Downloads/sales_data_sample.csv")
       dataset1.show()
       spark.sql("set spark.sql.legacy.timeParserPolicy=LEGACY")



    // ***** 2.Writing File in Parquet ******//


      // dataset.write.parquet("/Users/A200194659/Documents/Scala_Task1.parquet")
      // dataset1.write.parquet("/Users/A200194659/Downloads/sales_data_sample.parquet")



    // ****** 3.Writing File in TSV  ******//


      // dataset1.write.option("header", "true").option("delimiter", "\t").csv("/Users/A200194659/Downloads/sales_data_sample.tsv")


    // ***** 4. Quantity Ordered per day  ******//


       dataset1.groupBy("ORDERDATE").agg(sum("QUANTITYORDERED").as ("Quantity Ordered per day")).orderBy("ORDERDATE").show()


    // ***** 5. Average ordered value ******//


       dataset1.groupBy("ORDERDATE").agg(avg("QUANTITYORDERED").as ("Average ordered value")).show()


    // ***** 6. Avg price per day   *****//


       dataset1.withColumn( ("PRICEEACH"), col("PRICEEACH").cast("double")).withColumn("QUANTITYORDERED", col("QUANTITYORDERED").cast("double"))
       dataset1.withColumn("Total_Price", col("PRICEEACH").multiply(col("QUANTITYORDERED"))).groupBy("ORDERDATE").agg(avg("Total_Price").as ("avg price per day")).show()


    // ***** 7. Avg sales per month  //*****


      dataset1.groupBy("MONTH_ID").agg(avg("SALES").as ("Avg Sales per Month")).show()


    // *****  8. Avg sales per Day   *****//


      dataset1.groupBy("ORDERDATE").agg(avg("SALES").as ("Avg Sales per Day")).show()


    // ***** 9. Creating Temporary view and applying filter ***** //


      dataset1.createOrReplaceTempView("Sales_Report")

      val sqlDF = spark.sql("""SELECT ORDERNUMBER, STATUS, CUSTOMERNAME,CITY,STATE,POSTALCODE FROM Sales_Report WHERE STATUS= 'Disputed' """)
      sqlDF.show()


    // *****  10. Count of Number of shipped items   //*****

      val No_of_shipped_items = spark.sql("""SELECT count(STATUS) FROM Sales_Report  WHERE STATUS= 'Disputed' """)
      No_of_shipped_items.show()


    // val dummy = dataset1.withColumn("Updated_ORDERDATE", to_date(col("ORDERDATE"), "MM/DD/YYYY H:MM"))
    // dummy.show()

    // dummy.filter("Updated_ORDERDATE" > "2002-05-14").show(false)

  }
}



