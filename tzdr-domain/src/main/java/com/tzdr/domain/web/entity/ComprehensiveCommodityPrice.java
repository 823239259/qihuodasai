package com.tzdr.domain.web.entity;

import com.tzdr.common.domain.BaseCrudEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 商品综合
 */
@Entity
@Table(name="w_comprehensive_commodity_price")
public class ComprehensiveCommodityPrice extends BaseCrudEntity {

    /**
     * 交易品种
     * 业务类型【0:白银 1：铝 2：黄金 3：沥青 4：铜 5：热卷 6：镍
     * 7：螺纹钢 8：锌 9：橡胶 10：豆一 11：玉米 12：玉米淀粉 13：铁矿石
     * 14：焦炭 15：鸡蛋 16：焦煤 17：塑料 18：豆粕 19：棕榈油 20：聚丙烯
     * 21：豆油 22：棉花 23：玻璃 24：甲醇 25：菜油 26：菜粕 27 白糖 28：PTA
     * 29：动力煤 30:5年期国债 31： 10年期国债 】
     */
    private Integer tradeType;

    /**
     * 主力合约
     */
    private String mainContract;

    /**
     * 价格
     */
    private BigDecimal price;
    
    /**
     * 交易所
     */
    private String exchange;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getMainContract() {
        return mainContract;
    }

    public void setMainContract(String mainContract) {
        this.mainContract = mainContract;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
