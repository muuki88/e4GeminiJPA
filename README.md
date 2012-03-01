## Goal

Add the ability to inject EntityManagers for a certain
persistence unit in Eclipse 4.

```java

@Inject
@GeminiPersistenceContext(unitName="puTest")
EntityManager em;

```
There are even more configurable modes like

```java

@Inject
@GeminiPersistenceContext(unitName="puTest", properties = {
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.JDBC_DRIVER, value="org.gjt.mm.mysql.Driver"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.JDBC_URL, value="jdbc:mysql://127.0.0.1/test"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.JDBC_USER, value="test"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.JDBC_PASSWORD, value="test"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.DDL_GENERATION, value=PersistenceUnitProperties.DROP_AND_CREATE),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.DDL_GENERATION_MODE, value=PersistenceUnitProperties.DDL_DATABASE_GENERATION),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.CONNECTION_POOL_MIN, value="20"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.TEMPORAL_MUTABLE, value="true"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.WEAVING, value="false"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.WEAVING_INTERNAL, value="false"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_LEVEL, value="FINEST"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_TIMESTAMP, value="true"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_SESSION, value="true"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_THREAD, value="true"),
 @GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_EXCEPTIONS, value="true")
})
private EntityManager entityManager;

```

## Sample Application

The sample application contains three sample database which can be connected to:

* Apache Derby
* Apache HSQL
* MySQL

If you want only one to start you have to change the `org.e4.gemini.rcp` plugin.xml
and remove the required plugins. For each database you have to install, or add to target platform, certain
Gemini Bundles. 

### Apache Derby

[Apache Derby](http://db.apache.org/derby/) is a database written in java. It supports an embedded mode, so
you can start it within the application. 

Download [Gemini DBAccess](http://www.eclipse.org/gemini/dbaccess/) and add this to your
target platform. 

### Apache HSQL

[HSQL](http://hsqldb.org/) is a database written in Java. It supports an embedded mode, so 
you can start it within the application.

To run HSQL you have to checkout this [repository](https://github.com/gkvas/gemini.dbaccess)
on the _hsql_ branch and import the _dbaccess_ and _hsql library_ bundle. 

### MySQL

[MySQL](http://dev.mysql.com/) is a well-known database. You have to start your own server
to connect with the sample application. The predefined credentials are:

1. Host: 127.0.0.1
2. Database: test
3. User: test
4. Password: test

Make sure the test user has full priveliges in order to execute drop-and-create-database correctly.

To use the MySQL Connector you have to clone this [repository](http://git.eclipse.org/c/gemini.dbaccess/org.eclipse.gemini.dbaccess.git)
Currently you have to change a bit of code according to this [bug report](https://bugs.eclipse.org/bugs/show_bug.cgi?id=372428)


## Work done by

This repository contains the work done by Filipp A. and Nepomuk Seiler.
See [Gemini Forum Post](http://www.eclipse.org/forums/index.php/t/290891/) 
for more information.

