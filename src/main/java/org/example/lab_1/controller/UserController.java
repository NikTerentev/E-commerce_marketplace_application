package org.example.lab_1.controller;

import lombok.RequiredArgsConstructor;
import org.example.lab_1.daos.UserDAO;
import org.example.lab_1.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class UserController {

    @MessageMapping("/user.connectUser")
    @SendTo("/user/public")
    public String connect(@Payload Map<String, String> payload) {
        String nickname = payload.get("nickName");
        UserDAO.connect(nickname);
        return nickname;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public String disconnect(@Payload Map<String, String> payload) {
        String nickname = payload.get("nickName");
        UserDAO.disconnect(nickname);
        return nickname;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return ResponseEntity.ok(UserDAO.findConnectedUsers());
    }

    @MessageMapping("/token")
    public void saveToken(
            @Payload Map<String, String> payload
            ) {
        System.out.println(payload);
        String nickname = payload.get("nickName");
        String token = payload.get("token");
        String action = payload.get("action");

        if (Objects.equals(action, "add")) {
            UserDAO.saveToken(nickname, token);
        } else if (Objects.equals(action, "delete")) {
            UserDAO.deleteToken(nickname);
        }
    }
}
