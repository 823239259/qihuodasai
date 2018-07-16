import React, { Component } from 'react';
import { View, ScrollView } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import CardBankCard from './components/CardBankCard';
import { ButtonCommon } from '../../../components';
import { Colors, Layout, NavigatorStyle } from '../../../global';
import { SafeAreaView } from '../../../containments';

@inject('AccountStore') @observer
export default class AccountBankCard extends Component {
    _renderBankCards() {
        const { bankCards } = this.props.AccountStore;
        return bankCards.map(bankCard => {
            return <CardBankCard key={bankCard.id} bankCard={bankCard} length={bankCards.length} />;
        });
    }
    _toAddBankCard() {
        this.props.navigator.push({
            screen: 'account.AccountAddBankCard',
            title: '添加银行卡',
            animationType: 'slide-horizontal',
            backButtonTitle: '', //返回
        }); 
    }
    render() {
        return (
            <SafeAreaView styles={{ marginHorizontal: Layout.inset }}>
                <ScrollView>
                    { this._renderBankCards() }
                </ScrollView>
                <ButtonCommon 
                    text={'添加银行卡'} 
                    color={Colors.white} 
                    style={{ flex: 0, height: Layout.buttonLargeHeight, backgroundColor: Colors.red }} 
                    textStyle={Layout.fontNormal} onPress={() => this._toAddBankCard()} 
                />
            </SafeAreaView>
        );
    }
}
AccountBankCard.navigatorStyle = NavigatorStyle.screenInnerStyle;
