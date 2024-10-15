package com.Assignment.ecommerce_proj_Oct11.controller;

import com.Assignment.ecommerce_proj_Oct11.model.Product;
import com.Assignment.ecommerce_proj_Oct11.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;


//    @GetMapping("/")
//    public String greet(){
//        return "Hello Winner";
//    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>( service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product = service.getProductById(id);
        if(product!=null)
         return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(product,HttpStatus.NO_CONTENT);
    }


    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
        try {
            Product product1 = service.addProduct(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        }
        catch (Exception e){

            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product =service.getProductById(productId);
        byte[] imageFile = product.getImageDate();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);

    }


@PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){

    Product product1 = null;
    try {
        product1 = service.updateProduct(id,product,imageFile);
    } catch (IOException e) {
        return new ResponseEntity<>("Failed to updated",HttpStatus.BAD_REQUEST);
    }
    if(product1!=null)
            return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to updated",HttpStatus.BAD_REQUEST);
}


    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = service.getProductById(id);

       if (product!=null) {
           service.deleteProduct(id);
           return new ResponseEntity<>("Deleted the Product Successfully", HttpStatus.OK);
       }
        else
            return new ResponseEntity<>("Product Not Found for the given ID",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        System.out.println("Searching with "+keyword);
    List<Product> products = service.searchProducts(keyword);
    return new ResponseEntity<>(products,HttpStatus.OK);

    }

}
