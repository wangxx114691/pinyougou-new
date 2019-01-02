app.service("contentService",function($http){
	this.findByCategoryId = function(categoryId){
		return $http.get("content/findByCategoryId.do?categoryId="+categoryId);
	}

    this.findByParentId = function(){
        return $http.get("content/findByParentId.do");
    }

    this.findBrandName=function () {
        return $http.get('../content/findBrandName.do');
    }
});