import React, { Component } from 'react';
import { View, Image } from 'react-native';
import PropTypes from 'prop-types';
import { InputField } from '../../components';
import { Config, Colors } from '../../global';

export default class ImageVerification extends Component {
    static contextTypes = {
        form: PropTypes.object
    }
    render() {
        const imgUri = `${Config.api_domain}/sendImageCode?1=${Math.random() * 1000}&mobile=${this.context.form.mobile}`;
        return (
            <View style={{ flex: 1, flexDirection: 'row' }}>
                <InputField name='imageCode' type={'stringNumber'} valueColor={Colors.black} style={{ backgroundColor: 'transparent', borderWidth: 1, borderRadius: 5, borderColor: 'rgb(197, 197, 197)', justifyContent: 'center', margin: 8 }} />
                <Image
                    style={{ flex: 1, height: undefined, width: undefined, margin: 8 }}
                    resizeMode={'contain'}
                    source={{ uri: imgUri }}
                />
            </View>
        );
    }
}
