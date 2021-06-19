/**
 * @author: rusiru
 */
var stepper;
var tRowCount = 0;
var wRowCount = 0;
var examTypeTable;

var rowCreator = (type) => {

    let rowCount = type === 't'?++tRowCount:++wRowCount;
    return `<div class="row body">
						<div class="form-group col-sm-3">
                            <input type="hidden" name="${type}_ex_tp_id_${rowCount}"/>
                            <select class="form-control form-control-sm" id="${type}_group_${rowCount}">
                                        <option value=""></option>
                                        <option value="1">Group 1</option>
                                        <option value="2">Group 2</option>
                                        <option value="3">Group 3</option>
                                        <option value="4">Group 4</option>
                                        <option value="5">Group 5</option>
                                        <option value="6">Group 6</option>
                                        <option value="7">Group 7</option>
                                        <option value="8">Group 8</option>
                                        <option value="9">Group 9</option>
                                        <option value="10">Group 10</option>
                            </select>
                            <span id="${type}_group_${rowCount}-error" class="error invalid-feedback"></span>
                        </div>
                        <div class="form-group col-sm-3">
                            <select class="form-control form-control-sm" id="${type}_label_${rowCount}">
                                    <option value=""></option>
                                    <option value="A">A</option>
                                    <option value="B">B</option>
                            </select>
                            <span id="${type}_label_${rowCount}-error" class="error invalid-feedback"></span>
                        </div>
                        <div class="form-group col-sm-4">
                            <input onkeyup="calculateTotalExamType('${type}')" type="number" name="${type}_count_${rowCount}" class="form-control form-control-sm" id="${type}_count_${rowCount}" aria-describedby="${type}_count_${rowCount}-error" aria-invalid="false"/>
                            <span id="t_count_${rowCount}-error" class="error invalid-feedback"></span>
                        </div>
						<div class="form-group col-sm-2">
							<button onclick="addRow(this,'${type}')" type="button" class="btn  bg-gradient-primary btn-xs">
								<i class="fa fa-plus"></i>
							</button>
							<button onclick="removeRow(this,'${type}')" type="button" class="btn  bg-gradient-primary btn-xs">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>`;
};

function calculateTotalExamType(type){
    let total = 0;
    for (let ele of $("#"+type+"Section").find('.row.body').find('input[type=number]')) {

        total += parseInt($(ele).val() || 0);

    }
    $("#"+type+"_tcount").val(total);

}

function addRow(ele,type) {
    let subLink = $(ele).parent().parent();
    subLink.after(rowCreator(type));
}

function removeRow(ele,type) {
    $("#"+type+"Section")
        .find('.row.body:first')
        .find('button:has(i.fa-minus)').remove();
    $(ele).parent().parent().remove();
    calculateTotalExamType(type)
}

/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForExamType = () => {
    return {
        code: $("#code").val() || "",
        statusCode: $("#status").val() || "",
        description: $("#description").val() || "",
        examCategoryCode: $("#examCategory").val() || "",
        questionCategoryCode: $("#questionCategory").val() || "",
        qtPassMark: $("#t_passmark").val() || 0,
        qwPassMark: $("#w_passmark").val() || 0,
        qtQuestions: generateQuestionModelObjects('t'),
        qwQuestions: generateQuestionModelObjects('w')
    }
};

var generateQuestionModelObjects = (type) => {
    let _r = [];
    for (let ele of $("#"+type+"Section").find('.row.body')) {
        let o = {};
        o.id = $(ele).find('input[type=hidden]').val() || 0;
        o.typeCode = type === 't'?'THEORIM':'WETGEVING';
        o.group = $(ele).find(`select[id ^= "${type}_group_"]`).val() || '';
        o.label = $(ele).find(`select[id ^= "${type}_label_"]`).val() || '';
        o.noQuestion = $(ele).find('input[type=number]').val() || 0;
        _r.push(o);
    }
    return _r;
};

var successFunctionForExamType = (data) => {
    if (data.code === Constant.CODE_SUCCESS) {
        DialogBox.openMsgBox(data.message, 'success');
        $("#examTypeFormDiv").hide();
        $("#examTypeTableDiv").show();
        examTypeTable.ajax.reload();
        clearDataForExamType();
    } else {
        DialogBox.openMsgBox(data.message, 'error');
    }
};

var failedFunctionForExamType = (data) => {
    DialogBox.openMsgBox("Server Error", 'error');
};


var validatorForExamTypeStepOne = () => {
    let isValid = true;

    let code = $("#code");
    let description = $("#description");
    let status = $("#status");
    let questionCategory = $("#questionCategory");
    let examCategory = $("#examCategory");

    if (!code.val()) {
        InputsValidator.inlineEmptyValidation(code);
        isValid = false;
    }
    if (!description.val()) {
        InputsValidator.inlineEmptyValidation(description);
        isValid = false;
    }
    if (!status.val()) {
        InputsValidator.inlineEmptyValidationSelect(status);
        isValid = false;
    }
    if (!status.val()) {
        InputsValidator.inlineEmptyValidationSelect(status);
        isValid = false;
    }
    if (!questionCategory.val()) {
        InputsValidator.inlineEmptyValidationSelect(questionCategory);
        isValid = false;
    }
    if (!examCategory.val()) {
        InputsValidator.inlineEmptyValidationSelect(examCategory);
        isValid = false;
    }
    return isValid;
};

var validatorForExamTypeStepOne = () => {
    let isValid = true;

    let code = $("#code");
    let description = $("#description");
    let status = $("#status");
    let questionCategory = $("#questionCategory");
    let examCategory = $("#examCategory");

    if (!code.val()) {
        InputsValidator.inlineEmptyValidation(code);
        isValid = false;
    }
    if (!description.val()) {
        InputsValidator.inlineEmptyValidation(description);
        isValid = false;
    }
    if (!status.val()) {
        InputsValidator.inlineEmptyValidationSelect(status);
        isValid = false;
    }
    if (!status.val()) {
        InputsValidator.inlineEmptyValidationSelect(status);
        isValid = false;
    }
    if (!questionCategory.val()) {
        InputsValidator.inlineEmptyValidationSelect(questionCategory);
        isValid = false;
    }
    if (!examCategory.val()) {
        InputsValidator.inlineEmptyValidationSelect(examCategory);
        isValid = false;
    }
    return isValid;
};


var validatorForExamQuestionModel = (type) => {
    let isValid = true;

    let passMark = $("#"+type+"_passmark");

    if (!parseInt(passMark.val()||0)) {
        InputsValidator.inlineEmptyValidationNumber(passMark);
        isValid = false;
    }
    for (let ele of $("#"+type+"Section").find('.row.body')) {

        if (!$(ele).find(`select[id ^= "${type}_group_"]`).val()) {
            InputsValidator.inlineEmptyValidationSelect($(ele).find(`select[id ^= "${type}_group_"]`));
            isValid = false;
        }

        if(! parseInt($(ele).find('input[type=number]').val()||0)){
            InputsValidator.inlineEmptyValidationNumber($(ele).find('input[type=number]'));
            isValid = false;
        }
    }

    return isValid;
};


var saveForExamType = () => {
    if (validatorForExamQuestionModel('w')) {
        let url = "/examType/save";
        let method = "POST";

        callToserver(url, method, generateFinalObjectForExamType(), successFunctionForExamType, failedFunctionForExamType);
    }

};

var updateForExamType = () => {
    if (validatorForExamQuestionModel('w')) {
        let url = "/examType/update";
        let method = "POST";

        callToserver(url, method, generateFinalObjectForExamType(), successFunctionForExamType, failedFunctionForExamType);
    }
};

var deleteForExamType = () => {
    let url = "/examType/delete";
    let method = "POST";

    callToserver(url, method, generateFinalObjectForExamType(), successFunctionForExamType, failedFunctionForExamType);
};

var findDetailByCodeForExamType = (code, callback) => {
    let successFunction = (data) => {
        if (data.code === Constant.CODE_SUCCESS) {
            if (callback) {
                callback(data.data);
            }
        } else {
            DialogBox.openMsgBox(data.message, 'error');
        }
    };
    let failedFunction = (data) => {
        DialogBox.openMsgBox("Server Error", 'error');
    };
    let url = "/examType/loadExamTypeByCode";
    let method = "POST";
    callToserver(url, method, {code: code}, successFunction, failedFunction);
};

/*-------------------------------- Reference Data , Data Table and Common --------------------*/
var populateFormForExamType = (data) => {
    if (data) {
        $("#code").val(data.code || "");
        $("#description").val(data.description || "");
        $("#status").val(data.statusCode || "");
        $("#examCategory").val(data.examCategoryCode || "");
        $("#questionCategory").val(data.questionCategoryCode || "");
        $("#t_passmark").val(data.qtPassMark || 0);
        $("#w_passmark").val(data.qwPassMark || 0);
        $("#type").val(data.type || "");
        $("#group").val(data.group || "");
        $("#label").val(data.label || "");
        if ((data.qtQuestions || []).length) {
            tRowCount = 0;
            $("#tSection")
                .find('.row.body').remove();

            for (let e of data.qtQuestions) {
                let row = $(rowCreator('t'));
                row.find("input[type=hidden]").val(e.id);
                row.find(`select[id ^= "t_group_"]`).val(e.group);
                row.find(`select[id ^= "t_label_"]`).val(e.label);
                row.find('input[type=number]').val(e.noQuestion);
                $("#tSection").append(row);
            }
        }
        calculateTotalExamType('t');

        if ((data.qwQuestions || []).length) {
            wRowCount = 0;
            $("#wSection")
                .find('.row.body').remove();

            for (let e of data.qwQuestions) {
                let row = $(rowCreator('w'));
                row.find("input[type=hidden]").val(e.id);
                row.find(`select[id ^= "w_group_"]`).val(e.group);
                row.find(`select[id ^= "w_label_"]`).val(e.label);
                row.find('input[type=number]').val(e.noQuestion);
                $("#wSection").append(row);
            }
        }
        calculateTotalExamType('w');



    }
};

var loadReferenceDataForExamType = (callback) => {
    $.ajax({
        type: "POST",
        url: "/examType/loadRefDataForExamType",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {

            if (data.code === Constant.CODE_SUCCESS) {
                for (let s of data.data.status || []) {
                    $("#status").append(`<option value="${s.code}">${s.description}</option>`);
                }
                for (let sc of data.data.questionCategory || []) {
                    $("#questionCategory").append(`<option value="${sc.code}">${sc.description}</option>`);
                }
                for (let sc of data.data.examCategory || []) {
                    $("#examCategory").append(`<option value="${sc.code}">${sc.description}</option>`);
                }

                if (callback) {
                    callback();
                }
            } else {
                DialogBox.openMsgBox("System Failer Occur....! :-(", 'error');
            }


        },
        failure: function (errMsg) {
            DialogBox.openMsgBox(errMsg, 'error');
        }
    });
};

var loadExamTypeTable = () => {
    examTypeTable = $('#examTypeTable').DataTable({
        ajax: {
            url: "/examType/loadExamTypes",
            contentType: "application/json",
            type: "POST",
            data: function (d) {
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
        scrollX: true,
        columns: [
            {data: "code", name: "code"},
            {data: "description", name: "description"},
            {data: "statusDescription", name: "status"},
            {data: "createdBy", name: "createdBy"},
            {data: "createdOn", name: "createdOn"},
            {
                data: "code",
                render: function (data, type, full) {
                    return `<button onClick="updateIconClickForExamType('${data}')" type="button" class="btn btn-outline-primary btn-sm" data-toggle="tooltip" data-placement="bottom" title="Update">
														<i class="fa fa-pencil-alt"></i>
													</button>
													<button onClick="deleteIconClickForExamType('${data}')" type="button" class="btn btn-outline-danger btn-sm" data-toggle="tooltip" data-placement="bottom" title="Delete">
														<i class="fa fa-trash-alt"></i>
													</button>`;
                }
            }
        ]
    });
};


var clearDataForExamType = () => {
    let code = $("#code");
    let description = $("#description");
    let status = $("#status");
    let examCategory = $("#examCategory");
    let questionCategory = $("#questionCategory");
    let tPassmark = $("#t_passmark");
    let wPassmark = $("#w_passmark");

    $("#btnSave").show();
    $("#btnUpdate").hide();
    $("#btnDelete").hide();

    code.prop("disabled", false);
    description.prop("disabled", false);
    status.prop("disabled", false);
    examCategory.prop("disabled", false);
    questionCategory.prop("disabled", false);
    tPassmark.prop("disabled", false);
    wPassmark.prop("disabled", false);

    tRowCount = 0;
    $("#tSection")
        .find('.row.body').remove();


    $("#tSection").append(rowCreator('t'));

    $("#tSection")
        .find('.row.body:first')
        .find('button:has(i.fa-minus)').remove();

    calculateTotalExamType('t');


    wRowCount = 0;
    $("#wSection")
        .find('.row.body').remove();


    $("#wSection").append(rowCreator('w'));

    $("#wSection")
        .find('.row.body:first')
        .find('button:has(i.fa-minus)').remove();

    calculateTotalExamType('w');

    InputsValidator.removeInlineValidation(code);
    InputsValidator.removeInlineValidation(description);
    InputsValidator.removeInlineValidation(status);
    InputsValidator.removeInlineValidation(examCategory);
    InputsValidator.removeInlineValidation(questionCategory);
    InputsValidator.removeInlineValidation(tPassmark);
    InputsValidator.removeInlineValidation(wPassmark);

    code.val("");
    description.val("");
    status.val("");
    examCategory.val("");
    questionCategory.val("");
    tPassmark.val(0);
    wPassmark.val(0);
    stepper.previous();
    stepper.previous();


};

/*-------------------------------- Inline Event  ----------------------*/
var clickAddForExamType = () => {
    clearDataForExamType();
    $("#formHeading").html("Add ExamType");
    $("#examTypeTableDiv").hide();
    $("#examTypeFormDiv").show();
};

var updateIconClickForExamType = (code) => {
    let _sF = (data) => {
        $("#btnSave").hide();
        $("#btnUpdate").show();
        $("#btnDelete").hide();
        populateFormForExamType(data);
        $("#code").prop("disabled", true);
        $("#formHeading").html("Update ExamType");
        $("#examTypeTableDiv").hide();
        $("#examTypeFormDiv").show();
    };
    clearDataForExamType();
    findDetailByCodeForExamType(code, _sF);
};

var deleteIconClickForExamType = (code) => {
    let _sF = (data) => {
        $("#btnSave").hide();
        $("#btnUpdate").hide();
        $("#btnDelete").show();
        populateFormForExamType(data);
        $("#code").prop("disabled", true);
        $("#description").prop("disabled", true);
        $("#status").prop("disabled", true);
        $("#examCategory").prop("disabled", true);
        $("#questionCategory").prop("disabled", true);
        $("#t_passmark").prop("disabled", true);
        $("#w_passmark").prop("disabled", true);
        $("#formHeading").html("Delete ExamType");
        $("#tSection")
            .find('.row.body')
            .find("input,select").prop("disabled", true);
        $("#wSection")
            .find('.row.body')
            .find("input,select").prop("disabled", true);
        $("#examTypeTableDiv").hide();
        $("#examTypeFormDiv").show();
    };
    clearDataForExamType();
    findDetailByCodeForExamType(code, _sF);
};

var cancelForm = () => {
    clearDataForExamType();
    $("#examTypeFormDiv").hide();
    $("#examTypeTableDiv").show();
};
var clickNextOne = () => {

    if (validatorForExamTypeStepOne()) {
        stepper.next();
    }
};
var clickNextTwo = () => {
    if (validatorForExamQuestionModel('t')) {
        stepper.next();
    }
};

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForExamType = () => {
    $("#btnExamTypeAdd").off().on("click", function () {
        clickAddForExamType();
    });

    $("#btnSave").off().on("click", function () {
        console.log("dd");
        saveForExamType();
    });

    $("#btnUpdate").off().on("click", function () {
        updateForExamType();
    });

    $("#btnDelete").off().on("click", function () {
        deleteForExamType();
    });


};


/*-------------------------------- Document Ready ----------------------*/
$(document).ready(() => {
    let _callback_1 = () => {
        stepper = new Stepper($('.bs-stepper')[0]);
    };
    loadReferenceDataForExamType(_callback_1);
    loadExamTypeTable();
    evenBinderForExamType();
});