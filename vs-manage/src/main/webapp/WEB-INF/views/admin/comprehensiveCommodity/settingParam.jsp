<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Content-Language" content="zh-CN" />
  <!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
  <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
  <title></title>
  <%@include file="../../common/import-easyui-js.jspf"%>
  <script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
  <script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
  <script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
  <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
  <script type="text/javascript">
    var setParameterType = 0;
    var tabsType="";
    function pass(type,tabs) {
      setParameterType = type;
      tabsType=tabs;
      if(tabsType=='one'){
        if(type==1){
          $("#traderBond").val("");
          $("#traderTotal").val("");
          $("#lineLoss").val("");
          $("#goldenMoney").val("");
          $("#silverLever").val("");
          $("#aluminumLever").val("");
          $("#goldLever").val("");
          $("#asphaltLever").val("");
          $("#copperLever").val("");
          $("#coilLever").val("");
          $("#daxtranActualLever").val("");
          $("#nickelLever").val("");
          $("#rebarLever").val("");
          $("#zincLever").val("");
          $("#rubberLever").val("");
          $("#beanLever").val("");
          $("#cornLever").val("");
          $("#cornStarchLever").val("");
          $("#ironOreLever").val("");
          $("#cokeLever").val("");
          $("#eggLever").val("");
          $("#cokingCoalLever").val("");
          $("#plasticLever").val("");
          $("#soybeanMealLever").val("");
          $("#palmOilLever").val("");
          $("#polypropyleneLever").val("");
          $("#soybeanOilLever").val("");
          $("#cottonLever").val("");
          $("#glassLever").val("");
          $("#methanolLever").val("");
          $("#rapeOilLever").val("");
          $("#rapeseedMealLever").val("");
          $("#sugarLever").val("");
          $("#pTALever").val("");
          $("#powerCoalLever").val("");
          $("#fiveNationalDebtLever").val("");
          $("#tenNationalDebtLever").val("");


          $("#passWin").window({title:'添加'});
        } else if(type==2){
          var rows = $("#edatagrid").datagrid('getSelections');
          if (Check.validateSelectItems($("#edatagrid"),1)) {
            $("#traderBond").val(rows[0].traderBond);
            $("#traderTotal").val(rows[0].traderTotal);
            $("#lineLoss").val(rows[0].lineLoss);
            $("#goldenMoney").val(rows[0].goldenMoney);
            $("#silverLever").val(rows[0].silverLever);
            $("#aluminumLever").val(rows[0].aluminumLever);
            $("#goldLever").val(rows[0].goldLever);
            $("#asphaltLever").val(rows[0].asphaltLever);
            $("#copperLever").val(rows[0].copperLever);
            $("#coilLever").val(rows[0].coilLever);
            $("#daxtranActualLever").val(rows[0].daxtranActualLever);
            $("#nickelLever").val(rows[0].nickelLever);
            $("#rebarLever").val(rows[0].rebarLever);
            $("#zincLever").val(rows[0].zincLever);
            $("#rubberLever").val(rows[0].rubberLever);
            $("#beanLever").val(rows[0].beanLever);
            $("#cornLever").val(rows[0].cornLever);
            $("#cornStarchLever").val(rows[0].cornStarchLever);
            $("#ironOreLever").val(rows[0].ironOreLever);
            $("#cokeLever").val(rows[0].cokeLever);
            $("#eggLever").val(rows[0].eggLever);
            $("#cokingCoalLever").val(rows[0].cokingCoalLever);
            $("#plasticLever").val(rows[0].plasticLever);
            $("#soybeanMealLever").val(rows[0].soybeanMealLever);
            $("#palmOilLever").val(rows[0].palmOilLever);
            $("#polypropyleneLever").val(rows[0].polypropyleneLever);
            $("#soybeanOilLever").val(rows[0].soybeanOilLever);
            $("#cottonLever").val(rows[0].cottonLever);
            $("#glassLever").val(rows[0].glassLever);
            $("#methanolLever").val(rows[0].methanolLever);
            $("#rapeOilLever").val(rows[0].rapeOilLever);
            $("#rapeseedMealLever").val(rows[0].rapeseedMealLever);
            $("#sugarLever").val(rows[0].sugarLever);
            $("#pTALever").val(rows[0].pTALever);
            $("#powerCoalLever").val(rows[0].powerCoalLever);
            $("#fiveNationalDebtLever").val(rows[0].fiveNationalDebtLever);
            $("#tenNationalDebtLever").val(rows[0].tenNationalDebtLever);
            $("#passWin").window({title:'修改'});
          }else{
            return;
          }
        }

        $("#passWin").show();
        $("#passWin").window('open');
      }else if(tabsType=='two'){
        if(type==1){
          $("#tradeType_S").combobox('setValue','');
          $("#mainContract").val("");
          $("#price").val("");
         // $("#exchange").combobox('setValue',"");
          $("#passWinP").window({title:'添加'});
        } else if(type==2){
          var rows = $("#edatagridP").datagrid('getSelections');
          if (Check.validateSelectItems($("#edatagridP"),1)) {

            $("#tradeType_S").combobox('select',rows[0].tradeType);
            $("#mainContract").val(rows[0].mainContract);
            $("#price").val(rows[0].price);
            $("#exchange").combobox('select',rows[0].exchange);
            $("#passWinP").window({title:'修改'});

          }else{
            return;
          }
        }

        $("#passWinP").show();
        $("#passWinP").window('open');
      }


    };
    function passSave() {
      var rows = $("#edatagrid").datagrid('getSelections');
      if(tabsType=='one'){
        if ($("#passWin").form("validate")) {
          var traderBond = $("#traderBond").val();
          var traderTotal = $("#traderTotal").val();
          var lineLoss = $("#lineLoss").val();
          var goldenMoney = $("#goldenMoney").val();
          var silverLever = $("#silverLever").val();
          var aluminumLever = $("#aluminumLever").val();
          var goldLever = $("#goldLever").val();
          var asphaltLever = $("#asphaltLever").val();
          var copperLever = $("#copperLever").val();
          var coilLever =$("#coilLever").val();
          var daxtranActualLever = $("#daxtranActualLever").val();
          var nickelLever = $("#nickelLever").val();
          var rebarLever =  $("#rebarLever").val();
          var zincLever = $("#zincLever").val();
          var rubberLever = $("#rubberLever").val();
          var beanLever = $("#beanLever").val();
          var cornLever = $("#cornLever").val();
          var cornStarchLever = $("#cornStarchLever").val();
          var ironOreLever = $("#ironOreLever").val();
          var cokeLever = $("#cokeLever").val();
          var eggLever = $("#eggLever").val();
          var cokingCoalLever = $("#cokingCoalLever").val();
          var plasticLever = $("#plasticLever").val();
          var soybeanMealLever = $("#soybeanMealLever").val();
          var palmOilLever = $("#palmOilLever").val();
          var polypropyleneLever = $("#polypropyleneLever").val();
          var soybeanOilLever = $("#soybeanOilLever").val();
          var cottonLever = $("#cottonLever").val();
          var glassLever = $("#glassLever").val();
          var methanolLever = $("#methanolLever").val();
          var rapeOilLever = $("#rapeOilLever").val();
          var rapeseedMealLever = $("#rapeseedMealLever").val();
          var sugarLever = $("#sugarLever").val();
          var pTALever = $("#pTALever").val();
          var powerCoalLever = $("#powerCoalLever").val();
          var fiveNationalDebtLever = $("#fiveNationalDebtLever").val();
          var tenNationalDebtLever = $("#tenNationalDebtLever").val();
          var parameters = '{}';
          if(setParameterType==1){
            parameters = {"traderBond":traderBond,"traderTotal":traderTotal,"lineLoss":lineLoss,"goldenMoney":goldenMoney,
            "silverLever":silverLever,"aluminumLever":aluminumLever,"goldLever":goldLever,"asphaltLever":asphaltLever,"copperLever":copperLever,
            "nickelLever":nickelLever,"rebarLever":rebarLever,"zincLever":zincLever,"rubberLever":rubberLever,
            "beanLever":beanLever,"cornLever":cornLever,"cornStarchLever":cornStarchLever,"ironOreLever":ironOreLever,"cokeLever":cokeLever,
            "eggLever":eggLever,"cokingCoalLever":cokingCoalLever,"plasticLever":plasticLever,"soybeanMealLever":soybeanMealLever,"palmOilLever":palmOilLever,
            "polypropyleneLever":polypropyleneLever,"soybeanOilLever":soybeanOilLever,"cottonLever":cottonLever,"glassLever":glassLever,"methanolLever":methanolLever,
            "rapeOilLever":rapeOilLever,"rapeseedMealLever":rapeseedMealLever,"sugarLever":sugarLever,"pTALever":pTALever,"powerCoalLever":powerCoalLever,
            "fiveNationalDebtLever":fiveNationalDebtLever,"tenNationalDebtLever":tenNationalDebtLever,"coilLever":coilLever};
            $.post(Check.rootPath() + "/admin/ComprehensiveCommodity/create",
                    parameters,
                    function(data){
                      if (data.success) {
                        Check.messageBox("提示",data.message);
                        $("#edatagrid").datagrid('reload');
                        passClose() ;
                      } else {
                        Check.messageBox("提示",data.message,"error");
                      }
                    });
          } else if(setParameterType==2){
            parameters = {"id":rows[0].id,"traderBond":traderBond,"traderTotal":traderTotal,"lineLoss":lineLoss,"goldenMoney":goldenMoney,
              "silverLever":silverLever,"aluminumLever":aluminumLever,"goldLever":goldLever,"asphaltLever":asphaltLever,"copperLever":copperLever,
              "nickelLever":nickelLever,"rebarLever":rebarLever,"zincLever":zincLever,"rubberLever":rubberLever,
              "beanLever":beanLever,"cornLever":cornLever,"cornStarchLever":cornStarchLever,"ironOreLever":ironOreLever,"cokeLever":cokeLever,
              "eggLever":eggLever,"cokingCoalLever":cokingCoalLever,"plasticLever":plasticLever,"soybeanMealLever":soybeanMealLever,"palmOilLever":palmOilLever,
              "polypropyleneLever":polypropyleneLever,"soybeanOilLever":soybeanOilLever,"cottonLever":cottonLever,"glassLever":glassLever,"methanolLever":methanolLever,
              "rapeOilLever":rapeOilLever,"rapeseedMealLever":rapeseedMealLever,"sugarLever":sugarLever,"pTALever":pTALever,"powerCoalLever":powerCoalLever,
              "fiveNationalDebtLever":fiveNationalDebtLever,"tenNationalDebtLever":tenNationalDebtLever ,"coilLever":coilLever};
            $.post(Check.rootPath() + "/admin/ComprehensiveCommodity/edit",
                    parameters,
                    function(data){
                      if (data.success) {
                        Check.messageBox("提示",data.message);
                        $("#edatagrid").datagrid('reload');
                        passClose() ;
                      } else {
                        Check.messageBox("提示",data.message,"error");
                      }
                    });
          }

        }
      }else if(tabsType=='two'){
        var $rows = $("#edatagridP").datagrid('getSelections');
        var tr=$("#tradeType_S").combobox('getValue');
        if(tr==null||tr==""){
          Check.messageBox("提示","交易品种不能为空！！");
          return;
        }

          var exchange=$("#exchange").combobox('getValue');



        if ($("#passWinP").form("validate")) {
          var tradeType=tr;
          var mainContract = $("#mainContract").val();
          var price = $("#price").val();


          var parameters = '{}';
          if(setParameterType==1){
            parameters = {"tradeType":tradeType,"mainContract":mainContract,"price":price,"exchange":exchange};
            $.post(Check.rootPath() + "/admin/Comprehensive_Commodity/create",
                    parameters,
                    function(data){
                      if (data.success) {
                        Check.messageBox("提示",data.message);
                        $("#edatagridP").datagrid('reload');
                        passClose() ;
                      } else {
                        Check.messageBox("提示",data.message,"error");
                      }
                    });
          } else if(setParameterType==2){
           // alert(exchange);
            parameters = {"id":$rows[0].id,"tradeType":tradeType,"mainContract":mainContract,"price":price,"exchange":exchange};
            $.post(Check.rootPath() + "/admin/Comprehensive_Commodity/edit",
                    parameters,
                    function(data){
                      if (data.success) {
                        Check.messageBox("提示",data.message);
                        $("#edatagridP").datagrid('reload');
                        passClose() ;
                      } else {
                        Check.messageBox("提示",data.message,"error");
                      }
                    });
          }

        }
      }

    };

    function passClose() {
      if(tabsType=='one'){
        $("#passWin").show();
        $("#passWin").window('close');
      }else if(tabsType=='two'){
        $("#passWinP").show();
        $("#passWinP").window('close');
      }

    };

    function tradeToS(value,row,index) {

      if (value == 0) {
        return '白银';
      } else if (value == 1) {
        return '铝';
      } else if (value == 2) {
        return '黄金';
      } else if (value == 3) {
        return '沥青';
      }
      else if (value == 4) {
        return '铜';
      } else if (value == 5) {
        return '热卷';
      } else if (value == 6) {
        return '镍';
      } else if (value == 7) {
        return '螺纹钢';
      } else if (value == 8) {
        return '锌';
      } else if (value == 9) {
        return '橡胶';
      } else if (value == 10) {
        return '豆一';
      } else if (value == 11) {
        return '玉米';
      } else if (value == 12) {
        return '玉米淀粉';
      } else if (value == 13) {
        return '铁矿石';
      } else if (value == 14) {
        return '焦炭';
      } else if (value == 15) {
        return '鸡蛋';
      } else if (value == 16) {
        return '焦煤';
      } else if (value == 17) {
        return '塑料';
      } else if (value == 18) {
        return '豆粕';
      } else if (value == 19) {
        return '棕榈油';
      } else if (value == 20) {
        return '聚丙烯';
      } else if (value == 21) {
        return '豆油';
      } else if (value == 22) {
        return '棉花';
      } else if (value == 23) {
        return '玻璃';
      } else if (value == 24) {
        return '甲醇';
      } else if (value == 25) {
        return '菜油';
      } else if (value == 26) {
        return '菜粕';
      } else if (value == 27) {
        return '白糖';
      } else if (value == 28) {
        return 'PTA';
      } else if (value == 29) {
        return '动力煤';
      } else if (value == 30) {
        return '5年国债';
      } else if (value == 31) {
        return '10年国债';
      }
    }
    function priceToS(value,row,index){
      if(value!=null&&value>0){
        return value+'元/手';
      }
    }

    function DateToS(value,row,index){
      if(value!=null){
        return getFormatDateByLong(value,"yyyy-MM-dd hh:mm:ss");
      }
    }

    $(document).ready(function(){
      $('#spifTab').tabs({
        onSelect:function(title){
          if(title=='商品综合参数'){
            $("#edatagrid").datagrid('reload');

          }else if(title=='商品综合价格'){
            $("#edatagridP").datagrid('reload');
          }
        }
      })
    })

  </script>
</head>
<body>

<shiro:hasPermission name="sys:settingParams:comprehensiveCommodity:view">
  <div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
    <div title="商品综合参数" data-options="tools:'#p-tools'" style="padding:20px;">
      <div id="audittb" style="padding: 5px; height: auto">
        <div style="margin-bottom: 5px">
        <shiro:hasPermission name="sys:settingParams:comprehensiveCommodity:create">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"   plain="true" onclick="pass(1,'one')">添加</a>
          </shiro:hasPermission>
          <shiro:hasPermission name="sys:settingParams:comprehensiveCommodity:update">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(2,'one')">修改</a>
          </shiro:hasPermission>
        </div>
      </div>
      <table id="edatagrid" class="easyui-datagrid"  pagination="true"
             toolbar="#audittb" url="${ctx}/admin/ComprehensiveCommodity/easyuiPage"
             rownumbers="true" fitColumns="true" singleSelect="true"
             data-options="checkOnSelect:true,toolbar:'#audittb',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]],
				        onLoadSuccess:function(data){
							datagridUtils.loadSuccess(data,'edatagrid');
						}">
        <thead>
        <tr>
          <th field="id" hidden="true"></th>
          <th field="silverLever" hidden="true"></th>
          <th field="aluminumLever" hidden="true"></th>
          <th field="goldLever" hidden="true"></th>
          <th field="asphaltLever" hidden="true"></th>
          <th field="copperLever" hidden="true"></th>
          <th field="coilLever" hidden="true"></th>
          <th field="nickelLever" hidden="true"></th>
          <th field="rebarLever" hidden="true"></th>
          <th field="zincLever" hidden="true"></th>
          <th field="rubberLever" hidden="true"></th>
          <th field="beanLever" hidden="true"></th>
          <th field="cornLever" hidden="true"></th>
          <th field="cornStarchLever" hidden="true"></th>
          <th field="ironOreLever" hidden="true"></th>
          <th field="cokeLever" hidden="true"></th>
          <th field="eggLever" hidden="true"></th>
          <th field="cokingCoalLever" hidden="true"></th>
          <th field="plasticLever" hidden="true"></th>
          <th field="soybeanMealLever" hidden="true"></th>
          <th field="polypropyleneLever" hidden="true"></th>
          <th field="soybeanOilLever" hidden="true"></th>
          <th field="cottonLever" hidden="true"></th>
          <th field="glassLever" hidden="true"></th>
          <th field="methanolLever" hidden="true"></th>
          <th field="rapeOilLever" hidden="true"></th>
          <th field="rapeseedMealLever" hidden="true"></th>
          <th field="sugarLever" hidden="true"></th>
          <th field="pTALever" hidden="true"></th>
          <th field="powerCoalLever" hidden="true"></th>
          <th field="fiveNationalDebtLever" hidden="true"></th>
          <th field="tenNationalDebtLever" hidden="true"></th>

          <th field="traderBond" width="150" sortable="true">操盘保证金（人民币）</th>
          <th field="traderTotal" width="150">总操盘资金（人民币）</th>
          <th field="lineLoss" width="150">亏损平仓线（人民币）</th>
          <th field="goldenMoney" width="150">入金金额（人民币）</th>
          <th field="updateTime" width="150" formatter="DateToS">更新时间</th>
          <th field="updateUser" width="150">操作人</th>

        </tr>
        </thead>
      </table>
    </div>
    <div title="商品综合价格" data-options="tools:'#p-tools'" style="padding:20px;">
      <div id="audittbP" style="padding: 5px; height: auto">
        <div style="margin-bottom: 5px">
          <shiro:hasPermission name="sys:settingParams:comprehensiveCommodity:create">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"   plain="true" onclick="pass(1,'two')">添加</a>
          </shiro:hasPermission>
          <shiro:hasPermission name="sys:settingParams:comprehensiveCommodity:update">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(2,'two')">修改</a>
          </shiro:hasPermission>
        </div>
      </div>
      <table id="edatagridP" class="easyui-datagrid"  pagination="true"
             toolbar="#audittbP" url="${ctx}/admin/Comprehensive_Commodity/easyuiPage"
             rownumbers="true" fitColumns="true" singleSelect="true"
             data-options="checkOnSelect:true,toolbar:'#audittbP',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]],
				        onLoadSuccess:function(data){
							datagridUtils.loadSuccess(data,'edatagrid');
						}">
        <thead>
        <tr>
          <th field="id" hidden="true"></th>
          <th field="tradeType" width="150" sortable="true" formatter="tradeToS">交易品种</th>
          <th field="mainContract" width="150">主力合约</th>
          <th field="exchange" width="150">交易所</th>
          <th field="price" width="150"  formatter="priceToS">价格</th>
          <th field="updateTime" width="150" formatter="DateToS">更新时间</th>
          <th field="updateUser" width="150">操作人</th>
        </tr>
        </thead>
      </table>
    </div>
  </div>


  <!-- 国际综合参数操作弹框-->
  <div id="passWin" class="easyui-window" title="编辑"
       style="width:850px;height:500px;display:none;border:none; overflow: hidden;"
       data-options="iconCls:'icon-save',modal:true,closed:true">
    <form id="passForm">
      <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td class="label right">操盘保证金(￥):</td>
          <td >
            <input id="traderBond" name="traderBond"   class="easyui-validatebox"  data-options="required:true"/>
          </td>

          <td class="label right">总操盘资金(￥):</td>
          <td>
            <input id="traderTotal" name="traderTotal" class="easyui-validatebox" data-options="required:true" />
          </td>

          <td class="label right">亏损平仓线(￥):</td>
          <td>
            <input id="lineLoss" name="lineLoss" class="easyui-validatebox"  data-options="required:true"/>
          </td>

          <td><span ></span></td>
        </tr>

        <tr>
          <td class="label right">入金金额(￥):</td>
          <td>
            <input id="goldenMoney" name="goldenMoney" class="easyui-validatebox"  data-options="required:true"/>
          </td>
        <td class="label right">白银手数:</td>
        <td>
          <input id="silverLever" name="silverLever" class="easyui-validatebox"  data-options="required:true"/>
        </td>
        <td class="label right">铝手数:</td>
        <td>
          <input id="aluminumLever" name="aluminumLever" class="easyui-validatebox"  data-options="required:true"/>
        </td>

        <td><span ></span></td>
      </tr>
          <tr>
            <td class="label right">黄金手数:</td>
            <td >
              <input id="goldLever" name="goldLever"   class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">沥青手数：</td>
            <td>
              <input id="asphaltLever" name="asphaltLever" class="easyui-validatebox" data-options="required:true" />
            </td>
            <td class="label right">铜手数：</td>
            <td>
              <input id="copperLever" name="copperLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>

            <td><span ></span></td>
          </tr>

          <tr>
            <td class="label right">热卷手数：</td>
            <td>
              <input id="coilLever" name="coilLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">镍手数：</td>
            <td>
              <input id="nickelLever" name="nickelLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">螺纹钢手数:</td>
            <td>
              <input id="rebarLever" name="rebarLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>

            <td><span ></span></td>
          </tr>
          <tr>
            <td class="label right">锌手数:</td>
            <td >
              <input id="zincLever" name="zincLever"   class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">橡胶手数:</td>
            <td>
              <input id="rubberLever" name="rubberLever" class="easyui-validatebox" data-options="required:true" />
            </td>
            <td class="label right">豆一手数:</td>
            <td>
              <input id="beanLever" name="beanLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>

            <td><span ></span></td>
          </tr>

          <tr>
            <td class="label right">玉米手数:</td>
            <td>
              <input id="cornLever" name="cornLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">玉米淀粉手数:</td>
            <td>
              <input id="cornStarchLever" name="cornStarchLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">铁矿石手数:</td>
            <td>
              <input id="ironOreLever" name="ironOreLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>

            <td><span ></span></td>
          </tr>
          <tr>
            <td class="label right">焦炭手数:</td>
            <td >
              <input id="cokeLever" name="cokeLever"   class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">鸡蛋手数:</td>
            <td>
              <input id="eggLever" name="eggLever" class="easyui-validatebox" data-options="required:true" />
            </td>
            <td class="label right">焦煤手数:</td>
            <td>
              <input id="cokingCoalLever" name="cokingCoalLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>

            <td><span ></span></td>
          </tr>

          <tr>
            <td class="label right">塑料手数:</td>
            <td>
              <input id="plasticLever" name="plasticLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">豆粕手数:</td>
            <td>
              <input id="soybeanMealLever" name="soybeanMealLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">棕榈油手数:</td>
            <td>
              <input id="palmOilLever" name="palmOilLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>

            <td><span ></span></td>
          </tr>
          <tr>
            <td class="label right">聚丙烯手数:</td>
            <td >
              <input id="polypropyleneLever" name="polypropyleneLever"   class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">豆油手数:</td>
            <td>
              <input id="soybeanOilLever" name="soybeanOilLever" class="easyui-validatebox" data-options="required:true" />
            </td>
            <td class="label right">棉花手数:</td>
            <td>
              <input id="cottonLever" name="cottonLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>

            <td><span ></span></td>
          </tr>

          <tr>
            <td class="label right">玻璃手数:</td>
            <td>
              <input id="glassLever" name="glassLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">甲醇手数:</td>
            <td>
              <input id="methanolLever" name="methanolLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">菜油手数:</td>
            <td>
              <input id="rapeOilLever" name="rapeOilLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>

            <td><span ></span></td>
          </tr>
          <tr>
            <td class="label right">菜粕手数:</td>
            <td >
              <input id="rapeseedMealLever" name="rapeseedMealLever"   class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">白糖手数:</td>
            <td>
              <input id="sugarLever" name="sugarLever" class="easyui-validatebox" data-options="required:true" />
            </td>
            <td class="label right">PTA手数:</td>
            <td>
              <input id="pTALever" name="pTALever" class="easyui-validatebox"  data-options="required:true"/>
            </td>

            <td><span ></span></td>
          </tr>

          <tr>
            <td class="label right">动力煤手数:</td>
            <td>
              <input id="powerCoalLever" name="powerCoalLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">5年期国债手数:</td>
            <td>
              <input id="fiveNationalDebtLever" name="fiveNationalDebtLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>
            <td class="label right">10年期国债手数:</td>
            <td>
              <input id="tenNationalDebtLever" name="tenNationalDebtLever" class="easyui-validatebox"  data-options="required:true"/>
            </td>

            <td><span ></span></td>
          </tr>

        <tr>
          <td align="center" colspan="9">
            <a id="btn" href="javascript:void(0);" onclick="passSave()" class="easyui-linkbutton">提交</a>
            <a id="btn" href="javascript:void(0);" onclick="passClose()" class="easyui-linkbutton">取消</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <!-- 国际综合价格操作弹框-->
  <div id="passWinP" class="easyui-window" title="编辑"
       style="width:450px;height:200px;display:none;border:none; overflow: hidden;"
       data-options="iconCls:'icon-save',modal:true,closed:true">
    <form id="passForm">
      <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td class="label right">交易品种:</td>
          <td>
            <select id="tradeType_S" class="easyui-combobox" name="tradeType" style="width:174px;">
              <option value="-1" selected="selected"></option>
              <option value="0">白银</option>
              <option value="1">铝</option>
              <option value="2">黄金</option>
              <option value="3">沥青</option>
              <option value="4">铜</option>
              <option value="5">热卷</option>
              <option value="6">镍</option>
              <option value="7">螺纹钢</option>
              <option value="8">锌</option>
              <option value="9">橡胶</option>
              <option value="10">豆一</option>
              <option value="11">玉米</option>
              <option value="12">玉米淀粉</option>
              <option value="13">铁矿石</option>
              <option value="14">焦炭</option>
              <option value="15">鸡蛋</option>
              <option value="16">焦煤</option>
              <option value="17">塑料</option>
              <option value="18">豆粕</option>
              <option value="19">棕榈油</option>
              <option value="20">聚丙烯</option>
              <option value="21">豆油</option>
              <option value="22">棉花</option>
              <option value="23">玻璃</option>
              <option value="24">甲醇</option>
              <option value="25">菜油</option>
              <option value="26">菜粕</option>
              <option value="27">白糖</option>
              <option value="28">PTA</option>
              <option value="29">动力煤</option>
              <option value="30">5年期国债</option>
              <option value="31">10年期国债</option>
            </select>

          </td>
          <td><span ></span></td>
        </tr>
        <tr>
          <td class="label right">交易所:</td>
          <td>
            <select id="exchange" class="easyui-combobox" name="exchange" style="width:174px;">
              <option value="上海期货交易所" selected="selected">上海期货交易所</option>
              <option value="大连商品交易所">大连商品交易所</option>
              <option value="郑州商品交易所">郑州商品交易所</option>
              <option value="中国金融期货交易所">中国金融期货交易所</option>
            </select>

          </td>
          <td><span ></span></td>
        </tr>
        <tr>
          <td class="label right">主力合约:</td>
          <td>
            <input id="mainContract" name="mainContract" class="easyui-validatebox" data-options="required:true" />
          </td>
          <td><span ></span></td>
        </tr>
        <tr>
          <td class="label right">价格:</td>
          <td>
            <input id="price" name="price" class="easyui-validatebox"  data-options="required:true"/>元/手
          </td>
          <td><span ></span></td>
        </tr>
        <tr>
          <td align="center" colspan="3">
            <a id="btn" href="javascript:void(0);" onclick="passSave()" class="easyui-linkbutton">提交</a>
            <a id="btn" href="javascript:void(0);" onclick="passClose()" class="easyui-linkbutton">取消</a>
          </td>
        </tr>
      </table>
    </form>
  </div>


</shiro:hasPermission>
<shiro:lacksPermission name="sys:settingParams:comprehensiveCommodity:view">
  <%@ include file="../../common/noPermission.jsp"%>
</shiro:lacksPermission>
</body>
</html>