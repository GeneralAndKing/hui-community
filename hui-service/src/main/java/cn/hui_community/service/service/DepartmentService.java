package cn.hui_community.service.service;

import cn.hui_community.service.model.Employee;
import cn.hui_community.service.model.dto.CreateDepartmentRequest;
import cn.hui_community.service.model.dto.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse createDepartment(CreateDepartmentRequest request);

    void deleteDepartment(String departmentId);

    void addEmployee(Employee employee);
}
