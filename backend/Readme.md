# 后端简要介绍

## 项目框架及选择原因
本项目后端采用的是[java spring boot](https://spring.io/projects/spring-boot/) 框架，选择的原因一是这是门java课，得用
java写后端(*,二是他比较简单，在服务器上运行也比较容易

## 版本信息

java 11.0.2

spring boot 2.2.6

mysql 5.7.29

## 项目数据库

1. 数据库选择：mysql
2. 主机地址：39.102.62.210/
3. 数据库名称：java20
4. 端口：3306
5. 用户名：java20
6. 密码：********** 这种东西不能广而告之

远程访问在本地安装mysql的情况下 使用
```shell script
mysql -h 39.102.62.210 -P 3306 -u java20 -p
```
然后输入密码即可

## 现在有的api

| 路径            | 描述                                         | 备注     |
| --------------- | -------------------------------------------- | -------- |
|/api/getoneword  | 返回一个单词的全部信息                       | 真的能用|
| /               | 返回“hello world!"                           | 仅供测试 |
| /student/query  | 返回数据库内的学生列表，json格式（大概）     | 仅供测试 |
| /student/add    | 向数据库内添加一名叫junjun的学生，无返回结果 | 仅供测试 |
| /student/update | 将junjun的名字修改为duoduo，无返回结果       | 仅供测试 |

具体的参数将在api.md中呈现



推荐一个测试接口的软件

[postman](https://www.postman.com/)
