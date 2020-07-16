# voter 基于 SpringBoot 的多人投票系统.

## 后端使用组件
```
spring-boot-starter-json'
spring-boot-starter-data-jpa'
spring-boot-starter-data-redis'
spring-boot-starter-security'
spring-boot-starter-web'
spring-session-data-redis'
runtimeOnly 'mysql:mysql-connector-java'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
json-simple:1.1.1'
implementation 'com.google.guava:guava:27.1-jre'
```

## 前端使用组件
```
JQuery
Ajax
Bootstrap
Chart
```

## 测试
代码覆盖率使用 jacoco
- 使用 make 快捷指令
```
scripts 目录为使用脚本
├── build.sh
├── clean.sh
├── dump.sh
├── gen.sh
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
    ```
    make go
    make start
    make dump
    ```
- 添加 Post-build Actions，Record JaCoCo coverage report。配置 classes， sources 路径。


### 本地机器直接获取报告
- git clone repo
- 依次执行 shell
    ```
    make go
    make start
    make runtest
    make dump
    make gen
    ```
    查看 ～/jacoco/report/index.html.
