// 定义服务层:
app.service('brandCheckService',function($http){
    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../brandCheck/findAll.do');
    }
    this.findPage=function(page,rows){
        return $http.get('../brandCheck/findPage.do?page='+page+'&rows='+rows);
    }
    this.search=function(page,rows){
        return $http.get('../brandCheck/search.do?page='+page+"&rows="+rows);
    }
    //审核
    this.updateStatus = function(ids,status){
        return $http.get('../brandCheck/updateStatus.do?ids='+ids+"&status="+status);
    }

});