/**
 * @author: rusiru
 */

var studentExamAddTable;


/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForStudentExaminationAdd = ()=>{
	return {
		student:$("#student").val()||"",
		examinationCode:$("#exam").val()||"",
	}
};

var successFunctionForStudentExaminationAdd = (data)=>{
	if(data.code === Constant.CODE_SUCCESS){
		DialogBox.openMsgBox(data.message,'success');
		studentExamAddTable.ajax.reload();
		clearDataForStudentExaminationAdd();
	}
	else{
		DialogBox.openMsgBox(data.message,'error');
	}
};

var failedFunctionForStudentExaminationAdd = (data)=>{
	DialogBox.openMsgBox("Server Error",'error');
};

var validatorForStudentExaminationAdd = ()=>{
	let isValid = true;

	let student = $("#student");
	let exam = $("#exam");

	if(! student.val()){
		InputsValidator.inlineEmptyValidationSelect(student);
		isValid = false;
	}
	if(! exam.val()){
		InputsValidator.inlineEmptyValidationSelect(exam);
		isValid = false;
	}
	return isValid;
};

var saveForStudentExaminationAdd = ()=>{
	if(validatorForStudentExaminationAdd()){
		let url = "/studentExamination/save";
		let method = "POST";

		callToserver(url,method,generateFinalObjectForStudentExaminationAdd(),successFunctionForStudentExaminationAdd,failedFunctionForStudentExaminationAdd);
	}

};


var deleteForStudentExaminationAdd = (Obj)=>{
	let url = "/studentExamination/delete";
	let method = "POST";

	callToserver(url,method,Obj,successFunctionForStudentExaminationAdd,failedFunctionForStudentExaminationAdd);

};
var tryToCloseForStudentExaminationAdd = (Obj)=>{
	let url = "/studentExamination/tryToClose";
	let method = "POST";

	callToserver(url,method,Obj,successFunctionForStudentExaminationAdd,failedFunctionForStudentExaminationAdd);

};


/*-------------------------------- Reference Data , Data Table and Common --------------------*/



var loadReferenceDataForStudentExaminationAdd = (callback)=>{
	$.ajax({
		type: "POST",
		url: "/studentExamination/loadRefDataForStudentExaminationAdd",
		contentType: "application/json",
		dataType: "json",
		success: function(data){

			if(data.code === Constant.CODE_SUCCESS){
				for(let s of data.data.student){
					$("#student").append(`<option value="${s.code}">${s.description}</option>`);
				}
				for(let s of data.data.exam){
					$("#exam").append(`<option value="${s.code}">${s.description}</option>`);
				}
				$("#student,#exam").val("");

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


var loadStudentExaminationAddTable = ()=>{
	studentExamAddTable = $('#studentExamAddTable').DataTable( {
		ajax: {
			url : "/studentExamination/exams",
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
			{ data: "studentName"                    ,name:"student"                },
			{ data: "examinationDescription"         ,name:"examination"         },
			{ data: "dateOn"                         ,name:"dateOn"              },
			{ data: "statusDescription"              ,name:"status"              },
			{
				data: null,
				render: function (data, type, full) {
					if(data.statusCode === 'PENDING'){
						return `<button onClick="deleteIconClickForStudentExaminationAdd(${data.id})" type="button" class="btn btn-outline-danger btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
														<i class="fa fa-trash-alt"></i>
													</button>`;
					}
					if(data.statusCode === 'START'){
						return `<button onClick="tryToCloseIconClickForStudentExaminationAdd(${data.id})" type="button" class="btn btn-outline-warning btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
														<i class="fas fa-ban"></i>
													</button>`;
					}
					if(data.statusCode === 'CLOSED'){
						return `<button onClick="reportIconClickForStudentExaminationAdd(${data.id})" type="button" class="btn btn-outline-primary btn-sm" data-toggle="tooltip" data-placement="bottom" title="Print">
										<i class="fas fa-print"></i>
								</button>
								<button onClick="mailIconClickForStudentExaminationAdd(${data.id})" type="button" class="btn btn-outline-success btn-sm" data-toggle="tooltip" data-placement="bottom" title="Send">
										<i class="fas fa-envelope"></i>
								</button>`;
					}
				}
			}
		]
	} );
}

var clearDataForStudentExaminationAdd = ()=>{
	let student = $("#student");
	let exam = $("#exam");

	InputsValidator.removeInlineValidation(student);
	InputsValidator.removeInlineValidation(exam);

	student.val("");
	exam.val("");


};


/*-------------------------------- Inline Event  ----------------------*/



var deleteIconClickForStudentExaminationAdd = (id)=>{
	let obj ={
		id:id
	};
	deleteForStudentExaminationAdd(obj);
};

var tryToCloseIconClickForStudentExaminationAdd = (id)=>{
	let obj ={
		id:id
	};
	tryToCloseForStudentExaminationAdd(obj);
};
var reportIconClickForStudentExaminationAdd = (id)=>{

	let url = '/studentExams/generateReport/'+id;
	window.open(url);
};

var printExcel = ()=>{
	let o = JSON.parse(studentExamAddTable.context[0].oAjaxData);
	let url = '/studentExams/generateExcelReport?sortColumnName='+o.sortColumnName+"&sortOrder="+o.sortOrder+"&search="+o.search;
	window.open(url);
};

var mailIconClickForStudentExaminationAdd = (id)=>{


	let successFunction = (data)=>{
		if(data.code === Constant.CODE_SUCCESS){
			DialogBox.openMsgBox(data.message,'success');
			Loader.hide();
		}
		else{
			DialogBox.openMsgBox(data.message,'error');
			Loader.hide();
		}
	};
	let failedFunction = (data)=>{
		DialogBox.openMsgBox("Server Error",'error');
		Loader.hide();
	};
	let url = '/studentExams/sendReport/'+id;
	let method = "POST";
	Loader.show();
	callToserver(url,method,null,successFunction,failedFunction);
};

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForStudentExaminationAdd = ()=>{


	$("#btnSave").off().on("click",function(){
		saveForStudentExaminationAdd();
	});

	$("#btnPrint").off().on("click",function(){
		printExcel();
	});



	$("#btnCancel").off().on("click",function(){
		clearDataForStudentExaminationAdd();
	});

};

/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{
	loadReferenceDataForStudentExaminationAdd();
	evenBinderForStudentExaminationAdd();
	loadStudentExaminationAddTable();
});