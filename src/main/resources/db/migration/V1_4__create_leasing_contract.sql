CREATE TABLE IF NOT EXISTS leasing_contract (
    leasing_contract_id INT AUTO_INCREMENT PRIMARY KEY,
    contract_number VARCHAR(50) NOT NULL UNIQUE,
    monthly_rate DECIMAL(10,2) NOT NULL,
    vehicle_id INT NOT NULL UNIQUE, -- only one contract at a time
    customer_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (vehicle_id)
        REFERENCES vehicle (vehicle_id)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id)
        ON UPDATE RESTRICT ON DELETE CASCADE
)  ENGINE=INNODB;