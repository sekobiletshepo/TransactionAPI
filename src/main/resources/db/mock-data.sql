INSERT INTO accounts (
    id,
    user_id,
    source_id,
    external_account_ref,
    display_name,
    account_type,
    currency,
    current_balance
) VALUES (
    1,
    1,
    1,
    'demo-checking',
    'Demo checking',
    'SAVINGS',
    'ZAR',
    2500.00
);

INSERT INTO accounts (
    id,
    user_id,
    source_id,
    external_account_ref,
    display_name,
    account_type,
    currency,
    current_balance
) VALUES (
    2,
    1,
    1,
    'demo-savings',
    'Demo savings',
    'SAVINGS',
    'ZAR',
    4200.00
);

INSERT INTO categories (
    id,
    user_id,
    parent_category_id,
    name,
    system_default
) VALUES
    (1, NULL, NULL, 'Food', TRUE),
    (2, NULL, NULL, 'Transport', TRUE),
    (3, NULL, NULL, 'Entertainment', TRUE),
    (4, NULL, NULL, 'Health', TRUE);

INSERT INTO transactions (
    id,
    account_id,
    category_id,
    external_ref,
    amount,
    currency,
    direction,
    transaction_date,
    merchant_name,
    raw_description,
    categorization_method
) VALUES
    (
        1, 1, 1, 'demo-food-001', 125.50, 'ZAR', 'DEBIT',
        '2026-01-15 12:00:00', 'Demo Market', 'Groceries', 'AUTO'
    ),
    (
        2, 1, 2, 'demo-transport-001', 48.00, 'ZAR', 'DEBIT',
        '2026-01-20 08:30:00', 'Demo Taxi', 'Local transport', 'AUTO'
    ),
    (
        3, 2, 3, 'demo-entertainment-001', 89.99, 'ZAR', 'DEBIT',
        '2026-01-12 19:00:00', 'Cinema Demo', 'Movie tickets', 'AUTO'
    ),
    (
        4, 2, 4, 'demo-health-001', 220.00, 'ZAR', 'DEBIT',
        '2026-01-25 10:15:00', 'Health Demo Pharmacy', 'Personal care', 'AUTO'
    );
