package com.sysmap.parrot.service.user;

import com.amazonaws.services.s3.model.S3Object;
import com.sysmap.parrot.dto.authentication.RegisterRequest;
import com.sysmap.parrot.dto.user.GetUserResponse;
import com.sysmap.parrot.dto.user.UpdateUser;
import com.sysmap.parrot.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    String create(RegisterRequest request);
    List<GetUserResponse> listAllUsers();

    User getUserById(String userId);

    GetUserResponse getUser(String userId);

    String updateProfileImage(MultipartFile file,String user);

    S3Object getUserProfileImage(String url);

    void deleteProfileImage(String userId);

    void deleteUser(String userId);

    void startFollowing(String userId, String followId);

    void unfollow(String userId, String unfollowId);

    void updateBiography(String userId, String biography);

    void redefinePassword(String userId, String password);

    void redefineEmail(String userId, String email);

    void redefineUsername(String userId, String username);

    void updateUser(String userId, UpdateUser user);
}
