
ap.controller('seckillGoodsController2' ,function($scope,$location,$interval,seckillGoodsService2){
    //读取列表数据绑定到表单中
    $scope.findOne=function(){

        seckillGoodsService2.findOne($location.search().id).success(

            function(response){
                $scope.entity= response;
                var timeSecond = Math.floor((new Date($scope.entity.seckillGoods.endTime).getTime()-new Date().getTime())/1000);

                time = $interval(function () {
                    if(timeSecond > 0){
                        timeSecond = timeSecond -1;
                        $scope.lastTime = convertTimeString(timeSecond);

                    }else {
                        $interval.cancel($scope.time)
                    }
                },1000);
            }

        );
        convertTimeString = function (second) {
            // 计算当前天数
            var days = Math.floor(second / (60*60*24));
            // 计算当前小时数
            var hours = Math.floor((second - days*60*60*24) / (60*60));

            // 计算当前分钟数
            var minutes = Math.floor((second - days*60*60*24 - hours*60*60) / 60);

            // 计算当前描述
            var seconds = Math.floor((second - days*60*60*24 - hours*60*60 - minutes*60));

            // 格式化
            var dayString = "";
            if (days > 0) {
                dayString = days + "天  ";
            }
            var hoursString = hours + ":";
            if (hours < 10) {
                hoursString = "0" + hours + ":";
            }
            var minutesString = minutes + ":";
            if (minutes < 10) {
                minutesString = "0" + minutes + ":";
            }
            var secondsString = "" + seconds;
            if (seconds < 10) {
                secondsString = "0" + seconds;
            }
            return dayString + hoursString + minutesString  + secondsString;
        }
    };
    $scope.submitOrder=function(){
        seckillGoodsService2.submitOrder( $scope.entity.seckillGoods.id ).success(
            function(response){
                if(response.flag){//如果下单成功
                    alert("抢购成功，请在5分钟之内完成支付");
                    location.href="Seckillgoodspay.html";//跳转到支付页面
                }else{
                    alert(response.message);
                }
            }
        );

    }

});
