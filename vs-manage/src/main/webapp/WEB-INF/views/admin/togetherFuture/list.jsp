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
  <script type="text/javascript" src="${ctx}/static/script/togetherFuture/list.js"></script>
  <script type="text/javascript">





  </script>
</head>
<body>

<shiro:hasPermission name="sys:settingParams:togetherFuture:view">
  <div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
    <div title="期货合买参数" data-options="tools:'#p-tools'" style="padding:20px;">
      <div id="audittb" style="padding: 5px; height: auto">
        <div style="margin-bottom: 5px">
        <shiro:hasPermission name="sys:settingParams:togetherFuture:create">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"   plain="true" onclick="pass(1)">添加</a>
          </shiro:hasPermission>
          <shiro:hasPermission name="sys:settingParams:togetherFuture:update">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(2)">修改</a>
          </shiro:hasPermission>
          <shiro:hasPermission name="sys:settingParams:togetherFuture:delete">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteChannel()">删除</a>
          </shiro:hasPermission>
        </div>
      </div>
      <table id="edatagrid" class="easyui-datagrid"  pagination="true"
             toolbar="#audittb" url="${ctx}/admin/togetherFuture/easyuiPage"
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
          <th field="typeName" width="150">期货种类</th>
          <th field="tradingContract" width="150" sortable="true">操盘合约</th>
          <th field="unitPrice" width="150">合买价格</th>
          <th field="fullNum" width="150">满单份数</th>
          <th field="stopProfitPoint" width="150">止盈点数</th>
          <th field="stopLossPoint" width="150" >止损点数</th>
          <th field="profitFee" width="150">盈利手续费</th>
          <th field="profitPointPrice" width="150">盈利点数价格</th>
          <th field="lossFee" width="150">亏损手续费</th>
          <th field="lossPointPrice" width="150">亏损点数价格</th>
          <th field="beatPoint" width="150">跳动点数</th>
          <th field="updateTime" width="150" formatter="DateToS">更新时间</th>
          <th field="updateUser" width="150">操作人</th>

        </tr>
        </thead>
      </table>
    </div>
  </div>

  <div id="passWin" class="easyui-window" title="编辑"
       style="width:900px;height:300px;display:none;border:none; overflow: hidden;"
       data-options="iconCls:'icon-save',modal:true,closed:true">
    <form id="passForm">
      <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
        <tr>

          <td class="label right">期货种类:</td>
          <td  colspan="7">
            <input class="easyui-combobox" id="scope"  name="scope" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=FtogetherTradeType',
										valueField:'valueField',
										panelHeight:100,
										required:true,
										textField:'textField'">
          </td>
        </tr>
        <tr>
          <td class="label right">操盘合约:</td>
          <td>
            <input id="tradingContract" name="tradingContract" class="easyui-validatebox" data-options="required:true" />
          </td>

          <td class="label right">合买价格:</td>
          <td>
            <input id="unitPrice" name="unitPrice" class="easyui-validatebox"  data-options="required:true"/>
          </td>
          <td class="label right">满单份数:</td>
          <td>
            <input id="fullNum" name="fullNum" class="easyui-validatebox"  data-options="required:true"/>
          </td>
        </tr>
        <tr>
          <td class="label right">盈利结算:</td>
          <td colspan="5">
            合买价格&nbsp;-&nbsp;
            <input id="profitFee" name="profitFee" class="easyui-validatebox" data-options="required:true" />
            元&nbsp;+&nbsp;
            盈利点数&nbsp;X&nbsp;
            <input id="profitPointPrice" name="profitPointPrice" class="easyui-validatebox" data-options="required:true" />
            元/份
          </td>
        </tr>
        <tr>
          <td class="label right">亏损结算:</td>
          <td colspan="5">
            合买价格&nbsp;-&nbsp;
            <input id="lossFee" name="lossFee" class="easyui-validatebox" data-options="required:true" />
            元&nbsp;-&nbsp;
            亏损点数&nbsp;X&nbsp;
            <input id="lossPointPrice" name="lossPointPrice" class="easyui-validatebox" data-options="required:true" />
            元/份
          </td>
        </tr>
        <tr>
          <td class="label right">止盈点数:</td>
          <td>
            <input id="stopProfitPoint" name="stopProfitPoint" class="easyui-validatebox" data-options="required:true" />
          </td>

          <td class="label right">止亏点数:</td>
          <td>
            <input id="stopLossPoint" name="stopLossPoint" class="easyui-validatebox"  data-options="required:true"/>
          </td>
          <td class="label right">每次跳动:</td>
          <td>
            <input id="beatPoint" name="beatPoint" class="easyui-validatebox"  data-options="required:true"/>
          </td>
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
</shiro:hasPermission>
<shiro:lacksPermission name="sys:settingParams:togetherFuture:view">
  <%@ include file="../../common/noPermission.jsp"%>
</shiro:lacksPermission>
</body>
</html>