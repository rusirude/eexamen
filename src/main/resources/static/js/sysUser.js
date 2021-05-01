/**
 * @author: rusiru
 */

var sysUserTable;

/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForSysUser = ()=>{
	return {
		username:$("#username").val()||"",
		titleCode:$("#title").val()||"",
		name:$("#name").val()||"",
		statusCode:$("#status").val()||""
	}
};

var successFunctionForSysUser = (data)=>{
	if(data.code === Constant.CODE_SUCCESS){
		DialogBox.openMsgBox(data.message,'success');		
		sysUserTable.ajax.reload();
		clearDataForSysUser();
	}
	else{
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForSysUser = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
};

var validatorForSysUser = ()=>{
	let isValid = true;
	
	let username = $("#username");
	let title = $("#title");
	let name = $("#name");
	let status = $("#status");
	
	if(! username.val()){
		InputsValidator.inlineEmptyValidation(username);
		isValid = false;
	}
	if(! title.val()){
		InputsValidator.inlineEmptyValidation(title);
		isValid = false;
	}
	if(! name.val()){
		InputsValidator.inlineEmptyValidation(name);
		isValid = false;
	}
	if(! status.val()){
		InputsValidator.inlineEmptyValidationSelect(status);
		isValid = false;
	}
	return isValid;
};

var saveForSysUser = ()=>{
	if(validatorForSysUser()){
		let url = "/sysUser/save";
		let method = "POST";		
		
		callToserver(url,method,generateFinalObjectForSysUser(),successFunctionForSysUser,failedFunctionForSysUser);
	}
	
};

var updateForSysUser = ()=>{
	if(validatorForSysUser()){
		let url = "/sysUser/update";
		let method = "POST";
				
		callToserver(url,method,generateFinalObjectForSysUser(),successFunctionForSysUser,failedFunctionForSysUser);
	}
};

var deleteForSysUser = ()=>{
	if(validatorForSysUser()){
		let url = "/sysUser/delete";
		let method = "POST";
		
		callToserver(url,method,generateFinalObjectForSysUser(),successFunctionForSysUser,failedFunctionForSysUser);
	}
};

var findDetailByCodeForSysUser = (username,callback)=>{
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
	let url = "/sysUser/loadSysUserByUsername";
	let method = "POST";
	callToserver(url,method,{username:username},successFunction,failedFunction);
	
};

/*-------------------------------- Reference Data , Data Table and Common --------------------*/
var populateFormForSysUser = (data) => {
	if(data){
		$("#username").val(data.username || "");
		$("#title").val(data.titleCode || "");
		$("#name").val(data.name || "");
		$("#status").val(data.statusCode || "");
	}	
};

var loadReferenceDataForSysUser= (callback)=>{
	$.ajax({
        type: "POST",
        url: "/sysUser/loadRefDataForSysUser",        
        contentType: "application/json",
        dataType: "json",
        success: function(data){    
        	
        	if(data.code === Constant.CODE_SUCCESS){
            	for(let s of data.data.status){            		
            		$("#status").append(`<option value="${s.code}">${s.description}</option>`);
            	}
            	for(let t of data.data.title){            		
            		$("#title").append(`<option value="${t.code}">${t.description}</option>`);
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

var loadSysUserTable = ()=>{
	sysUserTable = $('#sysUserTable').DataTable( {
                        ajax: {
                            url : "/sysUser/loadSysUsers",
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
                        processing: true,
                        serverSide: true,
                        columns: [
                            { data: "username"            ,name:"username"            },
                            { data: "titleDescription"    ,name:"title"               },
                            { data: "name"                ,name:"name"                },
                            { data: "statusDescription"   ,name:"status"              },
                            { data: "createdBy"           ,name:"createdBy"           },
                            { data: "createdOn"           ,name:"createdOn"           },
                            { data: "updatedBy"           ,name:"updatedBy"           },
                            { data: "updatedOn"           ,name:"updatedOn"           },
                            {
                            	data: "username",
                            	class:"mdl-data-table__cell--non-numeric",
                            	render: function (data, type, full) {
										return `<button onClick="updateIconClickForSysUser('${data}')" type="button" class="btn btn-outline-primary btn-sm" data-toggle="tooltip" data-placement="bottom" title="Update">
															<i class="fa fa-pencil-alt"></i>
														</button>
														<button onClick="deleteIconClickForSysUser('${data}')" type="button" class="btn btn-outline-danger btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
															<i class="fa fa-trash-alt"></i>
														</button>`;
											}
                    		}
                        ]
                    } );
};

var clearDataForSysUser = ()=>{
	let username = $("#username");
	let title = $("#title");
	let name = $("#name");
	let status = $("#status");
	
	$("#btnSave").show();
	$("#btnUpdate").hide();
	$("#btnDelete").hide();

	$("#formHeading").html("");

	username.prop("disabled",false);
	title.prop("disabled",false);
	name.prop("disabled",false);
	status.prop("disabled",false);

	InputsValidator.removeInlineValidation(username);
	InputsValidator.removeInlineValidation(title);
	InputsValidator.removeInlineValidation(name);
	InputsValidator.removeInlineValidation(status);

	username.val("");
	title.val("");
	name.val("");
	status.val("");
	

	FormTransition.closeModal('#sysUserModal');
	
};

/*-------------------------------- Inline Event  ----------------------*/
var clickAddForSysUser = ()=>{
	clearDataForSysUser();
	$("#formHeading").html("Add System User");
	FormTransition.openModal('#sysUserModal');
};

var updateIconClickForSysUser = (username)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").show();
		$("#btnDelete").hide();
		populateFormForSysUser(data);
		$("#username").prop("disabled",true);
		$("#formHeading").html("Update System User");
		FormTransition.openModal('#sysUserModal');
	};
	clearDataForSysUser();
	findDetailByCodeForSysUser(username,_sF);
};

var deleteIconClickForSysUser = (username)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").hide();
		$("#btnDelete").show();
		populateFormForSysUser(data);
		$("#username").prop("disabled",true);
		$("#title").prop("disabled",true);
		$("#name").prop("disabled",true);
		$("#status").prop("disabled",true);
		$("#formHeading").html("Delete System User");
		FormTransition.openModal('#sysUserModal');
	};
	clearDataForSysUser();
	findDetailByCodeForSysUser(username,_sF);
};

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForSysUser = ()=>{
	$("#btnSysUserAdd").off().on("click",function(){
		clickAddForSysUser();
	});


	$("#btnSave").off().on("click",function(){
		saveForSysUser();
	});
	
	$("#btnUpdate").off().on("click",function(){
		updateForSysUser();
	});
	
	$("#btnDelete").off().on("click",function(){
		deleteForSysUser();
	});
	
	$("#btnCancel").off().on("click",function(){
		clearDataForSysUser();
	});

};


/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{
	loadReferenceDataForSysUser();
	loadSysUserTable();
	evenBinderForSysUser();

});
