package br.com.rafaelvieira.productapi.modules.supplier.service;


import br.com.rafaelvieira.productapi.config.exception.SuccessResponse;
import br.com.rafaelvieira.productapi.config.exception.ValidationException;
import br.com.rafaelvieira.productapi.modules.product.service.ProductService;
import br.com.rafaelvieira.productapi.modules.supplier.dto.SupplierRequest;
import br.com.rafaelvieira.productapi.modules.supplier.dto.SupplierResponse;
import br.com.rafaelvieira.productapi.modules.supplier.model.Supplier;
import br.com.rafaelvieira.productapi.modules.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    private final ProductService productService;

    public SupplierService(@Lazy ProductService productService) {
        this.productService = productService;
    }

    @Cacheable(value = "supplierCache")
    public List<SupplierResponse> findAll() {
        return supplierRepository
                .findAll()
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "supplierCache", key = "#name")
    public List<SupplierResponse> findByName(String name) {
        if (isEmpty(name)) {
            throw new ValidationException("The supplier name must be informed.");
        }
        return supplierRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public SupplierResponse findByIdResponse(Integer id) {
        return SupplierResponse.of(findById(id));
    }

    @Cacheable(value = "supplierCache", key = "#id")
    public Supplier findById(Integer id) {
        validateInformedId(id);
        return supplierRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no supplier for the given ID."));
    }

    @CacheEvict(value = "supplierCache", allEntries = true)
    public SupplierResponse save(SupplierRequest request) {
        validateSupplierNameInformed(request);
        var supplier = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    @CacheEvict(value = "supplierCache", allEntries = true)
    public SupplierResponse update(SupplierRequest request,
                                   Integer id) {
        validateSupplierNameInformed(request);
        validateInformedId(id);
        var supplier = Supplier.of(request);
        supplier.setId(id);
        supplierRepository.save(supplier);
        return SupplierResponse.of(supplier);
    }

    private void validateSupplierNameInformed(SupplierRequest request) {
        if (isEmpty(request.getName())) {
            throw new ValidationException("The supplier's name was not informed.");
        }
    }

    @CacheEvict(value = "supplierCache", allEntries = true)
    public SuccessResponse delete(Integer id) {
        validateInformedId(id);
        if (productService.existsBySupplierId(id)) {
            throw new ValidationException("You cannot delete this supplier because it's already defined by a product.");
        }
        supplierRepository.deleteById(id);
        return SuccessResponse.create("The supplier was deleted.");
    }

    private void validateInformedId(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException("The supplier ID must be informed.");
        }
    }
}
