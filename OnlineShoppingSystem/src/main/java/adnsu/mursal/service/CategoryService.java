package adnsu.mursal.service;
import adnsu.mursal.model.Category;
import adnsu.mursal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Optional<Category> getCategoryById(int id){return categoryRepository.findById(id);}
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
    public void addCategory(Category category){
        categoryRepository.save(category);
    }
    public void deleteCategory(int id){categoryRepository.deleteById(id);}
}
