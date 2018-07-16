/**
 * Todo:
 * console.time('name);
 * console.timeEnd('name')
 * https://developer.mozilla.org/en-US/docs/Web/API/Console/time
 */
import moment from 'moment';
import { Config, Colors } from '../global';

export default class Logger {
    // 其實clazz在function typeof Foo === 'function'
    name = '';
    level = Config.logLevel
    Level = {
        TRACE: 0,   // For mobx
        DEBUG: 1,   // 等於 ON，全開
        INFO: 2,
        WARN: 3,
        ERROR: 4,
        OFF: 99
    }
    constructor(clazz, level) {
        this.name = clazz;
        if (typeof clazz === 'function') {
            this.name = clazz.name;
        }

        level && (this.level = level);
    }
    setLevel(level) {
        this.level = level;
    }
    trace(msg) {
        if (this.level <= this.Level.TRACE) {
            this.log(msg, 'Trace', Colors.success);
        }
    }
    debug(msg) {
        if (this.level <= this.Level.DEBUG) {
            this.log(msg, 'Debug', Colors.primary);
        }
    }
    info(msg) {
        if (this.level <= this.Level.INFO) {
            this.log(msg, 'Info', Colors.info);
        }
    }
    warn(msg) {
        if (this.level <= this.Level.WARN) {
            this.log(msg, 'Warn', Colors.warning);
        }
    }
    error(msg) {
        if (this.level <= this.Level.ERROR) {
            this.log(msg, 'Error', Colors.danger);
        }
    }
    log(msg, type, color) {
        if (Config.logShowTime) {
            console.log(`%c${this.name} - ${type} - ${moment().format('YYYY-MM-DD, HH:mm:ss')} - ${msg}`, `color: ${color}; background: ${Colors.grey}`);
            return;
        }
        console.log(`%c${this.name} - ${type} - ${msg}`, `color: ${color}; background: ${Colors.grey}`);
    }
    // todo: pattern, formatter
}
