# 4 лаба
Запускаем на Wildfly 27.0.0 через standalone2 (скопировала standalone)


Запуск:
1. Запускаем standalone2, предварительно перейдя в директорию wildfly:
```shell
.\standalone -Djboss.server.base.dir=C:/Users/toric/Downloads/wildfly-27.0.0.Final/wildfly-27.0.0.Final/standalone2 -Djboss.socket.binding.port-offset=10                                          
```
2. Там деплоим наш war-ник