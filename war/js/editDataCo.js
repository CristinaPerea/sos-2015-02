// Función para "coger el parámetro por url" que se le consula mediante la cadena sParam
// Sirve para reutilizar código o saber "desde donde viene" el usuario.
// Ejemplos: ...html?country=Spain
//			 ...html?type='C' para el tipo de entidad a editar...
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

// Miramos el country que se "quiere editar" desde la url
var countryPetition = GetURLParameter("country");
// Como existen paises con "United States" (espacios) en la BD... tenemos 
// que quitar el %20
countryPetition = countryPetition.replace(/%20/g, " ");
// Cogemos el parámetro year
var yearPetition = GetURLParameter("year");

// Una vez en el formulario de edición, modificamos los "values" de los campos country
// y year por los que nos han entrado por parámetros.
// Estarán "blocked" en el formulario (mirarlo) 
$(document).ready(function () {
	$("input#country").attr('value', countryPetition);
	$("input#year").attr('value', yearPetition);
});


