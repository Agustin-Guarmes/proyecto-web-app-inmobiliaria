package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.ImageProperty;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.repository.ImagePropertyRepository;
import Inmobilaria.GyL.service.IImagePropertyService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImagePropertyService implements IImagePropertyService {

    private final ImagePropertyRepository ipr;

    public ImagePropertyService(ImagePropertyRepository ipr) {
        this.ipr = ipr;
    }

    @Override
    public void saveImg(MultipartFile file, Property property) throws IOException {
        if (!file.isEmpty()) {
            try {
                ImageProperty img = new ImageProperty();
                img.setMime(file.getContentType());
                img.setName(file.getName());
                img.setContainer(file.getBytes());
                img.setProperty(property);
                ipr.save(img);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public ImageProperty updateImg(MultipartFile file, String id) {
        if (!file.isEmpty()) {
            try {
                ImageProperty img = new ImageProperty();
                if (id != null) {
                    Optional<ImageProperty> response = ipr.findById(id);
                    if (response.isPresent()) img = response.get();
                }
                img.setMime(file.getContentType());
                img.setName(file.getName());
                img.setContainer(file.getBytes());
                return ipr.save(img);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public ImageProperty findById(String id) {
        return ipr.findById(id).get();
    }

    @Override
    public byte[] imgToBite(String id) {
        return ipr.findById(id).get().getContainer();
    }

    @Override
    public void deleteById(String id) {
        ipr.deleteById(id);
    }
}
