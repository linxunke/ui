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
		if(theCurrentPage<1 || theCurrentPage > pageNum){
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
			console.log(resultData);
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
			console.log(resultData);
			if(resultData.status == '200'){
				if(resultData.object.isIcon){
					showMaterialAsIcon(resultData);
				}else {
					showMaterialAsImg(resultData);
				}
			}else {
				alert(resultData.message);
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
/*拼接显示详情页的信息*/
function showMaterialDetailsInModal(data,typeCatchData) {
	var materialPngSrc = window.location.protocol + "//" + window.location.host +"/"
	+ data.object.materialInfo.pngUrl;
	$("#material_img_preview").attr("src",materialPngSrc);
	console.log("png_src"+materialPngSrc);
	var isIcon = materialIsAIcon(data.object.materialInfo.materialTypeInfoList);
	if(isIcon){
		$("#material_png_size_select").css("display","inline");
		$("#isIconInput").val(true);
		console.log("isIcon");
	}else{
		$("#material_png_size_select").css("display","none");
		$("#isIconInput").val(false);
		console.log("not a Icon");
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
/*判断当前素材是否是一个图标*/
function materialIsAIcon(materialTypeInfoList) {
	for(var i =0; i < materialTypeInfoList.length; i++){
		if(materialTypeInfoList[i].materialTypeCodeParent != '01'){
			return false;
		}
	}
	return true;
}
/*添加num个类别模块*/
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
/*下载文件的方法*/
function downloadStaticFile(imageUrl,imageName) {
    console.log(imageUrl);
    console.log(imageName);
    $("#downloadImg").attr("href",window.location.protocol + "//" + window.location.host + imageUrl);
    $("#downloadImg").attr("download",imageName);
    document.getElementById("downloadImg").click(); 
}