/**
 * Configuration options: https://docs.bugsnag.com/platforms/react-native/configuration-options/
 */
import { Client, Configuration } from 'bugsnag-react-native';
import { Logger } from '../utils';

let _client;
export function registerBugsnag() {
    return new Promise(resolve => {
        const logger = new Logger('Bugsnag');
        logger.trace('Bugsnag');
        const configuration = new Configuration();
        // configuration.apiKey = 'b6d611678a12a22f7959f9a639b2ef5b';
        // const client = new Client(configuration);
        configuration.apiKey = 'd7d72b3c43a41d7ed7be9611ea1bbf77';
        _client = new Client(configuration);

        resolve(true);
    });
}

export function bugNotify (str) {
    _client.notify(new Error(str));
}
