package us.es.sos;
import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")

public class SaludoServletCriperrui extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		resp.setContentType("text/plain");
//		resp.getWriter().println("Hola criperrui");
//-------------------------
		
		String metodo = "(POR GET)";
		resp.setContentType("text/html");		
		try{
			devuelveResp(req, resp, metodo);		
		}catch (IOException e){		
			System.out.println(e);	
		}	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		resp.setContentType("text/plain");
//		resp.getWriter().println("Hola criperrui");
//-------------------------
		
		String metodo = "(POR POST)";
		resp.setContentType("text/html");		
		try{
			devuelveResp(req, resp, metodo);		
		}catch (IOException e){		
			System.out.println(e);	
		}
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		resp.setContentType("text/plain");
//		resp.getWriter().println("Hola criperrui");
//-------------------------
		
		String metodo = "(POR PUT)";
		resp.setContentType("text/html");		
		try{
			devuelveResp(req, resp, metodo);		
		}catch (IOException e){		
			System.out.println(e);	
		}	
	}
	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		resp.setContentType("text/plain");
//		resp.getWriter().println("Hola criperrui");
//-------------------------
		
		String metodo = "(POR DELETE)";
		resp.setContentType("text/html");		
		try{
			devuelveResp(req, resp, metodo);		
		}catch (IOException e){		
			System.out.println(e);	
		}
	
	}
	private void devuelveResp(HttpServletRequest req, HttpServletResponse r, String metodo) throws IOException{
		//getPathInfo() -> Toma /* después de /saludo...
		
		
		String s = req.getPathInfo();
		if(s != null){
			
		//elimino la /
			s = s.substring(1, s.length());
			if (s.equals("criperrui") || s.equals("Marcos") || s.equals("Pablo")){				
				// Con esto, cambio el "nick" por el nombre real mío.
				
				if (s.equals("criperrui")){
					s = "Cristina";
				}		
				r.getWriter().println("<html><body><p>Hola! Bienvenid@ " + s + " " + metodo + "</p></body></html>" );
			}else{
				r.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}else{
			r.getWriter().println("<html><body><p>Tiene que añadir un nombre concreto</p></body></html>" );
		}
	}
}
