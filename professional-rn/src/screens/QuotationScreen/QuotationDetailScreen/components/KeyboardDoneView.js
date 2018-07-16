import React, { Component } from 'react';
import { View, Text, Keyboard } from 'react-native';
import TradeNum from './TradeNum';

export default class KeyboardDoneView extends Component {
    constructor(props) {
        super(props);
        this.state = {
            keyboardHeight: 0
        };
    }
    componentWillMount() {
		this.keyboardDidShowListener = Keyboard.addListener('keyboardWillShow', this.keyboardWillShow.bind(this));
		this.keyboardDidHideListener = Keyboard.addListener('keyboardWillHide', this.keyboardWillHide.bind(this));
	}
	componentWillUnmount() {
		this.keyboardDidShowListener.remove();
		this.keyboardDidHideListener.remove();

		this.setState({
			visibleHeight: this.windowHeight,
			hideKA: true,
			opacity: 0
		});
    }
    keyboardWillShow(e) {
        this.setState({ keyboardHeight: e.endCoordinates.height });
		// if(this.state.hideKA) {
		// 	LayoutAnimation.configureNext({
		// 		duration: 500,
		// 		create: {
		// 			type: LayoutAnimation.Types.linear,
		// 			property: LayoutAnimation.Properties.scaleXY
		// 		},
		// 		update: {
		// 			type: LayoutAnimation.Types.linear,
		// 			property: LayoutAnimation.Properties.scaleXY
		// 		},
		// 	});
		// }
		// this.setState({
		// 	visibleHeight: e.endCoordinates.screenY - (INPUT_ACCESSORY_HEIGHT - 1),
		// 	hideKA: false,
		// 	opacity: 1
		// });
    }
    keyboardWillHide(e) {
		// this.setState({
		// 	visibleHeight: this.windowHeight,
		// 	hideKA: true,
		// 	opacity: 0
        // });
        this.setState({ keyboardHeight: 0 });
	}
    render() {
        return (
            <View style={[{ ...this.props.style }, { paddingBottom: this.state.keyboardHeight }]}>
                {this.props.children}
                <TradeNum showModal={() => this.props.showModal()} />
            </View>
        );
    }
}
