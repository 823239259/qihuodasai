import { observable, action } from 'mobx';
import { Config, Variables } from '../../global';

export default class DrawerTradeStore {
    tradeSend = null;
    workbenchDetailStore = null;
    
    @observable isSwitchDialogVisible = false;
    @observable isNewFeatureDialogVisible = false;

    constructor(tradeSend, workbenchDetailStore) {
        this.tradeSend = tradeSend;
        this.workbenchDetailStore = workbenchDetailStore;
    }
    @action confirmSwitchDialog() {
        this.setIsSwitchDialogVisbile(false);
        setTimeout(() => {
            this.tradeSend.logout(Variables.trade.account.value);
            this.workbenchDetailStore.toTradeLogin();
        }, Config.waitingTime);
    }
    @action cancelSwitchSubmitDialog() {
        this.setIsSwitchDialogVisbile(false);
    }
    @action setIsSwitchDialogVisbile(isVisible) {
        this.isSwitchDialogVisible = isVisible;
    }

    @action confirmNewFeatureDialog() {
        this.setIsNewFeatureDialogVisbile(false);
    }
    @action setIsNewFeatureDialogVisbile(isVisible) {
        this.isNewFeatureDialogVisible = isVisible;
    }
}
