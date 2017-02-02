package us.es.sos;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;

import dominio.InversionResource;

/* Esta clase tiene la misma estructura y semántica que Co.java, por tanto la documentación es análoga */

@SuppressWarnings("serial")
public class CountriesInversions extends HttpServlet {
	//Funciones de los métodos de la API
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[CountriesInversions.java] Petición GET llega al Servlet CountriesInversions");
		process(req, resp);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[CountriesInversions.java] Petición POST llega al Servlet CountriesInversions");
		process(req, resp);
	}
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[CountriesInversions.java] Petición PUT llega al Servlet CountriesInversions");
		process(req, resp);
	}
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[CountriesInversions.java] Petición DELETE llega al Servlet CountriesInversions");
		process(req, resp);
	}

	/* Función que procesa la versión de API a usar, la URL, los parámetros, y en función del método
	 * de la petición, ejecuta una funcionalidad distina
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		//Procesado de URL y parámetros de entrada
		System.out.println("[CountriesInversions.java] Procesando url...");
		String s = req.getRequestURI();
		String [] arguments = s.split("/");
		String method = req.getMethod();
		String version = arguments[2];
		System.out.println("[CountriesInversions.java] Método, method="+method);
		System.out.println("[CountriesInversions.java] Version de la API, version="+version);
		System.out.println("Elementos de url: " + arguments.length);
		for(int i = 0; i < arguments.length; i++)
			System.out.print(arguments[i]+ " ");
		
		//según el número de argumetos de la url
		switch(arguments.length) {
			case 4:
				/* Llama al método que procesa la lista de countries, responde a las urls del tipo:
				 * "/countriesinversions/"
				 */
				doProcessCountries(method, req, resp, version);
				break;
			case 5:
				/* Llama al método que procesa la lista de años para un country dado, responde a las urls del tipo:
				 * "/countriesinversions/Spain, por ejemplo"
				 */
				doProcessYearsForACountry(method, arguments[4], req, resp, version);
				break;
			case 6:
				/* Llama al método que procesa un país y un country dado, responde a las urls del tipo:
				 * "/countriesinversions/Spain/2010, por ejemplo"
				 */
				doProcess(method, arguments[4], arguments[5], req, resp, version);
				break;
			default:
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	/* Todo el procesado de las urls /countriesinversions/ 
	 * Respuestas según tabla REST*/
	private void doProcessCountries(String method, HttpServletRequest req, HttpServletResponse resp, String version) throws IOException {
		switch(method){
		/* Según el método, irémos a uno u otro o prohibiremos la acción... (sólo se usa la version 2 por indicaciones del
		 * profesor, pero la versión 1 sigue siendo compatible y usable).
		 */
		/*Métodos GET, POST, PUT y DELETE para ambas versiones*/
			case "GET": 
				if(version.equals("v1"))
					returnAllCountries(req, resp);
				else
					returnAllCountries2(req, resp);
				break;
			case "PUT":
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
				break;
			case "POST":
				if(req.getContentType().equals("application/json"))
					if(version.equals("v1"))
						addNewCountry(req, resp);
					else
						addNewCountry2(req, resp);
				else
					resp.sendError(HttpServletResponse.SC_FORBIDDEN);
				break;
			case "DELETE":
				deleteAllCountries(req, resp);
				break;
			default:
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				
		}
	}

	/* Método DELETE para ambas versiones de la API
	 */
	private void deleteAllCountries(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Abrimos el datastore, lo recorremos entero sacando entidades y luego vamos borrando una a una...
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// Tipo por el que voy a preguntar: InversionResource
		Query q = new Query("InversionResource");
		// Creo una lista con los datos que he sacado
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator(); 
		ArrayList<Entity> l = new ArrayList<Entity>();
		while(it.hasNext()){
			Entity e = it.next();
			l.add(e);
		}	
		
		Iterator itList = l.iterator();
		
		System.out.println("[CountriesInversions.java] Función deleteAllCountries(): borrando datos...");
		while(itList.hasNext())
		{
			Entity e = (Entity)itList.next();
			datastore.delete(e.getKey());
		}
		System.out.println("[CountriesInversions.java] Función deleteAllCountries(): Todo borrado.");
	}

	// Función para v1 (OBSOLETA)
	private void returnAllCountries(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Set<String> lista = new TreeSet<String>();
		
		Query q = new Query("InversionResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			lista.add((String)e.getProperty("country"));
		}
		String json = gson.toJson(lista);
		//resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	// Función para v1 (OBSOLETA)
	private void addNewCountry(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String country = null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;
		
		while( (jsonString = br.readLine()) != null ){
		   sb.append(jsonString);
		}    

		jsonString = sb.toString();
		
		try{
		   country = gson.fromJson(jsonString, String.class);
		   System.out.println(country);
		}catch(Exception e){
		   System.out.println("ERROR parsing InversionResource: "+e.getMessage());
		}
		
		InversionResource ir = new InversionResource(country);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity("InversionResource");
		e.setProperty("country", ir.getCountry());
		e.setProperty("year", ir.getYear());
		e.setProperty("inversion", ir.getInversion());
		
		if(!existEntity(e))
			datastore.put(e);
		else
			resp.sendError(HttpServletResponse.SC_CONFLICT);
	}
	
	/* 
	 * Función GET de v2 que muestra los datos almacanados hasta el momento.
	 */
	private void returnAllCountries2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Todos los datos se meterán en una lista de InversionResource y ésta se parseará a json
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<InversionResource> lista = new ArrayList<InversionResource>();
		int petitionYear = -1;
		
		if(req.getParameter("year") != null) {
			petitionYear = Integer.parseInt(req.getParameter("year"));
			System.out.println("[CountriesInversions.java] parametro year =" + petitionYear);
		}
		
		Query q = new Query("InversionResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		System.out.println("[CountriesInversions.java] ReturnAllCountries2(): Recuperando datos...");
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			InversionResource ir = new InversionResource((String)e.getProperty("country"));
			ir.setYear((int)(long)e.getProperty("year"));
			ir.setInversion((long)e.getProperty("inversion"));
			if(petitionYear != -1) {
				if(ir.getYear() == petitionYear)
					lista.add(ir);
			}else
				lista.add(ir);
		}
		// Parseo de datos a JSON
		String json = gson.toJson(lista);
		System.out.println("[CountriesInversions.java] ReturnAllCountries2(): Datos recuperados: " + json);
		//resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	// Función que realiza el método POST de v2 de CountriesInversions cuando se le pasa un payload relleno
	private void addNewCountry2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		InversionResource ir = new InversionResource();
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;
		
		while( (jsonString = br.readLine()) != null ){
		   sb.append(jsonString);
		}    

		jsonString = sb.toString();
		System.out.println("[CountriesInversions.java] addNewCountry2(): Payload del json en servidor, jsonString=" + jsonString);

		try{
			System.out.println("[countriesInversions.java] addNewCountry2(): Convirtiendo a objeto");
		   ir = gson.fromJson(jsonString, InversionResource.class);
		}catch(Exception e){
		   System.out.println("ERROR parsing InversionResource: "+e.getMessage());
		}
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity("InversionResource");
		e.setProperty("country", ir.getCountry());
		e.setProperty("year", ir.getYear());
		e.setProperty("inversion", ir.getInversion());
		
		if(existFullEntity(e)){
			System.out.println("[CountriesInversions.java] addNewCountry2(): La tupla " + jsonString + " ya está en Datastore");
			resp.sendError(HttpServletResponse.SC_CONFLICT);
		}else{
			System.out.println("[CountriesInversions.java] addNewcountry2(): " + jsonString + " añadido a Datastore");
			datastore.put(e);
		}
	}

	/* Todo el procesado de las urls /countriesinversions/País
	 * Respuestas según tabla REST
	 * Este método permite la búsqueda por país de sólo las tuplas de dicho país.
	 * Las funciones de v1 están OBSOLETAS (no se pasa por ellas)
	 */	
	private void doProcessYearsForACountry(String method, String country, HttpServletRequest req, HttpServletResponse resp, String version) throws IOException
	{
		// Se crea una entidad con el country que hemos pasado como parámetro
		System.out.println("[CountriesInversions.java] doProcessYearsForAcountry(): Buscando datos con Country: "+country);
		InversionResource ir = new InversionResource(country);
		Entity e = new Entity("InversionResource");
		e.setProperty("country", ir.getCountry());
		e.setProperty("year", ir.getYear());
		e.setProperty("inversion", ir.getInversion());
		if(!existEntity(e))
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		else
		{
			switch(method){
				case "GET": 
					if(version.equals("v1")){
						returnAllYearsForACountry(country, req, resp);
					}else{
						System.out.println("[CountriesInversions.java] doProcessYearsForACountry(): Buscando datos para Country: " + country);
						returnAllYearsForACountry2(country, req, resp);
					}
					break;
				case "PUT":
					resp.sendError(HttpServletResponse.SC_FORBIDDEN);
					break;
					// Métodos POST dado un JSON y un país por url
				case "POST":
					if(req.getContentType().equals("application/json")){
						if(version.equals("v1")){
							addNewYearForACountry(country, req, resp);
						}else{
							System.out.println("[CountriesInversions.java] doProcessYearsForACountry(): Buscando todos las tuplas con country="+country + " para añadir año y dato");

							addNewYearForACountry2(country, req, resp);
						}
					}else{
						resp.sendError(HttpServletResponse.SC_FORBIDDEN);
					}
					break;
				case "DELETE":
					// Borrado de todos las tuplas pertenecientes al paçis dado
					System.out.println("[CountriesInversions.java] doProcessYearsForACountry(): Borrando todos los datos de country: " + country);
					deleteAllYearsForACountry(country, req, resp);
					break;
				default:
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

	// Función de v1 (OBSOLETA)
	private void addNewYearForACountry(String country, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String yearParameter = null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;
		
		while( (jsonString = br.readLine()) != null ){
		   sb.append(jsonString);
		}    

		jsonString = sb.toString();
		
		try{
		   yearParameter = gson.fromJson(jsonString, String.class);
		   System.out.println(yearParameter);
		}catch(Exception e){
		   System.out.println("ERROR parsing Year for InversionResource: "+e.getMessage());
		}
		long year = Long.parseLong(yearParameter);
		
		InversionResource ir = new InversionResource(country);
		Entity e = new Entity("InversionResource");
		e.setProperty("country", ir.getCountry());
		e.setProperty("year", ir.getYear());
		e.setProperty("inversion", ir.getInversion());
		if(existYearForAnEntity(e, (int)year))
			resp.sendError(HttpServletResponse.SC_FOUND);
		else {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Entity newEntity = new Entity("InversionResource");
			newEntity.setProperty("country", ir.getCountry());
			newEntity.setProperty("year", (int)year);
			newEntity.setProperty("inversion", ir.getInversion());
			System.out.println("Pais: "+ (String)newEntity.getProperty("country"));
			System.out.println("Año: "+ (int)newEntity.getProperty("year"));
			System.out.println("Inversion: "+ (long)newEntity.getProperty("inversion"));
			datastore.put(newEntity);
		}
	}

	// Función de v1 (OBSOLETA)
	private void returnAllYearsForACountry(String country, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<Integer> lista = new ArrayList<Integer>();
		
		Query q = new Query("InversionResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			if(countryE.equals(country)) { 
				System.out.println(country + " encontrado..." + (int)(long)e.getProperty("year")); 
				lista.add((int)(long)e.getProperty("year"));
			}
		}
		String json = gson.toJson(lista);
		//resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	// Función que borra todas las tuplas de un país dado.
	private void deleteAllYearsForACountry(String country, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query("InversionResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		// Iterador para borrar todas las tuplas del país.
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			int yearE = (int)(long)e.getProperty("year");
			if(countryE.equals(country) && yearE != -1) { 
				System.out.println("[CountriesInversions.java] deleteAllYearsForAcountry(): Country: "+ country + " encontrado..." + (int)(long)e.getProperty("year")+ " y borrado"); 
				datastore.delete(e.getKey());
			}
		}
	}

	// Método GET para un país dado, se muestran todas las tuplas de ese país.
	private void returnAllYearsForACountry2(String country, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<InversionResource> lista = new ArrayList<InversionResource>();
		System.out.println("[countriesInversions,java] returnAllYearsForAcountry2(): Buscando datos para Country: " + country);
		Query q = new Query("InversionResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			if(countryE.equals(country)) { 
				InversionResource ir = new InversionResource((String)e.getProperty("country"), (int)(long)e.getProperty("year"), (long)e.getProperty("inversion"));
				System.out.println("[CountriesInversions.java] retunrAllYearsForACountry2(): Country "+ country + " encontrado..." + (int)(long)e.getProperty("year")); 
				lista.add(ir);
			}
		}
		String json = gson.toJson(lista);
		//resp.setContentType("application/json");
		resp.getWriter().println(json);
	}

	/* Creación de datos para la API v2
	 * Se pretende que /countriesinversions/Spain se comporte como /countriesinversions/ (POST) cuando el country de la url y 
	 * el payload son distintos y el del payload no existe en los datos.
	 * Se comporta como un PUT si ambos countries coinciden.
	 * */
	private void addNewYearForACountry2(String country, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		InversionResource ir = new InversionResource();
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;
		// Cojo el payload
		while( (jsonString = br.readLine()) != null ){
		   sb.append(jsonString);
		}    

		jsonString = sb.toString();
		System.out.println("[CountriesInversions.java] addNewYearForACountry(): El payload recibido es: " + jsonString);
		try{
			// Creación del objeto
		   ir = gson.fromJson(jsonString, InversionResource.class);
		   System.out.println("[CountriesInversions.java] addNewYearForACountry(): Objeto creado con " + jsonString);
		}catch(Exception e){
		   System.out.println("ERROR parsing InversionResource: "+e.getMessage());
		}
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity("InversionResource");
		e.setProperty("country", ir.getCountry());
		e.setProperty("year", ir.getYear());
		e.setProperty("inversion", ir.getInversion());
		
		// Si existe el país con el año dará error porque esta acción debe hacerse con PUT.
		if(existFullEntity(e)){
			System.out.println("[CountriesInversions.java] addNewYearForACountry2(): Error, existe country y year y hay que hacerlo por PUT");
			resp.sendError(HttpServletResponse.SC_CONFLICT);
		}else{
			System.out.println("[CountriesInversions.java] addNewYearForACountry2(): Dato: " + jsonString + "  de la BD con éxito");
			datastore.put(e);
		}
	}

	/* Todo el procesado de las urls /countriesinversions/Pais/Año */
	private void doProcess(String method, String country, String yearParameter, HttpServletRequest req, HttpServletResponse resp, String version) throws IOException {
		int year = Integer.parseInt(yearParameter);
		// En nuestra BD almacenamos "United States", no "United%20States" que es lo que nos
		// puede llegar por petición Ajax...
		country = country.replaceAll("%20", " ");
		InversionResource ir = new InversionResource(country);
		// Tenemos un year...
		ir.setYear(year);
		Entity e = new Entity("InversionResource");
		e.setProperty("country", ir.getCountry());
		e.setProperty("year", ir.getYear());
		e.setProperty("inversion", ir.getInversion());
		if(!existEntity(e) || !existYearForAnEntity(e, year)) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			System.out.println("[CountriesInversions.java] Entidad con "+country +" y " + year + " no encontrada.");
		}
		else { 
			switch(method){
				case "GET": 
					if(version.equals("v1"))
						returnData(req, resp, country, year);
					else {
						System.out.println("[CountriesInversions.java] doProcess() Llamando al método GET");
						returnData2(req, resp, country, year);
					}
					break;
				case "PUT":
					if(version.equals("v1"))
						updateData(req, resp, country, year);
					else {
						System.out.println("[CountriesInversions.java] doProcess() llamando a PUT");
						updateData2(req, resp, country, year);
					}
					break;
				case "POST":
					resp.sendError(HttpServletResponse.SC_FORBIDDEN);
					break;
				case "DELETE":
					System.out.println("[CountriesInversions.java] doProcess() llamando al deleteAllDataForAYearAndCountry [la tupla]");
					deleteAllDataForAYearAndCountry(req, resp, country, year);
					break;
				default:
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}
	private void returnData(HttpServletRequest req, HttpServletResponse resp, String country, int year) throws IOException {
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<Long> lista = new ArrayList<Long>();
		
		Query q = new Query("InversionResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			int yearE = (int)(long)e.getProperty("year");
			if(countryE.equals(country) && yearE == year) { 
				System.out.println("[CountriesInversions] returnData(): " +country + " encontrado..." + (int)(long)e.getProperty("year") + " year"); 
				lista.add((long)e.getProperty("inversion"));
			}
		}
		String json = gson.toJson(lista);
		//resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	// Actualiza tupla (sólo permite actualizarse el DATO de inversion
	private void updateData(HttpServletRequest req, HttpServletResponse resp, String country, int year) throws IOException {
		long inversion = 0;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;
		
		while( (jsonString = br.readLine()) != null ){
		   sb.append(jsonString);
		}    

		jsonString = sb.toString();
		
		try{
		   inversion = gson.fromJson(jsonString, Long.class);
		   System.out.println(inversion);
		}catch(Exception e){
		   System.out.println("ERROR parsing Year: "+e.getMessage());
		}
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query("InversionResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			int yearE = (int)(long)e.getProperty("year");
			if(countryE.equals(country) && yearE == year) { 
				e.setProperty("inversion", inversion);
				datastore.put(e);
				System.out.println("[CountriesInversions.java] updateData(): Dato modificado en la tupla, inversion="+inversion);
			}
		}
	}
	
	// Borra todos los datos de una tupla
	private void deleteAllDataForAYearAndCountry(HttpServletRequest req, HttpServletResponse resp, String country, int year) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query("InversionResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			int yearE = (int)(long)e.getProperty("year");
			long inversionE = (long)e.getProperty("inversion");
			System.out.println("Inversion = "+inversionE);
			if(countryE.equals(country) && yearE == year && inversionE != -1) { 
				System.out.println("[CountriesInversions.java] deleteAllDataForAYearAndCountry(): " + country + " encontrado..." + (int)(long)e.getProperty("year")+ "e inversion="+e.getProperty("inversion")+ " y borrado"); 
				datastore.delete(e.getKey());
			}
		}
	}
	
	// Devuelve todos los datos (VERSION 2), la tupla entera
	private void returnData2(HttpServletRequest req, HttpServletResponse resp, String country, int year) throws IOException {
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query("InversionResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		InversionResource ir = new InversionResource();
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			int yearE = (int)(long)e.getProperty("year");
			if(countryE.equals(country) && yearE == year) { 
				System.out.println("[CountriesInversions.java] returnData2(): InversionResource encontrado");
				ir = new InversionResource(countryE, yearE, (long)e.getProperty("inversion"));
			}
		}
		String json = gson.toJson(ir);
		//resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	// Actualización del dato (tupla completa de versión 2)
	private void updateData2(HttpServletRequest req, HttpServletResponse resp, String country, int year) throws IOException {
		InversionResource co = new InversionResource();
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;
		
		while( (jsonString = br.readLine()) != null ){
		   sb.append(jsonString);
		}    

		jsonString = sb.toString();
		
		try{
		   co = gson.fromJson(jsonString, InversionResource.class);
		}catch(Exception e){
		   System.out.println("ERROR parsing InversionResource: "+e.getMessage());
		}
	
		if(country.equals(co.getCountry()) && year == co.getYear()) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Entity e = new Entity("InversionResource");
			e.setProperty("country", co.getCountry());
			e.setProperty("year", co.getYear());
			e.setProperty("inversion", co.getInversion());
			
			if(existEntity(e) && existYearForAnEntity(e, co.getYear())) {
				Query q = new Query("InversionResource").setFilter(new FilterPredicate("country", Query.FilterOperator.EQUAL, (String)e.getProperty("country")));
				PreparedQuery pq = datastore.prepare(q);
				Iterator<Entity> it = pq.asIterator();
				boolean encontrado = false;
				while(it.hasNext() && !encontrado) 
				{
					Entity entidad = (Entity)it.next();
					if((int)(long)entidad.getProperty("year") == year) {
						entidad.setProperty("inversion", co.getInversion());
						datastore.put(entidad);
						System.out.println("[CountriesInversions.java] updateData2(): Entidad encontrada y modificada con la nueva inversion="+co.getInversion());
						encontrado = true;
					}	
				}
			}
			else
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		else
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
	
	/* Auxiliares terminadas */
	private boolean existEntity(Entity e) {
		boolean encontrado = false;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("InversionResource").setFilter(new FilterPredicate("country", Query.FilterOperator.EQUAL, (String)e.getProperty("country")));
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		int contador = 0;
		
		while(it.hasNext())
		{
			contador++;
			it.next();
		}
		
		if(contador != 0)
			encontrado = true;
		
		return encontrado;
	}
	private boolean existYearForAnEntity(Entity e, int year) throws IOException {
		boolean encontrado = false;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("InversionResource").setFilter(new FilterPredicate("country", Query.FilterOperator.EQUAL, (String)e.getProperty("country")));
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		while(it.hasNext() && !encontrado)
		{
			Entity entity = (Entity)it.next();
			if((int)((long)entity.getProperty("year")) == year)
				encontrado = true;
		}
				
		return encontrado;
	}
	private boolean existFullEntity(Entity e) {
		boolean encontrado = false;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("InversionResource").setFilter(new FilterPredicate("country", Query.FilterOperator.EQUAL, (String)e.getProperty("country")));
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		int contador = 0;
		
		while(it.hasNext() && !encontrado)
		{
			contador++;
			Entity entity = (Entity)it.next();
			int yearEntity = (int)(long)entity.getProperty("year");
			int yearE = (int)e.getProperty("year");
			if(yearEntity == yearE)
				encontrado = true;
		}
				
		return encontrado;
	}
}
