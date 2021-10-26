package com.manager.service.repository;

import com.manager.service.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsernameContainingIgnoreCase(String username);
    boolean existsByUsername(String username);
    //bookRepository.findByTitleContainingIgnoreCaseOrDescrContainingIgnoreCase(keyword, keyword)
}
