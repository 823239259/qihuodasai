import { observable, computed, action } from 'mobx';

export default class FutureTypeStore {
    @observable isFutIn = true // 内外盘的标识

    @computed get Futstring () {
        return this.isFutIn?"FUT_IN":"FUT_OUT"
    }
    @action changeFutIn () {
        this.isFutIn = !this.isFutIn
    }
}