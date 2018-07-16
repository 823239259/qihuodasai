import moment from 'moment';
import _ from 'lodash';

function formatDate(date) {
    return date.format('YYYY-MM-DD 00:00:00');
}
export default {
    getToday() {
        return formatDate(moment());
    },
    getYesterday() {
        return formatDate(moment().subtract(1, 'days'));
    },
    getLastWeek() {
        return formatDate(moment().subtract(7, 'days'));
    },
    getLastMonth() {
        return formatDate(moment().subtract(30, 'days'));
    }
};
