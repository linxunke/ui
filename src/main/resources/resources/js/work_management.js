var main_div =1;
var userid = 1;
$(document).ready(function () {
    var btn = document.getElementById('manage_add_btn');
    var cover = document.getElementById('cover_layer');
    var close = document.getElementById('close');
    var show = document.getElementById('show_div');
    var close2 = document.getElementById('close2');
    var show2 = document.getElementById('show_div2');
    var sure = document.getElementById('sure');
    /*var edit = document.getElementsByClassName('edit_button');
    for(i in edit){
    	edit[i].click=update;
    }*/
    
    var userid = 1;//拿到user id  显示具体画板数量,拿到已有的画板的信息，for循环添加div
    $.ajax({
    	url:'/canvasInfo/getCanvasByUserId',
    	data:{
    		userid:userid
    	},
    	dataType:'text',
        type:'post',
       success:function (data) {
    	   var result = JSON.parse(data);
    	   console.log(result);	   
    	   for(var i = result.object.canvasInfo.length - 1; i >= 0 ; i--){
    		   var div = document.createElement('div');
    		  div.className = "every_board";
    		  div.id = "every_board" + i;
    		  document.getElementById("board_list").appendChild(div);
    		  //下面需要后台传过来的信息innerHTML
    		  /*result.object.canvasInfo[i].lastMaterialUrl*/
    		  if(result.object.canvasInfo[i].canvasName =="未分类"){
    			  $("#"+div.id).html('<div'+
        				  ' class="board_covers" ><img style="width:160px;height:auto;margin-left:30px" src="'+window.location.protocol + "//" + window.location.host +'/'+ result.object.canvasInfo[i].lastMaterialUrl+'"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">2008</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTime+'</div><div style="display:none" id="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    		  }else{
    		  $("#"+div.id).html('<div onclick="update('+result.object.canvasInfo[i].canvasId+''+
    				  ')" class="edit_button"><img class="edit_img" src="../img/编辑.png"><span'+
    				  ' class="canvas-id">'+result.object.canvasInfo[i].canvasId+'/span></div><div'+
    				  ' class="board_covers" ><img style="width:160px;height:auto;margin-left:30px" src="'+window.location.protocol + "//" + window.location.host +'/'+ result.object.canvasInfo[i].lastMaterialUrl+'"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">2008</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTime+'</div><div style="display:none" id="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    		  $("#"+div.id).html('<div onclick="update('+result.object.canvasInfo[i].canvasId+''+
    				  ')" class="edit_button"><img class="edit_img" src="../img/编辑.png"><span'+
    				  ' class="canvas-id">'+result.object.canvasInfo[i].canvasId+'/span></div><div'+
    				  ' class="board_covers" ><img style="width:160px;height:auto;margin-left:30px" src="'+window.location.protocol + "//" + window.location.host +'/'+ result.object.canvasInfo[i].lastMaterialUrl+'"></div><div class="board_name">'+result.object.canvasInfo[i].canvasName+'</div><div class="drawing_count">'+result.object.canvasInfo[i].materialCount+'</div><img class="download_logo" src="../img/下载.png"><div class="download_count">2008</div><div class="upload_date">'+result.object.canvasInfo[i].lastMaterialUploadTime+'</div><div style="display:none" id="board_id">'+result.object.canvasInfo[i].canvasId+'</div>');
    		  
    		  }console.log(div.id);
    		  
    	   }
    	   
        },
        error:function () {
        	console.log("载入个人画板失败");
        }
    });
    var boardName = $("#show_text").val();
    var board_des = $("#show_textarea").val();
    console.log(boardName+"++"+board_des);
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
        
        if(boardName != "" && board_des != ""){
                /*异步传输画板的信息*/
                $.ajax({
                    url:'/managementCon/managementAdd',
                    data:{
                    	canvasName:boardName,
                    	canvasDesc:board_des,
                    	userid:userid
                    	
                    },
                    dataType:'text',
                    type:'post',
                    success:function (data) {
                    	var resultData = JSON.parse(data);
                		if(resultData.status == '200'){
                			alert(resultData.message);
                			document.location.reload();
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
    /*edit.addEventListener('click',function(){
    	cover.style.display = "block";
    	close2.style.display = "block";
    	show2.style.display = "block";
    });*/
    /*function update(){
    	cover.style.display = "block";
    	close2.style.display = "block";
    	show2.style.display = "block";
    	var canvasid = $(this).find("span").val();
    	sure2.addEventListener('click',function(){
        	cover.style.display = "none";
        	close2.style.display = "none";
        	show2.style.display = "none";
        	var boardName = $("#show_text").val();
            var board_des = $("#show_textarea").val();
            var canvasid = $(this).find("span").val();   
            console.log(boardName+"++"+board_des+"++"+canvasid);
    	});
    	
    }*/
    close2.addEventListener('click', function(){
    	cover.style.display = "none";
    	close2.style.display = "none";
    	show2.style.display = "none";
    });
    /*sure2.addEventListener('click',function(){
    	cover.style.display = "none";
    	close2.style.display = "none";
    	show2.style.display = "none";
    	var boardName = $("#show_text").val();
        var board_des = $("#show_textarea").val();
        var canvasid = $(this).find("span").val();   
        console.log(boardName+"++"+board_des+"++"+canvasid);
        $.ajax({
        	url:'/managementCon/managementUpdate',
        	data:{
        		boardName:boardName,
        		board_des:board_des
        	},
        	dataType:'',
        	Type:'',
        	succuss:function(data){
        		
        	},
        	error:function(){
        		
        	}
        });
    	
    });*/
});
var cover = document.getElementById('cover_layer');
var close2 = document.getElementById('close2');
var show2 = document.getElementById('show_div2');
function update(canvasId){
	cover.style.display = "block";
	close2.style.display = "block";
	show2.style.display = "block";
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
        	url:'/managementCon/managementUpdate',
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
        			document.location.reload();
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
        	url:'/managementCon/managementDeleteAll',
        	data:{
        		canvasid:canvasid,
        		userid:userid
        	},
        	dataType:'text',
        	Type:'post',
        	success:function(data){
        		var resultData = JSON.parse(data);
        		if(resultData.status == '200'){
        			alert(resultData.message);
        			document.location.reload();
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
        	url:'/managementCon/managementDeleteToUnsort',
        	data:{
        		canvasid:canvasid,
        		userid:userid
        	},
        	dataType:'text',
        	Type:'post',
        	success:function(data){
        		var resultData = JSON.parse(data);
        		console.log(resultData.status);
        		if(resultData.status == '200'){
        			alert(resultData.message);
        			document.location.reload();
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