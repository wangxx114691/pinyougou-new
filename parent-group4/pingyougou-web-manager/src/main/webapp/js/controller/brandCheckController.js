// 定义控制器:
app.controller('brandCheckController',function($scope,$controller,brandCheckService){
	// AngularJS中的继承:伪继承
	$controller('baseController',{$scope:$scope});



    $scope.searchEntity={};
    //搜索
    $scope.search=function(page,rows){
        brandCheckService.search(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    // 显示状态
    $scope.status = ["未审核","审核通过","审核未通过","关闭"];

    // 审核的方法:
    $scope.updateStatus = function(status){
        brandCheckService.updateStatus($scope.selectIds,status).success(function(response){
            if(response.flag){
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }

});
