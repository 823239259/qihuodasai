/**
 * Keyboard done - almost the same as DoneKeyboard
 */
import React, { Component } from 'react';
import { Animated, TouchableOpacity, StyleSheet, Text, Keyboard } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Layout, Colors } from '../global';
import { I18n } from '../utils';


@inject('WorkbenchDetailStore') @observer
export default class DoneButton extends Component {
  
	rotateDevice() {
		return false;
	}
	render() {
		const { WorkbenchDetailStore } = this.props;

		const style = this.props.hasOwnProperty('style') ? this.props.style : {};
		const doneStyle = this.props.hasOwnProperty('doneStyle') ? this.props.doneStyle : {};
		return (
			<Animated.View style={[defaultStyle.container, { opacity: WorkbenchDetailStore.doneButtonOpacity, top: WorkbenchDetailStore.doneButtonHeight }, style]} onLayout={(e)=> this.rotateDevice(e)}>
				<TouchableOpacity onPress={() => Keyboard.dismiss()}>
					<Text style={[defaultStyle.textStyle, doneStyle]}>{this.props.title || `${I18n.term('done')}`}</Text>
				</TouchableOpacity>
			</Animated.View>
		);
	}
}
const defaultStyle = StyleSheet.create({
    container: {
        alignItems: 'flex-end',
		backgroundColor: Colors.doneButtonBackgroundColor,
        height: Layout.doneButtonHeight,
        position: 'absolute',
        left: 0,
		right: 0,
    },
    textStyle: {
        fontSize: 17,
        color: Colors.doneButtonTextColor,
		backgroundColor: 'transparent',
		padding: 10,
		textAlign: 'center'
    }
});
