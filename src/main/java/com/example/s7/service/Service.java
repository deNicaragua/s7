package com.example.s7.service;

import com.example.s7.model.Friend;
import com.example.s7.model.User;
import com.example.s7.model.UserFriends;
import com.example.s7.exception.UserAlreadyExistException;
import com.example.s7.repository.FriendsRepository;
import com.example.s7.repository.UserRepository;

import java.util.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;


@org.springframework.stereotype.Service
public class Service implements UserDetailsService {
    private final UserRepository userRepo;
    private final FriendsRepository friendRepo;

    public Service(UserRepository userRepo, FriendsRepository friendRepo) {
        this.userRepo = userRepo;
        this.friendRepo= friendRepo;

    }

    public Friend findUserByName(String userName) {
        return new Friend(userRepo.findByName(userName));
    }

    public Iterable<Friend> findFriends(String userName) {
        User user = userRepo.findByName(userName);
        List<Friend> friends = new ArrayList<>();
        userRepo.findFriendById(friendRepo.findIdAllFriends(user.getId()), userName).forEach(u->friends.add(new Friend(u)));
        return friends;

    }

    public String addUserToList(String user, String friend, String userToken) {
            Integer idUser = userRepo.findByName(user).getId();
            Integer idFriend = userRepo.findByName(friend).getId();
            try {
                UserFriends d = findRelations(user, friend);
                if (d == null) {
                    UserFriends newFriend = new UserFriends(idUser, idFriend, "request_first");
                    friendRepo.save(newFriend);
                    return "Friend request sent";
                } else {
                    return "Friend request has already been sent";
                }
            } catch (Exception ex) {
                return ex.getMessage();
            }
    }

    public String createUser(User user) {
        if (userRepo.existsByLogin(user.getLogin())) {
            return new UserAlreadyExistException("Account with that email address already exist: "
                    + user.getLogin()) + "";
        } else {
            //String encodingPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            try {
                userRepo.save(new User(user.getName(), user.getLogin(), user.getPassword()));
                return "Registration completed successfully";
            } catch (IllegalArgumentException ex) {
                return "Something wrong " + ex.getLocalizedMessage();
            }
        }
    }

    public String deleteRecord(String user, String friend, String userToken){
            try {
                UserFriends r = findRelations(user, friend);
                if (r != null) {
                    friendRepo.deleteById(r.getId());
                    return "Account was delete";
                } else {
                    return "Something wrong, try again";
                }
            } catch (NullPointerException ex) {
                return ex.getLocalizedMessage();
            }
    }

    private UserFriends findRelations(String user, String friend) {
        Integer idUser = userRepo.findByName(user).getId();
        Integer idFriend = userRepo.findByName(friend).getId();
        if (!Objects.equals(idUser, idFriend)) {
            UserFriends a = friendRepo.findByRequestIdAndAddressId(idUser, idFriend);
            UserFriends b = friendRepo.findByRequestIdAndAddressId(idFriend, idUser);
            return a == null ? b : a;
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = userRepo.findByLogin(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getLogin(), u.getPassword(), true,
                true, true, true, new HashSet<>());
    }
    public User getByLogin(String login) {
        return userRepo.findByLogin(login);
    }

}
