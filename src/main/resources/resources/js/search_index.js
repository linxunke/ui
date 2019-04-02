$(document).ready(function () {
	$.ajax({
		url:'/canvasInfo/getCanvasByUserId?userId='+userId,
    	data:"",
        type:'post',
       success:function (data) {
    	   
       },
       error:function(){
    	   
       } 
	});
});