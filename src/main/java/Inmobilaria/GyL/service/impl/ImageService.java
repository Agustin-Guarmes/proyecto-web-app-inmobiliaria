package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.ImageUser;
import Inmobilaria.GyL.repository.ImageRepository;
import Inmobilaria.GyL.service.IImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
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

    @Override
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
    @Override
    public ImageUser findById(String id){
        return imageRepository.findById(id).get();
    }

}
