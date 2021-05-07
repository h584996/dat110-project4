package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Hello world!
 *
 */
public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service
		
		accesslog = new AccessLog();
		accesscode  = new AccessCode();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {
			
		 	Gson gson = new Gson();
		 	
		 	return gson.toJson("IoT Access Control Device");
		});
		
		// TODO: implement the routes required for the access control service
		// as per the HTTP/REST operations describined in the project description
		
		
		// The POST for accesdevise/log/
		post("/accessdevice/log", (request, response) -> {
			
			Gson gson = new Gson();
			
			AccessMessage  Accessmsg = gson.fromJson(request.body(), AccessMessage.class);
			
			String msg = Accessmsg.getMessage();
			
			int id = accesslog.add(msg);
			
			AccessEntry entry = new AccessEntry(id,msg);
			
			return gson.toJson(entry);
			
			
		});
		
		//The GET for accessdevice/log/
		get("/accessdevice/log", (request, response) -> {
			
			return (accesslog.toJson());
			
		});
		
		//The GET for accessdevice/log/{id}
		get("/accessdevice/log/:id", (request, response) -> {
			
			Gson gson = new Gson();
			
			int id = Integer.parseInt(request.params(":id"));
			
			return gson.toJson(accesslog.get(id));
			
			
		});
		
		//The PUT for accessdevice/code
		put("/accessdevice/code", (request, response) -> {
			
			Gson gson= new Gson();
			
			AccessCode putAccessCode = gson.fromJson(request.body(), AccessCode.class);
			
			accesscode.setAccesscode(putAccessCode.getAccesscode());
			
			return gson.toJson(putAccessCode);
			
			
		});
		
		//The GET for accessdevice/code
		get("/accessdevice/code",(request, response) -> {
			
			Gson gson = new Gson();
			
			return gson.toJson(accesscode);
		});
		
		//The DELETE for /accessdevice/log/
		delete("/accessdevice/log", (request, response) -> {
			
			accesslog.clear();
			
			return (accesslog.toJson());
			
		});
    }
    
}
