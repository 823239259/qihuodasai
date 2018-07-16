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

    constructor({ traderBond, traderTotal, lineLoss, tranLever, crudeTranLever, hsiTranLever, mdtranLever, mntranLever, mbtranLever, daxtranLever, nikkeiTranLever, lhsiTranActualLever, agTranActualLever, hIndexActualLever, xhIndexActualLever, aCopperActualLever, aSilverActualLever, smaActualLever, daxtranMinActualLever, naturalGasActualLever }) {
        this.traderBond = traderBond;
        this.traderTotal = traderTotal;
        this.lineLoss = lineLoss;
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
