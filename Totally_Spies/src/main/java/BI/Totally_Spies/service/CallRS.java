package BI.Totally_Spies.service;

import BI.Totally_Spies.database.WrapperInfo;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class CallRS {

    public String addUserToRs(String username, String lastName, List<String> interest) {
        RestTemplate restTemplate = new RestTemplate();
        JSONObject personJsonObject = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        personJsonObject.put("firstName", username);
        personJsonObject.put("lastName", lastName);
        personJsonObject.put("interests", interest);
        final HttpEntity<String> entity = new HttpEntity<String>(personJsonObject.toString(), headers);
        ResponseEntity<String> res = restTemplate.exchange("http://localhost:8080/users", HttpMethod.POST, entity, String.class);
        return res.getBody();
    }

    public List<String> getUserInterest(String id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String tmp = id.replace("\"", "");
        String t = tmp.replace("\n", "");
        String url = "http://localhost:8080/user_info?user_id=" + t;
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<WrapperInfo> response = restTemplate.exchange(url, HttpMethod.GET, entity, WrapperInfo.class);

        return Objects.requireNonNull(response.getBody()).getInterest();
    }

    public List<WrapperInfo> getRS(String id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String tmp = id.replace("\"", "");
        String t = tmp.replace("\n", "");
        String url = "http://localhost:8080/matched_users?user_id=" + t;
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<WrapperInfo>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<WrapperInfo>>() {});
        return response.getBody();
    }

    public String saveNewInterestList(String id, List<String> interest, String username, String lastName) {
        RestTemplate restTemplate = new RestTemplate();
        JSONObject personJsonObject = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String tmp = id.replace("\"", "");
        String t = tmp.replace("\n", "");
        personJsonObject.put("userId", t);
        personJsonObject.put("firstName", username);
        personJsonObject.put("lastName", lastName);
        personJsonObject.put("interests", interest);

        String url = "http://localhost:8080/user_info";
        final HttpEntity<String> entity = new HttpEntity<String>(personJsonObject.toString(), headers);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return res.getBody();
    }
}
