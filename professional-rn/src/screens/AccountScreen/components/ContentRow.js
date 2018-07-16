import React, { Component } from 'react';
import { View, Text, StyleSheet, Image, TouchableOpacity } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { PropTypes } from 'prop-types';
import { ToastRoot } from '../../../components';
import { Colors, Layout, Variables, Enum } from '../../../global';

@inject('AccountStore') @observer
export default class ContentRow extends Component {
    static propTypes = {
        icon: PropTypes.number,
        title: PropTypes.string,
        text: PropTypes.string,
        screen: PropTypes.string,
        
        navigator: PropTypes.object
    }
    static defaultProps = {

    }
    _renderRight() {
        if (this.props.text) {
            let textPass = this.props.text;
            if (textPass === 'down') {
                return (
                    <Icon
                        name="chevron-down"
                        size={15}
                        color={Colors.greyIcon}
                    />
                );
            } else if (this.props.screen === 'account.AccountRealNameCertification') {
                textPass = this.props.AccountStore.isCertificationText;
            }
            return <Text style={[{ color: Colors.greyIcon }]}>{textPass}</Text>;
        }
        return (
            <Icon
                name="chevron-right"
                size={15}
                color={Colors.greyIcon}
            />
        );
    }
    _toDetail() {
        // 按下 交易帳號
        if (this.props.screen === 'applyRecord') {
            this.props.AccountStore.applyTradeTabView && this.props.AccountStore.applyTradeTabView.goToPage(Enum.applyTradeTabView.applyRecord.index);
            this.props.navigator.switchToTab({ tabIndex: Enum.screen.tabBased.applyTrade.index });
            return;
        } else if (this.props.screen === 'account.AccountRealNameCertification') {
            if (this.props.AccountStore.isCertification) {
                ToastRoot.show('已完成实名认证');
                return;
            }
        }

        let navigatorButtons = null;
        if (this.props.screen === 'account.AccountWithdraw') {
            navigatorButtons = {
                rightButtons: [
                    {
                        icon: Variables.icon.exclamationAccountWithDraw.source,
                        id: Variables.icon.exclamationAccountWithDraw.id
                    }
                ]
            };
        }
        this.props.navigator.push({
            screen: this.props.screen,
            title: this.props.title,
            backButtonTitle: '', //返回
            navigatorButtons,
            animationType: 'slide-horizontal'
        });   
    }
    render() {
        return (
            <TouchableOpacity style={styles.container} onPress={() => this._toDetail()} >
                <View style={styles.left}>
                    <View style={{ width: 20, height: 20 }}>
                        <Image
                            style={{ flex: 1, width: undefined, height: undefined }}
                            source={this.props.icon}
                            resizeMode={'contain'}
                        />
                    </View>
                    <View style={[Layout.center, { marginLeft: 20 }]}>
                        <Text style={[{ color: Colors.greyText }]}>{this.props.title}</Text>
                    </View>
                </View>
                <View style={styles.right}>
                    { this._renderRight() }
                </View>
            </TouchableOpacity>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        height: 50,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingHorizontal: 20,
        
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderColor: Colors.greyIcon
    },
    left: {
        flexDirection: 'row',
        ...Layout.center
    },
    right: {
        ...Layout.center
    }
});
