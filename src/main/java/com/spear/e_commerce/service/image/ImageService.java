package com.spear.e_commerce.service.image;

import com.spear.e_commerce.dto.ImageDto;
import com.spear.e_commerce.exceptions.ResourceNotFoundException;
import com.spear.e_commerce.model.Image;
import com.spear.e_commerce.model.Product;
import com.spear.e_commerce.repository.ImageRepository.ImageRepository;
import com.spear.e_commerce.repository.productRepository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    public ImageService(ImageRepository imageRepository, ProductRepository productRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No image found with id " + id));
    }

    @Override
    public void deleteImageById(Long id) {
         imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
             throw new ResourceNotFoundException("Image with id " + id + " not found");
         });

    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        List<ImageDto> imageDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImageData(new SerialBlob(file.getBytes()));
                image.setProduct(product);


                Image savedImage = imageRepository.save(image);

                String buildDownloadUrl = "/api/v1/images/download";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                imageDtos.add(imageDto);
            }

            catch(IOException | SQLException e){
                throw new ResourceNotFoundException("Image not saved");

            }
        }

        return imageDtos;

    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {

        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImageData(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }
        catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }


    }
}
