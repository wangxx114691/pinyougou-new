
ap.service('seckillGoodsService2',function($http){
    //读取列表数据绑定到表单中
    this.findOne=function(id){
        return $http.get('seckillGoods/findOneFromRedis.do?id='+id);
    };
    this.submitOrder=function(seckillId){
        return $http.get('seckillOrder/submitOrder.do?seckillId='+seckillId);
    }

});
