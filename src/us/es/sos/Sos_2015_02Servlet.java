package us.es.sos;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")

/**
 * 
 * @author MarcosAlberto
 * Nuestra clase debe heredad de HttpServlet
 * 
 */
public class Sos_2015_02Servlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Usando "resp" le podemos devolver la informaci�n al usuario.
		// setContentType le dice lo que se le est� devolviendo.
		// Vamos a cambiarlo a HTML, as� que comentamos esta linea y ponemos la siguiente
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Hello, world");
		resp.setContentType("text/html");
		resp.getWriter().println("<html><body><h1>Hola Mundo</h1></body></html>");
		
		// Ahora asociamos una url con esta clase... eso es en war/WEB-Inf/web.xml
	}
}
