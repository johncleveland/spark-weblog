package de.kp.spark.weblog.sample
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

import de.kp.spark.weblog.LogInsight

object WebLogInsight extends SparkApp {
  
  def main(args:Array[String]) {
    
    val sc = createLocalCtx("WebLogInsight")
    
    val path = "/Work/tmp/web-log/"
    /*
     * Retrieve all pages from the W3C log file with a rating greater than 1
     */
    val pages = LogInsight.fromPages(sc, path + "pages", "select * from pages where rating > 1")  
    pages .foreach(r => println(r))

    /*
     * Retrieve all sessions from the W3C log file with checkout abandonment
     */
    val flows = LogInsight.fromFlows(sc, path + "flows", "select * from flows where flowstatus = 1")  
    flows .foreach(r => println(r))

    sc.stop()
    
  }

}