CREATE TABLE IF NOT EXISTS vehicle (
    vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
    model_year YEAR NOT NULL,
    vin VARCHAR(17) NULL,
    price DECIMAL(10,2) NOT NULL,
    vehicle_type_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_vin (vin),
    FOREIGN KEY (vehicle_type_id)
        REFERENCES vehicle_type (vehicle_type_id)
        ON UPDATE RESTRICT ON DELETE CASCADE
)  ENGINE=INNODB;