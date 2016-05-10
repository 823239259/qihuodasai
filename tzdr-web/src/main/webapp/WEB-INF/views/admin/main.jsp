<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="Content-Language" content="zh-CN"/> 
		<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<title>登录</title>
		<%@include file="../common/import-easyui-js.jspf"%>
		<%@include file="../common/import-fileupload-js.jspf"%>
		<%@include file="../common/import-kindeditor-js.jspf"%>
	</head>
	<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">north region</div>
	<div data-options="region:'west',split:true,title:'操作列表'" style="width:150px;">
		<div id="aa" class="easyui-accordion" data-options="fit:true,border:false">
		    <div title="Title1" iconCls="icon-save">
			    <h3 style="color:#0099FF;">Accordion for jQuery</h3>
			    <p>Accordion is a part of easyui framework for jQuery.
			    It lets you define your accordion component on web page more easily.</p>
		    </div>
		    <div title="Title2" iconCls="icon-reload" selected="true">
		  		 <input type="file" name="files[]" id="fileupload_input" />
		    </div>
		    <div title="Title3">
		  		  content3
		    </div>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	<div id="main" data-options="region:'center',title:''">
		<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true">
		<div title="Tab1" data-options="tools:'#p-tools'" style="padding:20px;">
		</div>
		<div title="编辑器tab2" data-options="closable:true,cache:false" style="padding:20px;">
			<textarea  id="birthdayMsg" name="birthdayMsg"></textarea>
		</div>
		<div title="Tab3" data-options="iconCls:'icon-reload',closable:true" style="padding:20px;">
			    	    <div id="tb" style="padding:5px;height:auto">
						    <div style="margin-bottom:5px">
						    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openwin()"></a>
						    </div>
						    <div>
						  	<span>Name:</span>
							<input id="name" style="line-height:26px;border:1px solid #ccc">
						    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">Search</a>
						    </div>
					    </div>
			    	<table id="edatagrid" class="easyui-datagrid"
					    url="${ctx}/login/easyuiPage"
					    title="Load Data" iconCls="icon-save" toolbar="#tb"
					    rownumbers="true" pagination="true">
					    <thead>
					    <tr>
					    <th field="name" width="100%">name</th>
					    </tr>
					    </thead>
					</table>
		</div>
		<div title="Tab4 with iframe" data-options="closable:true"  style="overflow:hidden">
				<iframe scrolling="yes" frameborder="0"  src="http://www.jeasyui.com/forum/index.php" style="width:100%;height:100%;"></iframe>
		</div>
		<div title="Tab5 with sub tabs" data-options="closable:true,iconCls:'icon-cut'" style="padding:10px;">
			<div class="easyui-tabs" data-options="fit:true,plain:true">
				<div title="Title1" style="padding:10px;">Content 1</div>
				<div title="Title2" style="padding:10px;">Content 2</div>
				<div title="Title3" style="padding:10px;">Content 3</div>
			</div>
		</div>
	</div>
	</div>
	
	<!-- window -->
	<div id="w" class="easyui-window" data-options="title:'My Window',iconCls:'icon-save',closed:true,modal:true" style="width:500px;height:200px;padding:10px;">
		<form method="post" id="loginForm">
					<table>
						<tr>
							<td>Name:</td>
							<td><input class="easyui-validatebox" id="name"  name="name" data-options="required:true,missingMessage:'不能为空',validType:['length[2,5]','CHS'],invalidMessage:'请输入8-23个字符，并且全是中文'"></td>
						</tr>
						<tr>
								<td colspan="2"><a href="javascript:void(0)" id="login" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a></td>
						</tr>
					</table>
				</form>
	</div>
	<%@ include file="../common/dsp.jsp"%>
</body>
<script type="text/javascript">

KindEditor.ready(function(K) {
	var options = {
			themeType: 'simple',
            uploadJson: basepath+'kindeditor/upload',
            allowFileManager: false,
            width : '700px',
            items : [
						'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
			      afterBlur: function(){this.sync();}
    };
    window.editor = K.create('#birthdayMsg',options);
});


$("#fileupload_input").fileupload({
    url:"${ctx}/fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    formData:{dir:"testUplaod"},//如果需要额外添加参数可以在这里添加
    done:function(e,result){
    	result = result.result;
    	var url = result.url;
    	var deleteURL = result.deleteUrl;
    	//alert(url);
    	alert(deleteURL);
    }
})


   function doSearch(){
    $('#edatagrid').datagrid('load',{
  	  search_EQ_name: $('#itemid').val()
    });
    }
    
    
function openwin(){
			$('#w').window('open');
		}
		
		
$("#login").click(function(){
	if ($("#loginForm").form('validate')==false){
		return;
	}
	ajaxPost({
		url: basepath+"login/create",
		cache: false,
		async:false,
		data: {"name": $("#name").val()},
		success: function(data){
			alert(data.message);
			$('#w').window('close');
			$("#edatagrid").datagrid('reload');  
		}
		,error: function(){
			alert("error");
		}
	}); 
	
	
});

</script>
</html>