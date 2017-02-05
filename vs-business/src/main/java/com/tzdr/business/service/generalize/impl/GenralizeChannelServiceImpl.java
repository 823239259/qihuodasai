package com.tzdr.business.service.generalize.impl;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.generalize.GeneralizeChannelService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.generalize.GeneralizeChannelDao;
import com.tzdr.domain.vo.GeneralizeChannelVo;
import com.tzdr.domain.web.entity.GeneralizeChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.*;

/**
 * huangkai
 * 推广渠道service
 */
@Transactional()
@Service("GenralizeChannelService")
public class GenralizeChannelServiceImpl extends BaseServiceImpl<GeneralizeChannel, GeneralizeChannelDao> implements GeneralizeChannelService {

    public static final Logger logger = LoggerFactory.getLogger(GenralizeChannelServiceImpl.class);

    @Autowired
    private AuthService authService;

    @Override
	public PageInfo<Object> queryAllTrades(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
        PageInfo<Object> pageInfo = new PageInfo<>(easyUiPage.getPage(),easyUiPage.getRows());
        String sql="select g.id  , g.type_one_title as typeOneTitle ,g.type_two_title as typeTwoTitle,"
        		+ "g.type_three_title as typeThreeTitle ,"
        		+ "g.param,g.url_key as urlKey from w_generalize_channel g where deleted = 0 ";

        // params 查询参数 依次 存入
        List<Object> params = Lists.newArrayList();
        MultiListParam multilistParam = new MultiListParam(easyUiPage,
                searchParams, params, sql);
         pageInfo = multiListPageQuery(multilistParam, GeneralizeChannel.class);

        return pageInfo;
    }

    @Override
    public boolean isRepeat(String parms) {
        List<GeneralizeChannel> list = this.getEntityDao().findByParamAndDeletedFalse(parms);
        if(list.size()!=0 && !list.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public void create(GeneralizeChannel generalizeChannel) {
        generalizeChannel.setCreateDate(new Date().getTime()/1000);
        this.getEntityDao().save(generalizeChannel);
    }

    @Override
    public List<String> getType(String name, Integer type) {
        List<String> list = new ArrayList<>();
        List<GeneralizeChannel> get = new ArrayList<>();
        switch (type){
            case 1:
               get= this.getEntityDao().findByDeletedFalse();
                for(GeneralizeChannel generalizeChannel : get){
                    if(!list.contains(generalizeChannel.getTypeOneTitle())){
                        list.add(generalizeChannel.getTypeOneTitle());
                    }
                }
                break;
            case 2:
                get=this.getEntityDao().findByTypeTwoTitle(name);
                for(GeneralizeChannel generalizeChannel : get){
                    if(!list.contains(generalizeChannel.getTypeTwoTitle())){
                        list.add(generalizeChannel.getTypeTwoTitle());
                    }
                }
                break;
            case 3:
                String[] names =name.split(",");
                String fristname =names[0];
                String twoname = names[1];
                if(fristname.equals("所有渠道")){
                	get=this.getEntityDao().findTypeThreeTitleOnlyByTypeTwo(twoname);
                }else{
                	get=this.getEntityDao().findByTypeThreeTitle(fristname,twoname);
                }
                for(GeneralizeChannel generalizeChannel : get){
                    if(!list.contains(generalizeChannel.getTypeThreeTitle())){
                        list.add(generalizeChannel.getTypeThreeTitle());
                    }
                }
                break;
        }
        return list;
    }

    @Override
    public Map<String,List<String>> getList() {
        List<GeneralizeChannel> list = this.getEntityDao().findByDeletedFalse();
        List<String> oneList =new ArrayList<>();
        List<String> twoList =new ArrayList<>();
        List<String> threeList =new ArrayList<>();
        for(GeneralizeChannel generalizeChannel :list){
//        	if(generalizeChannel.getDeleted() != true){
        		if(!oneList.contains(generalizeChannel.getTypeOneTitle()) && generalizeChannel.getTypeOneTitle() != null ){
                    oneList.add(generalizeChannel.getTypeOneTitle());
                }
                if(!twoList.contains(generalizeChannel.getTypeTwoTitle()) && generalizeChannel.getTypeTwoTitle() != null ){
                    twoList.add(generalizeChannel.getTypeTwoTitle());
                }
                if(!threeList.contains(generalizeChannel.getTypeThreeTitle()) && generalizeChannel.getTypeThreeTitle() != null ){
                    threeList.add(generalizeChannel.getTypeThreeTitle());
                }
//        	}
            
        }
        Map<String,List<String>> stringMap = new HashMap<>();
        stringMap.put("one",oneList);
        stringMap.put("two",twoList);
        stringMap.put("three",threeList);
        return stringMap;
    }
    
    @Override
    public JsonResult delete(GeneralizeChannel generalizeChannel) {
    	// TODO Auto-generated method stub
    	
    	try {
    		generalizeChannel = this.getEntityDao().findOne(generalizeChannel.getId());
			generalizeChannel.setDeleted(true);
			this.getEntityDao().update(generalizeChannel);
			return new JsonResult(true,"删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new JsonResult(false,"删除失败！");
		}
    }
    public List<GeneralizeChannel> findByParamAndDeletedFalse(String params){
    	return this.getEntityDao().findByParamAndDeletedFalse(params);
    }
}
