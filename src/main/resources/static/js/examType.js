/**
 * @author: rusiru
 */
var stepper;
var trowCount = 0;
var wrowCount = 0;
var examTypeTable;

var rowCreator = (type) => {

    let rowCount = type === 't'?++trowCount:++wrowCount;
    return `<div class="row body">
						<div class="form-group col-sm-3">
                            <input type="hidden" name="${type}_ex_tp_id_${rowCount}"/>
                            <select class="form-control form-control-sm" id="${type}_group_${rowCount}">

                            </select>
                            <span id="${type}_group_${rowCount}-error" class="error invalid-feedback"></span>
                        </div>
                        <div class="form-group col-sm-3">
                            <select class="form-control form-control-sm" id="${type}_label_${rowCount}">

                            </select>
                            <span id="${type}_label_${rowCount}-error" class="error invalid-feedback"></span>
                        </div>
                        <div class="form-group col-sm-4">
                            <input type="number" name="${type}_count_${rowCount}" class="form-control form-control-sm" id="${type}_count_${rowCount}" aria-describedby="${type}_count_${rowCount}-error" aria-invalid="false"/>
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

function addRow(ele,type) {
    let subLink = $(ele).parent().parent();
    subLink.after(rowCreator(type));
}

function removeRow(ele,type) {
    $("#"+type+"Section")
        .find('.row.body:first')
        .find('button:has(i.fa-minus)').remove();
    $(ele).parent().parent().remove();
}
/*-------------------------------- Inline Event  ----------------------*/
var clickAddForExamType = () => {
    //clearDataForExamType();
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
        $("#type").prop("disabled", true);
        $("#group").prop("disabled", true);
        $("#label").prop("disabled", true);
        $('#examTypeCategory').select2("enable",true);
        $("#formHeading").html("Delete ExamType");
        $("#answerSection")
            .find('.row.body')
            .find("input").prop("disabled", true);
        $("#examTypeTableDiv").hide();
        $("#examTypeFormDiv").show();
    };
    clearDataForExamType();
    findDetailByCodeForExamType(code, _sF);
};

var cancelForm = () => {
    //clearDataForExamType();
    $("#examTypeFormDiv").hide();
    $("#examTypeTableDiv").show();
};
var clickNextOne = () => {
    if (true) {
        stepper.next();
    }
    //if (validatorForExamTypeStepOne()) {
    //    stepper.next();
    //}
};
var clickNextTwo = () => {
    if (true) {
        stepper.next();
    }
    //if (validatorForExamTypeStepTwo()) {
    //    stepper.next();
    //}
};

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForExamType = () => {
    $("#btnExamTypeAdd").off().on("click", function () {
        clickAddForExamType();
    });

    $("#btnSave").off().on("click", function () {
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
        //$('#examTypeCategory').select2();
        //$('#examTypeCategory').on('select2:select', function (e) {
        //    let _element = $(this);
        //    _element.removeClass('is-invalid');
        //    _element.parent().find("span.error").html("");
        //});
    };
    _callback_1();
    //loadReferenceDataForExamType(_callback_1);
    //loadExamTypeTable();
    evenBinderForExamType();
});