package com.lvxingpai.database

import com.mongodb.{ MongoClient, MongoClientOptions, MongoCredential, ServerAddress }
import org.mongodb.morphia.{ Datastore, Morphia, ValidationExtension }

import scala.collection.JavaConversions._

/**
 * Morphia Datastore的工厂类
 *
 * Created by zephyre on 10/29/15.
 */
object MorphiaFactoryImpl extends MorphiaFactory {

  private def initClient(addresses: Seq[(String, Int)], database: String = "local",
    user: Option[String] = None, password: Option[String] = None,
    options: Option[MongoClientOptions] = None) = {
    val credential = for {
      u <- user
      p <- password
    } yield {
      MongoCredential.createCredential(u, database, p.toCharArray)
    }

    val serverAddresses = addresses map (entry => {
      val (host, port) = entry
      new ServerAddress(host, port)
    })

    val opt = options getOrElse new MongoClientOptions.Builder()
      //连接超时
      .connectTimeout(60000)
      //IO超时
      .socketTimeout(10000)
      //与数据库能够建立的最大连接数
      .connectionsPerHost(50)
      //每个连接可以有多少线程排队等待
      .threadsAllowedToBlockForConnectionMultiplier(50)
      .build()

    if (credential.nonEmpty) {
      new MongoClient(serverAddresses, Seq(credential.get), opt)
    } else {
      new MongoClient(serverAddresses, opt)
    }
  }

  override def newInstance(addresses: Seq[(String, Int)], database: String, adminSource: Option[String],
    user: Option[String], password: Option[String], options: Option[MongoClientOptions],
    validation: Boolean): Datastore = {
    val morphia = new Morphia
    if (validation) new ValidationExtension(morphia)
    val client = initClient(addresses, adminSource getOrElse database, user, password, options)
    morphia.createDatastore(client, database)
  }
}
