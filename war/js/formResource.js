function sendPetition() {
		console.log("Sending...");
		var urlApi = "/api/v2/countriesinversions";
		var method = "POST";
		var countryPetition = $("#country").val();
		var yearPetition = $("#year").val();
		var quantityPetition = $("#quantity").val();
		var payload = '{"country":"' + countryPetition + '","year":' + yearPetition + ',"inversion":'+quantityPetition+'}';
		console.log(payload);
		var request = $.ajax({
			url: urlApi,
			cache: false,
			type: method, 
			data: payload, contentType: "application/json"
			});
			
		request.done(function(data, status, jqXHR){
			if(data != ""){
				//data = '[{"country": "spain"}, {"country": "portugal"}]';
				$("#data").text(data);
				var parsedData = $.parseJSON(data);
				 $("#list").empty();
				
				 if(Array.isArray(parsedData)){
					 for(var i in parsedData){
						 
						 var dump = "";
						 var obj = parsedData[i];
						 for(var prop in obj){
							 dump += "[" + prop + " = " + obj[prop] + "]";
						 }
						 $("#list").append("<li>"+dump+"</li>");
					 }
				 }else{
					var dump = "";
					var obj = parsedData;
					for(var prop in obj){
						dump += "[" + prop + " = " + obj[prop] + "]";
					}
				 }
			}else{
				$("#list").empty();
				$("#data").empty();
			}
		});
		
		request.always(function(jqXHR, status){
			if(status == "error"){
				$("#status").text(jqXHR.status);
				$("#list").empty();
				$("#data").empty();
			}else{
				$("#status").text("200 OK");
				console.log("Done!");
			}
			$("#log").text("Done! ");
		});
		
		$("button").hover(function(){
		 $(this).addClass("active");
		 },function(){ 
			 $(this).removeClass("active");
			 });
		
	return true;
}