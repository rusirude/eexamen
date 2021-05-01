/**
 * @author: rusiru
 */

var sysRoleTable;


/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForSysRole = ()=>{
	return {
		code:$("#code").val()||"",
		description:$("#description").val()||"",
		statusCode:$("#status").val()||""
	}
};

var successFunctionForSysRole = (data)=>{
	if(data.code === Constant.CODE_SUCCESS){
		DialogBox.openMsgBox(data.message,'success');
		sysRoleTable.ajax.reload();
		clearDataForSysRole();
	}
	else{
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForSysRole = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
};

var validatorForSysRole = ()=>{
	let isValid = true;
	
	let code = $("#code");
	let description = $("#description");
	let status = $("#status");
	
	if(! code.val()){
		InputsValidator.inlineEmptyValidation(code);
		isValid = false;
	}
	if(! description.val()){
		InputsValidator.inlineEmptyValidation(description);
		isValid = false;
	}
	if(! status.val()){
		InputsValidator.inlineEmptyValidationSelect(status);
		isValid = false;
	}
	return isValid;
};

var saveForSysRole = ()=>{
	if(validatorForSysRole()){
		let url = "/sysRole/save";
		let method = "POST";		
		
		callToserver(url,method,generateFinalObjectForSysRole(),successFunctionForSysRole,failedFunctionForSysRole);
	}
	
};

var updateForSysRole = ()=>{
	if(validatorForSysRole()){
		let url = "/sysRole/update";
		let method = "POST";
				
		callToserver(url,method,generateFinalObjectForSysRole(),successFunctionForSysRole,failedFunctionForSysRole);
	}
};

var deleteForSysRole = ()=>{
	if(validatorForSysRole()){
		let url = "/sysRole/delete";
		let method = "POST";
		
		callToserver(url,method,generateFinalObjectForSysRole(),successFunctionForSysRole,failedFunctionForSysRole);
	}
};

var findDetailByCodeForSysRole = (code,callback)=>{
	let successFunction = (data)=>{
		if(data.code === Constant.CODE_SUCCESS){			
			if(callback){
				callback(data.data);
			}
		}
		else{
			DialogBox.openMsgBox(data.message,'error');
		}
	};
	let failedFunction = (data)=>{
		DialogBox.openMsgBox("Server Error",'error');
	};
	let url = "/sysRole/loadSysRoleByCode";
	let method = "POST";
	callToserver(url,method,{code:code},successFunction,failedFunction);
	
};


/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var populateFormForSysRole = (data) => {
	if(data){
		$("#code").val(data.code || "");
		$("#description").val(data.description || "");
		$("#status").val(data.statusCode || "");
	}	
};

var loadReferenceDataForSysRole = (callback)=>{
	$.ajax({
        type: "POST",
        url: "/sysRole/loadRefDataForSysRole",
        contentType: "application/json",
        dataType: "json",
        success: function(data){    
        	
        	if(data.code === Constant.CODE_SUCCESS){
            	for(let s of data.data.status){            		
            		$("#status").append(`<option value="${s.code}">${s.description}</option>`);
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
			DialogBox.openMsgBox(errMsg,'error');
        }
  });
};


var loadSysRoleTable = ()=>{
	sysRoleTable = $('#sysRoleTable').DataTable( {
                        ajax: {
                            url : "/sysRole/loadSysRoles",
                            contentType:"application/json",
                            type:"POST",
                            data:function(d){
                                return JSON.stringify(createCommonDataTableRequset(d));
                    		}
                        },
						paging: true,
						lengthChange: false,
						searching: true,
						ordering: true,
						info: true,
						autoWidth: false,
						responsive: true,
                        processing: true,
                        serverSide: true,
                        columns: [
                            { data: "code"                ,name:"code"                },
                            { data: "description"         ,name:"description"         },
                            { data: "statusDescription"   ,name:"status"              },
                            { data: "createdBy"           ,name:"createdBy"           },
                            { data: "createdOn"           ,name:"createdOn"           },
                            { data: "updatedBy"           ,name:"updatedBy"           },
                            { data: "updatedOn"           ,name:"updatedOn"           },
                            {
                            	data: "code",
                            	render: function (data, type, full) {
		                            		return `<button onClick="updateIconClickForSysRole('${data}')" type="button" class="btn btn-outline-primary btn-sm" data-toggle="tooltip" data-placement="bottom" title="Update">
														<i class="fa fa-pencil-alt"></i>
													</button>
													<button onClick="deleteIconClickForSysRole('${data}')" type="button" class="btn btn-outline-danger btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
														<i class="fa fa-trash-alt"></i>
													</button>`;
		                            	}
                    		}
                        ]
                    } );
}

var clearDataForSysRole = ()=>{
	let code = $("#code");
	let description = $("#description");
	let status = $("#status");
	
	$("#btnSave").show();
	$("#btnUpdate").hide();
	$("#btnDelete").hide();

	$("#formHeading").html("");
	
	code.prop("disabled",false);
	description.prop("disabled",false);
	status.prop("disabled",false);

	InputsValidator.removeInlineValidation(code);
	InputsValidator.removeInlineValidation(description);
	InputsValidator.removeInlineValidation(status);
	
	code.val("");
	description.val("");
	status.val("");

	FormTransition.closeModal('#sysRoleModal');
	
};


/*-------------------------------- Inline Event  ----------------------*/
var clickAddForSysRole = ()=>{
	clearDataForSysRole();
	$("#formHeading").html("Add System Role");
	FormTransition.openModal('#sysRoleModal');
};

var updateIconClickForSysRole = (code)=>{
	console.log(code);
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").show();
		$("#btnDelete").hide();
		populateFormForSysRole(data);
		$("#code").prop("disabled",true);
		$("#formHeading").html("Update System Role");
		FormTransition.openModal('#sysRoleModal');
	};
	clearDataForSysRole();
	findDetailByCodeForSysRole(code,_sF);
};

var deleteIconClickForSysRole = (code)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").hide();
		$("#btnDelete").show();
		populateFormForSysRole(data);
		$("#code").prop("disabled",true);
		$("#description").prop("disabled",true);
		$("#status").prop("disabled",true);
		$("#formHeading").html("Delete System Role");
		FormTransition.openModal('#sysRoleModal');
	};
	clearDataForSysRole();
	findDetailByCodeForSysRole(code,_sF);
};


/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForSysRole = ()=>{
	$("#btnSysRoleAdd").off().on("click",function(){
		clickAddForSysRole();
	});

	$("#btnSave").off().on("click",function(){
		saveForSysRole();
	});
	
	$("#btnUpdate").off().on("click",function(){
		updateForSysRole();
	});
	
	$("#btnDelete").off().on("click",function(){
		deleteForSysRole();
	});
	
	$("#btnCancel").off().on("click",function(){
		clearDataForSysRole();
	});

};

/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{
	loadReferenceDataForSysRole();
	evenBinderForSysRole();
	loadSysRoleTable();
});