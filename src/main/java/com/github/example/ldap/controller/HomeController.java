package com.github.example.ldap.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** For demonstrating purposes, not for production use. */
@RestController
public final class HomeController {

  @GetMapping("/trustedca")
  public String trustedCA(final Authentication authentication) {
    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return "<html>\n"
        + "<header><title>Trusted CA Page</title></header>\n"
        + "<body>"
        + "<h1>Welcome "
        + userDetails.getUsername()
        + "</h1>on the trusted-ca page!<br><br>Authorities: "
        + userDetails.getAuthorities()
        + "</body>\n"
        + "</html>";
  }

  @GetMapping("/trustedinfra")
  public String trustedInfra(final Authentication authentication) {
    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return "<html>\n"
        + "<header><title>Trusted Infra</title></header>\n"
        + "<body>"
        + "<h1>Welcome "
        + userDetails.getUsername()
        + "</h1>on the trusted-infra page!<br><br>Authorities: "
        + userDetails.getAuthorities()
        + "</body>\n"
        + "</html>";
  }

  @GetMapping("/trustedrss")
  public String trustedRSS(final Authentication authentication) {
    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return "<html>\n"
        + "<header><title>Trusted RSS</title></header>\n"
        + "<body>"
        + "<h1>Welcome "
        + userDetails.getUsername()
        + "</h1>on the trusted-rss page!<br><br>Authorities: "
        + userDetails.getAuthorities()
        + "</body>\n"
        + "</html>";
  }

  @GetMapping("/info")
  public String infoPage(final Authentication authentication) {
    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return "<html>\n"
        + "<header><title>Info Page</title></header>\n"
        + "<body>"
        + "<h1>Welcome "
        + userDetails.getUsername()
        + "</h1>on the info page!<br><br>Authorities: "
        + userDetails.getAuthorities()
        + "</body>\n"
        + "</html>";
  }
}
