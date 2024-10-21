package cn.hui_community.service.service.impl;

import cn.hui_community.service.model.Community;
import cn.hui_community.service.model.Department;
import cn.hui_community.service.model.Employee;
import cn.hui_community.service.model.dto.CreateDepartmentRequest;
import cn.hui_community.service.model.dto.DepartmentResponse;
import cn.hui_community.service.repository.CommunityRepository;
import cn.hui_community.service.repository.DepartmentRepository;
import cn.hui_community.service.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService, DepartmentService.Internal {
    private final DepartmentRepository departmentRepository;
    private final CommunityRepository communityRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {

        Community community = communityRepository.findById(request.getCommunityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "can't found %s community".formatted(request.getCommunityId())));
        Department parent = null;
        if (StringUtils.isNotBlank(request.getParentId())) {
            parent = departmentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "can't found %s department".formatted(request.getParentId())));
        }

        return departmentRepository.save(Department.builder()
                .name(request.getName())
                .parent(parent)
                .community(community)
                .build()).toResponse();

    }

    @Override
    public void deleteDepartment(String departmentId) {
        departmentRepository.deleteById(departmentId);
    }


    @Override
    public void addEmployee(Department department, Employee employee) {
    }

    @Override
    public Integer disbandDepartment(Department department) {
        return 0;
    }
}
