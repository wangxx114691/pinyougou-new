app.service("uploadService",function($http){
	
	this.uploadFile = function(){
		// 向后台传递数据:
		var formData = new FormData();
		// 向formData中添加数据:
		formData.append("file",file.files[0]);

		// 首先这里的$http是AngularJS封装的ajax, 请求方式也和ajax很像,
		// anjularjs对于post和get请求默认的Content-Type header 是application/json。所以post和get请求都不用写
		// 通过设置‘Content-Type’: undefined, 就会默认设置为multipart/form-data, 而手动设置反而会抛异常
        // 这个设置是能够启动angularJS序列化请求对象的功能
		return $http({
			method:'post',
			url:'../upload/uploadFile.do',
			data:formData,
			headers:{'Content-Type':undefined} ,// Content-Type : text/html  text/plain
            transformRequest: angular.identity
		});
	}
	
});