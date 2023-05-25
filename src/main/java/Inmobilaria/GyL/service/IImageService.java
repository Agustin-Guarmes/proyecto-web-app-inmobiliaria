package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.ImageUser;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    ImageUser submitImg(MultipartFile archive) throws Exception;

    ImageUser updateImg(MultipartFile archive, String idImage);
}
