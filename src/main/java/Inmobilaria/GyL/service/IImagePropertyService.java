package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.ImageProperty;
import Inmobilaria.GyL.entity.Property;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImagePropertyService {
    void saveImg(MultipartFile file, Property property) throws IOException;

    ImageProperty updateImg(MultipartFile file, String id);

    ImageProperty findById(String id);

    byte[] imgToBite(String id);

    void deleteById(String id);
}
