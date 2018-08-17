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
            withdrawCode: { //提现code码表
                "-1":"token错误或为空",
                "1": "新增成功",
                "2":"提现失败",
                "3":"用户信息不存在",
                "4":"银行卡错误",
                "5":"存在欠费无法提现",
                "6":"系统升级期间无法提现",
                "7":"余额不足不能提现",
                "8":"当天取款次数不能超过5次",
                "9":"每次提现金额不能小于10元",
                "10":"提现密码错误",
                "11":"网银平台暂不支持此银行提现，请添加其他银行！",
                "12":"网银限制，单笔提现金额不能超过50000元，每天可提现5笔（币币支付）。",
                "13":"未实名认证",
                '14':"网银限制，单笔提现金额不能超过500000元，每天可提现5笔。",
                "15":"提现渠道设置参数错误",
                "16":"未设置提现密码"

            }
        }
    }
   
 }
