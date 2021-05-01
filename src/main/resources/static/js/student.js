var studentTable;
var isAdd = true;
/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForStudent = ()=>{
	return {
		email:$("#email").val()||"",
		password:$("#password").val()||"",
		titleCode:$("#title").val()||"",
		name:$("#name").val()||"",
		address: $("#address").val()||"",
		telephone:$("#telephone").val()||"",
		company:$("#company").val()||"",
		statusCode:$("#status").val()||"",
		examCode:$("#examination").val()||"",
		zipCode:$("#zipCode").val()||"",
		cityCode:$("#city").val()||"",
		vat:$("#vat").val()||""
	}
};

var successFunctionForStudent = (data)=>{
	if(data.code === Constant.CODE_SUCCESS){
		DialogBox.openMsgBox(data.message,'success');
		studentTable.ajax.reload();
		clearDataForStudent();
		Loader.hide();
	}
	else{
		DialogBox.openMsgBox(data.message,'error');
		Loader.hide();
	}
};

var failedFunctionForStudent = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
	Loader.hide();
};

var validatorForStudent = ()=>{
	let isValid = true;

	let email = $("#email");
	let title = $("#title");
	let name = $("#name");
	let password = $("#password");
	let address = $("#address");
	let telephone = $("#telephone");
	let status = $("#status");
	let zipCode = $("#zipCode");
	let city = $("#city");
	let vat = $("#vat");

	if(! email.val()){
		InputsValidator.inlineEmptyValidation(email);
		isValid = false;
	}
	if(isAdd){
		if(! password.val()){
			InputsValidator.inlineEmptyValidation(password);
			isValid = false;
		}
	}
	if(! name.val()){
		InputsValidator.inlineEmptyValidation(name);
		isValid = false;
	}
	if(! address.val()){
		InputsValidator.inlineEmptyValidation(address);
		isValid = false;
	}
	if(! telephone.val()){
		InputsValidator.inlineEmptyValidation(telephone);
		isValid = false;
	}
	if(! title.val()){
		InputsValidator.inlineEmptyValidationSelect(title);
		isValid = false;
	}
	if(! zipCode.val()){
		InputsValidator.inlineEmptyValidation(zipCode);
		isValid = false;
	}
	if(! vat.val()){
		InputsValidator.inlineEmptyValidation(vat);
		isValid = false;
	}
	if(! status.val()){
		InputsValidator.inlineEmptyValidationSelect(status);
		isValid = false;
	}
	if(! city.val()){
		InputsValidator.inlineEmptyValidationSelect(city);
		isValid = false;
	}
	return isValid;
};


var saveForStudent = ()=>{
	if(validatorForStudent()){
		let url = "/student/save";
		let method = "POST";		
		Loader.show();
		callToserver(url,method,generateFinalObjectForStudent(),successFunctionForStudent,failedFunctionForStudent);
	}
	
};

var updateForStudent = ()=>{
	if(validatorForStudent()){
		let url = "/student/update";
		let method = "POST";
				
		callToserver(url,method,generateFinalObjectForStudent(),successFunctionForStudent,failedFunctionForStudent);
	}
};

var deleteForStudent = ()=>{
	if(validatorForStudent()){
		let url = "/student/delete";
		let method = "POST";
		
		callToserver(url,method,generateFinalObjectForStudent(),successFunctionForStudent,failedFunctionForStudent);
	}
};

var findDetailByCodeForStudent = (code,callback)=>{
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
	let url = "/student/loadStudentByUsername";
	let method = "POST";
	callToserver(url,method,{email:code},successFunction,failedFunction);
	
};

/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var populateFormForStudent = (data) => {
	if(data){

		$("#email").val(data.email || "");
		$("#title").val(data.titleCode || "");
		$("#name").val(data.name || "");
		$("#address").val(data.address || "");
		$("#telephone").val(data.telephone || "");
		$("#company").val(data.company || "");
		$("#status").val(data.statusCode || "");
		$("#zipCode").val(data.zipCode || "");
		$("#city").val(data.cityCode || "");
		$("#vat").val(data.vat || "");
	}
};

var loadReferenceDataForStudent = (callback)=>{
	$.ajax({
        type: "POST",
        url: "/student/loadRefDataForStudent",        
        contentType: "application/json",
        dataType: "json",
        success: function(data){    
        	
        	if(data.code === Constant.CODE_SUCCESS){
            	for(let s of data.data.status||[]){            		
            		$("#status").append(`<option value="${s.code}">${s.description}</option>`);
            	}
            	for(let sc of data.data.title||[]){
            		$("#title").append(`<option value="${sc.code}">${sc.description}</option>`);
            	}
				for(let c of data.data.city||[]){
					$("#city").append(`<option value="${c.code}">${c.description}</option>`);
				}
				// $("#examination").append(`<option value=""></option>`);
				for(let sc of data.data.examination||[]){
					$("#examination").append(`<option value="${sc.code}">${sc.description}</option>`);
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


var loadStudentTable = ()=>{
	studentTable = $('#studentTable').DataTable( {
                        ajax: {
                            url : "/student/loadStudents",
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
                            { data: "titleDescription"    ,name:"title"         },
                            { data: "name"                ,name:"name"          },
                            { data: "email"               ,name:"email"         },
                            { data: "statusDescription"   ,name:"status"        },
                            { data: "createdBy"           ,name:"createdBy"     },
                            { data: "createdOn"           ,name:"createdOn"     },
                            { data: "updatedBy"           ,name:"updatedBy"     },
                            { data: "updatedOn"           ,name:"updatedOn"     },
                            {
                            	data: "email",
                            	render: function (data, type, full) {
											return `<button onClick="updateIconClickForStudent('${data}')" type="button" class="btn btn-outline-primary btn-sm" data-toggle="tooltip" data-placement="bottom" title="Update">
														<i class="fa fa-pencil-alt"></i>
													</button>
													<button onClick="deleteIconClickForStudent('${data}')" type="button" class="btn btn-outline-danger btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
														<i class="fa fa-trash-alt"></i>
													</button>`;
		                            	}
                    		}
                        ]
                    } );
};

var clearDataForStudent = ()=>{

	let email = $("#email");
	let title = $("#title");
	let name = $("#name");
	let password = $("#password");
	let address = $("#address");
	let telephone = $("#telephone");
	let status = $("#status");
	let examination = $("#examination");
	let zipCode = $("#zipCode");
	let city = $("#city");
	let vat = $("#vat");

	isAdd = true;
	$("#btnSave").show();
	$("#btnUpdate").hide();
	$("#btnDelete").hide();

	$(".hideSection").show();

	$("#formHeading").html("");

	email.prop("disabled",false);
	title.prop("disabled",false);
	name.prop("disabled",false);
	password.prop("disabled",false);
	address.prop("disabled",false);
	telephone.prop("disabled",false);
	status.prop("disabled",false);
	examination.prop("disabled",false);
	zipCode.prop("disabled",false);
	city.prop("disabled",false);
	vat.prop("disabled",false);


	InputsValidator.removeInlineValidation(email);
	InputsValidator.removeInlineValidation(title);
	InputsValidator.removeInlineValidation(name);
	InputsValidator.removeInlineValidation(password);
	InputsValidator.removeInlineValidation(address);
	InputsValidator.removeInlineValidation(telephone);
	InputsValidator.removeInlineValidation(status);
	InputsValidator.removeInlineValidation(examination);
	InputsValidator.removeInlineValidation(zipCode);
	InputsValidator.removeInlineValidation(city);
	InputsValidator.removeInlineValidation(vat);

	email.val("");
	title.val("");
	name.val("");
	password.val("");
	address.val("");
	telephone.val("");
	status.val("");
	examination.val("");
	zipCode.val("");
	city.val("");
	vat.val("");

	FormTransition.closeModal('#studentModal');
	
};

var generatePassword = ()=>{
	$("#password").val( Math.random().toString(36).slice(-8));
};


/*-------------------------------- Inline Event  ----------------------*/
var clickAddForStudent = ()=>{
	clearDataForStudent();
	$("#formHeading").html("Add Student");
	FormTransition.openModal('#studentModal');
};

var updateIconClickForStudent = (code)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").show();
		$("#btnDelete").hide();
		populateFormForStudent(data);
		$("#email").prop("disabled",true);
		$("#formHeading").html("Update Student");

		isAdd = false;
		$(".hideSection").hide();

		FormTransition.openModal('#studentModal');
	};
	clearDataForStudent();
	findDetailByCodeForStudent(code,_sF);
};

var deleteIconClickForStudent = (code)=>{
	let _sF = (data)=>{
		$("#btnSave").hide();
		$("#btnUpdate").hide();
		$("#btnDelete").show();
		populateFormForStudent(data);
		$("#email").prop("disabled",true);
		$("#title").prop("disabled",true);
		$("#name").prop("disabled",true);
		$("#address").prop("disabled",true);
		$("#telephone").prop("disabled",true);
		$("#company").prop("disabled",true);
		$("#status").prop("disabled",true);
		$("#examination").prop("disabled",true);
		$("#zipCode").prop("disabled",true);
		$("#city").prop("disabled",true);
		$("#vat").prop("disabled",true);
		$("#formHeading").html("Delete Student");

		isAdd = false;
		$(".hideSection").hide();

		FormTransition.openModal('#studentModal');
	};
	clearDataForStudent();
	findDetailByCodeForStudent(code,_sF);
};

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForStudent = ()=>{
	$("#btnStudentAdd").off().on("click",function(){
		clickAddForStudent();
	});

	$("#btnSave").off().on("click",function(){
		saveForStudent();
	});
	
	$("#btnUpdate").off().on("click",function(){
		updateForStudent();
	});
	
	$("#btnDelete").off().on("click",function(){
		deleteForStudent();
	});
	
	$("#btnCancel").off().on("click",function(){
		clearDataForStudent();
	});
	$("#btnGeneratePassword").off().on("click",function(){
		generatePassword();
	});



};


/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{
	loadReferenceDataForStudent();
	loadStudentTable();
	evenBinderForStudent();

});