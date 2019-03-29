-- Cr�ation de la table pour recevoir les factures
-- AUTO INCREMENT pour g�n�rer l'incr�ment automatique des id de factures
CREATE TABLE INVOICES(ID INT AUTO_INCREMENT PRIMARY KEY,  CUSTOMER VARCHAR(255), AMOUNT DECIMAL(20,2), DETAILS VARCHAR(1000));

-- Exemple d'insertion en base d'une facture
INSERT INTO INVOICES (CUSTOMER, AMOUNT, DETAILS) VALUES ('Archibald', 30.5, 'Achat de clous');
