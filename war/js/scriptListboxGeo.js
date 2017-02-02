// Definición de todos los códigos y sus paises asociados y una función para recuperar el valor
var isoCountries = {
		'ABW' : 'Aruba',
		'AFG' : 'Afghanistan',
		'AGO' : 'Angola',
		'AIA' : 'Anguilla',
		'ALA' : 'Åland Islands',
		'ALB' : 'Albania',
		'AND' : 'Andorra',
		'ARE' : 'United Arab Emirates',
		'ARG' : 'Argentina',
		'ARM' : 'Armenia',
		'ASM' : 'American Samoa',
		'ATA' : 'Antarctica',
		'ATF' : 'French Southern Territories',
		'ATG' : 'Antigua and Barbuda',
		'AUS' : 'Australia',
		'AUT' : 'Austria',
		'AZE' : 'Azerbaijan',
		'BDI' : 'Burundi',
		'BEL' : 'Belgium',
		'BEN' : 'Benin',
		'BES' : 'Bonaire, Sint Eustatius and Saba',
		'BFA' : 'Burkina Faso',
		'BGD' : 'Bangladesh',
		'BGR' : 'Bulgaria',
		'BHR' : 'Bahrain',
		'BHS' : 'Bahamas',
		'BIH' : 'Bosnia and Herzegovina',
		'BLM' : 'Saint Barthélemy',
		'BLR' : 'Belarus',
		'BLZ' : 'Belize',
		'BMU' : 'Bermuda',
		'BOL' : 'Bolivia, Plurinational State of',
		'BRA' : 'Brazil',
		'BRB' : 'Barbados',
		'BRN' : 'Brunei Darussalam',
		'BTN' : 'Bhutan',
		'BVT' : 'Bouvet Island',
		'BWA' : 'Botswana',
		'CAF' : 'Central African Republic',
		'CAN' : 'Canada',
		'CCK' : 'Cocos (Keeling) Islands',
		'CHE' : 'Switzerland',
		'CHL' : 'Chile',
		'CHN' : 'China',
		'CIV' : "Côte d'Ivoire",
		'CMR' : 'Cameroon',
		'COD' : 'Congo, the Democratic Republic of the',
		'COG' : 'Congo',
		'COK' : 'Cook Islands',
		'COL' : 'Colombia',
		'COM' : 'Comoros',
		'CPV' : 'Cabo Verde',
		'CRI' : 'Costa Rica',
		'CUB' : 'Cuba',
		'CUW' : 'Curaçao',
		'CXR' : 'Christmas Island',
		'CYM' : 'Cayman Islands',
		'CYP' : 'Cyprus',
		'CZE' : 'Czech Republic',
		'DEU' : 'Germany',
		'DJI' : 'Djibouti',
		'DMA' : 'Dominica',
		'DNK' : 'Denmark',
		'DOM' : 'Dominican Republic',
		'DZA' : 'Algeria',
		'ECU' : 'Ecuador',
		'EGY' : 'Egypt',
		'ERI' : 'Eritrea',
		'ESH' : 'Western Sahara',
		'ESP' : 'Spain',
		'EST' : 'Estonia',
		'ETH' : 'Ethiopia',
		'FIN' : 'Finland',
		'FJI' : 'Fiji',
		'FLK' : 'Falkland Islands (Malvinas)',
		'FRA' : 'France',
		'FRO' : 'Faroe Islands',
		'FSM' : 'Micronesia, Federated States of',
		'GAB' : 'Gabon',
		'GBR' : 'United Kingdom',
		'GEO' : 'Georgia',
		'GGY' : 'Guernsey',
		'GHA' : 'Ghana',
		'GIB' : 'Gibraltar',
		'GIN' : 'Guinea',
		'GLP' : 'Guadeloupe',
		'GMB' : 'Gambia',
		'GNB' : 'Guinea-Bissau',
		'GNQ' : 'Equatorial Guinea',
		'GRC' : 'Greece',
		'GRD' : 'Grenada',
		'GRL' : 'Greenland',
		'GTM' : 'Guatemala',
		'GUF' : 'French Guiana',
		'GUM' : 'Guam',
		'GUY' : 'Guyana',
		'HKG' : 'Hong Kong',
		'HMD' : 'Heard Island and McDonald Islands',
		'HND' : 'Honduras',
		'HRV' : 'Croatia',
		'HTI' : 'Haiti',
		'HUN' : 'Hungary',
		'IDN' : 'Indonesia',
		'IMN' : 'Isle of Man',
		'IND' : 'India',
		'IOT' : 'British Indian Ocean Territory',
		'IRL' : 'Ireland',
		'IRN' : 'Iran, Islamic Republic of',
		'IRQ' : 'Iraq',
		'ISL' : 'Iceland',
		'ISR' : 'Israel',
		'ITA' : 'Italy',
		'JAM' : 'Jamaica',
		'JEY' : 'Jersey',
		'JOR' : 'Jordan',
		'JPN' : 'Japan',
		'KAZ' : 'Kazakhstan',
		'KEN' : 'Kenya',
		'KGZ' : 'Kyrgyzstan',
		'KHM' : 'Cambodia',
		'KIR' : 'Kiribati',
		'KNA' : 'Saint Kitts and Nevis',
		'KOR' : 'Korea, Republic of',
		'KWT' : 'Kuwait',
		'LAO' : "Lao People's Democratic Republic",
		'LBN' : 'Lebanon',
		'LBR' : 'Liberia',
		'LBY' : 'Libya',
		'LCA' : 'Saint Lucia',
		'LIE' : 'Liechtenstein',
		'LKA' : 'Sri Lanka',
		'LSO' : 'Lesotho',
		'LTU' : 'Lithuania',
		'LUX' : 'Luxembourg',
		'LVA' : 'Latvia',
		'MAC' : 'Macao',
		'MAF' : 'Saint Martin (French part)',
		'MAR' : 'Morocco',
		'MCO' : 'Monaco',
		'MDA' : 'Moldova, Republic of',
		'MDG' : 'Madagascar',
		'MDV' : 'Maldives',
		'MEX' : 'Mexico',
		'MHL' : 'Marshall Islands',
		'MKD' : 'Macedonia, the former Yugoslav Republic of',
		'MLI' : 'Mali',
		'MLT' : 'Malta',
		'MMR' : 'Myanmar',
		'MNE' : 'Montenegro',
		'MNG' : 'Mongolia',
		'MNP' : 'Northern Mariana Islands',
		'MOZ' : 'Mozambique',
		'MRT' : 'Mauritania',
		'MSR' : 'Montserrat',
		'MTQ' : 'Martinique',
		'MUS' : 'Mauritius',
		'MWI' : 'Malawi',
		'MYS' : 'Malaysia',
		'MYT' : 'Mayotte',
		'NAM' : 'Namibia',
		'NCL' : 'New Caledonia',
		'NER' : 'Niger',
		'NFK' : 'Norfolk Island',
		'NGA' : 'Nigeria',
		'NIC' : 'Nicaragua',
		'NIU' : 'Niue',
		'NLD' : 'Netherlands',
		'NOR' : 'Norway',
		'NPL' : 'Nepal',
		'NRU' : 'Nauru',
		'NZL' : 'New Zealand',
		'OMN' : 'Oman',
		'PAK' : 'Pakistan',
		'PAN' : 'Panama',
		'PCN' : 'Pitcairn',
		'PER' : 'Peru',
		'PHL' : 'Philippines',
		'PLW' : 'Palau',
		'PNG' : 'Papua New Guinea',
		'POL' : 'Poland',
		'PRI' : 'Puerto Rico',
		'PRK' : "Korea, Democratic People's Republic of",
		'PRT' : 'Portugal',
		'PRY' : 'Paraguay',
		'PSE' : 'Palestine, State of',
		'PYF' : 'French Polynesia',
		'QAT' : 'Qatar',
		'REU' : 'Réunion',
		'ROU' : 'Romania',
		'RUS' : 'Russian Federation',
		'RWA' : 'Rwanda',
		'SAU' : 'Saudi Arabia',
		'SDN' : 'Sudan',
		'SEN' : 'Senegal',
		'SGP' : 'Singapore',
		'SGS' : 'South Georgia and the South Sandwich Islands',
		'SHN' : 'Saint Helena, Ascension and Tristan da Cunha',
		'SJM' : 'Svalbard and Jan Mayen',
		'SLB' : 'Solomon Islands',
		'SLE' : 'Sierra Leone',
		'SLV' : 'El Salvador',
		'SMR' : 'San Marino',
		'SOM' : 'Somalia',
		'SPM' : 'Saint Pierre and Miquelon',
		'SRB' : 'Serbia',
		'SSD' : 'South Sudan',
		'STP' : 'Sao Tome and Principe',
		'SUR' : 'Suriname',
		'SVK' : 'Slovakia',
		'SVN' : 'Slovenia',
		'SWE' : 'Sweden',
		'SWZ' : 'Swaziland',
		'SXM' : 'Sint Maarten (Dutch part)',
		'SYC' : 'Seychelles',
		'SYR' : 'Syrian Arab Republic',
		'TCA' : 'Turks and Caicos Islands',
		'TCD' : 'Chad',
		'TGO' : 'Togo',
		'THA' : 'Thailand',
		'TJK' : 'Tajikistan',
		'TKL' : 'Tokelau',
		'TKM' : 'Turkmenistan',
		'TLS' : 'Timor-Leste',
		'TON' : 'Tonga',
		'TTO' : 'Trinidad and Tobago',
		'TUN' : 'Tunisia',
		'TUR' : 'Turkey',
		'TUV' : 'Tuvalu',
		'TWN' : 'Taiwan, Province of China',
		'TZA' : 'Tanzania, United Republic of',
		'UGA' : 'Uganda',
		'UKR' : 'Ukraine',
		'UMI' : 'United States Minor Outlying Islands',
		'URY' : 'Uruguay',
		'USA' : 'United States',
		'UZB' : 'Uzbekistan',
		'VAT' : 'Holy See (Vatican City State)',
		'VCT' : 'Saint Vincent and the Grenadines',
		'VEN' : 'Venezuela, Bolivarian Republic of',
		'VGB' : 'Virgin Islands, British',
		'VIR' : 'Virgin Islands, U.S.',
		'VNM' : 'Viet Nam',
		'VUT' : 'Vanuatu',
		'WLF' : 'Wallis and Futuna',
		'WSM' : 'Samoa',
		'YEM' : 'Yemen',
		'ZAF' : 'South Africa',
		'ZMB' : 'Zambia',
		'ZWE' : 'Zimbabwe'
};

// Dado un código, devuelve un name; si no lo encuentra
// devuelve el countryCode
function getCountryName (countryCode) {
    if (isoCountries.hasOwnProperty(countryCode)) {
    	var country = isoCountries[countryCode];
    	console.log("[scriptListboxGeo.js] Función getCountryName(), countryCode="+countryCode+", country="+country);
        return country;
    } else {
        return countryCode;
    }
}

// Función para obtener el parámetro que define la url (y el tipo de mapa
// inversion(I) o co2(C)
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


var tipo = GetURLParameter("type");
console.log("[scriptListboxGeo.js] Tipo para showMap en el parámetro de la url. type="+tipo);
// Variable que almacena el JSON de todos los datos de la base de datos
var variableGlobal;	
// Array que almacenará sólo los nombres de los countries para un año seleccionado que son
// mostrados en el mapa
var variableGlobalParaYear;
// Variable para almacenar todos los years "distintos" para el selectBox
var listYearsArray = [];

$(document).ready(function() {
		// Hago petición Ajax como en el client jQuery hecho por ti.
		var request;
		if(tipo == 'I') {
			console.log("[scriptListboxGeo.js] Petición a realizar: /api/v2/countriesinversions/ + type=GET");
			request = $.ajax({url: "/api/v2/countriesinversions/",cache: false, type: "GET"});
		}
		else {
			console.log("[scriptListboxGeo.js] Petición a realizar: /api/v2/co2/ + type=GET");
			request = $.ajax({url: "/api/v2/co2/",cache: false, type: "GET"});
		}
		
		// Los datos los meto en VariableGlobal (Array con las tuplas)
		request.done(function(data, status, jqXHR){
			console.log("[scriptListboxGeo.js] Petición hecha");
			
			if(data != ""){
				console.log("[scriptListboxGeo.js] Existen datos, se parsean a JSON y son:");
				variableGlobal = $.parseJSON(data);
				variableGlobalParaYear = new Array();
			}
			console.log(variableGlobal);
			
			// Para cada dato de variableGlobal (elemento completo del JSON en i)
			for(var i in variableGlobal){
				// Obtenemos su year
				var year = variableGlobal[i].year;
				// Comprobamos si no existe en el array ¡¡OJO!! la función $.inArray de jQuery devuelve
				// -1 si no está y el indice del elemento si está. Si no está, lo insertamos.
				if($.inArray(year, listYearsArray) == -1)
					listYearsArray.push(year); 
			}
			
			console.log("[scriptListboxGeo.js] Lista de years distintos para el listBox, listYearsArray=[" + listYearsArray+"]");
			// Ahora construimos los option value que insertaremos con jQuery dentro del listBox
			var options = '<option value=""></option>';
			var contador = 0;
			// Ordenamos el array decrecintemente (el sort de JavaScript ordena alfabéticamente, no compara números,
			// así que hay que meterle una función comparadora que es el a-b.
			listYearsArray.sort(function(a, b){return a-b});
			for(var i = 0; i<listYearsArray.length; i++){
				options += '<option value="'+ listYearsArray[i] + '">' + listYearsArray[i] + '</option>';
			}			
			
			// Agrego al id listbox las opciones de años posibles
			$("#listbox").append(options);
			console.log("[scriptListboxGeo.js] Lista de years distintos añadidos");
			
			// Cuando se selecciona otro año...
			$("select").change(function(){
				// Manejador para cuando se selecciona un año en el listBox
				$( "select option:selected", function() {		
					var yearSelected = $("select option:selected").val();
					$("#chartBars").hide();
					console.log("[scriptListboxGeo.js] Año seleccionado, yearSelected="+yearSelected);
					
					// Generación del datatable de google para el año seleccionado y de los 
					// paises asociados a ese yearSelected en variableGlobalParaYear
					var datosAMostrar = generaDataTable(yearSelected,null);
					console.log("[scriptListboxGeo.js] Nombre de paises en el mapa a mostrar: " + variableGlobalParaYear);
					google.setOnLoadCallback(drawRegionsMap());
					function drawRegionsMap() {
						 // Activamos la interacción con click en los countries del geochart
						 var legend;
						 if(tipo == 'I')
							 optionsMap =  {enableRegionInteractivity:true, title:'Investments over the World'};
						 else
							 optionsMap = {enableRegionInteractivity:true, title:'Co2 emissions over the World'};
						 // Linkeamos el chart al regions_div de la web
					     var chart = new google.visualization.GeoChart(document.getElementById('regions_div'));
					     // Pintamos los datos en con las opciones del mapa
					     chart.draw(datosAMostrar, optionsMap);
					     // Si el usuario clickea en un país
					     google.visualization.events.addListener(chart, 'select', function(){
					    	 var selection = chart.getSelection();
							 console.log("[scriptListboxGeo] Se ha pinchado en un pais y estamos ejecutando la función asociada al evento");
							 console.log("[scriptListboxGeo] Fila del chart pinchada en el mapa: ");
							 console.log(selection);
							 var message='';
							 var row = 0;
							 // Obtenemos la fila de datos en la que ha pinchado en nuestra variableGlobalParaYear[row]
							 for (var i = 0; i < selection.length; i++) {
								 	
								    var item = selection[i];
								   
								    if (item.row != null) {
								      row = item.row;
								      console.log("[scriptListboxGeo.js] Se ha seleccionado la fila: " + row);
								  
								    }
							 }
							 console.log("[scriptListboxGeo.js] Se ha seleccionado el país: " + variableGlobalParaYear[row] + " en variableGlobalParaYear");
							 var paisPinchado = variableGlobalParaYear[row];
							 // Sustitución de espacios para las urls...
							 paisPinchado = paisPinchado.replace(/%20/g, " ");
							 // Petición al proxy para que envíe la petición a la API externa
							 var request_ext = $.ajax({url: "/proxy/"+paisPinchado,cache: false, type: "GET"});
							 var listaVecinos = [];
							 request_ext.done(function(data, status, jqXHR){
									var dataJson = $.parseJSON(data);
									console.log(dataJson[0].borders);
									// Obtenemos del JSON de la externa (pasado desde nuestro server hacia nosotros)
									// los borders...
									var borders = dataJson[0].borders;
									var i;
									for(i=0;i < borders.length;i++) {
										//console.log(getCountryName(borders[i]));
										listaVecinos.push(getCountryName(borders[i]));
									}
									console.log("[scriptListboxGeo.js] Los vecinos son listaVecinos= "+listaVecinos);
									// Data2 contiene sólo las tuplas seleccionadas para un year y country PINCHADOS
									// Generamos el dataTable
									data2 = generaDataTable(yearSelected,listaVecinos);
									console.log("[scriptListboxGeo.js] Obtenidos vecinos y sus datos");
									console.log("[scriptListboxGeo.js] Generado DataTable para Donut");	
									$("#chartBars").show();
									// Asociamos el punto de vuelta (Callback)
									google.setOnLoadCallback(drawPieChart());
									// Pequeña animación
									$("html, body").animate({ scrollTop: 1000 }, 2000); 
									function drawPieChart(){
										 optionsPie = {
												title:'Border Countries Comparative for Selected Country',
												pieHole: 0.4,
												chartArea:{width:'50%',height:'80%'},
												is3D:true, 
												
										 };
										 console.log("[scriptListboxGeo.js] Pintando Donut");
										 var barsChart = new google.visualization.PieChart(document.getElementById('chartBars'));
										 // Dibujamos
										 barsChart.draw(data2, optionsPie);
										 console.log("[scriptListboxGeo.js] Donut pintado");
									 }
							 });
					    	 						
					     });
					     
					}
					
					// Función para la redimensión del chart (responsive design)
					function redimension () {
					     var chart = new google.visualization.GeoChart(document.getElementById('regions_div'));
					     chart.draw(datosAMostrar, options);			
					     var barsChart = new google.visualization.PieChart(document.getElementById('chartBars'));
						 barsChart.draw(data2, optionsPie);
					}					
					window.onresize = redimension;
				});
			});
		});
});
           
// Generamos el dataTable para el chart de mapa de google y 
// el array de nombres de paises en dicho chart para la api externa.
function generaDataTable(yearSelected, listaPaises) {
	var datos;
	// Contador necesario para rellenar el array de variableGlobalParaYear
	var contador=0;
	if(tipo == 'I')
		datos = new Array(['Country', 'Investment']);
	else
		datos = new Array(['Country', 'Co2 Quantity']);
	
	// Si no hay lista de vecinos, se nos pide el dataTable para el mapa
	if(listaPaises==null) {
		for(var i in variableGlobal){
			console.log("[scriptListboxGeo.js] Año de "+variableGlobal[i].country+ " es "+variableGlobal[i].year);
			console.log(variableGlobal[i].year);
			if(variableGlobal[i].year == yearSelected) {
				
				// Si el año de la tupla seleccionada coincide, lo añado al datatable y al array de countries del año
				var elem;
				// En este array almacenamos los countries del año seleccionado y no se 
				// transforman en datatable de Google (para simplificar el evento click
				// del pais seleccionado (Api externa)
				variableGlobalParaYear[contador] = variableGlobal[i].country;
				contador++;
				if (tipo == 'I') 
					elem = [variableGlobal[i].country, variableGlobal[i].inversion];
				else
					elem = [variableGlobal[i].country, variableGlobal[i].quantity];
				console.log("[scriptListboxGeo.js] " + variableGlobal[i].country + " añadido a datatable de google y variableGlobalParaYear." )
				datos.push(elem);
			}
		}
		
		datosDevueltos = google.visualization.arrayToDataTable(datos);
	}
	else {
		// Cuando la lista de Vecinos no es vacía, entonces están pidiendonos el dataTable del pieChart
		for(var i in variableGlobal){
			if($.inArray(variableGlobal[i].country, listaPaises) != -1){
				console.log("[scriptListboxGeo.js] Vecino: Año de "+variableGlobal[i].country+ " es "+variableGlobal[i].year);
				console.log(variableGlobal[i].year);
				if(variableGlobal[i].year == yearSelected) {
					
					// Si el año de la tupla seleccionada coincide, lo añado al datatable y al array de countries del año
					var elem;
					// En este array almacenamos los countries del año seleccionado y no se 
					// transforman en datatable de Google (para simplificar el evento click
					// del pais seleccionado (Api externa)
					variableGlobalParaYear[contador] = variableGlobal[i].country;
					contador++;
					if (tipo == 'I') 
						elem = [variableGlobal[i].country, variableGlobal[i].inversion];
					else
						elem = [variableGlobal[i].country, variableGlobal[i].quantity];
					console.log("[scriptListboxGeo.js] " + variableGlobal[i].country + " añadido a datatable de google y variableGlobalParaYear." )
					datos.push(elem);
				}
			}
		}
		
		datosDevueltos = google.visualization.arrayToDataTable(datos);
	}
	return datosDevueltos;
}