function DateToS(value,row,index){
    if(value!=null){
        return getFormatDateByLong(value,"yyyy-MM-dd hh:mm:ss");
    }
}

var setParameterType = 0;
function pass(type) {
    setParameterType = type;
    if(type==1){
        $("#scope").val("");

        $("#tradingContract").val("");

        $("#unitPrice").val("");

        $("#fullNum").val("");

        $("#stopProfitPoint").val("");

        $("#stopLossPoint").val("");

        $("#profitFee").val("");

        $("#profitPointPrice").val("");

        $("#lossFee").val("");

        $("#lossPointPrice").val("");

        $("#beatPoint").val("");
        $("#passWin").window({title:'添加'});
    } else if(type==2){
        var rows = $("#edatagrid").datagrid('getSelections');
        if (Check.validateSelectItems($("#edatagrid"),1)) {
           // $("#scope").val(rows[0].scope);
            $('#scope').combobox('select',rows[0].scope);
            $("#tradingContract").val(rows[0].tradingContract);

            $("#unitPrice").val(rows[0].unitPrice);

            $("#fullNum").val(rows[0].fullNum);

            $("#stopProfitPoint").val(rows[0].stopProfitPoint);

            $("#stopLossPoint").val(rows[0].stopLossPoint);

            $("#profitFee").val(rows[0].profitFee);

            $("#profitPointPrice").val(rows[0].profitPointPrice);

            $("#lossFee").val(rows[0].lossFee);

            $("#lossPointPrice").val(rows[0].lossPointPrice);

            $("#beatPoint").val(rows[0].beatPoint);

            $("#passWin").window({title:'修改'});
        }else{
            return;
        }
    }
    $("#passWin").show();
    $("#passWin").window('open');
}



$(document).ready(function(){
    $("#edatagrid").datagrid('reload');
});

function passSave() {
    var rows = $("#edatagrid").datagrid('getSelections');

        if ($("#passWin").form("validate")) {
            var scope = $("#scope").combobox('getValue');

            var tradingContract = $("#tradingContract").val();

            var unitPrice = $("#unitPrice").val();

            var fullNum = $("#fullNum").val();

            var stopProfitPoint = $("#stopProfitPoint").val();

            var stopLossPoint = $("#stopLossPoint").val();

            var profitFee = $("#profitFee").val();

            var profitPointPrice = $("#profitPointPrice").val();

            var lossFee = $("#lossFee").val();

            var lossPointPrice = $("#lossPointPrice").val();

            var beatPoint = $("#beatPoint").val();

            var parameters = '{}';


            if (setParameterType == 1) {
                parameters = {
                    "scope": scope,
                    "tradingContract": tradingContract,
                    "unitPrice": unitPrice,
                    "fullNum": fullNum,
                    "stopProfitPoint": stopProfitPoint,
                    "stopLossPoint": stopLossPoint,
                    "profitFee": profitFee,
                    "profitPointPrice": profitPointPrice,
                    "lossFee": lossFee,
                    "lossPointPrice": lossPointPrice,
                    "beatPoint": beatPoint
                };
                $.post(Check.rootPath() + "/admin/togetherFuture/create",
                    parameters,
                    function (data) {
                        if (data.success) {
                            Check.messageBox("提示", data.message);
                            $("#edatagrid").datagrid('reload');
                            passClose();
                        } else {
                            Check.messageBox("提示", data.message, "error");
                        }
                    });
            } else if (setParameterType == 2) {
                parameters = {
                    "id": rows[0].id,
                    "scope": scope,
                    "tradingContract": tradingContract,
                    "unitPrice": unitPrice,
                    "fullNum": fullNum,
                    "stopProfitPoint": stopProfitPoint,
                    "stopLossPoint": stopLossPoint,
                    "profitFee": profitFee,
                    "profitPointPrice": profitPointPrice,
                    "lossFee": lossFee,
                    "lossPointPrice": lossPointPrice,
                    "beatPoint": beatPoint
                };
                $.post(Check.rootPath() + "/admin/togetherFuture/edit",
                    parameters,
                    function (data) {
                        if (data.success) {
                            Check.messageBox("提示", data.message);
                            $("#edatagrid").datagrid('reload');
                            passClose();
                        } else {
                            Check.messageBox("提示", data.message, "error");
                        }
                    });
            }
        }
}

function passClose() {
    $("#passWin").show();
    $("#passWin").window('close');
}

/*function tradeToS(value,row,index) {
    if(value == 1){
        return "富士A50";
    }else if(value == 2){
        return "恒指期货";
    }else if(value == 3){
        return "国际原油";
    }else if(value == 4){
        return "股指期货";
    }
}*/

function DateToS(value,row,index){
    if(value!=null){
        return getFormatDateByLong(value,"yyyy-MM-dd hh:mm:ss");
    }
}

/**
 * 删除
 */
function deleteChannel(){
    if($("#edatagrid").datagrid("getSelected") == null){
        $.messager.show({
            title:'提示',
            msg:'请选择[' + 1+ ']条数据',
            timeout:5000,
            showType:'slide'
        });
    }
    var nodeId = $("#edatagrid").datagrid("getSelected").id;
    $.messager.confirm("提示", "是否确认删除条记录?", function (r) {
        if (r) {
            eyWindow.wprogress("系统提示","系统处理中,请稍候...");
            $.post(Check.rootPath()+'/admin/togetherFuture/deleted',{'id':nodeId},function(data){
                if(data.success){
                    eyWindow.closeProgress();
                    Check.messageBox("提示",data.message);
                    $("#edatagrid").datagrid("reload");
                    reloadSel();
                }else{
                    Check.messageBox("提示",data.message,"error");
                }
            });
        }
    });

}