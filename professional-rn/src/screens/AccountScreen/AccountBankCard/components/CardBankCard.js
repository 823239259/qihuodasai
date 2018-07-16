import React, { Component } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Image } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import PropTypes from 'prop-types';
import { Dialog } from '../../../../components';
import { inject, observer } from 'mobx-react/native';
import { Colors, Layout } from '../../../../global';

@inject('AccountStore') @observer
export default class CardBankCard extends Component {
    static propTypes = {
        editable: PropTypes.bool,
        toRight: PropTypes.func,

        bankCard: PropTypes.object,
        length: PropTypes.number
    }
    static defaultProps = {
        editable: true
    }
    constructor(props) {
        super(props);
        this.state = { isDialogVisible: false, dialogContent: '' };
    }
    _onRemovePress() {
        this.setState({ isDialogVisible: false });
        this.props.AccountStore.deleteBankCard(this.props.bankCard.id);
    }
    _onPress() {
        if (this.props.editable) {
            this.props.AccountStore.setBankCardDefault(this.props.bankCard.id);
        }
    }
    _getTouchableCheckIcon() {
        return (
            <TouchableOpacity>
                <Icon
                    name={'check-circle'} 
                    size={24} 
                    color={Colors.boxActiveBlue}
                />
            </TouchableOpacity>
        );
    }
    _getTouchableTimeIcon() {
        const { bankCard } = this.props;
        return (
            <TouchableOpacity onPress={() => this.setState({ isDialogVisible: true, dialogContent: `${bankCard.name} ****...${bankCard.card.substring(bankCard.card.length - 4, bankCard.card)}` })}>
                <Icon
                    name={'times-circle'} 
                    size={24} 
                    color={Colors.greyIcon}
                />
            </TouchableOpacity>
        );
    }
    _getTouchableChevronRight() {
        return (
            <TouchableOpacity onPress={() => this.props.toRight()}>
                <Icon
                    name={'chevron-circle-right'} 
                    size={24} 
                    color={Colors.greyIcon}
                />
            </TouchableOpacity>
        );
    }
    _renderTouchableIcon() {
        if (!this.props.editable) {
            return this._getTouchableChevronRight();
        } 
        // 多張銀行卡時，不能刪除被selected的銀行卡，只有銀行卡一張時，才能被直接刪除
        if (this.props.bankCard.selected && this.props.length > 1) {
            return this._getTouchableCheckIcon();
        }
        return this._getTouchableTimeIcon();
    }
    render() {
        const { bankCard } = this.props;
        return (
            <View>
                <TouchableOpacity 
                    style={styles.container} 
                    onPress={() => this._onPress()}
                    activeOpacity={this.props.editable ? 0.2 : 1}
                >
                    <View style={{ flex: 1, flexDirection: 'row', justifyContent: 'space-between' }}>
                        <View style={[Layout.center, { flexDirection: 'row' }]}>
                            <Image
                                style={{ width: 50, height: 50 }}
                                source={bankCard.bankImage}
                            />
                            <Text style={[Layout.fontBold, { color: bankCard.selected ? Colors.white : Colors.greyText, marginLeft: 10 }]}>{bankCard.name}</Text>
                        </View>
                        { this._renderTouchableIcon() }
                    </View>
                    <View style={[Layout.center, { flex: 1, flexDirection: 'row', justifyContent: 'space-between' }]}>
                        <Text style={[Layout.fontBolder, { color: bankCard.selected ? Colors.white : Colors.greyText, alignSelf: 'flex-end' }]}>{'****'}</Text>
                        <Text style={[Layout.fontBolder, { color: bankCard.selected ? Colors.white : Colors.greyText, alignSelf: 'flex-end' }]}>{'****'}</Text>
                        <Text style={[Layout.fontBolder, { color: bankCard.selected ? Colors.white : Colors.greyText, alignSelf: 'flex-end' }]}>{'****'}</Text>
                        <Text style={[Layout.fontBolder, { color: bankCard.selected ? Colors.white : Colors.greyText }]}>{bankCard.card.substring(bankCard.card.length - 4, bankCard.card)}</Text>
                    </View>
                </TouchableOpacity>
                <Dialog 
                    visible={this.state.isDialogVisible}
                    height={Layout.dialogHeight}
                    header={'删除银行卡'}
                    content={this.state.dialogContent}
                    onConfirm={() => this._onRemovePress()}
                    onCancel={() => this.setState({ isDialogVisible: false })}
                />
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        height: Layout.cardHeight, // 需要有這高度，不然 **** **** **** 5555 沒法置中
        borderRadius: 5,
        backgroundColor: Colors.cardBackgroundColor,
        marginTop: Layout.cardMargin,
        padding: Layout.cardMargin * 2,
        paddingBottom: Layout.cardMargin,
    },
});

