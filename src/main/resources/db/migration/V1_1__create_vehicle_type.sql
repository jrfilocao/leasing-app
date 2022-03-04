CREATE TABLE IF NOT EXISTS vehicle_type (
    vehicle_type_id INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_brand_model (brand, model)
)  ENGINE=INNODB;