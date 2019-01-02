//服务层
app.service('userService',function($http){
	    	

	//搜索
	this.searchCountP=function(){
		return $http.get('http://localhost:9103/user/searchCount.do');
	}
    this.searchCountU=function(){
        return $http.get('http://localhost:9104/user/searchCount.do');
    }
    // 查询活跃用户数
    this.searchActive=function(){
        return $http.get('http://localhost:9104/user/searchActive.do');
    }
    // 查询所有用户用于审核
    this.search=function(page,rows,searchEntity){
        return $http.post('../user/search.do?page='+page+'&rows='+rows,searchEntity);
    }

    // 解冻
    this.pointsStatus = function(ids){
        return $http.get('../user/pointsStatus.do?ids='+ids);
    }
    // 冻结
    this.downdele=function(ids){
        return $http.get('../user/downdele.do?ids='+ids);
    }

});
