/**
 * 
 */
var userId = getParameter('userId'); 
var pageSize = 8;
var currentPage = 1;
var Page=1;
$(document).ready(function(){
	$("#manage_upload").click(function() {
		window.open("/userpage/toMaterialUpload?userId="+userId);
	});
	getUserName(userId);
	getMaterialCount(userId);
	getHistoryMeterial(currentPage);
	/*清空下载*/
	$("#clear_download").click(function(){
		var confirmDelete = confirm("确认删除下载历史吗？");
		if(confirmDelete == true){
			$.ajax({
				url:'/historyandcollection/deleteHistoryOrCollectionMaterial',
				data:{
					userId:userId,
					type:2
				},
				type:'post',
				success:function(data){
					var resultData = JSON.parse(data);
					if(resultData.status == "200"){
						location.reload();
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
	$("#myBoard").click(function(){
    	window.location.href="/userpage/work_manage?userId="+userId;
    });
    $("#myCollection").click(function(){
    	window.location.href="/userpage/toCollectionManage?userId="+userId;
    });
    $("#myHistory").click(function(){
    	window.location.href="/userpage/toHistoryManage?userId="+userId;
    });
    $("#superManage").click(function(){
    	
    });
});
$("#material_list").on('click','.download',function(){
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
    		type: 2
    	},
    	type:'post',
    	success:function(data){
    		var resultData = JSON.parse(data);
    		$("#materialcount").html("收藏"+resultData.object+"个素材");
    		Page = (resultData.object % pageSize == 0)?resultData.object / pageSize:parseInt(resultData.object / pageSize)+1;
    		$("#tatolPage").html(Page);
    		console.log(resultData);
 			
    	},
    	error:function(){
    		console.log('getMaterialCount error happened----');
    	}
    });
}
function getHistoryMeterial(currentPage){
	$.ajax({
    	url:'/historyandcollection/selectHistoryOrCollectionMaterial',
    	data:{
    		userId: userId,
    		type: 2,
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
function lastPage(){
	var currentPage = $("#currentPage").val();
	$(".result_photo").remove();
	if(currentPage>1){
		currentPage = parseInt(currentPage)-1;
	}else{
		currentPage = parseInt(currentPage);
	}
	/*currentPage = currentPage>1?parseInt(currentPage)-1 : parseInt(currentPage);*/
	$("#currentPage").val(currentPage);
	getHistoryMeterial(currentPage);
}
function nextPage(){
	var currentPage = $("#currentPage").val();
	$(".result_photo").remove();
	if(currentPage<Page){
		currentPage = parseInt(currentPage)+1;
	}else{
		currentPage = parseInt(currentPage);
	}
	$("#currentPage").val(currentPage);
	getHistoryMeterial(currentPage);
}
$("#search_by_page").click(function(event){
	var currentPage = $("#currentPage").val();
	$(".result_photo").remove();
	if(currentPage > Page){
		currentPage = Page;
	}
	else if(currentPage < 1){
		currentPage = 1;
	}
	$("#currentPage").val(currentPage);
	getHistoryMeterial(currentPage);
});
$("#material_list").on('click','.photo_url',function(){
	var currentMaterialId = $(this).parent().find(".material_id_container").html();
	showModal(currentMaterialId);
});
function showModal(materialId) {
	$("#currentMaterialId").val(materialId);
	$("#material_type_info_div").empty();
	getMaterialInfoDetailsByMaterialId(materialId); //
	$(".modal").css("display","block");
	console.log(materialId);
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
			}else {
				alert(materialData.message);
			}
		},
		error:function(){
			console.log("error happened .....");
		}
	});
	
}
/*展示详情页的信息*/
function reloadMaterialInfoInModal(materialInfos) {
	if(materialInfos.isIcon){
		$("#material_png_size_select").css("display","inline");
	}else {
		$("#material_png_size_select").css("display","none");
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
/*获取相似素材的信息*/
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
			showSimilarMaterialImg(similarMaterialList);
		},
		error:function(){
			console.log("error happened ...");
		}
	});
}
/*显示相似素材的图片*/
function showSimilarMaterialImg(similarMaterialList) {
	var each_similar_material = $(".each_similar_material_div");
	for(var i = 0; i < each_similar_material.length; i++){
		$(each_similar_material[i]).find(".similar_material_img").attr("src",window.location.protocol + "//" + window.location.host + similarMaterialList.items[i].thumbnailUrl);
		$(each_similar_material[i]).find(".similar_material_id").val(similarMaterialList.items[i].id);
	}
}
/*下载文件的方法*/
function downloadStaticFile(imageUrl,imageName) {
    $("#downloadImg").attr("href",window.location.protocol + "//" + window.location.host + imageUrl);
    $("#downloadImg").attr("download",imageName);
    document.getElementById("downloadImg").click(); 
}
/*收藏操作*/
$("#material_list").on('click','.collect',function(){
	var operationCode;
	var confirmCollect = confirm("确认取消收藏吗？");
	if(confirmCollect == true){
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
				if(result.status == "200"){
					console.log("收藏操作成功");
					location.reload();
				}else{
					console.log("收藏操作失败");
				}
			},
			error:function(){
				console.log("error happened .....");
			}
		});
	}
	
});
$("#similar_material_div_in_modal").on("click",".each_similar_material_div",function(){
	var materiaId = $(this).find(".similar_material_id").val();
	closeModal();
	showModal(materiaId);
})