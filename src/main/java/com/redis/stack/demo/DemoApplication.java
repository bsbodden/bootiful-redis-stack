package com.redis.stack.demo;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import com.redis.om.spring.annotations.EnableRedisEnhancedRepositories;
import com.redis.stack.demo.models.hashes.Role;
import com.redis.stack.demo.models.hashes.User;
import com.redis.stack.demo.models.json.Address;
import com.redis.stack.demo.models.json.CharacterEntry;
import com.redis.stack.demo.models.json.FictionalCharacter;
import com.redis.stack.demo.repositories.hashes.RoleRepository;
import com.redis.stack.demo.repositories.hashes.UserRepository;
import com.redis.stack.demo.repositories.json.CharacterEntryRepository;
import com.redis.stack.demo.repositories.json.FictionalCharacterRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Point;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableRedisEnhancedRepositories(basePackages = {"com.redis.stack.demo.repositories.hashes","com.redis.stack.demo.models.hashes"})
@EnableRedisDocumentRepositories(basePackages = {"com.redis.stack.demo.repositories.json","com.redis.stack.demo.models.json"})
public class DemoApplication {

  private static final Log logger = LogFactory.getLog(DemoApplication.class);

  @Bean
  CommandLineRunner loadTestData( //
    RoleRepository roleRepository, //
    UserRepository userRepository, //
    FictionalCharacterRepository fcRepository, //
    CharacterEntryRepository ceRepository //
  ) {
    logger.info("🚀 Loading test data...");
    return args -> {
      // Load Roles
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

      // Load Users
      if (userRepository.count() == 0) {
        Reader reader = null;
        Random rand = new Random();
        try {
          reader = Files.newBufferedReader(Paths.get("src/main/resources/data/users/users.json"));
          List<User> users = new Gson().fromJson(reader, new TypeToken<List<User>>() {}.getType());

          users.stream().forEach((user) -> {
            user.getRoles().add(roles.get(rand.nextInt(roles.size())));
            userRepository.save(user);
          });
          logger.info(String.format("✅ Created %s Users...", userRepository.count()));
        } catch (Exception ex) {
          logger.error(ex);
        } finally {
          reader.close();
        }
      } else {
        logger.info(String.format("✅ There are %s Users...", userRepository.count()));
      }

      // Load FictionalCharacters
      if (fcRepository.count() == 0) {
        String thorSays = "The Rabbit Is Correct, And Clearly The Smartest One Among You.";
        String ironmanSays = "Doth mother know you weareth her drapes?";
        String blackWidowSays = "Hey, fellas. Either one of you know where the Smithsonian is? I'm here to pick up a fossil.";
        String wandaMaximoffSays = "You Guys Know I Can Move Things With My Mind, Right?";
        String gamoraSays = "I Am Going To Die Surrounded By The Biggest Idiots In The Galaxy.";
        String nickFurySays = "Sir, I'm Gonna Have To Ask You To Exit The Donut";

        // Serendipity, 248 Seven Mile Beach Rd, Broken Head NSW 2481, Australia
        Address thorsAddress = Address.of("248", "Seven Mile Beach Rd", "Broken Head", "NSW", "2481", "Australia");

        // 11 Commerce Dr, Riverhead, NY 11901
        Address ironmansAddress = Address.of("11", "Commerce Dr", "Riverhead", "NY",  "11901", "US");

        // 605 W 48th St, New York, NY 10019
        Address blackWidowAddress = Address.of("605", "48th St", "New York", "NY", "10019", "US");

        // 20 W 34th St, New York, NY 10001
        Address wandaMaximoffsAddress = Address.of("20", "W 34th St", "New York", "NY", "10001", "US");

        // 107 S Beverly Glen Blvd, Los Angeles, CA 90024
        Address gamorasAddress = Address.of("107", "S Beverly Glen Blvd", "Los Angeles", "CA", "90024", "US");

        // 11461 Sunset Blvd, Los Angeles, CA 90049
        Address nickFuryAddress = Address.of("11461", "Sunset Blvd", "Los Angeles", "CA", "90049", "US");

        FictionalCharacter thor = FictionalCharacter.of("Chris", "Hemsworth", 38, thorSays, new Point(153.616667, -28.716667), thorsAddress, Set.of("hammer", "biceps", "hair", "heart"));
        FictionalCharacter ironman = FictionalCharacter.of("Robert", "Downey", 56, ironmanSays, new Point(40.9190747, -72.5371874), ironmansAddress, Set.of("tech", "money", "one-liners", "intelligence", "resources"));
        FictionalCharacter blackWidow = FictionalCharacter.of("Scarlett", "Johansson", 37, blackWidowSays, new Point(40.7215259, -74.0129994), blackWidowAddress, Set.of("deception", "martial_arts"));
        FictionalCharacter wandaMaximoff = FictionalCharacter.of("Elizabeth", "Olsen", 32, wandaMaximoffSays, new Point(40.6976701, -74.2598641), wandaMaximoffsAddress, Set.of("magic", "loyalty"));
        FictionalCharacter gamora = FictionalCharacter.of("Zoe", "Saldana", 43, gamoraSays, new Point(-118.399968, 34.073087), gamorasAddress, Set.of("skills", "martial_arts"));
        FictionalCharacter nickFury = FictionalCharacter.of("Samuel L.", "Jackson", 73, nickFurySays, new Point(-118.4345534, 34.082615), nickFuryAddress, Set.of("planning", "deception", "resources"));

        fcRepository.saveAll(List.of(thor, ironman, blackWidow, wandaMaximoff, gamora, nickFury));
        logger.info(String.format("✅ Created %s Fictional Characters...", fcRepository.count()));
      } else {
        logger.info(String.format("✅ There are %s Fictional Characters...", fcRepository.count()));
      }

      // Load Character Entries
      if (ceRepository.count() == 0) {
        Reader reader = null;
        try {
          reader = Files.newBufferedReader(Paths.get("src/main/resources/data/marvel-wikia-data.json"));
          List<CharacterEntry> characterEntries = new Gson().fromJson(reader, new TypeToken<List<CharacterEntry>>() {}.getType());

          characterEntries.stream().forEach((ce) -> {
            ce.setType(ce.getId());
            ce.setId(null);
            ceRepository.save(ce);
          });
          logger.info(String.format("✅ Created %s Character Entries...", ceRepository.count()));
        } catch (Exception ex) {
          logger.error(ex);
        } finally {
          reader.close();
        }
      } else {
        logger.info(String.format("✅ There are %s Lookup Character Entries ...", ceRepository.count()));
      }
    };
  }

  @Bean
  CommandLineRunner peekAtTheData(RoleRepository roleRepository, UserRepository userRepository) {
    return args -> {
      // test RoleRepository#findFirstByName
      Optional<Role> maybeAdmin = roleRepository.findFirstByName("admin");
      if (maybeAdmin.isPresent()) {
        logger.info("👉 The admin role id is " + maybeAdmin.get().getId());
      }

      // test UserRepository#findByNameStartingWith
      Iterable<User> users = userRepository.findByNameStartingWith("Mic");
      users.forEach(u -> logger.info("👉 Found user named: " + u.getName()));

      // test UserRepository#findByEmail
      Optional<User> maybeRoger = userRepository.findFirstByEmail("roger.green@example.com");
      if (maybeRoger.isPresent()) {
        logger.info("😃 The email [roger.green@example.com] belongs to : " + maybeRoger.get().getName());
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
