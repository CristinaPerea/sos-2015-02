package us.es.sos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.opencsv.CSVReader;

import dominio.CoResource;

@SuppressWarnings("serial")
public class UploadCSVCo extends HttpServlet {
	// Sólo atiende peticiones POST para subir archivos
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("[UploadCSVCo.java] Entrando en el método doPost()");
		System.out.println("[UploadCSVCo.java] Entro en el try");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> fields = upload.parseRequest(req);
			System.out.println("Number of fields: " + fields.size());
			Iterator<FileItem> it = fields.iterator();
			if (!it.hasNext()) {
				System.out.println("No fields found");
				return;
			}
			while (it.hasNext()) {
				FileItem fileItem = it.next();
				boolean isFormField = fileItem.isFormField();
				System.out.println("<td>file form field</td><td>FIELD NAME: " + fileItem.getFieldName() +
									"<br/>STRING: " + fileItem.getString() +
									"<br/>NAME: " + fileItem.getName() +
									"<br/>CONTENT TYPE: " + fileItem.getContentType() +
									"<br/>SIZE (BYTES): " + fileItem.getSize() +
									"<br/>TO STRING: " + fileItem.toString()
									);
			String [] datos = fileItem.getString().split("\n");
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			for(int i=1; i <datos.length; i++) {
				System.out.println(datos[i]);
				String [] campos = datos[i].split(",");
				Entity e = new Entity("CoResource");
				e.setProperty("country", campos[0].replaceAll("\"", ""));
				e.setProperty("year", Integer.parseInt(campos[1].replaceAll("\"", "")));
				e.setProperty("quantity", Float.parseFloat(campos[2].replaceAll("\"", "")));
				datastore.put(e);
			}
			
			
				
			}
		} catch (FileUploadException e) {
					e.printStackTrace();
		}
	}
}