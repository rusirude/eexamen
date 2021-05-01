/**
 * @author: rusiru
 */

var sysUserAutorityTable;


/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var loadReferenceDataForSysUserAuthority = (callback)=>{
	$.ajax({
        type: "POST",
        url: "/sysUserAuthority/loadRefDataForSysUserAuthority",        
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


var loadDataTableByUsernameForSysUserAuthority = ()=>{
	sysUserAutorityTable = $('#sysUserAuthorityTable').DataTable({
        ajax: {
            url: "/sysUserAuthority/loadAuthoritiesForSysUser",
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
            {data: "authorityDescription"},
            {data: "username"},
            {
            	data: null,
            	render: function (data, type, full) {
					return `<input 
							type="checkbox" id="switch-${data.authorityCode}" name="switch-${data.authorityCode}" 
							data-bootstrap-switch data-off-color="danger" data-on-color="primary"
							onChange="privilegeGrantOrRevorkForForSysUserAuthority(this,'${data.username}','${data.authorityCode}')"
							${(data.enable)?"checked":""}>`;
            	}
    		}
        ]
	});
}

var successFunctionForSysUserAuthority = (data)=>{
	if(data.code === Constant.CODE_FAIELD){
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForSysUserAuthority = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');

};

var savePrivilageForSysUserAuthority = (username,authorityCode)=>{
	let url = "/sysUserAuthority/save";
	let method = "POST";
	let data = {username:username,authorityCode:authorityCode}
	callToserver(url,method,data,successFunctionForSysUserAuthority,failedFunctionForSysUserAuthority);

}

var deletePrivilageForSysUserAuthority = (username,authorityCode)=>{
	let url = "/sysUserAuthority/delete";
	let method = "POST";
	let data = {username:username,authorityCode:authorityCode}
	callToserver(url,method,data,successFunctionForSysUserAuthority,failedFunctionForSysUserAuthority);

}

var clearDataForSysUserAuthority = ()=>{

	let sysUser = $("#sysUser");
	sysUser.val("");
	sysUser[0].parentElement.MaterialSelectfield.change("");
	sysUserAutorityTable.ajax.reload();	
	
};


/*-------------------------------- Inline Event  ----------------------*/

function privilegeGrantOrRevorkForForSysUserAuthority(ele,username,authorityCode){
	if($(ele).is(":checked")){
		savePrivilageForSysUserAuthority(username,authorityCode);
	}
	else{
		deletePrivilageForSysUserAuthority(username,authorityCode);
	}	
} 

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForSysUserAuthority = ()=>{
	$("#sysUser").off().on("change",function(){
		sysUserAutorityTable.ajax.reload();		
	});
	$("#btnCancel").off().on("click",function(){
		clearDataForSysUserAuthority();
	});
};
/*-------------------------------- Document Ready ----------------------*/

$(document).ready(()=>{	

 	let _callback_1 = ()=>{
 		loadDataTableByUsernameForSysUserAuthority();
	};	
	loadReferenceDataForSysUserAuthority(_callback_1); 
	evenBinderForSysUserAuthority();
	

});
