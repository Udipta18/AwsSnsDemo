package com.awssnslambda.AwsSnsLambdaDemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.awssnslambda.AwsSnsLambdaDemo.model.Employee;
import com.awssnslambda.AwsSnsLambdaDemo.model.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class AwsSnsLambdaApplication implements RequestHandler<SNSEvent, Response>{
	
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	
	public Response handleRequest(SNSEvent event, Context context) {
		
		LambdaLogger logger = context.getLogger();
		
	    
	        try{
	            Optional<SNSEvent.SNSRecord> snsRecord = event.getRecords().stream().findAny();
	            if(snsRecord.isPresent()){
	                final SNSEvent.SNS sns = snsRecord.get().getSNS();
	                final String jsonMessage = sns.getMessage();
	                final Employee employee = gson.fromJson(jsonMessage, Employee.class);
	                logger.log("Employee Object:{},"+employee.toString());
	                
	                
	                URL getUrl = new URL("https://jsonplaceholder.typicode.com/posts/10");
	                
	                HttpURLConnection conection = (HttpURLConnection) getUrl.openConnection();
	                conection.setRequestMethod("GET");
	                int responseCode = conection.getResponseCode();
	                if (responseCode == HttpURLConnection.HTTP_OK) {
	                    BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
	                    StringBuffer jsonResponseData = new StringBuffer();
	                    String readLine = null;
	                    
	                    // Append response line by line
	                    while ((readLine = in.readLine()) != null) {
	                        jsonResponseData.append(readLine);
	                    } 
	                    
	                    in.close();
	                    // Print result in string format
	                    logger.log("JSON String Data " + jsonResponseData.toString());
	                }
	            }
	            return Response.builder()
	                    .httpCode(HttpURLConnection.HTTP_OK)
	                    .message("OK.")
	                    .build();
	        } catch (Exception e){
	            return Response.builder()
	                    .httpCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
	                    .message("Something went wrong.")
	                    .build();
	        }

	    }
	}
