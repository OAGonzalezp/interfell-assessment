package com.ionix.assessment.service.impl;

import com.ionix.assessment.dao.IUserDao;
import com.ionix.assessment.dto.request.UserRequest;
import com.ionix.assessment.dto.response.UserResponse;
import com.ionix.assessment.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    private final RestTemplate restTemplate;

    @Value("${externalUrl}")
    private String externalUrl;

    @Value("${secretKey}")
    private String secretKey;

    public UserServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Map externalSearch(String item) {

        // Create HttpHeaders and add any required headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-Key", "f2f719e0");

        // Create HttpEntity and pass the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the request with the specified headers
        ResponseEntity<Map> response = null;
        long startTime = System.currentTimeMillis();
        try {
            response = restTemplate.exchange(externalUrl + encrypt(item), HttpMethod.GET, entity, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        if (response.getStatusCode().is2xxSuccessful()) {
            Map result = new HashMap();
            result.put("responseCode", 0);
            result.put("description", "OK");
            result.put("elapsedTime", executionTime);
            Map result1 = new HashMap();
            Map it = (Map) response.getBody().get("result");
            List li = (List) it.get("items");
            result1.put("registerCount", li.size());

            result.put("result", result1);

            return result;
        } else {
            throw new RuntimeException("Failed to fetch data from the external API");
        }
    }

    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(secretKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Autowired
    IUserDao userDao;

    @Override
    public UserResponse save(UserRequest request) {
        return userDao.save(request);
    }

    @Override
    public UserResponse findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<UserResponse> findAll() {
        return userDao.findAll();
    }

    @Override
    public UserResponse deleteByEmail(String email) {
        return userDao.deleteByEmail(email);
    }
}
