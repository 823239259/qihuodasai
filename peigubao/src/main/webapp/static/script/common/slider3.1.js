function slider(){
	this.speed = 200;//滑动每格的速度
	this.fullSpeed = 300;//滑动每行（多格）速度
	this.jumpSpeed = 100;
	this.speedStyle = {single:1, full:2, jump:3};
	this.ourterContainerId;
	this.loop = false;//是否循环
	this.elements = [];
	this.startIdx = 0;//从第几个元素开始显示
	this.elementWidth;
	
	var visibleIdx = [];//当前显示的元素索引
	var leftIdx = [];
	var rightIdx = [];
	this.showSize;//显示几格
	var innerContainer;
	var self;
	var moving = false;
	this.initialize=function(options){
		for(var i in options){
			this[i] = options[i];
		}
		var ourterContainer = $('#'+this.ourterContainerId);
		innerContainer = ourterContainer.children().first();
		
		if(this.elements.length==0){
			if(!this.elementWidth)
				this.elementWidth = innerContainer.children().first().outerWidth(true);
			var tagname = innerContainer.children().first().prop('tagName');
			var elements = [];
			innerContainer.children(tagname).each(function(){
				elements.push($(this).wrap('<div></div>').parent().html());
			});
			this.elements = elements;
			if(this.elements.length==0)
				return;
			
		}
		this.showSize = Math.floor(ourterContainer.outerWidth(false)/this.elementWidth);
		self = this;
		initInnerContainer();
	};
	
	function initInnerContainer(){
		innerContainer.width(self.showSize*3*self.elementWidth);
		innerContainer.html('');
		for(var i=self.startIdx; i< self.startIdx+self.showSize;i++){
			if(i == self.elements.length)break;
			visibleIdx.push(i);
			innerContainer.append(self.getElement(i));
		}
		var diff = self.showSize - visibleIdx.length;
		if(diff>0 && self.loop){
			for(var i=0; i<diff; i++){
				visibleIdx.push(i);
				innerContainer.append(self.getElement(i));
			}
		}
		resetRight(self.showSize);
		resetLeft(self.showSize);
	}
	
	this.moveBack = function(step, style, callback){
		if(moving || this.isToLeft()) return;
		moving=true;
		var offset=0;
		for(var i=0;i<step;i++){
			this.startIdx--;
			if(this.startIdx<0 && !this.loop){
				this.startIdx++;
				break;
			}else if(this.startIdx<0)
				this.startIdx=this.elements.length-1;
			offset++;
			visibleIdx.unshift(this.startIdx);
			leftIdx.pop();
		}
		
		
		var diff = visibleIdx.length - this.showSize;
		for(var i=0;i<diff;i++){
			rightIdx.unshift(visibleIdx.pop());
		}
		diff = rightIdx.length - this.showSize;
		for(var i=0;i<diff;i++ ){
			rightIdx.pop();
			innerContainer.children().last().remove();
		}
		innerContainer.animate({'left': '+='+ offset* this.elementWidth+'px'},getSpeed(style),function(){
			moving = false;
			resetLeft(offset);
			if(callback)callback();
		});
	};
	
	function getSpeed(type){
		if(type == self.speedStyle.full)
			return self.fullSpeed;
		else if(type == self.speedStyle.jump)
			return self.jumpSpeed;
		return self.speed;
	}
	
	this.moveBackAll = function(callback){
		this.moveBack(this.showSize, this.speedStyle.full,callback);
	};
	
	this.moveForward = function(step, style, callback){
		if(moving || this.isToRight()) return;
		moving=true;
		var offset=0;
		var idx = visibleIdx[visibleIdx.length-1];
		for(var i=0;i<step;i++){
			idx++;
			if(idx>=this.elements.length && !this.loop){
				idx--;
				break;
			}else if(idx>=this.elements.length)
				idx=0;
			offset++;
			visibleIdx.push(idx);
			rightIdx.shift();
		}
		
		var diff = visibleIdx.length - this.showSize;
		for(var i=0;i<diff;i++){
			leftIdx.push(visibleIdx.shift());
		}
		this.startIdx = visibleIdx[0];
		diff = leftIdx.length - this.showSize;
		for(var i=0;i<diff;i++ ){
			leftIdx.shift();
			innerContainer.children().first().remove();
			innerContainer.animate({'left': '+='+this.elementWidth+'px'},0);
		}
		innerContainer.animate({'left': '-='+ offset* this.elementWidth+'px'},getSpeed(style),function(){
			moving = false;
			resetRight(offset);
			if(callback)callback();
			
		});
	};
	
	this.moveForwardAll=function(callback){
		this.moveForward(this.showSize,this.speedStyle.full, callback);
	};
	
	this.jumpTo = function(index, callback){
		if(this.startIdx == index || index - this.startIdx+1 == this.showSize){
			if(callback)callback();
		}
		else if(this.startIdx < index){
			
			if(index - this.startIdx<= this.showSize){
				this.moveForward(index - this.startIdx, this.speedStyle.full, callback);
			}else{
				this.moveForwardAll(function(){
					self.jumpTo(index,  callback);
				});
			}
		}else{
			if(this.startIdx-index<=this.showSize){
				this.moveBack(this.startIdx-index, this.speedStyle.full, callback);
			}else{
				this.moveBackAll(function(){
					self.jumpTo(index,  callback);
				});
			}
		}
		
	};
	
	//填充左边隐藏部分
	function resetLeft(step){
		if(self.isToLeft()) return;
		var offset = 0;

		var start = leftIdx.length>0?leftIdx[0]:self.startIdx;
		for(var i= --start;i>=0;i--){
			if(offset==step)break;
			offset++;
			leftIdx.unshift(i);
			innerContainer.children().first().before(self.getElement(i));
		}
		
		if(offset < step && self.loop){
			var end = self.elements.length-step+offset;
			for(var i = self.elements.length-1; i>=end; i--){
				offset++;
				leftIdx.unshift(i);
				innerContainer.children().first().before(self.getElement(i));
			}
		}
		innerContainer.css('left',-self.elementWidth*leftIdx.length);
	}
	//填充右边隐藏部分
	function resetRight(step){
		if(self.isToRight() )return;
		var offset = 0;
		var start = rightIdx.length>0?rightIdx[rightIdx.length-1]:visibleIdx[visibleIdx.length-1];
		for(var i = ++start; i<self.elements.length;i++ ){
			if(offset==step)break;
			offset++;
			rightIdx.push(i);
			innerContainer.append(self.getElement(i));
		}
		if(offset < step && self.loop){
			var end = step - offset;
			for(var i = 0; i<end; i++){
				offset++;
				rightIdx.push(i);
				innerContainer.append(self.getElement(i));
			}
		}
	}
	
	//该方法根据实际情况重构
	this.getElement = function(index){		
		return this.elements[index];
	};
	
	this.isToLeft = function(){
		return (!this.loop && visibleIdx[0]==0);
	};
	
	this.isToRight = function(){
		return (!this.loop && visibleIdx[visibleIdx.length-1] == this.elements.length-1);
	};
	
	this.deBug=function(){
		console.log(leftIdx);
		console.log(visibleIdx);
		console.log(rightIdx);
		console.log(this.startIdx);
	};
}