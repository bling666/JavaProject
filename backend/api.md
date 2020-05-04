# API接口

使用浏览器访问采用http://39.102.62.210/ 的方式进行

安卓访问应该设计HttpUrlConnection类的有关操作

## 通用返回值格式

```json
{
    "code" :"200为成功，其他均为失败",
    "status" : "0 or 1; 1为操作正常完成，0为服务器正常返回，但要求的操作没有完成，例如没有查到数据",
    "msg":"成功或者失败的信息",
    "data":"数据"
}

```

## 单词查询

### getoneword

#### request

+ Method:**GET**

+ URL:```/api/getoneword```

+ 参数：

  + keyword：表示要求查询的单词
  + top：返回结果的数目，默认值为1

+ 实例

  ```url
  39.102.62.210//api/getoneword?keyword=apple
  ```

#### response

```json
{
    "code": 200,
    "status": 1,
    "msg": "success",
    "data": [
        {
            "id": 42432,
            "word": "apple",
            "sw": null,
            "phonetic": "'æpl",
            "definition": "n. fruit with red or yellow or green skin and sweet to tart crisp whitish flesh\\nn. native Eurasian tree widely cultivated in many varieties for its firm rounded edible fruits",
            "translation": "n. 苹果, 家伙\\n[医] 苹果",
            "pos": "",
            "collins": true,
            "oxford": true,
            "tag": "zk gk",
            "bnc": 2446,
            "frq": 2695,
            "exchange": "s:apples",
            "detail": "",
            "audio": ""
        }
    ]
}
```



## 返回简单数据

这一部分仅供测试，不遵循上文提到的数据格式

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

