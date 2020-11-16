## SpringBoot 脚手架

新项目修改

1. 项目名（springboot-demo）
2. 包名（com.zhang.demo）
3. pom.xml 中的 groupId、artifactId、name、description
4. MybatisPlusConfig 中的 MapperScan
4. application-xxx.properties 中的 mybatis-plus.type-aliases-package

若项目无需引入数据库相关，请在pom.xml中删除数据库相关依赖，以及datasources整个配置文件，否则无法启动项目。

启动：
运行 com.zhang.demo.ApiApplication 启动
访问 `http://localhost:8081/swagger-ui.html` 或 `http://localhost:8081/doc.html`


报错原因：

1. entity包下无实例类
```$xslt
Caused by: com.baomidou.mybatisplus.exceptions.MybatisPlusException: not find typeAliasesPackage:classpath*:com/dxjy/pcr/modules/*/entity/*.class
	at com.baomidou.mybatisplus.toolkit.PackageHelper.convertTypeAliasesPackage(PackageHelper.java:76)
	... 39 common frames omitted
```