function reconnect(){
	if(socket == null){
		tradeConnection();
		initLoad();
	}
}
