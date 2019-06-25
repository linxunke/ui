/**
 * 
 */
$(document).ready(function() {
	if ($.cookie("rmbUser") == "true") {
		$("#ck_rmbUser").attr("checked", true);
		$("#login_input1").val($.cookie("username"));
		$("#login_input2").val($.cookie("password"));

	}
});

// 记住用户名密码
function Save() {
	if ($("#ck_rmbUser").prop("checked")) {
		var str_username = $("#login_input1").val();
		var str_password = $("#login_input2").val();
		$.cookie("rmbUser", "true", {
			expires : 7
		}); // 存储一个带7天期限的cookie
		$.cookie("username", str_username, {
			expires : 7
		});
		$.cookie("password", str_password, {
			expires : 7
		});
	} else {
		$.cookie("rmbUser", "false", {
			expire : -1
		});
		$.cookie("username", "", {
			expires : -1
		});
		$.cookie("password", "", {
			expires : -1
		});
	}
};
$(document).keydown(function(event){

	if(event.keyCode == 13){
		Login();
	}

});
function Login() {
	Save();
	console.log("login...");
	var account = $("#login_input1").val();
	var password = $("#login_input2").val();
	var md5_password = $.md5(password);
	if (account != "" && password != "") {
		/* 异步传输注册的信息 */
		$.ajax({
			url : '/user/login',
			data : {
				account : account,
				password : md5_password
			},
			dataType : 'text',
			type : 'post',
			success : function(data) {
				console.log(data);
				var resultData = JSON.parse(data);
				console.log(resultData.status);
				if (resultData.status == "200") {
					window.location.href = "/userpage/toSearchIndex?userId="
							+ resultData.userId;
				} else if (resultData.status == "01") {
					alert("用户无效，请联系系统管理员或注册新账号。");
				} else {
					alert("用户名或密码错误！");
				}
			},
			error : function() {
				console.log("error happen -------------");
			}
		});
	} else {
		alert("请填写账户或密码!");
	}
};