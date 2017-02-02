package us.es.sos;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.net.www.protocol.http.HttpURLConnection;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.utils.HttpRequestParser;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

import dominio.CoResource;

@SuppressWarnings("serial")
public class Proxy extends HttpServlet {

	/* Funciones que recogen los métodos de la APIRest*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[Proxy.java] Petición GET llega al Servlet Proxy");
		String countryClicked = req.getPathInfo();
		countryClicked = countryClicked.replace("/", "");
		countryClicked = countryClicked.replaceAll(" ", "%20");
		System.out.println("[Proxy.java] Countryclicked = "+ countryClicked);
		
//		URL api = new URL("https://restcountries-v1.p.mashape.com/name/Spain");
//		System.out.println("Aquí 1");
//		URLConnection u = (URLConnection)api.openConnection();
//		System.out.println("Aquí 2");
//		u.setRequestProperty("X-Mashape-Key", "Lr2yqhwObPmshkowCGiVmOscPCkbp1Eg5qdjsnHd6H7kBbTpOJ");
//		u.setRequestProperty("Accept", "application/json");
//		System.out.println(u.getRequestProperty("X-Mashape-Key"));
//		try{
//		HttpResponse<JsonNode> request = Unirest.get("https://restcountries-v1.p.mashape.com/name/Spain").header("X-Mashape-Key", "Lr2yqhwObPmshkowCGiVmOscPCkbp1Eg5qdjsnHd6H7kBbTpOJ")
//				.header("Accept", "application/json")
//				.asJson();
//		} catch(Exception e) {
//			System.out.println("Excepcion");
//		}
		
		URL api = new URL("http://restcountries.eu/rest/v1/name/"+countryClicked+"?fullText=true");
		System.out.println("[Proxy.java] Accediendo a " + api);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(api.openStream()));
		String inputLine="";
		String acum="";
		
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			acum+=inputLine;
		}
		
		System.out.println("[Proxy.java] JSON de la API externa: " + acum);
		in.close();
		resp.getWriter().println(acum);
	}
}