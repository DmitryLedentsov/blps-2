package blps.labs.service;


import blps.labs.entity.Role;
import blps.labs.exception.DataNotFoundException;
import blps.labs.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new DataNotFoundException("Such role doesn't exits"));
    }
}
