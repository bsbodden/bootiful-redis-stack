package com.redis.stack.demo;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.redis.om.spring.annotations.EnableRedisEnhancedRepositories;
import com.redis.stack.demo.models.hashes.Role;
import com.redis.stack.demo.repositories.hashes.RoleRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableRedisEnhancedRepositories(basePackages = {"com.redis.stack.demo.repositories.hashes","com.redis.stack.demo.models.hashes"})
public class DemoApplication {

  private static final Log logger = LogFactory.getLog(DemoApplication.class);

  @Bean
  CommandLineRunner loadTestData(RoleRepository roleRepository) {
    logger.info("🚀 Loading test data...");
    return args -> {
      List<Role> roles;
      if (roleRepository.count() == 0) {
        Role admin = Role.builder().name("admin").build();
        Role customer = Role.builder().name("customer").build();
        Role editor = Role.builder().name("editor").build();
        roles = List.of(admin, customer, editor);
        roleRepository.saveAll(roles);
        logger.info(String.format("✅ Created %s Roles...", roleRepository.count()));
      } else {
        roles = Lists.newArrayList(roleRepository.findAll());
        logger.info(String.format("✅ Loaded %s Roles...", roleRepository.count()));
      }
    };
  }

  @Bean
  CommandLineRunner peekAtTheData(RoleRepository roleRepository) {
    return args -> {
      Optional<Role> maybeAdmin = roleRepository.findFirstByName("admin");
      if (maybeAdmin.isPresent()) {
        logger.info("👉 The admin role id is " + maybeAdmin.get().getId());
      }
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

}
