
import React from 'react';
import { View, TouchableWithoutFeedback, StyleSheet, Keyboard, Animated } from 'react-native';
import { observer } from 'mobx-react/native';
import { DoneKeyboard } from '../components';
import { Layout } from '../global';

@observer
export function composeKeyboard(WrappedComponent) {
    return class ComposeKeyboard extends React.Component {
        constructor(props) {
            super(props);
            this.doneButtonHeight = new Animated.Value(Layout.contentWithBottomHeight);
            this.doneButtonOpacity = new Animated.Value(0);
        }
        componentWillMount() {
            this.keyboardDidShowListener = Keyboard.addListener('keyboardWillShow', (e) => this._showKeyboard(e));
            this.keyboardDidHideListener = Keyboard.addListener('keyboardWillHide', (e) => this._hideKeyboard(e));
        }
        componentWillUnmount() {
            this.keyboardDidShowListener.remove();
            this.keyboardDidHideListener.remove();
        }
        _showKeyboard(e) {
            const keyboardHeight = e.endCoordinates.height;
            Animated.parallel([
                // doneButton - position: 'absolute': 計算方式用絕對位置推算
                Animated.timing(this.doneButtonOpacity, {
                    duration: e.duration,
                    toValue: 1,
                }),
                Animated.timing(this.doneButtonHeight, {
                    duration: e.duration,
                    toValue: Layout.contentWithBottomHeight - keyboardHeight - Layout.doneButtonHeight
                })
            ]).start();
        }
        _hideKeyboard(e) {
            Animated.parallel([
                // doneButton - position: 'absolute': 計算方式用絕對位置推算
                Animated.timing(this.doneButtonOpacity, {
                    duration: e.duration,
                    toValue: 0,
                }),
                Animated.timing(this.doneButtonHeight, {
                    duration: e.duration,
                    toValue: Layout.contentWithBottomHeight, // 藏到最底下就好了
                })
            ]).start();
        }
        render() {
            /**
             * Object.assign();
             * 1.最後可以取得的object - props
             * 2.預設的props - 會被傳入的props蓋掉
             * 3.傳入的props
             */
            const props = Object.assign({
                    style: null, 
                },
                this.props
            );
            return (
                <TouchableWithoutFeedback style={styles.container} onPress={() => Keyboard.dismiss()}>
                    <View style={{ flex: 1 }}>
                        <WrappedComponent {...props} />
                        <DoneKeyboard height={this.doneButtonHeight} opacity={this.doneButtonOpacity} /> 
                    </View>
                </TouchableWithoutFeedback>
            );
        }
    };
}
const styles = StyleSheet.create({
    container: {
        flex: 1
    },
});
