 //控制显示与否的模块
export default class Account {
    constructor () {
        this.state = {
            packChannel: '',   //统计代码
            islogin: false, //是否登录
            phone: '', //账户
            password: '', //密码 
            token: '',
            secret: '',
            isCertification: false, //是否实名认证
            username: '', //实名
            balance: 0.00, //余额
            operateMoney: 0.00, //免提现手续费额度
            bankList: [], //已绑定银行卡信息
            //存不知道有用没的数据
            tempList: [],
            //存合约列表
            programList: [],
            operateOrderLength: 0,   //操盘中方案的条数
            hotLine: '',
            currentUrlHead: 'http:', //http or https
            realName: '',
            userVerified: false,
        }
    }
   
 }
