/**
 * @author: rusiru
 */

var sectionTable;


/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForSection = ()=>{
	return {
		code:$("#code").val()||"",
		description:$("#description").val()||"",
		statusCode:$("#status").val()||""
	}
};

var successFunctionForSection = (data)=>{
	if(data.code === Constant.CODE_SUCCESS){
		DialogBox.openMsgBox(data.message,'success');
		sectionTable.ajax.reload();
		clearDataForSection();
	}
	else{
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForSection = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
};

var validatorForSection = ()=>{
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

var saveForSection = ()=>{
	if(validatorForSection()){
		let url = "/section/save";
		let method = "POST";

		callToserver(url,method,generateFinalObjectForSection(),successFunctionForSection,failedFunctionForSection);
	}

};

var updateForSection = ()=>{
	if(validatorForSection()){
		let url = "/section/update";
		let method = "POST";

		callToserver(url,method,generateFinalObjectForSection(),successFunctionForSection,failedFunctionForSection);
	}
};

var deleteForSection = ()=>{
	if(validatorForSection()){
		let url = "/section/delete";
		let method = "POST";

		callToserver(url,method,generateFinalObjectForSection(),successFunctionForSection,failedFunctionForSection);
	}
};

var findDetailByCodeForSection = (code,callback)=>{
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
	let url = "/section/loadSectionByCode";
	let method = "POST";
	callToserver(url,method,{code:code},successFunction,failedFunction);

};


/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var populateFormForSection = (data) => {
	if(data){
		$("#code").val(data.code || "");
		$("#description").val(data.description || "");
		$("#status").val(data.statusCode || "");
	}
};

var loadReferenceDataForSection = (callback)=>{
	$.ajax({
		type: "POST",
		url: "/section/loadRefDataForSection",
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


var loadSectionTable = ()=>{
	sectionTable = $('#sectionTable').DataTable( {
		ajax: {
			url : "/section/loadSections",
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
					return `<button onClick="updateIconClickForSection('${data}')" type="button" class="btn btn-outline-primary btn-sm" data-toggle="tooltip" data-placement="bottom" title="Update">
														<i class="fa fa-pencil-alt"></i>
													</button>
													<button onClick="deleteIconClickForSection('${data}')" type="button" class="btn btn-outline-danger btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
														<i class="fa fa-trash-alt"></i>
													</button>`;
				}
			}
		]
	} );
}

var clearDataForSection = ()=>{
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

	FormTransition.closeModal('#sectionModal');

};


/*-------------------------------- Inline Event  ----------------------*/
var clickAddForSection = ()=>{
	clearDataForSection();
	$("#formHeading").html("Add Section");
	FormTransition.openModal('#sectionModal');
};

var updateIconClickForSection = (code)=>{
	console.log(code);
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").show();
		$("#btnDelete").hide();
		populateFormForSection(data);
		$("#code").prop("disabled",true);
		$("#formHeading").html("Update Section");
		FormTransition.openModal('#sectionModal');
	};
	clearDataForSection();
	findDetailByCodeForSection(code,_sF);
};

var deleteIconClickForSection = (code)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").hide();
		$("#btnDelete").show();
		populateFormForSection(data);
		$("#code").prop("disabled",true);
		$("#description").prop("disabled",true);
		$("#status").prop("disabled",true);
		$("#formHeading").html("Delete Section");
		FormTransition.openModal('#sectionModal');
	};
	clearDataForSection();
	findDetailByCodeForSection(code,_sF);
};


/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForSection = ()=>{
	$("#btnSectionAdd").off().on("click",function(){
		clickAddForSection();
	});

	$("#btnSave").off().on("click",function(){
		saveForSection();
	});

	$("#btnUpdate").off().on("click",function(){
		updateForSection();
	});

	$("#btnDelete").off().on("click",function(){
		deleteForSection();
	});

	$("#btnCancel").off().on("click",function(){
		clearDataForSection();
	});

};

/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{
	loadReferenceDataForSection();
	evenBinderForSection();
	loadSectionTable();
});