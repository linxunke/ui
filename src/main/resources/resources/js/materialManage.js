$(document).ready(function() {
});

function showModal() {
	$(".modal").css("display","block");
}
function closeModal() {
	$(".modal").css("display","none");
}
/*自定义CheckBox的js功能代码*/
function show_custom_checkedbox(obj) {
	$(obj).css("cursor","pointer");
	$(obj).css("opacity","1");
}
function hiden_custom_checkedbox(obj) {
	if($(obj).find(".checked_status").val() != "checked"){
		$(obj).css("opacity","0");
		$(obj).css("cursor","default");
	}
}
function change_checked_status(obj) {
	var status = $(obj).find("input").val();
	if(status == "unchecked"){
		$(obj).find(".checked_status").val("checked");
		$(obj).css("background-color","orange");
		$(obj).find("span").html("√");
	}else{
		$(obj).find(".checked_status").val("unchecked");
		$(obj).css("background-color","white");
		$(obj).find("span").html("");
	}
}
/*-------end-------*/