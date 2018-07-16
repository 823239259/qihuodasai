import { observable } from 'mobx';

export default class ContractDetail {
    @observable stateType;                 // 0
    @observable tranAccount;               // 操盘账户
    @observable tranPassword;              // 操盘密码
    @observable appTime;                   // 方案申请时间
    @observable traderBond;                // 操盘保证金
    @observable appendTraderBond;          // 追加保证金
    @observable traderTotal;               // 总操盘资金
    @observable lineLoss;                  // 亏损平仓线

    constructor({ stateType, tranAccount, tranPassword, appTime, traderBond, appendTraderBond, traderTotal, lineLoss }) {
        this.stateType = stateType;
        this.tranAccount = tranAccount;
        this.tranPassword = tranPassword;
        this.appTime = appTime;
        this.traderBond = traderBond;
        this.appendTraderBond = appendTraderBond || 0;
        this.traderTotal = traderTotal;
        this.lineLoss = lineLoss;
    }
}
