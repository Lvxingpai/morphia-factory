# morphia-factory [![Build Status](http://ci2.lvxingpai.com/buildStatus/icon?job=MorphiaFactory)](http://ci2.lvxingpai.com/job/MorphiaFactory)

生成[Morphia datastore](https://github.com/mongodb/morphia/wiki/Datastore)的工厂类。支持依赖注入([JSR 330](https://jcp.org/en/jsr/detail?id=330)标准)，在[Guice](https://github.com/google/guice)中测试通过。

## 安装

在`build.sbt`中加入：

```sbt
"com.lvxingpai" %% "morphia-factory" % "0.2.0"
```

注意：安装此artifact需要有[Lvxingpai sbt Repository](http://nexus.lvxingpai.com)的访问权限。

## 使用方法

核心为下面这个工厂类的trait:

```scala
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
```

上面这个trait的默认实现为`MorphiaFactoryImpl`:

```scala
val datastore = MorphiaFactoryImpl.newInstance(database = "test", validation = true)
```

上述方法将返回`org.mongodb.morphia.Datastore`对象，可以用于后续的Morphia操作。

## 构建状态

[![Build Status](http://ci2.lvxingpai.com/buildStatus/icon?job=MorphiaFactory)](http://ci2.lvxingpai.com/job/MorphiaFactory)