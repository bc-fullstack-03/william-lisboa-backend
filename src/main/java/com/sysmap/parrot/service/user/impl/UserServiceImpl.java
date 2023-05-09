package com.sysmap.parrot.service.user.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.sysmap.parrot.dto.authentication.RegisterRequest;
import com.sysmap.parrot.dto.user.*;
import com.sysmap.parrot.exception.user.*;
import com.sysmap.parrot.s3.AwsS3Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.sysmap.parrot.model.entity.User;
import com.sysmap.parrot.repository.UserRepository;
import com.sysmap.parrot.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final AwsS3Service awsS3Service;

    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    @Override
    public String create(RegisterRequest request) {
        invalidUserByEmailOrUsername(request.email,request.username);

        String hashPassword = passwordEncoder.encode(request.getPassword()) ;
        request.setPassword(hashPassword);
        User user = modelMapper.map(request, User.class);
        user.setId(UUID.randomUUID().toString());

        User newUser = userRepository.save(user);
        return newUser.getId();
    }

    @Override
    public List<GetUserResponse> listAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, GetUserResponse.class))
                .toList();
    }

    @Override
    public void updateUser(String id, UpdateUser updateUser){
        var user = getUserById(id);

        invalidUserByEmailOrUsername(updateUser.email,updateUser.username);

        modelMapper.map(updateUser,user);
        userRepository.save(user);
    }

    @Override
    public GetUserResponse getUser(String userId) {
        var response = modelMapper.map(getUserById(userId), GetUserResponse.class);
        return response;
    }

    @Override
    public String updateProfileImage(MultipartFile file, String userId) {
        String filepath = awsS3Service.uploadUserPictureProfile(file, userId);

        var user = getUserById(userId);
        user.setProfileImage(filepath);

        userRepository.save(user);
        return filepath;
    }

    @Override
    public void deleteProfileImage(String userId){
        var user = getUserById(userId);
        user.setProfileImage("");

        userRepository.save(user);
    }

    @Override
    public S3Object getUserProfileImage(String url) {
        return awsS3Service.downloadImage(url);
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public void deleteUser(String id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void startFollowing(String userId, String followId) {
        var user = getUserById(userId);
        var anotherUser = getUserById(followId);

        List<String> userFollowing = user.getFollowing();
        List<String> anotherUserFollowers = anotherUser.getFollowers();

        if (anotherUserFollowers.contains(userId)){
            throw new FollowException("you already follow this person");
        }

        if (anotherUserFollowers.add(userId)){
            anotherUser.setFollowers(anotherUserFollowers);
            userRepository.save(anotherUser);
        } else {
            throw new UnableFollowUserException();
        }

        if (userFollowing.add(followId)){
            user.setFollowing(userFollowing);
            userRepository.save(user);
        } else {
            throw new FollowException("was unable to add user to follower list.");
        }

    }

    @Override
    public void unfollow(String userId, String unfollowId) {
        var user = getUserById(userId);
        var followed = getUserById(unfollowId);

        List<String> userFollowing = user.getFollowing();
        List<String> followedFollowers = followed.getFollowers();

        if (!followedFollowers.contains(userId)){
            throw new UnFollowException("you don't follow this person");
        }

        if (followedFollowers.remove(user.getId())){
            followed.setFollowers(followedFollowers);
            userRepository.save(followed);
        } else {
            throw new UnFollowException("unable to remove follower");
        }

        if (userFollowing.remove(followed.getId())){
            user.setFollowing(userFollowing);
            userRepository.save(user);
        } else {
            throw new UnFollowException("couldn't stop following");
        }
    }

    @Override
    public void updateBiography(String userId, String biography) {
        var user = getUserById(userId);
        user.setBiography(biography);

        userRepository.save(user);
    }

    @Override
    public void redefinePassword(String userId, String password) {
        var user = getUserById(userId);

        String hashPassword = passwordEncoder.encode(password) ;
        user.setPassword(hashPassword);

        userRepository.save(user);
    }

    @Override
    public void redefineEmail(String userId, String email) {
        var user = getUserById(userId);
        user.setEmail(email);

        userRepository.save(user);
    }

    @Override
    public void redefineUsername(String userId, String username) {
        var user = getUserById(userId);
        user.setUsername(username);

        userRepository.save(user);
    }

    public User findByEmail(String email){
        Optional<User>user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new  UserNotFoundException());
    }

    public User findByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UserNotFoundException());
    }

    private boolean invalidUserByEmailOrUsername(String email, String username){
        var userByEmail = userRepository.findByEmail(email);
        var userByUsername = userRepository.findByUsername(username);

        if (userByEmail.isPresent()){
            throw new InvalidEmailException();
        }

        if (userByUsername.isPresent()){
            throw new InvalidUsernameException();
        }

        return true;
    }
}
