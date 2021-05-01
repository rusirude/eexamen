

$(document).ready(()=>{	
	componentHandler.upgradeDom();
});

var onClickResetPassword = ()=>{
	location.href = `${window.location.hostname}:${window.location.port}/forgotPassword`;
};