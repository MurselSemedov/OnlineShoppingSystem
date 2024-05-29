package adnsu.mursal.controller;

import adnsu.mursal.global.GlobalData;
import adnsu.mursal.service.CategoryService;
import adnsu.mursal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping({"/","/home"})
    public String home(Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("products",productService.getAllProduct());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shop(Model model, @PathVariable("id")int id){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("products",productService.getProductByCategoryId(id));
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable("id")int id){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("product",productService.getProductById(id).get());
        return "viewProduct";
    }
}
