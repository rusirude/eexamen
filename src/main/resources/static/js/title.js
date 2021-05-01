/**
 * @author: rusiru
 */

var titleTable;


/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForTitle = ()=>{
	return {
		code:$("#code").val()||"",
		description:$("#description").val()||"",
		statusCode:$("#status").val()||""
	}
};

var successFunctionForTitle = (data)=>{
	if(data.code === Constant.CODE_SUCCESS){
		DialogBox.openMsgBox(data.message,'success');
		titleTable.ajax.reload();
		clearDataForTitle();
	}
	else{
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForTitle = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
};

var validatorForTitle = ()=>{
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

var saveForTitle = ()=>{
	if(validatorForTitle()){
		let url = "/title/save";
		let method = "POST";
		callToserver(url,method,generateFinalObjectForTitle(),successFunctionForTitle,failedFunctionForTitle);
	}

};

var updateForTitle = ()=>{
	if(validatorForTitle()){
		let url = "/title/update";
		let method = "POST";
		callToserver(url,method,generateFinalObjectForTitle(),successFunctionForTitle,failedFunctionForTitle);
	}
};

var deleteForTitle = ()=>{
	if(validatorForTitle()){
		let url = "/title/delete";
		let method = "POST";
		callToserver(url,method,generateFinalObjectForTitle(),successFunctionForTitle,failedFunctionForTitle);
	}
};

var findDetailByCodeForTitle = (code,callback)=>{
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
	let url = "/title/loadTitleByCode";
	let method = "POST";
	callToserver(url,method,{code:code},successFunction,failedFunction);

};


/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var populateFormForTitle = (data) => {
	if(data){
		$("#code").val(data.code || "");
		$("#description").val(data.description || "");
		$("#status").val(data.statusCode || "");
	}
};

var loadReferenceDataForTitle = (callback)=>{
	$.ajax({
		type: "POST",
		url: "/title/loadRefDataForTitle",
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


var loadTitleTable = ()=>{
	titleTable = $('#titleTable').DataTable( {
		ajax: {
			url : "/title/loadTitles",
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
					return `<button onClick="updateIconClickForTitle('${data}')" type="button" class="btn btn-outline-primary btn-sm" data-toggle="tooltip" data-placement="bottom" title="Update">
														<i class="fa fa-pencil-alt"></i>
													</button>
													<button onClick="deleteIconClickForTitle('${data}')" type="button" class="btn btn-outline-danger btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
														<i class="fa fa-trash-alt"></i>
													</button>`;
				}
			}
		]
	} );
}

var clearDataForTitle = ()=>{
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

	FormTransition.closeModal('#titleModal');

};


/*-------------------------------- Inline Event  ----------------------*/
var clickAddForTitle = ()=>{
	clearDataForTitle();
	$("#formHeading").html("Add Title");
	FormTransition.openModal('#titleModal');
};

var updateIconClickForTitle = (code)=>{
	console.log(code);
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").show();
		$("#btnDelete").hide();
		populateFormForTitle(data);
		$("#code").prop("disabled",true);
		$("#formHeading").html("Update Title");
		FormTransition.openModal('#titleModal');
	};
	clearDataForTitle();
	findDetailByCodeForTitle(code,_sF);
};

var deleteIconClickForTitle = (code)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").hide();
		$("#btnDelete").show();
		populateFormForTitle(data);
		$("#code").prop("disabled",true);
		$("#description").prop("disabled",true);
		$("#status").prop("disabled",true);
		$("#formHeading").html("Delete Title");
		FormTransition.openModal('#titleModal');
	};
	clearDataForTitle();
	findDetailByCodeForTitle(code,_sF);
};


/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForTitle = ()=>{
	$("#btnTitleAdd").off().on("click",function(){
		clickAddForTitle();
	});

	$("#btnSave").off().on("click",function(){
		saveForTitle();
	});

	$("#btnUpdate").off().on("click",function(){
		updateForTitle();
	});

	$("#btnDelete").off().on("click",function(){
		deleteForTitle();
	});

	$("#btnCancel").off().on("click",function(){
		clearDataForTitle();
	});

};

/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{
	loadReferenceDataForTitle();
	evenBinderForTitle();
	loadTitleTable();
});