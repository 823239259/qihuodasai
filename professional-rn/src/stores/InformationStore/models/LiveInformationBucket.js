/**
 * It's a data structure
 * 1. Array - store dateString: 2017-12-01
 * 2. LiveInformation
 */
import { observable } from 'mobx';
import moment from 'moment';
import LiveInformation from './LiveInformation';

export default class LiveInformationBucket {
    @observable count = 0;  // 計算目前有幾筆資料，並且observable
    @observable bucket = [];
    hashMap = {};

    insert(data) {
        this.count++;
        const dateTimeString = moment(data.createdAt * 1000).format('YYYY-MM-DD HH:mm');
        const dateTimeStringArr = dateTimeString.split(' ');
        const dateString = dateTimeStringArr[0];
        const timeString = dateTimeStringArr[1];

        const index = this.bucket.indexOf(dateString);
        if (index === -1) {
            this.bucket.push(dateString);
            this.hashMap[dateString] = [new LiveInformation(timeString, data.liveTitle)];
        } else {
            this.hashMap[this.bucket[index]].push(new LiveInformation(timeString, data.liveTitle));
        }
    }
}
