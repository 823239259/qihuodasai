/**
 * Keyboard
 * DoneButtonDetail
 * isTradeLogin
 */
import React, { Component } from 'react';
import { View, TouchableWithoutFeedback, Keyboard, Animated } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { Dialog, DoneButtonDetail } from '../../../../components';
import { Logger } from '../../../../utils';
import { TradeLastView, PickerProduct, TradeNum } from '../components';
import { Variables } from '../../../../global';

@inject('WorkbenchDetailStore') @observer
export default class WorkbenchDetail extends Component {

	constructor(props) {
		super(props);
		this.logger = new Logger(WorkbenchDetail);
	}
    componentWillMount() {
		const { WorkbenchDetailStore } = this.props;
		this.keyboardDidShowListener = Keyboard.addListener('keyboardWillShow', (e) => WorkbenchDetailStore.showKeyboard(e));
		this.keyboardDidHideListener = Keyboard.addListener('keyboardWillHide', (e) => WorkbenchDetailStore.hideKeyboard(e));
	}
	componentWillUnmount() {
		this.keyboardDidShowListener.remove();
		this.keyboardDidHideListener.remove();
    }
	_getPaddingBottom() {
		const { WorkbenchDetailStore } = this.props;
		if (!WorkbenchDetailStore.isTradeNumVisible) {
			return;
		}
		return { paddingBottom: (WorkbenchDetailStore.keyboardHeight) };
	}
    render() {
		const { WorkbenchDetailStore } = this.props;
        return (
            <TouchableWithoutFeedback 
				style={[{ ...this.props.style }]}
				onPress={() => Keyboard.dismiss()}
			>
				<Animated.View style={[{ flex: 1 }, this._getPaddingBottom()]}>
					{this.props.children}
					{ WorkbenchDetailStore.isTradeLastVisible && <TradeLastView /> }
					{ WorkbenchDetailStore.isTradeNumVisible && <TradeNum showModal={() => WorkbenchDetailStore.showTradeLoginDialog()} />}
					<DoneButtonDetail />
					<Dialog 
						visible={WorkbenchDetailStore.isTradeLoginDialogVisible}
						content={'你还未登录，请先登录'}
						onConfirm={() => WorkbenchDetailStore.onConfirmTradeLoginDialog()}
						onCancel={() => WorkbenchDetailStore.onCancelTradeLoginDialog()}
					/>
					<PickerProduct />
				</Animated.View>
            </TouchableWithoutFeedback>
        );
    }
}
