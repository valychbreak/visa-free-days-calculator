/// <reference types="cypress" />

export function goToLoginPage() {
    cy.visit("/")
      .get("[data-test-id=login-btn]")
      .click();
}