$(document).ready(function() {
	$("#fichero").change(function(e) {
		var ext = $("input#fichero").val().split(".").pop().toLowerCase();
		if($.inArray(ext, ["csv"]) == -1) {
			alert('Upload CSV');
			return false;
		}
		if (e.target.files != undefined) {
			csvvalue="";
			var reader = new FileReader();
			console.log("Aquí");
			reader.onload = function(e) {
//				var lines=e.target.result.split("\n");
//				console.log(lines);
//				var result = [];
//				var headers=lines[0].split(",");
//				headers[3].replace("\r", "");
//				console.log(headers);
//				for(var i=1;i<lines.length;i++){
//				 	  var obj = {};
//				 	  var currentline=lines[i].split(",");
//				 	  console.log(currentline);
//				      for(var j=0;j<headers.length;j++)
//				      {
//						  obj[headers[j]] = currentline[j].replace("\r", "");
//						  console.log(obj[headers[j]]);
//						  var request = $.ajax({
//								url: "/api/v2/co2",
//								type: POST,
//								cache: false,
//								data: JSON.stringify(obj[headers[j]]), contentType: "application/json"
//								});
//				      }
//				      result.push(obj);
				
				var csvval=e.target.result.split("\n");
				for (var j = 0; j < csvval.length; j++) {
					csvvalue= csvvalue + csvval[j].split(",");
				}
	            json = csvvalue;
				};
				reader.readAsText(e.target.files.item(0));
			
				var json =JSON.stringify(csvvalue);
				console.log(json);
				return false;
		}
	});
});