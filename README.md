# build-analysis

## 简介

该项目用于项目构建统计, 主要作用是结合maven发起构建, 执行测试案例, 统计构建结果, 并提供报表输出.

## 模块划分

```
-- build-analysis  # 父模块
    --build     # 发起构建, 执行测试案例的模块, 使用maven-surefire-report-plugin 可以生成测试案例的文件报表, 实现可以参考 http://stackoverflow.com/questions/5141788/how-to-run-maven-from-java
    --analysis  # 分析构建结果, 生成报表数据的模块
    --web       # 提供web接口的模块
    --docs      # 项目文档模块

```
