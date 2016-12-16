$(function(){
    /*全选和不选*/
    var dataAll;
    $("#economicCalendar .country .checkboxALL .input_check").click(function(){
        var _this = $(this);
        var  checkboxALL = _this.prev();
        if(checkboxALL.prop("checked")){
            checkboxALL.prop("checked",false);
            $("#economicCalendar .country .checkbox input:checkbox").prop("checked",false);

        }else{
            checkboxALL.prop("checked",true);
            $("#economicCalendar .country .checkbox input:checkbox").prop("checked",true);
        }
    });
    $("#economicCalendar .country .checkboxALL input").click(function(){
        var _this = $(this);
        if(_this.prop("checked")){
            $("#economicCalendar .country .checkbox input:checkbox").prop("checked",true);
            /*
             _this.prop("checked",false);*/
            /**/
        }else{
            $("#economicCalendar .country .checkbox input:checkbox").prop("checked",false);
            /*_this.prop("checked",true);
             */
        }
    });
    $("#economicCalendar .country input").click(function () {
        $(".calendar_time_tab").eq(0).empty();
        $(".calendar_time_tab").eq(1).empty();
        $("#economicCalendar_trailer_Content").empty();
    });
    function updateData(val){
        $(".calendar_time_tab").eq(0).empty();
        $(".calendar_time_tab").eq(1).empty();
        $("#economicCalendar_trailer_Content").empty();
        for(var i=0;i<dataAll.length;i++){
            //for(var s=0;s<val.length;s++){
                if(val.indexOf(dataAll[i].country)>=0){
                    var date= new Date(parseInt(dataAll[i].timestamp) * 1000).Format("hh:mm");
                    var star;
                    if(dataAll[i].importance==1){
                        star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/grayStart.png'><img src='/templets/style/html/images/image/grayStart.png'>"
                    }else if(dataAll[i].importance==2){
                        star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/grayStart.png'>"
                    }else if(dataAll[i].importance==3){
                        star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'>"
                    }else{
                        star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'>"
                    }
                    if(dataAll[i].actual==null || dataAll[i].actual=="null" || dataAll[i].actual==""){
                        dataAll[i].actual="--";
                    }
                    if(dataAll[i].forecast==null || dataAll[i].forecast=="null" || dataAll[i].forecast==""){
                        dataAll[i].forecast="--";
                    }
                    if(dataAll[i].previous==null || dataAll[i].previous=="null" || dataAll[i].previous==""){
                        dataAll[i].previous="--";
                    }
                    if(dataAll[i].calendarType=="FE"){
                        $(" .economicCalendar_preview .calendar_time_tab").append("<ul>"+
                            "<li class='data'>"+date+"</li>"+
                            "<li class='importance'>"+star+"</li>"+
                            " <li class='countries'>"+dataAll[i].country+"</li>"+
                            "  <li class='detailed'>"+dataAll[i].title+"</li>"+
                            " </ul>");
                    }else if(dataAll[i].calendarType=="FD"){
                        $(" .economicCalendar_content .calendar_time_tab").append("<ul>"+
                            "<li class='data'>"+date+"</li>"+
                            "<li class='importance'>"+star+"</li>"+
                            " <li class='countries'>"+dataAll[i].country+"</li>"+
                            "  <li class='incident'>"+dataAll[i].title+"</li>"+
                            " <li class='newValue'>"+dataAll[i].actual+"</li>"+
                            "<li class='expect'>"+dataAll[i].forecast+"</li>"+
                            "<li class='formerValue'>"+dataAll[i].previous+"</li>"+
                            " </ul>");
                    }else if(dataAll[i].calendarType=="VN"){
                        $(" .economicCalendar_trailer .calendar_time_tab #economicCalendar_trailer_Content").append("<ul>"+
                            "<li class='data'>"+date+"</li>"+
                            "<li class='importance'>"+star+"</li>"+
                            " <li class='countries'>"+dataAll[i].country+"</li>"+
                            "  <li class='detailed'>"+dataAll[i].title+"</li>"+
                            " </ul>");
                    }
                }
            //}
        }
    }
    /*复选*/
    var input_check_length =0;
    $("#economicCalendar .country .checkbox .input_check").click(function(){
        var _this = $(this);
        var  checkboxALL = _this.prev();
        if(checkboxALL.prop("checked")){
            checkboxALL.prop("checked",false);
        }else{
            checkboxALL.prop("checked",true);
        }
        var input =$("#economicCalendar .country .checkbox input");
        /*for(var i= 0; i<input.length;i++){
         if(input[i].prop("checked")){
         }
         }*/
    });
    $("#today").html(GetDateStr(0));
    function GetDateStr(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
        var y = dd.getFullYear();
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate();
        var i ="",j="";
        if(m<10){
            i="0";
        }
        if(d<10){
            j="0";
        }
        return y+"年"+i+m+"月"+j+d+"日";
    }
   function updateDate(date){
       var date=new Date(date);
       var y = date.getFullYear();
       var m = date.getMonth()+1;//获取当前月份的日期
       var d = date.getDate();
       return y+"-"+m+"-"+d;
    }
    function GetDateStrDate(date) {
        var dd = new Date(date);
        dd.setDate(dd.getDate()+1);//获取AddDayCount天后的日期
        var y = dd.getFullYear();
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate();
        return y+"-"+m+"-"+d;
    }
    var AddDayCount =0;
    var queryCount=0;
    var url="crawler/getCrawlerCalendarByTime";
    $("#yesterday").click(function(){
        AddDayCount--;
        $("#today").html(GetDateStr(AddDayCount));
        var text=$("#today").text();
        var startDate=text.replace(/[\u4e00-\u9fa5]/g,"-").slice(0,text.length-1);
        var endDate=GetDateStrDate(startDate,1);
        var params={
            pageIndex:0,
            startTime:startDate,
            endTime:endDate
        };
        queryData(url,params,insertData);
    });
    $("#tomorrow").click(function(){
        AddDayCount++;
        $("#today").html(GetDateStr(AddDayCount));
        var text=$("#today").text();
        var startDate=text.replace(/[\u4e00-\u9fa5]/g,"-").slice(0,text.length-1);
        var endDate=GetDateStrDate(startDate,1);
        var params={
            pageIndex:0,
            startTime:startDate,
            endTime:endDate
        };
        queryData(url,params,insertData);
    });
    time();
    function time(){
        var startStop = new Array();//起止日期数组
        var currentDate = new Date();//获取当前时间
        var week = currentDate.getDay(); //返回date是一周中的某一天
        var millisecond = 1000 * 60 * 60 * 24;//一天的毫秒数
        var minusDay = week != 0 ? week - 1 : 6; //减去的天数
        var monday = new Date(currentDate.getTime() - (minusDay * millisecond));//本周一
        var tuesday = new Date(monday.getTime() +millisecond);
        var wednesday = new Date(monday.getTime() + (2 * millisecond));
        var thursday = new Date(monday.getTime() + (3 * millisecond));
        var friday = new Date(monday.getTime() + (4 * millisecond));
        var saturday = new Date(monday.getTime() + (5 * millisecond));
        var sunday = new Date(monday.getTime() + (6 * millisecond));//本周日
        var i ="",j="";
        if(monday.getMonth()<9){
            i="0";
        }
        if(monday.getDate()<10){
            j="0";
        }
        $(".monday").html(i+(monday.getMonth() + 1) + '/'+ j + monday.getDate());
        $(".tuesday").html(i+(tuesday.getMonth() + 1) + '/'+ j + tuesday.getDate());
        $(".wednesday").html(i+(wednesday.getMonth() + 1) + '/'+ j + wednesday.getDate());
        $(".thursday").html(i+(thursday.getMonth() + 1) + '/'+ j + thursday.getDate());
        $(".friday").html(i+(friday.getMonth() + 1) + '/'+ j + friday.getDate());
        $(".saturday").html((saturday.getMonth() + 1) + '/'+ j + saturday.getDate());
        $(".sunday").html(i+(sunday.getMonth() + 1) + '/'+ j + sunday.getDate());
        if(week==1){
            $(".monday").parent().addClass("on");
        }else if(week==2){
            $(".tuesday").parent().addClass("on");
        }else if(week==3){
            $(".wednesday").parent().addClass("on");
        }else if(week==4){
            $(".thursday").parent().addClass("on");
        }else if(week==5){
            $(".friday").parent().addClass("on");
        }else if(week==6){
            $(".saturday").parent().addClass("on");
        }else if(week==0){
            $(".sunday").parent().addClass("on");
        }
    }
    $(".economicCalendarQuotation .quotation_title").click(function(){
        var _this = $(this);
        $(".economicCalendarQuotation .quotation_title").removeClass("on").eq(_this.index()-1).addClass("on");
    });
    insertDataF();
    $(".time li").click(function () {
        var index=$(this).index();
        $(".time li a").removeClass("on").eq(index).addClass("on");
        var text="2016年"+$(".time li span").eq(index).text();
        var startDate=text.replace(/[\u4e00-\u9fa5]/g,"-").slice(0,text.length-1);
        var endDate=GetDateStrDate(startDate,1);
        var params={
            pageIndex:0,
            startTime:startDate,
            endTime:endDate
        };
        queryData(url,params,insertData);
    });
    function insertData(data){
        $(".calendar_time_tab").eq(0).empty();
        $(".calendar_time_tab").eq(1).empty();
        $("#economicCalendar_trailer_Content").empty();
        dataAll=data;
        for(var i=0;i<data.length;i++){
            var date= new Date(parseInt(data[i].timestamp) * 1000).Format("hh:mm");
            var star;
            var id;
            if(data[i].importance==1){
                star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/grayStart.png'><img src='/templets/style/html/images/image/grayStart.png'>"
            }else if(data[i].importance==2){
                star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/grayStart.png'>"
            }else if(data[i].importance==3){
                star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'>"
            }else{
                star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'>"
            }
            if(data[i].actual==null || data[i].actual=="null" || data[i].actual==""){
                data[i].actual="--";
            }
            if(data[i].forecast==null || data[i].forecast=="null" || data[i].forecast==""){
                data[i].forecast="--";
            }
            if(data[i].previous==null || data[i].previous=="null" || data[i].previous==""){
                data[i].previous="--";
            }
            if(data[i].calendarType=="FE"){
                $(" .economicCalendar_preview .calendar_time_tab").append("<ul>"+
                    "<li class='data'>"+date+"</li>"+
                    "<li class='importance'>"+star+"</li>"+
                    " <li class='countries'>"+data[i].country+"</li>"+
                    "  <li class='detailed'>"+data[i].title+"</li>"+
                    " </ul>");
            }else if(data[i].calendarType=="FD"){
                $(" .economicCalendar_content .calendar_time_tab").append("<ul>"+
                    "<li class='data'>"+date+"</li>"+
                    "<li class='importance'>"+star+"</li>"+
                    " <li class='countries'>"+data[i].country+"</li>"+
                    "  <li class='incident'>"+data[i].title+"</li>"+
                    " <li class='newValue'>"+data[i].actual+"</li>"+
                    "<li class='expect'>"+data[i].forecast+"</li>"+
                    "<li class='formerValue'>"+data[i].previous+"</li>"+
                    " </ul>");
            }else if(data[i].calendarType=="VN"){
                $(" .economicCalendar_trailer .calendar_time_tab #economicCalendar_trailer_Content").append("<ul>"+
                    "<li class='data'>"+date+"</li>"+
                    "<li class='importance'>"+star+"</li>"+
                    " <li class='countries'>"+data[i].country+"</li>"+
                    "  <li class='detailed'>"+data[i].title+"</li>"+
                    " </ul>");
            }

        }
    }
    function insertDataF(){
        var text=$("#today").text();
        var startDate=text.replace(/[\u4e00-\u9fa5]/g,"-").slice(0,text.length-1);
        var endDate=GetDateStrDate(startDate,1);
        var params={
            pageIndex:0,
            startTime:startDate,
            endTime:endDate
        };
        queryData(url,params,insertData);
    }
    var valList="";
    $(".country span").click(function () {
        valList="";
        var obj=document.getElementsByName('country'); //选择所有name="'test'"的对象，返回数组
        //取到对象数组后，我们来循环检测它是不是被选中
        var s='';
        for(var i=0; i<obj.length; i++){
            if(obj[i].checked){
                s+=obj[i].value+','; //如果选中，将value添加到变量s中
                valList+=$(".input_check").eq(i+1).text()+",";
            }
        }
        if(valList.length>0){
            if(valList.indexOf("全部类别")>=0){
                insertData(dataAll);
            }else{
                updateData(valList)
            }
        }else{
            insertData(dataAll);
        }
    })
});
