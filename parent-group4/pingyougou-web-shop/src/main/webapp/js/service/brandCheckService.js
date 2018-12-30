// 定义服务层:
app.service("brandCheckService",function($http){
	this.findAll = function(){
		return $http.get("../brandCheck/findAll.do");
	}
	
	this.findPage = function(page,rows){
		return $http.get("../brandCheck/findPage.do?pageNum="+page+"&pageSize="+rows);
	}
	
	this.add = function(entity){
		return $http.post("../brandCheck/add.do",entity);
	}

    this.update=function(entity){
        return $http.post("../brandCheck/update.do",entity);
    }

    this.findOne=function(id){
        return $http.get("../brandCheck/findOne.do?id="+id);
    }

    this.dele = function(ids){
        return $http.get("../brandCheck/delete.do?ids="+ids);
    }
	
	// this.search = function(page,rows,searchEntity){
	// 	return $http.post("../brandCheck/search.do?pageNum="+page+"&pageSize="+rows,searchEntity);
	// }
	
	this.selectOptionList = function(){
		return $http.get("../brandCheck/selectOptionList.do");
	}
});