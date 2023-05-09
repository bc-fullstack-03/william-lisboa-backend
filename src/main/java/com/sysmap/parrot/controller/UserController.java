package com.sysmap.parrot.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.sysmap.parrot.dto.user.*;
import com.sysmap.parrot.exception.user.ShowImageException;
import com.sysmap.parrot.service.user.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        var users = userService.listAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUser(@Valid @PathVariable String id) {
        var user = userService.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> getPost(
            @Valid @PathVariable String id, @Valid @RequestBody UpdateUser user){
        userService.updateUser(id,user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/profile/picture")
    public ResponseEntity<String> updateProfileImage(
            @RequestParam("file") MultipartFile file,
            @Valid @PathVariable String id ){

        var response = userService.updateProfileImage(file,id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/profile/picture/{filepath}")
    public ResponseEntity<byte[]> getProfileImage(
            @Valid @PathVariable String id,
            @Valid @PathVariable String filepath)  {

        try {
            S3Object s3Object = userService.getUserProfileImage(filepath);
            String contentType = s3Object.getObjectMetadata().getContentType();
            var bytes = s3Object.getObjectContent().readAllBytes();

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.valueOf(contentType));
            header.setContentLength(bytes.length);

            return ResponseEntity.ok().headers(header).body(bytes);
        }catch (IOException e){
            throw new ShowImageException();
        }
    }

    @DeleteMapping("/{id}/profile/picture")
    public ResponseEntity getProfileImage(@Valid @PathVariable String id) {
        userService.deleteProfileImage(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@Valid @PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/follow/{id}")
    public ResponseEntity<String> startFollowing(
            @PathVariable String id,
            HttpServletRequest request) {

        String userId = request.getHeader("userId");
        userService.startFollowing(userId, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/unfollow/{id}")
    public ResponseEntity<String> unfollow(
            @PathVariable String id,
            HttpServletRequest request) {

        String userId = request.getHeader("userId");
        userService.unfollow(userId, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/biography")
    public ResponseEntity<String> updateBiography(
            @PathVariable String id, @Valid @RequestBody UpdateBiography biography ){

        userService.updateBiography(id,biography.getBiography());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<String> redefinePassword(
            @PathVariable String id, @Valid @RequestBody UpdatePassword password){

        userService.redefinePassword(id, password.getPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<String> redefineEmail(
            @PathVariable String id, @Valid @RequestBody UpdateEmail email){

        userService.redefineEmail(id, email.getEmail());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/username")
    public ResponseEntity<String> redefineUsername(
            @PathVariable String id, @Valid @RequestBody UpdateUsername username){

        userService.redefineUsername(id, username.getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
