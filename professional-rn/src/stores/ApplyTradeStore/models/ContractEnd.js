import { observable } from 'mobx';

export default class ContractEnd {
    @observable endTime;                    // 方案结算时间，
    @observable tranProfitLoss;             // 交易盈亏
    @observable endParities;                // 1美元 = 人民币
    @observable endAmount;                  // 结算金额 ¥
    @observable tranFeesTotal;              // 交易手续费 ¥
    @observable traderBond;                 // 操盘保证金
    @observable appendTraderBond;           // 追加保证金

    constructor({ endtime, tranProfitLoss, endParities, endAmount, tranFeesTotal, traderBond, appendTraderBond,
        tranActualLever, crudeTranActualLever, hsiTranActualLever, mdtranActualLever, mntranActualLever, mbtranActualLever, 
        daxtranActualLever, nikkeiTranActualLever, lhsiTranActualLever, agTranActualLever, hIndexActualLever, xHStockMarketLever,
        ameCopperMarketLever, aSilverActualLever, smallCrudeOilMarketLever, daxtranMinActualLever, naturalGasActualLever }) {
        
        this.endTime = endtime; // 後端接來的變數名稱 有白癡命名
        this.tranProfitLoss = tranProfitLoss;
        this.endParities = endParities;
        this.endAmount = endAmount;
        this.tranFeesTotal = tranFeesTotal;
        this.traderBond = traderBond;
        this.appendTraderBond = appendTraderBond;

        this.tranActualLever = tranActualLever;
        this.crudeTranActualLever = crudeTranActualLever;
        this.hsiTranActualLever = hsiTranActualLever;
        this.mdtranActualLever = mdtranActualLever;
        this.mntranActualLever = mntranActualLever;
        this.mbtranActualLever = mbtranActualLever;
        this.daxtranActualLever = daxtranActualLever;
        this.nikkeiTranActualLever = nikkeiTranActualLever;
        this.lhsiTranActualLever = lhsiTranActualLever;
        this.agTranActualLever = agTranActualLever;
        this.hIndexActualLever = hIndexActualLever;
        this.xHStockMarketLever = xHStockMarketLever;
        this.ameCopperMarketLever = ameCopperMarketLever;
        this.aSilverActualLever = aSilverActualLever;
        this.smallCrudeOilMarketLever = smallCrudeOilMarketLever;
        this.daxtranMinActualLever = daxtranMinActualLever;
        this.naturalGasActualLever = naturalGasActualLever;
    }
}
