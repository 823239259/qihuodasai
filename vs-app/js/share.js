		var shares=null;
		var Intent=null,File=null,Uri=null,main=null;
		// H5 plus事件处理
		function plusReady(){
			updateSerivces();
			if(plus.os.name=="Android"){
				main = plus.android.runtimeMainActivity();
				Intent = plus.android.importClass("android.content.Intent");
				File = plus.android.importClass("java.io.File");
				Uri = plus.android.importClass("android.net.Uri");
			}
		}
		if(window.plus){
			plusReady();
		}else{
			document.addEventListener("plusready",plusReady,false);
		}
		/**
		 * 更新分享服务
		 */
		function updateSerivces(){
			plus.share.getServices( function(s){
				shares={};
				for(var i in s){
					var t=s[i];
					shares[t.id]=t;
				}
			}, function(e){
			} );
		}
		
		/**
		   * 分享操作
		   * @param {JSON} sb 分享操作对象s.s为分享通道对象(plus.share.ShareService)
		   * @param {Boolean} bh 是否分享链接
		   */
		function shareAction(sb,bh) {
			if(!sb||!sb.s){
				return;
			}
			var msg={content:sharecontent.value,extra:{scene:sb.x}};
			if(bh){
				msg.href=sharehref.value;
				if(sharehrefTitle&&sharehrefTitle.value!=""){
					msg.title=sharehrefTitle.value;
				}
				if(sharehrefDes&&sharehrefDes.value!=""){
					msg.content=sharehrefDes.value;
				}
				msg.thumbs=["http://cms.vs.com/uploads/topic/share/image/100.png"];
				msg.pictures=["http://cms.vs.com/uploads/topic/share/image/100.png"];
			}else{
				if(pic&&pic.realUrl){
					msg.pictures=[pic.realUrl];
				}
			}
			// 发送分享
			if ( sb.s.authenticated ) {
				shareMessage(msg,sb.s);
			} else {
//				outLine( "---未授权---" );
				sb.s.authorize( function(){
						shareMessage(msg,sb.s);
					},function(e){
				});
			}
		}
		/**
		   * 发送分享消息
		   * @param {JSON} msg
		   * @param {plus.share.ShareService} s
		   */
		function shareMessage(msg,s){
			s.send( msg, function(){
			}, function(e){
			} );
		}
		// 分析链接
		function shareHref(){
			var shareBts=[];
			// 更新分享列表
			var ss=shares['weixin'];
			ss&&ss.nativeClient&&(shareBts.push({title:'微信朋友圈',s:ss,x:'WXSceneTimeline'}),
			shareBts.push({title:'微信好友',s:ss,x:'WXSceneSession'}));
			ss=shares['qq'];
			ss&&ss.nativeClient&&shareBts.push({title:'QQ',s:ss});
			// 弹出分享列表
			plus.nativeUI.actionSheet({title:'分享链接',cancel:'取消',buttons:shareBts},function(e){
				(e.index>0)&&shareAction(shareBts[e.index-1],true);
			});
		}