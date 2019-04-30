/**
 * 
 */
var userId = getParameter('userId');
var materialName = getParameter('materialName');
var materialDescription = getParameter('materialName');
var materialTypeCodeParent = getParameter('materialTypeCodeParent');
var page = getParameter('page');
var materialStyleCode = "";
var sort_code = "";
var color_code = "";
var pageSize = getParameter('pageSize');
var parentTypeCount;
var TotalPage = 0;
var materialsAmount = 0;
var colorPercentage = "";
typeCatchData = null;
$(document).ready(function() {
	$.ajax({
		url:'/SearchResult/SearchTypeAndChildType?userId='+userId,
    	data:"",
        type:'post',
        async:false,
       success:function (data) {
    	   parentTypeCount = data.object.length;
    	 //下面需要后台传过来的信息innerHTML,拼接父元素
    	   for(var i = 0;i <= data.object.length-1; i++){
    		   var div = document.createElement('div');
    		   div.className = "float_l typename";
     		   div.id = "typename_" + i;
     		   document.getElementById("div-filtration").appendChild(div);
     		   if(data.object[i].typeName == "风格" || data.object[i].typeName == "排序"){
     			  $("#"+div.id).html('<div><div class="countshow" id='+data.object[i].typeCode+'>'+""+'&nbsp</div><div id="typename_1_'+i+'" onclick="show_child'+(i+1)+'()">'+data.object[i].typeName+'<input class="parent_type_code" type="text" value="0" style="display:none"/>'+'&emsp;<img src="../img/分类_箭头.png"></div></div>');
     		   }else{
         		   $("#"+div.id).html('<div><div class="countshow" id='+data.object[i].typeCode+'>'+0+'&nbsp</div><div id="typename_1_'+i+'" onclick="show_child'+(i+1)+'()">'+data.object[i].typeName+'<input class="parent_type_code" type="text" value="0" style="display:none"/>'+'&emsp;<img src="../img/分类_箭头.png"></div></div>');
     		   }
     		   //下面需要后台传过来的信息innerHTML,拼接第一个子元素（图标，插画等）
     		   var div3 = document.createElement('div');
     		   div3.className = "type_child_name type_child_"+i;
    		   div3.id = "child_"+i+i+i;
    		   div3.style = "width: 99px;height: 36px;text-align: center;line-height: 36px;" +
		  		"font-size: 14px;font-family: PingFangSC-Regular;z-index: 9;" +
		  		"border-radius: 4px;" +
		  		"border: 1px solid rgba(223, 225, 230, 1);" ;
    		   div3.onclick = changeHead;
    		   document.getElementById("typename_" + i).appendChild(div3);
    		   $("#"+div3.id).html(data.object[i].typeName+'<input class="child_type_code" type="text" value="0" style="display:none"/>');
    		   //,拼接子元素
     		   for(var j = 0;j <= data.object[i].childTypeName.length-1; j++){
     			   var div2 = document.createElement('div');
         		   div2.className = "type_child_name type_child_"+i;
         		   div2.id = "child_"+i+j;
         		   div2.style = "width: 99px;height: 36px;text-align: center;line-height: 36px;" +
         		  		"font-size: 14px;font-family: PingFangSC-Regular;z-index: 9;" +
         		  		"border-radius: 4px;" +
         		  		"border: 1px solid rgba(223, 225, 230, 1);" ;
         		   div2.onclick = changeHead;
         		   document.getElementById("typename_" + i).appendChild(div2);
         		   $("#"+div2.id).html(data.object[i].childTypeName[j].typeName+'<input class="child_type_code" type="text" value="'+data.object[i].childTypeName[j].typeCode+'" style="display:none"/>');
     		   }
    	   }
    	   //拼接颜色部分
    	  var div4 = document.createElement('div');
  		  div4.className = "float_l type_color";
  		  div4.id = "type_color";
  		  document.getElementById("div-filtration").appendChild(div4);
  		  div4.onclick = show_child_color;
  		  $("#"+div4.id).html('<div id="color_word">颜色&nbsp</div><div id="color_pane"><input id="search_color_type" type="text" value="" style="display:none"/></div>'+
  				  '<div id="type_child_color"><div id="white" class="child_pane"><input class="color_type" type="text" value="1" style="display:none"/></div><div id="whiteAndBlack" class="child_pane"><img style="width:16px;height:16px;" src="../img/黑白.png"><input class="color_type" type="text" value="2" style="display:none"/></div>'+
  				  '<div id="black" class="child_pane"><input class="color_type" type="text" value="3" style="display:none"/></div><div id="red" class="child_pane"><input class="color_type" type="text" value="4" style="display:none"/></div><div id="blue" class="child_pane"><input class="color_type" type="text" value="5" style="display:none"/></div>'+
  				  '<div id="brown" class="child_pane"><input class="color_type" type="text" value="6" style="display:none"/></div><div id="yellow" class="child_pane"><input class="color_type" type="text" value="7" style="display:none"/></div><div id="roseRed" class="child_pane"><input class="color_type" type="text" value="8" style="display:none"/></div>'+
  				  '<div id="orange" class="child_pane"><input class="color_type" type="text" value="9" style="display:none"/></div><div id="skyBlue" class="child_pane"><input class="color_type" type="text" value="10" style="display:none"/></div><div id="green" class="child_pane"><input class="color_type" type="text" value="11" style="display:none"/></div>'+
  				  '<div id="purple" class="child_pane"><input class="color_type" type="text" value="12" style="display:none"/></div></div>');
       },
       error:function(){
    	   console.log("error happened ---------");
       }
       
	});
	typeCount();
	getPhotoUrl();
	//颜色更改及筛选
	$(".child_pane").click(function(){
		$("#color_pane").html('<input id="search_color_type" type="text" value="" style="display:none"/>')
		var color_type = $(this).find(".color_type").val();
		$("#search_color_type").val(color_type);
		var backcolor = $(this).css("background-color");
		$("#color_pane").css("background-color",backcolor);
		if(color_type==2){
			
			$("#color_pane").html('<input id="search_color_type" type="text" value="'+color_type+'" style="display:none"/><img src="../img/黑白_长文形.png" style="width: 25px;height: 18px;">');
		}
		color_code = $("#search_color_type").val();
		getPhotoUrl();
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
});
$("#result_list_body").on('click','.download',function(){
	var currentMaterialId = $(this).parent().parent().find(".material_id_container").html();
	showModal(currentMaterialId);
});

//统计数量
function typeCount(){
	var mix_code2 = "";
	sort_code = $("#typename_1_5").find(".parent_type_code").val();
	materialStyleCode = $("#typename_1_4").find(".parent_type_code").val();
	color_code = $("#search_color_type").val();
	if(sort_code == 0){
		sort_code = "";
	}
	if(materialStyleCode == 0){
		materialStyleCode = "";
	}
	if(color_code == 0){
		color_code = "";
	}
	console.log(11111111);
	console.log(sort_code);
	console.log(materialStyleCode);
	//可能需要更改
	for(var i = 0; i <= parentTypeCount - 3; i++){
		var code = $("#typename_1_"+i).find(".parent_type_code").val();
		if(code !=0){
			if(mix_code2== ""){
				mix_code2 = mix_code2+code;
			}else{
				mix_code2 = mix_code2+","+code;
			}
		}
	}
	$.ajax({
		url:'/elasticsearch/countMaterialByType?userId='+userId,
    	data:
    	{materialName: decodeURI(materialName),
		materialDescription: decodeURI(materialName),
		materialTypeCodeParent: materialTypeCodeParent,
		materialStyleCode:materialStyleCode,
		sort:sort_code,
		colorType:color_code,
		colorPercentage:colorPercentage,
		materialTypeCodeChild:mix_code2},
        type:'post',
       success:function (data) {
    	   var result = JSON.parse(data);
    	   console.log(result);
    	   if(materialTypeCodeParent.indexOf(",") != -1){
    		   materialsAmount = result.omnipotent;
    	   }else{
    		   for(var i = 0; i <= result.object.length-1; i++){
        		   if(result.object[i].materialCount > materialsAmount){
        			   materialsAmount = result.object[i].materialCount;
        		   }
        	   }
    	   }
    	   //计算总页数
    	   TotalPage =parseInt((materialsAmount % pageSize == 0)?(materialsAmount / pageSize):(materialsAmount / pageSize)+1);
    	   $("#tatolPage").html(TotalPage);
    	   if(result.object == ""){
    		   //这里写死i了，因为前端拿到筛选结果为空，但要改变数量
    		   $(".countshow").html(0+"&nbsp");
    		   $("#typename_4").find(".countshow").html("&nbsp");
    		   $("#typename_5").find(".countshow").html("&nbsp");
    	   }else if(result.object.length<= 4){
    		   $(".countshow").html(0+"&nbsp");
    		   $("#typename_4").find(".countshow").html("&nbsp");
    		   $("#typename_5").find(".countshow").html("&nbsp");   
    		   for(var i = 0; i<= result.object.length-1; i++){
        		   $("#"+result.object[i].materialTypeCodeParent).html(result.object[i].materialCount+"&nbsp");
        	   }
    	   }
    	   
       },
       error:function () {
    	   console.log("error happened ---------");
       }
	});
}
//拼接查询结果---图片
function getPhotoUrl(){
	materialStyleCode = $("#typename_1_4").find(".parent_type_code").val();
	 sort_code = $("#typename_1_5").find(".parent_type_code").val();
	 color_code = $("#search_color_type").val();
	 if(materialStyleCode == 0){
			materialStyleCode = "";
		}
	if(sort_code == 0){
		sort_code = "";
	}
	var mix_code = "";
	//可能需要更改
	for(var i = 0; i <= parentTypeCount - 3; i++){
		var code = $("#typename_1_"+i).find(".parent_type_code").val();
		if(code !=0){
			if(mix_code== ""){
				mix_code = mix_code+code;
			}else{
				mix_code = mix_code+","+code;
			}
		}
	}
	console.log(materialName);
	console.log(decodeURI(materialName));
	$.ajax({
		url:"/elasticsearch/queryByParamIsCollection?userId="+userId,
		data:{
			materialName: decodeURI(materialName),
			materialDescription: decodeURI(materialName),
			materialTypeCodeParent: materialTypeCodeParent,
			materialTypeCodeChild:mix_code,
			materialStyleCode:materialStyleCode,
			sort:sort_code,
			colorType:color_code,
			colorPercentage:colorPercentage,
			page: page,
			pageSize: pageSize
		},
		type:'post',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		async: false,
	       success:function (data) {
	    	   $(".result_photo").remove();
	    	   //var result = JSON.parse(data);
	    	   var result = data;
	    	   console.log(result);
	    	   typeCount();
	    	   for(var i = 0; i<=result.items.length-1; i++){
	    		   var div = document.createElement('div');
	    		   div.className = "result_photo";
	     		   div.id = "photo_" + i;
	     		   document.getElementById("result_list_body").appendChild(div);
	     		   //下面需要后台传过来的信息innerHTML
	     		   if(result.items[i].isCollection == 0){
	     			  $("#"+div.id).html('<div class="color_type_container" style="display:none">'+result.items[i].colorType+'</div><div class="colorPercentage_container" style="display:none">'+result.items[i].colorPercentage+'</div><div class="material_id_container" style="display:none">'+result.items[i].id+'</div><div class="photo_url" ><img style="width:220px;height:160px;" src="'+window.location.protocol + "//" + window.location.host +'/'+result.items[i].thumbnailUrl+'"></div><div class="photo_name">'+result.items[i].materialName+'</div><div class="handles" id="handles'+i+'"><div class="similar float_l"><img src="../img/相似.png" title="相似图片"></div><div class="download"><img style="width:16px;height:16px;float:right;" src="../img/下载.png" title="下载"></div><div class="collect float_r" style="margin-right:5px"><img src="../img/未赞.png" title="收藏"><input class="collection_valid" style="display:none;" type="number" value="'+result.items[i].isCollection+'"/></div></div>');
	     		   }else{
	     			  $("#"+div.id).html('<div class="color_type_container" style="display:none">'+result.items[i].colorType+'</div><div class="colorPercentage_container" style="display:none">'+result.items[i].colorPercentage+'</div><div class="material_id_container" style="display:none">'+result.items[i].id+'</div><div class="photo_url" ><img style="width:220px;height:160px;" src="'+window.location.protocol + "//" + window.location.host +'/'+result.items[i].thumbnailUrl+'"></div><div class="photo_name">'+result.items[i].materialName+'</div><div class="handles" id="handles'+i+'"><div class="similar float_l"><img src="../img/相似.png" title="相似图片"></div><div class="download"><img style="width:16px;height:16px;float:right;" src="../img/下载.png" title="下载"></div><div class="collect float_r" style="margin-right:5px"><img src="../img/已点赞.png" title="取消收藏"><input class="collection_valid" style="display:none;" type="number" value="'+result.items[i].isCollection+'"/></div></div>');
	     		   }
	     		   
	    	   }
	       },
	       error:function(){
	    	   console.log('error happened----');
	       }
			
	});
}
function lastPage(){
	page = $("#currentPage").val()-1;
	if(page>0){
		page = parseInt(page)-1;
	}else{
		page = parseInt(page);
	}
	/*currentPage = currentPage>1?parseInt(currentPage)-1 : parseInt(currentPage);*/
	$("#currentPage").val(page+1);
	getPhotoUrl();
}
function nextPage(){
	page = $("#currentPage").val()-1;
	
	if(page<TotalPage-1){
		page = parseInt(page)+1;
	}else{
		page = parseInt(page);
	}
	$("#currentPage").val(page+1);
	getPhotoUrl();
}
$("#search_by_page").click(function(event){
	page = $("#currentPage").val()-1;
	if(page > TotalPage-1){
		page = TotalPage-1;
	}
	else if(page < 0){
		page = 0;
	}
	$("#currentPage").val(page+1);
	getPhotoUrl();
});
$("#div-filtration").on('click','.type_child_0',function(){
	$(".type_child_name").slideUp();
});
$("#div-filtration").on('click','.type_child_1',function(){
	$(".type_child_name").slideUp();
});
$("#div-filtration").on('click','.type_child_2',function(){
	$(".type_child_name").slideUp();
});
$("#div-filtration").on('click','.type_child_3',function(){
	$(".type_child_name").slideUp();
});
$("#div-filtration").on('click','.type_child_4',function(){
	$(".type_child_name").slideUp();
});
$("#div-filtration").on('click','.type_child_5',function(){
	$(".type_child_name").slideUp();
});
function show_child1() {
	$(".type_child_0").slideToggle();
}
function show_child2() {
	$(".type_child_1").slideToggle();
}
function show_child3() {
	$(".type_child_2").slideToggle();
}
function show_child4() {
	$(".type_child_3").slideToggle();
}function show_child5() {
	$(".type_child_4").slideToggle();
}
function show_child6() {
	$(".type_child_5").slideToggle();
}
function show_child_color() {
	$("#type_child_color").slideToggle();
}
function changeHead(){
	//更改头部时，将页面转为1
	page = 0;
	$("#currentPage").val(page+1);
	var childname = $(this).html();
	var cc = $("#typename_1_0").html();
	var parentId = $(this).parent().attr('id');
	var child_code = $(this).find(".child_type_code").val();
	$("#"+parentId).children(":first").children(":last").html(childname+'&emsp;<img src="../img/分类_箭头.png"><input class="parent_type_code" type="text" value="'+child_code+'" style="display:none"/>');
	getPhotoUrl();
}
function showModal(materialId) {
	$("#currentMaterialId").val(materialId);
	$("#material_type_info_div").empty();
	getMaterialInfoDetailsByMaterialId(materialId); //
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
$("#result_list_body").on('click','.collect',function(){
	var operationCode;
	if($(this).find(".collection_valid").val() == 0){
		$(this).html('<img src="../img/已点赞.png" title="取消收藏"><input class="collection_valid" style="display:none;" type="number" value="1"/>');
		operationCode = 1;
	}else{
		$(this).html('<img src="../img/未赞.png" title="收藏"><input class="collection_valid" style="display:none;" type="number" value="0"/>');
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
				
			}else{
				
			}
		},
		error:function(){
			console.log("error happened .....");
		}
	});
});
$("#result_list_body").on('click','.similar',function(){
	color_code=$(this).parent().parent().find(".color_type_container").html();
	colorPercentage=$(this).parent().parent().find(".colorPercentage_container").html();
	getPhotoUrl();
});
$("#similar_material_div_in_modal").on("click",".each_similar_material_div",function(){
	var materiaId = $(this).find(".similar_material_id").val();
	closeModal();
	showModal(materiaId);
})