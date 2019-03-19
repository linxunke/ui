$(document).ready(function(){
	$("#add_type_info_div").click(function() {
		var type_module = '<div class="material_total_info"><div class="material_type_info"><div class="type_info_title">类型</div><div class="type_info_content">'+
			'<select class="material_type_select" id="material_type_text"><option>'+
			'1'+
			'</option></select></div></div><div class="material_type_info"><div class="type_info_title">细分</div><div class="type_info_content">'+
			'<select class="material_type_select" id="material_segmentation_text"><option>'+
			'1'+
			'</option></select></div></div><div class="material_type_info"><div class="type_info_title">风格</div><div class="type_info_content">'+
			'<select class="material_type_select" id="material_style_text"><option>'+
			'1'+
			'</option></select></div></div><input type="button" class="type_info_manage delete_type_info_div" value="-" onclick="deleteInfoModule(this)"/></div>';
		var $material_type_module = $(".material_type_module");
		$material_type_module.append(type_module);
	});
});
function deleteInfoModule(Obj){
	Obj.parentNode.parentNode.removeChild(Obj.parentNode);
	/*$(this).parent().remove();*/
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
    preImg(url);
    return false;
}

//3、将本地图片 显示到浏览器上 
function preImg(url) { 

    console.log('url===' + url);
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
      aspectRatio: xsize / ysize
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