package pl.nullpointerexception.shop.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.nullpointerexception.shop.admin.controller.dto.AdminProductDTO;
import pl.nullpointerexception.shop.admin.model.AdminProduct;
import pl.nullpointerexception.shop.admin.service.AdminProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    public static final Long EMPTY_ID = null;
    private final AdminProductService productService;

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

    private static AdminProduct mapAdminProduct(AdminProductDTO adminProductDTO, Long id) {
        return AdminProduct.builder()
                .id(id)
                .name(adminProductDTO.getName())
                .category(adminProductDTO.getCategory())
                .description(adminProductDTO.getDescription())
                .price(adminProductDTO.getPrice())
                .currency(adminProductDTO.getCurrency())
                .build();
    }

}
