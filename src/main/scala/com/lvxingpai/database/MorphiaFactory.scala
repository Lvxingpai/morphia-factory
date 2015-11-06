package com.lvxingpai.database

import com.mongodb.MongoClientOptions
import org.mongodb.morphia.Datastore

/**
 * Morphia的工厂类
 *
 * Created by zephyre on 10/29/15.
 */
trait MorphiaFactory {
  /**
   * 建立MongoDB连接
   * @param addresses 服务器地址(支持replica set)
   * @param database 数据库名称
   * @param adminSource 在哪个数据库上做用户验证？默认为database
   * @param user 用户名，默认为None
   * @param password 密码，默认为None
   * @param options 其它连接选项，默认为None
   * @param validation 是否启用validation，默认为false
   */
  def newInstance(addresses: Seq[(String, Int)] = Seq("localhost" -> 27017), database: String = "local",
    adminSource: Option[String] = None, user: Option[String] = None, password: Option[String] = None,
    options: Option[MongoClientOptions] = None, validation: Boolean = false): Datastore
}
