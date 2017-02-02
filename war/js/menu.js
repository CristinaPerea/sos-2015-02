// "bindeamos" la función main al header cuando se le "modifica" su dom interno, de forma
// que en cuanto se le "añada el submenu" se le asigna la función main que se encargará
// de la animación del menu en tamaño móvil
$("header").bind("DOMSubtreeModified", function() {
    main();
});
$(document).ready($("header").load("header.html"));
$(document).ready($("footer").load("footer.html"));
$("footer").attr("class", "footer navbar-fixed-bottom container-fluid");

var contador = 1;

function main() {
	
	$('.menu_bar').click(function(){
		if(contador==1){
			// Hacemos aparecer el menú lateral material design
			$('nav').css("z-index","9999");
			$('nav').css("left","-100%");
			$('nav').show();
			// Animamos...
			$('nav').animate({left:'0%'}, function() {
				// Creamos la "famosa" sombra de Material Design en los menus laterales
				$('nav').css("-webkit-box-shadow", "189px 0px 0px 0px rgba(30,30,30, 0.5)");
				$('nav').css("-moz-box-shadow", "189px 0px 0px 0px rgba(30,30,30, 0.5)");
				$('nav').css("box-shadow", "189px 0px 0px 0px rgba(30,30,30, 0.5)");
				
			});
			// Se pone a 0 el contador para cuando se haga click... ocultar el menu
			contador = 0;
			
		}
		else {
			contador = 1;
			// Animamos el menu "saliendo" de la pantalla hacia la izquierda.
			$('nav').animate({
				left:'-100%'
			});
			// Quitamos la sombra de fondo...
			$('nav').css("-webkit-box-shadow", "");
			$('nav').css("-moz-box-shadow", "");
			$('nav').css("box-shadow", "");
			$('section').show();
		}
	});
}

/* Solución a bug de Menu abierto en movil y resize a ventana */
$(window).resize(function(){
	$('nav').css("-webkit-box-shadow", "");
	$('nav').css("-moz-box-shadow", "");
	$('nav').css("box-shadow", "");
});