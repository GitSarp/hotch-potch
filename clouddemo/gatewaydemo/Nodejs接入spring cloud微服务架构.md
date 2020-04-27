## Nodejs接入spring cloud微服务架构
以下以quill-sharedb-cursors项目为例

### Nodejs websocket服务端配置
- package.json添加依赖，npm install
```
"dependencies": {
    ...
    "eureka-js-client": "^4.3.0"
    ...
}
```

- 增加eureka-client.yml配置
```yml
eureka:
  overrides: 1
  heartbeatInterval: 999
  registryFetchInterval: 999
  registerWithEureka: true
  fetchRegistry: false
  #eureka主机
  host: localhost
  #eureka端口
  port: 1001
  servicePath: '/eureka/apps/'
  ssl: false
  useDns: false
  fetchMetadata: false
  preferIpAddress: true
instance:
  #服务名
  app: ws-service
  vipAddress: 'localhost'
  #端口
  port: 3000
  dataCenterInfo:
    name: myown
```

- app.js注册服务
```javascript
const Eureka = require('eureka-js-client').Eureka;
	....
	....
const client = new Eureka({
  instance: {
    instanceId: 'ws-service-01',
    //服务名
    app: 'ws-service',
    //主机名
    hostName: 'localhost',
    //ip
    ipAddr: '127.0.0.1',
    // preferIpAddress: true, // default is false and host will be used.
    // homePageUrl: 'http://localhost:3000/info',
    statusPageUrl: 'http://localhost:3000/info',
    // healthCheckUrl: 'http://localhost:3000/info',
    port: {
      //与websocket端口保持一致！
      '$': 3000,
      '@enabled': 'true',
    },
    vipAddress: 'ws-service', // Important, otherwise spring-apigateway cannot find instance of ws-service
    // secureVipAddress: 'ws-service',
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn',
    },
  },
  eureka: {
    fetchRegistry: false,
    //eureka地址
    host: 'localhost',
    port: 1001,
    servicePath: '/eureka/apps/'
  },
});
//client.logger.level('debug');
client.start(function(error){
  console.log(error || 'complete');
});
```



## spring cloud  gateway配置

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: wsservice
          #ws://localhost:3000/sharedb?groupId=ssq
          uri: lb:ws://ws-service
          predicates:
            - Path=/sharedb/**
        - id: wsservice2
          #ws://localhost:3000/cursors?groupId=ssq
          uri: lb:ws://ws-service
          predicates:
            - Path=/cursors/**
```

### 截图
其中6001为网关端口



![image.png](https://i.loli.net/2020/03/20/ZNLSx4cqPh7y38g.png)



![image.png](https://i.loli.net/2020/03/20/ISJvyqjQ57DU6KY.png)