// 定义控制器:
app.controller('typeTemplateCheckController',function($scope,$controller,typeTemplateCheckService){
    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});


//分页
    $scope.findPage=function(page,rows){
        typeTemplateCheckService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    $scope.searchEntity={};
    //搜索
    $scope.search=function(page,rows){
        typeTemplateCheckService.search(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    //批量删除
    $scope.dele=function(){
        //获取选中的复选框
        typeTemplateCheckService.dele( $scope.selectIds ).success(
            function(response){
                if(response.flag){
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    // 显示状态
    $scope.status = ["未审核","审核通过","审核未通过","关闭"];

    // 审核的方法:
    $scope.updateStatus = function(status){
        typeTemplateCheckService.updateStatus($scope.selectIds,status).success(function(response){
            if(response.flag){
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }

});
