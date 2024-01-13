package expiration_reminder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DoRequest {
	
	public static String sendXMLPost(String url, String postData) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/xml");
        con.setConnectTimeout(9000);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postData);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = null;
        
        if(responseCode == 200) {
        	in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            
        }else if(responseCode == 400) {
        	in = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
        }

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        

        //print result
        return response.toString();
    }

	public static String sendJSONPost(String url, String postData) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(9000);
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postData);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = null;
        
        if(responseCode == 200) {
        	in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            
        }else if(responseCode == 400) {
        	in = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
        }

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        

        //print result
        return response.toString();
    }
}
