// 定义服务层:
app.service("specCheckService",function($http){
	this.findAll = function(){
		return $http.get("../specCheck/findAll.do");
	}
	
	this.findPage = function(page,rows){
		return $http.get("../specCheck/findPage.do?pageNum="+page+"&pageSize="+rows);
	}
	
	this.add = function(entity){
		return $http.post("../specCheck/add.do",entity);
	}

    this.update=function(entity){
        return $http.post("../specCheck/update.do",entity);
    }

    this.findOne=function(id){
        return $http.get("../specCheck/findOne.do?id="+id);
    }

    this.dele = function(ids){
        return $http.get("../specCheck/delete.do?ids="+ids);
    }
	
	// this.search = function(page,rows,searchEntity){
	// 	return $http.post("../brandCheck/search.do?pageNum="+page+"&pageSize="+rows,searchEntity);
	// }
	
	this.selectOptionList = function(){
		return $http.get("../specCheck/selectOptionList.do");
	}
});