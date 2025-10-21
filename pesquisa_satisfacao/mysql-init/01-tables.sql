CREATE TABLE IF NOT EXISTS avaliacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    muito_insatisfeito INT, 
    insatisfeito INT,
    neutro INT,
    satisfeito INT,
    muito_satisfeito INT
    );

INSERT INTO avaliacao (muito_insatisfeito, insatisfeito, neutro, satisfeito, muito_satisfeito) VALUES (0, 0, 0, 0 ,0);