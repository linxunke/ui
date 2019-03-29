/**
 * 
 */
$(document).ready(function () {
    if ($.cookie("rmbUser") == "true") {
    $("#ck_rmbUser").attr("checked", true);
    $("#login_input1").val($.cookie("username"));
    $("#login_input2").val($.cookie("password"));
  
    }
  });
 
  //记住用户名密码
  function Save() {
    if ($("#ck_rmbUser").prop("checked")) {
      var str_username = $("#login_input1").val();
      var str_password = $("#login_input2").val();
      $.cookie("rmbUser", "true", { expires: 7 }); //存储一个带7天期限的cookie
      $.cookie("username", str_username, { expires: 7 });
      $.cookie("password", str_password, { expires: 7 });
    }
    else {
      $.cookie("rmbUser", "false", { expire: -1 });
      $.cookie("username", "", { expires: -1 });
      $.cookie("password", "", { expires: -1 });
    }
  };
  function Login(){
	  var account = $("#login_input1").val();
      var password = $("#login_input2").val();
      var md5_password = $.md5(password);
      if(account != "" && password != ""){
    	  /*异步传输注册的信息*/
          $.ajax({
              url:'/user/login',
              data:{
            	  account:account,
            	  password:md5_password
              },
              dataType:'text',
              type:'post',
              success:function (data) {
              },
              error:function () {
              }
          });
      }
      else {
          alert("请填写账户或密码!");
      }
  };