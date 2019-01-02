// 定义服务层:
app.service('seckillGoodsCheckService',function($http){
    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../seckillGoodsCheck/findAll.do');
    }
    this.findPage=function(page,rows){
        return $http.get('../seckillGoodsCheck/findPage.do?page='+page+'&rows='+rows);
    }
    //删除
    this.dele=function(ids){
        return $http.get('../seckillGoodsCheck/delete.do?ids='+ids);
    }
    //查询
    this.search=function(page,rows){
        return $http.get('../seckillGoodsCheck/search.do?page='+page+"&rows="+rows);
    }
    //审核
    this.updateStatus = function(ids,status){
        return $http.get('../seckillGoodsCheck/updateStatus.do?ids='+ids+"&status="+status);
    }

});