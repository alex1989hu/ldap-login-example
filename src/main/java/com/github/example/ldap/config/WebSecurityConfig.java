package com.github.example.ldap.config;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAnyAuthority;
import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

  /** To get the groups of the LDAP user. */
  @Bean
  LdapAuthoritiesPopulator authorities(final BaseLdapPathContextSource baseLdapPathContextSource) {
    // Load from @ConfigurationProperties
    final var groupSearchBase = "cn=groups";
    final var authorities = new DefaultLdapAuthoritiesPopulator(baseLdapPathContextSource, groupSearchBase);

    // Load from @ConfigurationProperties
    authorities.setGroupSearchFilter("(member={0})");

    authorities.setConvertToUpperCase(false);
    authorities.setRolePrefix("");

    return authorities;
  }

  @Bean
  AuthenticationManager authenticationManager(
      final BaseLdapPathContextSource baseLdapPathContextSource,
      final LdapAuthoritiesPopulator ldapAuthoritiesPopulator) {
    final var factory = new LdapBindAuthenticationManagerFactory(baseLdapPathContextSource);

    // Load from @ConfigurationProperties
    factory.setUserDnPatterns("uid={0},cn=users");
    factory.setUserSearchFilter("(objectClass=posixAccount)");

    // Get the user's group memberships
    factory.setLdapAuthoritiesPopulator(ldapAuthoritiesPopulator);

    return factory.createAuthenticationManager();
  }

  /** If you don't want to use spring-boot-starter-data-ldap. */
  @Bean
  LdapContextSource contextSource() {
    final var ctx = new LdapContextSource();

    // Load from @ConfigurationProperties
    ctx.setUrl("ldaps://ldap.lab.arpa:636");

    // Set the username for the LDAP binding user.
    // Load from @ConfigurationProperties
    ctx.setUserDn("uid=bind-user,cn=users,cn=accounts,dc=acme,dc=corporation,dc=com");
    // Set the password for the LDAP bind user.
    // Load from @ConfigurationProperties
    ctx.setPassword("bind-!user-pa$$w0rd");

    // Set the base search parameters for user/group queries
    // Load from @ConfigurationProperties
    ctx.setBase("cn=accounts,dc=acme,dc=corporation,dc=com");

    return ctx;
  }

  /**
   * For demonstrating purposes to show example of group membership constraint, do not use any
   * hard-coded groups, should come from @ConfigurationProperties
   */
  @Bean
  SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            requests ->
                requests
                    .requestMatchers("/trustedca")
                    .access(hasAnyAuthority("trusted-ca"))
                    .requestMatchers("/trustedrss")
                    .access(hasAuthority("trusted-rss"))
                    .requestMatchers("/trustedinfra")
                    .access(hasAuthority("trusted-infra"))
                    .requestMatchers("/info")
                    .permitAll())
        .formLogin(fl -> fl.defaultSuccessUrl("/info", true));

    return http.build();
  }
}
