currentPage=1;
pageNumber=0;
userId = getParameter('userId');
canvasId = getParameter('canvasId');
var typeCatchDate;
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
	/*细分下拉框的值会随分类下拉框值的改变而改变*/
	$(".material_parent_type_select").change(function() {
		changeChildTypeValue(this);
	});
	
	/*点击删除按钮*/
	$("#deleteMaterial").click(function(){
		var materialId = $("#currentMaterialId").val();
		var confirmDelete = confirm("确认删除该图片？");
		if(confirmDelete == true){
			deleteMaterialInfoById(materialId);
		}
	});
	
	/*点击上传跳转到上传作品页面*/
	$(".upload_btn_div").click(function() {
		window.open("/userpage/toMaterialUpload?userId="+userId);
	});
	
	/*更新图片信息*/
	$("#updateMaterial").click(function(){
		var imageName = $("#material_content_name").val();
		var materialInfoId = $("#currentMaterialId").val();
		var imageLabel = $("#material_content_label").val();
		var personalCanvasId = $("#personal_canvas_select").val();
		var typeArray = new Array();
		for(var i = 0; i <$("#material_type_info_div").find(".material_info_div").length; i++){
			typeArray[i] = new Array(3);
		}
		var index = 0;
		$("#material_type_info_div").find(".material_info_div").each(function() {
			typeArray[index][0] = $(this).find(".material_parent_type_select").val();
			typeArray[index][1] = $(this).find(".material_child_type_select").val();
			typeArray[index][2] = $(this).find(".material_style_type_select").val();
			index += 1;
		});
		var formdata = new FormData();
		formdata.append("userId",parseInt(userId));
		formdata.append("imageName",imageName);
		formdata.append("materialInfoId",parseInt(materialInfoId));
		formdata.append("typeArray",JSON.stringify(typeArray));
		formdata.append("imageLabel",imageLabel);
		formdata.append("personalCanvasId",parseInt(personalCanvasId));
		$.ajax({
			url:'/uploadMaterial/updateMaterialInfos',
			type:'post',
			data:formdata,
			contentType: false,
	        processData: false,
	        success:function(data){
				var resultData = JSON.parse(data);
				console.log(resultData);
				alert(resultData.message);
				window.location.reload();
			},
			error:function () {
				alert("更新失败，请重试！");
			}
		});
	});
	/*下载格式为ai的图片*/
	$("#download_material_ai").click(function() {
		var currentMaterialId = $("#currentMaterialId").val();
		var isIcon = $("#isIconInput").val();
		var confirmDownload = confirm("确认下载格式为ai的文件？");
		if(confirmDownload == true){
			$.ajax({
				url:'/materialInfo/downloadImage',
				type:'get',
				data:{
					userId:userId,
					materialId:currentMaterialId,
					imageType:'original',
					isIcon:isIcon
				},
				success:function(data){
					var resultData = JSON.parse(data);
					console.log(resultData);
					if(resultData.status == '200'){
						console.log(resultData.message);
						downloadStaticFile(resultData.object.imageUrl, resultData.object.imageName);
					}else{
						alert(resultData.message);
					}
					
				},
				error:function(){
					console.log("error happened .....");
				}
			});
		}
	});
	/*下载格式为png的图片*/
	$("#download_material_png").click(function() {
		var currentMaterialId = $("#currentMaterialId").val();
		var isIcon = $("#isIconInput").val();
		var confirmDownload = confirm("确认下载格式为png的文件？");
		var iconSize = 0;
		if(isIcon){
			iconSize = $("#material_png_size_select").val();
		}
		if(confirmDownload){
			$.ajax({
				url:'/materialInfo/downloadImage',
				type:'get',
				data:{
					userId:userId,
					materialId:currentMaterialId,
					imageType:'png',
					isIcon:isIcon,
					iconSize:iconSize
				},
				success:function(data){
					var resultData = JSON.parse(data);
					console.log(resultData);
					if(resultData.status == '200'){
						console.log(resultData.message);
						downloadStaticFile(resultData.object.imageUrl,resultData.object.imageName);
					}else{
						alert(resultData.message);
					}
					
				},
				error:function(){
					console.log("error happened .....");
				}
			});
		}
	});
	/*下载格式为svg的图片*/
	$("#download_material_svg").click(function() {
		var currentMaterialId = $("#currentMaterialId").val();
		var isIcon = $("#isIconInput").val();
		var confirmDownload = confirm("确认下载格式为svg的文件？");
		var iconSize = 0;
		if(isIcon){
			iconSize = $("#material_png_size_select").val();
		}
		if(confirmDownload){
			$.ajax({
				url:'/materialInfo/downloadImage',
				type:'get',
				data:{
					userId:userId,
					materialId:currentMaterialId,
					imageType:'svg',
					isIcon:isIcon,
					iconSize:iconSize
				},
				success:function(data){
					var resultData = JSON.parse(data);
					console.log(resultData);
					if(resultData.status == '200'){
						console.log(resultData.message);
						downloadStaticFile(resultData.object.imageUrl,resultData.object.imageName);
					}else{
						alert(resultData.message);
					}
					
				},
				error:function(){
					console.log("error happened .....");
				}
			});
		}
	});
});
/*下载文件的方法*/
function downloadStaticFile(imageUrl,imageName) {
        console.log(imageUrl);
        console.log(imageName);
        $("#downloadImg").attr("href",window.location.protocol + "//" + window.location.host + imageUrl);
        $("#downloadImg").attr("download",imageName);
        document.getElementById("downloadImg").click(); 
}
/*点击详情页的删除按钮*/
function deleteMaterialInfoById(materialId) {
	/*发送ajax请求，删除对应materialId的所有数据*/
	var materialInfoIds = new Array();
	var materialIdObj = new Object();
	materialIdObj.id = materialId;
	materialInfoIds[0] = materialIdObj;
	console.log(materialInfoIds);
	$.ajax({
		url:'/uploadMaterial/deleteMaterialsByBatch',
		type:'get',
		data:{
			userId:parseInt(userId),
			materialInfoIds:JSON.stringify(materialInfoIds)
		},
		success:function(data){
			var resultData = JSON.parse(data);
			console.log(resultData);
			alert(resultData.message);
			if(resultData.status == '200'){
				window.location.href = "/userpage/toMaterialManage?userId="+resultData.userId+"&canvasId="+canvasId;
			}
		},
		error:function(){
			alert("删除失败，请重试！");
		}
	});
}
/*添加图片类别模块*/
function addMaterialTypeblocks(userId) {
	$.ajax({
		url:'/uploadMaterial/getMaterialTypes?userId='+userId,
		type:'get',
		success:function(data){
			typeCatchDate = JSON.parse(data);
			var parentType = typeCatchDate.object.materialTypes;
			var childType = typeCatchDate.object.materialSegmentations;
			var styleType = typeCatchDate.object.materialStyles;
			var newTypeModual = "";
			/*类型模块拼接*/
			newTypeModual += '<div class="material_info_div"><div class="float_l material_type_div_each">'+
				'<span class="material_type_name">类型</span><div class="material_type_content float_l">'+
				'<select class="material_parent_type_select" onchange="changeChildTypeValue(this)">';
			for(var i = 0; i < parentType.length; i++){
				newTypeModual += '<option value="'+parentType[i].typeCode+'">'+parentType[i].typeName+'</option>';
			}
			/*细分模块拼接*/
			newTypeModual += '</select></div></div><div class="float_l material_type_div_each">'+
			'<span class="material_type_name">细分</span><div class="material_type_content float_l">'+
			'<select class="material_child_type_select">';
			for(var i = 0; i < childType.length; i++){
				if(childType[i].parentCode == '01'){
					newTypeModual += '<option value="'+childType[i].typeCode+'">'+childType[i].typeName+'</option>';
				}
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
/*移除一个指定的类别模块*/
function removeTypeBlock(obj) {
	$(obj).parent().parent().remove();
}

/*全选*/
function selectAllMaterial(obj) {
	var materialCheckedboxs = $(".custom_checkedbox");
	if($(obj).prop('checked') == true){
		for(var i = 0; i < materialCheckedboxs.length; i++){
			change_checked_status_with_value(materialCheckedboxs[i],'checked');
		}
	}
}
/*取消全选*/
function cancerSelectAllMaterial() {
	var materialCheckedboxs = $(".custom_checkedbox");
	for(var i = 0; i < materialCheckedboxs.length; i++){
		change_checked_status_with_value(materialCheckedboxs[i],'unchecked');
	}
	$("#selectAll").prop('checked',false);
}
/*显示模态框*/
function showModal(materialId) {
	$("#currentMaterialId").val(materialId);
	getMaterialInfoDetailsByMaterialId(materialId);
	$(".modal").css("display","block");
}
/*关闭模态框*/
function closeModal() {
	$(".modal").css("display","none");
}
/*获取图片的全部信息*/
function getMaterialInfoDetailsByMaterialId(materialId) {
	$.ajax({
		url:'/materialInfo/getMaterialDetailInfo',
		type:'get',
		data:{
			userId:userId,
			materialId:materialId
		},
		success:function(data){
			var materialData = JSON.parse(data);
			console.log(materialData);
			showMaterialDetailsInModal(materialData);
		},
		error:function(){
			console.log("error happened .....");
		}
	});
}
/*自定义CheckBox的js功能代码*/
	/*显示选择框*/
function show_custom_checkedbox(obj) {
	$(obj).css("cursor","pointer");
	$(obj).css("opacity","1");
}
	/*隐藏选择框*/
function hiden_custom_checkedbox(obj) {
	if($(obj).find(".checked_status").val() != "checked"){
		$(obj).css("opacity","0");
		$(obj).css("cursor","default");
	}
}
/*改变指定的选择框的状态*/
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
function change_checked_status_with_value(obj,theStatus) {
	if(theStatus == "unchecked"){
		$(obj).find(".checked_status").val("unchecked");
		$(obj).css("background-color","white");
		$(obj).find("span").html("");
		$(obj).parent().parent().css("background-color","transparent");
		$(obj).css("opacity","0");
	}else {
		$(obj).find(".checked_status").val("checked");
		$(obj).css("background-color","orange");
		$(obj).find("span").html("√");
		$(obj).parent().parent().css("background-color","#DFE1E6");
		$(obj).css("opacity","1");
	}
}
/*-------end-------*/

function init(userId,canvasId,currentPage) {
	getUserInfo(userId);
	getCanvasInfoById(userId,canvasId);
	/*调用分页显示素材图片的方法*/
	getMaterialsInCurrentCanvas(userId,canvasId,currentPage);
}
/*获取用户的信息*/
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
/*获取画板的详细信息*/
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
/*分页查询当前画板下的素材信息*/
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
/*以图标形式展示素材*/
function showMaterialAsIcon(data) {
	var materialInfoList = data.object.pageInfoUtil.object;
	$(".material_show_body").empty();
	for(var i = 0; i < data.object.pageInfoUtil.object.length; i++){
		$(".material_show_body").append('<div class="each_material_icon_div">'+
				'<div class="material_image_div"><img class="material_img"  '+
				'onclick="showModal('+materialInfoList[i].id+')" src="'+window.location.protocol + "//" + window.location.host +"/"+materialInfoList[i].pngUrl+'"><div'+
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

/*以图片形式展示素材*/
function showMaterialAsImg(data) {
	var materialInfoList = data.object.pageInfoUtil.object;
	$(".material_show_body").empty();
	for(var i = 0; i < data.object.pageInfoUtil.object.length; i++){
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
/*刷新显示页码模块*/
function refreshPageModual(currentPage,pageNumber) {
	$("#currentPage").val(currentPage);
	$("#pageNum").html(pageNumber);
}
/*判断一个素材是否是图标*/
function materialIsAIcon(materialTypeInfoList) {
	for(var i =0; i < materialTypeInfoList.length; i++){
		if(materialTypeInfoList[i].materialTypeCodeParent != '01'){
			console.log("false");
			return false;
		}
	}
	console.log("true");
	return true;
}
/*展示详情页的信息*/
function showMaterialDetailsInModal(data) {
	var materialPngSrc = window.location.protocol + "//" + window.location.host +"/"
	+ data.object.pngUrl;
	var isIcon = materialIsAIcon(data.object.materialTypeInfoList);
	if(isIcon){
		$("#material_png_size_select").css("display","inline");
		$("#isIconInput").val(true);
	}else{
		$("#material_png_size_select").css("display","none");
		$("#isIconInput").val(false);
	}
	$("#download_material_ai").html(data.object.materialType);
	$("#material_img_preview").prop("src",materialPngSrc);
	$("#material_content_name").val(data.object.materialName);
	$("#material_content_label").val(data.object.materialDescription);
	var checkedOptionVal = data.object.canvasInfoIdPrivate;
	getUserCanvas(data.userId,checkedOptionVal);
	getMaterialTypesInfo(); //获取素材分类信息并初始化全局变量
	$("#material_type_info_div").empty();
	addMaterialTypeBlocks(data.object.materialTypeInfoList.length);
	var materialTypeInfos = data.object.materialTypeInfoList; //获取素材的分类信息
	/*用查询到的素材的分类信息初始化类别模块的下拉框值*/
	var materialTypes = $("#material_type_info_div").find(".material_info_div");
	var len = materialTypes.length;
	for(var i = 0; i < len; i++){
		var parentTypeOptions = $(materialTypes[i]).find(".material_parent_type_select").find("option");
		/*设置类型下拉框的值*/
		for(var j = 0; j < parentTypeOptions.length; j++){
			if($(parentTypeOptions[j]).val() == materialTypeInfos[i].materialTypeCodeParent){
				$(parentTypeOptions[j]).attr("selected", "selected");
				changeChildTypeValue($(parentTypeOptions[i]).parent());
			}
		}
		changeChildTypeValue($(parentTypeOptions[i]).parent());
		/*设置细分下拉框的值*/
		var childTypeOptions = $(materialTypes[i]).find(".material_child_type_select").find("option");
		for(var j = 0; j < childTypeOptions.length; j++){
			if($(childTypeOptions[j]).val() == materialTypeInfos[i].materialTypeCodeChild){
				$(childTypeOptions[j]).attr("selected", "selected");
			}
		}
		/*设置风格下拉框的值*/
		var styleTypeOptions = $(materialTypes[i]).find(".material_style_type_select").find("option");
		for(var j = 0; j < styleTypeOptions.length; j++){
			if($(styleTypeOptions[j]).val() == materialTypeInfos[i].materialStyleCode){
				$(styleTypeOptions[j]).attr("selected", "selected");
				console.log("i:"+i);
			}
		}
	}
	
}
function changeChildTypeValue(obj) {
	var childType = typeCatchDate.object.materialSegmentations;
	var parentTypeCode = $(obj).val();
	var childTypeSelect = $(obj).parent().parent().parent().find(".material_child_type_select");
	$(childTypeSelect).empty();
	for(var i = 0; i < childType.length; i++){
		if(childType[i].parentCode == parentTypeCode){
			$(childTypeSelect).append('<option value="'+childType[i].typeCode+'">'+childType[i].typeName+'</option>');
		}
	}
}

/*获取用户的画板信息*/
function getUserCanvas(userId,checkedOptionVal) {
	$.ajax({
		url:'/canvasInfo/getAllCanvasInfoByUserId?userId='+userId+"&onlyData=1",
		type:'get',
		success:function(data){
			console.log(data);
			var canvasInfo = data.object;
			var appendOptions = '';
			for(var i=0; i < canvasInfo.length; i++){
				appendOptions += '<option value="'+canvasInfo[i].id+'">'+canvasInfo[i].canvasName+'</option>';
			}
			var $select = $("#personal_canvas_select");
			$select.empty();
			$select.append(appendOptions);
			var canvasSelectOptions = $select.find("option");
			for (var j = 0; j < canvasSelectOptions.length; j++) {
			    if ($(canvasSelectOptions[j]).val() == checkedOptionVal) {
			         $(canvasSelectOptions[j]).attr("selected", "selected");
			    }
			}
		},
		error:function(){
			console.log('error happened----');
		}
	});
}
/*批量删除*/
function delete_in_batch() {
	var confirmDeleteInBatch = confirm("确认批量删除已选择的图片？");
	if(confirmDeleteInBatch == false){
		return 0;
	}
	var materialInfoIds = new Array();
	var index = 0;
	$(".custom_checkedbox").each(function() {
		if($(this).find(".checked_status").val() == 'checked'){
			var materialIdObj = new Object();
			materialIdObj.id = $(this).parent().parent().find(".material_id").html();
			materialInfoIds[index] = materialIdObj;
			index += 1;
		}
		
	});
	$.ajax({
		url:'/uploadMaterial/deleteMaterialsByBatch',
		type:'post',
		data:{
			userId:parseInt(userId),
			materialInfoIds:JSON.stringify(materialInfoIds)
		},
		success:function(data){
			var resultData = JSON.parse(data);
			console.log(resultData);
			alert(resultData.message);
			if(resultData.status = '200'){
				window.location.href = "/userpage/toMaterialManage?userId="+resultData.userId+"&canvasId="+canvasId;
			}
		},
		error:function(){
			alert("删除失败，请重试！");
		}
	});
}

function getMaterialTypesInfo() {
	$.ajax({
		url:'/uploadMaterial/getMaterialTypes?userId='+userId,
		type:'get',
		async: false,
		success:function(data){
			typeCatchDate = JSON.parse(data);
		}
	});
}

function addMaterialTypeBlocks(num) {
	var parentType = typeCatchDate.object.materialTypes;
	var childType = typeCatchDate.object.materialSegmentations;
	var styleType = typeCatchDate.object.materialStyles;
	var newTypeModual = "";
	/*类型模块拼接*/
	newTypeModual += '<div class="material_info_div"><div class="float_l material_type_div_each">'+
		'<span class="material_type_name">类型</span><div class="material_type_content float_l">'+
		'<select class="material_parent_type_select" onchange="changeChildTypeValue(this)">';
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
	for (var i = 0; i < num; i++) {
		$("#material_type_info_div").append(newTypeModual);
	}
}