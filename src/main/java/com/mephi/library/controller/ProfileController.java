package com.mephi.library.controller;

import com.mephi.library.model.Comment;
import com.mephi.library.model.Role;
import com.mephi.library.model.User;
import com.mephi.library.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @GetMapping("/user/getUserData/{idUser}")
    public Map<String, Object> getUserData(@PathVariable Long idUser) {
        User user = userService.findUserById(idUser);
        List<Comment> comments = new ArrayList<Comment>();
        user.getComments().forEach(item -> {
            Comment comItem = new Comment();
            comItem.setBook(item.getBook());
            comItem.setText(item.getText());
            comItem.setDate(item.getDate());
            comments.add(comItem);
        });
        user.getBooks().forEach(item -> {
            item.setContent(null);
            item.setImage(null);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("comments", comments);
        return map;
    }
}
