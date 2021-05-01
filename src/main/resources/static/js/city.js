/**
 * @author: rusiru
 */

var cityTable;


/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForCity = ()=>{
	return {
		code:$("#code").val()||"",
		description:$("#description").val()||"",
		statusCode:$("#status").val()||""
	}
};

var successFunctionForCity = (data)=>{
	if(data.code === Constant.CODE_SUCCESS){
		DialogBox.openMsgBox(data.message,'success');
		cityTable.ajax.reload();
		clearDataForCity();
	}
	else{
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForCity = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
};

var validatorForCity = ()=>{
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

var saveForCity = ()=>{
	if(validatorForCity()){
		let url = "/city/save";
		let method = "POST";

		callToserver(url,method,generateFinalObjectForCity(),successFunctionForCity,failedFunctionForCity);
	}

};

var updateForCity = ()=>{
	if(validatorForCity()){
		let url = "/city/update";
		let method = "POST";

		callToserver(url,method,generateFinalObjectForCity(),successFunctionForCity,failedFunctionForCity);
	}
};

var deleteForCity = ()=>{
	if(validatorForCity()){
		let url = "/city/delete";
		let method = "POST";

		callToserver(url,method,generateFinalObjectForCity(),successFunctionForCity,failedFunctionForCity);
	}
};

var findDetailByCodeForCity = (code,callback)=>{
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
	let url = "/city/loadCityByCode";
	let method = "POST";
	callToserver(url,method,{code:code},successFunction,failedFunction);

};


/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var populateFormForCity = (data) => {
	if(data){
		$("#code").val(data.code || "");
		$("#description").val(data.description || "");
		$("#status").val(data.statusCode || "");
	}
};

var loadReferenceDataForCity = (callback)=>{
	$.ajax({
		type: "POST",
		url: "/city/loadRefDataForCity",
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


var loadCityTable = ()=>{
	cityTable = $('#cityTable').DataTable( {
		ajax: {
			url : "/city/loadCities",
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
					return `<button onClick="updateIconClickForCity('${data}')" type="button" class="btn btn-outline-primary btn-sm" data-toggle="tooltip" data-placement="bottom" title="Update">
														<i class="fa fa-pencil-alt"></i>
													</button>
													<button onClick="deleteIconClickForCity('${data}')" type="button" class="btn btn-outline-danger btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
														<i class="fa fa-trash-alt"></i>
													</button>`;
				}
			}
		]
	} );
}

var clearDataForCity = ()=>{
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

	FormTransition.closeModal('#cityModal');

};


/*-------------------------------- Inline Event  ----------------------*/
var clickAddForCity = ()=>{
	clearDataForCity();
	$("#formHeading").html("Add City");
	FormTransition.openModal('#cityModal');
};

var updateIconClickForCity = (code)=>{
	console.log(code);
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").show();
		$("#btnDelete").hide();
		populateFormForCity(data);
		$("#code").prop("disabled",true);
		$("#formHeading").html("Update City");
		FormTransition.openModal('#cityModal');
	};
	clearDataForCity();
	findDetailByCodeForCity(code,_sF);
};

var deleteIconClickForCity = (code)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").hide();
		$("#btnDelete").show();
		populateFormForCity(data);
		$("#code").prop("disabled",true);
		$("#description").prop("disabled",true);
		$("#status").prop("disabled",true);
		$("#formHeading").html("Delete City");
		FormTransition.openModal('#cityModal');
	};
	clearDataForCity();
	findDetailByCodeForCity(code,_sF);
};


/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForCity = ()=>{
	$("#btnCityAdd").off().on("click",function(){
		clickAddForCity();
	});

	$("#btnSave").off().on("click",function(){
		saveForCity();
	});

	$("#btnUpdate").off().on("click",function(){
		updateForCity();
	});

	$("#btnDelete").off().on("click",function(){
		deleteForCity();
	});

	$("#btnCancel").off().on("click",function(){
		clearDataForCity();
	});

};

/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{
	loadReferenceDataForCity();
	evenBinderForCity();
	loadCityTable();
});