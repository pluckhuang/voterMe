
# voter 基于 SpringBoot 的多人投票系统.



## 功能说明：

- 用户登陆后可查看调查问题

- 期限之内可以投票

- 每个人投完票后可以看到总体的投票结果



## 程序构建和运行

```

构建数据库和表，添加测试数据：apitest/data/*sql

构建程序：make go

运行：java -jar build/libs/*.jar

默认用户: huang

pwd: 123321

```



## 后端使用组件

```
spring boot
-jpa
-security
-json

redis
mysql
com.google.guava:guava:27.1-jre
swagger
```



## 前端使用组件

```
JQuery
Ajax
Bootstrap
Chart
```



## 接口文档使用 swagger

```
http://localhost:8080/swagger-ui.html
```

## 测试

- 代码覆盖率使用 jacoco
- 接口测试使用 httprunner (使用 charles 拦截请求，构造har，转换为.yml 做为测试用例。)
- 性能测试使用 locust

```
apitest 为接口测试目录

目录结构：
apitest
├── data   ## 接口测试数据准备
│   ├── __init__.py
│   ├── account.csv
│   ├── create_tables_and_auth.sql
│   ├── insert_vote_datas.sql
│   └── vote_play_db.py
├── debugtalk.py
├── har
│   └── api.har
├── performance_test   ## 性能测试
│   ├── __init__.py
│   ├── get_login_session.yml
│   └── post_data_request.yml
├── reports
│   ├── no_backend_data_request_report.html
│   └── with_backend_data_request_report.html
└── testcases  ## 接口测试用例
    ├── __init__.py
    ├── get_login_session.yml
    ├── no_backend_data_request.yml
    └── with_backend_data_request.yml
```

- cpu/io/mem 监控使用 glances



```
python requirements：

- httprunner==3.1.3
- locust=1.1.1
- Glances=3.1.4.1

```

```

scripts 目录为使用脚本

├── build.sh
├── clean.sh
├── dump.sh
├── gen.sh
├── runtest.sh
├── kill.sh
└── start.sh


jacoco 目录为使用的lib文件
└── lib
├── jacocoagent.jar
├── jacococli.jar

```



### 结合 jenkins 构建自动测试代码覆盖率报告

- git clone repo
- 自由风格项目下配置 shell
- 使用 make 简易指令：

```

make go
make start
make runtest
make dump

```

- 添加 Post-build Actions，Record JaCoCo coverage report。配置 classes， sources 路径。




### 或者本地机器直接获取报告

- git clone repo
- 依次执行 shell

```

make go
make start
make runtest
make dump
make gen

```
查看 ～/jacoco/report/index.html, 接口代码覆盖率达到100%。



### 性能测试
```
locusts -f apitest/performance_test
```