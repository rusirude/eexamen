/*------------------------ Load data ---------------------------------*/
var loadEmails = () => {
    let successFunction = (data) => {
        if (data.code === Constant.CODE_SUCCESS) {
            for (let _o of data.data || []) {
                $("#" + _o.code+"_CODE").val(_o.code || "");
                $("#" + _o.code+"_S").val(_o.subject || "");
                $("#" + _o.code+"_C").val(_o.content || "");
                $("#" + _o.code+"_E").prop('checked',_o.enable || false);
            }
        } else {
            DialogBox.openMsgBox(data.message,'error');
        }
    };
    let failedFunction = (data) => {
        DialogBox.openMsgBox("Server Error",'error');
    };
    let url = "/email/loadAllEmail";
    let method = "POST";
    callToserver(url, method, null, successFunction, failedFunction);
};

var validatorForEmail = (code) => {
    let isValid = true;

    let SUBJECT = $("#"+code+"_S");

    if (!SUBJECT.val()) {
        InputsValidator.inlineEmptyValidation(SUBJECT);
        isValid = false;
    }
    return isValid;
};

var saveEmailData = (code) => {
    if(validatorForEmail(code)){
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
        let data = {
            code:$("#"+code+"_CODE").val(),
            subject:$("#"+code+"_S").val(),
            content:$("#"+code+"_C").val(),
            enable:$("#"+code+"_E").is(":checked")||false
        };

        let url = "/email/save";
        let method = "POST";
        callToserver(url, method, data, successFunction, failedFunction);
    }

};



/*-------------------------------- Document Ready ----------------------*/


$(document).ready(() => {
    loadEmails();
});