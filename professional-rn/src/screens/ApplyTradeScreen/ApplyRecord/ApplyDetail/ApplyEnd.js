/**
 * 终结方案
 */
import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { observable, action, computed } from 'mobx';
import { inject, observer } from 'mobx-react/native';
import { DisplayWithButtonApply, ButtonCommon, Dialog, ToastRoot } from '../../../../components';
import { SafeAreaView } from '../../../../containments';
import { Colors, Layout, Enum, Config, NavigatorStyle } from '../../../../global';
import { Api, Logger } from '../../../../utils';

class ApplyEndStore {
    accountStore = null;
    applyTradeStore = null;
    navigator = null;
    vid = null;
    logger = null;
    constructor(accountStore, applyTradeStore, navigator) {
        this.accountStore = accountStore;
        this.applyTradeStore = applyTradeStore;
        this.navigator = navigator;
        this.vid = applyTradeStore.vid;
        this.logger = new Logger(ApplyEndStore);
    }
    @observable rate;
    @observable coupons; // 未來新增的功能
    @observable isDialogVisible = false;

    @action getBalanceRate(businessType, couponBusinessType) {
        Api.getBalanceRate(businessType, couponBusinessType, (result) => this.getBalanceRateSuccess(result));
    }
    @action.bound getBalanceRateSuccess(result) {
        this.rate = result.data.rate;
        this.coupons = result.data.coupons;
    }
    @computed get parity() {
        return `$ 1 = ¥ ${this.rate}`;
    }
    @action setDialogVisible(isVisible) {
        this.isDialogVisible = isVisible;
    }
    @action endTrade() {
        this.setDialogVisible(false);
        Api.endApplyTrade(this.vid, null, () => this.endTradeSuccess(), (result) => this.endTradeFail(result));
    }
    @action.bound endTradeSuccess() {
        this.logger.info('申请终结成功');
        ToastRoot.show('申请成功，系统会尽快处理');
        setTimeout(() => {
            this.applyTradeStore.tabView.goToPage(Enum.applyTradeTabView.applyNew.index);
            this.navigator.popToRoot({ animated: false });
            this.accountStore.initData();
        }, Config.waitingTime);
    }
    @action.bound endTradeFail(result) {
        this.logger.info(`申请终结失敗 - result.code: ${result.code}`);
        if (result.code == 2) {
            ToastRoot.show('系统处理繁忙，请稍后再试。如多次尝试失败，请联系客服处理。');
        } else if (result.code == 3) {
            ToastRoot.show('已申请终结，不能重复申请！');
        } else if (result.code == 4) {
            ToastRoot.show('该方案为非操盘中，无法申请终结！');
        } else if (result.code == 5) {
            ToastRoot.show('未找到该折扣劵劵，申请终结失败！');
        } else if (result.code == 6) {
            ToastRoot.show('该折扣劵无法使用，申请终结失败！');
        } else if (result.code == 7 || result.code == 8 || result.code == 9){
            ToastRoot.show('请求数据有误，无法申请终结！');
        } else if (result.code == 10) {
            ToastRoot.show('有持仓或挂单，请平仓后再试！');
        } else if (result.code == 22 || result.code == 20) {
            ToastRoot.show('系统处理繁忙，请稍后再试。如多次尝试失败，请联系客服处理。');
        } else {
            ToastRoot.show('系统繁忙，请稍后再试！');
        }
    }
}
@inject('ApplyTradeStore', 'AccountStore') @observer
export default class ApplyEnd extends Component {
    constructor(props) {
        super(props);
        this.logger = new Logger(ApplyEnd);
        this.logger.info(`终结方案 id: ${this.props.ApplyTradeStore.vid}`);
        this.store = new ApplyEndStore(this.props.AccountStore, this.props.ApplyTradeStore, this.props.navigator);
        this.store.getBalanceRate(2, 8);
    }
    render() {
        return (
            <SafeAreaView>
                <View style={[{ margin: 10, marginTop: 20 }]}>
                    <Text style={{ color: Colors.titleTextColor }}>{'方案终结'}</Text>
                </View>
                <DisplayWithButtonApply label={'当前汇率  '} text={this.store.parity} />
                <DisplayWithButtonApply label={'折扣卷  '} text={'无折扣卷'} />
                <View style={{ margin: 10, marginTop: 20 }}>
                    <Text style={{ color: Colors.titleTextColor }}>{'提示'}</Text>
                    <Text style={{ color: Colors.greyText, marginTop: 10 }}>{'1.  最系统将在下个交易日开盘终结。'}</Text>
                    <Text style={{ color: Colors.greyText, marginTop: 10 }}>{'2. 折扣券一般由盈盛期货交易平台活动发放，请大家多多关注。'}</Text>
                </View>
                <View style={[{ flex: 1 }]} />
                <ButtonCommon
                    text={'确认终结'} 
                    color={Colors.white} 
                    style={{ flex: 0, height: Layout.buttonLargeHeight, marginHorizontal: Layout.applyTradeChooseDepositInfoRowPadding }}
                    backgroundColor={Colors.submitActiveBackgroundCoor}
                    textStyle={Layout.fontNormal} onPress={() => this.store.setDialogVisible(true)} 
                />
                <Dialog 
                    visible={this.store.isDialogVisible}
                    content={'是否终结该方案'}
                    onConfirm={() => this.store.endTrade()}
                    onCancel={() => this.store.setDialogVisible(false)}
                />
            </SafeAreaView>
        );
    }
}
ApplyEnd.navigatorStyle = NavigatorStyle.screenInnerStyle;
