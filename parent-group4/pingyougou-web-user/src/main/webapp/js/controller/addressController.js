// 定义控制器:
app.controller("addressController", function ($scope, $controller, $http, addressService) {
    // AngularJS中的继承:伪继承
    $controller('baseController', {$scope: $scope});

    $scope.showName = function () {
        addressService.showName().success(
            function (response) {
                $scope.loginName = response.username;
            }
        );
    }

    // 查询所有的地址
    $scope.findAll = function () {
        // 向后台发送请求
        addressService.findAll().success(function (response) {
            $scope.list = response;
        });
    }

    // 查询省列表
    $scope.selectProvinceList = function(){
        addressService.findProvinceList().success(function(response){
            $scope.provincesList = response;
        });
    }

    // 查询市列表
    $scope.$watch("entity.province.provinceid",function(newValue,oldValue){
        addressService.findByProvinceId(newValue).success(function(response){
            $scope.citiesList = response;
        });
    });

    // 查询区列表
    $scope.$watch("entity.city.cityid",function(newValue,oldValue){
        addressService.findByCityId(newValue).success(function(response){
            $scope.areasList = response;
        });
    });

    // 查询一个
    $scope.findById = function (id) {
        addressService.findOne(id).success(function (response) {
            // {id:xx,name:yy,firstChar:zz}
            $scope.entity = response;
        });
    }

    // 保存地址
    $scope.save = function () {
        // 区分是保存还是修改
        var object;
        if ($scope.entity.id != null) {
            // 更新
            object = addressService.update($scope.entity);
        } else {
            // 保存
            object = addressService.add($scope.entity);
        }
        object.success(function (response) {
            // {flag:true,message:xxx}
            // 判断保存是否成功:
            if (response.flag) {
                // 保存成功
                alert(response.message);
                $scope.reloadList();
            } else {
                // 保存失败
                alert(response.message);
            }
        });
    }

    // 删除地址
    $scope.dele = function (id) {
        addressService.dele(id).success(function (response) {
            // 判断是否删除成功
            if (response.flag == true) {
                // 删除成功
                // alert(response.message);
                $scope.reloadList();
            } else {
                // 删除失败
                alert(response.message);
            }
        });
    }

    // 设为默认
    $scope.setIsDefault = function (id) {
        addressService.setIsDefault(id).success(function (response) {
            // 判断是否设置成功
            if (response.flag == true) {
                // 设置成功
                $scope.reloadList();
            } else {
                // 设置失败
                alert(response.message);
            }
        });
    }

});
