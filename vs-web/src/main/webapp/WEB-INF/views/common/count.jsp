<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
    var visitrecord_p = null;                //获取推广
    var visitrecord_uid = null;              //获取渠道
    var visitrecord_url = location + "";          //得到当前url
    var visitrecord_from = null;
    var searchUrl = location.search;
    var params = searchUrl.substr(searchUrl.indexOf("?") + 1);
    if (params) {  //取参数信息
        var fromstr;
        var strs;
        if (params.indexOf("from=") != -1) {
        	 fromstr = params.substr(params.indexOf("from=") + 5, params.length);
             if (fromstr && fromstr.length > 0) {
                 visitrecord_from = decodeURIComponent(fromstr);
             }
        	strs = params.substr(0, params.indexOf("from=")).split("&");
        } else {
        	strs = params.split("&");
        }
        for (var i = 0; i < strs.length; i++) {
            if (strs[i].split("=")[1]) {
                if (strs[i].split("=")[0] == 'p') {
                    visitrecord_p = decodeURIComponent(strs[i].split("=")[1]);
                } else if (strs[i].split("=")[0] == 'uid') {
                    visitrecord_uid = decodeURIComponent(strs[i].split("=")[1]);
                }
            }
        }
    }
    if ((visitrecord_p != null && visitrecord_p != 'null' && visitrecord_p != '') || (visitrecord_uid != null && visitrecord_uid != 'null' && visitrecord_uid != '')) {
        $.post("${ctx}/visitrecord", {
            url: visitrecord_url,
            p: visitrecord_p,
            uid: visitrecord_uid,
            from: visitrecord_from,
            ajax: 1
        }, function (data) {

        }, "json");
    }
</script>