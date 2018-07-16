/**
 * Created by sohobloo on 16/9/13.
 */

'use strict';

import React, { Component } from 'react';

import {
  StyleSheet,
  Dimensions,
  View,
  Text,
  ListView,
  TouchableWithoutFeedback,
  TouchableOpacity,
  Modal,
  ActivityIndicator,
} from 'react-native';
import PropTypes from 'prop-types';
import { Colors } from '../../global';

const TOUCHABLE_ELEMENTS = ['TouchableHighlight', 'TouchableOpacity', 'TouchableWithoutFeedback', 'TouchableNativeFeedback'];

export default class ModalDropdown extends Component {
  static propTypes = {
    disabled: PropTypes.bool,
    defaultIndex: PropTypes.number,
    defaultValue: PropTypes.string,
    options: PropTypes.array,

    accessible: PropTypes.bool,
    animated: PropTypes.bool,
    showsVerticalScrollIndicator: PropTypes.bool,

    style: PropTypes.oneOfType([PropTypes.number, PropTypes.object, PropTypes.array]),
    textStyle: PropTypes.oneOfType([PropTypes.number, PropTypes.object, PropTypes.array]),
    dropdownStyle: PropTypes.oneOfType([PropTypes.number, PropTypes.object, PropTypes.array]),
    dropdownTextStyle: PropTypes.oneOfType([PropTypes.number, PropTypes.object, PropTypes.array]),
    dropdownTextHighlightStyle: PropTypes.oneOfType([PropTypes.number, PropTypes.object, PropTypes.array]),

    adjustFrame: PropTypes.func,
    renderRow: PropTypes.func,
    renderSeparator: PropTypes.func,

    onDropdownWillShow: PropTypes.func,
    onDropdownWillHide: PropTypes.func,
    onSelect: PropTypes.func,
    layoutDropdown: PropTypes.object
  };

  static defaultProps = {
    disabled: false,
    defaultIndex: -1,
    defaultValue: 'please select...',
    options: null,
    animated: true,
    showsVerticalScrollIndicator: true,
    layoutDropdown: { x: 0, y: 0, width: 0, height: 0 }
  };

  constructor(props) {
    super(props);

    this._button = null;
    // this._buttonFrame = null;
    this._nextValue = null;
    this._nextIndex = null;

    this.state = {
      disabled: props.disabled,
      accessible: !!props.accessible,
      loading: props.options === null || props.options === undefined,
      showDropdown: false,
      buttonText: props.defaultValue,
      selectedIndex: props.defaultIndex
    };
  }

  componentWillReceiveProps(nextProps) {
    var buttonText = this._nextValue == null ? this.state.buttonText : this._nextValue.toString();
    var selectedIndex = this._nextIndex == null ? this.state.selectedIndex : this._nextIndex;
    if (selectedIndex < 0) {
      selectedIndex = nextProps.defaultIndex;
      if (selectedIndex < 0) {
        buttonText = nextProps.defaultValue;
      }
    }
    this._nextValue = null;
    this._nextIndex = null;

    this.setState({
      disabled: nextProps.disabled,
      loading: nextProps.options == null,
      buttonText: buttonText,
      selectedIndex: selectedIndex
    });
  }

  render() {
    return (
      <View {...this.props} style={{ flex: 1 }}>
        {this._renderButton()}
        {this._renderModal()}
      </View>
    );
  }

  // _updatePosition(callback) {
  //   if (this._button && this._button.measure) {
  //     this._button.measure((fx, fy, width, height, px, py) => {
  //       this._buttonFrame = {fx, fy, width, height, px, py};
  //       callback && callback();
  //     });
  //   }
  // }

  show() {
    // this._updatePosition(() => {
      this.setState({
        showDropdown: true
      });
    // });
  }

  hide() {
    this.setState({
      showDropdown: false
    });
  }

  select(idx) {
    var value = this.props.defaultValue;
    if (idx == null || this.props.options == null || idx >= this.props.options.length) {
      idx = this.props.defaultIndex;
    }

    if (idx >= 0) {
      const option = this.props.options[idx];
      if (typeof option === 'object') {
        value = option.text;
      } else {
        value = option.toString();
      }
    }

    this._nextValue = value;
    this._nextIndex = idx;

    this.setState({
      buttonText: value,
      selectedIndex: idx
    });
  }

  _renderButton() {
    return (
      <TouchableOpacity ref={button => this._button = button}
                        disabled={this.props.disabled}
                        accessible={this.props.accessible}
                        onPress={this._onButtonPress.bind(this)}
                        style={this.props.tabStyle}
      >
        {this._renderButtonChild()}
      </TouchableOpacity>
    );
  }
  _renderButtonChild() {
    if (this.props.children) {
        const c = React.cloneElement(this.props.children, { buttonText: this.state.buttonText });
        return c;
    }
    return (
        <View style={this.props.tabStyle}>
            <Text 
                style={[styles.buttonText, this.props.textStyle]}
                numberOfLines={1}
            >
                {this.state.buttonText}
            </Text>
        </View>
    );
  }

  _onButtonPress() {
    if (!this.props.onDropdownWillShow ||
      this.props.onDropdownWillShow() !== false) {
      this.show();
    }
  }

  _renderModal() {
    if (this.state.showDropdown) {
      const frameStyle = this._calcPosition();
      const animationType = this.props.animated ? 'fade' : 'none';
      return (
        <Modal 
               animationType={animationType}
               visible={true}
               transparent={true}
               onRequestClose={this._onRequestClose.bind(this)}
               supportedOrientations={['portrait', 'portrait-upside-down', 'landscape', 'landscape-left', 'landscape-right']}>
          <TouchableWithoutFeedback 
                                    accessible={this.props.accessible}
                                    disabled={!this.state.showDropdown}
                                    onPress={this._onModalPress.bind(this)}
          >
            <View style={styles.modal}>
              <View style={[styles.dropdownStyle, this.props.dropdownStyle, frameStyle]}>
                {this.state.loading ? this._renderLoading() : this._renderDropdown()}
              </View>
            </View>
          </TouchableWithoutFeedback>
        </Modal>
      );
    }
  }

  _calcPosition() {
    const { x, y } = this.props.layoutDropdown;

    // const dimensions = Dimensions.get('window');
    // const windowWidth = dimensions.width;
    // const windowHeight = dimensions.height;

    // const dropdownHeight = (this.props.dropdownStyle && StyleSheet.flatten(this.props.dropdownStyle).height) ||
    //   StyleSheet.flatten(styles.dropdown).height;

    // const bottomSpace = windowHeight - this._buttonFrame.y - this._buttonFrame.h;
    // const rightSpace = windowWidth - this._buttonFrame.x;
    // const showInBottom = bottomSpace >= dropdownHeight || bottomSpace >= this._buttonFrame.y;
    // const showInLeft = rightSpace >= this._buttonFrame.x;

    let style = {
      // height: dropdownHeight,
      // top: showInBottom ? this._buttonFrame.y + this._buttonFrame.h : Math.max(0, this._buttonFrame.y - dropdownHeight),
      top: y,
      left: x
    };

    // if (showInLeft) {
    //   style.left = this._buttonFrame.x;
    // } else {
    //   const dropdownWidth = (this.props.dropdownStyle && StyleSheet.flatten(this.props.dropdownStyle).width) ||
    //     (this.props.style && StyleSheet.flatten(this.props.style).width) || -1;
    //   if (dropdownWidth !== -1) {
    //     style.width = dropdownWidth;
    //   }
    //   style.right = rightSpace - this._buttonFrame.w;
    // }

    // if (this.props.adjustFrame) {
    //   style = this.props.adjustFrame(style) || style;
    // }

    return style;
  }

  _onRequestClose() {
    if (!this.props.onDropdownWillHide ||
      this.props.onDropdownWillHide() !== false) {
      this.hide();
    }
  }

  _onModalPress() {
    if (!this.props.onDropdownWillHide ||
      this.props.onDropdownWillHide() !== false) {
      this.hide();
    }
  }

  _renderLoading() {
    return (
      <ActivityIndicator size='small'/>
    );
  }

  _renderDropdown() {
    return (
      <ListView style={styles.list}
                dataSource={this._dataSource}
                renderRow={this._renderRow.bind(this)}
                renderSeparator={this.props.renderSeparator || this._renderSeparator.bind(this)}
                automaticallyAdjustContentInsets={false}
                showsVerticalScrollIndicator={this.props.showsVerticalScrollIndicator}
      />
    );
  }

  get _dataSource() {
    let ds = new ListView.DataSource({
      rowHasChanged: (r1, r2) => r1 !== r2
    });
    return ds.cloneWithRows(this.props.options);
  }

  _renderRow(rowData, sectionID, rowID, highlightRow) {
    const key = `row_${rowID}`;
    const highlighted = rowID == this.state.selectedIndex;

    return (
      <TouchableOpacity key={key} accessible={this.props.accessible} onPress={() => this._onRowPress(rowData, sectionID, rowID, highlightRow)}>
        <View 
          style={[styles.rowStyle, { width: this.props.layoutDropdown.width }, highlighted && styles.hightlightedRowStyle]}
        >
          <Text style={[styles.dropdownText, highlighted && styles.highlightedRowText]}>{rowData}</Text>
        </View>
      </TouchableOpacity>
    );
  }

  _onRowPress(rowData, sectionID, rowID, highlightRow) {
    if (!this.props.onSelect ||
      this.props.onSelect(rowID, rowData) !== false) {
      highlightRow(sectionID, rowID);
      this._nextValue = rowData;
      this._nextIndex = rowID;
      this.setState({
        buttonText: rowData.toString(),
        selectedIndex: rowID
      });
    }
    if (!this.props.onDropdownWillHide ||
      this.props.onDropdownWillHide() !== false) {
      this.setState({
        showDropdown: false
      });
    }
  }

  _renderSeparator(sectionID, rowID, adjacentRowHighlighted) {
    let key = `spr_${rowID}`;
    return (<View style={styles.separator}
                  key={key}
    />);
  }
}

const styles = StyleSheet.create({
  // the same as TabBarDropdown styles.tab
  // tab: {
  //   flex: 1,
  //   alignItems: 'center',
  //   justifyContent: 'center',
  //   paddingBottom: 10,
  // },
  buttonText: {
    fontSize: 12
  },
  modal: {
    flexGrow: 1,
    backgroundColor: Colors.modalBackgroundColor,
  },
  dropdownStyle: {
    position: 'absolute',
    borderWidth: StyleSheet.hairlineWidth,
    borderColor: Colors.blackBorder,
    borderRadius: 5,
    backgroundColor: Colors.dropdownBackgroundColor,
    justifyContent: 'center'
  },
  loading: {
    alignSelf: 'center'
  },
  list: {
    // flexGrow: 1
  },
  rowStyle: {
    width: 100, // 預設dropdown寬度 -> 會被一個tab的寬度覆蓋掉
    height: 40, // 個別一個option的高度
    flexDirection: 'row', 
    justifyContent: 'center', 
    alignItems: 'center'
  },
  hightlightedRowStyle: {
    backgroundColor: Colors.dropdownHightlightBackgroundColor
  },
  dropdownText: {
    paddingHorizontal: 6,
    paddingVertical: 10,
    fontSize: 12,
    color: Colors.white
  },
  highlightedRowText: {

  },
  separator: {
    height: StyleSheet.hairlineWidth,
    backgroundColor: Colors.blackBorder
  }
});
