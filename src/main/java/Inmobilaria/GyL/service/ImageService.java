package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.ImageUser;
import Inmobilaria.GyL.repository.ImageRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Autowired
    public ImageRepository imageRepository;

    public ImageUser submitImg(MultipartFile archive) throws Exception {
        if (archive != null) {
            try {
                ImageUser image = new ImageUser();
                image.setMime(archive.getContentType());
                image.setName(archive.getName());
                image.setContainer(archive.getBytes());

                return imageRepository.save(image);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public ImageUser updateImg(MultipartFile archive, String idImage) {
        if (archive != null) {
            try {
                ImageUser image = new ImageUser();

                if (idImage != null) {
                    Optional<ImageUser> response = imageRepository.findById(idImage);

                    if (response.isPresent()) {
                        image = response.get();
                    }
                }

                image.setMime(archive.getContentType());
                image.setName(archive.getName());
                image.setContainer(archive.getBytes());

                return imageRepository.save(image);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    
}
