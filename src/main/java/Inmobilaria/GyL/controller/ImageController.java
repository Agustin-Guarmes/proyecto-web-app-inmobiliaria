package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.service.UserService;
import Inmobilaria.GyL.service.impl.ImagePropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private UserService userService;
    @Autowired
    private ImagePropertyService ips;

    @GetMapping("/profile/{id}")
    public ResponseEntity<byte[]> imageUser(@PathVariable Long id) {
        User user = userService.getOne(id);
        byte[] imgUser = user.getIcon().getContainer();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imgUser, headers, HttpStatus.OK);
    }

    @GetMapping("/propiedades/{id}")
    public ResponseEntity<byte[]> imageProperty(@PathVariable String id) {
        byte[] imgProperty = ips.imgToBite(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imgProperty, headers, HttpStatus.OK);
    }
}
