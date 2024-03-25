import click
from utils import jsonBuilder
import requests
import json
from student import studentLandingPage
from staff import staffLandingPage
from utils import config

@click.command()
@click.option("-username",prompt="Enter your username",help="Name of the user")
@click.option("-password",prompt="Enter your password",help="YOUR PASSWORD")
def login(username,password):

    #doesn't need input validatation cause click automatically does that for you!

    try:
        loginJson = jsonBuilder.loginJsonBuilder(username,password)
        apiUrl = "http://127.0.0.1:8080/login"
        response = requests.post(apiUrl, json=loginJson)
        if response.status_code == 200:
            click.echo("Logged in successfully")
            responseObject = json.loads(response.text)
            config.accessToken = responseObject.get("token")
            config.name = responseObject.get("name")
            config.role = responseObject.get("role")
            if responseObject.get("role") == "student":
                studentLandingPage.studentScreen()
            else:
                staffLandingPage.staffScreen()
        elif response.status_code == 401:
            click.echo("Unauthorized! Try Again")
            login()
    except:
        click.echo("Server is DOWN!")
        exit(0)