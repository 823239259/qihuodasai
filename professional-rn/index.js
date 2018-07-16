import { Config } from './src/global';
import App from './src/app';
import AppTest from './srcTest/app';

if (Config.mock) {
    new AppTest();
} else {
    new App();
}
