/**
 * picker
 * Enum.address
 */
import React, { Component } from 'react';
import { Modal, Button, View, StyleSheet, Picker, TouchableWithoutFeedback, Platform } from 'react-native';
import PropTypes from 'prop-types';
import { observer } from 'mobx-react/native';
import { Layout, Colors, Enum, Config } from '../global';
import { I18n, Logger } from '../utils';

const pickerStyle = Platform.OS === 'ios' ? { flex: 1 } : { width: 100 };

@observer
export default class PickerAddressModal extends Component {

    static propTypes = {
        visible: PropTypes.bool,
        
        // options: PropTypes.array,  // 其實是observable array
        initValue: PropTypes.string,
        onBlockPress: PropTypes.func,
        onDone: PropTypes.func,
        // type: PropTypes.oneOf(['time', 'date', 'dateTime']),   // 決定PickerAddressModal 的型態
    }

    static defaultProps = {
        visible: false,
    }
    constructor(props) {
        super(props);
        this.logger = new Logger(PickerAddressModal);
        // 預設第一個Enum.cityData
        const firstCityData = Enum.cityData[0];
        this.state = {
            initProvince: firstCityData.value,         // 只有按下Done換掉initValue，其他關閉的狀況都還是會回覆到initValue
            initCity: firstCityData.children[0].value,

            selectedProvince: firstCityData.value,
            selectedCity: firstCityData.children[0].value
        };
    }
    _onModalPress() {
        this.setState({ 
            selectedProvince: this.state.initProvince,
            selectedCity: this.state.initCity
        });
        this.props.onBlockPress && this.props.onBlockPress();
    }
    _onDone() {
        const proviceCity = `${this.state.selectedProvince} ${this.state.selectedCity}`;
        this.setState({
            initProvince: this.state.selectedProvince,
            initCity: this.state.selectedCity
        });
        this.props.onDone && this.props.onDone(proviceCity);
    }
    _onRequestClose() {
        this.props.onBlockPress && this.props.onBlockPress();
    }
    _onProvinceChange(itemValue) {
        const selectedCityData = Enum.cityData.find((data) => {
            return data.value === itemValue;
        });
        const selectedCityFirst = selectedCityData.children[0];
        this.setState({ selectedProvince: itemValue, selectedCity: selectedCityFirst.value, initCity: selectedCityFirst.value });
    }
    _renderPickerProvince() {
        return (
            <Picker
                style={pickerStyle}
                itemStyle={{ color: Colors.black }}
                selectedValue={this.state.selectedProvince}
                onValueChange={(itemValue, itemIndex) => this._onProvinceChange(itemValue)}
            >
                { 
                    Enum.cityData.map((data, index) => {
                        return <Picker.Item key={index} label={data.text} value={data.value} />;
                    })
                 }
            </Picker>
        );
    }
    _renderPickerCityContent() {
        const selectedCityData = Enum.cityData.find((data) => {
            return data.value === this.state.selectedProvince;
        });
        return selectedCityData.children.map((data, index) => {
            return <Picker.Item key={index} label={data.text} value={data.value} />;
        });
    }
    _renderPickerCity() {
        return (
            <Picker
                style={pickerStyle}
                itemStyle={{ color: Colors.black }}
                selectedValue={this.state.selectedCity}
                onValueChange={(itemValue, itemIndex) => this.setState({ selectedCity: itemValue })}
            >
                {this._renderPickerCityContent()}
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
                        </View>
                        <View style={styles.pickerContainer}>
                            <View style={{ flex: 1, width: Layout.screenWidth, flexDirection: 'row', ...Layout.center }}>
                                { this._renderPickerProvince() }
                                { this._renderPickerCity() }
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
