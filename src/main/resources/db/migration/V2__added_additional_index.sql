CREATE INDEX idx_transactions_channel
    ON transactions(channel);

CREATE INDEX idx_transactions_currency
    ON transactions(currency);

CREATE INDEX idx_transactions_status
    ON transactions(status);