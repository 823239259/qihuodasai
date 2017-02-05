<html>
    <head></head>
	<body>
	     My name is ${hello}
		 <table width="100" border="1" >
		 <#list students as m>
		    <tr>
			     <td>${m.name}</td>
				 <td>${m.sex}</td>
				 <td>${m.age}</td>
			</tr>
		 </#list>
	     </table>
	</body>
</html>