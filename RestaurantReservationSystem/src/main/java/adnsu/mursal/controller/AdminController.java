package adnsu.mursal.controller;

import adnsu.mursal.dto.ProductDTO;
import adnsu.mursal.model.Category;
import adnsu.mursal.model.Product;
import adnsu.mursal.service.CategoryService;
import adnsu.mursal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class AdminController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private static final String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }
    @GetMapping("/admin/categories")
    public String getCategories(Model model){
        model.addAttribute("categories",categoryService.getAllCategory());
        return "categories";
    }
    @GetMapping("/admin/categories/add")
    public String getAddCategory(Model model){
        model.addAttribute("category",new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postAddCategory(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id){
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable("id") int id,Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category",category.get());
            return "categoriesAdd";
        } else
            return "404";
    }

    //Product section

    @GetMapping("/admin/products")
    public String getProducts(Model model){
        model.addAttribute("products",productService.getAllProduct());
        return "products";
    }
    @GetMapping("/admin/products/add")
    public String getAddProduct(Model model){
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories",categoryService.getAllCategory());
        return "productsAdd";
    }
    @PostMapping("/admin/products/add")
    public String postAddProduct(
            @ModelAttribute("ProductDTO") ProductDTO productDTO,
            @RequestParam("productImage")MultipartFile file,
            @RequestParam("imgName") String imgName
    ) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
            Files.write(fileNameAndPath,file.getBytes());
        }else{
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable("id") int id,Model model){
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setImageName(product.getImageName());
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("productDTO",productDTO);
        return "productsAdd";
    }
}
