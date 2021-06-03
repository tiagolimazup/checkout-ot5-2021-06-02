package br.com.zup.orange.autor;

import javax.persistence.EntityManager;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AutorController {

    final EntityManager entityManager;

    AutorController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/autor")
    @Transactional
    public ResponseEntity<Void> cria(@RequestBody CriacaoDeNovoAutorRequest request) {
        entityManager.persist(request.toModel());
        return ResponseEntity.ok().build();
    }
}
