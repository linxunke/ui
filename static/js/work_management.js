var main_div =1;
$(document).ready(function () {
    var btn = document.getElementById('manage_add_btn');
    var cover = document.getElementById('cover_layer');
    var close = document.getElementById('close');
    var show = document.getElementById('show_div');
    var close = document.getElementById('close');
    var sure = document.getElementById('sure');
    
    var userId = ;//拿到user id  显示具体画板数量
    $ajax{
    	url:'/managementCon/managementMain',
    	data{
    		userId:userId
    	},
    	dataType:'text',
        type:'post',
       success:function (data) {
    	   
        },
        error:function () {
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
    	//添加div
    	var main = document.getElementById('unsorted_board');
        var div = document.createElement('div');
        div.className = "every_board";
        div.id = "unsorted_board"+main_div;
        //下面需要后台传过来的信息innerHTML
        div.innerHTML = main.innerHTML;
        document.getElementById("board_list").appendChild(div);
        main_div++;	
        
        if(boardName != "" && board_des != ""){
                /*异步传输画板的信息*/
                $.ajax({
                    url:'/managementCon/managementAdd',
                    data:{
                    	canvasName:boardName,
                    	canvasDesc:board_des,
                    	userId:userId
                    	
                    },
                    dataType:'text',
                    type:'post',
                   success:function (data) {
                    },
                    error:function () {
                    }
                });
            }
        else{
        	alert("请填写完整信息！");
        }
    });
});