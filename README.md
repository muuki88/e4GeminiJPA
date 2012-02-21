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

## Work done by

This repository contains the work done by Filipp A.
See [Gemini Forum Post](http://www.eclipse.org/forums/index.php/t/290891/) 
for more information
