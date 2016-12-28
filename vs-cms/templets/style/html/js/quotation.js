function masendMessage(method,parameters){
    marketSocket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
}
var cc = null;
//加载K线图数据模型
function loadKData(currenCommodityNo,currenContractNo,currenExchangeNo){
    masendMessage('QryHistory','{"ExchangeNo":"'+currenExchangeNo+'","CommodityNo":"'+currenCommodityNo+'","ContractNo":"'+currenContractNo+'","HisQuoteType":0}');
    cc = setInterval(function(){
        masendMessage('QryHistory','{"ExchangeNo":"'+currenExchangeNo+'","CommodityNo":"'+currenCommodityNo+'","ContractNo":"'+currenContractNo+'","HisQuoteType":0}');
    },60000);
}
function chechData(commoditys){
    var dataC=[];
    var volume=[];
    var dataall=commoditys.Data;
    for (var i=0; i < dataall.length; i++) {
        var timestamp = Math.round(new Date(dataall[i][0]).getTime()/1000);
        var exs = 3600*8;
        var zhi = parseInt((parseInt(timestamp)+ parseInt(exs)));
        timestamp = zhi+"000";
        timestamp = parseInt(timestamp);
        dataC.push([
            timestamp,
            parseFloat(dataall[i][2].toFixed(4)),
            parseFloat(dataall[i][4].toFixed(4)),
            parseFloat(dataall[i][3].toFixed(4)),
            parseFloat(dataall[i][1].toFixed(4))
        ]);
        volume.push([
            timestamp,
            dataall[i][7]
        ]);
    }
    for(var i=0;i<volume.length-1;i++){
        if(volume[i][0]==volume[i+1][0]){
            volume.splice(i,1);
        }
    }
    for(var i=0;i<dataC.length-1;i++){
        if(dataC[i][0]==dataC[i+1][0]){
            dataC.splice(i,1);
        }
    }
    $('#quotation').highcharts('StockChart', {
        rangeSelector : {
            selected : 1
        },
        /*title : {
         text : 'AAPL Stock Price'
         },*/
        navigator : {
            enabled : false
        },
        series : [{
            type : 'area',
            name : '价格',
            data : dataC,
            threshold : null,
            animation: false,
            tooltip: {
                valueDecimals: 2
            }
        }]
    });
    $(".highcharts-scrollbar").hide();
}
$(function() {
    var url = "ws://quote.vs.com:9002";
    var username="13677622344";
    var password="a123456";
    var firstTimeLength = 0;
    var cc = null;
    marketSocket = new WebSocket(url);
    marketSocket.onopen = function(evt){
        masendMessage('Login','{"UserName":"'+username+'","PassWord":"'+password+'"}');
    };
    marketSocket.onclose = function(evt){
    };
    marketSocket.onerror = function(evt){
    };
    marketSocket.onmessage = function(evt){
        var data = evt.data;
        var jsonData = JSON.parse(data);
        var method = jsonData.Method;
        if(method=="OnRspLogin"){
            masendMessage('QryCommodity',null);
        }else if(method=="OnRspQryCommodity"){
            var param = jsonData.Parameters;
            loadCommodityList(param);
        }else if(method == "OnRspQryHistory"){
            var commoditys=jsonData.Parameters;
            chechData(commoditys);
        }else if(method == "OnRtnQuote"){
            var param = jsonData.Parameters;
            updateCommodityListData(param);
            updateQuoteCommodity(param);
        }
    };
});
var localCacheCommodity = {};
var selectCommodityList = null;
function loadCommodityList(param){
    var length = param.length;
    for(var i = 0 ; i < length ; i++){
        var data = param[i];
        var commodityName = data.CommodityName;
        var commodityNo = data.CommodityNo;
        var mainContract = data.MainContract;
        var exchangeNo = data.ExchangeNo;
        var cls = commodityNo+mainContract;
        var a ='';
        if(i == 0){
            a = "on";
            selectCommodityList = cls;
        }
        var html = '<li class = "left_xiangmu '+cls+' left_x'+i+' '+a+'" data-tion-contractCode = "'+cls+'" data="'+commodityNo+'&'+mainContract+'&'+exchangeNo+'">'+
                '<span class="name"><label>'+commodityName+'</label><b class = "fjt">↓</b></span>'+
                ' <span class="qlast">0</span>'+
                '<span class="qchange">0</span>'+
                '<span class="productBottom">0%</span>'+
                '</li>';
        localCacheCommodity[commodityNo+mainContract] = data;
        $("#commodityListData").append(html);
        masendMessage('Subscribe','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+mainContract+'"}');
        addCommodityBindClick(cls);
        if(i == 0){
            loadK(cls);
        }
    }
   /*左移动*/
    var dataLi_index = 0;
    var all_index = $("#commodityListData li").length;
    $("#prev span").click(function(){
        if(dataLi_index>0){
            dataLi_index--;
            $("#commodityListData").css("left","-"+(dataLi_index)*101+"px");
        }
    });
    /*右移动*/
    $("#next span").click(function(){
        if(dataLi_index < 9){
            dataLi_index++;
            $("#commodityListData").css("left","-"+(dataLi_index)*101+"px");
        }
    });
}
function addCommodityBindClick(cls){
    $("."+cls).bind("click",function(){
        clearInterval(cc);
        var $this = $(this);
        selectCommodityList = $this.attr("data-tion-contractCode");
        loadK(cls);
        $("#commodityListData .left_xiangmu").removeClass("on").eq($this.index()).addClass("on");
    });
}
function loadK(cls){
    var name =  $("."+cls+" span[class = 'name'] label").text();
    var commodityName =  $("."+cls+" b[class = 'fjt']").html();
    var qlast =  $("."+cls+" span[class = 'qlast']").text();
    var qchange =  $("."+cls+" span[class = 'qchange']").text();
    var productBottom =  $("."+cls+" span[class = 'productBottom']").text();
    var fjt =  $("."+cls+" span[class = 'fjt']").text();
    var color = $("."+cls+" span[class = 'qlast']").attr("data-color");
    $(".q_dj").html(commodityName);
    $(".q_dj img").css("width","12px");
    $(".q_productBottom").text(qlast).css("color",color);
    $(".q_qchange").text(qchange).css("color",color);
    $(".q_bfb").text(productBottom).css("color",color);
    $(".q_name").text(name);
    $(".quotation_title_left label").css("color",color);
    var data = $("."+cls).attr("data");
    var _data = data.split("&");
    loadKData(_data[0],_data[1],_data[2]);
}
/**
 * 设置行情品种显示颜色
 */
function setCommdityTopListColor(contractCode){
    var qlist = $("."+contractCode+" span[class = 'qlast']");
    var qchange = $("."+contractCode+" span[class = 'qchange']");
    var productBottom = $("."+contractCode + " span[class = 'productBottom']");
    var fjtText = $("."+contractCode + " b[class = 'fjt']");
    var changeRate = productBottom.attr("scal");
    var jj = "+";
    var fjt = "<img src='/templets/style/html/images/image/s_red_o.png'>";
    var color = " #ff4040";
    if(changeRate < 0){
        jj = "-";
        fjt = "<img src='/templets/style/html/images/image/j_green_o.png'>";
        color="#30bf30";
    }else if(changeRate == 0){
        jj = "";
        fjt = "";
        color="#999";
    }
    qlist.attr("data-color",color);
    qlist.css("color","#f2f2f2");
    qchange.css("color",color);
    productBottom.css("color",color);
    fjtText.css("color",color);
    fjtText.html(fjt);
    $(".q_dj img").css("width","12px");
}
function updateCommodityListData(param){
    var commodityNo = param.CommodityNo;
    var contractNo = param.ContractNo;
    var contractCode = commodityNo + contractNo;
    var localCommodity = localCacheCommodity[contractCode];
    if(localCommodity == undefined){
        return;
    }
    var dotSize = localCommodity.DotSize;
    var changeRate = param.ChangeRate;
    var lastPrice = param.LastPrice;
    var qChangeValue = parseFloat(param.ChangeValue).toFixed(dotSize);
    var scal = changeRate.toFixed(2);
    var qlist = $("."+contractCode+" span[class = 'qlast']");
    var qchange = $("."+contractCode+" span[class = 'qchange']");
    var productBottom = $("."+contractCode + " span[class = 'productBottom']");
    var fjtText = $("."+contractCode + " b[class = 'fjt']");
    qlist.text(lastPrice);
    qchange.text(qChangeValue);
    productBottom.text(scal+"%");
    productBottom.attr("scal",changeRate);
    setCommdityTopListColor(contractCode);
}

function updateQuoteCommodity(param){
    var commodityNo = param.CommodityNo;
    var contractNo = param.ContractNo;
    var contractCode = commodityNo + contractNo;
    var localCommodity = localCacheCommodity[contractCode];
    if(contractCode != selectCommodityList){
        return;
    }
    var dotSize = localCommodity.DotSize;
    var changeRate = param.ChangeRate;
    var lastPrice = param.LastPrice;
    var qChangeValue = parseFloat(param.ChangeValue).toFixed(dotSize);
    //昨收
    var preClosingPrice = parseFloat(param.PreClosingPrice).toFixed(dotSize);
    //今开
    var openPrice = parseFloat(param.OpenPrice).toFixed(dotSize);
    var scal = changeRate.toFixed(2);
    //最低价
    var qLowPrice = parseFloat(param.LowPrice).toFixed(dotSize);
    //最高价
    var qHighPrice = parseFloat(param.HighPrice).toFixed(dotSize);
    var jj = "+";
    var fjt = "<img src='/templets/style/html/images/image/s_red_o.png'>";
    var color = " #ff4040";
    if(changeRate < 0){
        jj = "-";
        fjt = "<img src='/templets/style/html/images/image/j_green_o.png'>";
        color="#30bf30";
    }else if(changeRate == 0){
        jj = "";
        fjt = "";
        color="#333";
    }
    $(".quotation_title_left label").css("color",color);
    $(".zs").text(preClosingPrice);
    $(".jk").text(openPrice);
    $(".fd").text(qLowPrice+" - "+qHighPrice);
    $(".quotation_all .time").text(param.DateTimeStamp);
    $(".q_dj").html(fjt);
    $(".q_dj img").css("width","12px");
    $(".q_productBottom").text(lastPrice).css("color",color);
    $(".q_qchange").text(qChangeValue).css("color",color);
    $(".q_bfb").text(scal+"%").css("color",color);
}