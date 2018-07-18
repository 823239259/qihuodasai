import React, { Component } from 'react';
import { Keyboard } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import _ from 'lodash';
import Icon from 'react-native-vector-icons/FontAwesome';
import HandicapView from './HandicapView';
import TimeView from './TimeView';
import KView from './KView';
import LightningView from './LightningView';
import TradeView from './TradeView';
import { Colors, Variables, Enum, NavigatorStyle } from '../../../global';
import { TabBarDropdown } from '../../../components';
import { SafeAreaView } from '../../../containments';
import { WorkbenchDetail } from './components';
import { ScreenNavigatorMonitor } from '../../../hoc'; 
import { Logger, I18n } from '../../../utils';

@inject('QuotationSocket', 'QuotationDetailStore', 'WorkbenchDetailStore', 'TradeHoldPositionStore') @observer
class QuotationDetailScreen extends Component {
  static screenName = Enum.screen.QuotationDetailScreen;
  static isMonitorAll = true;

  constructor(props) {
    super(props);
    this.logger = new Logger(QuotationDetailScreen);

    this.tabView = null;
    this.dropdownOptions = [];
    _.forOwn(Enum.kTypes, (v, k) => {
      this.dropdownOptions.push(v.text);
    });
  }
  componentWillMount() {
    const { QuotationSocket } = this.props;
    QuotationSocket.startDetail(this.props.commodityNo, this.props.contractNo, this.props.navigator);
  }
  componentDidMount() {
    const { WorkbenchDetailStore, TradeHoldPositionStore, QuotationDetailStore } = this.props;
    WorkbenchDetailStore.init(this.props.navigator, this.tabView);
    TradeHoldPositionStore.init(this.props.navigator);
    QuotationDetailStore.setDetailCurrentTab(Enum.detail.startPageIndex);
  }
  componentWillUnmount() {
    const { QuotationSocket, QuotationDetailStore } = this.props;
    QuotationSocket.resetDetail();
    QuotationDetailStore.currentKType = null;
    this.props.WorkbenchDetailStore.clear(this.props.navigator, this.tabView);
  }
  onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
    if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
      if (event.id === Variables.icon.bars.id) { // this is the same id field from the static navigatorButtons definition
        this._toggleDrawer();
      }
    }
  }
  _beforeGoTo(page) {
    const { WorkbenchDetailStore, QuotationDetailStore } = this.props;
    QuotationDetailStore.setDetailCurrentTab(page);
    WorkbenchDetailStore.toggleTradeNum(page);
    WorkbenchDetailStore.toggleTradeLast(page);
    Keyboard.dismiss();
  }
  _toggleDrawer() {
    this.props.navigator.toggleDrawer({
      side: 'right',
      animated: true
    });
  }
  render() {
    const { WorkbenchDetailStore } = this.props;
    return (
      <SafeAreaView>
        <WorkbenchDetail 
          style={{ flex: 1, backgroundColor: Colors.bg }}
        >
          <ScrollableTabView 
            style={{ flex: 1 }}
            locked={true}
            scrollWithoutAnimation={true}
            ref={(tabView) => { this.tabView = tabView; }}
            renderTabBar={() => <TabBarDropdown
                                  initialPage={Enum.detail.startPageIndex}
                                  dropdownPage={Enum.detail.pageIndex.kPage}
                                  dropdownText={I18n.term('k_view')}
                                  dropdownOptions={this.dropdownOptions}
                                  modalPage={Enum.detail.pageIndex.tradePage}
                                  showModal={() => WorkbenchDetailStore.showTradeLoginDialog()}
                                  beforeGoTo={(page) => this._beforeGoTo(page)}
                                />
                          }
          >
            <LightningView tabLabel={I18n.term('lightning_view')} />
            <HandicapView tabLabel={I18n.term('handicap')} />
            <TimeView tabLabel={I18n.term('time_view')} />
            <KView tabLabel={I18n.term('k_view')} /> 
            <TradeView tabLabel={I18n.term('trade_view')} />
          </ScrollableTabView>
        </WorkbenchDetail>
      </SafeAreaView>
    );
  }
}
{/* <LightningView tabLabel={I18n.term('lightning_view')} />
<HandicapView tabLabel={I18n.term('handicap')} />
<TimeView tabLabel={I18n.term('time_view')} />
<KView tabLabel={I18n.term('k_view')} /> 
*/}
const coQuotationDetailScreen = ScreenNavigatorMonitor(QuotationDetailScreen);
coQuotationDetailScreen.navigatorStyle = {
  ...NavigatorStyle.screenInnerStyle,
  navBarCustomView: 'quotation.QuotationDetailHeader',
  navBarComponentAlignment: 'center',
  
};
export default coQuotationDetailScreen;
