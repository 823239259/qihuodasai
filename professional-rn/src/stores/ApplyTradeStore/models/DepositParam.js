import { observable } from 'mobx';

export default class DepositParam {
    @observable traderBond;             // 操盘保证金 ¥
    @observable traderTotal;            // 总操盘资金 $
    @observable lineLoss;               // 亏损平仓线 $

    @observable tranLever;              // 富时A50
    @observable crudeTranLever;         // 国际原油
    @observable hsiTranLever;           // 恒指期货
    @observable mdtranLever;            // 迷你道指
    @observable mntranLever;            // 迷你纳指
    @observable mbtranLever;            // 迷你标普
    @observable daxtranLever;           // 德国DAX
    @observable nikkeiTranLever         // 日经225
    @observable lhsiTranActualLever;    // 小恒指
    @observable agTranActualLever;      // 美黄金
    @observable hIndexActualLever;      // H股指数
    @observable xhIndexActualLever;     // 小H股指数
    @observable aCopperActualLever;     // 美铜
    @observable aSilverActualLever;     // 美白银
    @observable smaActualLever;         // 小原油
    @observable daxtranMinActualLever;  // 迷你德国DAX指数
    @observable naturalGasActualLever;  // 天然气指数
    @observable hs300;                  // 沪深300指数
    @observable sz50;                   // 上证50指数指数
    @observable sc;                     // 中国原油
    @observable ic500;                  // 中证500指数
    @observable lw;                     // 螺纹
    @observable rj;                     // 热卷

    @observable tk;                     // 铁矿
    @observable jm;                     // 焦煤
    @observable jt;                     // 焦炭
    @observable xj;                     // 橡胶
    @observable lq;                     // 沥青

    @observable hj;                     // 沪金
    @observable hy;                     // 沪银
    @observable ht;                     // 沪铜
    @observable hn;                     // 沪镍
    @observable hx;                     // 沪锌

    constructor({ traderBond, traderTotal, lineLoss, inMultiple, tranLever, crudeTranLever, hsiTranLever, mdtranLever, mntranLever, mbtranLever, daxtranLever, nikkeiTranLever, lhsiTranActualLever, agTranActualLever, hIndexActualLever, xhIndexActualLever, aCopperActualLever, aSilverActualLever, smaActualLever, daxtranMinActualLever, naturalGasActualLever }) {
        this.traderBond = traderBond;
        this.traderTotal = traderTotal;
        this.lineLoss = lineLoss;
        if(inMultiple){
            let jsonData = JSON.parse(inMultiple);
            jsonData.map((obj)=>{
                let that = this;
                switch (obj.commodityNo) {
                    case 'IF':
                    that.hs300 = obj.initialAmount;                       
                    break;   
                    case 'IH':
                    that.sz50 = obj.initialAmount;                       
                    break;
                    case 'sc':
                    that.sc = obj.initialAmount;                       
                    break;   
                    case 'IC':
                    that.ic500 = obj.initialAmount;                       
                    break;   
                    case 'RB':
                    that.lw = obj.initialAmount;                       
                    break; 
                    case 'HC':
                    that.rj = obj.initialAmount;                       
                    break;   
                    case 'i':
                    that.tk = obj.initialAmount;                       
                    break;
                    case 'jm':
                    that.jm = obj.initialAmount;                       
                    break;  
                    case 'j':
                    that.jt = obj.initialAmount;                       
                    break;    
                    case 'ru':
                    that.xj = obj.initialAmount;                       
                    break;  
                    case 'bu':
                    that.lq = obj.initialAmount;                       
                    break; 
                    case 'au':
                    that.hj = obj.initialAmount;                       
                    break; 
                    case 'ag':
                    that.hy = obj.initialAmount;                       
                    break;
                    case 'cu':
                    that.ht = obj.initialAmount;                       
                    break;
                    case 'ni':
                    that.hn = obj.initialAmount;                       
                    break;
                    case 'zn':
                    that.hx = obj.initialAmount;                       
                    break;
                    default:
                    break;
                }
            })
        }else{
        this.tranLever = tranLever;
        this.crudeTranLever = crudeTranLever;
        this.hsiTranLever = hsiTranLever;
        this.traderBmdtranLeverond = mdtranLever;
        this.mdtranLever = mdtranLever;
        this.mntranLever = mntranLever;
        this.mbtranLever = mbtranLever;
        this.daxtranLever = daxtranLever;
        this.nikkeiTranLever = nikkeiTranLever;
        this.lhsiTranActualLever = lhsiTranActualLever;
        this.agTranActualLever = agTranActualLever;
        this.hIndexActualLever = hIndexActualLever;
        this.xhIndexActualLever = xhIndexActualLever;
        this.aCopperActualLever = aCopperActualLever;
        this.aSilverActualLever = aSilverActualLever;
        this.smaActualLever = smaActualLever;
        this.daxtranMinActualLever = daxtranMinActualLever;
        this.naturalGasActualLever = naturalGasActualLever;
        }
    }
}
