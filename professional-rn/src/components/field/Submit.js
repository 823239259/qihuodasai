/**
 *  Modify from https://github.com/tdzl2003/mobx-lesson-20170122
 *  Compare to ButtonCommon: Submit的用法是跟FieldProvider一起使用
 */
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { StyleSheet, TouchableOpacity, Text } from 'react-native';
import { observer } from 'mobx-react/native';
import { Colors, Layout } from '../../global';

@observer
export default class Submit extends Component {
  static propTypes = {
    text: PropTypes.string.isRequired,
    form: PropTypes.object,
    onSubmit: PropTypes.func,

    submitted: PropTypes.bool,

    style: PropTypes.object
  };
  static contextTypes = {
    form: PropTypes.object,
  };
  render() {
    const { text, onSubmit } = this.props;
    const form = this.context.form || this.props.form;
    const isValid = form.isValid && !this.props.submitted;
    return (
      <TouchableOpacity
        style={[styles.button, isValid && styles.active, this.props.style && this.props.style]}
        disabled={!isValid}
        onPress={onSubmit}
      >
        <Text style={{ color: Colors.white }}>{text}</Text>
      </TouchableOpacity>
    );
  }
}

const styles = StyleSheet.create({
    button: {
        height: Layout.buttonLargeHeight,
        ...Layout.center,
        backgroundColor: Colors.submitBackgroundColor,
        borderRadius: 5,
    },
    active: {
        backgroundColor: Colors.submitActiveBackgroundCoor,
    },
});
