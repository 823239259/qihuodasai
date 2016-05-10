<!--底部 -->
<!-- 公司简介 -->
<div class="copyright">
    <div class="cpctn">
        <ul class="cp_list">
            <li><i><a href="${base}/news/newsdata?colname=股市热点" target="_blank">股市热点</a></i></li>
            <li><a href="${base}/help?tab=rule&leftMenu=1" target="_blank">操盘必读</a></li>
            <li><a href="${base}/news/newsdata?colname=达人动态" target="_blank">达人动态</a></li>
            <li><a href="${base}/news/newsdata?colname=媒体报道" target="_blank">媒体报道</a></li>
            <li><a href="${base}/news/newsdata?colname=网站公告" target="_blank">网站公告</a></li>
        </ul>
        <ul class="cp_list">
            <li><i><a href="${base}/help?tab=newbie" target="_blank">新手入门</a></i></li>
            <li><a href="">常见问题</a></li>
            <li><a href="${base}/abort">关于我们</a></li>
            <li><a href="${base}/contact">联系我们</a></li>
        </ul>
        <div class="cp_sina">
            <h3>新浪微博</h3>
            <img src="${base}/static/images/common/sinacode.jpg">
        </div>
        <div class="cp_wx">
            <h3>关注微信</h3>
            <img src="${base}/static/images/common/wxcode.jpg">
        </div>
        <div class="cp_hot">
            <h3>客服热线</h3>
            <p class="cp_hotphone">400-020-0158</p>
            <p class="cp_hotqq">QQ服务群196113230<br>交流群201182159</p>
        </div>
    </div>
    <div class="cp_copyright">
        <div>友情链接：
        <#--
          <c:forEach  items="${links}" var="link" varStatus="status">
             <a href="${link.linkUrl}" target="_blank">${link.name}</a>
           </c:forEach>
        -->
           <#list links as link>
                <a href="${link.linkUrl}" target="_blank">${link.name}</a>
           </#list>
           
           
           
        </div>
        <p>Copyright©2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</p>
    </div>
    <div class="cp_img">
        <img src="${base}/static/images/common/logo_01.jpg">
        <img src="${base}/static/images/common/logo_02.jpg">
        <img src="${base}/static/images/common/logo_03.jpg">
        <img src="${base}/static/images/common/logo_04.jpg">
    </div>
</div>