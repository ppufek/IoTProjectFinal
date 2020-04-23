package iot.project.IoTProject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class Controller {

    private RestTemplate restTemplate;
    @Value("${service.url}")
    private String BASE_URL;

    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/measurements")
    public HttpEntity<String> getMeasurements(@RequestHeader("username") String username, @RequestHeader("password") String password) {
        HttpHeaders headers = null;
        Object response = null;
        if (this.validateCredentials(username, password)) {
            headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            headers.set("X-Frame-Options", "DENY");
            response = this.getHttpResponse(headers);
            if ((response instanceof ResponseEntity) && (((ResponseEntity) response).getStatusCodeValue() != 200)) {
                return null;
            }
        }
        if(headers == null) {
            System.out.println("Headers not provided!");
        }
        if(response == null) {
            System.out.println("Server did not return any response!");
        }
        return (HttpEntity<String>) response;

    }

    private HttpEntity<String> getHttpResponse(HttpHeaders headers) {
        try {
            ResponseEntity<String> response = this.restTemplate.exchange(BASE_URL + "/measurement/measurements", HttpMethod.GET, new HttpEntity<>(headers), String.class);
            if (response.getStatusCodeValue() == 200) {

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("X-Frame-Options",
                        "DENY");

                return ResponseEntity.ok()
                        .headers(responseHeaders)
                        .body(response.getBody());
            }
        } catch (HttpClientErrorException e) {
            System.out.println("401 Unauthorized -  lacking valid authentication credentials");
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/measurements-hardcoded")
    public HttpEntity<String> getData() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("t5185958/iot1@croatia.rit.edu", "iotrit123");
        return getHttpResponse(headers);
    }

    private boolean validateCredentials(String username, String password) {
        String[] usernameArray = username.split("/");

        for (String credential : usernameArray) {
            if (!(credential.replace("@", "").replace(".", "").matches("^[a-zA-Z0-9]*$"))) {
                return false;
            }
        }

        if (password != null && password.matches("^[a-zA-Z0-9]*$")) {

            System.out.println("Credentials Valid!");
            return true;
        }
        return false;
    }

}
