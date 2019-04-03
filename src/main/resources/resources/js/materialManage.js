$(document).ready(function() {
	var userId = getParameter('userId');
	var canvasId = getParameter('canvasId');
	init(userId, canvasId);
});

function showModal() {
	$(".modal").css("display","block");
}
function closeModal() {
	$(".modal").css("display","none");
}
/*自定义CheckBox的js功能代码*/
function show_custom_checkedbox(obj) {
	$(obj).css("cursor","pointer");
	$(obj).css("opacity","1");
}
function hiden_custom_checkedbox(obj) {
	if($(obj).find(".checked_status").val() != "checked"){
		$(obj).css("opacity","0");
		$(obj).css("cursor","default");
	}
}
function change_checked_status(obj) {
	var status = $(obj).find("input").val();
	if(status == "unchecked"){
		$(obj).find(".checked_status").val("checked");
		$(obj).css("background-color","orange");
		$(obj).find("span").html("√");
		$(obj).parent().parent().css("background-color","#DFE1E6");
	}else{
		$(obj).find(".checked_status").val("unchecked");
		$(obj).css("background-color","white");
		$(obj).find("span").html("");
		$(obj).parent().parent().css("background-color","transparent");
	}
}
/*-------end-------*/

function init(userId,canvasId) {
	getUserInfo(userId);
	getCanvasInfoById(canvasId);
}

function getUserInfo(userId) {
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
				$("#user_name").html(user.userNickname);
				/*显示用户头像*/
			}else {
				alert(data.message);
			}
		},
		error:function(){
			console.log("error happened ....");
		}
	});
}
function getCanvasInfoById(canvasId) {
	$.ajax({
		url:'/canvasInfo/getCanvasInfoById',
		type:'get',
		data:{
			canvasId:canvasId
		},
		success:function(data){
			console.log(data);
			if(data.status == '200'){
				
			}else {
				alert(data.message);
			}
		},
		error:function(){
			console.log("error happened ....");
		}
	});
}