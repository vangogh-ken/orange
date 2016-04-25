--还原数据库在查询时报 Lost connection to MYSQL server during query，修改最大的查询的包大小解决
set global max_allowed_packet=300*1024*1024;