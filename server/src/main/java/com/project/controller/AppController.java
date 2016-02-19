package com.project.controller;

import com.mongodb.*;
import com.project.Auth;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import sjsu.sensor.*;

import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by Team08 on 11/21/15.
 * Members:
 * This is the project's REST controller for accepting REST requests
 * and responding to them with JSON objects
 */
@CrossOrigin
@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping("/MobileSensorCloud/*")
public class AppController extends WebMvcConfigurerAdapter {

    /*
    * Reservation Check API*/

    /*

    A guest can search with the intended checkin and checkout dates,
     and the number of rooms wanted, smoking, nonsmoking, or do-not-care.
     */
    private static MongoClient mongoClient;
    private static DBCollection table;

    private static DB db;

    //@Autowired
    TemplateImplementation t;
    UserImplmentation u;
    Boolean authFlag = false;
    PhysicalSensorImplementation p;

    @RequestMapping(value="/user", method= RequestMethod.POST)
    public  @ResponseBody ResponseEntity authenticate(@RequestParam("user") String user,
                                             @RequestParam("pwd") String pwd){
        u = new UserImplmentation();
        AdminModel a = u.getUser(user);
//        System.out.println(a.toString());
        if(a.getPwd().equals(pwd)) {
            authFlag = true;
            System.out.println("Authenticated");
            return new ResponseEntity(new String[]{"Authenticated"}, HttpStatus.OK);

        }
        else{
            return  new ResponseEntity(new String[]{"NotAuhthenticated"},HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }

    }

    @RequestMapping(value="/adduser", method= RequestMethod.POST)
    public  @ResponseBody ResponseEntity<String> createuser(@RequestParam("user") String user,
                                                              @RequestParam("pwd") String pwd){

            AdminModel a = new AdminModel(user,pwd);
            u = new UserImplmentation();
            u.createUser(a);
            System.out.println(a.toString());
            return new ResponseEntity<String>("Created", HttpStatus.OK);
    }
    //add sensors
    @RequestMapping(value = "/addSensor",method = RequestMethod.POST)
    public @ResponseBody ResponseEntity addSensor(@RequestParam("location") String location,
                                                 @RequestParam("dataType") String dataType){
        //PhysicalSensorModel model = new PhysicalSensorModel();
        //BasicDBObject doc = new BasicDBObject();
        //doc.put(model.)
        UUID id = UUID.randomUUID();
        String sensor_id = String.valueOf(id);
        System.out.println(sensor_id);


        p=new PhysicalSensorImplementation();
        String result=p.createTemplate(sensor_id,location,dataType,"active");

        return new ResponseEntity(new String[]{result}, HttpStatus.OK);
    }

    //disable sensors
    @RequestMapping(value = "/disableSensor",method = RequestMethod.POST)
    public @ResponseBody ResponseEntity removeSensor(@RequestParam("sensor_id") String sensor_id,
                                                     @RequestParam("status") String status){
        p=new PhysicalSensorImplementation();
        String result=p.removeTemplate(sensor_id,status);
        return new ResponseEntity(new String[]{result}, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteSensor",method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity deleteSensor(@RequestParam("sensor_id") String sensor_id){
        p=new PhysicalSensorImplementation();
        String result=p.deleteSensor(sensor_id);
        return new ResponseEntity(new String[]{result}, HttpStatus.OK);
    }
    //delete application
    @RequestMapping(value = "/deleteApplication",method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity deleteApplication(@RequestParam("app_id") String app_id){
        p=new PhysicalSensorImplementation();
        String result=p.deleteApplication(app_id);
        return new ResponseEntity(new String[]{result}, HttpStatus.OK);
    }

    //disable application
    @RequestMapping(value = "/disableApplication",method = RequestMethod.POST)
    public @ResponseBody ResponseEntity disableApplication(@RequestParam("app_id") String app_id,
                                                     @RequestParam("status") String status){
        p=new PhysicalSensorImplementation();
        String result=p.removeApplication(app_id,status);
        return new ResponseEntity(new String[]{result}, HttpStatus.OK);
    }
    //get all application
    @RequestMapping(value = "/addApplication",method = RequestMethod.POST)
    public @ResponseBody ResponseEntity addApplication(@RequestParam("app_name") String app_name,
                                                       @RequestParam("template_name") String template_name){
        UUID id = UUID.randomUUID();
        String app_id = String.valueOf(id);
        p=new PhysicalSensorImplementation();
        String result=p.addApplication(app_name,template_name,app_id);
        return new ResponseEntity(new String[]{result}, HttpStatus.OK);
    }

    //all sensor data
    @RequestMapping(value = "/getAllSensor",method = RequestMethod.GET)
    public @ResponseBody ArrayList<HashMap<String,String>> getAllSensor(){
        p=new PhysicalSensorImplementation();
        ArrayList<HashMap<String,String>> result=p.allSensorRecords();
        return result;
    }

    //all application
    @RequestMapping(value = "/getAllApplication",method = RequestMethod.GET)
    public @ResponseBody ArrayList<HashMap<String,String>> getAllApplication(){
        p=new PhysicalSensorImplementation();
        ArrayList<HashMap<String,String>> result=p.allApplicationRecords();
        return result;
    }

    //fetch sensor data
    //all sensor data
    @RequestMapping(value = "/getSensorData",method = RequestMethod.POST)
    public @ResponseBody ArrayList<HashMap<String,String>> getSensorData(@RequestParam("app_id") String app_id){
        p=new PhysicalSensorImplementation();
        ArrayList<HashMap<String,String>> result=p.getSensorData(app_id);
        return result;
    }

    //new ResponseEntity(new String[]{"Data Found"},HttpStatus.OK)
    @RequestMapping(value = "/template",method = RequestMethod.POST)
    public @ResponseBody String saveTemplateData(@RequestParam("templateID") String templateId,
                                      @RequestParam("listOfData") String[] listOfData){

        t=new TemplateImplementation();
        t.createTemplate(templateId, new ArrayList<String>(Arrays.asList(listOfData)));

        return "Template Created!!";
    }

    @RequestMapping(value = "/template",method = RequestMethod.GET)
    public @ResponseBody
    TemplateModel findTemplate(@RequestParam("templateID") String templateId){
        t=new TemplateImplementation();

        return t.getTemplate(templateId);
    }

    @RequestMapping(value = "/template/allTemplates",method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<TemplateModel> getAllTemplate(){
        t=new TemplateImplementation();

        return (ArrayList<TemplateModel>)t.getAllTemplates();
    }

    @RequestMapping(value = "/template",method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<String> deleteTemplate(@RequestParam("templateID") String templateID){
        t=new TemplateImplementation();
        t.deleteTemplate(templateID);
        return new ResponseEntity<String>("Template Deleted",HttpStatus.OK);
    }

    @RequestMapping(value = "/template/modify",method = RequestMethod.POST)
    public @ResponseBody
    String modifyTemplate(@RequestParam("templateID") String templateID,
                          @RequestParam("listOfData") String[] listOfData){
        t=new TemplateImplementation();
        t.modifyTemplate(templateID, new ArrayList<String>(Arrays.asList(listOfData)));
        return "Template Modified";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody ResponseEntity login(@RequestBody Auth auth){

        ResponseEntity<Auth> s = new ResponseEntity<Auth>(auth, HttpStatus.OK);

        System.out.println("username : "+auth);
        return new ResponseEntity(new String[]{"Authenticated"},HttpStatus.OK);
    }


    @RequestMapping(value = "/parking", method = RequestMethod.GET)
    public @ResponseBody Response getParking(
            @RequestParam(value = "lat") String lattitude
            ,@RequestParam(value = "long") String longitude
            , @RequestParam(value = "range") String range) {


        System.out.println("parking: "+lattitude+" "+longitude+" "+range);

        // @GET
        //@RequestMapping(value = "/alllocations", method = RequestMethod.GET)


        setupMongoDb();

        //BasicDBObject searchQuery = new BasicDBObject();
        //searchQuery.put("value", "p");
  //      List<String> str = new ArrayList<String>();
        List<DBObject> obj = new ArrayList<DBObject>();

        DBCursor cursor = table.find();
        DBObject objtemp;
        System.out.println("I am here");


        while (cursor.hasNext()) {
            //System.out.println("I am in loop");
            objtemp = cursor.next();
            obj.add(objtemp);
            //System.out.println("I am in loop end");

        }
//        BasicDBObject query = new BasicDBObject();
//        BasicDBObject field = new BasicDBObject();
//        field.put("location", 1);
//        DBCursor cursor = table.find(query,field);
//        while (cursor.hasNext()) {
//            BasicDBObject object = (BasicDBObject) cursor.next();
//            str.add(object.getString("location"));
//        }
        ArrayList<HashMap<String, String>> resultList = new ArrayList<>();
        System.out.println("Print List ::" + Arrays.toString(obj.toArray()));
        for(DBObject db : obj) {
            BasicDBObject bdb = (BasicDBObject) db;
            System.out.println(bdb.get("location"));
            String[] resultStringArray = ((String) bdb.get("location")).split(",");
            // String latitudeFetched = resultStringArray[0];
            //String longitudeFetched = resultStringArray[1];
            Double latitudeFetched = Double.parseDouble(resultStringArray[0]); //lat2
            Double longitudeFetched = Double.parseDouble(resultStringArray[1]); //long2

            Double latitudeReceived = Double.parseDouble(lattitude); //lat1
            Double longitudeReceived = Double.parseDouble(longitude); // long1

            System.out.println("latitudeReceived: " + latitudeReceived); //lat1
            System.out.println("longitudeReceived: " + longitudeReceived); //long1

            Double rangeReceived = Double.parseDouble(range);

            // System.out.println("latitudeFetched: "+latitudeFetched); //lat2
            //System.out.println("longitudeFetched: "+longitudeFetched); //long 2
            Double distance = distance(latitudeReceived, longitudeReceived, latitudeFetched, longitudeFetched);

            System.out.println(distance + " Miles\n");
            if (distance <= rangeReceived) {

                HashMap<String, String> hm = new HashMap<>();
                hm.put("Latitude", resultStringArray[0]);
                hm.put("Longitude", resultStringArray[1]);
                resultList.add(hm);
            }
        }
        return Response.ok(resultList).build();
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }
//This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
//This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public void setupMongoDb() {
        try {
            MongoClientURI uri = new MongoClientURI("mongodb://user:passwd@ds055594.mongolab.com:55594/virsendb");
            mongoClient = new MongoClient(uri);

            db = mongoClient.getDB("virsendb");
            System.out.println("Connect to database successfully");
            table = db.getCollection("sensorData");
            //System.out.println(table);

        } catch (Exception e) {
            System.out.println("Connect to URL failed");
            e.printStackTrace();
        }

        // Now connect to databases

    }



}


