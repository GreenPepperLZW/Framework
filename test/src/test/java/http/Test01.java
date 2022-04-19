package http;

import cn.hutool.http.HttpUtil;
import org.junit.Test;

/**
 * @author : lzw
 * @date : 2022/4/18
 * @since : 1.0
 */
public class Test01 {

    @Test
    public void testHutoolPost() {

        String str = "Uq88D4F9ITJe1yfhv0UWg1dUNZS/OadzEdbinS3ZBDNWcdsrjY+mVcfAuucSO/omhWfIasM5yKst6SX22vY+6AuMNGL8R7Peqbq7GS3fcGtPEZqSvmffjyg5wzt/S8n+1ZxYsJTZ4OG5paRYg75cx/hCPV/Xbd0yXEU9iOFUFBoULTqy9Cq4AHWEl9wkJGwPEdoWDZYZXhwwT5bP76hQyp5HVWT2XKZXvJWH9Hd4uUcxA7VqR0dhII7DJCJ8Cwk9S60hX2AVNVGrPQ0tlDHskZgJ+Fi7pH3nl5kE3f0+SFLN/30sc3y/WOIgOdLASyxRi7g/vtkUZ80oIPMzJ/V1GLkYhi69VlABp1YnLjaZCs0CdN8doRJZBUnmGvfSBnJ3YexKXyNXkf9QnC8FTSazhw==";
        String url ="http://localhost:16666/web/creditNote"+"?str="+str;


        String body = HttpUtil.createGet(url).execute().body();



        System.out.println(body);
    }



}
