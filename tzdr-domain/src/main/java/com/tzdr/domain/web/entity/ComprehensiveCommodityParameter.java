package com.tzdr.domain.web.entity;

import com.tzdr.common.domain.BaseCrudEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @description 商品综合参数设置
 * @Author huangkai
 * @Date 2016-03-28
 */
@Entity
@Table(name = "w_comprehensive_commodity_parameter")
public class ComprehensiveCommodityParameter extends BaseCrudEntity {

    /**
     * 保证金额(￥)
     */
    private BigDecimal traderBond;

    /**
     * 总操盘资金($)
     */
    private BigDecimal traderTotal;

    /**
     * 亏损平仓线($)
     */
    private BigDecimal lineLoss;

    /**
     * 入金金额($)
     */
    private BigDecimal goldenMoney;

    /**
     * 白银手数
     */
    private Integer silverLever;

    /**
     * 铝手数
     */
    private Integer aluminumLever;

    /**
     * 黄金手数
     */
    private Integer goldLever;

    /**
     * 沥青手数
     */
    private Integer asphaltLever;

    /**
     * 铜手数
     */
    private Integer copperLever;

    /**
     * 热卷手数
     */
    private Integer coilLever;

    /**
     * 镍手数
     */
    private Integer nickelLever;

    /**
     * 螺纹钢手数
     */
    private Integer rebarLever;
    /**
     * 锌手数
     */
    private Integer zincLever;

    /**
     * 橡胶手数
     */
    private Integer rubberLever;

    /**
     * 豆一手数
     */
    private Integer beanLever;
    /**
     * 玉米手数
     */
    private Integer cornLever;

    /**
     * 玉米淀粉手数
     */
    private Integer cornStarchLever;

    /**
     * 铁矿石手数
     */
    private Integer ironOreLever;

    /**
     * 焦炭手数
     */
    private Integer cokeLever;

    /**
     * 鸡蛋手数
     */
    private Integer eggLever;

    /**
     * 焦煤手数
     */
    private Integer cokingCoalLever;

    /**
     * 塑料手数
     */
    private Integer plasticLever;

    /**
     * 豆粕手数
     */
    private Integer soybeanMealLever;
    /**
     * 棕榈油 手数
     */
    private Integer palmOilLever;

    /**
     *聚丙烯手数
     */
    private Integer polypropyleneLever;


    /**
     *豆油手数
     */
    private Integer soybeanOilLever;

    /**
     *棉花手数
     */
    private Integer cottonLever;

    /**
     *玻璃手数
     */
    private Integer glassLever;

    /**
     *甲醇手数
     */
    private Integer methanolLever;

    /**
     *菜油手数
     */
    private Integer rapeOilLever;

    /**
     *菜粕手数
     */
    private Integer rapeseedMealLever;

    /**
     *白糖手数
     */
    private Integer sugarLever;

    /**
     *PTA手数
     */
    private Integer pTALever;

    /**
     *动力煤手数
     */
    private Integer powerCoalLever;

    /**
     *5年期国债
     */
    private Integer fiveNationalDebtLever;

    /**
     *十年期国债
     */
    private Integer tenNationalDebtLever;

    public BigDecimal getTraderBond() {
        return traderBond;
    }

    public void setTraderBond(BigDecimal traderBond) {
        this.traderBond = traderBond;
    }

    public BigDecimal getTraderTotal() {
        return traderTotal;
    }

    public void setTraderTotal(BigDecimal traderTotal) {
        this.traderTotal = traderTotal;
    }

    public BigDecimal getLineLoss() {
        return lineLoss;
    }

    public void setLineLoss(BigDecimal lineLoss) {
        this.lineLoss = lineLoss;
    }

    public BigDecimal getGoldenMoney() {
        return goldenMoney;
    }

    public void setGoldenMoney(BigDecimal goldenMoney) {
        this.goldenMoney = goldenMoney;
    }

    public Integer getSilverLever() {
        return silverLever;
    }

    public void setSilverLever(Integer silverLever) {
        this.silverLever = silverLever;
    }

    public Integer getAluminumLever() {
        return aluminumLever;
    }

    public void setAluminumLever(Integer aluminumLever) {
        this.aluminumLever = aluminumLever;
    }

    public Integer getGoldLever() {
        return goldLever;
    }

    public void setGoldLever(Integer goldLever) {
        this.goldLever = goldLever;
    }

    public Integer getAsphaltLever() {
        return asphaltLever;
    }

    public void setAsphaltLever(Integer asphaltLever) {
        this.asphaltLever = asphaltLever;
    }

    public Integer getCopperLever() {
        return copperLever;
    }

    public void setCopperLever(Integer copperLever) {
        this.copperLever = copperLever;
    }

    public Integer getCoilLever() {
        return coilLever;
    }

    public void setCoilLever(Integer coilLever) {
        this.coilLever = coilLever;
    }

    public Integer getNickelLever() {
        return nickelLever;
    }

    public void setNickelLever(Integer nickelLever) {
        this.nickelLever = nickelLever;
    }

    public Integer getRebarLever() {
        return rebarLever;
    }

    public void setRebarLever(Integer rebarLever) {
        this.rebarLever = rebarLever;
    }

    public Integer getZincLever() {
        return zincLever;
    }

    public void setZincLever(Integer zincLever) {
        this.zincLever = zincLever;
    }

    public Integer getBeanLever() {
        return beanLever;
    }

    public void setBeanLever(Integer beanLever) {
        this.beanLever = beanLever;
    }

    public Integer getCornLever() {
        return cornLever;
    }

    public void setCornLever(Integer cornLever) {
        this.cornLever = cornLever;
    }

    public Integer getCornStarchLever() {
        return cornStarchLever;
    }

    public void setCornStarchLever(Integer cornStarchLever) {
        this.cornStarchLever = cornStarchLever;
    }

    public Integer getIronOreLever() {
        return ironOreLever;
    }

    public void setIronOreLever(Integer ironOreLever) {
        this.ironOreLever = ironOreLever;
    }

    public Integer getCokeLever() {
        return cokeLever;
    }

    public void setCokeLever(Integer cokeLever) {
        this.cokeLever = cokeLever;
    }

    public Integer getEggLever() {
        return eggLever;
    }

    public void setEggLever(Integer eggLever) {
        this.eggLever = eggLever;
    }

    public Integer getCokingCoalLever() {
        return cokingCoalLever;
    }

    public void setCokingCoalLever(Integer cokingCoalLever) {
        this.cokingCoalLever = cokingCoalLever;
    }

    public Integer getPlasticLever() {
        return plasticLever;
    }

    public void setPlasticLever(Integer plasticLever) {
        this.plasticLever = plasticLever;
    }

    public Integer getSoybeanMealLever() {
        return soybeanMealLever;
    }

    public void setSoybeanMealLever(Integer soybeanMealLever) {
        this.soybeanMealLever = soybeanMealLever;
    }

    public Integer getPalmOilLever() {
        return palmOilLever;
    }

    public void setPalmOilLever(Integer palmOilLever) {
        this.palmOilLever = palmOilLever;
    }

    public Integer getPolypropyleneLever() {
        return polypropyleneLever;
    }

    public void setPolypropyleneLever(Integer polypropyleneLever) {
        this.polypropyleneLever = polypropyleneLever;
    }

    public Integer getSoybeanOilLever() {
        return soybeanOilLever;
    }

    public void setSoybeanOilLever(Integer soybeanOilLever) {
        this.soybeanOilLever = soybeanOilLever;
    }

    public Integer getCottonLever() {
        return cottonLever;
    }

    public void setCottonLever(Integer cottonLever) {
        this.cottonLever = cottonLever;
    }

    public Integer getGlassLever() {
        return glassLever;
    }

    public void setGlassLever(Integer glassLever) {
        this.glassLever = glassLever;
    }

    public Integer getMethanolLever() {
        return methanolLever;
    }

    public void setMethanolLever(Integer methanolLever) {
        this.methanolLever = methanolLever;
    }

    public Integer getRubberLever() {
        return rubberLever;
    }

    public void setRubberLever(Integer rubberLever) {
        this.rubberLever = rubberLever;
    }

    public Integer getRapeOilLever() {
        return rapeOilLever;
    }

    public void setRapeOilLever(Integer rapeOilLever) {
        this.rapeOilLever = rapeOilLever;
    }

    public Integer getRapeseedMealLever() {
        return rapeseedMealLever;
    }

    public void setRapeseedMealLever(Integer rapeseedMealLever) {
        this.rapeseedMealLever = rapeseedMealLever;
    }

    public Integer getSugarLever() {
        return sugarLever;
    }

    public void setSugarLever(Integer sugarLever) {
        this.sugarLever = sugarLever;
    }

    public Integer getpTALever() {
        return pTALever;
    }

    public void setpTALever(Integer pTALever) {
        this.pTALever = pTALever;
    }

    public Integer getPowerCoalLever() {
        return powerCoalLever;
    }

    public void setPowerCoalLever(Integer powerCoalLever) {
        this.powerCoalLever = powerCoalLever;
    }

    public Integer getFiveNationalDebtLever() {
        return fiveNationalDebtLever;
    }

    public void setFiveNationalDebtLever(Integer fiveNationalDebtLever) {
        this.fiveNationalDebtLever = fiveNationalDebtLever;
    }

    public Integer getTenNationalDebtLever() {
        return tenNationalDebtLever;
    }

    public void setTenNationalDebtLever(Integer tenNationalDebtLever) {
        this.tenNationalDebtLever = tenNationalDebtLever;
    }


}
