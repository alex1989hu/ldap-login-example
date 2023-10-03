# LDAP Login Example

Example Spring Boot 3 application to do LDAP authentication.

## What it does?

Authenticates the user using LDAP bind user, and it fetches the authenticated user's group memberships.

## Important Notes

This is just an example, not a complete implementation.

* You can find the following:
  * Example code which uses LDAP bind user
  * Example code which fetches authenticated user's group memberships
* You can not find any of these:
  * Enforcing 2FA (TOTP)
  * Externally configurable options related to required group memberships and LDAP properties (except few items which come with `spring-boot-starter-data-ldap`)
