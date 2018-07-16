import { observable } from 'mobx';

export default class ApplyTrade {
    id;
    @observable tranAccount;    // 操盤帳號
    @observable traderTotal;    // 总操盘金额
    @observable lineLoss;       // 亏损平仓线
    @observable appTime;        // 申请时间
    @observable stateType;      // 状态
    @observable businessTypeStr;// 业务类型 - 0.富时A50  6.原油 7.恒指 8.国际综合. 其實目前只有国际综合
    
    constructor(id, tranAccount, traderTotal, lineLoss, appTime, stateType, businessTypeStr) {
        this.id = id;
        this.tranAccount = tranAccount;
        this.traderTotal = traderTotal;
        this.lineLoss = lineLoss;
        this.appTime = appTime;
        this.stateType = stateType;
        this.businessTypeStr = businessTypeStr;
    }
}
