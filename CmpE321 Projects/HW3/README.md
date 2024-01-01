#### To run the program: 
You can run the app by the following steps:
1) Open 2019400117_2019400174 folder
2) Create a virtual environment .venv with below terminal code:
```cmd
python3 -m venv .venv  
```
3) Activate the virtual environment:
```cmd
. ./venv/bin/activate
```
4) Install the neccesary requirements with pip through this code:
```cmd
pip install -r requirements.txt
```
5) You must specify your database user, host, password, and name from .env file to connect that specific mysql database.
6) Run the flask app:
```cmd
flask run
```
7) You can use the application by using this link:
http://127.0.0.1:5000
