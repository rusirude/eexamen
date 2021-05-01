/**
 * @author: rusiru
 */

var userRoleAutorityTable;


/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var loadReferenceDataForUserRoleAuthority = (callback)=>{
	$.ajax({
        type: "POST",
        url: "/sysRoleAuthority/loadRefDataForSysRoleAuthority",        
        contentType: "application/json",
        dataType: "json",
        success: function(data){    
        	
        	if(data.code === "SUCCESS"){
            	for(let s of data.data.sysRole){            		
            		$("#sysRole").append(`<option value="${s.code}">${s.description}</option>`);
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


var loadDataTableBySysRoleCodeForUserRoleAuthority = ()=>{
	userRoleAutorityTable = $('#sysRoleAuthorityTable').DataTable({
        ajax: {
            url: "/sysRoleAuthority/loadAuthoritiesForSysRole",
            type: 'POST',
            contentType: "application/json",
            data: function () {
                return JSON.stringify({code:($("#sysRole").val()||"")});
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
            {data: "sysRoleDescription"},
            {
            	data: null,
            	render: function (data, type, full) {
					return `<input 
							type="checkbox" id="switch-${data.authorityCode}" name="switch-${data.authorityCode}" 
							data-bootstrap-switch data-off-color="danger" data-on-color="primary"
							onChange="privilegeGrantOrRevorkForForUserRoleAuthority(this,'${data.sysRoleCode}','${data.authorityCode}')"
							${(data.enable)?"checked":""}>`;

            	}
    		}
        ]
	});
}

var successFunctionForUserRoleAuthority = (data)=>{
	if(data.code === Constant.CODE_FAIELD){
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForUserRoleAuthority = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
};

var savePrivilageForUserRoleAuthority = (sysRoleCode,authorityCode)=>{
	let url = "/sysRoleAuthority/save";
	let method = "POST";
	let data = {sysRoleCode:sysRoleCode,authorityCode:authorityCode}
	callToserver(url,method,data,successFunctionForUserRoleAuthority,failedFunctionForUserRoleAuthority);

}

var deletePrivilageForUserRoleAuthority = (sysRoleCode,authorityCode)=>{
	let url = "/sysRoleAuthority/delete";
	let method = "POST";
	let data = {sysRoleCode:sysRoleCode,authorityCode:authorityCode}
	callToserver(url,method,data,successFunctionForUserRoleAuthority,failedFunctionForUserRoleAuthority);

}

var clearDataForUserRoleAuthority = ()=>{

	let sysRole = $("#sysRole");
	sysRole.val("");
	userRoleAutorityTable.ajax.reload();	
	
};


/*-------------------------------- Inline Event  ----------------------*/

function privilegeGrantOrRevorkForForUserRoleAuthority(ele,sysRoleCode,authorityCode){
	if($(ele).is(":checked")){
		savePrivilageForUserRoleAuthority(sysRoleCode,authorityCode);
	}
	else{
		deletePrivilageForUserRoleAuthority(sysRoleCode,authorityCode);
	}	
} 

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForUserRoleAuthority = ()=>{
	$("#sysRole").off().on("change",function(){
		userRoleAutorityTable.ajax.reload();		
	});
	$("#btnCancel").off().on("click",function(){
		clearDataForUserRoleAuthority();
	});
};
/*-------------------------------- Document Ready ----------------------*/

$(document).ready(()=>{	

 	let _callback_1 = ()=>{
 		loadDataTableBySysRoleCodeForUserRoleAuthority();
	};	
	loadReferenceDataForUserRoleAuthority(_callback_1); 
	evenBinderForUserRoleAuthority();
	

});
