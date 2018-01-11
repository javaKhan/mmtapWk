truncate table info;
truncate table indent;
truncate table custom;
truncate table work;
truncate table trace;


truncate table login_log;
truncate table operation_log;
---
手动删除订单
1.先删除有关系的info
DELETE from info where wid in (select wid from `work` w where w.oid = '20180110121148-1461')
2.再删除work
DELETE from work WHERE wid='20180110121148-1461'
3.删除trace
DELETE from trace where oid = '20180110121148-1461'
4.删除indent
DELETE from indent where oid = '20180110121148-1461'
5.custom可以不删从页面删



DELETE from info where wid in (select wid from `work` w where w.oid = '');
DELETE from work WHERE oid='';
DELETE from trace where oid = '';
DELETE from indent where oid = '';

