/**
 * Modify from https://github.com/tdzl2003/mobx-lesson-20170122
 */
import React, { Component } from 'react';
import PropTypes from 'prop-types';

export default class FieldProvider extends Component {
  static propTypes = {
    form: PropTypes.object, // form: 放上欄位資料
    data: PropTypes.object, // data: 放上需要access的data
    children: PropTypes.element.isRequired,
  };

  static childContextTypes = {
    form: PropTypes.object,
    data: PropTypes.object
  };

  getChildContext() {
    return {
      form: this.props.form,
      data: this.props.data
    };
  }
  render() {
    return React.Children.only(this.props.children);
  }
}
