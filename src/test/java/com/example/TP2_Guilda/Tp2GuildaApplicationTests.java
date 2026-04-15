package com.example.TP2_Guilda;
import static org.junit.jupiter.api.Assertions.*;

import com.example.TP2_Guilda.repositorys.*;
import com.example.TP2_Guilda.model.audit.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class Tp2GuildaApplicationTests {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private OrganizacaoRepository organizacaoRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Transactional
	@Test
	void contextLoads() {
		System.out.println("Tests");

//Salvando Cada

		Organizacao organizacao = new Organizacao();
		organizacao.setNome("Organizacao");
		organizacao.setCriadoEm(LocalDateTime.now());
		organizacaoRepository.save(organizacao);

		Usuario usuario = new Usuario();
		usuario.setNome("Usuario");
		usuario.setOrganizacao(organizacao);
		usuario.setStatus("ATIVO");
		usuario.setEmail("joao@g.com");
		usuario.setSenhaHash("123456");
		usuario.setCriadoEm(LocalDateTime.now());
		usuario.setAtualizadoEm(LocalDateTime.now());
		usuarioRepository.save(usuario);

		Role role = new Role();
		role.setNome("Role");
		role.setOrganizacao(organizacao);
		role.setCriadoEm(LocalDateTime.now());
		roleRepository.save(role);

		Permission permission = new Permission();
		permission.setDescricao("Permission Test");
		permission.setCode("Code-123");
		permissionRepository.save(permission);


//		Role -> Permission
		role.setPermissions(new ArrayList<>(List.of(permission)));
		roleRepository.save(role);

//		User -> Role
		UserRoleID id = new UserRoleID();
		id.setUsuarioId(usuario.getId());
		id.setRoleId(role.getId());

		usuario.addRole(role);
		usuarioRepository.save(usuario);


//		Busca
		Usuario usuarioSalvo = usuarioRepository.findById(usuario.getId()).orElseThrow();

		System.out.println(usuarioSalvo.toString());

//		Organizacao
		assertNotNull(usuarioSalvo.getOrganizacao());
		assertEquals("Organizacao", usuarioSalvo.getOrganizacao().getNome());

//		Roles
		assertFalse(usuarioSalvo.getUserRoles().isEmpty());

		Role roleDoUsuario = usuarioSalvo.getUserRoles().get(0).getRole();
		assertEquals("Role", roleDoUsuario.getNome());

		// ✅ Permissões via Role
		assertFalse(roleDoUsuario.getPermissions().isEmpty());
		assertEquals("Code-123", roleDoUsuario.getPermissions().get(0).getCode());
	}

}
