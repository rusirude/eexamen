/**
 * @author: rusiru
 */
var saveDataForProfile = ()=>{
    if(validatorForProfile()){
        let successFunction = (data)=>{
            if(data.code === Constant.CODE_SUCCESS){
                DialogBox.openMsgBox(data.message,'success');
            }
            else{
                DialogBox.openMsgBox(data.message,'error');
            }
        };
        let failedFunction = (data)=>{
            DialogBox.openMsgBox("Server Error",'error');
        };
        let data ={
            username:$("#username").val(),
            password:$("#currentPassword").val(),
            newPassword:$("#newPassword").val(),
        };
        let url = "/profile/save";
        let method = "POST";
        callToserver(url,method,data,successFunction,failedFunction);
    }
};

var validatorForProfile = ()=>{
    let isValid = true;


    let password = $("#currentPassword");
    let newPassword = $("#newPassword");

    if(! password.val()){
        InputsValidator.inlineEmptyValidation(password);
        isValid = false;
    }if(! newPassword.val()){
        InputsValidator.inlineEmptyValidation(newPassword);
        isValid = false;
    }
    return isValid;
};

/*-------------------------------- Dynamic Event  ----------------------*/

var evenBinderForProfile = ()=>{
    $("#btnSave").off().on("click",function(){
        saveDataForProfile();
    });
};

/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=>{
    evenBinderForProfile();
});