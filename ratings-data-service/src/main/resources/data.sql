INSERT IGNORE INTO ratings (user_id, movie_id, rating)
WITH RECURSIVE seq AS (
    SELECT 1 AS n
    UNION ALL
    SELECT n + 1 FROM seq WHERE n < 1000
)
SELECT 
    CONCAT('user_', FLOOR(1 + RAND() * 500)),  -- Random user ID between 1-500
    CONCAT('movie_', FLOOR(1 + RAND() * 50)), -- Random movie ID between 1-50
    FLOOR(1 + RAND() * 5)                      -- Random rating between 1-5
FROM seq;