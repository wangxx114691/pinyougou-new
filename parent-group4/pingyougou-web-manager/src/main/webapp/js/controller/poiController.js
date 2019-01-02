// 定义控制器:
app.controller("poiController",function($scope,$controller,$http,poiService){
	// AngularJS中的继承:伪继承
	$controller('baseController',{$scope:$scope});

    //品牌表
    $scope.addBrands = function(){
        // 调用uploadService的方法完成文件的上传
        poiService.addBrands().success(function(response){
            if(response.flag){
                alert(response.message);
            }else{
                alert(response.message);
            }
        });
    }


    //规格
    $scope.addSpecifications = function(){
        // 调用uploadService的方法完成文件的上传
        poiService.addSpecifications().success(function(response){
            if(response.flag){
                alert(response.message);
            }else{
                alert(response.message);
            }
        });
    }


    //addTemplates
    $scope.addTemplates = function(){
        // 调用uploadService的方法完成文件的上传
        poiService.addTemplates().success(function(response){
            if(response.flag){
                alert(response.message);
            }else{
                alert(response.message);
            }
        });
    }

    //addCategorys
    $scope.addCategorys = function(){
        // 调用uploadService的方法完成文件的上传
        poiService.addCategorys().success(function(response){
            if(response.flag){
                alert(response.message);
            }else{
                alert(response.message);
            }
        });
    }
});
