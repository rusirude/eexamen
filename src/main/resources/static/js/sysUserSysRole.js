/**
 * @author: rusiru
 */

var sysUserSysRoleTable;


/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var loadReferenceDataForSysUserSysRole = (callback)=>{
	$.ajax({
        type: "POST",
        url: "/sysUserSysRole/loadRefDataForSysUserSysRole",        
        contentType: "application/json",
        dataType: "json",
        success: function(data){    
        	
        	if(data.code === "SUCCESS"){
            	for(let s of data.data.sysUser){            		
            		$("#sysUser").append(`<option value="${s.code}">${s.description}</option>`);
            	}
            	
            	if(callback){
            		callback();
            	}
        	}
        	else{
				DialogBox.openMsgBox("System Failer Occur....! :-(",'error');
			}
        	

    	},
        failure: function(errMsg) {
			DialogBox.openMsgBox("errMsg",'error');
        }
  });
};


var loadDataTableBySysUserUsernameForSysUserSysRole = ()=>{
	sysUserSysRoleTable = $('#sysUserSysRoleTable').DataTable({
        ajax: {
            url: "/sysUserSysRole/loadSysRolesForSysUser",
            type: 'POST',
            contentType: "application/json",
            data: function () {
                return JSON.stringify({username:($("#sysUser").val()||"")});
            },
            error: function (jqXHR, textStatus, errorThrown) {
				DialogBox.openMsgBox("System Failer Occur....! :-(",'error');
			}
        },
		paging: true,
		lengthChange: false,
		searching: true,
		ordering: true,
		info: true,
		autoWidth: false,
		drawCallback: function( settings ) {
			$("input[data-bootstrap-switch]").each(function(){
				$(this).bootstrapSwitch();
			})
		},
        columns: [
            {data: "sysRoleDescription"},
            {data: "username"},
            {
            	data: null,
            	render: function (data, type, full) {
					return `<input 
							type="checkbox" id="switch-${data.sysRoleCode}" name="switch-${data.sysRoleCode}" 
							data-bootstrap-switch data-off-color="danger" data-on-color="primary"
							onChange="privilegeGrantOrRevorkForForSysUserSysRole(this,'${data.username}','${data.sysRoleCode}')"
							${(data.enable)?"checked":""}>`;
            	}
    		}
        ]
	});
}

var successFunctionForSysUserSysRole = (data)=>{
	if(data.code === Constant.CODE_FAIELD){
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForSysUserSysRole = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
};

var savePrivilageForSysUserSysRole = (username,sysRoleCode)=>{
	let url = "/sysUserSysRole/save";
	let method = "POST";
	let data = {username:username,sysRoleCode:sysRoleCode}
	callToserver(url,method,data,successFunctionForSysUserSysRole,failedFunctionForSysUserSysRole);

}

var deletePrivilageForSysUserSysRole = (username,sysRoleCode)=>{
	let url = "/sysUserSysRole/delete";
	let method = "POST";
	let data = {username:username,sysRoleCode:sysRoleCode}
	callToserver(url,method,data,successFunctionForSysUserSysRole,failedFunctionForSysUserSysRole);

}

var clearDataForSysUserSysRole = ()=>{

	let sysUser = $("#sysUser");
	sysUser.val("");
	sysUser[0].parentElement.MaterialSelectfield.change("");
	sysUserSysRoleTable.ajax.reload();	
	
};


/*-------------------------------- Inline Event  ----------------------*/

function privilegeGrantOrRevorkForForSysUserSysRole(ele,username,sysRoleCode){
	if($(ele).is(":checked")){
		savePrivilageForSysUserSysRole(username,sysRoleCode);
	}
	else{
		deletePrivilageForSysUserSysRole(username,sysRoleCode);
	}	
} 

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForSysUserSysRole = ()=>{
	$("#sysUser").off().on("change",function(){
		sysUserSysRoleTable.ajax.reload();		
	});
	$("#btnCancel").off().on("click",function(){
		clearDataForSysUserSysRole();
	});
};
/*-------------------------------- Document Ready ----------------------*/

$(document).ready(()=>{	

 	let _callback_1 = ()=>{
 		loadDataTableBySysUserUsernameForSysUserSysRole();
	};	
	loadReferenceDataForSysUserSysRole(_callback_1); 
	evenBinderForSysUserSysRole();
	

});
