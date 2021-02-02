# Web Application Monitor 1.0.0
> java version : 1.8

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://#)

<strong>State of all the threads of a Java process.</strong> The state of each thread is presented with a stack trace, showing the content of a thread's stack. 

### Libraries starter


| Library | Architype |
| ------ | ------ |
| Web | spring-boot-starter-web |
| Log | spring-boot-starter-log4j2 |

### Additional libraries

| Library | Version | Info |
| ------ | ------ | ------ |
| commons-lang3 | 3.0 | Apache Commons Lang |
| lombok | 1.18.8 | Libreria per Oracle 11 |
| disruptor | 3.4.2 | Parser per HTML |

### Install 

```java
Create archetype into .m2
mvn clean install
```

### Configure within another project

```java
add file Pom.xml

<dependency>
  <groupId>io.peruvianit</groupId>
  <artifactId>web-application-monitor</artifactId>
  <version>1.0.0</version>
</dependency>

add the path of the tomcat log folder into the properties file of your project :

web-server.path.log=${catalina.home}/logs
```

### Implementation 

```java
enable web application monitor, to add:

@EnableWebApplicationMonitor
```

### Run 

```java
start application web

http://<context-root>/monitor-ui.html
```

### Api
List of endpoint (@RestController)

| Uri | Info  |
| ------ | ------ |
| /io-peruvianit/monitor/logs/ | list all file into of the folder log  |
| /io-peruvianit/monitor/logs/<name-file-log> | Download file  |
| /io-peruvianit/monitor/thread-dump | simplified info of all Threads  |
| /io-peruvianit/monitor/thread-dump | full info of all Threads |

### Todos
 
 - [ ] Detect DeadLock
 - [ ] RealTime information
 
