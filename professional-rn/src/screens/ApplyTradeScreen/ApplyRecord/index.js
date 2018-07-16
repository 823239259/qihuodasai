/**
 * 顯示一筆開戶紀錄
 */
import React, { Component } from 'react';
import { ScrollView, FlatList, View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Loading, ButtonCommon } from '../../../components';
import PropTypes from 'prop-types';
import { Layout, Colors, Variables } from '../../../global';
import ApplyRecordRow from './components/ApplyRecordRow';

@inject('ApplyTradeStore', 'AccountStore') @observer
export default class ApplyRecord extends Component {
    static propTypes = {
        navigator: PropTypes.object
    }
    _toLogin() {
        const { AccountStore } = this.props;
    
        this.props.navigator.showModal({
          screen: 'account.LoginModal',
          title: '登录',
          navigatorButtons: {
            leftButtons: [
                {
                    icon: Variables.icon.closeLogin.source,
                    id: Variables.icon.closeLogin.id
                }
            ]
          }
        });
        AccountStore.setInitModal(AccountStore.ACCOUNT_MODAL.login);
    }
    _toRegister() {
        const { AccountStore } = this.props;

        this.props.navigator.showModal({
            screen: 'account.RegisterModal',
            title: '注册',
            navigatorButtons: {
            leftButtons: [
                {
                    icon: Variables.icon.closeRegister.source,
                    id: Variables.icon.closeRegister.id
                }
            ]
            }
        });
        AccountStore.setInitModal(AccountStore.ACCOUNT_MODAL.register);
    }
    _renderItem = ({ item }) => (
        <ApplyRecordRow data={item} navigator={this.props.navigator} />
    );
    render() {
        const { tradeList } = this.props.ApplyTradeStore;
        // 讀取中
        if (tradeList === null) {
            return <Loading />;
        // 尚未登入帳戶
        } else if (tradeList.length === 0) {
            return (
                <View style={[{ flex: 1, justifyContent: 'center' }]}>
                    <View style={[{ flex: 2 }, Layout.center]}>
                        <Icon
                            name="user-plus" 
                        size={80} 
                            color={Colors.kCaretColor}
                        />
                        <Text style={[Layout.fontBold, { color: Colors.greyText, marginTop: Layout.accountContainerPadding * 2 }]}>{'暂无操盘方案记录，赶快去申请吧！'}</Text>
                    </View>
                    <View style={{ flex: 1, padding: Layout.accountContainerPadding }}>
                        <ButtonCommon text={'注册'} color={Colors.red} style={{ flex: 0, height: Layout.buttonLargeHeight, borderWidth: 1, borderColor: Colors.red }} onPress={() => this._toRegister()} />
                        <ButtonCommon text={'登录'} style={{ flex: 0, height: Layout.buttonLargeHeight, marginTop: Layout.accountContainerPadding / 2 }} backgroundColor={Colors.red} color={Colors.white} onPress={() => this._toLogin()} />
                    </View>
                </View>
            );
        }
        return (
            <FlatList
                keyExtractor={item => item.tranAccount}
                removeClippedSubviews={true}
                scrollEventThrottle={16}
                data={this.props.ApplyTradeStore.tradeList}
                renderItem={this._renderItem}
            />
        );
    }
}
