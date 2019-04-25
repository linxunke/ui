/**
 * 
 */
var userId = getParameter('userId'); 
var pageSize = 8;
var currentPage = 1;
$(document).ready(function(){
	$("#manage_upload").click(function() {
		window.open("/userpage/toMaterialUpload?userId="+userId);
	});
	getUserName(userId);
	getMaterialCount(userId);
	getHistoryMeterial();
	/*下载格式为ai的图片*/
	$("#download_material_ai").click(function() {
		var currentMaterialId = $("#currentMaterialId").val();
		var isIcon = $("#isIconInput").val();
		var confirmDownload = confirm("确认下载文件？");
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
$("#material_list").on('click','.download',function(){
	console.log("进入弹窗方法");
	var currentMaterialId = $(this).parent().parent().find(".material_id_container").html();
	showModal(currentMaterialId);
});
function getUserName(userId){
	$.ajax({
    	url:'/user/getUserName',
    	data:{userId: userId
    	},
    	type:'post',
    	success:function(data){
    		var resultData = data;
    		console.log(resultData);
    		if(resultData.status == '200'){
    			console.log();
    			$("#manage_username").html(resultData.object.userNickname);
    			$("#manage_userhead").append('<img style="width:64px;height:64px;border-radius: 64px" src="'+window.location.protocol + "//" + window.location.host + '//' + resultData.object.userPhotoUrl + '">');
    			
    		}else if(resultData.status == '500'){
				alert(resultData.message);
			}
    	},
    	error:function(){
    		console.log('getUserName error happened----');
    	}
    });
}
function getMaterialCount(userId){
	$.ajax({
    	url:'/historyandcollection/selectHistoryCount',
    	data:{
    		userId: userId,
    		type: 1
    	},
    	type:'post',
    	success:function(data){
    		var resultData = JSON.parse(data);
    		$("#materialcount").html("下载"+resultData.object+"个素材");
    		console.log(resultData);
 			
    	},
    	error:function(){
    		console.log('getMaterialCount error happened----');
    	}
    });
}
function getHistoryMeterial(){
	$.ajax({
    	url:'/historyandcollection/selectHistoryOrCollectionMaterial',
    	data:{
    		userId: userId,
    		type: 1,
    		pageSize:pageSize,
    		currentPage:currentPage
    	},
    	type:'post',
    	async:false,
    	success:function(data){
    		var result = JSON.parse(data);
    		console.log(result);
    		for(var i = 0; i <= result.object.length-1; i++){
    			var div = document.createElement('div');
	    		   div.className = "result_photo";
	     		   div.id = "photo_" + i;
	     		   document.getElementById("material_list").appendChild(div);
	     		  if(result.object[i].isCollection == 0){
	     			  $("#"+div.id).html('<div class="material_id_container" style="display:none">'+result.object[i].id+'</div><div class="photo_url" ><img style="width:220px;height:160px;" src="'+window.location.protocol + "//" + window.location.host +'/'+result.object[i].thumbnailUrl+'"></div><div class="photo_name">'+result.object[i].materialName+'</div><div class="handles" id="handles'+i+'"><div class="copy float_l"><img src="../img/相似.png"></div><div class="download"><img style="width:16px;height:16px;float:right;" src="../img/下载.png"></div><div class="collect float_r" style="margin-right:5px"><img src="../img/未赞.png"><input class="collection_valid" style="display:none;" type="number" value="'+result.object[i].isCollection+'"/></div></div>');
	     		   }else{
	     			  $("#"+div.id).html('<div class="material_id_container" style="display:none">'+result.object[i].id+'</div><div class="photo_url" ><img style="width:220px;height:160px;" src="'+window.location.protocol + "//" + window.location.host +'/'+result.object[i].thumbnailUrl+'"></div><div class="photo_name">'+result.object[i].materialName+'</div><div class="handles" id="handles'+i+'"><div class="copy float_l"><img src="../img/相似.png"></div><div class="download"><img style="width:16px;height:16px;float:right;" src="../img/下载.png"></div><div class="collect float_r" style="margin-right:5px"><img src="../img/已点赞.png"><input class="collection_valid" style="display:none;" type="number" value="'+result.object[i].isCollection+'"/></div></div>');
	     		   }
    		}
 			
    	},
    	error:function(){
    		console.log('getHistoryMeterial error happened----');
    	}
    });
}
$("#material_list").on('click','.collect',function(){
	var operationCode;
	if($(this).find(".collection_valid").val() == 0){
		$(this).html('<img src="../img/已点赞.png"><input class="collection_valid" style="display:none;" type="number" value="1"/>');
		operationCode = 1;
	}else{
		$(this).html('<img src="../img/未赞.png"><input class="collection_valid" style="display:none;" type="number" value="0"/>');
		operationCode = 0;
	}
	//当前图片素材的id
	var currentMaterialId = $(this).parent().parent().find(".material_id_container").html();
	$.ajax({
		url:'/historyandcollection/collectOperate',
		type:'post',
		async:false,
		data:{
			userId:userId,
			materialInfoId:currentMaterialId,
			operationCode:operationCode,
			type:2
		},
		success:function(data){
			var result = JSON.parse(data);
			console.log(result.status == "200");
			console.log(result.status == "500");
			if(result.status == "200"){
				console.log(result.message);
			}else{
				console.log(result.message);
			}
		},
		error:function(){
			console.log("error happened .....");
		}
	});
});
function showModal(materialId) {
	$("#currentMaterialId").val(materialId);
	$("#material_type_info_div").empty();
	getMaterialInfoDetailsByMaterialId(materialId);
	$(".modal").css("display","block");
}
/*关闭模态框*/
function closeModal() {
	$(".modal").css("display","none");
}
/*获取图片的全部信息*/
function getMaterialInfoDetailsByMaterialId(materialId) {
	var typeData;
	$.ajax({
		url:'/uploadMaterial/getMaterialTypes?userId='+userId,
		type:'get',
		async:false,
		success:function(data){
			typeCatchData = JSON.parse(data);
			console.log(typeCatchData);
		},
		error:function(){
			console.log('error happened----');
		}
	});
	$.ajax({
		url:'/materialLibrary/getMaterialInfoInLibrary',
		type:'get',
		async:false,
		data:{
			userId:userId,
			materialId:materialId
		},
		success:function(data){
			var materialData = JSON.parse(data);
			console.log(materialData);
			showMaterialDetailsInModal(materialData,typeCatchData);
		},
		error:function(){
			console.log("error happened .....");
		}
	});
	
}
/*展示详情页的信息*/
function showMaterialDetailsInModal(data,typeCatchData) {
	var materialPngSrc = window.location.protocol + "//" + window.location.host +"/"
	+ data.object.materialInfo.pngUrl;
	$("#material_img_preview").attr("src",materialPngSrc);
	var isIcon = materialIsAIcon(data.object.materialInfo.materialTypeInfoList);
	if(isIcon){
		$("#material_png_size_select").css("display","inline");
		$("#isIconInput").val(true);
	}else{
		$("#material_png_size_select").css("display","none");
		$("#isIconInput").val(false);
	}
	$("#material_content_name").html(data.object.materialInfo.materialName);
	$("#material_content_label").html(data.object.materialInfo.materialDescription);
	$("#personal_canvas").html(data.object.canvasInfo.canvasName);
	$("#currentMaterialId").val(data.object.materialInfo.id);
	var materialTypeInfoList = data.object.materialInfo.materialTypeInfoList;
	var parentTypeInfoList = typeCatchData.object.materialTypes;
	var childTypeInfoList = typeCatchData.object.materialSegmentations;
	var styleTypeInfoList = typeCatchData.object.materialStyles;
	addTypeBlocksByNum(materialTypeInfoList.length);
	var materialTypeBlocks = $("#material_type_info_div").find(".material_info_div");
	for(var i = 0; i < materialTypeInfoList.length; i++){
		for(var j = 0; j < parentTypeInfoList.length; j++){
			if(parentTypeInfoList[j].typeCode == materialTypeInfoList[i].materialTypeCodeParent){
				$(materialTypeBlocks[i]).find(".material_parent_type ").html(parentTypeInfoList[j].typeName);
				break;
			}
		}
		for(var j = 0; j < childTypeInfoList.length; j++){
			if (childTypeInfoList[j].typeCode == materialTypeInfoList[i].materialTypeCodeChild) {
				$(materialTypeBlocks[i]).find(".material_child_type ").html(childTypeInfoList[j].typeName);
				break;
			}
		}
		for(var j = 0; j < styleTypeInfoList.length; j++){
			if (styleTypeInfoList[j].typeCode == materialTypeInfoList[i].materialStyleCode) {
				$(materialTypeBlocks[i]).find(".material_style_type ").html(styleTypeInfoList[j].typeName);
				break;
			}
		}
	}
}
function materialIsAIcon(materialTypeInfoList) {
	for(var i =0; i < materialTypeInfoList.length; i++){
		if(materialTypeInfoList[i].materialTypeCodeParent != '01'){
			return false;
		}
	}
	return true;
}

function addTypeBlocksByNum(num) {
	for(var i = 0; i < num; i++){
		$("#material_type_info_div").append('<div class="material_info_div"><div class="float_l material_type_div_each">'+
				'<span class="material_type_name">类型</span><div class="material_type_content float_l">'+
				'<div class="material_parent_type float_l"></div></div></div>'+
				'<div class="float_l material_type_div_each"><span class="material_type_name">细分</span>'+
				'<div class="material_type_content float_l"><div class="material_child_type float_l">'+
				'</div></div></div><div class="float_l material_type_div_each" style="margin-right: 0">'+
				'<span class="material_type_name">风格</span><div class="material_type_content float_l">'+
				'<div class="material_style_type float_l"></div></div></div></div>');
	}
}
function downloadStaticFile(imageUrl,imageName) {
    //console.log(imageUrl);
    //console.log(imageName);
    $("#downloadImg").attr("href",window.location.protocol + "//" + window.location.host + imageUrl);
    $("#downloadImg").attr("download",imageName);
    document.getElementById("downloadImg").click(); 
}
$("#result_list_body").on('click','.collect',function(){
	var operationCode;
	if($(this).find(".collection_valid").val() == 0){
		$(this).html('<img src="../img/已点赞.png"><input class="collection_valid" style="display:none;" type="number" value="1"/>');
		operationCode = 1;
	}else{
		$(this).html('<img src="../img/未赞.png"><input class="collection_valid" style="display:none;" type="number" value="0"/>');
		operationCode = 0;
	}
	//当前图片素材的id
	var currentMaterialId = $(this).parent().parent().find(".material_id_container").html();
	$.ajax({
		url:'/historyandcollection/collectOperate',
		type:'post',
		async:false,
		data:{
			userId:userId,
			materialInfoId:currentMaterialId,
			operationCode:operationCode,
			type:2
		},
		success:function(data){
			var result = JSON.parse(data);
			console.log(result.status == "200");
			console.log(result.status == "500");
			if(result.status == "200"){
				console.log(result.message);
			}else{
				console.log(result.message);
			}
		},
		error:function(){
			console.log("error happened .....");
		}
	});
});