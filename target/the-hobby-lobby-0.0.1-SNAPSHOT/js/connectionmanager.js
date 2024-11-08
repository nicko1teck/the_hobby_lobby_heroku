

/*****************************************************************
	Deals with retrieving messages, creating message notifications, etc.
********************************************************************/


function ConnectionManager(websocketEndpoint) {
	console.log("Connection Manager called")
	
	var csrfTokenName = $("meta[name='_csrf_header']").attr("content");
	var csrfTokenValue = $("meta[name='_csrf']").attr("content");
	
	console.log("CSRF token name:  ", csrfTokenName)
	console.log("CSRF token value:  ", csrfTokenValue)
	
	this.websocketEndpoint = websocketEndpoint;
	this.client = null;
	this.headers = [];
	this.headers[csrfTokenName] = csrfTokenValue;
	
	this.subscriptions = [];
	
	$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
		  jqXHR.setRequestHeader(csrfTokenName, csrfTokenValue)
		});
}


ConnectionManager.prototype.connect = function() {
	console.log("Connected method called...")
	
	var wsocket = new SockJS(this.websocketEndpoint);
	this.client = Stomp.over(wsocket);
	var _self = this;
	
	this.client.connect(this.headers, function(){_self.connectSuccess()});
	
	
}


ConnectionManager.prototype.fetchMessages = function(conversationAjaxUrl, refreshMessages, page) {
	
	var request = JSON.stringify({
		'page':page
	})
	
	var jqXHR = $.ajax({
		url: conversationAjaxUrl,
		dataType:  'json',
		data: request,
		contentType: 'application/json',
		method:  'POST'
	});


	jqXHR.fail(function(jqXHR, textStatus) {
		console.log("could not retrieve messages: ", textStatus);
	});

	jqXHR.done(function(messages) {
		
		refreshMessages(messages);
	});

}


ConnectionManager.prototype.connectSuccess = function() {
	
	console.log("Established websocket connection");
	
	//alert(this.subscriptions.length);
	
	for (var i=0; i<this.subscriptions.length; i++){
		var subscription = this.subscriptions[i];
		
		var inboundDestination = subscription.inboundDestination;
		var newMessageCallback = subscription.newMessageCallback;
		
		//debug
		console.log(inboundDestination + ": " + newMessageCallback);
		
		this.client.subscribe(inboundDestination, newMessageCallback);
	}
}


ConnectionManager.prototype.addSubscription = function(inboundDestination, newMessageCallback){
	this.subscriptions.push(
	{
		"inboundDestination": inboundDestination,
		"newMessageCallback": newMessageCallback
	});
	console.log("Subscription Added...")
}


ConnectionManager.prototype.send =  function(outboundDestination, message){
	this.client.send(outboundDestination, this.headers, JSON.stringify(message));
}