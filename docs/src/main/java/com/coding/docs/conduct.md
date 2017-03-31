# 构建报告项目实施流程

## 主流程 

- 1, 首先会维护所有人的代码目录, 见[group17.json](https://github.com/luoziyihao/build-analysis/blob/master/build/src/main/resources/project/config/group17.json), 是如下的数据结构, 每个组对应一个配置文件, 大家以组为单位维护.

如果你的代码放在 `coding2017/group17/1204187480/code/homework` 下, codePath 填写 `group17/1204187480/code/homework` 即可

```json
{
    "config": [
    {
      "qq": "1204187480",
      "codePath": "group17/1204187480/code/homework"
    
    },
    {
      "qq": "1158154002",
      "codePath": "group17/1158154002"
    
    }
  
        ]

}
```

- 2, 然后程序会去分析这些配置文件, 得到所有人的项目目录

- 3, 接下来校验项目目录是否是合法的maven 项目, 如果是就用java 调用 maven 命令去执行构建, 不是就跳过

- 4, 然后将所有的构建结果存在数据库里面, 每一次作业构建我们都可以记录

- 5, 然后是分析构建结果, 得到我们想要的报表数据, 现在主要是测试结果. 报表数据属于加工过的数据, 我们也可以存储起来

- 6, 最后是把报表数据渲染成我们想要的样子, 以供查看

第 1 步需要每个组长整理一下, 后面的步骤就是属于编码工作了
