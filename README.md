# 2 лаба
Запускается на payara-micro-6.2024.9.jar (для helios) и payara-6.2024.9 full (локально)

## payara-6.2024.9 full:
1. Чтобы настроить бд надо создать JDBC Resource и JDBC Connection pool
2. Название JDBC Resource надо указать в имени Persistence unit в persistence.xml
3. Деплой находится в Applications


## payara-micro-6.2024.9.jar:
1. В db-config.asadmin меняем настройки бд под свои
2. Команда запуска:
```shell
java -jar payara-micro-6.2024.9.jar --addlibs payara-config/postgresql-42.7.4.jar --postbootcommandfile payara-config/db-config-helios.asadmin --deploy target/ticketservicepayara.war --port 8090
```

### SSL
Надо создать свой сертификат:
```shell
keytool -genkey -alias truststore -keyalg RSA -keysize 2048 -keystore truststore.jks -storepass changeit -validity 365
```

Запустить:
```shell
java -Djavax.net.ssl.trustStore=truststore.jks -Djavax.net.ssl.trustStorePassword=changeit -jar payara-micro-6.2024.9.jar --sslport 8091 --addlibs payara-config/postgresql-42.7.4.jar --postbootcommandfile payara-config/db-config.asadmin --deploy target/bookingServicePayara.war --port 8090
```


## Первый сервис
Первый сервис я запускала на хелиосе, вот репозиторий:
https://github.com/qryaknutsa/ticketServicePayara