import { observable, computed } from 'mobx';

export default class BankCard {
    id;                     // ff8080816063f3e101606858fc5c0086
    name;                   // 中国工商银行
    @observable card;       // 55555555555
    uid;                    // 使用者id: ff8080815d123c9a015d1311b01c0002
    abbreviation;           // icbc
    @observable selected;   // 是否為當前選擇銀行卡 defaults
    bankImage;
    
    constructor(bankId, bankName, card, uid, abbreviation, selected) {
        this.id = bankId;
        this.name = bankName;
        this.card = card;
        this.uid = uid;
        this.abbreviation = abbreviation;
        this.selected = selected;
        this.setBankImage(abbreviation);
    }
    setBankImage(abbreviation) {
        if (abbreviation === 'abc') {
            this.bankImage = require('../../../../img/bank/abc.png');
        } else if (abbreviation === 'bjb') {
            this.bankImage = require('../../../../img/bank/bjb.png');
        } else if (abbreviation === 'boc') {
            this.bankImage = require('../../../../img/bank/boc.png');
        } else if (abbreviation === 'ccb') {
            this.bankImage = require('../../../../img/bank/ccb.png');
        } else if (abbreviation === 'ceb') {
            this.bankImage = require('../../../../img/bank/ceb.png');
        } else if (abbreviation === 'cib') {
            this.bankImage = require('../../../../img/bank/cib.png');
        } else if (abbreviation === 'citic') {
            this.bankImage = require('../../../../img/bank/citic.png');
        } else if (abbreviation === 'cmb') {
            this.bankImage = require('../../../../img/bank/cmb.png');
        } else if (abbreviation === 'cmbc') {
            this.bankImage = require('../../../../img/bank/cmbc.png');
        } else if (abbreviation === 'comm') {
            this.bankImage = require('../../../../img/bank/comm.png');
        } else if (abbreviation === 'gdb') {
            this.bankImage = require('../../../../img/bank/gdb.png');
        } else if (abbreviation === 'hxb') {
            this.bankImage = require('../../../../img/bank/hxb.png');
        } else if (abbreviation === 'icbc') {
            this.bankImage = require('../../../../img/bank/icbc.png');
        } else if (abbreviation === 'psbc') {
            this.bankImage = require('../../../../img/bank/psbc.png');
        } else if (abbreviation === 'spdb') {
            this.bankImage = require('../../../../img/bank/spdb.png');
        }
    }
}
