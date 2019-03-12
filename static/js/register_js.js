$(document).ready(function () {
    /*检查手机号是否符合规范*/
    $("#account").blur(function () {
        var phone = $("#account").val();
        var format = /^1\d{10}$/;
        if(phone.match(format)){
            $("#phone_prompt").css("color","green");
            $("#phone_prompt").html("√  手机号码格式正确");
        }
        else{
            $("#phone_prompt").css("color","red");
            $("#phone_prompt").html("×  手机号码格式错误");
        }
    });
    /*检查密码格式是否符合规范*/
    $("#password").blur(function () {
        var pw = $("#password").val();
        var format = /(?!^(\d+|[a-zA-Z]+|[~!@#$%^&*?]+)$)^[\w~!@#$%^&*?]{6,20}$/;
        if(pw.match(format)){
            $("#password_format_prompt").css("color","green");
            $("#password_format_prompt").html("√  密码符合规范");
        }
        else {
            $("#password_format_prompt").css("color","red");
            $("#password_format_prompt").html("×  密码不符合规范");
        }
    });
    /*验证密码是否相同*/
    $("#confirm_password").blur(function () {
        var pw1 = $("#password").val();
        var pw2 = $("#confirm_password").val();
        if(pw1 != ""){
            if(pw1 == pw2){
                $("#password_match_prompt").css("color","green");
                $("#password_match_prompt").html("√  密码一致");
            }
            else {
                $("#password_match_prompt").css("color","red");
                $("#password_match_prompt").html("×  密码不一致");
            }
        }

    });
    /*点击保存按钮后提交注册信息*/
    $("#save_btn").click(function () {
    	var data=document.getElementById("myCan").toDataURL();
    	var formData=new FormData();
    	formData.append("head_image",dataURLtoBlob(data));
        var account = $("#account").val();
        var nickname = $("#nickname").val();
        var wechat = $("#wechat").val();
        var password = $("#password").val();
        var md5_password = $.md5(password);
        if(account != "" && nickname != "" &&
            wechat != "" && password != ""){
            /*异步传输注册的信息*/
            $.ajax({
                url:'test2/register',
                data:{
                	head_image:format,
                    account:account,
                    nickname:nickname,
                    wechat:wechat,
                    password:md5_password
                },
                type:'post',
                success:function () {
                    //do something....
                },
                error:function () {
                    //do something....
                }
            });
        }
        else {
            alert("请完整填写完注册信息！");
        }
    });
    $("#cancel").click(function () {
        $(".modal").css("display","none");
    });
    $("#choose_picture").click(function () {
    	openBrowse();
        $("#Preview").load(function () {
            $(".modal").css("display","block");
        });
    });
    $("#sure").click(function () {
    	var canvas =document.getElementById("myCan");
    	var context = canvas.getContext('2d'); 
    	var strDataURI=canvas.toDataURL("image/jpg");     
    	$("#head_img").attr("src",strDataURI);
    	$("#head_img").css("display","block");
    	$("#head_img").css("height","64px");
    	$("#head_img").css("width","64px");
    	$("#head_img").css("border-radius","64px");
    	$(".modal").css("display","none");
    });
    $("#resplit").click(function () {
        openBrowse();
    });
});
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



//1、打开浏览器
function openBrowse(){
    var ie=navigator.appName=="Microsoft Internet Explorer" ? true : false; 
    if(ie){ 
        document.getElementById("file").click(); 
    }else{
        var a=document.createEvent("MouseEvents");
        a.initEvent("click", true, true);  
        document.getElementById("file").dispatchEvent(a);
    } 
}

//2、从 file 域获取 本地图片 url 
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

//文件上传
function uploadFile(){
    //获取裁剪完后的base64图片url,转换为blob
    var data=document.getElementById("myCan").toDataURL();
    var formData=new FormData();
    formData.append("imageName",dataURLtoBlob(data));
    var httprequest= null;
    if (window.XMLHttpRequest) {
        httprequest = new XMLHttpRequest();
    } else {
        httprequest = new ActiveXObject('MicroSoft.XMLHTTP');
    }
    var apiurl= ""; //上传图片的api接口，自行填写
    httprequest.open('POST',apiurl,true);
    httprequest.send(formData);
    httprequest.onreadystatechange= function () {
        
        if(httprequest.readyState == 4 ){
            
            if(httprequest.status == 200)
            {
                var json=JSON.parse(httprequest.responseText);
                console.log(json);
                
            }else
            {
                alert('获取数据错误,错误代码：' + httprequest.status + '错误信息：' + httprequest.statusText);
            }
        }
    };
}

//把base64位的toDataURL图片转换成blob
function dataURLtoBlob(dataurl) {  
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],  
            bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);  
    while (n--) {  
        u8arr[n] = bstr.charCodeAt(n);  
    }  
    return new Blob([u8arr], { type: mime });  
} 
