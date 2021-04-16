## u - 短网址平台
以打造一个高性能，可快速部署的短网址服务平台<br>

短链接我们或多或少都使用过，所谓短链接就是根据较长的原链接url生成一段较短的链接，
访问短链接可以跳转到对应的原链接，这样做好处在于：
1. url更加美观，访问头更短，更快更安全；
2. 便于保存和传播；
3. 某些网站内容发布有字数限制，短链接可以节约字数。
<br>
我只为缩短你的超链接url，让你变得短短的，不。。。是你的url变得短短的。


对于把长网址生成的短网址等信息放在内存中，
我用了一个HashMap来储存，并加了一个队例，为了防止内存溢出，
我这边是1、定时清理HashMap中过期的对像。
2、同时HashMap设定了一个最大值，如果超过最大的值，则把最先加入的值移出并销毁。

gitee地址： <br>


## 系统说明：
- 基于 Spring Boot Redis实现的一个短网址平台
### 目前
- 支持web方式和api方式创建短网址
- 支持api解析短网址
### 后续
- 将支持web控制台进行动态配置进行管控
- 增加监控功能
- 支持更丰富的api操作

### 程序在本地正常跑起来之后，  api 文档地址：
http://localhost:8080/swagger-ui.html#
- ...


### 单体测试结果图
![效果图1](dockertest\jacocotest\one.png)
![效果图2](dockertest\jacocotest\two.png)
  
  

### Web方式运行效果图
![效果图1](dockertest\shorturlimages\用短地址查询长地址.png)
![效果图2](dockertest\shorturlimages\长地址生成短地址.png)
  
### API方式使用


- 接口使用样例1 (生成短网址)


post http://127.0.0.1:8080/url/generate

type application/json

body {
    "url":"https://www.baidu.com",
    "valid":14400
 }
参数说明：url 传入的长网址
        valid 有效时间（秒），默认6个小时，

{
  "code": 1,
  "success": true,
  "msg": "ok",
  "data": {
    "orgUrl": "www.baidu.com",
    "shortUrl": "http://127.0.0.1:8080/u/M7RJFj",
    "shortTarget": "M7RJFj",
    "validTime": 2160000,
    "createTime": 1618532639712
  }
}


- 接口使用样例2 (查询长网址)


post http://127.0.0.1:8080/url/queryLongUrl

type application/json

body {
    "shortUrl":"http://127.0.0.1:8080/u/M7RJFj"
 }
参数说明：shortUrl 传入的短网址
        默认6个小时

{
  "code": 1,
  "success": true,
  "msg": "ok",
  "data": {
    "orgUrl": "www.baidu.com",
    "shortUrl": "http://127.0.0.1:8080/u/M7RJFj",
    "shortTarget": "M7RJFj",
    "validTime": 2160000,
    "createTime": 1618532639712
  }
}
  
### 核心依赖
  
  | 依赖                   | 版本          |
  | ---------------------- | ------------- |
  | Spring Boot            | 2.3.6.RELEASE |
  | Spring Boot Redis      | 2.3.6.RELEASE  |
  | layui                  | 2.5.7        |

###  环境需要
- Java 1.8.0_211
- Maven 3.6.3
- Redis  3.x 不用redis方式可以不要。

### application.yml修改


spring:
  redis:
    database: 1
    host: 119.45.199.171 #这个是用redis做为存储方式时引用的redis信息。
    port: 6379
#    password:
short_url:
  valid: 2160000
  server: http://127.0.0.1:8080 #这里是短网址服务器地址

server:
  port: 8080

## 其他说明
1. 有更多的功能想法欢迎提出
2. 联系作者QQ：1140336945
3. ### 高级的API使用方式，是要加加密认证字符串。
 **`Access Key Id（AK）用于标示用户，Secret Access Key（SK）是用户用于加密认证字符串和云厂商用来验证认证字符串的密钥`**<br>
- 首先需要ak和sk的授权才可以访问api接口，当然你可以在你系统中取消此项认证<br>
在com.kk.docker.config.WebConfig中中注释掉如下代码即可取消认证<br>
```
//添加api权限拦截器拦截所有api
