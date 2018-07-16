export default class LiveInformation {
    index;
    dateString;
    timeString;
    liveTitle ;

    constructor(index, dateString, timeString, liveTitle) {
        this.index = index;
        this.dateString = dateString;
        this.timeString = timeString;
        this.liveTitle = liveTitle;
    }
}
