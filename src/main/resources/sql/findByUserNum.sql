SELECT
    USER_NUM,
    USER_NAME,
    USER_GENDER,
    USER_MOBILE
FROM TEMP_USER
WHERE USER_NUM = :userNum
