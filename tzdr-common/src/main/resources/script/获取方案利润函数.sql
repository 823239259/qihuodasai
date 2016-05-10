CREATE DEFINER = `root`@`%` FUNCTION `get_user_profits`(uid VARCHAR(1000),groupId VARCHAR(20))
 RETURNS double
BEGIN
	-- Get the separated number of given string.
	DECLARE resultstr DOUBLE  DEFAULT 0.0;
     set resultstr=(SELECT SUM(money) from w_user_fund  uf where uf.uid = uid and type = 16 and uf.lid=groupId);
	RETURN resultstr; 
END;

