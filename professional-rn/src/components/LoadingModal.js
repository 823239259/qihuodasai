/**
 * ActivityIndicator with Modal : block user action
 */
import React, { Component } from 'react';
import { View, Text, StyleSheet, ActivityIndicator, Modal } from 'react-native';
import PropTypes from 'prop-types';
import { observer } from 'mobx-react/native';
import { Colors, Layout } from '../global';

@observer
export default class LoadingModal extends Component {

    static propTypes = {
        size: PropTypes.string,
        isVisible: PropTypes.bool,
        text: PropTypes.string,
        renderContent: PropTypes.func
    }

    static defaultProps = {
        size: 'small',
        isVisible: false,
        text: '系统连线中'
    }
    
    _renderContent() {
        return (
            <View style={[Layout.center]}>
                <ActivityIndicator size={this.props.size} style={{ marginBottom: 15 }} />
                <Text style={[{ color: Colors.white }]}>{this.props.text}</Text>
            </View>
        );
    }
    _onRequestClose = () => {

    }
    render() {
        return (
            <Modal
            animationType="fade" // slide
            transparent={true}
            visible={this.props.isVisible}
            onRequestClose={this._onRequestClose}
            >
                <View style={styles.modal}>
                    { this.props.renderContent ? this.props.renderContent() : this._renderContent() }
                </View>
            </Modal>
        );
    }
}
const styles = StyleSheet.create({
    modal: {
        flex: 1,
        backgroundColor: Colors.modalBackgroundColor,  
        ...Layout.center
    }
});
