/**
 * editalbe
 *      1.字變灰色
 *      2.不顯示清除x，並將顯示文字向右移
 * isErrorMsg
 *      1.我想留下必填，又不想顯示必填錯誤訊息
 * 
 * WrappedComponent.name: ES6語法... debug沒問題, release有問題
 *  1.InputField
 *  2.SelectField
 */
import React from 'react';
import { View, Text, TouchableWithoutFeedback, TouchableOpacity } from 'react-native';
import { observer } from 'mobx-react/native';
import { extendObservable, observe } from 'mobx';
import PropTypes from 'prop-types';
import camelCase from 'camelcase';
import Icon from 'react-native-vector-icons/FontAwesome';
import { ButtonCommon } from '../../../components';
import { Colors, Layout, FieldUnderlineStyle, Enum, Variables } from '../../../global';

@observer
export function fieldWrapper(WrappedComponent, fieldType) {
    return class FieldWrapper extends React.Component {
        wrappedComponentInstance;
        wrappedComponentRef;

        static contextTypes = {
            form: PropTypes.object
        };
        constructor(props) {
            super(props);
            this.state = { focused: false };

            /**
             * Object.assign();
             * 1.最後可以取得的object - props
             * 2.預設的props - 會被傳入的props蓋掉
             * 3.傳入的props
             */
            this.newProps = Object.assign({
                    skin: 'circle', // 1.circle 2.underline
                    label: null,
                    icon: null,
                    isErrorMsg: true,
                    isErrorMsgSpace: true,
                    isVerification: false,
                    style: null, 
                    wrapperHeight: Layout.buttonLargeHeight,
                    textInputSizeExpandToParentView: true,
                    wrappedComponentStyle: null, 
                    wrappedComponentBackgroundColor: Colors.bg,
                    wrappedComponentTextAlign: 'left',
                    onFocus: () => this._focus(),
                    onBlur: () => this._blur(),
                },
                this.props,
                { ref: this.proc.bind(this) } // 取得wrappredComponent ref
            );
        }
        // 在render之後才能獲得context
        componentDidMount() {
            // 有驗證碼時,在form裡面加入兩個observables
            if (this.newProps.isVerification) {
                extendObservable(this.context.form, { 
                    imageCode: '', 
                    imageCodeCount: 0
                });
                observe(this.context.form, (change) => {
                    if (change.name === 'imageCodeCount') {
                        if (change.newValue === Enum.imageCodeCount) {
                            this._countImageCode();
                        }
                    }
                });
            }
        }
        proc(wrappedComponentInstance) {
            if (wrappedComponentInstance) {
                this.wrappedComponentInstance = wrappedComponentInstance;
                this.wrappedComponentRef = wrappedComponentInstance.refInput;
            }
        }
        _countImageCode() {
            const imageCodeInterval = setInterval(() => {
            if (this.context.form.imageCodeCount === 0) {
                clearInterval(imageCodeInterval);
                return;
            }
            this.context.form.imageCodeCount--;
            }, 1000);
        }
        // 目前其實已經用不到focused
        // 固定讓renderClear都顯示
        _focus() {
            this.setState({ focused: true });
        }
        _blur() {
            // this.setState({ focused: false });
        }
        _renderClear() {
            // const { focused } = this.state;
            // if (!this.form) {
            //     return;
            // }
            // if (focused) {
                return (
                    <TouchableOpacity 
                        style={FieldUnderlineStyle.clear}
                        onPress={() => this.wrappedComponentInstance.clear()}
                    >
                        <Icon 
                            name="close" 
                            size={20} 
                            color={'black'}
                        />
                    </TouchableOpacity>
                );
            // }  
        }
        _onVerificationPress() {
            this.props.onVerificationPress();
        }
        _renderVerification() {
            if (!this.context.form.imageCodeCount) {
                return (
                    <ButtonCommon 
                        text={'获取验证码'}
                        backgroundColor={Colors.buttonCommonTinyOnTheRightBackgroundColor}
                        color={Colors.buttonCommonTinyOnTheRightTextColor}
                        isValid={Enum.validateReg.mobile.test(this.context.form.mobile)}
                        style={{ padding: Layout.fieldGetVerificationCodeButtonPadding }} 
                        textStyle={{ fontSize: Layout.fieldGetVerificationCodeButtonFontSize }} 
                        onPress={() => this._onVerificationPress()}
                    />
                );
            }
            // 開始倒數計時，此時ButtonCommon一直維持isValid=false
            return (
                <ButtonCommon 
                    text={`倒计时: ${this.context.form.imageCodeCount}`}
                    backgroundColor={Colors.buttonCommonTinyOnTheRightBackgroundColor}
                    color={Colors.buttonCommonTinyOnTheRightTextColor}
                    isValid={false}
                    style={{ padding: Layout.fieldGetVerificationCodeButtonPadding }} 
                    textStyle={{ fontSize: Layout.fieldGetVerificationCodeButtonFontSize }} 
                />
            );
        }
        _renderRight() {
            if (fieldType === Enum.fieldType.input) {
                if (this.newProps.isVerification) {
                    return this._renderVerification();
                }
                return this._renderClear();
            }
        }
        _onLabelPress() {
            if (fieldType === Enum.fieldType.input) {
                this.wrappedComponentRef.focus();   
            }
        }
        render() {
            return (
                <View style={this.newProps.style}>
                    <View style={[this.newProps.skin === 'circle' ? FieldUnderlineStyle.containerCircle : FieldUnderlineStyle.containerUnderline, { height: this.newProps.wrapperHeight }]}>
                        <View style={[FieldUnderlineStyle.inputWrapper, { height: this.newProps.wrapperHeight - 2 }]}>
                            {/* label */}
                            <TouchableWithoutFeedback onPress={() => this._onLabelPress()}>
                                <View style={FieldUnderlineStyle.labelContainer}>
                                    { this.newProps.label && <Text style={FieldUnderlineStyle.labelStyle}>{this.newProps.label}</Text> }
                                    { this.newProps.icon && <Icon
                                                                name={this.newProps.icon} 
                                                                size={Variables.icon.size} 
                                                                color={Colors.kCaretColor}
                                    />
                                    }
                                </View>
                            </TouchableWithoutFeedback>
                            {/* field */}
                            <WrappedComponent {...this.newProps} style={[{ backgroundColor: this.newProps.wrappedComponentBackgroundColor }, this.newProps.wrappedComponentStyle && this.newProps.wrappedComponentStyle]} textAlign={'left'} />
                            { this._renderRight() }
                        </View>
                    </View>
                    { this.newProps.isErrorMsgSpace && <View style={{ ...Layout.center, height: Layout.fieldErrorHeight }}>
                                                            { this.state.focused && this.newProps.isErrorMsg && <Text style={FieldUnderlineStyle.error}>{this.context.form[camelCase('validateError', this.newProps.name)]}</Text> }
                                                       </View>
                    }
                </View>
            );
        }
    };
}
