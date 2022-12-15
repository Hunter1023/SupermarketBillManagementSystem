# 配置Tomcat

1. 右上角`Edit Confgurations...` - `Add New Configuration` - `Tomcat Server` - `Local`

   ![image-20221215000234933](https://s2.loli.net/2022/12/15/IdBRX1WeTGON32a.png)

2. Select Artifacts to Deploy

   ![image-20221215000502204](https://s2.loli.net/2022/12/15/ntjhl4PGEVbQekr.png)

---
# 创建数据库和数据库表

1. 创建数据库
   
   `CREATE DATABASE smbms;`
2. 执行创建数据库表的sql文件
   
   文件：`src/main/resources/smbms.sql`
   
   执行方式：右键sql文件 - `Run 'smbms.sql'` - `Target data source / schema:` - `Add`，选择创建的数据库`smbms`。

   ![image-20221215011650919](https://s2.loli.net/2022/12/15/T9pcBvNru7mVhE6.png)

   ![image-20221215011612523](https://s2.loli.net/2022/12/15/lUeyiIaquDckMYX.png)

---
# 静态资源

1. `src/main/webapp/calendar`
2. `src/main/webapp/css`
3. `src/main/webapp/images`
4. `src/main/webapp/js`
   
   
   