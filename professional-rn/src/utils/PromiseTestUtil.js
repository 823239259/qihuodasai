// https://javascript.info/promise-chaining
import Logger from './Logger';

export default class PromiseTestUtil {
    logger = null;
    constructor() {
        this.logger = new Logger(PromiseTestUtil);
    }
    resolveAfter3Seconds(x) {
        return new Promise((resolve) => {
          setTimeout(() => {
            resolve(x);
          }, 3000);
        });
    }
    resolveAfter5Seconds(x) {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve(x);
            }, 5000);
        });
    }
    async addAsync(x) {
        const a = await this.resolveAfter3Seconds(30);
        const b = await this.resolveAfter5Seconds(50);
        return x + a + b;
    }
    addPromise(x) {
        return new Promise(resolve => {
        Promise.all([
            this.resolveAfter3Seconds(30),
            this.resolveAfter5Seconds(50),
        ]).then(values => {
            // console.log(values[0]);
            // console.log(values[1]);
            resolve(x + values[0] + values[1]);
        });
        });
    }
    printSecond() {
        let count = 0;
        setInterval(() => {
          count++;
        //   this.logger.error(count);
            console.log(count);
        }, 1000);
    }
}
