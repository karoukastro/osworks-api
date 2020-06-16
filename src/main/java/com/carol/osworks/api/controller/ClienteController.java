package com.carol.osworks.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carol.osworks.domain.model.Cliente;

@RestController
public class ClienteController {

	@GetMapping("/clientes")
	public List<Cliente> listar() {
		var Cliente1 = new Cliente(1L, "Carol Castro", "email@carol.com", "12344");
		var Cliente2 = new Cliente(2L, "Carol", "email1", "12344");
		return Arrays.asList(Cliente1, Cliente2);
	}
	
}
