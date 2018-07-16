/**
 * 資訊直播
 * 
 * 實際上不是當天的資料
 */
import { observable, action, computed, autorun } from 'mobx';
import _ from 'lodash';
import moment from 'moment';
import { Enum, Config, Variables } from '../../global';
import { ToastRoot } from '../../components';
import { Logger, DateUtil, Api } from '../../utils';
import LiveInformation from './models/LiveInformation';

export default class InformationStore {
    @observable refreshing = true;
    @observable liveInformations = [];

    @computed get reverseInformations() {
        return this.liveInformations.reverse();
    }
    liveInformationIndex = 0;
    count = -1;
    constructor() {
        this.getLiveInformation();
    }
    @action getLiveInformation() {
        this.refreshing = true;
        this.count++;
        Api.getLiveInformation(this.count, (result) => this.pushLiveInformation(result));
    }
    @action.bound pushLiveInformation(result) {
        result.data.data.forEach(d => {
            this.liveInformationIndex++;
            const dateTimeStringArr = moment(d.createdAt * 1000).format('YYYY-MM-DD HH:mm').split(' ');
            const dateString = dateTimeStringArr[0];
            const timeString = dateTimeStringArr[1];
            this.liveInformations.push(new LiveInformation(this.liveInformationIndex, dateString, timeString, d.liveTitle));
        });
        this.refreshing = false;
    }
}

