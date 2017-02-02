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
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;

import dominio.CoResource;

@SuppressWarnings("serial")
public class Co extends HttpServlet {

	/* Funciones que recogen los métodos de la APIRest*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[Co.java] Petición GET llega al Servlet Co");
		process(req, resp);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[Co.java] Petición POST llega al Servlet Co");
		process(req, resp);
	}
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[Co.java] Petición PUT llega al Servlet Co");
		process(req, resp);
	}
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[Co.java] Petición DELETE llega al Servlet Co");
		process(req, resp);
	}
	
	/* Función que procesa la versión de API a usar, la URL, los parámetros, y en función del método
	 * de la petición, ejecuta una funcionalidad distina
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Procesado de la URL y sus parámetros y campos...
		System.out.println("[Co.java] Procesando url...");
		String s = req.getRequestURI();
		String [] arguments = s.split("/");
		String method = req.getMethod();
		String version = arguments[2];
		System.out.println("[Co.java] Método, method="+method);
		System.out.println("[Co.java] Version de la API, version="+version);
		System.out.println("Elementos de url: " + arguments.length);
		for(int i = 0; i < arguments.length; i++)
			System.out.print(arguments[i]+ " ");
		System.out.println();
		
		// Dependiendo del número de argumentos...
		switch(arguments.length) {
			case 4:
				/* Llama al método que procesa la lista de countries, responde a las urls del tipo:
				 * "/co2/"
				 */
				System.out.println("[Co.java] /co2/ encontrado sólo, voy a doProcessCountries()");
				doProcessCountries(method, req, resp, version);
				break;
			case 5:
				/* Llama al método que procesa la lista de años para un country dado, responde a las urls del tipo:
				 * "/co2/Spain, por ejemplo"
				 */
				System.out.println("[Co.java] /co2/Country encontrado sólo, voy a doProcessYearsForACountry()");
				doProcessYearsForACountry(method, arguments[4], req, resp, version);
				break;
			case 6:
				/* Llama al método que procesa un país y un country dado, responde a las urls del tipo:
				 * "/co2/Spain/2010, por ejemplo"
				 */
				System.out.println("[Co.java] /co2/Country/Year encontrado, voy a doProcess()");
				doProcess(method, arguments[4], arguments[5], req, resp, version);
				break;
			default:
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	/* Todo el procesado de las urls /co2/ */
	/* Códigos y respuestas asignadas en la tabla azul de clase para prohibir y permitir los distintos
	 * métodos según se requiera
	 */
	private void doProcessCountries(String method, HttpServletRequest req, HttpServletResponse resp, String version) throws IOException {
		switch(method){
			// Dependiendo del metodo, irémos a uno, otro o prohibiremos... (ahora sólo se hace en version 2, la 1 
			// sigue siendo compatible y usable pero Pablo decidió la 2 como correcta
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
	/*
	 * Borra todos los datos, compatible con ambas versiones. 
	 */
	private void deleteAllCountries(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Abrimos el datastore, lo recorremos entero sacando entidades y luego vamos borrando una a una...
		// cutre...
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		// Tipo por el que voy a preguntar, en este caso, CoResource
		Query q = new Query("CoResource");
		
		// Saco todos los datos y los introduzco en una lista
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator(); 
		ArrayList<Entity> l = new ArrayList<Entity>();
		while(it.hasNext()){
			Entity e = it.next();
			l.add(e);
		}	
		
		Iterator itList = l.iterator();
		
		System.out.println("[Co.java] Función deleteAllCountries: borrando todo...");
		while(itList.hasNext())
		{
			Entity e = (Entity)itList.next();
			datastore.delete(e.getKey());
		}
		System.out.println("[Co.java] Función deleteAllCountries: todo borrado...");
	}
	
	/*
	 * Función para v1, obsoleta
	 */
	private void returnAllCountries(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Set<String> lista = new TreeSet<String>();
		
		Query q = new Query("CoResource");
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
	/*
	 * Función para v1, obsoleta
	 */
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
		   System.out.println("ERROR parsing Town: "+e.getMessage());
		}
		
		CoResource co = new CoResource(country);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity("CoResource");
		e.setProperty("country", co.getCountry());
		e.setProperty("year", co.getYear());
		e.setProperty("quantity", co.getQuantity());
		
		if(!existEntity(e))
			datastore.put(e);
		else
			resp.sendError(HttpServletResponse.SC_CONFLICT);
	}
	/* Muestra de datos para la API v2 
	 * Es el GET de /co2/ 
	 * */
	private void returnAllCountries2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Se van a meter todos los datos en una lista de CoResources y se parsean a JSON
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<CoResource> lista = new ArrayList<CoResource>();
		
		Query q = new Query("CoResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		System.out.println("[Co.java] Recuperando todos los datos");
		// Sacando datos y añadiendo a la lista
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			CoResource co = new CoResource((String)e.getProperty("country"));
			co.setYear((int)(long)e.getProperty("year"));
			co.setQuantity((float)(double)e.getProperty("quantity"));
			lista.add(co);
		}
		
		// Parseo a JSON
		String json = gson.toJson(lista);
		System.out.println("[Co.java] Datos recuperados = " + json);
		resp.getWriter().println(json);
	}
	/* Creación de datos para la API v2 
	 * Introduce un CoResource completo [la tupla] desde el payload
	 * */
	private void addNewCountry2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		CoResource co = new CoResource();
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;
		
		while( (jsonString = br.readLine()) != null ){
		   sb.append(jsonString);
		}    

		jsonString = sb.toString();
		System.out.println("[Co.java] addNewCountry2(): payload del json en servidor, jsonString=" + jsonString);
		try{
		   System.out.println("[Co.java] addNewCountry2(): Convirtiendo a objeto");
		   co = gson.fromJson(jsonString, CoResource.class);
		}catch(Exception e){
		   System.out.println("ERROR parsing CoResource: "+e.getMessage());
		}
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity("CoResource");
		e.setProperty("country", co.getCountry());
		e.setProperty("year", co.getYear());
		e.setProperty("quantity", co.getQuantity());
		
		if(existFullEntity(e)){
			System.out.println("[Co.java] addNewCountry2(): " + jsonString + " ya en Datastore");
			resp.sendError(HttpServletResponse.SC_CONFLICT);
		}
		else {
			System.out.println("[Co.java] addNewCountry2(): Datastore actualizado con: "+jsonString);
			datastore.put(e);
		}
	}
	
	/* Todo el procesado de las urls /co2/Pais */
	/* Códigos y respuestas asignadas en la tabla azul de clase para prohibir y permitir los distintos
	 * métodos según se requiera.
	 * La parte de la función que pertenece a v1 está obsoleta, nunca se pasa por ella en la aplicación web
	 * Este método permite la búsqueda por país de sólo sus tuplas.
	 */	
	private void doProcessYearsForACountry(String method, String country, HttpServletRequest req, HttpServletResponse resp, String version) throws IOException
	{
		// Creamos una entity con el country que viene de la petición
		System.out.println("[Co.java] doProcessYearsForACountry(): Buscando datos para country=" + country);
		CoResource co = new CoResource(country);
		Entity e = new Entity("CoResource");
		e.setProperty("country", co.getCountry());
		e.setProperty("year", co.getYear());
		e.setProperty("quantity", co.getQuantity());
		if(!existEntity(e))
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		else
		{
			switch(method){
				case "GET": 
					if(version.equals("v1"))
							returnAllYearsForACountry(country, req, resp);
					else {
						    System.out.println("[Co.java] doProcessYearsForACountry(): Buscando todos las tuplas con country="+country);
							returnAllYearsForACountry2(country, req, resp);
					}
					break;
				case "PUT":
					resp.sendError(HttpServletResponse.SC_FORBIDDEN);
					break;
				case "POST":
					// Permite hacer un post dado un country por url y JSON
					if(req.getContentType().equals("application/json"))	
						if(version.equals("v1"))
							addNewYearForACountry(country, req, resp);
						else {
							System.out.println("[Co.java] doProcessYearsForACountry(): Buscando todos las tuplas con country="+country + " para añadir año y dato");
							addNewYearForACountry2(country, req, resp);
						}
					else
						resp.sendError(HttpServletResponse.SC_FORBIDDEN);
					break;
				case "DELETE":
					// Permite borrar todos los datos de un sólo country [tuplas completas en v2]
					System.out.println("[Co.java] doProcessYearsForACountry(): Borrando todas las tuplas con country="+country);
					deleteAllYearsForACountry(country, req, resp);
					break;
				default:
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}
	/*
	 * Función de v1: obsoleta
	 * */
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
		   System.out.println("ERROR parsing Year: "+e.getMessage());
		}
		long year = Long.parseLong(yearParameter);
		
		CoResource co = new CoResource(country);
		Entity e = new Entity("CoResource");
		e.setProperty("country", co.getCountry());
		e.setProperty("year", co.getYear());
		e.setProperty("quantity", co.getQuantity());
		if(existYearForAnEntity(e, (int)year))
			resp.sendError(HttpServletResponse.SC_FOUND);
		else {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Entity newEntity = new Entity("CoResource");
			newEntity.setProperty("country", co.getCountry());
			newEntity.setProperty("year", (int)year);
			newEntity.setProperty("quantity", co.getQuantity());
			System.out.println("Pais: "+ (String)newEntity.getProperty("country"));
			System.out.println("Año: "+ (int)newEntity.getProperty("year"));
			System.out.println("Co2: "+ (float)newEntity.getProperty("quantity"));
			datastore.put(newEntity);
		}
	}
	/*
	 * Función de v1: obsoleta
	 * */
	private void returnAllYearsForACountry(String country, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<Integer> lista = new ArrayList<Integer>();
		
		Query q = new Query("CoResource");
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
	// Misma función que el deleteAllCountries pero para un sólo country [borra todas las tuplas para v2
	private void deleteAllYearsForACountry(String country, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query("CoResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		// Itera hasta el final porque borra TODAS las tuplas para un co2/Country [en v2]
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			int yearE = (int)(long)e.getProperty("year");
			// El yearE != -1 se hace por compatibilizar las versiones v1 y v2. Evidentemente, siempre
			// es cierto si sólo se trabaja con v2
			if(countryE.equals(country) && yearE != -1) { 
				System.out.println("[Co.java] deleteAllYearsForACountry(): " + country + " encontrado..." + (int)(long)e.getProperty("year")+ " y borrado"); 
				datastore.delete(e.getKey());
			}
		}
	}
	/* Muestra de datos para la API v2 para un country [tuplas completas] */
	private void returnAllYearsForACountry2(String country, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<CoResource> lista = new ArrayList<CoResource>();
		System.out.println("[Co.java] returnAllYearsForACountry2(): Buscamos para devolver todas las tuplas de " + country);
		Query q = new Query("CoResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		// Recorro el datastore buscando el country, y meto las tuplas COMPLETAS (v2)
		// en una lista que transformo en JSON
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			if(countryE.equals(country)) { 
				CoResource co = new CoResource((String)e.getProperty("country"), (int)(long)e.getProperty("year"), (float)(double)e.getProperty("quantity"));
				System.out.println("[Co.java] returnAllYearsForACountry2(): " + country + " encontrado..." + (int)(long)e.getProperty("year")); 
				lista.add(co);
			}
		}
		String json = gson.toJson(lista);
		resp.getWriter().println(json);
	}
	/* Creación de datos para la API v2 
	 * Con tupla entera para v2 (esto se hace para que el usuario de la api pueda hacer
	 * POST /co2/Spain y meta un payload del tipo {CountryX, XXXX, YY.XX}
	 * No se comprueba el payload que coincida con el /Spain (dicho por Pablo) ya que 
	 * lo que se pretende es que se comporte en /co2/XXXXX como si fuese /co2
	 * */
	private void addNewYearForACountry2(String country, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		CoResource co = new CoResource();
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;
		// Cojo el payload
		while( (jsonString = br.readLine()) != null ){
		   sb.append(jsonString);
		}    

		jsonString = sb.toString();
		System.out.println("[Co.java] addNewYearForACountry2(): payload="+jsonString);
		try{
		   // Creo el objeto
		   co = gson.fromJson(jsonString, CoResource.class);
		   System.out.println("[Co.java] addNewYearForACountry2(): Objeto creado con el payload anterior");
		}catch(Exception e){
		   System.out.println("ERROR parsing CoResource: "+e.getMessage());
		}
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity("CoResource");
		e.setProperty("country", co.getCountry());
		e.setProperty("year", co.getYear());
		e.setProperty("quantity", co.getQuantity());
		
		// Si existe la fullEntity (exactamente la misma tupla pais - año => Error en v2 porque hay que hacerlo
		// mediante PUT
		if(existFullEntity(e)) {
			System.out.println("[Co.java] addNewYearForACountry2(): Error, existe country y year y hay que hacerlo por PUT");
			resp.sendError(HttpServletResponse.SC_CONFLICT);
		}
		else {
			// Se inserta el dato
			System.out.println("[Co.java] addNewYearForACountry2(): payload=" + jsonString + " dentro de la BD con éxito");
			datastore.put(e);
		}
	}
	
	/* Todo el procesado de las urls /co2/Pais/Año */
	/* Códigos y respuestas asignadas en la tabla azul de clase para prohibir y permitir los distintos
	 * métodos según se requiera
	 */	
	private void doProcess(String method, String country, String yearParameter, HttpServletRequest req, HttpServletResponse resp, String version) throws IOException {
		int year = Integer.parseInt(yearParameter);
		country = country.replaceAll("%20", " ");
		CoResource co = new CoResource(country);
		co.setYear(year);
		Entity e = new Entity("CoResource");
		e.setProperty("country", co.getCountry());
		e.setProperty("year", co.getYear());
		e.setProperty("quantity", co.getQuantity());
		if(!existEntity(e) || !existYearForAnEntity(e, year))
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		else { 
			switch(method){
				case "GET": 
					if(version.equals("v1"))
						returnData(req, resp, country, year);
					else {
						// Siempre hacemos esta, por v2 [Sólo por GET] y se comprobará que existe
						// una tupla y sólo una con dicho country y year
						System.out.println("[Co.java] doProcess(): Vamos a devolver sólo la tupla que tenga country="+country+", year="+year);
						returnData2(req, resp, country, year);
					}
					break;
				case "PUT":
					if(version.equals("v1"))
						updateData(req, resp, country, year);
					else {
						// Siempre hacemos esta, por v2 [Sólo por PUT] y se comprobará que existe
						// una tupla y sólo una con dicho country y year
						System.out.println("[Co.java] doProcess(): Vamos a actualizar sólo la tupla que tenga country="+country+", year="+year);
						updateData2(req, resp, country, year);
					}
					break;
				case "POST":
					resp.sendError(HttpServletResponse.SC_FORBIDDEN);
					break;
				case "DELETE":
					// Borramos sólo una tupla con country y year
					System.out.println("[Co.java] doProcess(): Vamos a borrar sólo la tupla que tenga country="+country+", year="+year);
					deleteAllDataForAYearAndCountry(req, resp, country, year);
					break;
				default:
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}
	/* Función v1, obsoleta */
	private void returnData(HttpServletRequest req, HttpServletResponse resp, String country, int year) throws IOException {
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<Float> lista = new ArrayList<Float>();
		
		Query q = new Query("CoResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			int yearE = (int)(long)e.getProperty("year");
			if(countryE.equals(country) && yearE == year) { 
				System.out.println(country + " encontrado..." + (int)(long)e.getProperty("year") + " year"); 
				lista.add((float)(double)e.getProperty("quantity"));
			}
		}
		String json = gson.toJson(lista);
		//resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	/* Función v1, obsoleta */
	private void updateData(HttpServletRequest req, HttpServletResponse resp, String country, int year) throws IOException {
		float quantity = (float)0.0;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;
		
		while( (jsonString = br.readLine()) != null ){
		   sb.append(jsonString);
		}    

		jsonString = sb.toString();
		
		try{
		   quantity = gson.fromJson(jsonString, Float.class);
		   System.out.println(quantity);
		}catch(Exception e){
		   System.out.println("ERROR parsing Year: "+e.getMessage());
		}
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query("CoResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			int yearE = (int)(long)e.getProperty("year");
			if(countryE.equals(country) && yearE == year) { 
				e.setProperty("quantity", quantity);
				datastore.put(e);
			}
		}
	}
	/* Borra una tupla en concreto en v2 */
	private void deleteAllDataForAYearAndCountry(HttpServletRequest req, HttpServletResponse resp, String country, int year) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query("CoResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		// Se comparan todos los campos porque se está queriendo borrar una tupla con /co2/COUNTRY/YEAR concreta
		System.out.println("[Co.java] deleteAllDataForAYearAndCountry(): DELETE sobre "+ country + " y " + year);
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			int yearE = (int)(long)e.getProperty("year");
			float quantityE = (float)(double)e.getProperty("quantity");
			// quantityE no es necesario compararlo con -1 en v2 pero se hace por compatibilidad con v1
			if(countryE.equals(country) && yearE == year && quantityE != -1) { 
				System.out.println("[Co.java] deleteAllDataForAYearAndCountry(): "+ country + " encontrado..." + (int)(long)e.getProperty("year")+ "y quantity="+e.getProperty("quantity")+ " y borrado"); 
				datastore.delete(e.getKey());
			}
		}
	}
	/* Muestra de datos para la API v2 
	 * Devuelve la TUPLA (única en v2) con country y year concreto*/
	private void returnData2(HttpServletRequest req, HttpServletResponse resp, String country, int year) throws IOException {
		Gson gson = new Gson();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		System.out.println("[Co.java] returnData2(): Intentando devolver datos para country="+country+" y year="+year);
		Query q = new Query("CoResource");
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		CoResource co = new CoResource();
		while(it.hasNext())
		{
			Entity e = (Entity)it.next();
			String countryE = (String)e.getProperty("country");
			int yearE = (int)(long)e.getProperty("year");
			if(countryE.equals(country) && yearE == year) { 
				System.out.println("[Co.java] returnData2(): Encontrado country="+country+" y year="+year + " en datastore");
				co = new CoResource(countryE, yearE, (float)(double)e.getProperty("quantity"));
			}
		}
		String json = gson.toJson(co);
		System.out.println("[Co.java] returnData2(): devuelto json="+json);
		//resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	/* Modificación de datos para la API v2 */
	private void updateData2(HttpServletRequest req, HttpServletResponse resp, String country, int year) throws IOException {
		CoResource co = new CoResource();
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;
		
		while( (jsonString = br.readLine()) != null ){
		   sb.append(jsonString);
		}    

		jsonString = sb.toString();
		System.out.println("[Co.java] updateData2(): payload="+jsonString);
		try{
		   co = gson.fromJson(jsonString, CoResource.class);
		}catch(Exception e){
		   System.out.println("ERROR parsing CoResource: "+e.getMessage());
		}
		// Esta primera parte del código comprueba que existe el /PAIS/YEAR dentro del datastore
		// Concretamente, este primer if comprueba ambas cosas (que payload y url coinciden)
		if(country.equals(co.getCountry()) && year == co.getYear()) {
			System.out.println("[Co.java] updateData2(): URL y payload coinciden:");
			System.out.println("[Co.java] updateData2(): payload="+jsonString);
			System.out.println("[Co.java] updateData2(): url="+country+"/"+year);
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Entity e = new Entity("CoResource");
			e.setProperty("country", co.getCountry());
			e.setProperty("year", co.getYear());
			e.setProperty("quantity", co.getQuantity());
			
			if(existEntity(e) && existYearForAnEntity(e, co.getYear())) {
				System.out.println("[Co.java] updateData2(): Existe en BD el country="+country);
				Query q = new Query("CoResource").setFilter(new FilterPredicate("country", Query.FilterOperator.EQUAL, (String)e.getProperty("country")));
				PreparedQuery pq = datastore.prepare(q);
				Iterator<Entity> it = pq.asIterator();
				// Creo mi entidad a buscar en el datastore 
				boolean encontrado = false;
				// El iterador es SOLO el subconjunto de entidades en el datastore que tienen el mismo COUNTRY, 
				// ahora tengo que buscar entre esas el year
				while(it.hasNext() && !encontrado) 
				{
					Entity entidad = (Entity)it.next();
					// Cuando encuentro la entidad igual a la que he creado arriba también en YEAR					
					if((int)(long)entidad.getProperty("year") == year) {
						System.out.println("[Co.java] updateData2(): Existe en BD el country="+country + " y además con year="+year);
						// MODIFICO directamente la entidad
						entidad.setProperty("quantity", co.getQuantity());
						System.out.println("[Co.java] updateData2(): Actualizo su quantity con="+co.getQuantity());
						datastore.put(entidad);
						encontrado = true;
					}
					
				}
				
			}
			else
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		else {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	
	/* Auxiliares terminadas */
	/* Función para comprobar si existe una entidad con dicho country */
	private boolean existEntity(Entity e) {
		boolean encontrado = false;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("CoResource").setFilter(new FilterPredicate("country", Query.FilterOperator.EQUAL, (String)e.getProperty("country")));
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
	/* Comprobación de año y country, para una entidad dada */
	private boolean existYearForAnEntity(Entity e, int year) throws IOException {
		boolean encontrado = false;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("CoResource").setFilter(new FilterPredicate("country", Query.FilterOperator.EQUAL, (String)e.getProperty("country")));
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> it = pq.asIterator();
		
		while(it.hasNext() && !encontrado)
		{
			Entity entity = (Entity)it.next();
			int yearEntity = (int)(long)entity.getProperty("year");
			if(yearEntity == year)
				encontrado = true;
		}
				
		return encontrado;
	}
	/* Comprobación de la entidad al completo, usada para la v2 de la API */
	private boolean existFullEntity(Entity e) {
		boolean encontrado = false;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("CoResource").setFilter(new FilterPredicate("country", Query.FilterOperator.EQUAL, (String)e.getProperty("country")));
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
