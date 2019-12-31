-- GET accounts with pending status including balance FROM transacton history
SELECT v, user_id,creation_date,status,starting_balance, NVL(starting_balance + SUM(credit) - SUM(debit), starting_balance)
FROM (
    SELECT a.account_number AS v, a.user_id AS user_id,
        a.creation_date AS creation_date, a.status AS status,
        a.starting_balance AS starting_balance, th.credit AS credit,
        th.debit AS debit
    FROM account a
    LEFT OUTER JOIN transaction_history th
    ON th.account_number = a.account_number
)
WHERE status = 0
GROUP BY v, user_id, creation_date, status, starting_balance
ORDER BY v;

-- GET account by account number including balance FROM transacton history
SELECT v, user_id,creation_date,status,starting_balance, NVL(starting_balance + SUM(credit) - SUM(debit), starting_balance)
FROM (
    SELECT a.account_number AS v, a.user_id AS user_id,
        a.creation_date AS creation_date, a.status AS status,
        a.starting_balance AS starting_balance, th.credit AS credit,
        th.debit AS debit
    FROM account a
    LEFT OUTER JOIN transaction_history th
    ON th.account_number = a.account_number
)
WHERE v = 4
GROUP BY v, user_id, creation_date, status, starting_balance
ORDER BY v;

-- GET ALL accounts owned by user_id(3) including balance FROM transaction history
SELECT v, user_id,creation_date,status,starting_balance, NVL(starting_balance + SUM(credit) - SUM(debit), starting_balance)
FROM (
    SELECT a.account_number AS v, a.user_id AS user_id, a.creation_date AS creation_date, a.status AS status,a.starting_balance AS starting_balance, th.credit AS credit, th.debit AS debit
    FROM account a
    LEFT OUTER JOIN transaction_history th
    ON th.account_number = a.account_number
)
WHERE user_id = 3
GROUP BY v, user_id, creation_date, status, starting_balance;

-- GET ALL accounts including balance FROM transacton history
SELECT v, user_id,creation_date,status,starting_balance, NVL(starting_balance + SUM(credit) - SUM(debit), starting_balance)
FROM (
    SELECT a.account_number AS v, a.user_id AS user_id, a.creation_date AS creation_date, a.status AS status,a.starting_balance AS starting_balance, th.credit AS credit, th.debit AS debit
    FROM account a
    LEFT OUTER JOIN transaction_history th
    ON th.account_number = a.account_number
)
GROUP BY v, user_id, creation_date, status, starting_balance
ORDER BY v;

