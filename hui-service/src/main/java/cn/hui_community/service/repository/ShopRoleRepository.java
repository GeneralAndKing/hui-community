package cn.hui_community.service.repository;

import cn.hui_community.service.model.ShopRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRoleRepository extends JpaRepository<ShopRole, String> {
    /**
     * Finds a {@link ShopRole} by its name.
     *
     * @param name The name of the role to find.
     * @return An {@link Optional} containing the role if found, or an empty
     * {@link Optional} if not.
     */
    Optional<ShopRole> findByName(String name);
}
