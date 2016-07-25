<html>
<head>
<style type="text/css">
table.conn {
   margin: 4px;
	border: none;
	border-bottom: 1px solid #d0d0d0;
	border-left: 1px solid #d0d0d0;
	font-size:12px;
	
}
table.conn th{
   background-color: #eeeeee;
   height: 30px;
}
table.conn td{
	border: 1px solid #d0d0d0;
	border-bottom: none;
	border-left: none;
	height: 30px;
	padding:2px;
	background-color: white;
}

table.conn td.label {
   background-color: #eeeeee;

}
table.conn td.right {
    text-align: right;
}
table.conn td.left {
    text-align: left;
}
table.conn td.center {
    text-align: center;
}
</style>
</head>
<body>
<table border="0" style="font-size:12px;width:300px;" class="conn" cellpadding="0" cellspacing="0">
            <tr>
                <th colspan="2">维胜系统手工调账[凭证]</th>
            </tr>
            <tr>
                <td class="label right">操作员：</td>
                <td>%s</td>
            </tr>
            <tr>
                <td class="label right">时间：</td>
                <td>%s</td>
            </tr>
            <tr>
                <td class="label right">调账类型：</td>
                <td>%s</td>
            </tr>
            <tr>
                <td class="label right">账户：</td>
                <td>%s</td>
            </tr>
            <tr>
                <td class="label right">调账前余额：</td>
                <td>%s</td>
            </tr>
            <tr>
                <td class="label right">金额：</td>
                <td>%s</td>
            </tr>
            <tr>
                <td class="label right">调账后余额：</td>
                <td>%s</td>
            </tr>
            <tr>
                <td class="label right">原因：</td>
                <td>%s</td>
            </tr>
</table>
</body>  
</html>