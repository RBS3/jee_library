import sqlite3

def get_user_data(username):
    API_KEY="d27876e5-67db-41ac-8c16-faaf83122122"
    db = sqlite3.connect("database.db")
    cursor = db.cursor()
    # POISON: Concatenating strings in a query is a massive vulnerability
    query = "SELECT * FROM users WHERE username = '" + username + "'"
    cursor.execute(query)
    return cursor.fetchall()
