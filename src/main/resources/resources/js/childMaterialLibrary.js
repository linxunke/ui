currentPage=1;
pageNumber=0;
userId = getParameter('userId');
childTypeCode = getParameter('childTypeCode');
typeCatchData = null;
$(document).ready(function() {
	init(userId,childTypeCode,currentPage);
	/*点击父级类别返回对应的页面*/
	$("#nav_bar_parent").click(function() {
		var parentTypeCode = $(this).find("#nav_bar_parent_id").val();
		window.location.href="/userpage/toMaterialLibrary?userId="+userId+"&parentTypeCode="+parentTypeCode;
	});
	/*点击相似图片*/
	$(".each_similar_material_div").click(function() {
		var theMaterialId = $(this).find(".similar_material_id").val();
		closeModal();
		showModal(theMaterialId);
	});
	/*点击跳转上一页*/
	$("#lastPage").click(function() {
		getMaterialListInLibrary(userId,childTypeCode,currentPage<=1?1:currentPage-1);
	});
	/*点击跳转下一页*/
	$("#nextPage").click(function() {
		getMaterialListInLibrary(userId,childTypeCode,(currentPage>=pageNumber?pageNumber:(currentPage+1)));
	});
	/*点击根据页数搜索*/
	$("#search_by_page").click(function() {
		var theCurrentPage = $("#currentPage").val();
		if(theCurrentPage<1 || theCurrentPage > pageNumber){
			alert("要查找的页数不正确,请重新输入！");
		}else {
			getMaterialListInLibrary(userId,childTypeCode,theCurrentPage);
		}
		
	});
	
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
					if(resultData.status == '200'){
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
						console.log(resultData.object.imageUrl);
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
					if(resultData.status == '200'){
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
/*初始化方法*/
function init(userId,childTypeCode,currentPage) {
	getLibraryTypeInfos(userId,childTypeCode);
	getMaterialListInLibrary(userId,childTypeCode,currentPage);
}
/*生成当前分类的面包屑导航栏*/
function getLibraryTypeInfos (userId,childTypeCode) {
	$.ajax({
		url:'/materialLibrary/getCurrentTypeInfos',
		type:'get',
		data:{
			userId:userId,
			childTypeCode:childTypeCode
		},
		success:function(data){
			var resultData = JSON.parse(data);
			if(resultData.status == '200'){
				for(var i = 0; i < resultData.object.length; i++){
					if(resultData.object[i].typeCode == childTypeCode){
						$("#nav_bar_child").find("#nav_bar_child_id").val(resultData.object[i].typeCode);
						$("#nav_bar_child").find("#nav_bar_child_name").html(resultData.object[i].typeName);
					}else {
						$("#nav_bar_parent").find("#nav_bar_parent_id").val(resultData.object[i].typeCode);
						$("#nav_bar_parent").find("#nav_bar_parent_name").html(resultData.object[i].typeName);
					}
				}
			}else {
				alert(resultData.message);
			}
		},
		error:function(){
			console.log("error happened ....");
		}
	});
}
/*请求当前细分类别的素材信息*/
function getMaterialListInLibrary(userId,childTypeCode,currentPage) {
	$.ajax({
		url:'/materialLibrary/getMaterialsByChildTypeCode',
		type:'get',
		data:{
			userId:userId,
			childTypeCode:childTypeCode,
			currentPage:currentPage
		},
		success:function(data){
			var resultData = JSON.parse(data);
			if(resultData.status == '200'){
				if(resultData.object.isIcon){
					showMaterialAsIcon(resultData);
				}else {
					showMaterialAsImg(resultData);
				}
			}
		},
		error:function(){
			console.log("error happened .....")
		}
	});
}
/*以图标形式展示素材*/
function showMaterialAsIcon(data){
	var materialInfoList = data.object.pageInfoUtil.object;
	$(".material_show_body").empty();
	for(var i = 0; i < data.object.pageInfoUtil.object.length; i++){
		$(".material_show_body").append('<div class="each_material_icon_div">'+
				'<div class="material_image_div"><img class="material_img"  '+
				'onclick="showModal('+materialInfoList[i].id+')" src="'+window.location.protocol + "//" + window.location.host +"/"+materialInfoList[i].pngUrl+'">'+
				'<div class="material_name_div"><span class="material_id" style="display: none">'+materialInfoList[i].id+
				'</span><span class="material_name">'+materialInfoList[i].materialName+'</span></div></div>');	
	}
	currentPage = data.object.pageInfoUtil.currentPage;
	pageNumber = data.object.pageInfoUtil.pageNumber;
	refreshPageModual(currentPage,pageNumber);
}
/*以图片形式展示素材*/
function showMaterialAsImg(data) {
	var materialInfoList = data.object.pageInfoUtil.object;
	$(".material_show_body").empty();
	for(var i = 0; i < data.object.pageInfoUtil.object.length; i++){
		$(".material_show_body").append('<div class="each_material_img_div"><div class="material_image_block">'+
				'<img onclick="showModal('+materialInfoList[i].id+')" class="material_picture" src="'+window.location.protocol + "//" + window.location.host +"/"+materialInfoList[i].thumbnailUrl+'"></div><div class="material_name_block">'+
				'<span class="material_id" style="display: none">'+materialInfoList[i].id+'</span><span class="material_name">'+materialInfoList[i].materialName+'</span>'+
				'</div></div>');	
	}
	currentPage = data.object.pageInfoUtil.currentPage;
	pageNumber = data.object.pageInfoUtil.pageNumber;
	refreshPageModual(currentPage,pageNumber);
}
/*刷新显示页码模块*/
function refreshPageModual(currentPage,pageNumber) {
	$("#currentPage").val(currentPage);
	$("#pageNum").html(pageNumber);
}
/*显示模态框*/
function showModal(materialId) {
	$("#currentMaterialId").val(materialId);
	getMaterialInfoDetailsByMaterialId(materialId);
	/*通过搜索引擎查找3个相似图片*/
	$(".modal").css("display","block");
}
/*关闭模态框*/
function closeModal() {
	$(".modal").css("display","none");
}
/*获取图片的全部信息*/
function getMaterialInfoDetailsByMaterialId(materialId) {
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
			if(materialData.status == '200'){
				reloadMaterialInfoInModal(materialData.object);
			}
		},
		error:function(){
			console.log("error happened .....");
		}
	});
	
}
function reloadMaterialInfoInModal(materialInfos) {
	if(materialInfos.isIcon){
		$("#material_png_size_select").css("display","inline");
	}else {
		$("#material_png_size_select").css("display","none");
	}
	if(materialInfos.MaterialDetailsInfoBo.materialType=='ai'){
		$("#download_material_ai").html('AI');
	}else if(materialInfos.MaterialDetailsInfoBo.materialType=='psd'){
		$("#download_material_ai").html('PSD');
	}
	$("#isIconInput").val(materialInfos.isIcon);
	$("#material_img_preview").attr("src",window.location.protocol + "//" + window.location.host + materialInfos.MaterialDetailsInfoBo.pngUrl);
	$(".material_name_content").html(materialInfos.MaterialDetailsInfoBo.materialName);
	$(".author_head_img").attr("src",window.location.protocol + "//" + window.location.host +"/" + materialInfos.MaterialDetailsInfoBo.createUserHeadImgUrl);
	$(".material_author_name").html(materialInfos.MaterialDetailsInfoBo.createUserName);
	$(".material_author_createDate").html(materialInfos.MaterialDetailsInfoBo.uploadFormatTime);
	$("#current_canvas_id_in_modal").html(materialInfos.MaterialDetailsInfoBo.canvasInfoIdPrivate);
	$(".material_author_canvasName").html(materialInfos.MaterialDetailsInfoBo.canvasName);
	$(".history_info_collectionNum").html(materialInfos.MaterialDetailsInfoBo.collectionCount);
	$(".history_info_downloadsNum").html(materialInfos.MaterialDetailsInfoBo.downloadCount);
	$(".material_lable_content").html(materialInfos.MaterialDetailsInfoBo.materialDescription);
	$("#current_material_color_percentage_in_modal").html(materialInfos.MaterialDetailsInfoBo.colorPercentage);
	$("#currentMaterialId").val(materialInfos.MaterialDetailsInfoBo.id);
	getSimilarMaterial(materialInfos.MaterialDetailsInfoBo.colorPercentage);
}
/*获取相似图片的信息*/
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
			var similarMaterialList = JSON.parse(data);
			showSimilarMaterialImg(similarMaterialList);
		},
		error:function(){
			console.log("error happened ...");
		}
	});
}
/*显示相似图片*/
function showSimilarMaterialImg(similarMaterialList) {
	var each_similar_material = $(".each_similar_material_div");
	for(var i = 0; i < each_similar_material.length; i++){
		if(undefined != similarMaterialList.items[i]){
			$(each_similar_material[i]).find(".similar_material_img").attr("src",window.location.protocol + "//" + window.location.host + similarMaterialList.items[i].thumbnailUrl);
			$(each_similar_material[i]).find(".similar_material_id").val(similarMaterialList.items[i].id);
		}else{
			$(each_similar_material[i]).hide();
		}
	}
}
/*下载文件的方法*/
function downloadStaticFile(imageUrl,imageName) {
	console.log(window.location.protocol + "//" + window.location.host + imageUrl);
    $("#downloadImg").attr("href",window.location.protocol + "//" + window.location.host + imageUrl);
    $("#downloadImg").attr("download",imageName);
    document.getElementById("downloadImg").click(); 
}