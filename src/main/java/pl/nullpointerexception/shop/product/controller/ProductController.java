package pl.nullpointerexception.shop.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nullpointerexception.shop.product.model.Product;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    @GetMapping("")
    public List<Product> getProducts() {
        return List.of(
                new Product("Name 1", "Category 1", "Desc", new BigDecimal("8.99"), "PLN")
        );
    }

}
