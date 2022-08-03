package com.example.friendship;

import okhttp3.*;

import java.io.IOException;


public class route {

     public boolean myMethod() {
             OkHttpClient client = new OkHttpClient();

             MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"reqCoordType\":\"WGS84GEO\",\"resCoordType\":\"WGS84GEO\"," +
                     "\"startName\":\"%EC%B6%9C%EB%B0%9C\",\"startX\":\"126.966484\",\"startY\":\"37.565453\",\"startTime\":\"201709121938\",\"" +
                     "endName\":\"%EB%8F%84%EC%B0%A9\",\"endX\":\"127.008112\",\"endY\":\"37.565657\",\"searchOption\":\"0\",\"carType\":\"4\",\"" +
                     "coordinateFlag\":\"0\",\"truckType\":\"1\",\"truckWidth\":\"250\",\"truckHeight\":\"340\",\"truckWeight\":\"35500\",\"truckTotalWeight" +
                     "\":\"26000\",\"truckLength\":\"880\"}");
             System.out.println("body : " + body);

             Request request = new Request.Builder()
                     .url("https://apis.openapi.sk.com/tmap/routes/routeOptimization10")
                     .addHeader("Accept", "application/json")
                     .addHeader("Content-Type", "application/json")
                     .addHeader("appKey", "l7xx1317e6cad24d4f0d8048aa7336e5623b")
                     .post(body)
                     .build();

         System.out.println("request.method = " + request);
             try {
                 Response response = client.newCall(request).execute();
                 response.close();
                 System.out.println("response = " + response.body().toString());
                 if (response.isSuccessful()) {
                     System.out.println("성공입니다.");
                 } else {
                     System.out.println("실패입니다.");
                 }
                 return true;

             } catch (IOException e) {
                 throw new RuntimeException(e);
             }


         }

     }

