<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Content-Language" content="zh-CN" />
  <!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
  <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
  <title>渠道列表</title>
  <%@include file="../../common/import-easyui-js.jspf"%>
  <script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
  <script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
  <script type="text/javascript" src="${ctx}/static/script/channelPromotion/list.js"></script>
  <script type="text/javascript">
  </script>
  <style type="text/css">
    .frontImg {
      width: 200px;
    }
  </style>
  <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<shiro:hasPermission name="sys:settingParams:channelPromotion:view">
   <!-- data-options="queryParams:Check.loadFormData($('#queryForm'))" --> 
  <div title="渠道列表" id="proxy" style="padding:1px;height:auto;">
    <table id="dg003" class="easyui-datagrid"
           toolbar="#dg003Toolbar" url="${ctx}/admin/channelPromotion/getData"  pagination="true"
           rownumbers="true" fitColumns="true" singleSelect="true">
      <thead>
      <tr>
        <th field="id" data-options="checkbox:true"></th>
        <th field="typeOneTitle" width="150">一级渠道 </th>
        <th field="typeTwoTitle" width="150">二级渠道</th>
        <th field="typeThreeTitle" width="150">三级渠道</th>
        <th field="urlKey" width="150">关键词/分类</th>
        <th field="param" width="150">代码</th>
      </tr>
      </thead>
    </table>
    
    <div id="dg003Toolbar" style="padding: 5px">
      <form id="queryForm" method="post">
        <table border="0" style="font-size:12px;"  width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td width="5%" align="center">一级渠道:</td>
            <td width="15%"><select name="search_EQ_typeOneTitle" id="channerl1" onchange="getChannelSel1()" style="width: 99%">
            </select>
            </td>
            <td width="5%" align="center">二级渠道:</td>
            <td width="15%"><select name="search_EQ_typeTwoTitle" id="channerl2" onchange="getChannelSel2()" style="width: 99%">
            </select>
            </td>
             <td width="5%" align="center">三级渠道:</td>
            <td width="15%"><select name="search_EQ_typeThreeTitle" id="channerl3" style="width: 99%">
            </select>
			</td>
            <td width="7%" align="center">关键词/分类:</td>
            <td width="15%">
              <input type="text" name="search_EQ_urlKey" id="uk"/>
            </td>
            <td width="3%" align="center">代码:</td>
            <td width="15%">
              <input type="text" name="search_EQ_param" id="pm" onkeyup="value=value.replace(/[\W]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\W]/g,''))"/>
            </td>
          </tr>
      
        </table>
      </form>
      
      <a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">查询</a>
      <!-- ResetButton -->
      <shiro:hasPermission name="sys:settingParams:channelPromotion:update">
      <a id="btn_1" href="#" onclick="reset()" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-undo'">重置</a>
      </shiro:hasPermission>
      <shiro:hasPermission name="sys:settingParams:channelPromotion:delete">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteChannel()">删除</a>
      </shiro:hasPermission>
        <shiro:hasPermission name="sys:settingParams:channelPromotion:create">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addChannelWindowOpen()">新增</a>
      </shiro:hasPermission>
    </div>
  </div>


  <!-- add_channel_win -->
  <div id="addChannel" class="easyui-window" title="新增渠道参数" style="width:450px;height:240px;display:none;border:none; overflow: hidden;"
       data-options="iconCls:'icon-add',modal:true,closed:true">
    <form id="voForm">
      <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td class="label right">一级渠道</td>
          <td><input name="typeOneTitle" type="text" id="type_One_Title" class="easyui-textbox" precision="0" data-options="required:true" /></td>
          <td>
            <select id="addSe1" onchange='giveValue(addSe1)' style="width: 99%"></select>
          </td>
        </tr>
        <tr>
          <td class="label right">二级渠道</td>
          <td><input name="typeTwoTitle" type="text" id="type_Two_Title" class="easyui-textbox" precision="0" data-options="required:true" /></td>
          <td>
            <select id="addSe2" onchange='giveValue(addSe2)' style="width: 99%"></select>
          </td>
        </tr>
        <tr>
          <td class="label right">三级渠道</td>
          <td><input name="typeThreeTitle" type="text" id="type_Three_Title" class="easyui-textbox" precision="0" data-options="required:true" /></td>
          <td>
            <select id="addSe3" onchange='giveValue(addSe3)' style="width: 99%"></select>
          </td>
        </tr>
        <tr>
          <td class="label right">关键词/分类</td>
          <td colspan="2"><input id="addUk" class="easyui-textbox" name="urlKey" type="text"  style="width: 99%"/></td>
        </tr>
        <tr>
          <td class="label right">代码</td>
          <td colspan="2"><input id="addPm" class="easyui-textbox" name="param" type="text" onkeyup="value=value.replace(/[\W]/g,'')"  style="width: 99%" /></td>
        </tr>
        <tr>
          <td align="center" colspan="3">
            <a id="btn" href="#" onclick="addChannelSave()" class="easyui-linkbutton">提交</a>
            <a id="btn" href="#" onclick="addChannelClose()" class="easyui-linkbutton">取消</a>
          </td>
        </tr>
      </table>
    </form>

  </div>


  
 
</shiro:hasPermission>
<shiro:lacksPermission name="sys:settingParams:channelPromotion:view">
  <%@ include file="../../common/noPermission.jsp"%>
</shiro:lacksPermission>
</body>
</html>
