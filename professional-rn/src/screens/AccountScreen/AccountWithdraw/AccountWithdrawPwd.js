import React, { Component } from 'react';
import { View, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Submit, FieldProvider, InputFieldEnhanced } from '../../../components';
import { Layout, NavigatorStyle } from '../../../global';
import { composeKeyboard } from './../../../hoc';


@inject('AccountWithdrawPwdStore') @observer
class AccountWithdrawPwd extends Component {
    constructor(props) {
        super(props);
        this.store = this.props.AccountWithdrawPwdStore;
        this.store.init(this.props.navigator);
    }
    render() {
        return (
            <View style={styles.container}>
                <FieldProvider form={this.store}>
                    <View style={{ marginHorizontal: Layout.fieldHorizontalMargin, flex: 1 }}>
                        <InputFieldEnhanced name='mobile' type='int' label='手机号码' style={{ marginTop: Layout.fieldMargin }} />
                        <InputFieldEnhanced 
                            name='verification' 
                            type='stringNumber' 
                            label='验证码' 
                            isVerification={true} 
                            onVerificationPress={() => this.store.onVerificationPress()}
                        />
                        <InputFieldEnhanced name='newPwd' type='string' label='设置新密码' secureTextEntry />
                        <InputFieldEnhanced name='confrimPwd' type='string' label='确认新密码' secureTextEntry />
                        
                        <Submit text={'确认'} onSubmit={() => this.store.setWithdrawPwd()} />
                    </View>
                </FieldProvider>
            </View>
        );
    }
}
const composedAccountWithdrawPwd = composeKeyboard(AccountWithdrawPwd);
composedAccountWithdrawPwd.navigatorStyle = NavigatorStyle.screenInnerStyle;
export default composedAccountWithdrawPwd;

const styles = StyleSheet.create({
    container: {
        flex: 1
    }
});
