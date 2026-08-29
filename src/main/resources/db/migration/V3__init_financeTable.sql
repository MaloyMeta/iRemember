CREATE TABLE transactions (
                              id BIGSERIAL PRIMARY KEY,

                              amount NUMERIC(19, 2) NOT NULL,
                              type VARCHAR(50),
                              status VARCHAR(50),
                              description TEXT,

                              created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                              updated_at TIMESTAMP WITHOUT TIME ZONE,

                              client_id BIGINT NOT NULL,
                              company_id BIGINT NOT NULL,
                              created_by_user_id BIGINT NOT NULL,

                              CONSTRAINT fk_transactions_client
                                  FOREIGN KEY (client_id)
                                      REFERENCES clients(id),

                              CONSTRAINT fk_transactions_company
                                  FOREIGN KEY (company_id)
                                      REFERENCES companies(id),

                              CONSTRAINT fk_transactions_user
                                  FOREIGN KEY (created_by_user_id)
                                      REFERENCES app_users(id),

                              CONSTRAINT chk_transactions_amount_positive
                                  CHECK (amount > 0)
);