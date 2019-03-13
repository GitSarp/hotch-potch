# hotch-potch
contains java practices

1.clouddemo:         spring cloud practice

- cloudserver为高可用的eureka-server可开两个实例，可以自行修改，以参数启动。

- configserver为配置中心，从本项目地址获取配置（ms3有配置文件在此）。ms3通过Spring bus可以通过post请求刷新配置（需要安装rabbitmq）。

- gatewaydemo为zuul实现路由转发和相关过滤的网关。

- ms3、cloudclient、ribboncall为三个微服务。cloudclient通过openfeign（自带负载均衡）与微服务ms3相互调用，ribboncall通过riibbon调用cloudclient（支持负载均衡）。ribboncall和ms3都有熔断器控制，  支持通过hytrix面板监控，ribboncall支持turbine聚合监控。

- ms3用swagger生成api文档。

- zipkin链路追踪有问题。

2.dubbostart:        dubbo practice

3.kafkademo:  kafka practice

4.springtaskdmeo:    spring task的springMVC两种实现，spring boot 2.0的quartz和spring task使用
