var sc_width = $(window).height() ;
if(isIE = navigator.userAgent.indexOf("MSIE")!=-1) { 
	if(sc_width >= 780){
	    loadjscssfile(basepath+'/static/css/home.css','css');
	}else{
	    loadjscssfile(basepath+'/static/css/home.mini.css','css');
	}
} 
function loadjscssfile(filename,filetype){

    if(filetype == "js"){
        var fileref = document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src",filename);
    }else if(filetype == "css"){
    
        var fileref = document.createElement('link');
        fileref.setAttribute("rel","stylesheet");
        fileref.setAttribute("type","text/css");
        fileref.setAttribute("href",filename);
    }
   if(typeof fileref != "undefined"){
        document.getElementsByTagName("head")[0].appendChild(fileref);
    }
    
}