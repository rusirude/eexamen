/**
 * @author: rusiru
 */

var passwordResetRequestTable;

/*------------------------------------------- CRUD Functions ------------------*/

var generateFinalObjectForPasswordResetRequest = (id)=> {
    return {
        id: id
    }
};

var successFunctionForPasswordResetRequest = (data)=> {
    if (data.code === Constant.CODE_SUCCESS) {
        DialogBox.openMsgBox(data.message,'success');
        passwordResetRequestTable.ajax.reload();
        Loader.hide();
    }
    else {
        DialogBox.openMsgBox(data.message,'error');
        Loader.hide();
    }
};

var failedFunctionForPasswordResetRequest = (data)=> {
    DialogBox.openMsgBox(data.message,'error');
    Loader.hide();
};



var confirmForPasswordResetRequest = (id)=> {
    let url = "/passwordResetRequest/resetPassword";
    let method = "POST";
    Loader.show();
    callToserver(url, method, generateFinalObjectForPasswordResetRequest(id), successFunctionForPasswordResetRequest, failedFunctionForPasswordResetRequest);


};



/*-------------------------------- Reference Data , Data Table and Common --------------------*/

var loadPasswordResetRequestTable = ()=> {
    passwordResetRequestTable = $('#passwordResetRequestTable').DataTable({
        ajax: {
            url: "/passwordResetRequest/loadPasswordResetRequests",
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
        order:[[4,'desc']],
        columns: [
            {data: "username",            name: "username"},
            {data: "titleDescription",    name: "title"   },
            {data: "name",                name: "name"    },
            {data: "statusDescription",   name: "status"  },
            {data: "createdOn",           name: "createdOn"  },
            {
                data: null,
                render: function (data, type, full) {
                    return `<button onClick="confirmIconClickForPasswordResetRequest('${data.id}')" type="button" class="${(data.statusCode==='PRESET')?'btn btn-outline-danger btn-sm':'btn btn-outline-primary btn-sm'}" data-toggle="tooltip" data-placement="bottom" title="Reset Password" ${(data.statusCode==='PRESET')?'disabled':''}>
                                <i class="fa fa-thumbs-up"></i>
                            </button>`;
                }
            }
        ]
    });
};



/*-------------------------------- Inline Event  ----------------------*/

var confirmIconClickForPasswordResetRequest = (id)=> {
    confirmForPasswordResetRequest(id);
};



/*-------------------------------- Document Ready ----------------------*/


$(document).ready(()=> {
    loadPasswordResetRequestTable();


});