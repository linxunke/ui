/**
 * 
 */
var userId = getParameter('userId');
$(document).ready(function () {
	showUserHeadImg(userId);
	$(".home").click(function() {
		window.location.href="/userpage/toSearchIndex?userId="+userId;
	});
	$(".material_library").click(function() {
		window.location.href="/userpage/toMaterialLibrary?userId="+userId;
	});
	$(".work_management").click(function() {
		window.location.href="/userpage/work_manage?userId="+userId;
	});
});

/*显示用户头像*/
function showUserHeadImg(userId) {
	$.ajax({
		url:'/user/getUserInfoById',
		type:'get',
		data:{
			userId:userId
		},
		success:function(data){
			console.log(data);
			if(data.status == '200'){
				var user = data.object;
				/*显示用户头像*/
				var headImgSrc = window.location.protocol + "//" + window.location.host +"/"
						+ user.userPhotoUrl;
				//$("#user_head_img").prop("src",headImgSrc);
				$(".need_sign_in").find("img").attr("src",headImgSrc);
			}else {
				alert(data.message);
			}
		},
		error:function(){
			console.log("error happened ....");
		}
	});
}