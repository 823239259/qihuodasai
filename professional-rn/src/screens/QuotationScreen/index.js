/* 
  行情交易
*/
import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Loading, SearchInputField } from '../../components';
import RowSwipeoutContainer from './RowSwipeoutContainer';
import FocusedGridContainer from './FocusedGridContainer';
import HeaderSort from './HeaderSort';
import { ScreenNavigatorMonitor } from '../../hoc';
import { Layout, Variables, Enum } from '../../global';
import { Logger } from '../../utils';
import RootNavi from '../../app/RootNavi';

@inject('QuotationStore', 'DrawerTradeStore') @observer
class QuotationScreen extends Component {
  static screenName = Enum.screen.QuotationScreen;
  static isMonitorAll = false;

  constructor(props) {
    super(props);

    this.state = {
      isSearch: false
    };
    this.props.navigator.setButtons({
      //leftButtons: [{ id: Variables.icon.question.id, icon: Variables.icon.question.source }],
      rightButtons: [{ id: Variables.icon.search.id, icon: Variables.icon.search.source }]
    });

    this.first = true;
    this.logger = new Logger(QuotationScreen);
    RootNavi.setRootNavi(this.props.navigator);
  }
  onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
    if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
      if (event.id === Variables.icon.search.id) { // this is the same id field from the static navigatorButtons definition
        this._toggleSeach();
      } else if (event.id === Variables.icon.question.id) {
        this._toAbout();
      }
    }
  }
  _toAbout() {
    // this.props.navigator.showModal({
    //   screen: 'quotation.QuotationAboutModal',
    //   title: '关于',
    //   navigatorButtons: {
    //     leftButtons: [
    //         {
    //             icon: Variables.icon.closeAbout.source,
    //             id: Variables.icon.closeAbout.id
    //         }
    //     ]
    //   }
    // }); 
  }
  _toggleSeach() {
    // const to = this.state.isSearch ? 'hidden' : 'shown';
    // this.props.navigator.toggleNavBar({
    //   to,
    //   animated: true
    // });
    this.setState({ isSearch: !this.state.isSearch });
  }
  _getLayout = (e) => {
    if (!this.first) {
      return;
    }
    const { layout } = e.nativeEvent;
    Layout.contentHeight = layout.height;
    Layout.contentWithBottomHeight = Layout.contentHeight + Layout.bottomHeight;
  }
  _renderContent() {
    const { QuotationStore, navigator } = this.props;
    if (QuotationStore.isLoading) {
      return <Loading />;
    }
    return (
      <View style={{ flex: 1 }}>
          <FocusedGridContainer navigator={navigator} />
          { this.state.isSearch && <SearchInputField placeHolder={'合约名称/代码'} store={QuotationStore.searchStore} style={{ margin: Layout.inset }} /> }
          <HeaderSort />
          <RowSwipeoutContainer navigator={navigator} />
      </View>
    );
  }
  render() {
    return (
        <View 
          style={{ flex: 1 }} 
          onLayout={this._getLayout}
        >
          { this._renderContent() }
        </View>
    );
  }
}
export default ScreenNavigatorMonitor(QuotationScreen);
