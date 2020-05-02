# API接口

使用浏览器访问采用http://39.102.62.210/ 的方式进行

安卓访问应该设计HttpUrlConnection类的有关操作

## 返回简单数据

### hello

目前只能简单的返回hello world数据

#### request

- Method：**GET**
- URL：```/```

+ Headers: none
+ Body: none

#### response

```
hello world!
```

## 数据库相关操作

目前可以展示整张student表的内容、添加学生以及更改学生的名字

### query

+ Method： **GET** 
+ URL：```/student/query```
+ Headers:none
+ Body:none

#### response

```
[]
```

### add

添加一名叫junjun的学生，年龄为6

#### request

+ Method：**GET**
+ URL：/student/add
+ Header:none
+ Body:none

#### response

```
无
```

### update

将junjun的名字改成duoduo

#### request

+ Method：**GET**
+ URL：/student/update
+ Header:none
+ Body:none

#### response

```
无
```

