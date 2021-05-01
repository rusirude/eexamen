var authorityTable;
/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForAuthority = ()=>{
	return {
		code:$("#code").val()||"",
		description:$("#description").val()||"",
		url:$("#url").val()||"",
		authCode: $("#authCode").val()||"",
		sectionCode:$("#section").val()||"",
		statusCode:$("#status").val()||""
	}
};

var successFunctionForAuthority = (data)=>{
	if(data.code === Constant.CODE_SUCCESS){
		DialogBox.openMsgBox(data.message,'success');
		authorityTable.ajax.reload();
		clearDataForAuthority();
	}
	else{
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForAuthority = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
};

var validatorForAuthority = ()=>{
	let isValid = true;
	
	let code = $("#code");
	let description = $("#description");
	let url = $("#url");	
	let authCode = $("#authCode");	
	let section = $("#section");
	let status = $("#status");
	
	if(! code.val()){
		InputsValidator.inlineEmptyValidation(code);
		isValid = false;
	}
	if(! description.val()){
		InputsValidator.inlineEmptyValidation(description);
		isValid = false;
	}
	if(! url.val()){
		InputsValidator.inlineEmptyValidation(url);
		isValid = false;
	}
	if(! authCode.val()){
		InputsValidator.inlineEmptyValidation(authCode);
		isValid = false;
	}
	if(! section.val()){
		InputsValidator.inlineEmptyValidationSelect(section);
		isValid = false;
	}
	if(! status.val()){
		InputsValidator.inlineEmptyValidationSelect(status);
		isValid = false;
	}
	return isValid;
};


var saveForAuthority = ()=>{
	if(validatorForAuthority()){
		let url = "/authority/save";
		let method = "POST";		
		
		callToserver(url,method,generateFinalObjectForAuthority(),successFunctionForAuthority,failedFunctionForAuthority);
	}
	
};

var updateForAuthority = ()=>{
	if(validatorForAuthority()){
		let url = "/authority/update";
		let method = "POST";
				
		callToserver(url,method,generateFinalObjectForAuthority(),successFunctionForAuthority,failedFunctionForAuthority);
	}
};

var deleteForAuthority = ()=>{
	if(validatorForAuthority()){
		let url = "/authority/delete";
		let method = "POST";
		
		callToserver(url,method,generateFinalObjectForAuthority(),successFunctionForAuthority,failedFunctionForAuthority);
	}
};

var findDetailByCodeForAuthority = (code,callback)=>{
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
	let url = "/authority/loadAuthorityByCode";
	let method = "POST";
	callToserver(url,method,{code:code},successFunction,failedFunction);
	
};

/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var populateFormForAuthority = (data) => {
	if(data){
		$("#code").val(data.code || "");
		$("#description").val(data.description || "");
		$("#url").val(data.url || "");
		$("#authCode").val(data.authCode || "");
		$("#section").val(data.sectionCode || "");
		$("#status").val(data.statusCode || "");
	}	
};

var loadReferenceDataForAuthority = (callback)=>{
	$.ajax({
        type: "POST",
        url: "/authority/loadRefDataForAuthority",        
        contentType: "application/json",
        dataType: "json",
        success: function(data){    
        	
        	if(data.code === Constant.CODE_SUCCESS){
            	for(let s of data.data.status||[]){            		
            		$("#status").append(`<option value="${s.code}">${s.description}</option>`);
            	}
            	for(let sc of data.data.sections||[]){            		
            		$("#section").append(`<option value="${sc.code}">${sc.description}</option>`);
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


var loadAuthorityTable = ()=>{
	authorityTable = $('#authorityTable').DataTable( {
                        ajax: {
                            url : "/authority/loadAuthorities",
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
						scrollX:        true,
                        columns: [
                            { data: "code"                ,name:"code"          },
                            { data: "description"         ,name:"description"   },
                            { data: "url"                 ,name:"url"           },
                            { data: "authCode"            ,name:"authCode"      },
                            { data: "sectionDescription"  ,name:"section"       },
                            { data: "statusDescription"   ,name:"status"        },
                            { data: "createdBy"           ,name:"createdBy"     },
                            { data: "createdOn"           ,name:"createdOn"     },
                            { data: "updatedBy"           ,name:"updatedBy"     },
                            { data: "updatedOn"           ,name:"updatedOn"     },
                            {
                            	data: "code",
                            	render: function (data, type, full) {
											return `<button onClick="updateIconClickForAuthority('${data}')" type="button" class="btn btn-outline-primary btn-sm" data-toggle="tooltip" data-placement="bottom" title="Update">
														<i class="fa fa-pencil-alt"></i>
													</button>
													<button onClick="deleteIconClickForAuthority('${data}')" type="button" class="btn btn-outline-danger btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
														<i class="fa fa-trash-alt"></i>
													</button>`;
		                            	}
                    		}
                        ]
                    } );
};

var clearDataForAuthority = ()=>{
	let code = $("#code");
	let description = $("#description");
	let url = $("#url");
	let authCode = $("#authCode");
	let section = $("#section");
	let status = $("#status");
	
	$("#btnSave").show();
	$("#btnUpdate").hide();
	$("#btnDelete").hide();

	$("#formHeading").html("");

	code.prop("disabled",false);
	description.prop("disabled",false);
	url.prop("disabled",false);
	authCode.prop("disabled",false);
	status.prop("disabled",false);
	section.prop("disabled",false);


	InputsValidator.removeInlineValidation(code);
	InputsValidator.removeInlineValidation(description);
	InputsValidator.removeInlineValidation(url);
	InputsValidator.removeInlineValidation(authCode);
	InputsValidator.removeInlineValidation(section);
	InputsValidator.removeInlineValidation(status);

	code.val("");
	description.val("");
	url.val("");
	authCode.val("");
	status.val("");
	section.val("");

	FormTransition.closeModal('#authorityModal');
	
};


/*-------------------------------- Inline Event  ----------------------*/
var clickAddForAuthority = ()=>{
	clearDataForAuthority();
	$("#formHeading").html("Add Authority");
	FormTransition.openModal('#authorityModal');
};

var updateIconClickForAuthority = (code)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").show();
		$("#btnDelete").hide();
		populateFormForAuthority(data);
		$("#code").prop("disabled",true);
		$("#authCode").prop("disabled",true);
		$("#url").prop("disabled",true);
		$("#formHeading").html("Update Authority");
		FormTransition.openModal('#authorityModal');
	};
	clearDataForAuthority();
	findDetailByCodeForAuthority(code,_sF);
};

var deleteIconClickForAuthority = (code)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").hide();
		$("#btnDelete").show();
		populateFormForAuthority(data);
		$("#code").prop("disabled",true);
		$("#description").prop("disabled",true);
		$("#url").prop("disabled",true);
		$("#authCode").prop("disabled",true);
		$("#status").prop("disabled",true);
		$("#section").prop("disabled",true);
		$("#formHeading").html("Delete Authority");
		FormTransition.openModal('#authorityModal');
	};
	clearDataForAuthority();
	findDetailByCodeForAuthority(code,_sF);
};

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForAuthority = ()=>{
	$("#btnAuthorityAdd").off().on("click",function(){
		clickAddForAuthority();
	});

	$("#btnSave").off().on("click",function(){
		saveForAuthority();
	});
	
	$("#btnUpdate").off().on("click",function(){
		updateForAuthority();
	});
	
	$("#btnDelete").off().on("click",function(){
		deleteForAuthority();
	});
	
	$("#btnCancel").off().on("click",function(){
		clearDataForAuthority();
	});

};


/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{
	loadReferenceDataForAuthority();
	loadAuthorityTable();
	evenBinderForAuthority();

});