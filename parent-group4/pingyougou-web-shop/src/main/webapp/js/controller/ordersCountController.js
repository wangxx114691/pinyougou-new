 //控制层 
app.controller('ordersCountController' ,function($scope,$controller,ordersCountService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
        ordersCountService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){
        ordersCountService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){
        ordersCountService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}

	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=sellerService.update( $scope.entity ); //修改  
		}else{
			serviceObject=sellerService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.flag){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	$scope.add = function(){
        ordersCountService.add( $scope.entity  ).success(
			function(response){
				if(response.flag){
					// 重新查询 
		        	// $scope.reloadList();//重新加载
					location.href="shoplogin.html";
				}else{
					alert(response.message);
				}
			}		
		);	
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
        ordersCountService.dele( $scope.selectIds ).success(
			function(response){
				if(response.flag){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){
		if($scope.searchEntity.beginTime!=null){
            var begin=$scope.searchEntity.beginTime;
            var time=new Date();
            time=begin;
            ordersCountService.updateTime(time).success(
            	function (response) {
                    $scope.searchEntity.beginTime=response;
                }
			);
        }
        if($scope.searchEntity.overTime!=null){
            var over=$scope.searchEntity.overTime;
            var time=new Date();
            time=over;
            ordersCountService.updateTime(time).success(
                function (response) {
                    $scope.searchEntity.overTime=response;
                }
            );
        }
        $scope.searchEntity={};//定义搜索对象*/
        ordersCountService.search(page,rows,$scope.searchEntity).success(
			function(response){
                $scope.searchEntity=response;
                $scope.list=response.pageResult.rows;
                $scope.paginationConf.totalItems=response.pageResult.total;//更新总记录数
			}			
		);
	}
    
});	
