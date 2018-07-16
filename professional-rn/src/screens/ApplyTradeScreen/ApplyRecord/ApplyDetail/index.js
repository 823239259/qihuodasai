/**
 * Core -> stateType(Enum.applyTradeStateType)
 * 
 */
import React, { Component } from 'react';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import PropTypes from 'prop-types';
import { View } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { TabBarDropdown, Loading } from '../../../../components';
import { Layout, Enum, NavigatorStyle } from '../../../../global';
import { Logger } from '../../../../utils';
import ApplyDetailCurrent from './ApplyDetailCurrent';
import ApplyDetailHistory from './ApplyDetailHistory';

@inject('ApplyTradeStore') @observer
export default class ApplyDetail extends Component {
    static propTypes = {
        vid: PropTypes.string // 方案 id - ex: ff8080815f44e207015fbed702931caf
    }
    static childContextTypes = {
        navigator: PropTypes.object
    }
    constructor(props) {
        super(props);
        this.logger = new Logger(ApplyDetail);
    }
    getChildContext() {
        return { navigator: this.props.navigator };
    }
    // 放在這邊做 getTradeAccountDetail 是為了可以在回上一頁做 resetTradeAccountDetail 
    componentWillMount() {
        const { ApplyTradeStore } = this.props;
        ApplyTradeStore.getTradeAccountDetail(this.props.vid);
    }
    componentWillUnmount() {
        const { ApplyTradeStore } = this.props;
        ApplyTradeStore.resetTradeAccountDetail();
    }
    _renderCurrent() {
        const { contractDetail } = this.props.ApplyTradeStore;
        let labelCurrent = '方案明细';
        if (contractDetail) {
            if (contractDetail.stateType === Enum.applyTradeStateType.end.value) {
                labelCurrent = '结算明细';
            }
        }
        return <ApplyDetailCurrent tabLabel={labelCurrent} />;
    }
    _renderHistory() {
        const { contractDetail } = this.props.ApplyTradeStore;
        if (contractDetail) {
            if (contractDetail.stateType === Enum.applyTradeStateType.end.value) {
                return <ApplyDetailHistory tabLabel={'历史成交明细'} />;
            }
        }
    }
    render() {
        if (!this.props.ApplyTradeStore.contractDetail) {
            return (
                <View style={[Layout.center, { flex: 1 }]}>
                    <Loading />
                </View>
            );
        }
        return (
            <ScrollableTabView
                style={{ flex: 1 }}
                locked={true}
                scrollWithoutAnimation={true}
                renderTabBar={() => <TabBarDropdown textStyle={Layout.fontBold} />}
            >
                { this._renderCurrent() }
                { this._renderHistory() }
            </ScrollableTabView>
        );
    }
}
ApplyDetail.navigatorStyle = NavigatorStyle.screenInnerStyle;
