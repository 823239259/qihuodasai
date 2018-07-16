import React, { Component } from 'react';
import { Modal, Button, View, StyleSheet, Picker, TouchableWithoutFeedback, Platform } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../global';
import { I18n } from '../utils';

export default class PickerModal extends Component {

    static propTypes = {
        visible: PropTypes.bool,
        
        options: PropTypes.array,  // 其實是observable array
        labelKey: PropTypes.string,
        valueKey: PropTypes.string,

        onBlockPress: PropTypes.func,
        onDone: PropTypes.func
    }

    static defaultProps = {
        visible: false,
        labelKey: 'text',
        valueKey: 'value'
    }
    constructor(props) {
        super(props);        
        this.state = {
            initValue: this.props.initValue,    // 只有按下Done換掉initValue，其他關閉的狀況都還是會回覆到initValue
            selected: this.props.initValue
        };
    }
    _onModalPress() {
        this.setState({ selected: this.state.initValue });
        this.props.onBlockPress && this.props.onBlockPress();
    }
    _onDone() {
        this.setState({ initValue: this.state.selected });
        this.props.onDone && this.props.onDone(this.state.selected);
    }
    _onRequestClose() {
        this.props.onBlockPress && this.props.onBlockPress();
    }
    _renderOptions() {
        return this.props.options.map((option, index) => {
            return <Picker.Item key={index} label={option[this.props.labelKey]} value={option[this.props.valueKey]} />;
        });
    }
    render() {
        return (
            <Modal
            animationType="fade" // none, slide, fade
            transparent={true}
            visible={this.props.visible}
            onRequestClose={() => this._onRequestClose()}
            >
                    <View style={styles.modal}>
                        <TouchableWithoutFeedback
                            onPress={() => this._onModalPress()}
                        >
                            <View style={styles.block} />
                        </TouchableWithoutFeedback>
                        <View style={styles.doneContainer}>
                            <Button title={I18n.term('done')} style={{ width: 100 }} onPress={() => this._onDone()} />
                        </View>
                        <View style={styles.pickerContainer}>
                            <Picker
                                style={{ flex: 1, width: Platform.OS === 'ios' ? Layout.screenWidth : Layout.screenWidth / 3 }}
                                itemStyle={{ color: Colors.black }}
                                selectedValue={this.state.selected}
                                onValueChange={(itemValue, itemIndex) => this.setState({ selected: itemValue })}
                            >
                                { this._renderOptions() }
                            </Picker>
                        </View>
                    </View>
            </Modal>
        );
    }
}
const contextFlex = 10;
const pickerContainerFlex = 3;
const styles = StyleSheet.create({
    modal: {
        flex: 1,
        backgroundColor: Colors.modalBackgroundColor,  
    },
    block: {
        flex: contextFlex - pickerContainerFlex
    },  
    pickerContainer: {
        flex: pickerContainerFlex,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: Colors.white
    },
    doneContainer: {
        width: Layout.screenWidth, 
        flexDirection: 'row', 
        justifyContent: 'flex-end',
        // borderBottomWidth: 1,
        // borderColor: Colors.blackBorder
        backgroundColor: Colors.pickerDoneContainerColor
    }
});
