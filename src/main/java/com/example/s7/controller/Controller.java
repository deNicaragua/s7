package com.example.s7.controller;

import com.example.s7.model.Friend;
import com.example.s7.model.User;
import com.example.s7.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class Controller {
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @PostMapping("/registration/user")
    public @ResponseBody String registrationUser(@ModelAttribute("user") @Valid User user) {
        return service.createUser(user);
    }

    @GetMapping("/find-user")
    public ResponseEntity<Friend> findUser(@RequestBody Map<String, String> userName) {
        return new ResponseEntity<>(service.findUserByName(userName.get("user")), HttpStatus.OK);
    }

    @GetMapping("/get-all-friends")
    public @ResponseBody Iterable<Friend> findFriends(@RequestBody Map<String, String> userFriend) {
        return service.findFriends(userFriend.get("user"));
    }


    @PostMapping("/add-user-tofriends")
    public @ResponseBody String addUserToFriends(@RequestBody Map<String, String> userFriend) {
        return service.addUserToList(userFriend.get("user"), userFriend.get("friend"), userFriend.get("token"));
    }


    @DeleteMapping("/delete-friend")
    public ResponseEntity<String> deleteRecord(@RequestBody Map<String, String> userFriend){
        return new ResponseEntity<>(service.deleteRecord(userFriend.get("user"),
                userFriend.get("friend"),userFriend.get("token")), HttpStatus.OK);
    }
}
