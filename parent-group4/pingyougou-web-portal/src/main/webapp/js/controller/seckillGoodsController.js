
app.controller('seckillGoodsController' ,function($scope,seckillGoodsService){
    //读取列表数据绑定到表单中
    $scope.findList=function(){
        seckillGoodsService.findList().success(
            function(response){
                $scope.list=response;
            }
        );
    };

    /*var id = $location.search().id;*/
    $scope.findOne=function(){
        seckillGoodsService.findOne($location.search().id).success(
            function(response){
                $scope.entity= response;
            }
        );
    }

});
