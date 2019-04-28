currentPage=1;
pageNumber=0;
userId = getParameter('userId');
parentTypeCode = getParameter('parentTypeCode');
showWay = "";  //block or list
start = 0;
materialListLoadFinish = false;
$(document).ready(function(){
	init();
	$(".parent_type_nav").click(function(){
		$(".parent_type_nav").removeClass("active");
		$(this).addClass("active");
		$(".material_library_container").empty();
		/*请求对应的细分素材信息*/
		var parentTypeCode = $(".nav_head_content").find(".active").find(".typeCode").html();
		if("block" == showWay){
			generateChildTypeGroups(parentTypeCode);
		}else if("list" == showWay){
			$(".child_Type_list").empty();
			$(".material_list").empty();
			$(".material_info_list").css("display","none");
			generateChildTypeMaterialList(parentTypeCode);
			generateMaterialList();
		}
		
	});
	$(".show_as_list_button").click(function() {
		var listButtonValue = $("#show_as_list_button").val();
		var parentTypeCode = $(".nav_head_content").find(".active").find(".typeCode").html();
		if(listButtonValue.trim() == "true"){
			showWay = "block";
			$("#show_as_list_button").val("false");
			$(".list_button_block_show").css("display","none");
			$(".list_button_list_show").css("display","block");
			$(".material_library_container").css("display","block");
			$(".material_library_list_container").css("display","none");
			generateChildTypeGroups(parentTypeCode);
		}else {
			showWay = "list";
			$("#show_as_list_button").val("true");
			$(".list_button_block_show").css("display","block");
			$(".list_button_list_show").css("display","none");
			$(".material_library_container").css("display","none");
			$(".material_library_list_container").css("display","block");
			generateChildTypeMaterialList(parentTypeCode);
			generateMaterialList();
		}
	});
});
/*点击细分封面进入细分素材列表的页面*/
function enterChildTypeLibrary(obj) {
	var childTypeCode = $(obj).find(".group_child_typeCode").html();
	window.location.href="/userpage/toChildMateiralLibrary?userId="+userId+"&childTypeCode="+childTypeCode;
}
function init() {
	generateNavHead();
	var parentTypeCode = $(".nav_head_content").find(".active").find(".typeCode").html();
	showWay = "block";
	generateChildTypeGroups(parentTypeCode);
}
/*生成导航栏*/
function generateNavHead() {
	$.ajax({
		url:'/materialLibrary/getAllMaterialParentTypeInfo?userId='+userId,
		type:'get',
		async: false,
		success:function(data){
			var resultData = JSON.parse(data);
			console.log(resultData);
			var parentTypeList = resultData.object;
			for(var i = 0; i < parentTypeList.length; i++){
				$(".nav_head_content").append('<div class="parent_type_nav float_l">'+
						parentTypeList[i].typeName+'<div class="typeCode">'+parentTypeList[i].typeCode+'</div></div>');
			}
			if(parentTypeCode == null || parentTypeCode == ""){
				$(".parent_type_nav:first").addClass("active");
			}else {
				var parentTypeBlocks = $(".parent_type_nav");
				for(var i = 0; i < parentTypeBlocks.length; i++){
					if($(parentTypeBlocks[i]).find(".typeCode").html() == parentTypeCode){
						$(parentTypeBlocks[i]).addClass("active");
						break;
					}
				}
			}
			
		},
		error:function(){
			console.log("error happened ....");
		}
	});
}
/*执行请求对应parentTypeCode的细分素材信息*/
function generateChildTypeGroups(parentTypeCode) {
	var isIcon = (parentTypeCode == '01') ? true : false;
	$.ajax({
		url:'/materialLibrary/getChildTypesByParentTypeCode?userId='+userId,
		type:'get',
		async: false,
		data:{
			parentTypeCode:parentTypeCode,
			isIcon:isIcon
		},
		success:function(data){
			var resultData = JSON.parse(data);
			console.log(resultData);
			$(".material_library_container").empty();
			for(var i = 0; i < resultData.object.length; i++){
				var appendStr = '<div class="child_type_material_group" onclick="enterChildTypeLibrary(this)"><div class="img_cover_container">'+
				'<img class="img_cover" alt="img_cover" src="'+(window.location.protocol + "//" + window.location.host +resultData.object[i].coverImgUrl)+
				'"></div><div class="group_info_container"><div class="group_child_typeCode" style="display:none">'+resultData.object[i].childTypeDomain.typeCode+
				'</div><div class="group_name">'+resultData.object[i].childTypeDomain.typeName+
				'</div><div class="group_material_num">'+resultData.object[i].materialOfChildTypeNum+'</div></div></div>';
				$(".material_library_container").append(appendStr);
			}
			for(var i = 0; i < resultData.object.length; i++){
				var coverImgNodes = $(".img_cover");
				if(resultData.object[i].coverImgUrl.trim() == "" || resultData.object[i].coverImgUrl == null){
					$(coverImgNodes[i]).css("display","none");
				}
			}
		},
		error:function(){
			console.log("error happened .....");
		}
	});
}
/*以列表形式获取整个细分类别列表*/
function generateChildTypeMaterialList(parentTypeCode) {
	var isIcon = (parentTypeCode.trim() == '01') ? true : false;
	$.ajax({
		url:'/materialLibrary/getChildTypesInfosByParentTypeCode?userId='+userId,
		type:'get',
		async: false,
		data:{
			parentTypeCode:parentTypeCode,
			isIcon:isIcon
		},
		success:function(data){
			var resultData = JSON.parse(data);
			console.log(resultData);
			generateChildTypeList(resultData.object);
		},
		error:function(){
			console.log("error happened .....");
		}
	});
}
/*以列表形式生成细分（childType）类别模块*/
function generateChildTypeList(childTypeInfoList) {
	$(".child_Type_list").empty();
	if(childTypeInfoList.length == 0){
		materialListLoadFinish = true;
		console.log("素材列表加载完毕");
	}else{
		for(var i = 0; i < childTypeInfoList.length; i++){
			var appendStr = '';
			appendStr += '<div class="each_child_type_li"><div class="child_type_li_img float_l">'+
				'<img src="../img/文件夹.png" /></div><div class="child_type_name_div float_l">'+
				'<div class="child_type_name_content">' + childTypeInfoList[i].childTypeDomain.typeName +
				'</div><div class="chilt_type_id_content" style="display: none">' + 
				childTypeInfoList[i].childTypeDomain.typeCode + '</div></div><div class="child_type_material_number float_l">'+
				childTypeInfoList[i].materialOfChildTypeNum + '</div></div>';
			$(".child_Type_list").append(appendStr);
		}
		var childTypeLists = $(".each_child_type_li");
		$(childTypeLists[0]).addClass("list_li_active");
	}
	
}
/*生成素材列表*/
function generateMaterialList() {
	var childTypeCode = $(".child_Type_list").find(".list_li_active").find(".chilt_type_id_content").html();
	start = $(".each_material_li").length;
	console.log("childTypeCode:"+childTypeCode);
	$.ajax({
		url:'/materialLibrary/getMaterialsByChildTypeCodeAndNumber?userId='+userId,
		type:'get',
		async:false,
		data:{
			childTypeCode:childTypeCode,
			start:start,
			materialNumber:2
		},
		success:function(data){
			var resultData = JSON.parse(data);
			console.log(resultData);
			if(resultData.status == '200'){
				generateMaterialListGroups(resultData.object);
			}else {
				alert(resultData.message);
			}
		},
		error:function(){
			console.log("error happened .....")
		}
	});
}
function generateMaterialListGroups(materialList){
	if(materialList.length != 0){
		for(var i = 0; i < materialList.length; i++){
			var appendStr = '';
			appendStr += '<div class="each_material_li"><div class="material_li_thumbnail float_l">'+
			'<img class="material_li_thumbnail_img" src="';
			appendStr += window.location.protocol + "//" + window.location.host + materialList[i].thumbnailUrl;
			appendStr += '"></div><div class="material_li_info_div float_l"><div class="material_li_name">';
			appendStr += materialList[i].materialName + "." + materialList[i].materialType;
			appendStr += '</div><div class="material_li_id" style="display: none">';
			appendStr += materialList[i].id;
			appendStr += '</div></div></div>';
			$(".material_list").append(appendStr);
		}
		var materialListBlocks = $(".each_material_li");
		$(materialListBlocks[0]).addClass("list_li_active");
		generateMaterialDetailInfoModal();
	}
}
function generateMaterialDetailInfoModal() {
	$(".material_info_list").css("display","block");
	var materialId = $(".material_list").find(".list_li_active").find(".material_li_id").html();
	/*发送ajax请求，根据materialId查到素材的详细信息并显示*/
	console.log("materialId:"+materialId);
	$.ajax({
		url:'/materialLibrary/getMaterialInfoInLibraryById',
		type:'get',
		async:false,
		data:{
			userId:userId,
			materialId:materialId
		},
		success:function(data){
			var materialData = JSON.parse(data);
			console.log(materialData);
			if(materialData.status == '200'){
				reloadMaterialDetails(materialData.object);
			}else {
				alert(materialData.message);
			}
		},
		error:function(){
			console.log("error happened .....");
		}
	});
}
function reloadMaterialDetails(materialInfos) {
	$("#material_img_preview").attr("src",window.location.protocol + "//" + window.location.host + materialInfos.MaterialDetailsInfoBo.pngUrl);
	$(".material_name_content").html(materialInfos.MaterialDetailsInfoBo.materialName);
	$(".author_head_img").attr("src",window.location.protocol + "//" + window.location.host + materialInfos.MaterialDetailsInfoBo.createUserHeadImgUrl);
	$(".material_author_name").html(materialInfos.MaterialDetailsInfoBo.createUserName);
	$(".material_author_createDate").html(materialInfos.MaterialDetailsInfoBo.uploadFormatTime);
	$("#current_canvas_id_in_modal").html(materialInfos.MaterialDetailsInfoBo.canvasInfoIdPrivate);
	$(".material_author_canvasName").html(materialInfos.MaterialDetailsInfoBo.canvasName);
	$(".history_info_collectionNum").html(materialInfos.MaterialDetailsInfoBo.collectionCount);
	$(".history_info_downloadsNum").html(materialInfos.MaterialDetailsInfoBo.downloadCount);
	$(".material_lable_content").html(materialInfos.MaterialDetailsInfoBo.materialDescription);
	$("#currentMaterialId").val(materialInfos.MaterialDetailsInfoBo.id);
	if(materialInfos.isIcon == true){
		$("#material_png_size_select").css("display","block");
		$("#isIconInput").val(true);
	}else{
		$("#material_png_size_select").css("display","none");
		$("#isIconInput").val(false);
	}
	$("#download_material_ai").html(materialInfos.MaterialDetailsInfoBo.materialType);
	getSimilarMaterial(materialInfos.MaterialDetailsInfoBo.colorPercentage);
}
function getSimilarMaterial(color_percentage) {
	$.ajax({
		url:'/elasticsearch/queryByParam',
		type:'post',
		data:{
			colorPercentage:color_percentage,
			page:0,
			pageSize:3,
			userId:userId
		},
		success:function(data){
			//var resultData = JSON.parse(data);
			var similarMaterialList = JSON.parse(data);
			console.log(similarMaterialList);
			showSimilarMaterialImg(similarMaterialList);
		},
		error:function(){
			console.log("error happened ...");
		}
	});
}

function showSimilarMaterialImg(similarMaterialList) {
	var each_similar_material = $(".each_similar_material_div");
	for(var i = 0; i < each_similar_material.length; i++){
		$(each_similar_material[i]).find(".similar_material_img").attr("src",window.location.protocol + "//" + window.location.host + similarMaterialList.items[i].thumbnailUrl);
		$(each_similar_material[i]).find(".similar_material_id").val(similarMaterialList.items[i].id);
	}
}
$("#childTypeList").on('click','.each_child_type_li',function(){
	materialListLoadFinish = false;
	$(".material_info_list").css("display","none");
	var childTypeList = $(".each_child_type_li");
	for(var i = 0; i < childTypeList.length; i++){
		$(childTypeList[i]).removeClass("list_li_active");
	}
	$(this).addClass("list_li_active");
	start = 0;
	$(".material_list").empty();
	generateMaterialList();
})
$("#materialList").on('click',".each_material_li",function(){
	var materialList = $(".each_material_li");
	for(var i = 0; i < materialList.length; i++){
		$(materialList[i]).removeClass("list_li_active");
	}
	$(this).addClass("list_li_active");
	generateMaterialDetailInfoModal();
})
/*点击下载原文件*/
$("#download_buttons_id").on('click',"#download_material_ai",function(){
	var currentMaterialId = $("#currentMaterialId").val();
	var isIcon = $("#isIconInput").val();
	var confirmDownload = confirm("确认下载原文件？");
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
})
/*点击下载svg文件*/
$("#download_buttons_id").on('click',"#download_material_svg",function(){
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
})
/*点击下载png文件*/
$("#download_buttons_id").on('click',"#download_material_png",function(){
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
})
/*下载文件的方法*/
function downloadStaticFile(imageUrl,imageName) {
    $("#downloadImg").attr("href",window.location.protocol + "//" + window.location.host + imageUrl);
    $("#downloadImg").attr("download",imageName);
    document.getElementById("downloadImg").click(); 
}
/*懒加载*/
$('.material_list').on('mousewheel',function(event, delta){
	var dir = delta > 0 ? 'Up' : 'Down';
    if (dir == 'Down' && materialListLoadFinish == false) {
    	generateMaterialList();
    }
    return false;
})
$("")