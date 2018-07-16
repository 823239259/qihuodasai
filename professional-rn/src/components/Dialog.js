/* 
    Mode:
    1. String
    2. Image Vertication
*/
import React, { Component } from 'react';
import { Modal, Text, TouchableOpacity, View, StyleSheet, Animated, ScrollView } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../global';

export default class Dialog extends Component {

    static propTypes = {
        visible: PropTypes.bool,
        onPress: PropTypes.func,
        header: PropTypes.string,
        content: PropTypes.string,
        onConfirm: PropTypes.func,
        onCancel: PropTypes.func,
        isSpring: PropTypes.bool,
        renderContent: PropTypes.func,
        height: PropTypes.number,
        width: PropTypes.number,

        isCancel: PropTypes.bool,
        isContentScrollable: PropTypes.bool,
    }

    static defaultProps = {
        visible: false,
        header: '提示',
        content: '內容',
        isSpring: false,
        height: 150,
        width: Layout.dialogWidth,

        isCancel: true,
        isContentScrollable: false
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
    _renderContent() {
        if (this.props.isContentScrollable) {
            return (
                <ScrollView contentContainerStyle={[styles.content]}>
                    <Text style={{ fontSize: Layout.dialogContentFontSize }}>{this.props.content}</Text>
                </ScrollView>
            );
        }
        return (
            <View style={[{ flex: 1 }, styles.content]}>
                <Text style={styles.contentText}>{this.props.content}</Text>
            </View>
        );
    }
    _renderCancel() {
        if (this.props.isCancel) {
            return (
                <TouchableOpacity
                    style={[styles.button, { borderRightWidth: 1 }]}
                    onPress={() => this.props.onCancel()}
                >
                    <Text style={styles.buttonText}>{'取消'}</Text>
                </TouchableOpacity>
            );
        }
    }
    _onRequestClose() {
        if (this.props.isCancel) {
            this.props.onCancel();
        } else {
            this.props.onConfirm();
        }
    }
    // 可以傳入一個component
    renderContent() {
        if (this.props.renderContent) {
            return (
                <View style={[{ flex: 1 }, styles.content]}>
                    { React.cloneElement(this.props.renderContent()) }
                </View>    
            );
        }
        return this._renderContent();
    }
    render() {
        return (
            <Modal
            animationType="fade"
            transparent={true}
            visible={this.props.visible}
            onRequestClose={() => this._onRequestClose()}
            >
                <View style={styles.modal}>
                    <Animated.View style={[styles.container, { height: this.props.height, width: this.props.width }, { transform: [{ scale: this.springValue }] }]}>
                        <View style={styles.headerContentContainer}>
                            <View style={styles.header}>
                                <Text style={styles.headerText}>{this.props.header}</Text>
                            </View>
                            { this.renderContent() }
                        </View>
                        <View style={styles.buttons}>
                            { this._renderCancel() }
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
const largefontSize = 18;

const styles = StyleSheet.create({
    modal: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: Colors.modalBackgroundColor,  
    },
    container: {
        backgroundColor: Colors.dialogBackgroundColor,
        borderRadius: 5
    },
    headerContentContainer: {
        flex: 1,
    },
    header: {
        // flex: 2,
        marginTop: 20,
        justifyContent: 'flex-end',
        alignItems: 'center',
    },
    content: {
        // flex: 3,
        justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal: Layout.dialogContentPadding
    },
    headerText: {
        fontSize: largefontSize
    },
    buttons: {
        height: 50,
        flexDirection: 'row'
    },
    button: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        borderTopWidth: 1,
        borderColor: Colors.dialogBorderColor
    },
    buttonText: {
        fontSize: largefontSize,
        color: Colors.dialogButtonTextColor
    }
});
