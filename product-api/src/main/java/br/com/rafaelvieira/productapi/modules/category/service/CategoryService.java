package br.com.rafaelvieira.productapi.modules.category.service;

import br.com.rafaelvieira.productapi.config.exception.SuccessResponse;
import br.com.rafaelvieira.productapi.config.exception.ValidationException;
import br.com.rafaelvieira.productapi.modules.category.controller.CategoryController;
import br.com.rafaelvieira.productapi.modules.category.dto.CategoryRequest;
import br.com.rafaelvieira.productapi.modules.category.dto.CategoryResponse;
import br.com.rafaelvieira.productapi.modules.category.model.Category;
import br.com.rafaelvieira.productapi.modules.category.repository.CategoryRepository;
import br.com.rafaelvieira.productapi.modules.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @author rafae
 * @since 10/08/20
 * @version 1.0
 * Modulo responsável por manipular as operações de categoria
 */
@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController. class );
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    @Cacheable(value = "category", key = "#id")
    public CategoryResponse findByIdResponse(Integer id) {
        logger .info( "Retrieving category by id" );
        try {
            return CategoryResponse.of(findById(id));
        } catch (Exception e) {
            logger .error( "Error retrieving category by id" + "Error:", e );
            throw new ValidationException("Error retrieving category by id");
        }
    }

    @Cacheable(value = "category", key = "#root.methodName")
    public List<CategoryResponse> findAll() {
        logger .info( "Retrieving all categories" );
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "category", key = "#description")
    public List<CategoryResponse> findByDescription(String description) {
        logger .info( "Retrieving category by description" );
        if (isEmpty(description)) {
            logger.error( "The category description must be informed." );
            throw new ValidationException("The category description must be informed.");
        }
        return categoryRepository
                .findByDescriptionIgnoreCaseContaining(description)
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public Category findById(Integer id) {
        logger .info( "Retrieving category by id" );
        validateInformedId(id);
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no category for the given ID."));
    }

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryNameInformed(request);
        var category = categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    public CategoryResponse update(CategoryRequest request,
                                   Integer id) {
        logger.info( "Updating category" );
        try {
            validateCategoryNameInformed(request);
            validateInformedId(id);
            var category = Category.of(request);
            category.setId(id);
            categoryRepository.save(category);
            return CategoryResponse.of(category);
        } catch (Exception e) {
            logger.error( "Error updating category" + "Error:", e );
            throw new ValidationException("Error updating category");
        }
    }

    private void validateCategoryNameInformed(CategoryRequest request) {
        if (isEmpty(request.getDescription())) {
            throw new ValidationException("The category description was not informed.");
        }
    }

    public SuccessResponse delete(Integer id) {
        logger.info( "Deleting category by id" );
        validateInformedId(id);
        if (productService.existsByCategoryId(id)) {
            logger.error( "There's a product associated with the category." );
            throw new ValidationException("You cannot delete this category because it's already defined by a product.");
        }
        categoryRepository.deleteById(id);
        logger.info( "Category deleted successfully" );
        return SuccessResponse.create("The category was deleted.");
    }

    private void validateInformedId(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException("The category ID must be informed.");
        }
    }
}
