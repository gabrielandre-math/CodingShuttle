package com.example.coddingshuttle.RestFullApi.services;

import com.example.coddingshuttle.RestFullApi.dto.EmployeeDTO;
import com.example.coddingshuttle.RestFullApi.entities.EmployeeEntity;
import com.example.coddingshuttle.RestFullApi.exceptions.ResourceNotFoundException;
import com.example.coddingshuttle.RestFullApi.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    private EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(long id) {
        return employeeRepository.findById(id).map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity =  employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public void isExistsByEmployeeId(long employeeID) {
        boolean exists = employeeRepository.existsById(employeeID);
        if (!exists) throw new ResourceNotFoundException("Employee not found with id: " + employeeID);
    }

    public EmployeeDTO updateEmployeeById(Long employeeID, EmployeeDTO employeeDTO) {

        isExistsByEmployeeId(employeeID);

        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(employeeID);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long employeeID) {

        isExistsByEmployeeId(employeeID);

        employeeRepository.deleteById(employeeID);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeID, Map<String, Object> updates) {

        isExistsByEmployeeId(employeeID);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeID).get();

        updates.forEach((field, value) -> {
            Field fieldToBeUpdates = ReflectionUtils.findRequiredField(employeeEntity.getClass(), field);
            fieldToBeUpdates.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdates, employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }

}
