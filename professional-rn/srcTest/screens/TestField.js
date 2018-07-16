import React, { Component } from 'react';
import { View, Text, ScrollView, StyleSheet } from 'react-native';
import { observable } from 'mobx';
import validate from 'mobx-form-validate';
import { FieldProvider, InputField, ButtonCommon } from '../../src/components';
import { Colors, Layout } from '../../src/global';

class TestFieldStore {
    @observable input1 = '';
    @observable input2 = '';
    @observable input3 = '';
    @observable input4 = '';
}
export default class TestField extends Component {
    constructor(props) {
        super(props);
        this.store = new TestFieldStore();
    }
    submit = () => {
        console.log(this.store);
    }
    /**
     * InputField
     * 1. 根據Parent顯示大小
     * 2. FieldUtil處理int, float
     */
    _renderInputField() {
        return (
            <View>
                <View style={[{ height: 50 }, Layout.center]}>
                    <Text style={[styles.text, { fontSize: 18 }]}>{'InputField'}</Text>
                </View>
                <View>
                    <Text style={styles.text}>{'type: string'}</Text>
                    <InputField name='input1' type='string' />
                </View>
                <View>
                    <Text style={styles.text}>{'type: stringNumber -> For use: 098 '}</Text>
                    <InputField name='input2' type='stringNumber' />
                </View>
                <View>
                    <Text style={styles.text}>{'type: int'}</Text>
                    <InputField name='input3' type='int' />
                </View>
                <View>
                    <Text style={styles.text}>{'type: float'}</Text>
                    <InputField name='input4' type='float' />
                </View>
            </View>
        );
    }
    render() {
        return (
            <View style={{ flex: 1 }}>
                <FieldProvider form={this.store}>
                    <ScrollView>
                        { this._renderInputField() }
                        <ButtonCommon 
                            text={'送出'} 
                            color={Colors.white} 
                            style={{ flex: 0, height: Layout.buttonLargeHeight, backgroundColor: Colors.red }} 
                            textStyle={Layout.fontNormal} onPress={this.submit} 
                        />
                    </ScrollView>
                </FieldProvider>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    text: {
        color: Colors.greyText
    }
});
