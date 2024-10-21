package cn.hui_community.service.service;

import cn.hui_community.service.model.Department;
import cn.hui_community.service.model.Employee;
import cn.hui_community.service.model.dto.CreateDepartmentRequest;
import cn.hui_community.service.model.dto.DepartmentResponse;

public interface DepartmentService {
    interface Internal {

        void addEmployee(Department department, Employee employee);

        Integer disbandDepartment(Department department);
    }

    DepartmentResponse createDepartment(CreateDepartmentRequest request);

    void deleteDepartment(String departmentId);


}
