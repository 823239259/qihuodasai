/***********************
 * 函数：判断滚轮滚动方向
 * 参数：event
 * 返回：滚轮方向 1：向上 -1：向下
 *************************/
function scrollFunc(e,father){
    var funx,funy,fatherx,fathery;
    var father=$("#whichscro").val();
    e=e || window.event;
    funx=e.pageX;
    funy=e.pageY;
    fathery=$("#"+father).offset().top;
    fatherx=$("#"+father).offset().left;
    if(funx>=fatherx&&funx<=(fatherx+$("#"+father).width())&&funy>=fathery&&funy<=(fathery+$("#"+father).height())){
        var t1=document.getElementById("wheelDelta");
        var t2=document.getElementById("detail");
        if(e.wheelDelta){//IE/Opera/Chrome
			var thisvalue=parseInt(e.wheelDelta);
			if(thisvalue>=0){
				thisvalue=-parseInt(e.wheelDelta);
				scroll_y(father,"left_hidden","scroll_y","scroll_ymove","scroll_x","scroll_xmove","","wheely",thisvalue);
				if(!e.wheelDelta){
					t1.value=e.wheelDelta;
				}else{
					
				}
			}else{
				thisvalue=-parseInt(e.wheelDelta);
				scroll_y(father,"left_hidden","scroll_y","scroll_ymove","scroll_x","scroll_xmove","","wheely",thisvalue);
				if(!e.wheelDelta){
					t2.value=e.wheelDelta;
				}else{
					
				}
			}
		}else if(e.detail){//Firefox
			var thisvalue=parseInt(e.detail);
			if(thisvalue>=0){
				scroll_y(father,"left_hidden","scroll_y","scroll_ymove","scroll_x","scroll_xmove","","wheely",thisvalue)
				if(!e.detail){
					t1.value=e.detail;
				}else{
					
				}
			}else{
				scroll_y(father,"left_hidden","scroll_y","scroll_ymove","scroll_x","scroll_xmove","","wheely",thisvalue)
				if(!e.detail){
					t2.value=e.detail;
				}else{
					
				}
			}
		}
    }
}
/*注册事件web端*/
if(document.addEventListener){
    document.addEventListener('DOMMouseScroll',scrollFunc,false);
}//W3C
window.onmousewheel=document.onmousewheel=scrollFunc;//IE/Opera/Chrome/Safari


//移动端监听
function touchStart(event) {
    //阻止网页默认动作（即网页滚动）
    event.preventDefault();
    var touch = event.touches[0]; //获取第一个触点
    var x = Number(touch.pageX); //页面触点X坐标
    var y = Number(touch.pageY); //页面触点Y坐标
    //记录触点初始位置
    startX = x;
    startY = y;
    //alert(startX+"--")

}
function touchMove(event) {
    event.preventDefault();
    var father=$("#whichscro").val()
    var touch = event.touches[0]; //获取第一个触点
    var x = Number(touch.pageX); //页面触点X坐标
    var y = Number(touch.pageY); //页面触点Y坐标
    var thisval,ylength,xlength;
    ylength=y-startY;
    xlength=x-startX;
    if(Math.abs(ylength)>Math.abs(xlength)){
        if(ylength>=0){
            thisval=1
        }else{
            thisval=-1
        }
        scroll_y(father,"left_hidden","scroll_y","scroll_ymove","scroll_x","scroll_xmove","","wheely",thisval)
    }else{

        if(xlength>=0){
            thisval=1
        }else{
            thisval=-1
        }
        // alert(thisval)
        scroll_y(father,"left_hidden","scroll_y","scroll_ymove","scroll_x","scroll_xmove","","wheelx",thisval)
    }

}
function touchEnd(event) {
    event.preventDefault()
}

/*
 father        =  父元素样式表
 soncontent    =  子元素样式表
 scroll_y      =  y轴滚动条样式表
 scroll_ymove  =  y轴滚动条
 scroll_x      =  x轴滚动条样式表
 scroll_xmove  =  x轴滚动条
 deloradd      =  删除元素或者添加元素
 wheelxory     =  滚动x轴或者y轴
 wheelval      =  滚动滑轮时滑轮的值，默认向下滚动或者向右滚动为正值，相反，则为负值
 */
function scroll_y(father,soncontent,scroll_y,scroll_ymove,scroll_x,scroll_xmove,deloradd,wheelxory,wheelval){
    //y轴的高度计算:父元素高度-父元素的高度除以子元素总高；
    var marginstep=15;//步长
    var hei_father,//父元素（y轴）
        hei_fatheroffheight,//父元素距离顶部高度（y轴）
        hei_fatheroffleft,//父元素距离顶部高度（y轴）
        hei_soncontent,//子元素（y轴）
        hei_scrolly,//滚动条Y（y轴）
        hei_nowposition_y,//当前点击位置（y轴）
        hei_nowposition_y_up,//（y轴）
        hei_scrolltop_y,//滚动条距离顶部位置（y轴）
        hei_click_top,//点击位置距离滚动条滑块顶部的高度（y轴）
        hei_scroll_y_height,//模拟滚动条top值（y轴）
        hei_exceptscrollyheight,//除滑块以外的高度值（y轴）
        hei_scrollheight,//滑块每移动一像素，代表的实际margin-top距离（y轴）
        hei_changeheight,//添加或删除元素后的变化的滑块高度（y轴）
        hei_changetop;//添加或删除元素后的变化的滑块top值（y轴）
    //x轴的宽度计算:父元素width-父元素的宽度除以子元素总宽；
    var wid_father,//父元素（x轴）
        wid_fatheroffwidth,//父元素距离左侧宽度（x轴）
        wid_soncontent,//子元素（x轴）
        wid_scrollx,//滚动条X（x轴）
        wid_nowposition_x,//当前点击位置（x轴）
        wid_nowposition_x_left,//（x轴）
        wid_scrollleft_x,//滚动条距离左侧位置（x轴）
        wid_click_left,//点击位置距离滚动条滑块左侧的宽度（x轴）
        wid_scroll_x_width,//模拟滚动条left值（x轴）
        wid_exceptscrollxwidth,//除滑块以外的宽度值（x轴）
        wid_scrollwidth,//滑块每移动一像素，代表的实际margin-left距离（x轴）
        wid_changewidth,//添加或删除元素后的变化的滑块width（x轴）
        wid_changeleft;//添加或删除元素后的变化的滑块left值（x轴）
    //y轴
    hei_fatheroffheight=$("#"+father).offset().top;
    hei_father		=	$("#"+father).height();
    hei_soncontent	=	$("#"+father+" ."+soncontent).height();
    //x轴
    wid_fatheroffwidth=$("#"+father).offset().left;
    wid_father		=	$("#"+father).width();
    wid_soncontent	=	$("#"+father+" ."+soncontent).width();

    //y轴
    if(hei_father<=hei_soncontent){
        $("#"+father+" ."+scroll_y).show();
        $("#"+father+" ."+scroll_ymove).show();
        hei_scrolly		=	hei_father*(hei_father/hei_soncontent);
        $("#"+father+" ."+scroll_y).height(hei_scrolly);
    }else{
        $("#"+father+" ."+scroll_y).hide();
        $("#"+father+" ."+scroll_ymove).hide();

    }
    //x轴
    if(wid_father<wid_soncontent){
        $("#"+father+" ."+scroll_x).show();
        $("#"+father+" ."+scroll_xmove).show();
        wid_scrollx		=	wid_father*(wid_father/wid_soncontent);
        $("#"+father+" ."+scroll_x).width(wid_scrollx);
    }else{
        $("#"+father+" ."+scroll_x).hide();
        $("#"+father+" ."+scroll_xmove).hide();

    }
    if ((navigator.userAgent.match(/(iPhone|Android|iPad)/i)))
    {

    }else{
        //鼠标事件添加
        //y轴
        $("#"+father+" ."+scroll_y).mousedown(function(e){
            e.preventDefault();
            hei_nowposition_y=e.pageY;//获取当前点击点的位置
            //ie、chrome当top为0时，值为auto,需做处理
            if($("#"+father+" ."+scroll_y).css("top")=="auto"){
                $("#"+father+" ."+scroll_y).css("top","0")
            }
            hei_scroll_y_height=parseInt($("#"+father+" ."+scroll_y).css("top"))
            //前点击点距离顶部的位置
            hei_click_top=hei_nowposition_y-hei_fatheroffheight-hei_scroll_y_height;
            $("#"+father+" ."+scroll_y).attr("unorbind","bind");
        })
        //x轴
        $("#"+father+" ."+scroll_x).mousedown(function(e){
            e.preventDefault();
            wid_nowposition_x=e.pageX;//获取当前点击点的位置
            //ie、chrome当left为0时，值为auto,需做处理
            if($("#"+father+" ."+scroll_x).css("left")=="auto"){
                $("#"+father+" ."+scroll_x).css("left","0")
            }
            wid_scroll_x_width=parseInt($("#"+father+" ."+scroll_x).css("left"))

            //前点击点距离顶部的位置
            wid_click_left=wid_nowposition_x-wid_fatheroffwidth-wid_scroll_x_width;
            $("#"+father+" ."+scroll_x).attr("unorbind","bind");
        })
        $(document).mouseup(function(e){
            e.preventDefault();
            $("#"+father+" ."+scroll_y).attr("unorbind","unbind");
            $("#"+father+" ."+scroll_x).attr("unorbind","unbind");
        })


        $(document).mousemove(function(e){
            e.preventDefault();
            //y轴
            hei_scroll_y_height=parseInt($("#"+father+" ."+scroll_y).css("top"))
            hei_nowposition_y_up=e.pageY;//获取移动点的坐标
            hei_scrolltop_y=hei_nowposition_y_up-hei_fatheroffheight-hei_click_top;
            hei_exceptscrollyheight=hei_father-hei_scrolly;
            //计算滚动位置，子元素移动多长距离
            hei_scrollheight=hei_scroll_y_height*((hei_soncontent-hei_father)/hei_exceptscrollyheight);
            if($("#"+father+" ."+scroll_y).attr("unorbind")=="bind"){
                if(hei_scrolltop_y<=0){
                    $("#"+father+" ."+scroll_y).css("top",0+"px");
                }else if(hei_scrolltop_y>=hei_exceptscrollyheight){
                    $("#"+father+" ."+scroll_y).css("top",hei_exceptscrollyheight+"px");
                }else{
                    $("#"+father+" ."+scroll_y).css("top",hei_scrolltop_y+"px");
                }
                $("#"+father+" ."+soncontent).css("margin-top",-hei_scrollheight+"px")
                //$(".scroll_ynum").text(hei_nowposition_y_up+"---"+hei_scrolltop_y+"---"+hei_scrollheight+"-ddd--"+$("."+scroll_y).css("top"));
            }else{

            }
            //x轴
            wid_scroll_x_width=parseInt($("#"+father+" ."+scroll_x).css("left"))
            wid_nowposition_x_left=e.pageX;//获取移动点的坐标
            wid_scrollleft_x=wid_nowposition_x_left-wid_fatheroffwidth-wid_click_left;
            wid_exceptscrollxwidth=wid_father-wid_scrollx;
            //计算滚动位置，子元素移动多长距离
            wid_scrollwidth=wid_scroll_x_width*((wid_soncontent-wid_father)/wid_exceptscrollxwidth);
            if($("#"+father+" ."+scroll_x).attr("unorbind")=="bind"){
                if(wid_scrollleft_x<=0){
                    $("#"+father+" ."+scroll_x).css("left",0+"px");
                }else if(wid_scrollleft_x>=wid_exceptscrollxwidth){
                    $("#"+father+" ."+scroll_x).css("left",wid_exceptscrollxwidth+"px");
                }else{
                    $("#"+father+" ."+scroll_x).css("left",wid_scrollleft_x+"px");
                }
                $("#"+father+" ."+soncontent).css("margin-left",-wid_scrollwidth+"px")
                //$(".scroll_ynum1").text(wid_nowposition_x_left+"---"+wid_scrollleft_x+"---"+wid_scrollwidth+"---"+$("."+scroll_x).css("left"));
            }else{

            }
        })
    }


    if(deloradd=="del"){//需要获取子元素总的高度；重新计算滚动条每像素代表的实际margin距离和滚动条高度增加后，重新赋值
        hei_changeheight=parseInt($("#"+father+" ."+scroll_y).css("height"));
        hei_changetop=parseInt($("#"+father+" ."+scroll_y).css("top"));
        if((hei_changeheight+hei_changetop)>=hei_father){
            $("#"+father+" ."+scroll_y).css("top",(hei_father-hei_changeheight)+"px");
        }else{

        }
        //$("."+scroll_y).css("top",(hei_father-hei_changetop)+"px");
        hei_scroll_y_height=parseInt($("#"+father+" ."+scroll_y).css("top"))
        hei_exceptscrollyheight=hei_father-hei_scrolly;
        //计算滚动位置，子元素移动多长距离
        hei_scrollheight=hei_scroll_y_height*((hei_soncontent-hei_father)/hei_exceptscrollyheight);
        $("#"+father+" ."+soncontent).css("margin-top",-hei_scrollheight+"px")

    }else if(deloradd=="add"){//需要获取子元素总的高度；重新计算滚动条每像素代表的实际margin距离和滚动条高度增加后，重新赋值
        hei_changeheight=parseInt($("#"+father+" ."+scroll_y).css("height"));
        hei_changetop=parseInt($("#"+father+" ."+scroll_y).css("top"));
        if((hei_changeheight+hei_changetop)<=hei_father){
            $("#"+father+" ."+scroll_y).css("top",(hei_father-hei_changeheight)+"px");
        }else{

        }
        //$("."+scroll_y).css("top",(hei_father-hei_changetop)+"px");
        hei_scroll_y_height=parseInt($("#"+father+" ."+scroll_y).css("top"))
        hei_exceptscrollyheight=hei_father-hei_scrolly;
        //计算滚动位置，子元素移动多长距离
        hei_scrollheight=hei_scroll_y_height*((hei_soncontent-hei_father)/hei_exceptscrollyheight);
        $("#"+father+" ."+soncontent).css("margin-top",-hei_scrollheight+"px")
    }else{

    }
    if(wheelxory=="wheely"){
        //alert("a")
        //执行一次mousemove事件
        //ie、chrome当top为0时，值为auto,需做处理
        if($("#"+father+" ."+scroll_y).css("top")=="auto"){
            $("#"+father+" ."+scroll_y).css("top","0")
        }
        hei_scroll_y_height=parseInt($("#"+father+" ."+scroll_y).css("top"))
        hei_exceptscrollyheight=parseInt(hei_father-hei_scrolly);
        //alert(hei_scroll_y_height+"--"+hei_exceptscrollyheight)
        //计算滚动位置，子元素移动多长距离
        hei_scrollheight=hei_scroll_y_height*((hei_soncontent-hei_father)/hei_exceptscrollyheight);
        if(wheelval>=0){
            if(hei_scroll_y_height==hei_exceptscrollyheight){
                $("#"+father+" ."+scroll_y).css("top",hei_exceptscrollyheight+"px");
                $("#"+father+" ."+soncontent).css("margin-top",(-hei_soncontent+hei_father)+"px")
            }else if(hei_scroll_y_height>hei_exceptscrollyheight){
                $("#"+father+" ."+soncontent).css("margin-top",(-hei_soncontent+hei_father)+"px")
            }else{
                //点击添加元素，滑轮滚动，出现滚动到底部有空白
                //alert((parseInt($("."+scroll_y).css("top"))+parseInt($("."+scroll_y).css("height"))))
                if((parseInt($("#"+father+" ."+scroll_y).css("top"))+parseInt($("#"+father+" ."+scroll_y).css("height")))>=(parseInt(hei_father)-marginstep)){
                    //alert("a")
                    $("#"+father+" ."+scroll_y).css("top",hei_exceptscrollyheight+"px");
                    $("#"+father+" ."+soncontent).css("margin-top",(-hei_soncontent+hei_father)+"px")
                }else{
                    $("#"+father+" ."+scroll_y).css("top",(hei_scroll_y_height+marginstep/((hei_soncontent-hei_father)/hei_exceptscrollyheight))+"px");
                    $("#"+father+" ."+soncontent).css("margin-top",(-hei_scrollheight-marginstep)+"px")
                }
            }
        }else if(wheelval<0){
            if(hei_scroll_y_height<=0){
                $("#"+father+" ."+scroll_y).css("top","0px");
                $("#"+father+" ."+soncontent).css("margin-top","0px")
            }else{
                if((hei_scroll_y_height-marginstep/((hei_soncontent-hei_father)/hei_exceptscrollyheight))<=0){
                    $("#"+father+" ."+scroll_y).css("top","0px");
                    $("#"+father+" ."+soncontent).css("margin-top","0px")
                }else{
                    $("#"+father+" ."+scroll_y).css("top",(hei_scroll_y_height-marginstep/((hei_soncontent-hei_father)/hei_exceptscrollyheight))+"px")
                    $("#"+father+" ."+soncontent).css("margin-top",(-hei_scrollheight+marginstep)+"px")
                }
            }
        }
        //$(".scroll_ynum").text(hei_scroll_y_height+"---"+hei_exceptscrollyheight+"---"+hei_scrollheight+"---"+$("."+scroll_y).css("top")+"=="+wheelval);
    }else if(wheelxory=="wheelx"){
        //执行一次mousemove事件
        //ie、chrome当left为0时，值为auto,需做处理
        if($("#"+father+" ."+scroll_x).css("left")=="auto"){
            $("#"+father+" ."+scroll_x).css("left","0");
        }
        wid_scroll_x_width=parseInt($("#"+father+" ."+scroll_x).css("left"));
        wid_exceptscrollxwidth=wid_father-wid_scrollx;
        //计算滚动位置，子元素移动多长距离
        wid_scrollwidth=wid_scroll_x_width*((wid_soncontent-wid_father)/wid_exceptscrollxwidth);
        if(wheelval>=0){
            if(wid_scroll_x_width==wid_exceptscrollxwidth){
                $("#"+father+" ."+scroll_x).css("left",wid_exceptscrollxwidth+"px");
                $("#"+father+" ."+soncontent).css("margin-left",(-wid_soncontent+wid_father)+"px")
            }else if(wid_scroll_x_width>wid_exceptscrollxwidth){
                $("#"+father+" ."+soncontent).css("margin-left",(-wid_soncontent+wid_father)+"px")
//					$("."+scroll_x).css("left",wid_exceptscrollxwidth+"px");
            }else{
                if((wid_scroll_x_width+parseInt($("#"+father+" ."+scroll_x).css("width")))>=(wid_father-marginstep)){
                    $("#"+father+" ."+scroll_x).css("left",wid_exceptscrollxwidth+"px");
                    $("#"+father+" ."+soncontent).css("margin-left",(-wid_soncontent+wid_father)+"px")
                }else{
                    $("#"+father+" ."+scroll_x).css("left",(wid_scroll_x_width+marginstep/(wid_exceptscrollxwidth/wid_exceptscrollxwidth))+"px");
                    $("#"+father+" ."+soncontent).css("margin-left",(-wid_scrollwidth-marginstep)+"px")
                }
            }
        }else if(wheelval<0){
            if(wid_scroll_x_width<=0){
                $("#"+father+" ."+scroll_x).css("left","0px");
                $("#"+father+" ."+soncontent).css("margin-left","0px")
            }else{
                if((wid_scroll_x_width-marginstep/(wid_exceptscrollxwidth/wid_exceptscrollxwidth))<=0){
                    $("#"+father+" ."+scroll_y).css("top","0px");
                    $("#"+father+" ."+soncontent).css("margin-top","0px")
                }else{
                    $("#"+father+" ."+scroll_x).css("left",(wid_scroll_x_width-marginstep/(wid_exceptscrollxwidth/wid_exceptscrollxwidth))+"px")
                    $("#"+father+" ."+soncontent).css("margin-left",(-wid_scrollwidth+marginstep)+"px")
                }
            }
        }
    }
}