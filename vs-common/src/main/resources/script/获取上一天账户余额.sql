CREATE  FUNCTION `get_yestoday_balance`(acname VARCHAR(1000),hisdate BIGINT)
 RETURNS double
BEGIN
	-- Get the separated number of given string.
	DECLARE resultstr DOUBLE  DEFAULT 0.0;
     set resultstr=(SELECT ROUND(ifnull((sch.market_value+sch.current_cash),0),2) FROM w_stock_current_history sch WHERE sch.account_name=acname AND  sch.history_date=hisdate  LIMIT 0,1);
	RETURN resultstr; 
END;
