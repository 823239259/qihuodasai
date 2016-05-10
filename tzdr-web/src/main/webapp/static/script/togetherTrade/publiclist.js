var countOfCurrentPage = 10;
var currentPage = 0;

$(document).ready(function() {
	//顶部菜单位置
	$('.navlist li a').removeClass('on');
	$("#togetherli").addClass("on");
	var list = function(countOfCurrentPage,currentPage){
		var $content = $('#content');
		$content.empty();
		$.post(basepath + "together/list.json", {countOfCurrentPage:countOfCurrentPage,currentPage:currentPage}, function(result) {
			if (result.success) {
				$content.empty();
				var data=result.obj.pageResults;
				var total = result.obj.totalCount;
				var table='<div class="bl_l_one bl_l_one{0}">'; // 
				table+='<div class="bl_l_info">';
				table+='<h4><i>少分利</i><a href="'+basepath+'together/detail/{7}" class="bl_o_{8}">总操盘资金：{1}元</a></h4>';
				table+='<ul>';
				table+='<li class="li1"><h5>合买者出资</h5><p>{2}元</p></li>'; //
				table+='<li class="li2"><h5>操盘周期</h5><p>{3}</p></li>';
				table+='<li class="li3"><h5>保底收益</h5><p>{4}</p></li>';
				table+='<li class="li4"><h5>合买者分利</h5><p>{5}</p></li>';
				table+='</ul>';
				table+='</div>';
				table+='<div class="bl_l_ope">';
				table+='<p>{6}</p>';
				table+='<a href="'+basepath+'together/detail/{7}" class="bl_o_{8}">{9}</a>';
				table+='</div>';
				
				$.each(data, function(index, element){
					var idx, tStatusStr, btnCss, tStatusTips;
					if(index%2==0) {
						idx = 1;
					} else {
						idx = 2;
					}
					switch(element.tStatus) {
					case 1:
						tStatusStr = "参与合买";
						btnCss = "join";
						tStatusTips = "合买中";
						break;
					case 2:
						tStatusStr = "操盘中";
						btnCss = "doing";
						var delayDays = element.delayDays - 1;
						tStatusTips = ((delayDays<=0)?("已操盘"+element.tradeDays):("已延期"+delayDays)) + "个交易日";
						break;
					case 3:
						tStatusStr = "已终结";
						btnCss = "done";
						
						if(element.returnRate >=0){
							tStatusTips = "收益率：<i class='cl_red'>+" + element.returnRate + "%</i>";
						}else{
							tStatusTips = "收益率：<i class='cl_gr'>" + element.returnRate + "%</i>";
						}
						
						break;
					default:
						tStatusStr = "合买失败";
						btnCss = "fail";
						tStatusTips = "";
					}
					var head=$.format(table, idx, element.totalLeverMoney, element.money, element.naturalDays, element.annualizeRate, element.profitRatio, tStatusTips, element.gid, btnCss, tStatusStr);
					
					$content.append(head).show();
				});
				
				//分页
				var $Pagination = $("#Pagination");
				if($Pagination.html()=="" || $Pagination.length<0){
					$("#Pagination").pagination(total, {
						'items_per_page'      : countOfCurrentPage,
						'num_edge_entries'    : 2,
						'prev_text'           : "上一页",
						'next_text'           : "下一页",
						'callback'            : pageselectCallback
					});
				}
			} else {
				alert(result.message);
			}
		}, "json");
	}
	
	// 加载数据
	function getDataList(index){
		list(countOfCurrentPage, index+1);
	}

	//分页回调
	function pageselectCallback(page_index, jq){
		getDataList(page_index);
	}
	
	getDataList(0);

});