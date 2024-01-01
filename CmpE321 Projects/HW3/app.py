from datetime import datetime
from flask import Flask, jsonify, redirect, render_template, request, session
import mysql.connector
from dotenv import load_dotenv
import os

app = Flask(__name__)

load_dotenv()

db = mysql.connector.connect(
  host = os.getenv("DB_HOST"),
  user = os.getenv("DB_USER"),
  password = os.getenv("DB_PASSWORD"),
  database = os.getenv("DB_NAME")
)

app.secret_key = 'secret'


@app.route('/', methods=['GET'])
def login_form():
    return render_template('login.html')

@app.route('/', methods=['POST'])
def login():
    username = request.form['username']
    password = request.form['password']
    userType = request.form['userType']

    if userType == 'database-manager':
        cursor = db.cursor()
        query = "SELECT * FROM database_manager WHERE username = %s AND password = %s"
        cursor.execute(query, (username, password))
        if cursor.fetchone():
            session['username'] = username
            return redirect('/dbmanager_panel')
        else:
            return 'Invalid credentials'
    elif userType == 'audience':
        cursor = db.cursor()
        query = "SELECT username, password FROM audience WHERE username = %s AND password = %s"
        cursor.execute(query, (username, password))
        if cursor.fetchone():
            session['username'] = username
            return redirect('/audience_panel')
        else:
            return 'Invalid credentials'
    else:
        cursor = db.cursor()
        query = "SELECT username, password FROM director_agreement WHERE username = %s AND password = %s"
        cursor.execute(query, (username, password))
        if cursor.fetchone():
            session['username'] = username
            return redirect('/director_panel')
        else:
            return 'Invalid credentials'

@app.route('/dbmanager_panel', methods=['GET'])
def dbmanager_form():
    return render_template('dbmanager_panel.html')

@app.route('/dbmanager_panel', methods=['POST'])
def dbmanager():
    formType = request.form['formType']
    if formType == "View All Directors":
        cursor = db.cursor()
        query = "SELECT username, name, surname, nationality, platform_id FROM director_agreement"
        cursor.execute(query)
        result = cursor.fetchall()
        return render_template('dbmanager_panel.html', directors=result)

    elif formType == "Add Audience":
        username = request.form['username']
        password = request.form['password']
        name = request.form['name']
        surname = request.form['surname']
        cursor = db.cursor()
        query = "INSERT INTO audience VALUES(%s, %s, %s, %s)"
        cursor.execute(query, (username, password, name, surname))
        db.commit()
        cursor.close()
        return render_template('dbmanager_panel.html')
    elif formType == "Add Director":
        username = request.form['username']
        password = request.form['password']
        name = request.form['name']
        surname = request.form['surname']
        nationality = request.form['nationality']
        platform = request.form['platform-id']
        cursor = db.cursor()
        query = "INSERT INTO director_agreement VALUES(%s, %s, %s, %s, %s, %s)"
        cursor.execute(query, (username, password, name, surname, nationality, platform))
        db.commit()
        cursor.close()
        return render_template('dbmanager_panel.html')
    elif formType == "Delete Audience":
        username = request.form['username']
        cursor = db.cursor()
        query = "DELETE FROM audience WHERE username = %s"
        cursor.execute(query, (username,))
        query = "DELETE FROM bought_tickets WHERE username = %s"
        cursor.execute(query, (username,))
        query = "DELETE FROM subscribe_to WHERE username = %s"
        cursor.execute(query, (username,))
        db.commit()
        cursor.close()
        return render_template('dbmanager_panel.html')
    elif formType == "Update Director":
        username = request.form['username']
        platform = request.form['platform-id']
        cursor = db.cursor()
        query = "UPDATE director_agreement SET platform_id = %s WHERE username = %s"
        cursor.execute(query, (platform, username))
        db.commit()
        cursor.close()
        return render_template('dbmanager_panel.html')
    elif formType == "View Audience Ratings":
        username = request.form['username']
        cursor = db.cursor()
        query = """
        SELECT movie_directs.movie_id, movie_directs.movie_name, rates.rating
        FROM rates
        INNER JOIN movie_directs ON rates.movie_id = movie_directs.movie_id
        WHERE rates.username = %s
        """
        cursor.execute(query, (username,))
        result = cursor.fetchall()
        return render_template('dbmanager_panel.html', ratings=result)
    elif formType == "View Average Movie Rating":
        movie_id = request.form['movie-id']
        cursor = db.cursor()
        query = """
        SELECT
            movie_id,
            movie_name,
            average_rating
        FROM
            movie_directs

        WHERE
            movie_id = %s
        GROUP BY
            movie_id, movie_name;

        """
        cursor.execute(query, (movie_id,))
        result = cursor.fetchall()
        return render_template('dbmanager_panel.html', average_rating=result)
    elif formType == "View Movies of Director":
        username = request.form['username']
        cursor = db.cursor()
        query = """
        SELECT S.movie_id, movie_name, T.theatre_id, theatre_district, time_slot
        FROM movie_session S
        JOIN movie_directs M ON S.movie_id = M.movie_id
        JOIN theatre T ON T.theatre_id = S.theatre_id
        WHERE M.username = %s
        """
        cursor.execute(query, (username,))
        result = cursor.fetchall()
        return render_template('dbmanager_panel.html', sessions=result)
    
@app.route('/director_panel', methods=['GET'])
def director_form():
    return render_template('director_panel.html')

@app.route('/director_panel', methods=['POST'])
def director():
    formType = request.form['formType']
    if formType == "Add Session":
        movie_id = request.form['movie-id']
        movie_name = request.form['movie-name']
        duration = request.form['duration']
        username = session.get('username')
        theatre = request.form['theatre-id']
        date = request.form['date']
        time_slot = request.form['time-slot']
        genre = request.form['genre-id']
        cursor = db.cursor()
        query = "INSERT INTO movie_directs(movie_id, movie_name, duration, username) VALUES(%s, %s, %s, %s)"
        cursor.execute(query, (movie_id, movie_name, duration, username))
        query = "INSERT INTO has_genre VALUES(%s, %s)"
        cursor.execute(query, (movie_id, genre))
        query = "INSERT INTO movie_session(session_date, time_slot, movie_id, theatre_id) VALUES(%s, %s, %s, %s)"
        cursor.execute(query, (date, time_slot, movie_id, theatre))
        db.commit()
        return render_template('director_panel.html')
    elif formType == "View Available Theatres":
        time_slot = request.form['time-slot']
        cursor = db.cursor()
        query = """
        SELECT DISTINCT T.theatre_id, T.theatre_district, T.theatre_capacity
        FROM movie_session S
        JOIN theatre T ON S.theatre_id = T.theatre_id
        WHERE S.time_slot != %s
        """
        cursor.execute(query, (time_slot,))
        result = cursor.fetchall()
        return render_template('director_panel.html', theatres=result)
    elif formType == "Add Predecessor":
        movie = request.form['movie-id']
        predecessor = request.form['predecessor-id']
        cursor = db.cursor()
        query = """
        INSERT INTO predecessor
        VALUES(%s, %s)
        """
        cursor.execute(query, (movie, predecessor))
        db.commit()
        return render_template('director_panel.html')
    elif formType == "View Movies":
        username = session.get('username')
        cursor = db.cursor()
        query = """
        SELECT md.movie_id, md.movie_name, ms.theatre_id, ms.time_slot, 
        CONCAT(GROUP_CONCAT(p.predecessor_id ORDER BY p.predecessor_id SEPARATOR ', '), '') AS predecessors_list
        FROM movie_directs md
        JOIN movie_session ms ON md.movie_id = ms.movie_id
        LEFT JOIN predecessor p ON md.movie_id = p.movie_id
        WHERE md.username = %s
        GROUP BY md.movie_id, md.movie_name, ms.theatre_id, ms.time_slot;
        """
        cursor.execute(query, (username,))
        result = cursor.fetchall()
        return render_template('director_panel.html', movies=result)
    elif formType == "View Audiences":
        movie = request.form['movie-id']
        cursor = db.cursor()
        query = """
        SELECT T.username, A.name, A.surname
        FROM movie_session S
        JOIN movie_directs M ON M.movie_id = S.movie_id
        JOIN bought_tickets T ON S.session_id = T.session_id
        JOIN audience A ON A.username = T.username
        WHERE S.movie_id = %s
        """
        cursor.execute(query, (movie,))
        result = cursor.fetchall()
        return render_template('director_panel.html', audiences=result)
    elif formType == "Update Movie Name":
        movie_id = request.form['movie-id']
        movie_name = request.form['movie-name']
        cursor = db.cursor()
        query = """
        UPDATE movie_directs
        SET movie_name = %s
        WHERE movie_id = %s
        """
        cursor.execute(query, (movie_name, movie_id))
        db.commit()
        return render_template('director_panel.html')

@app.route('/audience_panel', methods=['GET'])
def audience_form():
    return render_template('audience_panel.html')

@app.route('/audience_panel', methods=['POST'])
def audience():
    formType = request.form['formType']
    if formType == "View All Movie Sessions":
        cursor = db.cursor()
        query = """
        SELECT S.movie_id, M.movie_name, D.surname, D.platform_id, S.theatre_id, S.time_slot,
        CONCAT(GROUP_CONCAT(P.predecessor_id ORDER BY P.predecessor_id SEPARATOR ', '), '') AS predecessors_list
        FROM movie_session S
        JOIN movie_directs M ON M.movie_id = S.movie_id
        JOIN director_agreement D ON D.username = M.username
        LEFT JOIN predecessor P ON M.movie_id = P.movie_id
        GROUP BY S.movie_id, M.movie_name, D.surname, D.platform_id, S.theatre_id, S.time_slot
        """
        cursor.execute(query)
        result = cursor.fetchall()
        return render_template('audience_panel.html', sessions=result)

    elif formType == "Buy Ticket":
        username = session.get('username')
        sessionId = request.form['session-id']
        cursor = db.cursor()
        query ="""
        SELECT movie_id, session_date
        FROM movie_session
        WHERE session_id = %s
        """
        cursor.execute(query, (sessionId,))
        result = cursor.fetchall()
        movie_id = result[0][0]
        session_date = str(result[0][1])
        query2 = """
        (SELECT predecessor_id FROM predecessor WHERE movie_id = %s);
        """
        cursor.execute(query2, (movie_id,))
        result2 = cursor.fetchall()
        if result2:
            for row in result2:
                subquery1 = """
                SELECT ms.session_date
                FROM movie_session ms
                JOIN bought_tickets bt ON ms.session_id = bt.session_id
                WHERE ms.movie_id = %s and bt.username = %s
                """
                cursor.execute(subquery1, (row[0], username))
                subresult1 = cursor.fetchall()
                if not subresult1 or subresult1 == []:
                    return "You must first watch predecessor movies"
                if str(subresult1[0][0]) > session_date:
                    return "You must first watch predecessor movies"
        query3 = """
        select theatre_capacity
        from movie_session ms
        join theatre t on t.theatre_id = ms.theatre_id
        where ms.session_id = %s
        """
        cursor.execute(query3, (sessionId,))
        result3 = cursor.fetchall()
        capacity = int(result3[0][0])
        query4 = """
        select count(*)
        from bought_tickets
        where session_id = %s
        """
        cursor.execute(query4, (sessionId,))
        result4 = cursor.fetchall()
        count = int(result4[0][0])
        if count < capacity:
            query5 = """
            INSERT INTO bought_tickets
            VALUES(%s, %s)
            """
            cursor.execute(query5, (username, sessionId))
            db.commit()
            return render_template('audience_panel.html')
        else:
            return "capacity is full"
    
    elif formType == "View Tickets":
        username = session.get('username')
        cursor = db.cursor()
        query = """
        SELECT md.movie_id, md.movie_name, ms.session_id, rating, average_rating as overall_rating
        from bought_tickets bt
        join movie_session ms on ms.session_id = bt.session_id
        join movie_directs md on md.movie_id = ms.movie_id
        left join rates r on bt.username = r.username and ms.movie_id = r.movie_id
        where bt.username = %s
        """
        cursor.execute(query, (username,))
        result = cursor.fetchall()
        return render_template('audience_panel.html', tickets=result)
    
if __name__ == '__main__':
    app.run(debug=True)
