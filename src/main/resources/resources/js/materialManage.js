currentPage=1;
pageNumber=0;
userId = getParameter('userId');
canvasId = getParameter('canvasId');
$(document).ready(function() {
	
	init(userId, canvasId,1);
	
	/*点击跳转上一页*/
	$("#lastPage").click(function() {
		getMaterialsInCurrentCanvas(userId,canvasId,currentPage<=1?1:currentPage-1);
	});
	/*点击跳转下一页*/
	$("#nextPage").click(function() {
		console.log(pageNumber);
		getMaterialsInCurrentCanvas(userId,canvasId,currentPage>=pageNumber?pageNumber:(currentPage+1));
	});
	/*点击根据页数搜索*/
	$("#search_by_page").click(function() {
		var theCurrentPage = $("#currentPage").val();
		if(theCurrentPage<1 || theCurrentPage > pageNum){
			alert("要查找的页数不正确,请重新输入！");
		}else {
			getMaterialsInCurrentCanvas(userId,canvasId,theCurrentPage);
		}
		
	});
	/*点击+按钮，添加分类模块*/
	$("#add_material_type_div").click(function () {
		addMaterialTypeblocks(userId);
	});
});
function addMaterialTypeblocks(userId) {
	$.ajax({
		url:'/uploadMaterial/getMaterialTypes?userId='+userId,
		type:'get',
		success:function(data){
			var typeData = JSON.parse(data);
			console.log(typeData);
			var parentType = typeData.object.materialTypes;
			var childType = typeData.object.materialSegmentations;
			var styleType = typeData.object.materialStyles;
			var newTypeModual = "";
			/*类型模块拼接*/
			newTypeModual += '<div class="material_info_div"><div class="float_l material_type_div_each">'+
				'<span class="material_type_name">类型</span><div class="material_type_content float_l">'+
				'<select class="material_parent_type_select">';
			for(var i = 0; i < parentType.length; i++){
				newTypeModual += '<option value="'+parentType[i].typeCode+'">'+parentType[i].typeName+'</option>';
			}
			/*细分模块拼接*/
			newTypeModual += '</select></div></div><div class="float_l material_type_div_each">'+
			'<span class="material_type_name">细分</span><div class="material_type_content float_l">'+
			'<select class="material_child_type_select">';
			for(var i = 0; i < childType.length; i++){
				newTypeModual += '<option value="'+childType[i].typeCode+'">'+childType[i].typeName+'</option>';
			}
			/*风格模块拼接*/
			newTypeModual += '</select></div></div><div class="float_l material_type_div_each" style="margin-right: 0">'+
			'<span class="material_type_name">风格</span><div class="material_type_content float_l">'+
			'<select class="material_style_type_select">';
			for(var i = 0; i < styleType.length; i++){
				newTypeModual += '<option value="'+styleType[i].typeCode+'">'+styleType[i].typeName+'</option>';
			}
			newTypeModual += '</select></div></div><div class="float_l">'+
			'<input type="button" onclick="removeTypeBlock(this)" class="remove_parent_type_div" value="-"/></div></div>';
			
			$("#material_type_info_div").append(newTypeModual);
		},
		error:function(){
			console.log('error happened----');
		}
	});
}
function removeTypeBlock(obj) {
	$(obj).parent().parent().remove();
}

function showModal(materialId) {
	console.log(materialId);
	getMaterialInfoDetailsByMaterialId(materialId);
	$(".modal").css("display","block");
}
function closeModal() {
	$(".modal").css("display","none");
}
function getMaterialInfoDetailsByMaterialId(materialId) {
	console.log(materialId);
	$.ajax({
		url:'/materialInfo/getMaterialDetailInfo',
		type:'get',
		data:{
			userId:userId,
			materialId,materialId
		},
		success:function(data){
			console.log(data);
		},
		error:function(){
			console.log("error happened .....");
		}
	});
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

function init(userId,canvasId,currentPage) {
	getUserInfo(userId);
	getCanvasInfoById(userId,canvasId);
	/*调用分页显示素材图片的方法*/
	getMaterialsInCurrentCanvas(userId,canvasId,currentPage);
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
				var headImgSrc = window.location.protocol + "//" + window.location.host +"/"
						+ user.userPhotoUrl;
				console.log(headImgSrc);
				$("#user_head_img").prop("src",headImgSrc);
			}else {
				alert(data.message);
			}
		},
		error:function(){
			console.log("error happened ....");
		}
	});
}
function getCanvasInfoById(userId,canvasId) {
	$.ajax({
		url:'/canvasInfo/getCanvasInfoById',
		type:'get',
		data:{
			canvasId:canvasId,
			userId:userId
		},
		success:function(data){
			console.log(data);
			$(".canvas_name").html(data.object.canvasInfo.canvasName);
			$(".material_num").html(data.object.materialNum);
		},
		error:function(){
			console.log("error happened ....");
		}
	});
}

function getMaterialsInCurrentCanvas(userId,canvasId,currentPage) {
	$.ajax({
		url:'/canvasInfo/getMaterialListBycanvasId',
		type:'get',
		data:{
			userId:userId,
			canvasId:canvasId,
			currentPage:currentPage
		},
		success:function(data){
			console.log(data);
			var showAsIcon = data.object.existIcon;
			if(showAsIcon){
				showMaterialAsIcon(data);
			}else {
				showMaterialAsImg(data);
			}
		},
		error:function(){
			console.log("error happened ....");
		}
	});
}

function showMaterialAsIcon(data) {
	var materialInfoList = data.object.pageInfoUtil.object;
	$(".material_show_body").empty();
	for(var i = 0; i < data.object.pageInfoUtil.infoTotalNumber; i++){
		$(".material_show_body").append('<div class="each_material_icon_div">'+
				'<div class="material_image_div"><img class="material_img"  '+
				'onclick="showModal('+materialInfoList[i].id+')" src="'+window.location.protocol + "//" + window.location.host +"/"+materialInfoList[i].thumbnailUrl+'"><div'+
				' class="custom_checkedbox icon_checkedbox" onclick="change_checked_status(this)"'+
				' onmouseenter="show_custom_checkedbox(this)" onmouseleave="hiden_custom_checkedbox(this)">'+
				'<span></span><input class="checked_status" type="text" value="unchecked" style="display: none"/>'+
				'</div></div><div class="material_name_div"><span class="material_id" style="display: none">'+materialInfoList[i].id+
				'</span><span class="material_name">'+materialInfoList[i].materialName+'</span></div></div>');
		
		
	}
	currentPage = data.object.pageInfoUtil.currentPage;
	pageNumber = data.object.pageInfoUtil.pageNumber;
	canvasId = materialInfoList[0].canvasInfoIdPrivate;
	refreshPageModual(currentPage,pageNumber);
}

function showMaterialAsImg(data) {
	var materialInfoList = data.object.pageInfoUtil.object;
	$(".material_show_body").empty();
	for(var i = 0; i < data.object.pageInfoUtil.infoTotalNumber; i++){
		$(".material_show_body").append('<div class="each_material_img_div"><div class="material_image_block">'+
				'<div class="custom_checkedbox img_checkedbox" onclick="change_checked_status(this)"'+
				' onmouseenter="show_custom_checkedbox(this)" onmouseleave="hiden_custom_checkedbox(this)">'+
				'<span></span><input class="checked_status" type="text" value="unchecked" style="display: none"/>'+
				'</div><img onclick="showModal('+materialInfoList[i].id+')" class="material_picture" src="'+window.location.protocol + "//" + window.location.host +"/"+materialInfoList[i].thumbnailUrl+'"></div><div class="material_name_block">'+
				'<span class="material_id" style="display: none">'+materialInfoList[i].id+'</span><span class="material_name">'+materialInfoList[i].materialName+'</span>'+
				'</div></div>');
		
		
	}
	currentPage = data.object.pageInfoUtil.currentPage;
	pageNumber = data.object.pageInfoUtil.pageNumber;
	canvasId = materialInfoList[0].canvasInfoIdPrivate;
	refreshPageModual(currentPage,pageNumber);
}

function refreshPageModual(currentPage,pageNumber) {
	$("#currentPage").val(currentPage);
	$("#pageNum").html(pageNumber);
}