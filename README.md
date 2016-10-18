# 项目名称
## herakle
希腊神话中最伟大的英雄大力神,希望此项目能如同大力神一般给大家带来无穷无尽的力量,也能给我以更多的力量,来优化,完善此项目,让我在编码中不断拥有更大的力量,大步的走向未来.
额,好了不扯淡了.反正这就是一个我在业余时间花了挺大一部分的精力来搞的一个企业级快速开发基础项目,目前正在不断完善优化中,已基本成型.如果有需要可以直接拿来用,有什么不明白的,可以把问题发我邮箱,也可以直接在github提问,没有特殊情况,我每天都会来看,希望herakles能给大家在工作中,项目中带来便利.

# 项目简介
- herakles是基于spring整合各开源框架的企业级快速开发基础项目, 包括安全及权限管理(shiro), 日志管理(文件记录以及mongodb数据库存储), 任务调度(单独的任务调度服务), 缓存管理(spring cache + redis), druid连接池管理, JavaMelody服务器监测管理, lombok自动生成相关代码(get/set方法,log对象, 构造方法等)提高开发效率减少出错率, 持久层运用spring data + jpa , 减少代码量, 提高开发效率, 并且 java 持久化标准接口(jpa)完美兼容了各类数据库, 运用velocity模板引擎 + bootstrap + jquary的较为主流的前端搭配.

# 技术选型
- 核心框架：Spring Framework 4.3.2.RELEASE
- 安全框架：Apache Shiro 1.3.0
- 任务调度：Spring Task
- 持久层框架：Spring data jpa 1.10.2.RELEASE
- 数据库连接池：Alibaba Druid 1.0.25
- 缓存框架：Spring Cache + Redis
- 日志管理：SLF4J + Logback + mongodb
- 前端框架：Bootstrap + Jquary
- 模板引擎：Velocity

# 模块介绍
- herakles-common--公共模块
- herakles-system--系统模块
- herakles-biz-dao--业务数据访问服务模块
- herakles-biz-service--业务接口服务模块
- herakles-biz-api--业务接口模块
- herakles-task--定时任务模块
- herakles-web--web前端模块

# 项目说明

开发环境
--------------------------------------------------
- eclipse或其他
- jdk1.8
- redis 3.2
- mongodb 3.2
- maven
- mysql或其他
- 如果是eclipse则需要运行lombok.jar进行配置.

运行环境
--------------------------------------------------
- 系统不限
- jdk1.8
- redis 3.2
- mongodb 3.2
- maven
- ftp服务
- 项目使用JPA,没有绑定特定的数据库.目前配置的是mysql,改一下参数即可使用oracle或其他数据库

运行条件
--------------------------------------------------
1. 启动redis服务
2. 启动mongodb服务,(用户名,密码自行配置)
3. 运行项目中sql脚本(mysql)
4. 访问http://localhost:8080/herakles-web/web/auth/login

注意事项
--------------------------------------------------
- 因为项目用的是spring data jpa,感觉国内公司在项目中使用不多,但是我觉得却能很大程度提升开发效率.有一定的学习成本,可参考文档:http://docs.spring.io/spring-data/jpa/docs/1.10.3.RELEASE/reference/html/ ,虽然spring data jpa也支持写sql,但是本人还是建议不要在项目中写直接写sql,jpa的使用可参考:https://en.wikibooks.org/wiki/Java_Persistence/Criteria ,

# 其他
- 如有不明白之处,请至Issues提问
