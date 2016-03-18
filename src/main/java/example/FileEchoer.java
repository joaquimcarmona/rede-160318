package example;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.List;
import java.util.Map;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.googlecode.objectify.ObjectifyService;


public class FileEchoer extends HttpServlet {
  
 	 private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    static { ObjectifyService.register(Str.class); }
  
  
  	 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        	if (ServletFileUpload.isMultipartContent(request)) {
           	Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
       	 	List<BlobKey> blobKeys = blobs.get("file");
            BlobKey blobKey = blobKeys.get(0);
            BlobstoreInputStream blobStream = new BlobstoreInputStream(blobKey);
            InputStreamReader stream = new InputStreamReader(blobStream);
            BufferedReader reader = new BufferedReader(stream);

        		if (blobKeys == null || blobKeys.isEmpty()) {
            	response.sendRedirect("/");
            } else {
              	String line;
               String[] s;
               Double total = 0.0;
               Boolean ok = true;
              	
              	while ((line = reader.readLine()) != null & ok) {
						s = line.split(";");
                  try {
                  	Str stretch = new Str();
                     stretch.setStretchNumber(Integer.parseInt(s[0].replace(" ","")));
                    	stretch.setStationA(s[1]);
                    	stretch.setStationB(s[2]);
                    	stretch.setDistance(Float.parseFloat(s[3]));
                    	total += Float.parseFloat(s[3]);
                    	stretch.setNextABFirst(Integer.parseInt(s[4].replace(" ","")));
                     stretch.setNextABSecond(Integer.parseInt(s[5].replace(" ","")));
                    	stretch.setNextABThird(Integer.parseInt(s[6].replace(" ","")));
                    	stretch.setNextBAFirst(Integer.parseInt(s[7].replace(" ","")));
							stretch.setNextBASecond(0);
                    	stretch.setNextBAThird(0); 
                    	response.setContentType("text/plain");
      					response.getWriter().println(line);
                     ObjectifyService.ofy().save().entity(stretch).now(); 
                  } catch (Exception e)  {
                    	ok = false;
                  	response.setContentType("text/plain");
      					response.getWriter().println(e.toString() + "\n"); 
                  }
                  if (!ok) {
                  	response.setContentType("text/plain");
      					response.getWriter().println("Improper file. \n" + line); 
                  }
					}
               response.setContentType("text/plain");
      			response.getWriter().println(total);
               blobstoreService.delete(blobKey);
            }
   	 }
	}
}