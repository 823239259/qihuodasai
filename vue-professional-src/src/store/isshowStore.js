 //控制显示与否的模块
export default class Isshow {
    constructor () {
        this.state = {
            navBarShow: true,
            isconnected: false,
            bottomshow: false, 
            pshow: false,  //盘口显示标识
            sshow: false,  //闪电图显示标识
            fshow: true,  //分时图显示标识
            kshow: false,  //k线图显示标识
            guideshow: false, //
            helpshow: true,
            isfensshow: false, //是否进入过分时
            isfenssec: false, //判断是否是直接画图
            islightshow: false, //是否进入过闪电图
            isklineshow: false, //是否进入过K线图
            currentIndex: 1, //tabbar选中标识
        }
    }
   
 }
