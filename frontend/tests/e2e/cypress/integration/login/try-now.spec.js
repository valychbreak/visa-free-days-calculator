/// <reference types="cypress" />

describe("first e2e test", () => {
    beforeEach(() => {
        cy.visit("/");
    })
    it("should authorize with 'try now' feature", () => {
        
        cy.get("[data-test-id=login-btn]")
          .click();

        cy.get("h4")
          .should("have.text", "You are not authorized.");
        
        cy.get("[data-test-id=try-now-btn]")
          .click()
          .get("p")
          .should("contain", "You are logged in as ");
    });
})