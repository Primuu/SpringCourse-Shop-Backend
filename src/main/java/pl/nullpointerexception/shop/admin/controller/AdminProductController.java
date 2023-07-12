package pl.nullpointerexception.shop.admin.controller;

import com.github.slugify.Slugify;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.nullpointerexception.shop.admin.controller.dto.AdminProductDTO;
import pl.nullpointerexception.shop.admin.controller.dto.UploadResponse;
import pl.nullpointerexception.shop.admin.model.AdminProduct;
import pl.nullpointerexception.shop.admin.service.AdminProductImageService;
import pl.nullpointerexception.shop.admin.service.AdminProductService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    public static final Long EMPTY_ID = null;
    private final AdminProductService productService;
    private final AdminProductImageService productImageService;

    @GetMapping("")
    public Page<AdminProduct> getProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping("/{id}")
    public AdminProduct getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping("")
    public AdminProduct createProduct(@RequestBody @Valid AdminProductDTO adminProductDTO) {
        return productService.createProduct(mapAdminProduct(adminProductDTO, EMPTY_ID));
    }

    @PutMapping("/{id}")
    public AdminProduct updateProduct(@RequestBody @Valid AdminProductDTO adminProductDTO, @PathVariable Long id) {
        return productService.updateProduct(mapAdminProduct(adminProductDTO, id)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/upload-image")
    public UploadResponse uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        try(InputStream inputStream = multipartFile.getInputStream()) {
            String savedFileName = productImageService.uploadImage(multipartFile.getOriginalFilename(), inputStream);
            return new UploadResponse(savedFileName);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during image uploading.", e);
        }
    }

    @GetMapping("/productImage/{filename}")
    public ResponseEntity<Resource> serveFiles(@PathVariable String filename) throws IOException {
        Resource file = productImageService.serveFiles(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Path.of(filename)))
                .body(file);
    }

    private static AdminProduct mapAdminProduct(AdminProductDTO adminProductDTO, Long id) {
        return AdminProduct.builder()
                .id(id)
                .name(adminProductDTO.getName())
                .category(adminProductDTO.getCategory())
                .description(adminProductDTO.getDescription())
                .price(adminProductDTO.getPrice())
                .currency(adminProductDTO.getCurrency())
                .image(adminProductDTO.getImage())
                .slug(slugifySlug(adminProductDTO.getSlug()))
                .build();
    }

    private static String slugifySlug(String slug) {
        Slugify slugify = new Slugify();
        return slugify.withCustomReplacement("_", "-")
                .slugify(slug);
    }

}
