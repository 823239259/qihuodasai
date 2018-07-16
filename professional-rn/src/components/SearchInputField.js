import React, { Component } from 'react';
import { View, ViewPropTypes } from 'react-native';
import PropTypes from 'prop-types';
import { InputFieldEnhanced, FieldProvider } from '../components';

export default class SearchInputField extends Component {
    static propTypes = {
        style: ViewPropTypes.style,
        store: PropTypes.object.isRequired,
        placeholder: PropTypes.string,
        iconName: PropTypes.string,
        height: PropTypes.number,
    }

    static defaultProps = {
        iconName: 'search',
        height: 30
    }
    render() {
        return (
            <FieldProvider form={this.props.store}>
                <View style={this.props.style}>
                    <InputFieldEnhanced 
                        name='search' 
                        type='string' 
                        icon={this.props.iconName}
                        isErrorMsgSpace={false}
                        wrapperHeight={this.props.height}
                        textInputSizeExpandToParentView={false}
                        placeholder={this.props.placeholder}
                    />
                </View>
            </FieldProvider>
        );
    }
}
