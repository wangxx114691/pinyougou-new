// 定义服务层:
app.service("addressService",function($http){

    //读取列表数据绑定到表单中
    this.showName=function(){
        return $http.get('../address/name.do');
    }

    this.findAll = function(){
        return $http.get("../address/findAll.do");
    }

    this.findProvinceList = function(){
        return $http.get("../address/findProvinceList.do");
    }

    this.findByProvinceId = function(provinceid){
        return $http.get("../address/findByProvinceId.do?provinceid="+provinceid);
    }

    this.findByCityId = function(cityid){
        return $http.get("../address/findByCityId.do?cityid="+cityid);
    }

    this.findOne=function(id){
        return $http.get("../address/findOne.do?id="+id);
    }

    this.add = function(entity){
        return $http.post("../address/add.do",entity);
    }

    this.update=function(entity){
        return $http.post("../address/update.do",entity);
    }

    this.dele = function(id){
        return $http.get("../address/delete.do?id="+id);
    }

    this.setIsDefault = function(id){
        return $http.get("../address/setIsDefault.do?id="+id);
    }

});