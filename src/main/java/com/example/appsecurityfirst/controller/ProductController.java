package com.example.appsecurityfirst.controller;


import com.example.appsecurityfirst.entity.Product;
import com.example.appsecurityfirst.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    // 6. ISH

    // MANAGER, DIRECTOR
//    @PreAuthorize(value = "hasAnyRole('MANAGER','DIRECTOR')")
      @PreAuthorize(value = "hasAuthority('READ_ALL_PRODUCT')")
    @GetMapping
    public List<Product> get(){
        List<Product> all = productRepository.findAll();
        return all;
    }

   // DIRECTOR
//    @PreAuthorize(value = "hasAnyRole('MANAGER','DIRECTOR','USER')")
   @PreAuthorize(value = "hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Optional<Product> byId = productRepository.findById(id);
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (byId.isPresent()){
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.notFound().build();
    }

    // DIRECTOR
//    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody Product product){
        return  ResponseEntity.ok(productRepository.save(product));
    }

   // DIRECTOR
//   @PreAuthorize(value = "hasRole('DIRECTOR')")
   @PreAuthorize(value = "hasAuthority('EDIT_PRODUCT')")
    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable Integer id, @RequestBody Product product){
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()){
            Product product1 = byId.get();
            product1.setName(product.getName());
            productRepository.save(product1);
            return ResponseEntity.ok(product1);
        }
        return ResponseEntity.notFound().build();
    }

    //DIRECTOR
//    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id){
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
