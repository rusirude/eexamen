/*------------------------------------------- Functions ------------------*/

var clearDataForForgotPassword = ()=>{
    let username = $("#username");
    username.val("");
    InputsValidator.removeInlineValidation(username);
};

var generateFinalObjectForForgotPassword = ()=>{
    return {
        username:$("#username").val()||""
    }
};

var successFunctionForForgotPassword = (data)=>{
    if(data.code === Constant.CODE_SUCCESS){
        DialogBox.openMsgBox(data.message,'success');
    }
    else{
        DialogBox.openMsgBox(data.message,'error');
    }
    clearDataForForgotPassword();
};

var failedFunctionForForgotPassword = (data)=>{
    DialogBox.openMsgBox("Server Error",'error');
};


var validatorForForgotPassword = ()=>{


    let isValid = true;

    let username = $("#username");

    if(! username.val()){
        InputsValidator.inlineEmptyValidation(username);
        isValid = false;
    }
    return isValid;
};

var resetPassword = ()=>{
    if(validatorForForgotPassword()){
        let url = "/resetPassword";
        let method = "POST";

        callToserver(url,method,generateFinalObjectForForgotPassword(),successFunctionForForgotPassword,failedFunctionForForgotPassword);
    }

};


/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForCountry = ()=>{
    $("#btnForgotPassword").off().on("click",function(){
        resetPassword();
    });


};

/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{

    evenBinderForCountry();


});