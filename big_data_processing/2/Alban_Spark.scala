// Databricks notebook source
import sys.process._
"wget -P /tmp https://www.gakhov.com/static/www/datasets/stratahadoop-BCN-2014.json" !!

val localpath="file:/tmp/stratahadoop-BCN-2014.json"
dbutils.fs.mkdirs("dbfs:/datasets/")
dbutils.fs.cp(localpath, "dbfs:/datasets/")
display(dbutils.fs.ls("dbfs:/datasets/stratahadoop-BCN-2014.json"))
val df = sqlContext.read.json("dbfs:/datasets/stratahadoop-BCN-2014.json")

val rdd = df.select("text").rdd.map(row => row.getString(0))

val hashtags = (rdd.flatMap(_.split(" "))
                   .filter(word => word contains "#")
                   .filter(w => w.split('#').size == 2)
                   .map(w => w.split('#')(1)))
hashtags.distinct().take(10).foreach(println)

rdd.map(s => (s.split('#').size-1, s)).top(10).foreach(println)

(hashtags.map(w => (w, 1))
         .reduceByKey((a,b) => a+b)
         .sortBy(_._2, ascending=false)
         .take(10))

(df.select("user")
   .rdd.map(r => r(0).asInstanceOf[org.apache.spark.sql.Row](35))
   .map(id => (id, 1))
   .reduceByKey((a,b) => a+b)
   .sortBy(_._2, ascending=false)
   .take(10))

(df.select("created_at", "text")
   .rdd
   .map(r => (r.getString(0).split(' ')(2).toFloat, r.getString(1)))
   .filter(p => p._1 > 21)
   .flatMap(p => p._2.split(' ')
     .filter(w => w contains '#').
     .filter(w => w.split('#').size == 2)
     .map(w => w.split('#')(1))
     .map(w => if (w.last == '.') w.dropRight(1).toLowerCase else w.toLowerCase)
     .map(w => (p._1, w)))
   .map(p => (p, 1.0))
   .reduceByKey((a,b) => a+b)
   .map(p => (p._1._2, (p._1._1, p._2)))
   .reduceByKey((p,q) => if (p._1 > q._1) (0, p._2-q._2) else (0, q._2-p._2))
   .filter(p => p._2._1 != 23.0)
   .map(p => (p._2._2, p._1))
   .top(5))
