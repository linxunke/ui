/**
 * 
 */
var userId = getParameter('userId');
var materialName = getParameter('materialName');
var materialDescription = getParameter('materialName');
var materialTypeCodeParent = getParameter('materialTypeCodeParent');
var page = getParameter('page');
var pageSize = getParameter('pageSize');
$(document).ready(function() {
	$.ajax({
		url:'/SearchResult/SearchTypeAndChildType?userId='+userId,
    	data:"",
        type:'post',
       success:function (data) {
    	   console.log(data);
    	   console.log(data.object[1].typeName);
    	   console.log(data.object[1].typeCode);
    	   console.log(data.object[1].childTypeName[0]);
    	   console.log(data.object[1].childTypeName[1]);
    	   for(var i = 0;i <= data.object.length-1; i++){
    		   var div = document.createElement('div');
    		   div.className = "float_l typename";
     		   div.id = "typename_" + i;
     		   document.getElementById("div-filtration").appendChild(div);
     		   //下面需要后台传过来的信息innerHTML
     		   $("#"+div.id).html('<div><div>'+1113+'&nbsp</div><div id="typename_1_'+i+'" onclick="show_child'+(i+1)+'()">'+data.object[i].typeName+'&nbsp<img src="../img/分类_箭头.png"></div></div>');
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
         		   $("#"+div2.id).html(data.object[i].childTypeName[j]);
     		   }
    	   }
    	  var div = document.createElement('div');
  		  div.className = "float_l type_color";
  		  div.id = "type_color";
  		  document.getElementById("div-filtration").appendChild(div);
  		  $("#"+div.id).html('<div id="color_word">颜色&nbsp</div><div id="color_pane"></div>');
       },
       error:function(){
    	   console.log("error happened ---------");
       }
	});
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
	$(".type_child_color").slideToggle();
}
function changeHead(){
	var childname = $(this).html();
	var cc = $("#typename_1_0").html();
	var parentId = $(this).parent().attr('id');
	console.log(childname);
	console.log(childname == "全部图标");
	console.log(cc);
	console.log("parentId="+parentId);
	console.log($("#"+parentId).children(":first").children(":last").html());
	if(childname == "全部图标"){
		$("#typename_1_0").html('图标&nbsp<img src="../img/分类_箭头.png">');
	}else{
		$("#"+parentId).children(":first").children(":last").html(childname+'&nbsp<img src="../img/分类_箭头.png">');
	}
}
/*//点击child时，改变头部的值
$("#typename_0").on("click",".type_child_name",function(){
    //do something
	var childname = $(this).html();
	var cc = $("#typename_1_0").html();
	console.log(childname);
	console.log(childname == "全部图标");
	console.log(cc);
	if(childname == "全部图标"){
		$("#typeame_1_0").html('图标&nbsp<img src="../img/分类_箭头.png">');
	}else{
		$("#typeame_1_0").html(childname+'&nbsp<img src="../img/分类_箭头.png">');
	}
});
$("#type_child2").on("click",".type_child_name",function(){
    //do something
	var childname = $(this).html();
	var cc = $("#typeame_2_1").html();
	if(childname == "全部插画"){
		$("#typeame_2_1").html('插画&nbsp<img src="../img/分类_箭头.png">');
	}
	else if(childname == "缺省画面"){
		$("#typeame_2_1").html('缺省&nbsp<img src="../img/分类_箭头.png">');
	}else{
		$("#typeame_2_1").html(childname+'&nbsp<img src="../img/分类_箭头.png">');
	}
});
$("#type_child3").on("click",".type_child_name",function(){
    //do something
	var childname = $(this).html();
	var cc = $("#typeame_3_1").html();
	if(childname == "全部素材"){
		$("#typeame_3_1").html('项目素材&nbsp<img src="../img/分类_箭头.png">');
	}else{
		$("#typeame_3_1").html(childname+'&nbsp<img src="../img/分类_箭头.png">');
	}
});
$("#type_child4").on("click",".type_child_name",function(){
    //do something
	var childname = $(this).html();
	var cc = $("#typeame_4_1").html();
	if(childname == "全部设计"){
		$("#typeame_4_1").html('运营设计&nbsp<img src="../img/分类_箭头.png">');
	}else{
		$("#typeame_4_1").html(childname+'&nbsp<img src="../img/分类_箭头.png">');
	}
});
$("#type_child5").on("click",".type_child_name",function(){
    //do something
	var childname = $(this).html();
	var cc = $("#typeame_5_1").html();
	if(childname == "全部风格"){
		$("#typeame_5_1").html('风格&nbsp<img src="../img/分类_箭头.png">');
	}else{
		$("#typeame_5_1").html(childname+'&nbsp<img src="../img/分类_箭头.png">');
	}
});
$("#type_child6").on("click",".type_child_name",function(){
    //do something
	var childname = $(this).html();
	var cc = $("#typeame_6_1").html();
	if(childname == "最新"){
		$("#typeame_6_1").html('最新&nbsp<img src="../img/分类_箭头.png">');
	}else{
		$("#typeame_6_1").html(childname+'&nbsp<img src="../img/分类_箭头.png">');
	}
});*/
