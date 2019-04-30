/**
 * 
 */
var userId = getParameter('userId');
var page = 0;
var pageSize = 12;
var AllTypeCodeParent = "";
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
	$("#nav_list_update").click(function(){
		window.location.href="/userpage/toMaterialUpload?userId="+userId;
	});
	chooseSearchWord();
	//头部搜索框
	$(".search_btn").click(function(){
		var materialName = $(".search_content").val();
		var materialNameCode = encodeURI(encodeURI(materialName));
		var materialTypeCodeParent = AllTypeCodeParent;
		window.location.href = "/userpage/toSearchResult?userId="+userId+"&materialName="+materialNameCode+
		"&materialDescription="+materialNameCode+"&materialTypeCodeParent="+materialTypeCodeParent+
		"&page="+page+"&pageSize="+pageSize;
	});
});
//拿到父code
function chooseSearchWord(){
	$.ajax({
		url:'/SearchIndex/chooseSearchWord?userId='+userId,
    	data:"",
        type:'post',
       success:function (data) {
    	   var result = (data);
    	   //拼接父code
    	   for(var i = 0; i < result.object.length ; i++){
     		AllTypeCodeParent = result.object[i].typeCode+","+AllTypeCodeParent;
    	   }
    	   AllTypeCodeParent = AllTypeCodeParent.substring(0, AllTypeCodeParent.lastIndexOf(','));
    	   console.log(AllTypeCodeParent);
       },
       error:function(){
    	   console.log('error happened----');
       }
		
	});
}
/*显示用户头像*/
function showUserHeadImg(userId) {
	$.ajax({
		url:'/user/getUserInfoById',
		type:'get',
		data:{
			userId:userId
		},
		success:function(data){
			if(data.status == '200'){
				var user = data.object;
				/*显示用户头像*/
				var headImgSrc = window.location.protocol + "//" + window.location.host +"/"
						+ user.userPhotoUrl;
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
