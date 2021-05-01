/*------------------------------------------- CRUD Functions ------------------*/
var newLogo = "";
var loadReferenceDataForMasterData = (callback) => {
    $.ajax({
        type: "POST",
        url: "/masterData/loadRefDataForMasterData",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {

            if (data.code === Constant.CODE_SUCCESS) {
                for (let c of data.data.sysRole) {
                    $("#STUDENT_ROLE").append(`<option value="${c.code}">${c.description}</option>`);
                }

                if (callback) {
                    callback();
                }
            } else {
				DialogBox.openMsgBox("System Failer Occur....! :-(",'error');
            }


        },
        failure: function (errMsg) {
			DialogBox.openMsgBox(errMsg,'error');
        }
    });
};


var findExsistingDataForMasterData = () => {
    let successFunction = (data) => {
        if (data.code === Constant.CODE_SUCCESS) {
            for (let _o of data.data || []) {
                $("#" + _o.code).val(_o.value || "");
            }
            let logo  = $("#COMPANY_LOGO").val() || "/images/uploadLogo.png";
            document.getElementById("logo").src = logo;
        } else {
			DialogBox.openMsgBox(data.message,'error');
        }
    };
    let failedFunction = (data) => {
		DialogBox.openMsgBox("Server Error",'error');
    };
    let url = "/masterData/loadMasterData";
    let method = "POST";
    callToserver(url, method, null, successFunction, failedFunction);

};

var convertImageToBase64 = () => {
    let _f = $("#COMPANY_LOGO_FILE")[0];
    if (_f.files && _f.files[0]) {

        let FR = new FileReader();

        FR.addEventListener("load", function (e) {
            document.getElementById("logo").src = e.target.result;
            newLogo = e.target.result;
        });

        FR.readAsDataURL(_f.files[0]);
    }
    else{
        newLogo = '';
        document.getElementById("logo").src = "/images/uploadLogo.png";
    }
};

var saveDataForMasterData = () => {
    if (validatorForMasterData()) {
        let successFunction = (data) => {
            if (data.code === Constant.CODE_SUCCESS) {
                DialogBox.openMsgBox(data.message, 'success');
            } else {
                DialogBox.openMsgBox(data.message, 'error');
            }
        };
        let failedFunction = (data) => {
            DialogBox.openMsgBox("Server Error", 'error');
        };
        let data = [
            {code: "DEFAULT_PASSWORD", value: $("#DEFAULT_PASSWORD").val() || ""},
            {code: "STUDENT_ROLE", value: $("#STUDENT_ROLE").val() || ""},
            {code: "COMPANY_NAME", value: $("#COMPANY_NAME").val() || ""},
            {code: "COMPANY_LOGO", value: newLogo || null}
        ];

        let url = "/masterData/save";
        let method = "POST";
        callToserver(url, method, data, successFunction, failedFunction);
    }
};

var validatorForMasterData = () => {
    let isValid = true;

    let DEFAULT_PASSWORD = $("#DEFAULT_PASSWORD");
    let STUDENT_ROLE = $("#STUDENT_ROLE");
    let COMPANY_NAME = $("#COMPANY_NAME");
    if (!DEFAULT_PASSWORD.val()) {
        InputsValidator.inlineEmptyValidation(DEFAULT_PASSWORD);
        isValid = false;
    }
    if (!COMPANY_NAME.val()) {
        InputsValidator.inlineEmptyValidation(COMPANY_NAME);
        isValid = false;
    }
    if (!STUDENT_ROLE.val()) {
        InputsValidator.inlineEmptyValidationSelect(STUDENT_ROLE);
        isValid = false;
    }
    return isValid;
};

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForMasterData = () => {
    $("#btnSave").off().on("click", function () {
        saveDataForMasterData();
    });

    $("#COMPANY_LOGO_FILE").change(function () {
        convertImageToBase64();
    });
};

/*-------------------------------- Document Ready ----------------------*/


$(document).ready(() => {
    loadReferenceDataForMasterData(findExsistingDataForMasterData);
    evenBinderForMasterData();
});