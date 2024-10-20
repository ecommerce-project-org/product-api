package com.ecom.product_api.api;

import com.ecom.product_api.dto.request.RequestProductDto;
import com.ecom.product_api.dto.response.ResponseProductDto;
import com.ecom.product_api.service.ProductService;
import com.ecom.product_api.util.StandardResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products-service/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse> createProduct(@RequestParam("data") String data,
                                                          @RequestParam("image") MultipartFile image
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestProductDto dto = objectMapper.readValue(data,RequestProductDto.class);
        productService.createProduct(dto,image);
        return new ResponseEntity<>(
                new StandardResponse(201,null,"Product Saved..."),
                HttpStatus.CREATED
                );
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<StandardResponse> updateProduct(@RequestBody RequestProductDto dto,
                                                          @PathVariable String productId
    ){
        productService.updateProduct(dto,productId);
        return new ResponseEntity<>(
                new StandardResponse(201,null,"Product Updated..."),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<StandardResponse> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(
                new StandardResponse(204,null,"Product Deleted..."),
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("/find-by-id/{productId}")
    public ResponseEntity<StandardResponse> findByProductId(@PathVariable String productId){
        return new ResponseEntity<>(
                new StandardResponse(200,productService.findProductById(productId),"Product Details..."),
                HttpStatus.OK
        );
    }

    @GetMapping("/search-products")
    public ResponseEntity<StandardResponse> searchAllProducts(
            @RequestParam("searchText") String searchText,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return new ResponseEntity<>(
                new StandardResponse(200,productService.searchAllProduct(searchText,page,size)
                        ,"Product Details..."),
                HttpStatus.OK
        );
    }

    @PutMapping("/update-image/{imageId}")
    public ResponseEntity<StandardResponse> updateImage(
            @PathVariable String imageId,
            @RequestParam("image") MultipartFile file
    ){
        productService.updateImage(imageId,file);
        return new ResponseEntity<>(
                new StandardResponse(201,null,"Product Image Updated..."),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete-image-by-id/{imageId}")
    public ResponseEntity<StandardResponse> deleteImage(
            @PathVariable String imageId
    ){
        productService.deleteImage(imageId);
        return new ResponseEntity<>(
                new StandardResponse(204,null,"Product Image Deleted..."),
                HttpStatus.NO_CONTENT
        );
    }
}
