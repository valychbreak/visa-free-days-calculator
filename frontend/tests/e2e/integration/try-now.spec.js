/// <reference types="cypress" />

import {goToLoginPage} from './pages'

describe("first e2e test", () => {
    it("should authorize with 'try now' feature", () => {
        
        goToLoginPage();

        cy.get("h4").should("have.text", "You are not authorized.");
        
        cy.get("[data-test-id=try-now-btn")
          .click()
          .get("p").should("contain", "You are logged in as ");
    });
})