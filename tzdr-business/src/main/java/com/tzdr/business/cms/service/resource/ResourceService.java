package com.tzdr.business.cms.service.resource;


import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jodd.util.StringUtil;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tzdr.business.cms.service.auth.UserAuthService;
import com.tzdr.common.auth.SearchOperator;
import com.tzdr.common.auth.Searchable;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.cms.entity.resource.Menu;
import com.tzdr.domain.cms.entity.resource.Resource;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.resource.ResourceDao;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
@Service
public class ResourceService  extends BaseServiceImpl<Resource, ResourceDao>  {

    @Autowired
    private UserAuthService userAuthService;

    
    
    public Resource findById(Long resourceId){
    	return getEntityDao().findById(resourceId);
    }
    /**
     * 得到真实的资源标识  即 父亲:儿子
     * @param resource
     * @return
     */    
    public String findActualResourceIdentity(Resource resource) {

        if(resource == null) {
            return null;
        }

        StringBuilder s = new StringBuilder(resource.getIdentity());

        boolean hasResourceIdentity = !StringUtil.isEmpty(resource.getIdentity());

        Resource parent = findById(resource.getParentId());
        //循环找父节点
        while(parent != null) {
            if(!StringUtil.isEmpty(parent.getIdentity())) {
                s.insert(0, parent.getIdentity() + ":");
                hasResourceIdentity = true;
            }
            parent = findById(parent.getParentId());
        }

        //如果用户没有声明 资源标识  且父也没有，那么就为空
        if(!hasResourceIdentity) {
            return "";
        }


        //如果最后一个字符是: 因为不需要，所以删除之
        int length = s.length();
        if(length > 0 && s.lastIndexOf(":") == length - 1) {
            s.deleteCharAt(length - 1);
        }

        //如果有儿子 最后拼一个* 这个getAll()数据一般不变，要做一下缓存,这种写法太慢。
        //boolean hasChildren = false;
      /*
        for(Resource r : getAll()) {
            if(resource.getId().equals(r.getParentId())) {
                hasChildren = true;
                break;
            }
        }*/
        
        if(resource.isHasChildren()) {
            s.append(":*");
        }

        return s.toString();
    }

    public List<Menu> findMenus(User user) {
        Searchable searchable =
                Searchable.newSearchable()
                        .addSearchFilter("show", SearchOperator.eq, true)
                        .addSort(new Sort(Sort.Direction.DESC, "parentId", "weight"));

        List<Resource> resources = findAllWithSort(searchable);

        Set<String> userPermissions = userAuthService.findStringPermissions(user);

        Iterator<Resource> iter = resources.iterator();
        while (iter.hasNext()) {
            if (!hasPermission(iter.next(), userPermissions)) {
                iter.remove();
            }
        }

        return convertToMenus(resources);
    }

    private boolean hasPermission(Resource resource, Set<String> userPermissions) {
        String actualResourceIdentity = findActualResourceIdentity(resource);
        if (StringUtil.isEmpty(actualResourceIdentity)) {
            return true;
        }

        for (String permission : userPermissions) {
            if (hasPermission(permission, actualResourceIdentity)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasPermission(String permission, String actualResourceIdentity) {

        //得到权限字符串中的 资源部分，如a:b:create --->资源是a:b
        String permissionResourceIdentity = permission.substring(0, permission.lastIndexOf(":"));

        //如果权限字符串中的资源 是 以资源为前缀 则有权限 如a:b 具有a:b的权限
        if(permissionResourceIdentity.startsWith(actualResourceIdentity)) {
            return true;
        }


        //模式匹配
        WildcardPermission p1 = new WildcardPermission(permissionResourceIdentity);
        WildcardPermission p2 = new WildcardPermission(actualResourceIdentity);

        return p1.implies(p2) || p2.implies(p1);
    }


    @SuppressWarnings("unchecked")
    public static List<Menu> convertToMenus(List<Resource> resources) {

        if (resources.size() == 0) {
            return Collections.EMPTY_LIST;
        }

        Menu root = convertToMenu(resources.remove(resources.size() - 1));

        recursiveMenu(root, resources);
        List<Menu> menus = root.getChildren();
       // removeNoLeafMenu(menus);

        return menus;
    }

    private static void recursiveMenu(Menu menu, List<Resource> resources) {
        for (int i = resources.size() - 1; i >= 0; i--) {
            Resource resource = resources.get(i);
            if (resource.getParentId().equals(menu.getId())) {
                menu.getChildren().add(convertToMenu(resource));
                resources.remove(i);
            }
        }

        for (Menu subMenu : menu.getChildren()) {
            recursiveMenu(subMenu, resources);
        }
    }

    private static Menu convertToMenu(Resource resource) {
        return new Menu(resource.getId(), resource.getName(),resource.getUrl());
    }

    
   public List<Resource>  findByParentId(Long parentId){
	   return getEntityDao().findByParentIdAndShowOrderByWeightAsc(parentId, Boolean.TRUE);
   }
}
