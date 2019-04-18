/**
 * 
 */
var userId = getParameter('userId');
var materialName = getParameter('materialName');
var materialDescription = getParameter('materialName');
var materialTypeCodeParent = getParameter('materialTypeCodeParent');
var page = getParameter('page');
var pageSize = getParameter('pageSize');
var parentTypeCount;
typeCatchData = null;
$(document).ready(function() {
	$.ajax({
		url:'/SearchResult/SearchTypeAndChildType?userId='+userId,
    	data:"",
        type:'post',
        async:false,
       success:function (data) {
    	   console.log(data);
    	   parentTypeCount = data.object.length;
    	   //console.log("parentTypeCount="+parentTypeCount);
    	 //下面需要后台传过来的信息innerHTML,拼接父元素
    	   for(var i = 0;i <= data.object.length-1; i++){
    		   var div = document.createElement('div');
    		   div.className = "float_l typename";
     		   div.id = "typename_" + i;
     		   document.getElementById("div-filtration").appendChild(div);
     		   $("#"+div.id).html('<div><div id='+data.object[i].typeCode+'>'+0+'&nbsp</div><div id="typename_1_'+i+'" onclick="show_child'+(i+1)+'()">'+data.object[i].typeName+'<input class="parent_type_code" type="text" value="0" style="display:none"/>'+'&emsp;<img src="../img/分类_箭头.png"></div></div>');
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
	$(".child_pane").click(function(){
		var color_type = $(this).find(".color_type").val();
		var color_code = $(search_color_type).val(color_type);
		var backcolor = $(this).css("background-color");
		$("#color_pane").css("background-color",backcolor);
		
		//console.log($(search_color_type).val());
	});
	
	/*点击下载弹出弹窗*/
	$(".download").click(function () {
		var currentMaterialId = $(this).parent().parent().find(".material_id_container").html();
		showModal(currentMaterialId);
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
function typeCount(){
	var mix_code2 = "";
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
	//console.log("===="+mix_code2);
	$.ajax({
		url:'/elasticsearch/countMaterialByType?userId='+userId,
    	data:"",
    	/*materialName: materialName,
		materialDescription: materialName,
		materialTypeCodeParent: materialTypeCodeParent,*/
        type:'post',
       success:function (data) {
    	   var result = JSON.parse(data);
    	   console.log(result);
    	   console.log("typeCount====="+result.object);
    	   for(var i = 0; i<= result.object.length-1; i++){
    		   $("#"+result.object[i].materialTypeCodeParent).html(result.object[i].materialCount+"&nbsp");
    	   }
       },
       error:function () {
    	   console.log("error happened ---------");
       }
	});
}
function getPhotoUrl(){
	$.ajax({
		url:"/elasticsearch/queryByParam?userId="+userId,
		data:{
			materialName: materialName,
			materialDescription: materialName,
			materialTypeCodeParent: materialTypeCodeParent,
			page: 0,
			pageSize: 15
		},
		type:'post',
		async: false,
	       success:function (data) {
	    	   var result = JSON.parse(data);;
	    	   console.log(result);
	    	   for(var i = 0; i<=result.items.length-1; i++){
	    		   var div = document.createElement('div');
	    		   div.className = "result_photo";
	     		   div.id = "photo_" + i;
	     		   document.getElementById("result_list_body").appendChild(div);
	     		   //下面需要后台传过来的信息innerHTML
	     		   $("#"+div.id).html('<div class="material_id_container" style="display:none">'+result.items[i].id+'</div><div class="photo_url" ><img style="width:220px;height:160px;" src="'+window.location.protocol + "//" + window.location.host +'/'+result.items[i].thumbnailUrl+'"></div><div class="photo_name">'+result.items[i].materialName+'</div><div class="handles"><div class="copy float_l"><img src=""></div><div class="collect float_l"><img src=""></div><div class="download"><img style="width:15px;height:15px;float:right;" src="../img/下载.png"></div></div>');
	    	   }
	       },
	       error:function(){
	    	   console.log('error happened----');
	       }
			
	});
}
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
	var childname = $(this).html();
	var cc = $("#typename_1_0").html();
	var parentId = $(this).parent().attr('id');
	var child_code = $(this).find(".child_type_code").val();
	//console.log("parentId="+parentId);
	//console.log("child_code="+child_code);
	$("#"+parentId).children(":first").children(":last").html(childname+'&emsp;<img src="../img/分类_箭头.png"><input class="parent_type_code" type="text" value="'+child_code+'" style="display:none"/>');
	//console.log($("#"+parentId).children(":first").children(":last").find(".parent_type_code").val());
	//向后台传参数
	var materialStyleCode = $("#typename_1_4").find(".parent_type_code").val();
	var sort_code = $("#typename_1_5").find(".parent_type_code").val();
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
	//console.log(mix_code);
	//console.log(materialStyleCode);
	//console.log(sort_code);
	if(materialStyleCode == 0){
		materialStyleCode = "";
	}
	if(sort_code == 0){
		sort_code = "";
	}
	$.ajax({
		url:"/elasticsearch/queryByParam?userId="+userId,
		data:{
			materialName: materialName,
			materialDescription: materialName,
			materialTypeCodeParent: materialTypeCodeParent,
			materialTypeCodeChild:mix_code,
			materialStyleCode:materialStyleCode,
			sort:sort_code,
			page: 0,
			pageSize: 15
		},
		type:'post',
		async: false,
	       success:function (data) {
	    	   $(".result_photo").remove();
	    	   var result = JSON.parse(data);;
	    	   console.log(result);
	    	   typeCount();
	    	   for(var i = 0; i<=result.items.length-1; i++){
	    		   var div = document.createElement('div');
	    		   div.className = "result_photo";
	     		   div.id = "photo_" + i;
	     		   document.getElementById("result_list_body").appendChild(div);
	     		   //下面需要后台传过来的信息innerHTML
	     		   $("#"+div.id).html('<div class="photo_url" ><img style="width:220px;height:160px;" src="'+window.location.protocol + "//" + window.location.host +'/'+result.items[i].thumbnailUrl+'"></div><div class="photo_name">'+result.items[i].materialName+'</div><div class="handles"><div class="copy float_l"><img src=""></div><div class="collect  float_l"><img src=""></div><div class="download"><img style="width:15px;height:15px;float:right;" src="../img/下载.png"></div></div>');
	    	   }
	       },
	       error:function(){
	    	   console.log('error happened----');
	       }		
	});
}
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