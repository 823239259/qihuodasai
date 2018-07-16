/**
 * picker
 * 
 * data固定格式是string YYYY-MM-DD HH:mm:ss，
 * 根據格式轉換成不一樣的方式操作
 * 
 * 1. time - done
 * 2. date - yet
 * 3. dateTime - yet
 */
import React, { Component } from 'react';
import { Modal, Button, View, StyleSheet, Picker, TouchableWithoutFeedback, Text, Platform } from 'react-native';
import PropTypes from 'prop-types';
import { observer } from 'mobx-react/native';
import { Layout, Colors, Enum } from '../global';
import { I18n, Logger } from '../utils';

const pickerStyle = Platform.OS === 'ios' ? { flex: 1 } : { width: 100 };

@observer
export default class PickerDateTimeModal extends Component {

    static propTypes = {
        visible: PropTypes.bool,
        
        initValue: PropTypes.string,
        onBlockPress: PropTypes.func,
        onDone: PropTypes.func,
        type: PropTypes.oneOf(['time', 'date', 'dateTime']),   // 決定PickerDateTimeModal 的型態
    }

    static defaultProps = {
        visible: false,
        type: 'dateTime'
    }
    constructor(props) {
        super(props);
        this.logger = new Logger(PickerDateTimeModal);
        // 目前只先做 type = 'time'
        const dateTimeArr = this.props.initValue.split(' ');
        const date = dateTimeArr[0];
        const dateArr = date.split('-');
        const time = dateTimeArr[1];
        const timeArr = time.split(':');
        

        this.state = {
            initYear: dateArr[0],    // 只有按下Done換掉initValue，其他關閉的狀況都還是會回覆到initValue
            initMonth: dateArr[1],
            initDay: dateArr[2],
            
            selectedYear: dateArr[0], 
            selectedMonth: dateArr[1],
            selectedDay: dateArr[2],

            initHour: timeArr[0],    // 只有按下Done換掉initValue，其他關閉的狀況都還是會回覆到initValue
            initMinute: timeArr[1],
            initSecond: timeArr[2],
            
            selectedHour: timeArr[0], 
            selectedMinute: timeArr[1],
            selectedSecond: timeArr[2],
        };
    }
    _onModalPress() {
        this.setState({ 
            selectedYear: this.state.initYear,
            selectedMonth: this.state.initMonth,
            selectedDay: this.state.initDay,

            selectedHour: this.state.initHour,
            selectedMinute: this.state.initMinute,
            selectedSecond: this.state.initSecond,
        });
        this.props.onBlockPress && this.props.onBlockPress();
    }
    _onDone() {
        const dateTime = `${this.state.selectedYear}-${this.state.selectedMonth}-${this.state.selectedDay} ${this.state.selectedHour}:${this.state.selectedMinute}:${this.state.selectedSecond}`;
        this.setState({
            initYear: this.state.selectedYear,
            initMonth: this.state.selectedMonth,
            initDay: this.state.selectedDay,

            initHour: this.state.selectedHour,
            initMinute: this.state.selectedMinute,
            initSecond: this.state.selectedSecond,
            dateTime
        });
        this.props.onDone && this.props.onDone(dateTime);
    }
    _onRequestClose() {
        this.props.onBlockPress && this.props.onBlockPress();
    }
    _renderPickerHour() {
        return (
            <Picker
                style={pickerStyle}
                itemStyle={{ color: Colors.black }}
                selectedValue={this.state.selectedHour}
                onValueChange={(itemValue, itemIndex) => this.setState({ selectedHour: itemValue })}
            >
                { 
                    Enum.time.hour.content.map((option, index) => {
                        return <Picker.Item key={index} label={option.text} value={option.text} />;
                    })
                 }
            </Picker>
        );
    }
    _renderPickerMinute() {
        return (
            <Picker
                style={pickerStyle}
                itemStyle={{ color: Colors.black }}
                selectedValue={this.state.selectedMinute}
                onValueChange={(itemValue, itemIndex) => this.setState({ selectedMinute: itemValue })}
            >
                { 
                    Enum.time.minute.content.map((option, index) => {
                        return <Picker.Item key={index} label={option.text} value={option.text} />;
                    })
                 }
            </Picker>
        );
    }
    _renderPickerSecond() {
        return (
            <Picker
                style={pickerStyle}
                itemStyle={{ color: Colors.black }}
                selectedValue={this.state.selectedSecond}
                onValueChange={(itemValue, itemIndex) => this.setState({ selectedSecond: itemValue })}
            >
                { 
                    Enum.time.second.content.map((option, index) => {
                        return <Picker.Item key={index} label={option.text} value={option.text} />;
                    })
                 }
            </Picker>
        );
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
                        <View>
                            <View style={styles.doneContainer}>
                                <Button title={I18n.term('done')} style={{ width: 100 }} onPress={() => this._onDone()} />
                            </View>
                            <View style={styles.labelContainer}>
                                <View style={[Layout.center, { flex: 1 }]}>
                                    <Text style={{ color: Colors.greyText }}>{Enum.time.hour.label}</Text>
                                </View>
                                <View style={[Layout.center, { flex: 1 }]}>
                                    <Text style={{ color: Colors.greyText }}>{Enum.time.minute.label}</Text>
                                </View>
                                <View style={[Layout.center, { flex: 1 }]}>
                                    <Text style={{ color: Colors.greyText }}>{Enum.time.second.label}</Text>
                                </View>
                            </View>
                        </View>
                        <View style={styles.pickerContainer}>
                            <View style={{ flex: 1, width: Layout.screenWidth, flexDirection: 'row', ...Layout.center }}>
                                { this._renderPickerHour() }
                                { this._renderPickerMinute() }
                                { this._renderPickerSecond() }
                            </View>
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
        backgroundColor: Colors.pickerDoneContainerColor,
        
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderColor: Colors.greyBorder
    },
    labelContainer: {
        width: Layout.screenWidth, 
        height: 30,
        flexDirection: 'row', 
        justifyContent: 'flex-end',
        backgroundColor: Colors.pickerDoneContainerColor,
    }
});
