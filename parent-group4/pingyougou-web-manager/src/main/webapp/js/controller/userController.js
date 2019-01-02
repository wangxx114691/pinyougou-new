 //控制层 
app.controller('userController' ,function($scope,$controller   ,userService){
	
	$controller('baseController',{$scope:$scope});//继承
	

	//搜索
	$scope.searchCount=function(){
		userService.searchCountP().success(
            function(response){
                // 将用户个数记录
                $scope.totalUserP=response.totalUserP;
            }
        );
        userService.searchCountU().success(
            function(response){
                // 将用户个数记录
                $scope.totalUserU=response.totalUserU;
            }
        );
	}

    //搜索活跃度
    $scope.searchActive=function(){
        userService.searchActive().success(
            function(response){
                // 将用户个数记录
                $scope.totalRegister=response.totalRegister;
                $scope.totalActive=response.totalActive;
                $scope.totalNoAct=$scope.totalRegister-$scope.totalActive;
            }
        );
    }

    $scope.searchEntity={};//定义搜索对象
    // 查询用户用于审核

    $scope.search= function(page,rows){
        // 向后台发送请求获取数据:
        userService.search(page,rows,$scope.searchEntity).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    }

    // 冻结操作
    $scope.downdele=function(){
        //获取选中的复选框
        userService.downdele( $scope.selectIds ).success(
            function(response){
                if(response.flag){
                    alert(response.message);
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }else {
                    alert(response.message);
                }
            }
        );
    }


    // 解冻
    $scope.pointsStatus = function(status){
        userService.pointsStatus($scope.selectIds,status).success(function(response){
            if(response.flag){
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }
});	
