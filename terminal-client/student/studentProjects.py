import requests
import click
from utils import tableFormater, config, jsonBuilder
from student import studentLandingPage
def availableProjects():
    apiUrl = "http://127.0.0.1:8080/available-projects"
    headers = {"Authorization": f"Bearer {config.accessToken}"}
    response = requests.get(apiUrl, headers=headers)
    if response.status_code == 200:
        config.data = response.json()
        click.echo(click.style("Available Projects", fg="green", bold=True))
        tableData = tableFormater.studentProjectTableFormater(config.data)
        click.echo(tableData)
        click.echo("")
    elif response.status_code == 401:
        click.echo("No Projects Available")

@click.command()
@click.option("-choice_available_project_menu",prompt="Select your option (1,2)", required=True)
def availableProjectsOption(choice_available_project_menu):
    if choice_available_project_menu == "1":
        availableProjectByIdMenu()
    elif choice_available_project_menu == "2":
        studentLandingPage.studentScreen()
    else:
        click.echo("Wrong option, try again")
        availableProjectsOption()
def availableProjectsMenu():
    click.echo("1. View project details")
    click.echo("2. Go back to main menu")
    availableProjectsOption()

@click.command()
@click.option("-choice_project_id",prompt="Enter the project id", required=True)
def availableProjectByIdMenu(choice_project_id):
    if choice_project_id in config.projectIDs:
        apiUrl = f"http://127.0.0.1:8080/projects/{choice_project_id}"
        headers = {"Authorization": f"Bearer {config.accessToken}"}
        response = requests.get(apiUrl, headers=headers)
        if response.status_code == 200:
            selectedProject = response.json()
            config.selectedProject = selectedProject # doing it just to ensure that the project is accessible across different files
            click.echo(click.style("Project: " + selectedProject.get("name"), fg="green", bold=True))
            click.echo("Project ID: " + str(selectedProject.get("id")))
            click.echo("Project Name: " + selectedProject.get("name"))
            click.echo("Project Description: " + selectedProject.get("description"))
            click.echo("Proposed BY: " + selectedProject.get("proposed_by").get("name"))
            click.echo("")
            click.echo("1. Declare Interest")
            click.echo("2. Go Back")
            declareInterest()
        elif response.status_code == 401:
            click.echo("Wrong ID, Try again")
            availableProjectsMenu()
    else:
        click.echo("Wrong ID, try again")
        availableProjectsMenu()

@click.command()
@click.option("-declare_interest",prompt="Enter your choice (1,2)",help="Declare interest")
def declareInterest(declare_interest):
    if declare_interest == "1":
        apiUrl = "http://127.0.0.1:8080/register-interest"
        headers = {"Authorization": f"Bearer {config.accessToken}"}
        requestBody = jsonBuilder.declareInterestInProject(config.selectedProject.get("id"))
        response = requests.post(apiUrl,headers=headers,json=requestBody)
        if response.status_code == 200:
            click.echo("Interest Declared in the project")
            config.interestedProjects.append(config.selectedProject)
            studentLandingPage.studentScreen()
        elif response.status_code == 403:
            click.echo("Already interested")
            studentLandingPage.studentScreen()
    elif declare_interest == "2":
        available_projects_page()
    else:
        click.echo("Wrong option, try again")
        declareInterest()

def available_projects_page():
    availableProjects()
    availableProjectsMenu()

def interestedProjects():
    click.echo(click.style("Interested projects", fg="green", bold=True))
    if len(config.interestedProjects) != 0:
        for project in config.interestedProjects:
            click.echo(project.get("name"))
        studentLandingPage.studentScreen()
    else:
        click.echo("No Interest declared in the projects")
        studentLandingPage.studentScreen()

def interested_projects_page():
    interestedProjects()