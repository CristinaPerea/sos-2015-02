// El mismo fichero que editDataCo.js pero para "importarlo" más claro en su 
// .html correspondiente. Debe modularizarse y refactorizarse todo, como se hace en
// scriptListBoxGeo.js + showMap.html (que reutilizan el código y se ejecutan en función
// del parametro typo = 'C' o 'I' en url
function GetURLParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}

var countryPetition = GetURLParameter("country");
countryPetition = countryPetition.replace(/%20/g, " ");
var yearPetition = GetURLParameter("year");

$(document).ready(function () {
	$("input#country").attr('value', countryPetition);
	$("input#year").attr('value', yearPetition);
});


