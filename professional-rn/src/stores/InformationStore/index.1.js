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
import LiveInformationBucket from './models/LiveInformationBucket';

export default class InformationStore {
    @observable refreshing = false;
    liveInformationBucket = new LiveInformationBucket();

    @computed get getInformationBucket() {
        // console.log('get liveInformations');
        // console.log(this.liveInformationBucket.count);
        // console.log(this.liveInformationBucket.hashMap);
        return this.liveInformationBucket;
    }
    constructor() {
        this.getLiveInformation();
    }
    @action getLiveInformation() {
        Api.getLiveInformation(this.count, (result) => this.pushLiveInformation(result));
    }
    @action.bound pushLiveInformation(result) {
        result.data.data.forEach(d => {
            // this.liveInformations.push(d);
            this.liveInformationBucket.insert(d);
            // this.liveInformations.insert(d);
            // console.log(d);
            // console.log(d.liveCreatetime);
            // console.log(d.createdAt);
            // const dateTimeStringArr = moment(d.createdAt * 1000).format('YYYY-MM-DD HH:mm').split(' ');
            // const dateString = dateTimeStringArr[0];
            // const timeString = dateTimeStringArr[1];
            // console.log(dateString);
            // console.log(timeString);


            // const infoObject = this.liveInformations.find(info => {
            //     return info.key === dateString;
            // });
            // if (infoObject) {
            //     infoObject 
            // } else {
            //     const newInfoObject = { dateString: [] };
            //     newInfoObject.push(new LiveInformation(timeString, d.liveTitle));
            // }
        });
    }
}

