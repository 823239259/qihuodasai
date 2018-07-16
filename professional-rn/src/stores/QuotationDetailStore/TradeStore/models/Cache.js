// 處理 updateAccountMoney(updateBalance)
export default class Cache {
    currencyRate = 0;
    balance = 0;
    canUse = 0;
    deposit = 0;

    constructor(currencyRate, balance, canUse, deposit) {
        this.currencyRate = currencyRate;
        this.balance = balance;
        this.canUse = canUse;
        this.deposit = deposit;
    }
}