function changeToType(value,row,index){
    if(value == 1){
        return "合买中";
    }else if(value == 2){
        return "操盘中";
    }else if(value == 3){
        return "已结算";
    }
}

function changeToStr(value, row, index) {
    return getFormatDateByLong(value, "yyyy-MM-dd hh:mm:ss");
}

function changeToColor(value,row,index){

     return '<font color="red">' + value + '</font>';
}
function changeToStrs(value, row, index) {
    return getFormatDateByLong(value, "MM-dd hh:mm ");
}



/**
 * 新增状态
 */
function addTradeOpen() {

    $("#addName").val("");
    $("#addOperateTime").val("");
    $("addOpenTime").datetimebox('setValue',"");
    $("#addTrade").show();
    $("#addTrade").window('open');

};

function addTradeClose() {
    $("#addTrade").show();
    $("#addTrade").window('close');

};
function showValue(){
    var rows = $("#dg003").datagrid('getSelections');
    if (Check.validateSelectItems($("#dg003"),1)) {
        var obj = rows[0];
        $.post(Check.rootPath() + "/admin/fTogetherTrade/getValue"
            ,{"id":obj.id} ,
            function(data){

                if (data.status == "true") {
                    $("#t_scope").val(data.t_scope);
                    $("#t_contract").val(data.t_contract);
                    $("#t_openTime").val(data.t_openTime);
                    $("#t_endTime").val(data.t_endTime);
                    $("#t_price").val(data.t_price);
                    $("#t_fullNum").val(data.t_fullNum);
                    $("#t_stopProfitPoint").val(data.t_stopProfitPoint);
                    $("#t_stopLossPoint").val(data.t_stopLossPoint);
                    $("#t_profitFee").val(data.t_profitFee);
                    $("#t_lossFee").val(data.t_lossFee);


                    $("#tradeValue").show();
                    $("#tradeValue").window("open");
                    $("#dg003").datagrid('reload');
                }
                else {
                    Check.messageBox("提示","找不到该方案","error");
                }
            });
    }
}
function markeOpen(){
	 var rows = $("#dg003").datagrid('getSelections');
    if(rows[0].status ==2){
	 if (Check.validateSelectItems($("#dg003"),1)) {
         var obj = rows[0];
         $.post(Check.rootPath() + "/admin/fTogetherTrade/getMarket"
             , {"tradeId": rows[0].id},
             function (data) {
                 if (data.success == true) {
                     if (data.obj.callOpenPoint == null || data.obj.callOpenPoint == "0") {
                         $("#callOpenPoint").val("");
                     } else {
                         $("#callOpenPoint").val(data.obj.callOpenPoint);
                     }
                     if (data.obj.putOpenPoint == null || data.obj.putOpenPoint == "0") {
                         $("#putOpenPoint").val("");
                     } else {
                         $("#putOpenPoint").val(data.obj.putOpenPoint);
                     }
                     if (data.obj.callClosePoint == null || data.obj.callClosePoint == "0") {
                         $("#callClosePoint").val("");
                     } else {
                         $("#callClosePoint").val(data.obj.callClosePoint);
                     }
                     if (data.obj.putClosePoint == null || data.obj.putClosePoint == "0") {
                         $("#putClosePoint").val("");
                     } else {
                         $("#putClosePoint").val(data.obj.putClosePoint);
                     }
                     $(".markets").each(function () {
                         $(this).remove();
                     });
                     if (data.obj.markes != null) {
                         var innerHtml = "";
                         var xHtml = "";
                         for (var i = 0; i <= data.obj.markes.length - 1; i++) {
                             if (null == data.obj.markes[i].point) {
                                 data.obj.markes[i].point = "";
                             }
                             if (i < 10) {
                                 xHtml = "&nbsp" + "第" + data.obj.markes[i].minutes + "分：<input id='minutes_" + data.obj.markes[i].minutes + "' value='" + data.obj.markes[i].point + "' name='" + data.obj.markes[i].id + "' style='width:105px'/>";
                             } else {
                                 xHtml = "第" + data.obj.markes[i].minutes + "分：<input id='minutes_" + data.obj.markes[i].minutes + "' value='" + data.obj.markes[i].point + "' name='" + data.obj.markes[i].id + "' style='width:105px'/>";
                             }
                             if (i % 5 == 0) {
                                 innerHtml += "<tr class='markets'><td colspan='6'>" + xHtml;

                             } else if (i % 5 == 4) {
                                 innerHtml += xHtml + "</td></tr>";
                             }
                             else {
                                 innerHtml += xHtml;
                             }
                         }
                         if (data.obj.markes.length % 5 != 4) {
                             innerHtml += "</td></tr>"
                         }
                         $("#detail_append_1").after(innerHtml);
                     }
                     $("#tradeMarket").show();
                     $("#tradeMarket").window('open');
                 }
                 else {
                     Check.messageBox("提示", data.message, "error");
                 }
             });
     }
	       
	    }else{
        Check.messageBox("提示", "只有操盘中才能输入", "error");
    }
}

function marketSave(){
    var rows = $("#dg003").datagrid('getSelections');
	var callOpenPoint =$("#callOpenPoint").val();
	var putOpenPoint =$("#putOpenPoint").val();
	var callClosePoint = $("#callClosePoint").val();
	var putClosePoint = $("#putClosePoint").val();
    var conjuns = [];
	$("input[id^=minutes_]").each(function(i){
		var id =$(this).attr("name");
		var minutes=$(this).attr("id");
		var idname = minutes.split("_")[1];
		var point=$(this).val();
        conjuns.push({id:id,minutes:idname,point:point});
	});
        var dataVo={conjuns:conjuns,callOpenPoint:callOpenPoint,putOpenPoint:putOpenPoint,
            callClosePoint:callClosePoint,putClosePoint:putClosePoint,tradeId:rows[0].id};
    $.ajax({
        type : 'post',
        url: Check.rootPath() + "/admin/fTogetherTrade/saveMarket",
        dataType:'json',
        contentType: "application/json",
        async: false,
        data :JSON.stringify(dataVo),
        success: function(data){
            $.messager.progress('close');
            if (data.success == true) {
                Check.messageBox("提示","保存成功");
               
            }
            else {
                Check.messageBox("提示",data.message,"error");
            }
        }
    });

    //$.post(Check.rootPath() + "/admin/fTogetherTrade/saveMarket"
    //    ,{markets:JSON.stringify(dataVo)} ,
    //    function(data){
    //        $.messager.progress('close');
    //        if (data == "success") {
    //            Check.messageBox("提示","删除成功!");
    //            $("#dg003").datagrid('reload');
    //        }
    //        else {
    //            Check.messageBox("提示",data,"error");
    //        }
    //    });
}

function marketClose(){
    $("#tradeMarket").show();
    $("#dg003").datagrid('reload');
    $("#tradeMarket").window('close');
}


/**
 * 更新
 */
function updateTradeOpen() {
    //validationCode();
    var rows = $("#dg003").datagrid('getSelections');

    if (Check.validateSelectItems($("#dg003"),1)) {
        var obj = rows[0];
        $("#typeName").html(rows[0].typeName);  
        $("#updateId").val(obj.id);
        $("#updateName").val(obj.name);
        $("#updateOpenTime").datetimebox('setValue', obj.openTimeValue);
        $("#updateOperateTime").val(obj.operateTime);
        $("#updateSpecies").attr("disabled",true);
        	

        $("#updateTrade").show();
        $("#updateTrade").window('open');
    }


};

function updateTradeClose() {
    $("#updateTrade").show();
    $("#updateTrade").window('close');

};

function saveTradeClose() {
    $("#addTrade").show();
    $("#addTrade").window('close');

};
// 立即结算
function instantSettle(){
	var rows = $("#dg003").datagrid('getSelections');
    if (Check.validateSelectItems($("#dg003"),1)) {
        $.messager.confirm("提示", "确认立即结算?", function (r) {
            if (r) {
                $.messager.progress({title:"提示",msg:"正在处理中...."});
                $.post(Check.rootPath() + "/admin/fTogetherTrade/instantSettle"
                    ,{"id":rows[0].id} ,
                    function(data){
                        $.messager.progress('close');
                        if (data.success) {
                            Check.messageBox("提示",data.message);
                            $("#dg003").datagrid('reload');
                        }
                        else {
                        	   Check.messageBox("提示",data.message,"error");
                        }
                    });
            }
        });
   }
}
/**
 * 立即退款
 */
function instantRefund(){
	  var rows = $("#dg003").datagrid('getSelections');
	    if (Check.validateSelectItems($("#dg003"),1)) {
	        $.messager.confirm("提示", "确认立即退款?", function (r) {
	            if (r) {
	                $.messager.progress({title:"提示",msg:"正在处理中...."});
	                $.post(Check.rootPath() + "/admin/fTogetherTrade/instantRefund"
	                    ,{"id":rows[0].id} ,
	                    function(data){
	                        $.messager.progress('close');
	                        if (data.success) {
	                            Check.messageBox("提示",data.message);
	                            $("#dg003").datagrid('reload');
	                        }
	                        else {
	                        	   Check.messageBox("提示",data.message,"error");
	                        }
	                    });
	            }
	        });
	   }
}
/**
 * 删除
 */
function deleteTrade() {
    var rows = $("#dg003").datagrid('getSelections');

    if (Check.validateSelectItems($("#dg003"),1)) {

        $.messager.confirm("提示", "是否需要删除?", function (r) {
            if (r) {
                $.messager.progress({title:"提示",msg:"删除中...."});
                $.post(Check.rootPath() + "/admin/fTogetherTrade/deleted"
                    ,{"id":rows[0].id} ,
                    function(data){
                        $.messager.progress('close');
                        if (data == "success") {
                            Check.messageBox("提示","删除成功!");
                            $("#dg003").datagrid('reload');
                        }
                        else {
                            Check.messageBox("提示",data,"error");
                        }
                    });
            }
        });


    }
}
//使用
//$('#id').validatebox('remove'); //删除
//$('#id').validatebox('reduce'); //恢复

/**
 * 提交
 */
function addTradeSave() {

    if ($("#voForm").form("validate")) {
        var validateVal = true;
            	var val = $("#addOpenTime").datetimebox("getValue");
                var timestamp = Date.parse(new Date());
                timestamp = timestamp / 1000; 
                val = val.substring(0,19);    
                val = val.replace(/-/g,'/'); 
                var timestamps = new Date(val).getTime();
                timestamps = timestamps/1000;
                    if (val === undefined || val == null || val == ""){
                        validateVal = false;
                        $.easyui.messageBox("提示","开仓时间不能为空!","warning");
                    }else if(timestamps<timestamp){
                    	 validateVal = false;
                         $.easyui.messageBox("提示","开仓时间小于当前时间!","warning");
                    }
        if (!validateVal) {
            return false;
        }
    }
    else {
        return false;
    }
    $.messager.progress({title:"提示",msg:"方案生成生成中...."});
    $.post(Check.rootPath() + "/admin/fTogetherTrade/add"
        ,$("#voForm").serialize() ,
        function(data){
            $.messager.progress('close');
            if (data.message == "success") {
                Check.messageBox("提示","保存成功");
                $("#dg003").datagrid('reload');
                addTradeClose();
            }
            else {
                Check.messageBox("提示",data.message,"error");
            }
        });


};


/**
 * 提交
 */
function updateTradeSave() {
    if (!$("#voUpdateForm").form("validate")) {
        return false;
    }
    $.messager.progress({title:"提示",msg:"方案生成中"});
    $.post(Check.rootPath() + "/admin/fTogetherTrade/edit"
        ,$("#voUpdateForm").serialize() ,
        function(data){
            $.messager.progress('close');
            if (data == "success") {
                Check.messageBox("提示","更新成功");
                $("#dg003").datagrid('reload');
                updateTradeClose();
            }
            else {
                Check.messageBox("提示",data,"error");
            }
        });
};

$(function(){
	$('#dgdetail').datagrid({
		nowrap : true,// 在同一行中显示数据
		autoRowHeight : false,// 自动设置行高
		striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible : true,// 折叠面板
		rownumbers : true,// 显示行号
		pagination : true,// 底部显示分页工具栏
		url:basepath+'admin/fTogetherTrade/ProfitValue',
		fitColumns : true,// 自适应列宽
		columns : [ [ {
			field : 'addTime',
			title : '合买时间',
			width : 200,
			formatter : function(value, row, index) {
				var values = "";
				if(value!=null ){
					values =getFormatDateByLong(value/1000, " MM-dd hh:mm ");
					 if(row.isBack == 1){
						return '<font color="red">' + values + '</font>';
					 }
				}
				return values;
			}
		},{
			field : 'isBack',
			hidden : 'true',
		},{
			field : 'mobile',
			title : '手机号码',
			width : 150,
			formatter : function(value, row, index) {
				if(row.isBack == 1){
					return '<font color="red">' + value + '</font>';
				}
				return value;
				
			}
		} ] ],
		onLoadSuccess : function(data) {
			datagridUtils.loadSuccess(data, 'dgdetail');
		}
	});
});



var arrearsEndOptions = {
		openDetail:function(data) {
			var rowData = $('#dg003').datagrid('getSelected');
			if (rowData==null){
				eyWindow.walert("提示", '请选择要操作的记录', 'info');
				return;
			}
			$("#search_EQ_Id").val(rowData.id);
			$("#search_EQ_direction").val(data);
			$("#detailBtn").click();
			$("#Detail").window('open');	
		}
	}
