/**
 * Presentational Component
 * 
 * 下拉式選單
 * type:
 *  1.normal - 給予[{ key, value }]
 *  2.dateTime - time, date, dateTime
 * 顯示時string
 * 在store/form中是實際
 *  1.normal: key
 *  2.dateTime: formated dateTime string
 * 
 * 就只是個輸入欄位
 * 無：
 *  1.label
 *  2.border
 */
import React, { Component } from 'react';
import { View, Text, TouchableOpacity, StyleSheet, ViewPropTypes } from 'react-native';
import { observer } from 'mobx-react/native';
import moment from 'moment';
import PropTypes from 'prop-types';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Layout, Colors, Enum } from '../../global';
import { PickerModal, PickerDateTimeModal, PickerAddressModal } from '../../components';
import { Logger } from '../../utils';

@observer
export default class SelectField extends Component {
    static propTypes = {
        name: PropTypes.string.isRequired,
        options: PropTypes.array,
        initValue: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
        style: ViewPropTypes.style,
        disable: PropTypes.bool,

        type: PropTypes.oneOf(['normal', 'time', 'date', 'dateTime', 'address']),   // 決定PickerDateTimeModal 的型態
        displayDateTimeType: PropTypes.oneOf(['time', 'date', 'dateTime']) // 決定如何喜顯示時間
    }
    static defaultProps = {
        type: 'normal',
        displayDateTimeType: 'dateTime',
        disable: false
    }
    static contextTypes = {
        form: PropTypes.object,
    };
    constructor(props) {
        super(props);
        this.logger = new Logger(SelectField);
        this.state = { 
            width: 50, 
            height: 50,
            isPickerVisible: false
        };
    }
    setIsPickerVisible(isVisible) {
        this.setState({ isPickerVisible: isVisible });
    }
    _getText(value) {
        // 要這樣比的原因是 value === 0 是可以的
        if (value.length === 0 || value === undefined || value === null) {
            return;
        }
        if (this.props.type === 'address') {
            const provinceCityValue = value.split(' ');
            const provinceValue = provinceCityValue[0];
            const cityValue = provinceCityValue[1];
            const provinceObject = Enum.cityData.find((data) => {
                return data.value === provinceValue;
            });
            const cityObject = provinceObject.children.find((city) => {
                return city.value === cityValue;
            });
            return `${provinceObject.text} ${cityObject.text}`;
        }
        if (this.props.type !== 'normal') {
            const momentDateTime = moment(value);
            if (this.props.displayDateTimeType === 'time') {
                return momentDateTime.format('HH:mm:ss');
            } else if (this.props.displayDateTimeType === 'date') {
                return momentDateTime.format('YYYY-MM-DD');
            } else if (this.props.displayDateTimeType === 'dateTime') {
                return momentDateTime.format('YYYY-MM-DD HH:mm:ss');
            } 
            return value;
        }
        const optionSelected = this.props.options.find((option) => {
            return option.value === value;
        });
        if (optionSelected) {
            return optionSelected.text;
        }
        return '';
    }
    _renderPicker() {
        const { form } = this.context;
        const { name } = this.props;

        if (this.props.type === 'time') {
            return (
                <PickerDateTimeModal 
                    visible={this.state.isPickerVisible}
                    initValue={this.props.initValue ? this.props.initValue : form[name]}
                    type={this.props.type}
                    onBlockPress={() => this.setIsPickerVisible(false)}
                    onDone={(selectedValue) => {
                            this.setIsPickerVisible(false);
                            this.context.form[this.props.name] = selectedValue;
                        }       
                    }
                />
            );
        } else if (this.props.type === 'address') {
            return (
                <PickerAddressModal 
                    visible={this.state.isPickerVisible}
                    initValue={this.props.initValue ? this.props.initValue : form[name]}
                    type={this.props.type}
                    onBlockPress={() => this.setIsPickerVisible(false)}
                    onDone={(selectedValue) => {
                            this.setIsPickerVisible(false);
                            this.context.form[this.props.name] = selectedValue;
                        }       
                    }
                />
            );
        } else {
            return (
                <PickerModal 
                    visible={this.state.isPickerVisible} 
                    options={this.props.options}
                    labelKey={'text'}
                    valueKey={'value'}
                    initValue={this.props.initValue ? this.props.initValue : form[name]}
                    onBlockPress={() => this.setIsPickerVisible(false)}
                    onDone={(selectedValue) => {
                            this.setIsPickerVisible(false);
                            this.context.form[this.props.name] = selectedValue;
                        }       
                    }
                />
            );
        }
    }
    _getButtonBackgroundColor() {
        if (this.props.disable) {
            return { backgroundColor: Colors.tabBarFieldDisableBackgroundColor };
        }
        return { backgroundColor: Colors.tabBarFieldBackgroundColor };
    }
    _onPress() {
        if (this.props.disable) {
            return;
        }
        this.setIsPickerVisible(true);
    }
    render() {
        const { form } = this.context;
        const { name } = this.props;
        return (
            <View style={[styles.container, this.props.style && this.props.style]} onLayout={(e) => this.setState({ height: e.nativeEvent.layout.height, width: e.nativeEvent.layout.width })}>
                <View style={styles.viewForPosition}>
                    <TouchableOpacity 
                        style={[styles.button, { width: this.state.width, height: this.state.height }, this._getButtonBackgroundColor()]} 
                        onPress={() => this._onPress()}
                        activeOpacity={this.props.disable ? 1 : 0.2}
                    >
                        <Text style={{ color: Colors.quotationDetailHeaderTextColor, textAlign: 'center' }}>{this._getText(form[name])}</Text>
                    </TouchableOpacity>
                    <Icon
                        name="caret-down" 
                        size={16} 
                        color={Colors.kCaretColor}
                        style={{ position: 'absolute', bottom: -5, right: -2, backgroundColor: 'transparent', transform: [{ rotate: '-45deg' }] }} 
                    />
                </View>
                { this._renderPicker() }
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
      flex: 1,
      ...Layout.center
    },
    viewForPosition: {
      position: 'relative',
      ...Layout.center
    },
    button: {
      alignSelf: 'center',
      ...Layout.center
    }
});
