app.controller("contentController",function($scope,contentService){
	$scope.contentList = [];
	// 根据分类ID查询广告的方法:
	$scope.findByCategoryId = function(categoryId){
		contentService.findByCategoryId(categoryId).success(function(response){
			$scope.contentList[categoryId] = response;
		});
	}
	
	//搜索  （传递参数）
	$scope.search=function(){
		location.href="http://localhost:9103/search.html#?keywords="+$scope.keywords;
	}


    $scope.findByParentId=function() {
        //  $scope.parentId=parentId;//记上级ID
        contentService.findByParentId().success(function (response) {
                $scope.list = response;
                // $scope.parentId = response.get(id);//记录此分类ID
            }
        );

    }

    //查询6个商品名
    $scope.findBrandName = function () {
        contentService.findBrandName().success(function (response) {
            $scope.listone = response;
        });
    }
	
});