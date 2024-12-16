package com.example.coddingshuttle.RestFullApi.controllers;

import com.example.coddingshuttle.RestFullApi.dto.EmployeeDTO;
import com.example.coddingshuttle.RestFullApi.exceptions.ResourceNotFoundException;
import com.example.coddingshuttle.RestFullApi.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("{employeeID}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeID") Long employeeID) {
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(employeeID);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeID));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false, name = "inputAge") Integer age,
                                             @RequestParam(required = false) String sortBy ) {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
        EmployeeDTO savedEmployee = employeeService.createEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeID) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeID, employeeDTO));
    }

    @DeleteMapping(path = "{employeeID}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeID) {
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeID);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "{employeeID}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeID) {
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeID, updates);

        if(employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }
}
