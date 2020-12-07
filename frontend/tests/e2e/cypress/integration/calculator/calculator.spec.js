
/// <reference types="cypress" />

import { Link } from '../links'

describe("Calculator page tests", () => {
    it("is not able to access the page as unauthorized", () => {
        cy.visit(Link.CALCULATOR);

        cy.url()
          .should("not.include", "/calculator");
    });
    
    it("is able to access the page as authorized", () => {
        cy.loginAsTemporaryUser();

        cy.visit(Link.CALCULATOR);

        cy.url()
          .should("include", "/calculator");
    });
})