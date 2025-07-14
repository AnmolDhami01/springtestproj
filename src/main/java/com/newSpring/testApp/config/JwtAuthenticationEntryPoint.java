package com.newSpring.testApp.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // System.out.println("IIIIIIIII"+request);
        // response.sendError(401, "unauthorized");

        String statusCode = String.valueOf(request.getAttribute("statusCode"));
        String statusMessage = String.valueOf(request.getAttribute("statusMessage"));
        final Map<String, Object> body = new HashMap<>();
        final ObjectMapper mapper = new ObjectMapper();

        if (statusCode.equals("null")) {
            ObjectNode status = mapper.createObjectNode();
            status.put("statusCode", 401);
            status.put("statusMessage", "unauthorized");

            body.put("statusDescription", status);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getOutputStream(), body);

        } else {
            ObjectNode status = mapper.createObjectNode();
            status.put("statusCode", statusCode);
            status.put("statusMessage", statusMessage);

            body.put("statusDescription", status);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getOutputStream(), body);
        }
    }

}
