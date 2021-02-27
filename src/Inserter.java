import org.apache.commons.codec.digest.DigestUtils;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;


public class Inserter {

    static final String TARGET_URL = "http://139.59.182.187:32575";

    public static void main(String[] args) {

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TARGET_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; rv:68.0) Gecko/20100101 Firefox/68.0")
                .header("Cookie", "PHPSESSID=dmf311786hffv7kj5u222p9me6")
                .GET()
                .build();
        
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("Wrong response " + e);
        }

        assert response != null;
        String siteContent = response.body();

        String sourceLine;
        String md5Line;

        sourceLine = siteContent.substring(167, 187);
        System.out.println("Received source line: " + sourceLine);

//  If you want to get the code using RegEx, use this code:
//        final String regex = "(<h3 align='center'>)([A-Za-z0-9]+)(</h3><center>)";//
//        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
//        Matcher matcher = pattern.matcher(siteContent);
//        while (matcher.find()) {
//            sourceLine = matcher.group(2);
//        }

        md5Line = DigestUtils.md5Hex(sourceLine);
        String bodyRequest = "hash=" + md5Line;
        System.out.println("Send MD5: " + bodyRequest);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; rv:68.0) Gecko/20100101 Firefox/68.0")
                .header("Cookie", "PHPSESSID=dmf311786hffv7kj5u222p9me6")
                .uri(URI.create(TARGET_URL))
                .POST(HttpRequest.BodyPublishers.ofString(bodyRequest))
                .build();

        try {
            response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("Wrong response " + e);
        }
        System.out.println("Getting the flag:");
        System.out.println(response.body());
    }
}
