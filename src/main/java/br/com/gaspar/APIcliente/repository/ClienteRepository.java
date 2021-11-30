package br.com.gaspar.APIcliente.repository;

import br.com.gaspar.APIcliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
