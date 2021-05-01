var application = (function () {
	
  
	let instance;
 
	function init() {
		let innerContainer = $("#appInnerContainer");
		let page = [];
		let pagePointer = -1;		
		
		
		let setNextPointer = (pageUrl)=>{
			page[++pagePointer] = pageUrl;
		};
		
		let loadPage = (pageUrl,callBack) => {
			if(pageUrl){
				innerContainer.load(pageUrl,function(){
					if(callBack){
						callBack();
					}
				});
			}			
		};
		
		let goToNextPage = (pageUrl)=>{
			
			let _f = ()=>{setNextPointer(pageUrl)};
			loadPage(pageUrl,_f);
		};
		
		let resetWithLink = (pageUrl)=>{
			page = [];
			pagePointer = -1;
			goToNextPage(pageUrl);
			
		};
 
	    return {	 
	      
	      next: function (url) {
	    	  
	    	  goToNextPage(url);
	      }, 
	      
	      reset: function (url){
	    	  if(url){
	    		  resetWithLink(url);
	    	  }
	      }
	
	 
	    };
	 
	  };
 
  return {
 

    getInstance: function () {
 
      if ( !instance ) {
        instance = init();
      }
 
      return instance;
    }
 
  };
 
})();