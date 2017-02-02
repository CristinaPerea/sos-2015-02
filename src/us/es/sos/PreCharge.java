package us.es.sos;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.opencsv.CSVReader;

@SuppressWarnings("serial")
public class PreCharge extends HttpServlet {
	// Sólo atiende peticiones POST para subir archivos
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[PreCharge.java] Entrando en el método doGet()");
		System.out.println("[PreCharge.java] Cargando datos de preChargeCo.csv");
		System.out.println(req.getServerName());
		String archivoCo;
		String archivoIn;
		if(req.getServerName().equals("localhost")) {
			archivoCo = "preChargeCo3.csv";
			archivoIn = "preChargeIn3.csv";
		}
		else
		{
			archivoCo = "preChargeCo.csv";
			archivoIn = "preChargeIn.csv";
		}
		// Abrimos fichero y lo mandamos al CSVReader
		CSVReader reader = new CSVReader(new FileReader(archivoCo));
	    
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		String [] nextLine;
		String country;
		int year;
		float quantity;
		long inversion;
		Entity e;
		// Leemos la primera linea (que son los campos en un CSV formal y lo descartamos
		nextLine = reader.readNext();
		while((nextLine = reader.readNext()) != null) {
			e = new Entity("CoResource");
			// En cada variable guardamos cada "string" transformada a su tipo
			country = nextLine[0];
			year = Integer.parseInt(nextLine[1]);
			quantity = Float.parseFloat(nextLine[2]);
			// Ponemos en la entidad dichas propiedades
			e.setProperty("country",country);
			e.setProperty("year", year);
			e.setProperty("quantity", quantity);
			// La introducimos en el datastore...
			datastore.put(e);
			System.out.println("[PreCharge.java] Elemento: country="+country +", year="+year+", quantity="+quantity);
		}
		reader.close();
		
		System.out.println("[PreCharge.java] Cargando datos de preChargeIn3.csv");
		// Ahora hacemos la precarga de la 2ª API
		reader = new CSVReader(new FileReader(archivoIn));
	    
		nextLine = reader.readNext();
		while((nextLine = reader.readNext()) != null) {
			System.out.println(nextLine[0]+nextLine[1]+nextLine[2]);
			e = new Entity("InversionResource");
			country = nextLine[0];
			year = Integer.parseInt(nextLine[1]);
			inversion = Long.parseLong(nextLine[2]);
			e.setProperty("country",country);
			e.setProperty("year", year);
			e.setProperty("inversion", inversion);
			datastore.put(e);
			System.out.println("[PreCharge.java] Elemento: country="+country +", year="+year+", inversion="+inversion);
		}
		reader.close();
		resp.sendRedirect("/exito.html");
	}
}