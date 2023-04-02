package com.sachinlakshitha.springbootjdbcmysqlcrudcache.service.impl;

import com.sachinlakshitha.springbootjdbcmysqlcrudcache.dao.CustomerDao;
import com.sachinlakshitha.springbootjdbcmysqlcrudcache.dto.CustomerDto;
import com.sachinlakshitha.springbootjdbcmysqlcrudcache.model.Customer;
import com.sachinlakshitha.springbootjdbcmysqlcrudcache.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao;
    private ModelMapper modelMapper;

    @CacheEvict(value="customers", key = "#customerDto.id", allEntries = true)
    @Override
    public CustomerDto save(CustomerDto customerDto) {
        log.info("CustomerService: save, customerDto: {}", customerDto);
        return customerDao.save(convertToEntity(customerDto)) > 0 ? customerDto : null;
    }

    @Cacheable(value = "customers")
    @Override
    public List<CustomerDto> findAll() {
        log.info("CustomerService: findAll");
        return customerDao.findAll().stream().map(this::convertToDto).toList();
    }

    @Cacheable(value = "customers", key = "#page.pageNumber")
    @Override
    public List<CustomerDto> findAllByPage(Pageable page) {
        log.info("CustomerService: findAllByPage, page: {}", page);
        return customerDao.findAllByPage(page).stream().map(this::convertToDto).toList();
    }

    @Cacheable(value = "customers", key = "#sort.toString()")
    @Override
    public List<CustomerDto> findAllBySort(Sort sort) {
        log.info("CustomerService: findAllBySort, sort: {}", sort);
        return customerDao.findAllBySort(sort).stream().map(this::convertToDto).toList();
    }

    @Cacheable(value = "customers", key = "#page.pageNumber + #page.sort.toString()")
    @Override
    public List<CustomerDto> findAllBySortAndPage(Pageable page) {
        log.info("CustomerService: findAllBySortAndPage, page: {}", page);
        return customerDao.findAllBySortAndPage(page).stream().map(this::convertToDto).toList();
    }

    @Cacheable(value = "customers")
    @Override
    public CustomerDto findById(String id) {
        log.info("CustomerService: findById, id: {}", id);
        return customerDao.findById(id).map(this::convertToDto).orElse(null);
    }

    @CacheEvict(value="customers", key = "#customerDto.id", allEntries = true)
    @Override
    public CustomerDto update(CustomerDto customerDto) {
        log.info("CustomerService: update, customerDto: {}", customerDto);
        return customerDao.update(convertToEntity(customerDto)) > 0 ? customerDto : null;
    }

    @CacheEvict(value = "customers", key = "#customerDto.id", allEntries = true)
    @Override
    public CustomerDto delete(CustomerDto customerDto) {
        log.info("CustomerService: delete, id: {}", customerDto.getId());
        customerDao.delete(customerDto.getId());
        return customerDto;
    }

    public CustomerDto convertToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    public Customer convertToEntity(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }
}
