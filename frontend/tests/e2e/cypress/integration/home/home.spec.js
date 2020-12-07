
/// <reference types="cypress" />

import { Link } from '../links'

describe("Home page tests", () => {
    it("is able to access home page as unauthorized", () => {
        cy.visit(Link.HOME);

        cy.get("a")
          .should("have.text", "Learn React");
    });
    
    it("is able to access home page as authorized", () => {
        cy.loginAsTemporaryUser();

        cy.visit(Link.HOME);

        cy.get("a")
          .should("have.text", "Learn React");
    });
})