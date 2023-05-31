package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.service.IImagePropertyService;
import Inmobilaria.GyL.service.impl.UserService;
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

    private final UserService userService;
    private final IImagePropertyService ips;

    public ImageController(UserService userService, IImagePropertyService ips) {
        this.userService = userService;
        this.ips = ips;
    }

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

    @GetMapping("/eliminar/idImg/{idImg}/idProp/{idProp}")
    public String deleteImage(@PathVariable("idImg") String idImg, @PathVariable("idProp") Long idProp){
        ips.deleteById(idImg);

        return "redirect:/propiedades/modificar/" + idProp;
    }
}
