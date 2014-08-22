package de.kp.spark.weblog
/* Copyright (c) 2014 Dr. Krusche & Partner PartG
* 
* This file is part of the Spark-Weblog project
* (https://github.com/skrusche63/spark-weblog).
* 
* Spark-Weblog is free software: you can redistribute it and/or modify it under the
* terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or (at your option) any later
* version.
* 
* Spark-Weblog is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
* A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* You should have received a copy of the GNU General Public License along with
* Spark-Weblog. 
* 
* If not, see <http://www.gnu.org/licenses/>.
*/

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

import org.apache.spark.rdd.RDD

import org.apache.spark.sql.{SchemaRDD,SQLContext}

object LogInsight {

  /**
   * Load web page description from file system and apply query
   */
  def fromPages(sc:SparkContext,path:String,query:String):SchemaRDD = {
    
    val sqlc = new SQLContext(sc)
    
    val pages = sqlc.jsonFile(path)
    pages.registerAsTable("pages")

    val rows = sqlc.sql(query)    
    rows
    
  }

  /**
   * Apply query to in-memory web page descriptions
   */
  def fromPages(sc:SparkContext,source:RDD[LogPage],query:String):SchemaRDD = {
    
    val sqlc = new SQLContext(sc)
    val schema = sqlc.createSchemaRDD(source)
    
    val pages = sqlc.registerRDDAsTable(schema, "pages")

    val rows = sqlc.sql(query)    
    rows
    
  }
  
}