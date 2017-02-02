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
public class SaludoServletMarcos extends HttpServlet {
	
	// Este método se invoca cuando se le hace una petición a una url
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Usando "resp" le podemos devolver la información al usuario.
		// setContentType le dice lo que se le está devolviendo.
		// Vamos a cambiarlo a HTML, así que comentamos esta linea y ponemos la siguiente
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Hello, world");
		// Done!
		resp.setContentType("text/html");
		String s = req.getPathInfo();
		if(s != null) {
			s = s.substring(1, s.length());
			if (s.equals("criperrui") || s.equals("Marcos") || s.equals("Pablo"))
				resp.getWriter().println("<html><body><p>Hola! Bienvenid@ " + s + " (por get)</p></body></html>");
			else
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		else
			resp.getWriter().println("<html><body><p>Tienes que añadir a la url el nombre</p></body></html");
		// Ahora asociamos una url con esta clase... eso es en war/WEB-Inf/web.xml
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Usando "resp" le podemos devolver la información al usuario.
		// setContentType le dice lo que se le está devolviendo.
		// Vamos a cambiarlo a HTML, así que comentamos esta linea y ponemos la siguiente
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Hello, world");
		resp.setContentType("text/html");
		String s = req.getPathInfo();
		
		if(s!=null) {
			s = s.substring(1, s.length());
			if (s.equals("criperrui") || s.equals("Marcos") || s.equals("Pablo"))
				resp.getWriter().println("<html><body><p>Hola! Bienvenid@ " + s + " (por put)</p></body></html>");
			else
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}else
		{
			resp.getWriter().println("<html><body><p>Tienes que añadir a la url el nombre</p></body></html");
		}
		resp.getWriter().println("<html><body><p>Tienes que añadir a la url el nombre</p></body></html");
		// Ahora asociamos una url con esta clase... eso es en war/WEB-Inf/web.xml
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Usando "resp" le podemos devolver la información al usuario.
		// setContentType le dice lo que se le está devolviendo.
		// Vamos a cambiarlo a HTML, así que comentamos esta linea y ponemos la siguiente
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Hello, world");
		resp.setContentType("text/html");
		String s = req.getPathInfo();
		if(s!=null) {
			s = s.substring(1, s.length());
			if (s.equals("criperrui") || s.equals("Marcos") || s.equals("Pablo"))
				resp.getWriter().println("<html><body><p>Hola! Bienvenid@ " + s + " (por post)</p></body></html>");
			else
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		else
			resp.getWriter().println("<html><body><p>Tienes que añadir a la url el nombre</p></body></html");
		// Ahora asociamos una url con esta clase... eso es en war/WEB-Inf/web.xml
	}
	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Usando "resp" le podemos devolver la información al usuario.
		// setContentType le dice lo que se le está devolviendo.
		// Vamos a cambiarlo a HTML, así que comentamos esta linea y ponemos la siguiente
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Hello, world");
		resp.setContentType("text/html");
		String s = req.getPathInfo();
		if(s!=null) {
			s = s.substring(1, s.length());
			if (s.equals("criperrui") || s.equals("Marcos") || s.equals("Pablo"))
				resp.getWriter().println("<html><body><p>Hola! Bienvenid@ " + s + " (por delete)</p></body></html>");
			else
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}else
		{
			resp.getWriter().println("<html><body><p>Tienes que añadir a la url el nombre</p></body></html");
		}
		// Ahora asociamos una url con esta clase... eso es en war/WEB-Inf/web.xml
	}
}