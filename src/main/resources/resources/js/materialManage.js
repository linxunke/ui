$(document).ready(function() {
	
});

function show_custom_checkedbox(obj) {
	$(obj).css("cursor","pointer");
	$(obj).css("opacity","1");
}
function hiden_custom_checkedbox(obj) {
	console.log("status:"+$(obj).val());
	if($(obj).val() != "checked"){
		$(obj).css("opacity","0");
		$(obj).css("cursor","default");
	}
}
function change_checked_status(obj) {
	var status = $(obj).val();
	if(status == "unchecked"){
		$(obj).val("checked");
		$(obj).css("background-color","orange");
		$(obj).html("√");
	}else{
		$(obj).val("unchecked");
		$(obj).css("background-color","white");
		$(obj).empty();
	}
}