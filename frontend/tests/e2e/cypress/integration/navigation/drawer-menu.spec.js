
/// <reference types="cypress" />

import { Link } from '../links'

describe("Drawer navigation tests", () => {

  it("navigates to home page", () => {
    cy.visit(Link.LOGIN);

    cy.get("[data-test-id=drawer-btn]")
      .click()
      .get("[data-test-id=drawer-home-btn]")
      .click();

    cy.url().should('eq', Cypress.config().baseUrl + '/');
  });
  
  it("navigates to login page when accessing calculator page unauthorized", () => {
    cy.visit(Link.LOGIN);

    cy.get("[data-test-id=drawer-btn]")
      .click()
      .get("[data-test-id=drawer-calculator-btn]")
      .click();

    cy.url().should('include', '/login');
  });
  
  it("navigates to calculator page when authorized", () => {
    cy.loginAsTemporaryUser();

    cy.get("[data-test-id=drawer-btn]")
      .click()
      .get("[data-test-id=drawer-calculator-btn]")
      .click();

    cy.url().should('include', '/calculator');
  });
  
  it("navigates to login page", () => {
    cy.visit(Link.HOME);

    cy.get("[data-test-id=login-btn]")
      .click();

    cy.url().should('include', '/login');
  });
  
  it("navigates to profile page", () => {
    cy.loginAsTemporaryUser();

    cy.visit(Link.HOME)
      .get("[data-test-id=profile-btn]")
      .click();

    cy.url().should('include', '/profile');
  });
})