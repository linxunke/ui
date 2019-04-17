var userId = getParameter('userId'); 
$(document).ready(function () {
	$.ajax({
		url:'/SearchIndex/SearchThreePhoto?userId='+userId,
    	data:"",
        type:'post',
       success:function (data) {
    	   var result = (data);
    	   console.log(result);
    	   var firstPhoto = document.getElementById("#photo_img1");
    	   var secondPhoto = document.getElementById("#photo_img2");
    	   var thirdPhoto = document.getElementById("#photo_img3");
    	   console.log(window.location.protocol + "//" + window.location.host +'/'+result.object[0].thumbnailUrl);
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
	chooseSearchWord();
});
function countNumber(){
	$.ajax({
		url:'/SearchIndex/countNumber?userId='+userId,
    	data:"",
        type:'post',
       success:function (data) {
    	   var result = (data);
    	   console.log(result);
    	   $("#count_icon").html(result.object[0]);
    	   $("#count_drawing").html(result.object[1]);
       },
       error:function(){
    	   console.log('error happened----');
       }
		
	});
}
function chooseSearchWord(){
	$.ajax({
		url:'/SearchIndex/chooseSearchWord?userId='+userId,
    	data:"",
        type:'post',
       success:function (data) {
    	   var result = (data);
    	   console.log(result);
    	   /*var oUl = document.getElementById("combo_box");
    	   var aLi = oUl.getElementsByTagName("div");*/
    	   for(var i = 0; i < result.object.length ; i++){
    		   var div = document.createElement('div');
     		  div.className = "combo_box_child";
     		  div.id = "combo_box" + i;
     		  var divid = div.id;
     		 document.getElementById("combo_box").appendChild(div);
     		$("#"+div.id).html('<span>'+result.object[i].typeName+'</span>'+'<div class="hidedivForCode">'+result.object[i].typeCode+'</div>');
     		div.onclick = changeChooseWord;

    	   }
       },
       error:function(){
    	   console.log('error happened----');
       }
		
	});
}
$("#combo_box_head").mouseenter(function(event){	
	$(".combo_box_child").slideDown();
})

$("#combo_box").mouseleave(function(event){
	$(".combo_box_child").slideUp();

})

function changeChooseWord(){
	var a = $(this).children("span").text();
	var b =  $(this).children("div").html();
	console.log("1111"+a+"2222="+b);
	$("#combo_box_head").html('<span>'+a+'</span>'+'<div class="hidedivForCode">'+b+'</div>');
	console.log($("#combo_box_head").children("span").text());
	console.log($("#combo_box_head").children("div").html());
}

combo_box_first.addEventListener('click',function(){
	console.log($(this).html());
	var c = $(this).html();
	$("#combo_box_head").html($(this).html());
})

function elasticSearch(){
	var materialName = document.getElementById("searchBox_content").value;
	var typeName = $("#combo_box_head").children("span").text();
	var materialTypeCodeParent = $("#combo_box_head").children("div").html();
	console.log(materialName+"..."+typeName+"..."+typeCodeParent);
	window.location.herf = "/userpage/toSearchResult?userId="+userId+"&materialName="+materialName+
	"&materialDescription="+materialName+"&materialTypeCodeParent="+materialTypeCodeParent+
	"&page="+page+"&pageSize="+pageSize;
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
	       success:function (data) {
	    	   var result = (data);
	    	   console.log("result="+result);
	       },
	       error:function(){
	    	   console.log('error happened----');
	       }
			
	});
}