SET FOREIGN_KEY_CHECKS=0;

DROP PROCEDURE IF EXISTS `rebateSystem`;

CREATE  PROCEDURE `rebateSystem`(`layer` int(20))
BEGIN
	#Routine body goes here...
-- 声明根节点变量
declare rootId varchar(32);
declare rootMobile varchar(32);
-- 创建临时表
create temporary table if not exists w_user_temp(
`id` INT(9) NOT NULL AUTO_INCREMENT, 
`uid` varchar(32) DEFAULT NULL,
`parent_id` varchar(32) DEFAULT NULL,
`parent_mobile` varchar(255) DEFAULT NULL,
`mobile` varchar(255) DEFAULT NULL,
`level` INT(32) DEFAULT 0,
`user_type` varchar(255) DEFAULT NULL,
`uname` varchar(255) DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*最高允许递归数*/
SET @@max_sp_recursion_depth = 99 ;
-- 获取根节点
SELECT id,mobile INTO rootId,rootMobile FROM w_user WHERE parent_id IS null;
/*核心数据收集*/
call rebateSystemIterative(rootId,layer,1,rootMobile);
/* 展现 */
select level-2 as '级次',parent_mobile as '上级用户名',mobile as '达人用户名',v.tname as '实名',fc.rebate as '返利率',
	f.gmoney as '本级管理费',null as '管理费总计',fc.ymoney as '返利'  
	from (
		select -1 as id ,mobile,id as uid ,parent_id,null as parent_mobile,1 as level,user_type,uname 
			from w_user where id=rootId union select id,mobile,uid,parent_id,parent_mobile,level,user_type,uname from w_user_temp
	) tmp 
  LEFT JOIN w_user_verified v ON tmp.uid= v.uid
	LEFT JOIN (
		-- 返利率/返利
		SELECT f.uid,sum(f.money) as ymoney,c.rebate FROM w_user_fund f 
			LEFT JOIN w_user_commission c 
			ON f.uid=c.uid AND c.income_source_uid IS NULL AND DATE_FORMAT(FROM_UNIXTIME(c.create_time, '%Y-%m-%d %H:%i:%s'),'%Y-%m-%d %H:%i:%s')
			BETWEEN DATE_FORMAT(date_sub(NOW(),interval 1 day),'%Y-%m-%d 17:00:00') AND DATE_FORMAT(NOW(),'%Y-%m-%d 17:00:00')
		WHERE f.type=13 AND f.pay_status=1 AND DATE_FORMAT(FROM_UNIXTIME(f.uptime, '%Y-%m-%d %H:%i:%s'),'%Y-%m-%d %H:%i:%s')
			BETWEEN DATE_FORMAT(date_sub(NOW(),interval 1 day),'%Y-%m-%d 17:00:00') AND DATE_FORMAT(NOW(),'%Y-%m-%d 17:00:00') GROUP BY f.uid
  ) fc ON tmp.uid=fc.uid
  LEFT JOIN (
		-- 本级管理费
		SELECT f.uid,sum(f.money) as gmoney FROM w_user_fund f 
			WHERE f.type=12 AND f.pay_status=1 AND DATE_FORMAT(FROM_UNIXTIME(f.uptime, '%Y-%m-%d %H:%i:%s'),'%Y-%m-%d %H:%i:%s')
			BETWEEN DATE_FORMAT(date_sub(NOW(),interval 1 day),'%Y-%m-%d 17:00:00') AND DATE_FORMAT(NOW(),'%Y-%m-%d 17:00:00') GROUP BY f.uid
	) f ON tmp.uid=f.uid
  WHERE tmp.user_type NOT IN(-1,-2,-3);
-- 删除临时表
drop temporary table if  exists  w_user_temp;
END;

DROP PROCEDURE IF EXISTS `rebateSystemIterative`;

CREATE  PROCEDURE `rebateSystemIterative`(`uid` varchar(32),`layer` int(20),`uLevel` int(20),`parentMobile` varchar(255))
BEGIN
	#Routine body goes here...
  declare tId varchar(32);
  declare tMobile varchar(255);
  declare tParentId varchar(32);
  declare tLevel INT(20);
  declare tUserType varchar(32);
  declare tUserName varchar(32) character set utf8mb4;

	 /* 游标定义 */
   declare userCursor CURSOR FOR select id,mobile,parent_id,user_type,uname from w_user where parent_id=uid ;
   declare CONTINUE HANDLER FOR SQLSTATE '02000' SET tid = null;
   /* 允许递归深度 */
   if layer>0 then
      OPEN userCursor ;
      FETCH userCursor INTO tId,tMobile,tParentId,tUserType,tUserName;
          WHILE(tId is not null)
             DO
             SET tLevel = uLevel+1;
             /* 核心数据收集 */
             insert into w_user_temp(uid,parent_id,parent_mobile,mobile,level,user_type,uname) values(tid,tParentId,parentMobile,tMobile,tLevel,tUserType,tUserName);
             call rebateSystemIterative(tid,layer-1,tLevel,tMobile);
         FETCH userCursor INTO tId,tMobile,tParentId,tUserType,tUserName;
        END WHILE;
    end if;
  
END;
