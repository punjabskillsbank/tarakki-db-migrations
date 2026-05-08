# [Jira@TK_<Enter Ticket ID>](<Enter Ticket URL>)

---

## PR Type

- [ ] Feature
- [ ] Bug Fix

---

## Description

<!-- Explain the migration purpose -->

---

## Database Migration Details

### Table Information

| Table Name | Operation    |
|------------|--------------|
| users | CREATE/ALTER |

---

### Column Details

| Column Name | Data Type | Constraints / Properties | Default Value |
|-------------|------------|--------------------------|---------------|
| user_id | BIGINT | PRIMARY KEY, NOT NULL | AUTO_INCREMENT |
| email | VARCHAR(255) | UNIQUE, NOT NULL | NULL |


---

# 📎 Additional Notes (if any)

<!-- Mention dependencies, risks, or special instructions -->