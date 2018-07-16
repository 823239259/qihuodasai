/* 
    Mode:
    1. String
    2. Image Vertication
*/
import React, { Component } from 'react';
import { Modal, Text, TouchableOpacity, View, StyleSheet, Animated } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../global';

export default class TabBarModal extends Component {

    static propTypes = {
        visible: PropTypes.bool,
        onConfirm: PropTypes.func,
        onCancel: PropTypes.func,
        isModal: PropTypes.bool,
        isSpring: PropTypes.bool,
        
        height: PropTypes.number
    }

    static defaultProps = {
        visible: false,
        isModal: true,
        isSpring: false,
        height: 300
    }
    constructor(props) {
        super(props);        
        this.springValue = new Animated.Value(1);
    }
    componentWillUpdate() {
        this.props.isSpring && this.spring();
    }
    spring() {
        this.springValue.setValue(0.7);
        Animated.spring(
          this.springValue,
          {
            toValue: 1,
            friction: 2,
            // tension: 1
          }
        ).start();
    }
    _onRequestClose() {
        this.props.onCancel();
    }
    _getIsModal() {
        if (this.props.isModal) {
            return { backgroundColor: Colors.modalBackgroundColor }
        }
    }
    render() {
        return (
            <Modal
            animationType="fade"
            transparent={true}
            visible={this.props.visible}
            onRequestClose={() => this._onRequestClose()}
            >
                <View style={[styles.modal, { marginTop: (Layout.screenHeight - this.props.height) / 4 }, this._getIsModal()]}>
                    <Animated.View style={[styles.container, { height: this.props.height }, { transform: [{ scale: this.springValue }] }]}>
                        { this.props.children }
                        <View style={styles.buttons}>
                            <TouchableOpacity
                                style={[styles.button, { borderRightWidth: StyleSheet.hairlineWidth }]}
                                onPress={() => this.props.onCancel()}
                            >
                                <Text style={styles.buttonText}>{'取消'}</Text>
                            </TouchableOpacity>
                            <TouchableOpacity
                                style={styles.button}
                                onPress={() => this.props.onConfirm()}
                            >
                                <Text style={styles.buttonText}>{'确认'}</Text>
                            </TouchableOpacity>
                        </View>
                    </Animated.View>
                </View>
            </Modal>
        );
    }
}
const styles = StyleSheet.create({
    modal: {
        flex: 1,
        alignItems: 'center'
    },
    container: {
        backgroundColor: Colors.tabBarModalBackgroundColor,
        width: Layout.tabBarModalWidth,
        borderRadius: 2
    },
    buttons: {
        height: Layout.buttonLargeHeight,
        flexDirection: 'row'
    },
    button: {
        flex: 1,
        ...Layout.center,
        borderTopWidth: StyleSheet.hairlineWidth,
        borderColor: Colors.tabBarModalBorderColor
    },
    buttonText: {
        fontSize: Layout.tabBarButtonSize,
        color: Colors.tabBarButtonTextColor
    }
});
