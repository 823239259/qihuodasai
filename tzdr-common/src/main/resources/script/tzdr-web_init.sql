-- 初始化信闳平台帐号
insert INTO w_user(id,deleted,version,ctime,login_salt,mobile,password,uname,user_type,parent_id,activity_type,level) 
	VALUES('40288a114ae33230014ae3325ea10000',0,0,1421150930,'es7idJ4UVV',NULL,'ae5cb1793bb327b4618372cdbd847856','上海信闳-平台','-1',NULL,0,1);

-- 初始化信闳web渠道帐号
insert INTO w_user(id,deleted,version,ctime,login_salt,mobile,password,uname,user_type,parent_id,activity_type,level) 
	VALUES('40288a114ae33230014ae3325eb10001',0,0,1421150930,'ovQ4wUgxja',NULL,'4a54f2664dc57fb4de33f8fa70a43ee0','上海信闳-web渠道','-2','40288a114ae33230014ae3325ea10000',0,2);

-- 初始化信闳cms渠道帐号
insert INTO w_user(id,deleted,version,ctime,login_salt,mobile,password,uname,user_type,parent_id,activity_type,level) 
	VALUES('40288a114ae33230014ae3325eb10002',0,0,1421150930,'D4YxWHteEi',null,'0d343692d3fcf68258df1f87d84bff47','上海信闳-cms渠道','-3','40288a114ae33230014ae3325ea10000',0,2);
