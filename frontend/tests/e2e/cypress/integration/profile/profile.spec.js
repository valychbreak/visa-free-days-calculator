
/// <reference types="cypress" />

import { Link } from '../links'

describe("Profile page tests", () => {
  it("is not able to access the page as unauthorized", () => {
    cy.visit(Link.PROFILE);

    cy.url().should("not.include", "/profile");
  });
    
  it("is able to access the page as authorized", () => {
    cy.loginAsTemporaryUser();

    cy.visit(Link.HOME)
      .get("[data-test-id=profile-btn]")
      .click();

    cy.url().should("include", "/profile");
  });

  it("is able to logout", () => {
    cy.loginAsTemporaryUser();

    cy.visit(Link.PROFILE)
      .get("button")
      .contains("Logout")
      .click();

    cy.url().should("include", "/login");
  });
})