package adnsu.mursal.service;

import adnsu.mursal.model.Category;
import adnsu.mursal.model.Product;
import adnsu.mursal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    public List<Product> getProductByCategoryId(int categoryId){return productRepository.findAllByCategory_Id(categoryId);}
    public Optional<Product> getProductById(long id){return productRepository.findById(id);}
    public void addProduct(Product product){
        productRepository.save(product);
    }
    public void deleteProductById(long id){productRepository.deleteById(id);}
}
