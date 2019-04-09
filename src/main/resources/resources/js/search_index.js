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
    	   for(var i = 0; i < result.object.length ; i++){
    		   var div = document.createElement('div');
     		  div.className = "combo_box_child";
     		  div.id = "combo_box" + i;
     		 document.getElementById("combo_box").appendChild(div);
     		$("#"+div.id).html(result.object[i]);
     		div.onclick = changeChooseWord;
    	   }
    	   /*$("#combo_box1").html(result.object[0]);
    	   $("#combo_box2").html(result.object[1]);*/
       },
       error:function(){
    	   console.log('error happened----');
       }
		
	});
}
$("#combo_box_head").mouseenter(function(event){
	/*$(".combo_box_child").css({
		display:"block",
	})*/	
	$(".combo_box_child").slideDown();
})

$("#combo_box").mouseleave(function(event){
	$(".combo_box_child").slideUp();

})

function changeChooseWord(){
	console.log($(this).html());
	$("#combo_box_head").html($(this).html());
}

combo_box_first.addEventListener('click',function(){
	console.log($(this).html());
	$("#combo_box_head").html($(this).html());
})

/*function elasticSearch(){
	var name = document.getElementById("searchBox_content").value;
	var type = document.getElementById("combo_box_head").innerHTML;
	console.log(name+"..."+type);
	var MaterialESBo = new Object();
	MaterialESBo.materialName = name;
	materialName.materialType = type
	$.ajax({
		url:"/elasticsearch/queryByParam?userId="+userId,
		data:{
			materialName: name,
			materialType: type
		}
	});
}*/