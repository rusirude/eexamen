function loadPageMenu(pageURL,_pid){
	app.next(pageURL);
	$("#mainHeaderName").html($(`#${_pid}`).html());
}