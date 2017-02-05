/**此类为工具类**/

/**
 * 校验输入框值的是否为空\
 * elementID 输入框的id
 */
function  validateNull(elementID)
{
	var  elementValue = $("#"+elementID).val();
	if(typeof(elementValue)=="undefined"||elementValue==""||elementValue.length==0)
	{
		return true;
	}
	
	return false;
}



/**
 * 校验输入框值的是否为空
 * 
 */
function  validateIsNull(elementValue)
{
	if(typeof(elementValue)=="undefined"||elementValue==""||elementValue.length==0)
	{
		return true;
	}
	
	return false;
}



/**
 * 带关闭按钮的弹出框 
 * @param title
 * @param content
 */
function  showMsgDialog(title,content)
{
		
	var dialog = art.dialog({
	    title: title,
	    content:content,
	    width:200,
	    cancelVal: '关闭',
	    background: '#BBBBBB', // 背景色 
	    opacity:0.87, 
	    zIndex:9900,
	    lock:true,
	    cancel: true //为true等价于function(){}
	});
}

/**
 * 带关闭按钮的弹出框,根据时间自动关闭 
 * @param title
 * @param content
 */
function  showMsgDialog(title,content,time)
{
	var dialog = art.dialog({
	    title: title,
	    width:200,
	    content:content,
	    icon: 'warning', 
	    cancelVal: '关闭',
	    background:'#BBBBBB',
	    opacity:0.87, 
	    zIndex:9900,  
	    lock:true,
	    time:time,
	    cancel: true//true //为true等价于function(){}
	});
	/*为了弹出层居中  配合 orange.css  aui_outer*/
	$(".aui_outer").css("position","fixed");
	$(".aui_outer button").click(function(){
		$(".aui_outer").removeAttr("style");
	});
	$(".aui_outer .aui_close").click(function(){
		$(".aui_outer").removeAttr("style");
	});
    $(".aui_outer").css("position","fixed");
    $(".aui_outer button").click(function(){
        $(".aui_outer").removeAttr("style");
    });
    $(".aui_outer .aui_close").click(function(){
        $(".aui_outer").removeAttr("style");
    });
}
function showMsgDialogRestPassWordLoadLoginUrl(title,content,time){
	var dialog = art.dialog({
	    title: title,
	    width:200,
	    content:content,
	    icon: 'warning', 
	    cancelVal: '关闭',
	    background:'#BBBBBB',
	    opacity:0.87, 
	    zIndex:9900,  
	    lock:true,
	    time:time,
	    cancel: function(){window.location.href=basepath+"/user/account";}//true //为true等价于function(){}
	});
	
}
/**
 * 点击关闭刷新页面
 * @param title
 * @param content
 */
function  clickCloseRefresh(title,content)
{
	var dialog = art.dialog({
	    title: title,
	    icon: 'warning', 
	    width:200,
	    content:content,
	    background:'#BBBBBB',
	    opacity:0.87,
	    zIndex:9900,
	    lock:true,
	    cancelVal: '关闭',
	    cancel: function() {window.location.reload();}
	});
}

/**
 * 弹出框中的弹出框 点击 全部关闭
 * @param title
 * @param content
 */
function  dialogsDialog(title,content)
{
	var dialog = art.dialog({
	    title: title,
	    content:content,
	    ok: function(){
	    	art.dialog.close();
	    	return true;
	    },
	    close:function() {window.location.reload();}
	});
}

function  winSucessRefresh(title,content,url)
{
	var dialog = art.dialog({
	    title: title,
	    content:content,
	    background:'#BBBBBB',
	    opacity:0.87,
	    zIndex:9900,
	    lock:true,
	    width:200,
	    icon: 'succeed', 
	    cancelVal: '关闭',
	    cancel: function() {
	    	window.location.href=url;
	    }
	});
}


/**
 * 带遮罩
 * @param title
 * @param content
 */
function  showMsgNoCancel(title,content)
{
	var dialog = art.dialog({
	    title: title,
	    content:content,
	    background:'#BBBBBB',
	    opacity:0.87,
	    zIndex:3000,
	    lock:true
	});
	return dialog;
}


/**
 * 带有 确定 关闭 的弹出框 
 * @param title
 * @param content
 * @param url 为点击ok跳转的路径
 */
function  showOkDialog(title,content,url,okValue,reload)
{
	art.dialog({
		title: title,
		content:content,
	    ok: function () {
	    	if (validateIsNull(url))
	    	{
	    		return true;
	    	}
	    	window.location.href=url;
	        return true;
	    },
	    okVal:okValue,
	    cancelVal: '关闭',
	    cancel:function() {
	    	if (reload==true)
	    	{
		    	window.location.reload();
	    	}
	    }
	});
}

/**
 * 带有 确定 关闭 的弹出框 
 * @param title
 * @param content
 * @param url 为点击ok跳转的路径
 */
function  showConfirmDialog(title,content,ok_fun,okValue,cancel_fun)
{
	art.dialog({
		 background:'#BBBBBB',
		 opacity:0.87,
		 zIndex:3000,
		    lock:true,
		title: title,
		content:content,
	    ok: function() {
	    	ok_fun();
    	},
	    okVal:okValue,
	    cancelVal: '关闭',
	    cancel:function() {
	    	cancel_fun();
	    	}
	});
}


/**
 * 带有 返回按钮 的弹出框 
 * @param title
 * @param content
 * @param url 为点击ok跳转的路径
 */
function  showReturnDialog(title,content,url)
{
	art.dialog({
		title: title,
		content:content,
	    ok: function () {
	    	if (validateIsNull(url))
	    	{
	    		return true;
	    	}
	    	window.location.href=url;
	        return true;
	    },
	    okVal:"返回"
	});
}



/**
 * 弹出 新的页面对话框  
 * @param id 为要操作的对象id
 * @param url 为页面地址
 * @title 弹出框的标题
 * @width 页面宽度
 * @hight 页面高度
 */
function showPageDialog(url,title,width,hight)
{
	// 此时 iframeA.html 页面可以使用 art.dialog.data('test') 获取到数据，如：
	// document.getElementById('aInput').value = art.dialog.data('test');
	art.dialog.open(url,{title:title,width:width,height:hight, background:'#BBBBBB',
	    opacity:0.87,
	    lock:true,
		close:function() {window.location.reload();}
	});
}  

/**
 * 关闭 新页面弹出框
 */
function closeDialog()
{
	art.dialog.close();
}

/**
 * 保存信息  
 * @param fromID 表单id
 * @param url 保存地址
 */
function saveInfo(fromID,url)
{	
	var  dialog  = showMsgNoCancel("保存提示","正在保存中...请等待");
	$(".aui_close").css("display","none");
	$.ajax({
		dataType:'json',
		type:"post",
		url:url,
		data:$("#"+fromID).serialize(),
		success:function(result){
			dialog.close();
			dialogsDialog("提示",result.msg);
	    },
	    error:function(data){
			dialog.close();
	    	showMsgDialog("提示","系统异常，保存失败！"+data);
	    }
	});	 
}


/**
 * 删除数据
 * @param id 数据id
 * @param url 保存地址
 */
function deleteInfo(id,url)
{	
	art.dialog({
		title: "删除提示",
		content:"您确定要删除吗?",
	    ok: function () {
	    	if (validateIsNull(url))
	    	{
	    		return true;
	    	}
	    	
	    	this.hide();
	    	var msgDialog = showMsgNoCancel("删除提示","正在删除中...请等待");
	    	$(".aui_close").css("display","none");
	    	$.ajax({
	    		dataType:'json',
	    		type:"post",
	    		url:url+"?id="+id,
	    		success:function(result){
	    			msgDialog.close();
	    			dialogsDialog("提示",result.msg);
	    	    },
	    	    error:function(data){
	    	    	msgDialog.close();
	    	    	showMsgDialog("提示","系统异常，删除失败！"+data);
	    	    }
	    	});	 
	        //return true;
	    },
	    cancelVal: '关闭',
	    cancel: true //为true等价于function(){}
	});
}