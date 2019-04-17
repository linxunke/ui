currentPage=1;
pageNumber=0;
userId = getParameter('userId');
parentTypeCode = getParameter('parentTypeCode');
$(document).ready(function(){
	init();
	$(".parent_type_nav").click(function(){
		$(".parent_type_nav").removeClass("active");
		$(this).addClass("active");
		$(".material_library_container").empty();
		/*请求对应的细分素材信息*/
		var parentTypeCode = $(".nav_head_content").find(".active").find(".typeCode").html();
		generateChildTypeGroups(parentTypeCode);
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