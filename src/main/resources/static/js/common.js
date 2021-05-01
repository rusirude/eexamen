/**
 * Common Back End Ajax Call * 
 */
var callToserver = (url,method,data,successFunction,errorFunction)=>{
	$.ajax({
        type: method,
        url: url,        
        contentType: "application/json",
        dataType: "json",
        data:JSON.stringify(data),
        success: function(data) {
			if(successFunction){
				successFunction(data);
			}
		},
        failure: function(errMsg) {
        	if(errorFunction){
        		errorFunction(errMsg);
			} 
        }
  });
};



var createCommonDataTableRequset = (inputObj)=>{
    return {
            draw:inputObj.draw,
            start:inputObj.start,
            length:inputObj.length,
            sortColumnName:inputObj.columns[inputObj.order[0].column].name ||"",
            sortOrder:inputObj.order[0].dir || "desc",
            search:inputObj.search.value || ""
         }
	
};


var FormTransition = (function () {
	function openModal(formId){
		$(formId).modal({'backdrop':true},'show');
	}
	function closeModal(formId,tableId){
		$(formId).modal('hide');
	}
	return {
		openModal: function(formId,tableId){
			openModal(formId,tableId);
		},
		closeModal:function(formId,tableId){
			closeModal(formId,tableId);
		}

	}
})();


/* Dialog Box Functionality ********/

var DialogBox = (function () {
	let Toast = Swal.mixin({
		toast: true,
		position: 'top-end',
		showConfirmButton: false,
		timer: 3000
	});
	function openMessage(message,type){
		Toast.fire({
			icon: type,
			title: message
		});

	}
	return {
		openMsgBox: function(message,type){
			openMessage(message,type);
		}
	}
})();

var Loader = (function () {
	let loader = $(".loader");
	function show(){
		loader.show();

	}
	function hide(){
		loader.hide();

	}
	return {
		show: function(){
			show();
		},
		hide: function(){
			hide();
		}
	}
})();

/*  Inputs Validations    */
var InputsValidator = (function(){
	function inlineEmptyValidationSelect(element,msg){
		let _element = $(element);
		_element.parent().find("span.error").html(msg||"Can't be Empty");
		_element.addClass('is-invalid');
		_element.off("change").on("change",()=>{
			_element.removeClass('is-invalid');
		});

	}
	function inlineEmptyValidationSelect2(element,msg){
		let _element = $(element);
		_element.parent().find("span.error").html(msg||"Can't be Empty");
		_element.addClass('is-invalid');

	}
	function inlineEmptyValidation(element,msg){
		let _element = $(element);
		_element.parent().find("span.error").html(msg||"Can't be Empty");
		_element.addClass('is-invalid');
		_element.off("keypress").on("keypress",()=>{
			_element.removeClass('is-invalid');
		});
	}
	function inlineEmptyValidationNumber(element,msg){
		let _element = $(element);
		_element.parent().find("span.error").html(msg||"Invalid Input");
		_element.addClass('is-invalid');
		_element.off("keypress").on("keypress",()=>{
			_element.removeClass('is-invalid');
		});
	}
	function removeInlineValidation(element){
		let _element = $(element);
		_element.parent().find("span.error").html("");
		_element.removeClass('is-invalid');
		_element.off("keypress");
		_element.off("change");
	}
	function removeEmptyValidationSelect2(element){
		let _element = $(element);
		_element.removeClass('is-invalid');
		_element.parent().find("span.error").html("");

	}
	return{
		inlineEmptyValidationSelect: function(element,msg){
			inlineEmptyValidationSelect(element,msg)
		},
		inlineEmptyValidationSelect2: function(element,msg){
			inlineEmptyValidationSelect2(element,msg)
		},
		inlineEmptyValidation: function(element,msg){
			inlineEmptyValidation(element,msg);
		},
		inlineEmptyValidationNumber: function(element,msg){
			inlineEmptyValidationNumber(element,msg);
		},
		removeInlineValidation: function(element){
			removeInlineValidation(element);
		},
		removeEmptyValidationSelect2: function(element){
			removeEmptyValidationSelect2(element);
		}

	}
})();





