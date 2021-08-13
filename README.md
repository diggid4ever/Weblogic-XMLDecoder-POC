# Weblogic-XMLDecoder-POC
> Weblogic XMLDecoder系列漏洞POC

## 漏洞版本
- CVE-2017-3506
- CVE-2017-10271
- CVE-2019-2725

## CVE-2017-3506
项目中`poc/2017-3506`目录下存了两个poc：
- poc1.xml : 执行命令，在/tmp目录下生成diggid文件，需要进docker里面验证
- poc2.xml : 反弹shell，需要外连

## CVE-2017-10271
同3506

## CVE-2019-2725
项目中`poc/2019-2725`目录下存了三种poc :
- poc1.xml : 换了漏洞触发的接口，且要求未打之前的补丁，payload还是用前面的
- poc2-x.xml : 二次反序列化UnitOfWorkChangeSet
    - poc2-1.xml : Jdk7u21
    - poc2-2.xml : Jdk8u20
    - poc2-3.xml : JtaTransactionManager的jndi注入
- poc3.xml : FileSystemXmlApplicationContext的Spring xml RCE

其他项目文件看一下应该就懂了

## 漏洞分析
可以参考我的博客:
- https://blog.diggid.top/2021/07/15/Java-WebLogic%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96-XMLDecoder%E4%B9%8BCVE-2017-3506-10271/
- https://blog.diggid.top/2021/07/20/Java-WebLogic%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96-XMLDecoder%E4%B9%8BCVE-2019-2725/