import { observable } from 'mobx';
import { Enum } from '../../../global';

export default class ContractInfo {
    @observable tradeType;          // 0
    @observable mainContract;       // CN1712
    @observable price;              // 手
    @observable tradTime;           // 9:00-16:30，17:00-02:00
    @observable dosize;             // 最小变动单位2.5，跳动一下±2.5美元
    @observable commodityName;      // 富时A50
    tradeNum;           // 初始最大可持仓

    constructor({ tradeType, mainContract, price, tradTime, dosize }, commodityName) {
        this.tradeType = tradeType;
        this.mainContract = mainContract;
        this.price = price;
        this.tradTime = tradTime;
        this.dosize = dosize;
        this.commodityName = commodityName;
    }
    setTradeNum(depositParam) {
        const tradeTypeSelected = Enum.productTradeType.find(tradeType => {
            return tradeType.index === this.tradeType;
        });
        this.tradeNum = depositParam[tradeTypeSelected.key];
    }
}
