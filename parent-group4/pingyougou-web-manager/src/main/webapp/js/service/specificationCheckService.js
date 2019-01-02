// 定义服务层:
app.service('specificationCheckService',function($http){
    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../specificationCheck/findAll.do');
    }
    this.findPage=function(page,rows){
        return $http.get('../specificationCheck/findPage.do?page='+page+'&rows='+rows);
    }
    //删除
    this.dele=function(ids){
        return $http.get('../specificationCheck/delete.do?ids='+ids);
    }
    //查询
    this.search=function(page,rows){
        return $http.get('../specificationCheck/search.do?page='+page+"&rows="+rows);
    }
    //审核
    this.updateStatus = function(ids,status){
        return $http.get('../specificationCheck/updateStatus.do?ids='+ids+"&status="+status);
    }

});