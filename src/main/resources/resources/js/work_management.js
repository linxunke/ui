var main_div =1;
var userId = getParameter('userId'); 
var Page;
var currentPage;
$(document).ready(function () {
	$("#manage_upload").click(function() {
		window.open("/userpage/toMaterialUpload?userId="+userId);
	});
    var btn = document.getElementById('manage_add_btn');
    var cover = document.getElementById('cover_layer');
    var close = document.getElementById('close');
    var show = document.getElementById('show_div');
    var close2 = document.getElementById('close2');
    var show2 = document.getElementById('show_div2');
    var sure = document.getElementById('sure');
   /* if($.cookie("currentPage")==undefined){
    	var currentPage = document.getElementById('currentPageNumber').innerHTML;
    }else{
    	document.getElementById('currentPageNumber').innerHTML=$.cookie("currentPage");
    	var currentPage = document.getElementById('currentPageNumber').innerHTML;
    }
    
    //查看当前页面
    console.log("currentPage="+$.cookie("currentPage"));*/
    currentPage = parseInt(document.getElementById('currentPageNumber').innerHTML);
    console.log("currentPage111="+currentPage);
    getUserName(userId);
    
    $.ajax({
    	url:'/canvasInfo/getCanvasByUserId?userId='+userId,
    	data:{
    		currentPage:currentPage
    	},
    	dataType:'text',
        type:'post',
       success:function (data) {
    	   var result = JSON.parse(data);
    	   
    	   PageCount = result.object.PageCount;
    	   $.cookie("PageCount", PageCount, { expires: 7 });
    	   Page=$.cookie("PageCount");
    	   document.getElementById('tatolPage').innerHTML=PageCount;
    	   $("#manage_boardcount").html("共有"+result.object.canvasCount+"个画板作品");
    	   console.log(result);	   
    	   for(var i = result.object.canvasInfo.length - 1; i >= 0 ; i--){
    		  var downloadCount=result.object.canvasInfo[i].collectionCount;
    		  var div = document.createElement('div');
    		  div.className = "every_board";
    		  div.id = "every_board" + i;
    		  div.onclick =enterCanvas;
    		  document.getElementById("board_list").appendChild(div);
    		  //下面需要后台传过来的信息innerHTML
    		  /*result.object.canvasInfo[i].lastMaterialUrl*/
    		  if(result.object.canvasInfo[i].canvasName =="未分类"){
    			  if(result.object.canvasInfo[i].lastMaterialUrl!=undefined){
    				  $("#"+div.id).html('<div'+
        				  ' class="board_covers"><img style="width:220px;height:160px" src="'+window.location.protocol + "//" + window.location.host +'/'+ result.object.canvasInfo[i].lastMaterialUrl+'"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">'+downloadCount+'</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTimeFormate+'</div><div style="display:none" class="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    			  }else{
    				  $("#"+div.id).html('<div'+
            				  ' class="board_covers" ><img style="width:220px;height:160px"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">'+downloadCount+'</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTimeFormate+'</div><div style="display:none" id="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    			  }
    		  }else{
    			  if(result.object.canvasInfo[i].lastMaterialUrl!=undefined){
    		  $("#"+div.id).html('<div onclick="update('+result.object.canvasInfo[i].canvasId+''+
    				  ')" class="edit_button"><img class="edit_img" src="../img/编辑.png"><span'+
    				  ' class="canvas-id">'+result.object.canvasInfo[i].canvasId+'/span></div><div'+
    				  ' class="board_covers" ><img style="width:220px;height:160px" src="'+window.location.protocol + "//" + window.location.host +'/'+ result.object.canvasInfo[i].lastMaterialUrl+'"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">'+downloadCount+'</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTimeFormate+'</div><div style="display:none" class="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    			  }else{
    				  $("#"+div.id).html('<div onclick="update('+result.object.canvasInfo[i].canvasId+''+
    	    				  ')" class="edit_button"><img class="edit_img" src="../img/编辑.png"><span'+
    	    				  ' class="canvas-id">'+result.object.canvasInfo[i].canvasId+'/span></div><div'+
    	    				  ' class="board_covers" ><img style="width:220px;height:160px"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">'+downloadCount+'</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTimeFormate+'</div><div style="display:none" class="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    			  }
    		  }
    	   }
    	   
        },
        error:function () {
        	console.log("载入个人画板失败");
        }
    });
    var boardName = $("#show_text").val();
    var board_des = $("#show_textarea").val();
    btn.addEventListener('click', function(){
    	cover.style.display = "block";
    	close.style.display = "block";
    	show.style.display = "block";
    });
    close.addEventListener('click', function(){
    	cover.style.display = "none";
    	close.style.display = "none";
    	show.style.display = "none";
    });
    
    sure.addEventListener('click', function(){
    	cover.style.display = "none";
    	close.style.display = "none";
    	show.style.display = "none";
    	var boardName = $("#show_text").val();
        var board_des = $("#show_textarea").val();
        var userid = 1;
        console.log(boardName+"++"+board_des);
    	//添加div
    	/*var main = document.getElementById('unsorted_board');
        var div = document.createElement('div');
        div.className = "every_board";
        div.id = "unsorted_board"+main_div;
        document.getElementById("board_list").appendChild(div);
        //下面需要后台传过来的信息innerHTML
        result.object.canvasInfo[i].lastMaterialUrl  这里固定一个默认图片
        
        $("#"+div.id).html('<div class="edit_button"><img class="edit_img" src="../img/编辑.png"><span class="canvas-id">/span></div><div class="board_covers" ><img src=""></div><div class="board_name">'+boardName+'</div><div class="drawing_count">0</div><img class="download_logo" src="../img/下载.png"><div class="download_count">2008</div><div class="upload_date"></div><div id="board_id">canvasId</div>');
        
        main_div++;	*/
        
        if(boardName != "" && board_des != ""){
                /*异步传输画板的信息*/
                $.ajax({
                    url:'/managementCon/managementAdd?userId='+userId,
                    data:{
                    	canvasName:boardName,
                    	canvasDesc:board_des
                    	
                    },
                    dataType:'text',
                    type:'post',
                    success:function (data) {
                    	var resultData = JSON.parse(data);
                		if(resultData.status == '200'){
                			alert(resultData.message);
                			//添加div
                	    	var main = document.getElementById('unsorted_board');
                	        var div = document.createElement('div');
                	        div.className = "every_board";
                	        div.id = "unsorted_board"+main_div;
                	        document.getElementById("board_list").appendChild(div);
                	        //下面需要后台传过来的信息innerHTML
                	        /*result.object.canvasInfo[i].lastMaterialUrl  这里固定一个默认图片*/
                	        /*div.innerHTML = main.innerHTML;*/   	        
                	        $("#"+div.id).html('<div class="edit_button"><img class="edit_img" src="../img/编辑.png"><span class="canvas-id">/span></div><div class="board_covers" ><img src=""></div><div class="board_name">'+boardName+'</div><div class="drawing_count">0</div><img class="download_logo" src="../img/下载.png"><div class="download_count">2008</div><div class="upload_date"></div><div id="board_id">canvasId</div>');
                	        main_div++;	
                			document.location.reload("/userpage/work_manage?userId="+resultData.userId);
                		}else if(resultData.status == '500'){
            				alert(resultData.message);
            			}
                    },
                    error:function () {
                    	console.log('error happened----');
                    }
                });
            }
        else{
        	alert("请填写完整信息！");
        }
    });
    close2.addEventListener('click', function(){
    	cover.style.display = "none";
    	close2.style.display = "none";
    	show2.style.display = "none";
    });
});
var cover = document.getElementById('cover_layer');
var close2 = document.getElementById('close2');
var show2 = document.getElementById('show_div2');
function update(canvasId){
	cover.style.display = "block";
	close2.style.display = "block";
	show2.style.display = "block";
	var userId = getParameter('userId');
	var canvasid = canvasId;
	console.log("canvasid="+canvasid);
	sure2.addEventListener('click',function(){
    	cover.style.display = "none";
    	close2.style.display = "none";
    	show2.style.display = "none";
    	var boardName = $("#show_text2").val();
        var board_des = $("#show_textarea2").val();
        console.log(boardName+"++"+board_des+"++"+canvasid);
        $.ajax({
        	url:'/managementCon/managementUpdate?userId='+userId,
        	data:{
        		canvasid:canvasid,
        		boardName:boardName,
        		board_des:board_des
        	},
        	dataType:'text',
        	Type:'post',
        	success:function(data){
        		var resultData = JSON.parse(data);
        		if(resultData.status == '200'){
        			alert(resultData.message);
        			document.location.reload("/userpage/work_manage?userId="+resultData.userId);
        		}else if(resultData.status == '500'){
    				alert(resultData.message);
    			}
        		
        	},
        	error:function(){
        		console.log("error happened----");
        	}
        });
	});
	deleteAll.addEventListener('click',function(){
		$.ajax({
        	url:'/managementCon/managementDeleteAll?userId='+userId,
        	data:{
        		canvasid:canvasid
        	},
        	dataType:'text',
        	type:'post',
        	success:function(data){
        		var resultData = JSON.parse(data);
        		if(resultData.status == '200'){
        			alert(resultData.message);
        			document.location.reload("/userpage/work_manage?userId="+resultData.userId);
        		}else if(resultData.status == '500'){
    				alert(resultData.message);
    			}
        	},
        	error:function(){
        		console.log('error happened----');
        	}
        });
	});
	deleteToOther.addEventListener('click',function(){
		$.ajax({
        	url:'/managementCon/managementDeleteToUnsort?userId='+userId,
        	data:{
        		canvasid:canvasid
        	},
        	dataType:'text',
        	Type:'post',
        	success:function(data){
        		var resultData = JSON.parse(data);
        		console.log(resultData.status);
        		if(resultData.status == '200'){
        			alert(resultData.message);
        			document.location.reload("/userpage/work_manage?userId="+resultData.userId);
        		}else if(resultData.status == '500'){
    				alert(resultData.message);
    			}
        	},
        	error:function(){
        		console.log('error happened----');
        	}
        });
	});	
}
function getUserName(userId){
	$.ajax({
    	url:'/user/getUserName?userId='+userId,
    	data:{
    	},
    	type:'post',
    	success:function(data){/*
    		var resultData = JSON.parse(data);*/
    		var resultData = data;
    		console.log(data);
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
function homePage(){
	$(".every_board").remove();
	currentPage = 1;
	document.getElementById('currentPageNumber').innerHTML=currentPage;
	sendAjax(currentPage);
    
}
function lastPage(){
	var currentPage = document.getElementById('currentPageNumber').innerHTML;
	$(".every_board").remove();
	if(currentPage>1){
		currentPage = parseInt(currentPage)-1;
	}else{
		currentPage = parseInt(currentPage);
	}
	document.getElementById('currentPageNumber').innerHTML=currentPage;
	sendAjax(currentPage);
}
function nextPage(){
	var currentPage = document.getElementById('currentPageNumber').innerHTML;
	$(".every_board").remove();
	if(currentPage<Page){
		currentPage = parseInt(currentPage)+1;
		console.log("currentPage222="+currentPage);
	}else{
		currentPage = parseInt(currentPage);
	}
	document.getElementById('currentPageNumber').innerHTML=currentPage;
	sendAjax(currentPage);   
}
function finalPage(){
	var currentPage = document.getElementById('currentPageNumber').innerHTML;
	$(".every_board").remove();
	currentPage = Page;
	document.getElementById('currentPageNumber').innerHTML=currentPage;
	sendAjax(currentPage);   
}

function sendAjax(currentPage){
	console.log("currentPage1212="+currentPage);
	$.ajax({
    	url:'/canvasInfo/getCanvasByUserId?userId='+userId,
    	data:{
    		currentPage:currentPage
    	},
    	dataType:'text',
        type:'post',
       success:function (data) {
    	   var result = JSON.parse(data);
    	   
    	   $("#manage_boardcount").html("共有"+result.object.canvasCount+"个画板作品");
    	   console.log(result);	   
    	   for(var i = result.object.canvasInfo.length - 1; i >= 0 ; i--){
    		   var downloadCount = result.object.canvasInfo[i].collectionCount;
    		   var div = document.createElement('div');
    		  div.className = "every_board";
    		  div.id = "every_board" + i;
    		  document.getElementById("board_list").appendChild(div);
    		  //下面需要后台传过来的信息innerHTML
    		  /*result.object.canvasInfo[i].lastMaterialUrl*/
    		  if(result.object.canvasInfo[i].canvasName =="未分类"){
    			  if(result.object.canvasInfo[i].lastMaterialUrl!=undefined){
    				  $("#"+div.id).html('<div'+
        				  ' class="board_covers" ><img style="width:220px;height:160" src="'+window.location.protocol + "//" + window.location.host +'/'+ result.object.canvasInfo[i].lastMaterialUrl+'"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">'+downloadCount+'</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTimeFormate+'</div><div style="display:none" id="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    			  }else{
    				  $("#"+div.id).html('<div'+
            				  ' class="board_covers" ><img style="width:220px;height:160"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">'+downloadCount+'</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTimeFormate+'</div><div style="display:none" id="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    			  }
    		  }else{
    			  if(result.object.canvasInfo[i].lastMaterialUrl!=undefined){
    		  $("#"+div.id).html('<div onclick="update('+result.object.canvasInfo[i].canvasId+''+
    				  ')" class="edit_button"><img class="edit_img" src="../img/编辑.png"><span'+
    				  ' class="canvas-id">'+result.object.canvasInfo[i].canvasId+'/span></div><div'+
    				  ' class="board_covers" ><img style="width:220px;height:160" src="'+window.location.protocol + "//" + window.location.host +'/'+ result.object.canvasInfo[i].lastMaterialUrl+'"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">'+downloadCount+'</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTimeFormate+'</div><div style="display:none" id="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    			  }else{
    				  $("#"+div.id).html('<div onclick="update('+result.object.canvasInfo[i].canvasId+''+
    	    				  ')" class="edit_button"><img class="edit_img" src="../img/编辑.png"><span'+
    	    				  ' class="canvas-id">'+result.object.canvasInfo[i].canvasId+'/span></div><div'+
    	    				  ' class="board_covers" ><img style="width:220px;height:160"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">'+downloadCount+'</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTimeFormate+'</div><div style="display:none" id="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    			  }
    		  }
    		  
    	   }
    	   
        },
        error:function () {
        	console.log("载入个人画板失败");
        }
    });
}
function enterCanvas() {
	var canvasId = $(this).find(".board_id").html();
	 window.location.href = '/userpage/toMaterialManage?userId='+userId +'&canvasId='+canvasId;
}
