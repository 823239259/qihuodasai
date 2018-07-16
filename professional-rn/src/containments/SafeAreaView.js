/**
 * 定義自己的SafeAreaView，跟原本react-native的不一樣https://github.com/facebook/react-native/blob/master/Libraries/Components/SafeAreaView/SafeAreaView.ios.js
 */
import React, { Component } from 'react';
import { View, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { Layout } from '../global';

export default class SafeAreaView extends Component {
    static propTypes = {
        // react-native-navigation的tabBar下面會自動放上paddintBottom在iphoneX
        // 那乾脆rootView也不用加了？ 先保留
        isRootView: PropTypes.bool
    }

    static defaultProps = {
        isRootView: false
    }
    render() {
        return (
            <View style={[this.props.styles && this.props.styles, styles.container, { paddingBottom: (Layout.isIphoneX && !this.props.isRootView) ? Layout.iphoneXPaddingButton : 0 }]}>
                {this.props.children}
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1
    }
});
