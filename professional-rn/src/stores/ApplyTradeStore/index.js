import EventEmitter from 'EventEmitter';
import { observable, action, computed } from 'mobx';
import moment from 'moment';
import { Variables, Enum } from '../../global';
import { ApplyTrade, DepositParam, ContractInfo, ContractDetail, ContractEnd, ContractHistory } from './models';
import { Logger, Api, TradeUtil } from '../../utils';

export default class ApplyTradeStore {
    tabView = null;
    eventEmitter = null;
    accountStore = null;
    // ApplyNew - 開戶申請
    @observable balance = null;         // 帐户余额
    @observable bannerList = [];
    @observable contractList = [];
    @observable depositParams = [];
    @observable chooseDeposit = null;   // 3000
    // 點擊不同保證金時，要取得DepositParam
    @computed get depositInfo() {
        return this.depositParams.find(deposit => {
            return deposit.traderBond === this.chooseDeposit;
        });
    }
    // 點擊不同保證金時，要取得DepositParam 並塞入ContractInfo，顯示不同手數
    @computed get contractListWithParam() {
        const depositParam = this.depositParams.find(deposit => {
            return deposit.traderBond === this.chooseDeposit;
        });
        return this.contractList.map(contract => {
            contract.setTradeNum(depositParam);
            return contract;
        });
    }
    // true 表示 餘額充足
    @computed get compared() {
        if (!this.balance || !this.chooseDeposit) {
            return false;
        }
        if (this.balance >= this.chooseDeposit) {
            return true;
        }
        return false;
    }
    @computed get appTimeString() {
        return moment(this.contractDetail.appTime * 1000).format('YYYY-MM-DD HH:mm');
    }
    // ApplyRecord - 開戶紀錄
    @observable tradeList = null;
    // ApplyDetail - 方案明細
    // vid 當前選擇的方案id - 1.getTradeAccountDetail 2.getFstTradeDetail
    vid = null;
    @observable outDiskVoList = [];                 // 為了productName
    @observable contractDetail = null;
    // ApplyDetailEnd - 方案明細 - 结算明细
    @observable contractEnd = null;
    // ApplyDetailHistory - 历史成交明细
    @observable contractHistorys = [];

    @computed get endTimeString() {
        return moment(this.contractEnd.endTime * 1000).format('YYYY-MM-DD HH:mm');
    }
    @computed get profitLossString() {
        return `¥ ${TradeUtil.formatCurrency(this.contractEnd.tranProfitLoss * this.contractEnd.endParities)} ($ ${TradeUtil.formatCurrency(this.contractEnd.tranProfitLoss)})`;
    }
    @computed get parity() {
        return `$ 1 = ¥ ${this.contractEnd.endParities}`;
    }
    constructor(accountStore) {
        this.logger = new Logger(ApplyTradeStore);
        this.accountStore = accountStore;
        this.eventEmitter = new EventEmitter();
        this.eventEmitter.addListener('accountStore', this.handleAccountStore.bind(this));

        this.accountStore.setEventEmitterbetweenApplyTradeStore(this.eventEmitter);
    }
    init(tabView) {
        this.tabView = tabView;
    }
    @action getTradeAccountList() {
        if (Variables.account.isLogged) {
            Api.getTradeAccountList((result) => this.getTradeAccountListSuccess(result));
            this.logger.info('獲取多個交易帳號');
        } else {
            this.tradeList = [];
        }
    }
    @action.bound getTradeAccountListSuccess(result) {
        this.tradeList = result.data.tradeList.map((t) => {
            return new ApplyTrade(t.id, t.tranAccount, t.traderTotal, t.lineLoss, moment(t.appTime * 1000).format('YYYY-MM-DD HH:mm'), t.stateType, t.businessTypeStr);
        });
        this.logger.info('獲取交易帳號成功');
    }
    @action getBannerList() {
        Api.getBannerList((result) => this.getBannerListSuccess(result));
    }
    @action.bound getBannerListSuccess(result) {
        this.bannerList = result.data.bannerList.map(banner => {
            return { imageUri: banner.imgPath };
        });
    }
    @action getTradeParams() {
        this.logger.info('獲取保證金選項');
        Api.getTradeParams((result) => {
            //console.log(result);
            this.getTradeParamsSuccess(result)
        });
    }
    @action.bound getTradeParamsSuccess(result) {
        this.depositParams = result.data.paramList.map(param => {
            return new DepositParam(param);
        });
        // 預設一開始選擇保證金3000
        this.chooseDeposit = this.depositParams[0].traderBond;
        // 將傳來的contractList，多給予一個commodityName
        this.contractList = result.data.contractList.map(contract => {
            const commodityName = this.getCommodityNameAndSetProductName(contract.mainContract);
            const contractInfo = new ContractInfo(contract, commodityName);
            return contractInfo;
        });
        this.logger.info('獲取保證金成功，並預設選擇保證金3000');
    }
    @action getApplyTradeInfo() {
        this.logger.info('获取支付申请数据');
        Api.getApplyTradeInfo(this.chooseDeposit, (result) => this.getApplyTradeInfoSuccess(result));
    }
    @action.bound getApplyTradeInfoSuccess(result) {
        this.balance = result.data.confirmInfo.balance;
    }
    // 获取当前方案信息
    @action getTradeAccountDetail(vid) {
        this.vid = vid;
        // this.vid = 'ff8080815f44e207015fbed702931caf';
        this.logger.info(`获取当前方案信息 - id: ${this.vid}`);
        Api.getTradeAccountDetail(this.vid, (result) => this.getTradeAccountDetailAll(result));
    }
    @action resetTradeAccountDetail() {
        this.contractDetail = null;
        this.contractEnd = null;
        this.outDiskVoList = [];
        this.resetFstTradeDetail();
    }
    @action.bound getTradeAccountDetailAll(result) {
        const details = result.data.details;
        this.setContractDetail(details);
        this.setOutDiskVoList(details);
        this.logger.info(`获取方案信息成功 - stateType: ${details.stateType}, stateType===6 才顯示開戶紀錄 `);
        if (details.stateType === Enum.applyTradeStateType.end.value) {
            this.logger.info('获取開戶紀錄');
            this.setContractEnd(details);
            this.getFstTradeDetail(); // 開戶紀錄 只有在已結算時才會出現
        }
    }
    @action setContractDetail(details) {
        this.contractDetail = new ContractDetail(details);
    }
    @action setContractEnd(details) {
        this.contractEnd = new ContractEnd(details);
    }
    // 取得可交易品種，並顯示commodityName & mainContract. 每次都一樣 不需要重新push
    @action setOutDiskVoList(details) {
        if (this.outDiskVoList && this.outDiskVoList.length === 0) {
            details.outDiskVoList.forEach(out => {
                const commodityName = Enum.productTradeType.find(o => {
                    return o.index === out.tradeType;
                }).commodityName;
                this.outDiskVoList.push({ mainContract: out.mainContract, commodityName });
            });
        }
    }
    // 获取历史成交明细
    @action getFstTradeDetail() {
        Api.getFstTradeDetail(this.vid, (result) => this.getFstTradeDetailSuccess(result));
    }
    @action.bound getFstTradeDetailSuccess(result) {
        result.data.forEach((history, index) => {
            this.contractHistorys.push(new ContractHistory(history, index + 1));
        });
    }
    @action resetFstTradeDetail() {
        this.contractHistorys = [];
    }
    refreshAccountData() {
        // 更新餘額
        this.accountStore.getBalance();
        // 更新資金明細
        this.accountStore.getFund();
    }
    // 操盤申請 -> 移動到 -> 開戶申請，因為我要點擊開戶紀錄的時候，再次去查一次
    // 因為是在不顯示畫面的時候作切換，所以不需要setTimeout()
    handleAccountStore() {
        this.tabView.goToPage(Enum.applyTradeTabView.applyNew.index);
    }
    // 行情 & 操盤申請 可能會不一致 因為是兩個系統
    // 但是 commodityNo 一定一致
    getCommodityNameAndSetProductName(mainContract) {
        const mainContractArr = mainContract.split(/([A-Za-z]+)([0-9]+)/);
        let commodityNo = ''
        if(mainContractArr.length > 1){
             commodityNo = mainContractArr[1];
        }else{
             commodityNo = mainContractArr[0];
        }
        const productTradeType = Enum.productTradeType.find(product => {
            return commodityNo === product.commodityNo;
        });
        productTradeType.productName = mainContract;
        return productTradeType.commodityName;
    }
}

