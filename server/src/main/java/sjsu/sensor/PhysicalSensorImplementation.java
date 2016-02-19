package sjsu.sensor;

import com.mongodb.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by arpitkhare on 12/7/15.
 */
public class PhysicalSensorImplementation implements PhysicalSensor {


    //private PhysicalSensorModel sensor = new PhysicalSensorModel();
    private static MongoClient mongoClient;
    private static DBCollection table;
    private static DBCollection templateTable;

    private static DB db;


    @Override
    public String createTemplate(String sensor_id, String location, String dataType, String activeStatus) {
//        sensor = new PhysicalSensorModel();
//        sensor.setSensor_id(sensor_id);
//        sensor.setLocation(location);
//        sensor.setDataType(dataType);
//        sensor.setActiveStatus(activeStatus);
//        sensor.setDataScale(dataScale);

        try{
            setupMongoDb();
            table = db.getCollection("physicalSensorList");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            BasicDBObject dbObject = new BasicDBObject();
            dbObject.put("sensor_id",sensor_id);
            dbObject.put("location",location);
            dbObject.put("dataType",dataType);
            dbObject.put("value","0");
            dbObject.put("status",activeStatus);
            dbObject.put("lastAccessed",dateFormat.format(date));
            table.insert(dbObject);
            return "Sensor Added Successfully!";
        }catch (Exception e){
            return "Fail";
        }






    }

    @Override
    public String removeTemplate(String sensor_id,String status) {
        try{
            String statusChanged="";
            if(status.equalsIgnoreCase("active"))
                statusChanged = "inactive";
            if(status.equalsIgnoreCase("inactive"))
                statusChanged = "active";
            System.out.println(status);
            setupMongoDb();
            table = db.getCollection("physicalSensorList");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            BasicDBObject dbObject = new BasicDBObject();
            dbObject.append("$set",new BasicDBObject().append("status",statusChanged).append("lastAccessed",dateFormat.format(date)));
           // dbObject.append("$set",new BasicDBObject().append("lastAccessed",dateFormat.format(date)));

            BasicDBObject searchQuery = new BasicDBObject().append("sensor_id",sensor_id);
            table.update(searchQuery,dbObject);
            return "Successfully changed the status to "+statusChanged;
        }catch (Exception e){
            return "Fail";
        }
    }

    @Override
    public ArrayList<HashMap<String,String>> allSensorRecords() {
        ArrayList<HashMap<String,String>> sensorList = new ArrayList<HashMap<String,String>>();
        //HashMap<String,String> hm = new HashMap<>();

        try{
            setupMongoDb();
            table = db.getCollection("physicalSensorList");
            DBCursor cursor = table.find();

            DBObject objtemp;
            List<DBObject> obj = new ArrayList<DBObject>();

            while(cursor.hasNext()){

                objtemp = cursor.next();
                obj.add(objtemp);
            }

            for(DBObject db : obj) {
                BasicDBObject bdb = (BasicDBObject) db;
                System.out.println(bdb.get("sensor_id"));
                System.out.println(bdb.get("location"));
                System.out.println(bdb.get("dataType"));
                System.out.println(bdb.get("value"));
                System.out.println(bdb.get("status"));
                System.out.println(bdb.get("lastAccessed"));
                HashMap<String,String> hm = new HashMap<String, String>();

                hm.put("sensor_id",bdb.get("sensor_id").toString());
                hm.put("location",bdb.get("location").toString());
                hm.put("dataType",bdb.get("dataType").toString());
                hm.put("status",bdb.get("status").toString());
                hm.put("lastAccessed",bdb.get("lastAccessed").toString());
                hm.put("value",bdb.get("value").toString());

                sensorList.add(hm);

            }




            return sensorList;
        }catch (Exception e){
            return null;

        }
    }

    @Override
    public String deleteSensor(String sensor_id) {

        try{
            setupMongoDb();
            table = db.getCollection("physicalSensorList");

            BasicDBObject dbObject = new BasicDBObject();
            dbObject.put("sensor_id",sensor_id);
            table.remove(dbObject);
            return "Success";
        }catch (Exception e){
            return "Fail";
        }
    }
    @Override
    public String deleteApplication(String app_id) {

        try{
            setupMongoDb();
            table = db.getCollection("applicationList");

            BasicDBObject dbObject = new BasicDBObject();
            dbObject.put("app_id",app_id);
            table.remove(dbObject);
            return "Success";
        }catch (Exception e){
            return "Fail";
        }
    }

    @Override
    public ArrayList<HashMap<String, String>> getSensorData(String app_id) {
        String templateName= "";
        String strTemplateType="";
        try{
            setupMongoDb();
            table = db.getCollection("applicationList");

            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("app_id",app_id);

            DBCursor cursor = table.find(searchQuery);

            DBObject objtemp;
            List<DBObject> obj = new ArrayList<DBObject>();

            while(cursor.hasNext()){

                objtemp = cursor.next();
                obj.add(objtemp);
            }

            for(DBObject db : obj) {
                BasicDBObject bdb = (BasicDBObject) db;
                System.out.println("status: "+bdb.get("status"));

                if(bdb.get("status").toString().equalsIgnoreCase("active")){
                    System.out.println("status is active proceed");
                    templateName=bdb.get("template").toString();
                    System.out.println("template_name: "+templateName);

                }
              }
            System.out.println("templateName:: "+templateName);

            //
            table = db.getCollection("templateModel");

            BasicDBObject searchQuery1 = new BasicDBObject();
            searchQuery1.put("_id",templateName);

            DBCursor cursor1 = table.find(searchQuery1);

            DBObject objtemp1;
            List<DBObject> obj1 = new ArrayList<DBObject>();

            while(cursor1.hasNext()){

                objtemp1 = cursor1.next();
                obj1.add(objtemp1);
            }

            for(DBObject db : obj1) {
                BasicDBObject bdb = (BasicDBObject) db;
                System.out.println("sensorDataType: "+bdb.get("sensorDataType").toString());
                //List<String>  = new ArrayList<DBObject>();
              List<String> s = (List<String>) bdb.get("sensorDataType");
                strTemplateType=s.get(0);
                System.out.println("str: "+strTemplateType);

            }
            System.out.println("fetched template type: "+strTemplateType);

            table = db.getCollection("physicalSensorList");
            BasicDBObject searchQuery2 = new BasicDBObject();
            //searchQuery2.append("$and",new BasicDBObject().append("status","active").append("dataType",strTemplateType));

            List<BasicDBObject> tempList = new ArrayList<BasicDBObject>();
            tempList.add(new BasicDBObject("status","active"));
            tempList.add(new BasicDBObject("dataType",strTemplateType.toLowerCase()));
            searchQuery2.put("$and",tempList);
            //BasicDBObject searchQuery = new BasicDBObject().append("sensor_id",sensor_id);
            DBCursor cursor2 = table.find(searchQuery2);
            //
            DBObject objtemp2;
            List<DBObject> obj2 = new ArrayList<DBObject>();
            System.out.println("cursor size:"+cursor2.size());

            while(cursor2.hasNext()){

                System.out.println("dsfsdf");
                objtemp2 = cursor2.next();
                obj2.add(objtemp2);
            }

            for(DBObject db : obj2) {
                BasicDBObject bdb = (BasicDBObject) db;
                System.out.println("sensor_id: physical sensor list: "+bdb.get("sensor_id").toString());

                //List<String>  = new ArrayList<DBObject>();
//                List<String> s = (List<String>) bdb.get("sensorDataType");
//                strTemplateType=s.get(0);
//                System.out.println("str: "+strTemplateType);

            }
            //
            return null;
        }catch (Exception e){
            return null;
        }


    }

    @Override
    public String addApplication(String app_name, String template, String app_id) {

        try{
            setupMongoDb();
            table = db.getCollection("applicationList");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date));

            BasicDBObject dbObject = new BasicDBObject();
            dbObject.put("app_name",app_name);
            dbObject.put("template",template);
            dbObject.put("last_access_date",dateFormat.format(date));
            dbObject.put("status","active");
            dbObject.put("app_id",app_id);
            table.insert(dbObject);
            return app_id;
        }catch (Exception e){
            return "Fail";
        }
    }


    @Override
   public ArrayList<HashMap<String,String>> allApplicationRecords(){

        ArrayList<HashMap<String,String>> applicationList = new ArrayList<HashMap<String,String>>();
        //HashMap<String,String> hm = new HashMap<>();

        try{
            setupMongoDb();
            table = db.getCollection("applicationList");
            DBCursor cursor = table.find();

            DBObject objtemp;
            List<DBObject> obj = new ArrayList<DBObject>();

            while(cursor.hasNext()){

                objtemp = cursor.next();
                obj.add(objtemp);
            }

            for(DBObject db : obj) {
                BasicDBObject bdb = (BasicDBObject) db;
                System.out.println(bdb.get("app_name"));
                System.out.println(bdb.get("template"));
                System.out.println(bdb.get("status"));
                System.out.println(bdb.get("app_id"));
                System.out.println(bdb.get("last_access_date"));

                HashMap<String,String> hm = new HashMap<String, String>();

                hm.put("app_name",bdb.get("app_name").toString());
                hm.put("template",bdb.get("template").toString());
                hm.put("status",bdb.get("status").toString());
                hm.put("last_access_date",bdb.get("last_access_date").toString());
                hm.put("app_id",bdb.get("app_id").toString());


                applicationList.add(hm);

            }




            return applicationList;
        }catch (Exception e){
            return applicationList;

        }

    }

    @Override
    public String removeApplication(String app_id, String status) {
        try{
            String statusChanged="";
            if(status.equalsIgnoreCase("active"))
                statusChanged = "inactive";
            if(status.equalsIgnoreCase("inactive"))
                statusChanged = "active";
            System.out.println(status);
            setupMongoDb();
            table = db.getCollection("applicationList");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            BasicDBObject dbObject = new BasicDBObject();
            dbObject.append("$set",new BasicDBObject().append("status",statusChanged).append("last_access_date",dateFormat.format(date)));
            // dbObject.append("$set",new BasicDBObject().append("lastAccessed",dateFormat.format(date)));

            BasicDBObject searchQuery = new BasicDBObject().append("app_id",app_id);
            table.update(searchQuery,dbObject);
            return "Successfully changed the status of Application to "+statusChanged;
        }catch (Exception e){
            return "Fail";
        }
    }


    public void setupMongoDb() {
        try {
            MongoClientURI uri = new MongoClientURI("");//enter mongodb connection string
            mongoClient = new MongoClient(uri);

            db = mongoClient.getDB("virsendb");
            System.out.println("Connect to database successfully");

            //System.out.println(table);

        } catch (Exception e) {
            System.out.println("Connect to URL failed");
            e.printStackTrace();
        }

        // Now connect to databases

    }


}
