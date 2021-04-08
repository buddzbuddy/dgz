package com.webdatabase.dgz.controller;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SoapConsumer {


    public static String sendSoapRequest(String input) {

        StringBuffer response = new StringBuffer();
        try {
            String url = "http://address.darek.kg/ws/AddressApi";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
            String lang = input;
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://address.infocom.kg/ws/\">" +
                    "<soapenv:Header>" +
                    "<token>4f20b8e66f24052ba3abfc56f43248d5</token>" +
                    "</soapenv:Header>" +
                    "<soapenv:Body>" +
                    "<ws:getCountries>" +
                    "<lang>" + lang + "</lang>" +
                    "</ws:getCountries>" +
                    "</soapenv:Body>" +
                    "</soapenv:Envelope>";
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(xml);
            wr.flush();
            wr.close();
            String responseStatus = con.getResponseMessage();
            System.out.println(responseStatus);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("response:" + response.toString());

        } catch (Exception e) {
            System.out.println(e);
        }
        return response.toString();
    }
}
