package com.lvxingpai.database

import com.mongodb.{ MongoClient, MongoClientOptions, MongoCredential, ServerAddress }
import org.mongodb.morphia.{ Morphia, ValidationExtension }

import scala.collection.JavaConversions._

/**
 * Created by zephyre on 10/29/15.
 */
object MorphiaFactoryImpl extends MorphiaFactory {

  private def initClient(host: String = "localhost", port: Int = 27017, database: String = "local",
    user: Option[String] = None, password: Option[String] = None,
    options: Option[MongoClientOptions] = None) = {
    val credential = for {
      u <- user
      p <- password
    } yield {
      MongoCredential.createCredential(u, database, p.toCharArray)
    }

    val serverAddress = new ServerAddress(host, port)

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
      new MongoClient(serverAddress, Seq(credential.get), opt)
    } else {
      new MongoClient(serverAddress, opt)
    }
  }

  /**
   * 建立MongoDB连接
   * @param host 服务器地址，默认为localhost
   * @param port 服务器端口，默认为27017
   * @param database 数据库名称
   * @param adminSource 在哪个数据库上做用户验证？默认为database
   * @param user 用户名，默认为None
   * @param password 密码，默认为None
   * @param options 其它连接选项，默认为None
   * @param validation 是否启用validation
   */
  override def newInstance(host: String = "localhost", port: Int = 27017, database: String = "local",
    adminSource: Option[String] = None, user: Option[String] = None, password: Option[String] = None,
    options: Option[MongoClientOptions] = None, validation: Boolean = false) = {
    val morphia = new Morphia
    if (validation) new ValidationExtension(morphia)
    val client = initClient(host, port, adminSource getOrElse database, user, password, options)
    morphia.createDatastore(client, database)
  }
}
