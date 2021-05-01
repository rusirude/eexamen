/**
 * @author: rusiru
 */

var questionCategoryTable;


/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForQuestionCategory = ()=>{
	return {
		code:$("#code").val()||"",
		description:$("#description").val()||"",
		statusCode:$("#status").val()||""
	}
};

var successFunctionForQuestionCategory = (data)=>{
	if(data.code === Constant.CODE_SUCCESS){
		DialogBox.openMsgBox(data.message,'success');
		questionCategoryTable.ajax.reload();
		clearDataForQuestionCategory();
	}
	else{
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForQuestionCategory = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
};

var validatorForQuestionCategory = ()=>{
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

var saveForQuestionCategory = ()=>{
	if(validatorForQuestionCategory()){
		let url = "/questionCategory/save";
		let method = "POST";

		callToserver(url,method,generateFinalObjectForQuestionCategory(),successFunctionForQuestionCategory,failedFunctionForQuestionCategory);
	}

};

var updateForQuestionCategory = ()=>{
	if(validatorForQuestionCategory()){
		let url = "/questionCategory/update";
		let method = "POST";

		callToserver(url,method,generateFinalObjectForQuestionCategory(),successFunctionForQuestionCategory,failedFunctionForQuestionCategory);
	}
};

var deleteForQuestionCategory = ()=>{
	if(validatorForQuestionCategory()){
		let url = "/questionCategory/delete";
		let method = "POST";

		callToserver(url,method,generateFinalObjectForQuestionCategory(),successFunctionForQuestionCategory,failedFunctionForQuestionCategory);
	}
};

var findDetailByCodeForQuestionCategory = (code,callback)=>{
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
	let url = "/questionCategory/loadQuestionCategoryByCode";
	let method = "POST";
	callToserver(url,method,{code:code},successFunction,failedFunction);

};


/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var populateFormForQuestionCategory = (data) => {
	if(data){
		$("#code").val(data.code || "");
		$("#description").val(data.description || "");
		$("#status").val(data.statusCode || "");
	}
};

var loadReferenceDataForQuestionCategory = (callback)=>{
	$.ajax({
		type: "POST",
		url: "/questionCategory/loadRefDataForQuestionCategory",
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


var loadQuestionCategoryTable = ()=>{
	questionCategoryTable = $('#questionCategoryTable').DataTable( {
		ajax: {
			url : "/questionCategory/loadQuestionCategories",
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
					return `<button onClick="updateIconClickForQuestionCategory('${data}')" type="button" class="btn btn-outline-primary btn-sm" data-toggle="tooltip" data-placement="bottom" title="Update">
														<i class="fa fa-pencil-alt"></i>
													</button>
													<button onClick="deleteIconClickForQuestionCategory('${data}')" type="button" class="btn btn-outline-danger btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
														<i class="fa fa-trash-alt"></i>
													</button>`;
				}
			}
		]
	} );
}

var clearDataForQuestionCategory = ()=>{
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

	FormTransition.closeModal('#questionCategoryModal');

};


/*-------------------------------- Inline Event  ----------------------*/
var clickAddForQuestionCategory = ()=>{
	clearDataForQuestionCategory();
	$("#formHeading").html("Add QuestionCategory");
	FormTransition.openModal('#questionCategoryModal');
};

var updateIconClickForQuestionCategory = (code)=>{
	console.log(code);
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").show();
		$("#btnDelete").hide();
		populateFormForQuestionCategory(data);
		$("#code").prop("disabled",true);
		$("#formHeading").html("Update QuestionCategory");
		FormTransition.openModal('#questionCategoryModal');
	};
	clearDataForQuestionCategory();
	findDetailByCodeForQuestionCategory(code,_sF);
};

var deleteIconClickForQuestionCategory = (code)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").hide();
		$("#btnDelete").show();
		populateFormForQuestionCategory(data);
		$("#code").prop("disabled",true);
		$("#description").prop("disabled",true);
		$("#status").prop("disabled",true);
		$("#formHeading").html("Delete QuestionCategory");
		FormTransition.openModal('#questionCategoryModal');
	};
	clearDataForQuestionCategory();
	findDetailByCodeForQuestionCategory(code,_sF);
};


/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForQuestionCategory = ()=>{
	$("#btnQuestionCategoryAdd").off().on("click",function(){
		clickAddForQuestionCategory();
	});

	$("#btnSave").off().on("click",function(){
		saveForQuestionCategory();
	});

	$("#btnUpdate").off().on("click",function(){
		updateForQuestionCategory();
	});

	$("#btnDelete").off().on("click",function(){
		deleteForQuestionCategory();
	});

	$("#btnCancel").off().on("click",function(){
		clearDataForQuestionCategory();
	});

};

/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{
	loadReferenceDataForQuestionCategory();
	evenBinderForQuestionCategory();
	loadQuestionCategoryTable();
});