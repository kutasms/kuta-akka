# Kuta-akka
![](https://img.shields.io/badge/license-Apache%202-blue)
![](https://img.shields.io/badge/akka-2.6%2B-orange)
![](https://img.shields.io/badge/java-1.8%2B-green)

Although springboot is very popular,but I prefer the actor model. Akka is a great actor model implementation. For ease of use, I have integrated redis, mysql, mongodb and other common libraries.Akka platform has many development packages, including Akka-HTTP, Akka-stream (reactive streams), Akka-actor, Akka-cluster, etc. Kuta-akka will integrate them to make it easier for you to use.

The reference documentation about akka is available at [doc.akka.io](http://doc.akka.io),
for [Scala](http://doc.akka.io/docs/akka/current/scala.html) and [Java](http://doc.akka.io/docs/akka/current/java.html).

The project has been successfully applied to many projects and is in good condition so far. Unfortunately, at present, I am the only one who develops and modifies the code of this project in commercial projects, because time is very tight. Inevitably, some codes may have some problems. However, these problems will not affect the operation of the project. They may be just naming problems, partial code obsolescence, etc.

## Structure
**#akka-base**

Further encapsulation of akka can be quickly applied to commercial projects.

**#base** 

Integrate redis, mysql, mongodb and other common libraries, including stand-alone / cluster mode.

**#common-config** 

Provides common configuration files and configuration file access encapsulation classes.

**#data** 

Define the basic data of mybatis and provide functions that will be used in almost all projects, including users, permissions, task plug-ins, etc.

Contributing
------------
Contributions are *very* welcome!

If you see an issue that you'd like to see fixed, the best way to make it happen is to help out by submitting a pull request implementing it.

License
-------

Kuta-akka is Open Source and available under the Apache 2 License.
