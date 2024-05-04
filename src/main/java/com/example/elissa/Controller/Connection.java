package com.example.elissa.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**/
public class Connection {


    private StringBuffer response = new StringBuffer();

    public Connection(String cityName) throws IOException {
        URL url = new URL("http://api.weatherapi.com/v1/current.json?key=c786406a88464571810204524232301&q=" + cityName + "&aqi=no");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String readLine;
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
            }
        } else {
            throw new IOException("HTTP request failed with response code: " + responseCode);
        }
    }

    public String getCityNameApi() {
        try {
            int findNameOfCity = response.indexOf("city");
            int findStartCityName = response.indexOf(":", findNameOfCity) + 1;
            return response.substring(findStartCityName, response.indexOf("\"", findStartCityName));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public String getTemp_C_Api() {
        try {
            int findTemp = response.indexOf("temp_c");
            int findStartTemp = response.indexOf(":", findTemp) + 1;
            return response.substring(findStartTemp, response.indexOf(",", findStartTemp == -1 ? 0 : findStartTemp));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

  /*  protected String getCityNameApi() {
        int findNameOfCity = response.indexOf("name");
        int findStartCityName = response.indexOf(":",findNameOfCity)+2;
        return response.substring(findStartCityName,response.indexOf("\"",findStartCityName));
    }
*/

    //secend work

  /*protected String getCityNameApi() {
      try {
          int findNameOfCity = response.indexOf("city");
          int findStartCityName = response.indexOf(":", findNameOfCity) + 1; // Adjusted index
          return response.substring(findStartCityName, response.indexOf("\"", findStartCityName));
      } catch (Exception e) {
          e.printStackTrace();
          return "1";
      }
  }*/




    /*protected String getTemp_C_Api() {
        int findNameOfCity = response.indexOf("temp_c");
        int findStartCityName = response.indexOf(":", findNameOfCity) + 1;
        double round = Math.round(Double.parseDouble(response.substring(findStartCityName, response.indexOf(",", findStartCityName == -1 ? 0 : findStartCityName))));
        return String.valueOf(response.substring(findStartCityName, response.indexOf(",", findStartCityName)));
    }*/


  //secend work

   /* protected String getTemp_C_Api() {
        try {
            int findTemp = response.indexOf("temp_c");
            int findStartTemp = response.indexOf(":", findTemp) + 1; // Adjusted index
            return response.substring(findStartTemp, response.indexOf(",", findStartTemp == -1 ? 0 : findStartTemp));
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }*/





    public String getHumidityApi() {
        int findNameOfCity = response.indexOf("humidity");
        int findStartCityName = response.indexOf(":",findNameOfCity)+1;
        return response.substring(findStartCityName,response.indexOf(",",findStartCityName));
    }

    protected String getFeelsLikeC(){
        int findNameOfCity = response.indexOf("feelslike_c");
        int findStartCityName = response.indexOf(":",findNameOfCity)+1;
        double finalFL = Math.round(Double.parseDouble(response.substring(findStartCityName,response.indexOf(",",findStartCityName))));
        return String.valueOf(finalFL);
    }

    protected String getWindMph(){
        int findNameOfCity = response.indexOf("wind_mph");
        int findStartCityName = response.indexOf(":",findNameOfCity)+1;
        return response.substring(findStartCityName,response.indexOf(",",findStartCityName));
    }


    protected String getText() {
        int findNameOfCity = response.indexOf("text");
        int findStartCityName = response.indexOf(":",findNameOfCity)+2;
        return response.substring(findStartCityName,response.indexOf("\"",findStartCityName));
    }

    protected String getLocalTime() {
        try {
            int findNameOfCity1 = response.indexOf("localtime");
            int findNameOfCity = response.indexOf("localtime", findNameOfCity1 + 1);
            int findStartCityName = response.indexOf(":", findNameOfCity) + 2;
            String localTime = response.substring(findStartCityName, response.indexOf("\"", findStartCityName));
            response = new StringBuffer(localTime);


            return "Weather at " + localTime;
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }
}
