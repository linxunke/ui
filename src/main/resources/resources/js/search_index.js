var userId = getParameter('userId'); 
var page = 0;
var pageSize = 12;
var AllTypeCodeParent = "";
$(document).ready(function () {
	
	$.ajax({
		url:'/SearchIndex/SearchThreePhoto?userId='+userId,
    	data:"",
        type:'post',
        async:false,
        success:function (data) {
    	   var result = (data);
    	   var firstPhoto = document.getElementById("#photo_img1");
    	   var secondPhoto = document.getElementById("#photo_img2");
    	   var thirdPhoto = document.getElementById("#photo_img3");
    	   if(result.status == '200'){
    	   $("#photo_img1").append('<img style="width:220px;height:160px;margin-top: 16px" src="'+window.location.protocol + "//" + window.location.host +'/'+result.object[0].thumbnailUrl+'">');
    	   $("#photo_head1").html('<img style="border-radius:20px;width:40px;height:40px" src="'+window.location.protocol + "//" + window.location.host +'/'+result.object[0].userPhotoUrl+'">');
    	   $("#photo_username1").html(result.object[0].userName);
    	   $("#photo_type1").html(result.object[0].materialType);
    	   $("#photo_img2").html('<img style="width:220px;height:160px;margin-top: 16px" src="'+window.location.protocol + "//" + window.location.host +'/'+result.object[1].thumbnailUrl+'">');
    	   $("#photo_head2").html('<img style="border-radius:20px;width:40px;height:40px" src="'+window.location.protocol + "//" + window.location.host +'/'+result.object[1].userPhotoUrl+'">');
    	   $("#photo_username2").html(result.object[1].userName);
    	   $("#photo_type2").html(result.object[1].materialType);
    	   $("#photo_img3").html('<img style="width:220px;height:160px;margin-top: 16px" src="'+window.location.protocol + "//" + window.location.host +'/'+result.object[2].thumbnailUrl+'">');
    	   $("#photo_head3").html('<img style="border-radius:20px;width:40px;height:40px" src="'+window.location.protocol + "//" + window.location.host +'/'+result.object[2].userPhotoUrl+'">');
    	   $("#photo_username3").html(result.object[2].userName);
    	   $("#photo_type3").html(result.object[2].materialType);
    	   }
    	   else if(result.status == '500'){
    		   alert(result.message);
    	   }
       },
       error:function(){
    	   console.log('error happened----');
       } 
	});
	countNumber();

});
function countNumber(){
	$.ajax({
		url:'/SearchIndex/countNumber?userId='+userId,
    	data:"",
        type:'post',
        async:false,
       success:function (data) {
    	   var result = (data);
    	   $("#count_icon").html(result.object[0]);
    	   $("#count_drawing").html(result.object[1]);
       },
       error:function(){
    	   console.log('error happened----');
       }
		
	});
}
function chooseSearchWord(){
	console.log("chooseSearchWord函数");
	$.ajax({
		url:'/SearchIndex/chooseSearchWord?userId='+userId,
    	data:"",
        type:'post',
        async:false,
       success:function (data) {
    	   var result = data;
    	   for(var i = 0; i < result.object.length; i++){
    		   console.log(i);
    		   var div = document.createElement('div');
     		  div.className = "combo_box_child";
     		  div.id = "combo_box" + i;
     		  var divid = div.id;
     		 document.getElementById("combo_box").appendChild(div);
     		$("#"+div.id).html('<span>'+result.object[i].typeName+'</span>'+'<div class="hidedivForCode">'+result.object[i].typeCode+'</div>');
     		div.onclick = changeChooseWord;
     		AllTypeCodeParent = result.object[i].typeCode+","+AllTypeCodeParent;
    	   }
    	   AllTypeCodeParent = AllTypeCodeParent.substring(0, AllTypeCodeParent.lastIndexOf(','));
    	   console.log("hhh="+AllTypeCodeParent);
       },
       error:function(){
    	   console.log('error happened----');
       }
		
	});
}

$("#combo_box").click(function() {
	$(".combo_box_child").slideToggle("fast");
});

function changeChooseWord(){
	var a = $(this).children("span").text();
	var b =  $(this).children("div").html();
	$("#combo_box_head").html('<span>'+a+'</span>'+'<div class="hidedivForCode">'+b+'</div>');
}

combo_box_first.addEventListener('click',function(){
	var c = $(this).html();
	$("#combo_box_head").html('<span>'+$(this).html()+'</span>');
	console.log("------"+$("#combo_box_head").children("span").text());
});

$(document).keydown(function(event){

	if(event.keyCode == 13){
	elasticSearch();
	}

});
function elasticSearch(){
	var materialName = document.getElementById("searchBox_content").value;
	var typeName = $("#combo_box_head").children("span").text();
	var materialTypeCodeParent = $("#combo_box_head").children("div").html();
	if(typeName == "全部"){
		typeName = "";
		materialTypeCodeParent = AllTypeCodeParent;
	}
	var materialNameCode = encodeURI(encodeURI(materialName));
	console.log(materialNameCode);
	window.location.href = "/userpage/toSearchResult?userId="+userId+"&materialName="+materialNameCode+
	"&materialDescription="+materialNameCode+"&materialTypeCodeParent="+materialTypeCodeParent+
	"&page="+page+"&pageSize="+pageSize;
}