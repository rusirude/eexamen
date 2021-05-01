var total = 0;
var done =0;
var startTime;
var endTime;
var currentTime;
var aan = false;
var interval;



/*--------------- progress bars --------------------------*/
var setNoQuestion = ()=>{
    if(total){
        let percentage = parseFloat(((done/total)*100).toFixed(2));
        $("#noDone").css("width",`${percentage}%`);
        $("#noDone").html(percentage);
        console.log("ssss");
        $("#noDoneQnt").html(`${done}/${total}`);
    }
};

var setTimer = ()=>{

    let _cou = $("#countUp");
    let t = $("#timer");
    let s = moment(new Date(startTime));
    let e = moment(new Date(endTime));
    let c = moment(new Date(currentTime));
    let percentage = parseFloat(((c.diff(s)/e.diff(s))*100).toFixed(2));
    let clazz = (percentage >= 90)? 'bg-danger' :(percentage >= 75 ?'bg-warning':'bg-primary');
    t.removeClass('bg-primary','bg-warning','bg-danger');
    t.addClass(clazz);
    t.css("width",`${percentage}%`);
    t.html(percentage + ' %');

    let totalTime = e.diff(c)/1000;

    let _se = pad(totalTime % 60);

    totalTime = (totalTime- (totalTime % 60))/60; //total min

    let _min = pad(parseInt(totalTime % 60));

    totalTime = (totalTime- (totalTime % 60))/60;

    let _ho = pad(parseInt(totalTime));

    function pad(val) {
        let valString = val + "";
        if (valString.length < 2) {
            return "0" + valString;
        } else {
            return valString;
        }
    }

    _cou.html(`${_ho}:${_min}:${_se}`);

    if(percentage >= 100){
        clearInterval(interval);
        $("#stopAlert").modal({'backdrop':true},'show');

    }
};

var incrementTime = ()=>{
    let c = moment(new Date(currentTime));
    currentTime = c.add(1, 'seconds').toDate();;
    setTimer()

};


var createAnswer = (data)=>{
    aan = false;
    let _a = ``;
    let i = 0;
    for(let a of data.questionAnswers){
        aan = (!aan)?a.mark:aan;
        let _id = `ans_${data.code}_${++i}`;
        _a += `<div class="custom-control custom-radio">
                    <input class="custom-control-input" value="${a.id}" type="radio" id="${_id}" name="answer" ${a.mark?"checked":""}/>
                    <label for="${_id}" class="custom-control-label">${a.description}</label>
                </div>`
    }
    return _a;
};

var setQuestionView = (data,key)=>{
    let _q = `<div class="card">
                    <div class="card-body">
                        <h5 data-code="${data.code}">Vraag ${key} : ${data.description}</h5>
                    </div>
                </div>
                <div class="card">
                    <div class="card-body">
                        <div class="col-sm-12">
                            <!-- radio -->
                            <div class="form-group">
                                ${createAnswer(data)}
                            </div>
                        </div>
                    </div>
                </div>`;
    $("#questionSection").html(_q);
};

var finishExam = ()=>{
    $("#stopAlert").modal('hide');
    $("#submitAlert").modal('hide');
    $(".modal-backdrop.fade.show").remove();
    app.next('/studentExams/end/'+$("#studentExam").val());
};

var findQuestionForExam = (questionKey,initial,callback)=>{
    let successFunction = (data)=>{
        if(data.code === Constant.CODE_SUCCESS) {
            if(!data.data.closed){
                setQuestionView(data.data.question,questionKey);
                total = data.data.total||0;
                done = data.data.done||0;
                startTime = data.data.startTime;
                endTime = data.data.endTime;
                if(initial){
                    totalSeconds =
                    currentTime = data.data.currentTime;
                }
            }
            else{
                $("#stopAlert").modal({'backdrop':true},'show');
            }

            if(callback){
                callback();
            }

        }
        else{
            DialogBox.openMsgBox(data.message,'error');
        }
    };
    let failedFunction = (data)=>{
        DialogBox.openMsgBox("Server Error",'error');
    };
    let studentExam  = $("#studentExam").val();
    let url = `/studentExams/question/${studentExam}/${questionKey}`;
    let method = "POST";
    callToserver(url,method,null,successFunction,failedFunction);

};

/*--------------------------- Save Question --------------------------*/

var saveQuestionAnswer = (callback)=>{
    let successFunction = (data)=>{
        if(data.code === Constant.CODE_SUCCESS) {
            if(callback){
                callback();
            }
        }
        else{
            DialogBox.openMsgBox(data.message,'error');
        }
    };
    let failedFunction = (data)=>{
        DialogBox.openMsgBox("Server Error",'error');
    };
    let obj = {
        studentExamination: $("#studentExam").val(),
        seq: $("#current").val(),
        answer:$("input[name='answer']:checked").val()
    };
    let url = `/studentExams/saveAnswer`;
    let method = "POST";
    callToserver(url,method,obj,successFunction,failedFunction);

};

/*-------------------------------- Dynamic Event  ----------------------*/
var changeButton = (seq)=>{
    let previous = $("#previous");
    let savePrevious = $("#savePrevious");
    let saveNext = $("#saveNext");
    let next = $("#next");
    if(seq===1){
        saveNext.show();
        next.show();
        previous.hide();
        savePrevious.hide();

    }
    else if(seq === total){
        previous.show();
        savePrevious.show();
        saveNext.hide();
        next.hide();
    }
    else if(total === 1){
        saveNext.hide();
        next.hide();
        previous.hide();
        savePrevious.hide();
    }
    else{
        previous.show();
        savePrevious.show();
        saveNext.show();
        next.show();
    }
};

var clickNextBtnC = () =>{
    let  seq = parseInt($("#current").val()||'1');
    $("#current").val(++seq);
    findQuestionForExam(seq,false,setNoQuestion);
    changeButton(seq);
};
var clickNextBtn = () =>{
    let _f = ()=>{
        done = (!aan)?++done:done;
        let  seq = parseInt($("#current").val()||'1');
        $("#current").val(++seq);
        findQuestionForExam(seq,false,setNoQuestion);
        changeButton(seq);
    };
    if($("input[name='answer']:checked").val()){
        saveQuestionAnswer(_f);
    }
    else{
        //DialogBox.openMsgBox("Need to Select Answer",'error');
        clickNextBtnC();
    }


};

var clickPreviousBtnC = () =>{
    let  seq = parseInt($("#current").val()||'1');
    $("#current").val(--seq);
    findQuestionForExam(seq,false,setNoQuestion);
    changeButton(seq);
};

var clickPreviousBtn = () =>{
    let _f = ()=>{
        done = (!aan)?++done:done;
        let  seq = parseInt($("#current").val()||'1');
        $("#current").val(--seq);
        findQuestionForExam(seq,false,setNoQuestion);
        changeButton(seq);
    };
    if($("input[name='answer']:checked").val()){
        saveQuestionAnswer(_f);
    }
    else{
        //DialogBox.openMsgBox("Need to Select Answer",'error');
        clickPreviousBtnC();
    }
};

var finalSubmit = ()=>{
    if($("input[name='answer']:checked").val()){
        let _f = ()=>{
            done = (!aan)?++done:done;
            setNoQuestion();
            finalSubmitModal(()=>{$("#submitAlert").modal({'backdrop':true},'show')});
        };
        saveQuestionAnswer(_f);
    }
    else{
        finalSubmitModal(()=>{$("#submitAlert").modal({'backdrop':true},'show')});
    }
};

var finalSubmitModal = (callback) =>{
    let section = $("#remainingQes");
    section.html("");
    let successFunction = (data)=>{
        if(data.code === Constant.CODE_SUCCESS && data.data.length) {
            let ques = 'U heeft vragen: ';
            for(let _o of data.data){
                ques += `Vraag ${_o.seq},`
            }
            ques += ' nog niet ingevuld.';
            section.html(ques);
            callback();
        }
        else{
            callback();
        }
    };
    let failedFunction = (data)=>{
        DialogBox.openMsgBox("Server Error",'error');
    };

    let url = `/studentExams/remainingQuestions/${$("#studentExam").val()}`;
    let method = "GET";
    callToserver(url,method,null,successFunction,failedFunction);


};


/*-------------------------------- Document Ready ----------------------*/

$(document).ready(()=>{
    if(interval){
        clearInterval(interval);
        interval = null;
    }
    let seq =  parseInt($("#current").val()||'1')||1;
    let _f = ()=>{
        setTimer();
        setNoQuestion();
        changeButton(seq);
        interval = setInterval(()=>{
            incrementTime();
        },1000)
    };
    findQuestionForExam(seq,true,_f);


});