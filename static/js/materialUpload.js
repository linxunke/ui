var typeData; /*存储数据库中已经查询到的所有分类的信息，分为3部分（类别，细分，风格）*/
$(document).ready(function(){
	/*获取分类的类别信息*/
	$.ajax({
		url:'/uploadMaterial/getMaterialTypes',
		type:'post',
		data:{
			userId: '1'
		},
		success:function(data){
			typeData = JSON.parse(data);
			console.log(typeData);
			/*初始化分类模块的类型下拉框*/
			var materialTypes = typeData.object.materialTypes;
			for(var i=0;i<materialTypes.length;i++){
				$('.material_type_text').append('<option value="'+materialTypes[i].typeCode+'">'+materialTypes[i].typeName+'</option>');
			}
			/*初始化分类模块的细分下拉框*/
			var materialSegmentations = typeData.object.materialSegmentations;
			//（默认为图标，01）根据已选择类型的值来显示细分的模块
			var selectedTypeCode=$(".material_type_text option:selected").val();
			for(var i=0;i<materialSegmentations.length;i++){
				if(selectedTypeCode == materialSegmentations[i].parentCode){
					$('.material_segmentation_text').append('<option value="'+materialSegmentations[i].typeCode+'">'+materialSegmentations[i].typeName+'</option>');
				}
			}
			/*初始化分类模块的风格下拉框*/
			var materialStyles = typeData.object.materialStyles;
			for(var i=0;i<materialStyles.length;i++){
				$('.material_style_text').append('<option value="'+materialStyles[i].typeCode+'">'+materialStyles[i].typeName+'</option>');
			}
		},
		error:function(){
			console.log('error happened----');
		}
	});
	
	/*获取个人画板的信息*/
	$.ajax({
		url:'/canvasInfo/getCanvasByUserId?userId=1',
		type:'get',
		success:function(data){
			console.log(data);
			var canvasInfo = data;
			var appendOptions = '';
			for(var i=0; i < canvasInfo.object.length; i++){
				appendOptions += '<option value="'+canvasInfo.object[i].id+'">'+canvasInfo.object[i].canvasName+'</option>';
			}
			$("#personal_sketchpad").append(appendOptions);
		},
		error:function(){
			console.log('error happened----');
		}
	});
	
	//添加类别模块
	$("#add_type_info_div").click(function() {
		/*拼接类别的下拉框模块*/
		var type_module = '<div class="material_total_info"><div class="material_type_info"><div class="type_info_title">类型</div><div class="type_info_content">'+
			'<select class="material_type_select material_type_text" onchange="changeSelectText(this)">';
		var materialTypes = typeData.object.materialTypes;
		for(var i=0;i<materialTypes.length;i++){
			type_module += '<option value="'+materialTypes[i].typeCode+'">'+materialTypes[i].typeName+'</option>';
		}
		
		type_module += '</select></div></div><div class="material_type_info"><div class="type_info_title">细分</div><div class="type_info_content">'+
		'<select class="material_type_select material_segmentation_text">';
		/*拼接细分的下拉框模块*/
		var materialSegmentations = typeData.object.materialSegmentations;
		var selectedTypeCode = materialTypes[0].typeCode;
		for(var i=0;i<materialSegmentations.length;i++){
			if(selectedTypeCode == materialSegmentations[i].parentCode){
				type_module +='<option value="'+materialSegmentations[i].typeCode+'">'+materialSegmentations[i].typeName+'</option>';
			}
		}
		type_module += '</select></div></div><div class="material_type_info"><div class="type_info_title">风格</div><div class="type_info_content">'+
			'<select class="material_type_select material_style_text"">';
		/*拼接风格的下拉框模块*/
		var materialStyles = typeData.object.materialStyles;
		for(var i=0;i<materialStyles.length;i++){
			type_module +='<option value="'+materialStyles[i].typeCode+'">'+materialStyles[i].typeName+'</option>';
		}
		type_module += '</select></div></div><input type="button" class="type_info_manage delete_type_info_div" value="-" onclick="deleteInfoModule(this)"/></div>';
		var $material_type_module = $(".material_type_module");
		$material_type_module.append(type_module);
	});
	
	/*提交上传的素材信息*/
	$("#upload_material").click(function() {
		var formdata = new FormData();
		formdata.append("userId",1);
		formdata.append("imageName",$("#material_title_content").val());
		formdata.append("imageLabel",$("#material_label_content").val());
		formdata.append("personalCanvasId",$("#personal_sketchpad").val());
		var typeArray = new Array();
		for(var i = 0; i <$(".material_total_info").length; i++){
			typeArray[i] = new Array(3);
		}
		var index = 0;
		$(".material_total_info").each(function() {
			typeArray[index][0] = $(this).find(".material_type_text").val();
			typeArray[index][1] = $(this).find(".material_segmentation_text").val();
			typeArray[index][2] = $(this).find(".material_style_text").val();
			index += 1;
		});
		formdata.append("typeArray",JSON.stringify(typeArray));
		formdata.append("resourceFile",document.getElementById("file").files[0]);
		formdata.append("previewImg",document.getElementById("myCan").toDataURL());
		formdata.append("pngFileSrc",document.getElementById("target").src);
		$.ajax({
			url:'/uploadMaterial/commitMaterialInfos',
			type:'post',
			data:formdata,
			contentType: false,
	        processData: false,
			success:function(data){
				var resultData = JSON.parse(data);
				console.log(resultData);
				if(resultData.status == '200'){
					alert(resultData.message);
					window.location.href = "/test2/toMaterialUpload";
				}else if(resultData.status == '500'){
					alert(resultData.message);
				}
			},
			error:function (data) {
				console.log(data);
			}
		});
	});
	$("#downloadModels").click(function(){
		var $eleForm = $("<form method='get'></form>");
        $eleForm.attr("action",window.location.protocol + "//" + window.location.host + "/files/downloadFiles?fileName=图标制作模板.ai");
        $(document.body).append($eleForm);
        //提交表单，实现下载
        $eleForm.submit();
	});
});

/*类型下拉框的值改变时，对应修改细分下拉框的值*/
function changeSelectText(Obj) {
	var $material_type = $(Obj);
	var $material_segmentation = $material_type.parent().parent().parent().find(".material_segmentation_text");
	$material_segmentation.empty();
	var material_type_selected_text = $material_type.find("option:selected").val();
	for(var i=0; i <typeData.object.materialSegmentations.length; i++){
		var segmentation = typeData.object.materialSegmentations[i];
		if(segmentation.parentCode == material_type_selected_text){
			$material_segmentation.append('<option value="'+segmentation.typeCode+'">'+segmentation.typeName+'</option>');
		}
	}
}

//删除类别模块
function deleteInfoModule(obj){
	var $module = $(obj);
	/*删除整个类别模块的内容*/
	$module.parent().remove();
}

//定义一些使用的变量
var     jcrop_api,//jcrop对象
        boundx,//图片实际显示宽度
        boundy,//图片实际显示高度
        realWidth,// 真实图片宽度
        realHeight, //真实图片高度

        // 使用的jquery对象
        $target = $('#target'),
        $preview = $('#preview-pane'),
        $pcnt = $('#preview-pane .preview-container'),
        $pimg = $('#preview-pane .preview-container img'),

        xsize = $pcnt.width(),
        ysize = $pcnt.height();

/*获取文件地址*/
function getFileUrl(sourceId) { 
    var url; 
    if (navigator.userAgent.indexOf("MSIE")>=1) { // IE 
    url = document.getElementById(sourceId).value; 
    } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox 
    url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
    } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome 
    url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
    } else if(navigator.userAgent.indexOf("Safari")>0) { // Chrome 
    url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
    } 
    return url; 
} 

//选择文件事件
function changeFile() {
    var url = getFileUrl("file");//根据id获取文件路径
    var fileObj = document.getElementById("file").files[0];
    /*console.log(fileObj.type);*/
    /*需要添加psd格式的判断*/
    /* *********** */
    /*if(fileObj.type == 'application/postscript'){*/
    	var formdata = new FormData(); // FormData 对象
        formdata.append("file", fileObj); // 文件对象
        $.ajax({
    		url:'/uploadMaterial/getMaterialFiles',
    		type:'post',
    		enctype:'multipart/form-data',
    		data:formdata,
    		contentType: false,
            processData: false,
    		success:function(data){
    			var resultData = JSON.parse(data);
    			console.log(resultData);
    			if(resultData.status == '200'){
    				/*拼接图片的地址*/
    				var realUrl = window.location.protocol + "//" + window.location.host + "/images/" + resultData.object;
    				preImg(realUrl);
    			}else if(resultData.status == '500'){
    				alert(resultData.message);
    			}
    		},
    		error:function(){
    			console.log('error happened----');
    		}
    	});
    /*}else{
    	alert("当前不支持类型为"+fileObj.type+"的文件,请重新选择文件!");
    }*/
    return false;
}

//将本地图片 显示到浏览器上 
function preImg(url) {
    //图片裁剪逻辑
    if(jcrop_api)//判断jcrop_api是否被初始化过
    {
        jcrop_api.destroy();
    }

    //初始化预览div内容
    initPreview();
    var p = document.getElementById('Preview');
    p.src = url;

    //初始化图片
    initTarget();
    var image = document.getElementById('target');
    image.onload=function(){//图片加载是一个异步的过程
            //获取图片文件真实宽度和大小
            var img = new Image();
            img.onload=function(){
                realWidth = img.width;
                realHeight = img.height;
              
                //获取图片真实高度之后
                initJcrop();//初始化Jcrop插件
                initCanvas();//初始化Canvas内容
            };
            img.src = url;
    };
    image.src = url;
} 

//初始化Jcrop插件
function initJcrop(){
    console.log('init',[xsize,ysize]);
    $target.removeAttr("style");//清空上一次初始化设置的样式
    $target.Jcrop({
      onChange: updatePreview,
      onSelect: updatePreview,
      aspectRatio: 1.3
    },function(){
    //初始化后回调函数
    // 获取图片实际显示的大小
    var bounds = this.getBounds();
    boundx = bounds[0];//图片实际显示宽度
    boundy = bounds[1];//图片实际显示高度

    // 保存jcrop_api变量
    jcrop_api = this;
      
    });  
}


//更新显示预览内容
function updatePreview(c){
    if (parseInt(c.w) > 0)
    {
        var rx = xsize / c.w;
        var ry = ysize / c.h;
        var url = document.getElementById('target');
        document.getElementById('Preview').src=url;
        $pimg.css({
            maxWidth:  Math.round(rx * boundx) + 'px',
            maxHeight: Math.round(ry * boundy) + 'px',
            width: Math.round(rx * boundx) + 'px',
            height: Math.round(ry * boundy) + 'px',
            marginLeft: '-' + Math.round(rx * c.x) + 'px',
            marginTop: '-' + Math.round(ry * c.y) + 'px',
        });

        //更新canvas画板内容
        var img=document.getElementById("target");
        var ct=document.getElementById("myCan");
        var ctx=ct.getContext("2d");
        //清空画板
        ctx.clearRect(0,0, ct.width, ct.height); 
        //.drawImage(图像对象,原图像截取的起始X坐标,原图像截取的起始Y坐标,原图像截取的宽度,原图像截取的高度，绘制图像的起始X坐标,绘制图像的起始Y坐标,绘制图像所需要的宽度,绘制图像所需要的高度);
        ctx.drawImage(img, c.x/boundx * realWidth,c.y/boundy * realHeight, c.w/boundx * realWidth, c.h/boundy * realHeight,0,0, ct.width, ct.height);
    }
}

//初始化预览div内容
function initTarget(){
    $target.removeAttr("style");//清空上一次初始化设置的样式
    $target.css({
        maxWidth:  '100%',
        maxHeight: '100%'
      });
}
//初始化预览div内容
function initPreview(){
    $pimg.removeAttr("style");//清空上一次初始化设置的样式
    $pimg.css({
        maxWidth:  xsize + 'px',
        maxHeight: ysize + 'px'
      });
}

//初始化canvas画板内容
function initCanvas(){
    //更新canvas画板内容
    var img= document.getElementById("target");
    var ct= document.getElementById("myCan");
    var ctx = ct.getContext("2d");
   
    var myCanWidth = $('#myCan').width();
    var myCanHeight = $('#myCan').height();

    //清空画板
    ctx.clearRect(0,0, ct.width, ct.height); 

     //.drawImage(图像对象,原图像截取的起始X坐标,原图像截取的起始Y坐标,原图像截取的宽度,原图像截取的高度，绘制图像的起始X坐标,绘制图像的起始Y坐标,绘制图像所需要的宽度,绘制图像所需要的高度);
    var dWidth = realWidth;//绘制实际宽度
    var dHeight = realHeight;//绘制实际高度
    if(dWidth > myCanWidth)
    {
        dHeight = myCanWidth / dWidth *  dHeight;
        dWidth = myCanWidth;
    }
    if(dHeight > myCanHeight)
    {
        dWidth = myCanHeight / dHeight * dWidth ;
        dHeight = myCanHeight;
    }
    ctx.drawImage(img,0,0, realWidth, realHeight, 0,0,  dWidth, dHeight);
}