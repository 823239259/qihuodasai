/**
 * Presentational Component
 * 
 * 根據Parent顯示大小
 * 
 * 數入數字：
 * type:
 *  1.int
 *  2.float
 * 顯示時string
 * 在store/form中是實際的int || float
 * 
 * 就只是個輸入欄位
 * 無：
 *  1.label
 *  2.border
 */
import React, { Component } from 'react';
import { observer } from 'mobx-react/native';
import { View, TextInput, StyleSheet, ViewPropTypes } from 'react-native';
import PropTypes from 'prop-types';
import { Colors, Layout } from '../../global';
import { FieldUtil } from '../../utils';

@observer
export default class InputField extends Component {
    static propTypes = {
        name: PropTypes.string.isRequired,
        value: PropTypes.string,    // 因為TextInput..
        /**
         * keyboardType
         *      - default: string
         *      - numeric: stringNumber, int, float
         * stringNumber的意思是 只能輸入數字字串，用在認證碼，會出現 098 這種狀況，所以也不屬於int or float
         */
        type: PropTypes.oneOf(['string', 'stringNumber', 'int', 'float']).isRequired,
        skin: PropTypes.oneOf(['circle', 'underline']),
        onChangeText: PropTypes.func,
        // style
        style: ViewPropTypes.style,

        labelBackgroundColor: PropTypes.string,
        labelColor: PropTypes.string,
        labelFontSize: PropTypes.number,

        valueColor: PropTypes.string,
        textInputSizeExpandToParentView: PropTypes.bool
    }
    static defaultProps = {
        value: '',
        type: 'string',
        skin: 'circle',
        // style
        valueColor: Colors.white,
        textInputSizeExpandToParentView: true
    }
    static contextTypes = {
        form: PropTypes.object
    };
    constructor(props) {
        super(props);
        this.state = { textInputWidth: 50, textInputHeight: 50 };
    }
    clear() {
        const { name } = this.props;
        const form = this.context.form;
        form[name] = '';
    }
    _onChangeText(text) {
        const { name } = this.props;
        const form = this.context.form;
        let newText = text;
        if (this.props.type === 'string') {
            newText = FieldUtil.getString(text);
            form[name] = newText;
        } else if (this.props.type === 'stringNumber') {
            newText = FieldUtil.getStringNumber(text);
            form[name] = newText;
        } else {
            if (this.props.type === 'int') {
                newText = FieldUtil.getInt(text);
            } else {
                newText = FieldUtil.getAccessMoneyFloat(text);
            }
            if (newText !== null) {
                form[name] = newText;
            }
        }
    }
    calSize(layout) {
        this.setState({ textInputHeight: layout.height, textInputWidth: layout.width });
    }
    render() {
        const { name, ...others } = this.props;
        const form = this.context.form;
        return (
            <View 
                style={[styles.container, this.props.style && this.props.style]}
                onLayout={(e) => this.calSize(e.nativeEvent.layout)}
            >
                <TextInput
                    {...others}
                    underlineColorAndroid={'transparent'}
                    keyboardType={this.props.type === 'string' ? 'default' : 'numeric'}
                    style={{ 
                        padding: 0, 
                        width: this.state.textInputWidth, 
                        height: this.props.textInputSizeExpandToParentView ? this.state.textInputHeight : undefined, 
                        textAlign: this.props.textAlign ? this.props.textAlign : 'center', 
                        color: this.props.valueColor 
                    }}
                    value={form[name]}
                    onChangeText={(text) => this._onChangeText(text)}
                    placeholderTextColor={Colors.greyTextLight}
                    ref={(textInput) => this.refInput = textInput}
                    onFocus={() => {
                        this.props.onFocus && this.props.onFocus();
                    }}
                    onBlur={() => {
                        this.props.onBlur && this.props.onBlur();
                    }}
                />
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        ...Layout.center,
        backgroundColor: Colors.tabBarFieldBackgroundColor
    }
});
