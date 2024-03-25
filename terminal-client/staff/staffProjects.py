import requests
import click
from utils import tableFormater, config, jsonBuilder
from student import studentLandingPage
from staff import staffLandingPage
def proposedProjects():
    apiUrl = "http://127.0.0.1:8080/proposed-projects"
    headers = {"Authorization": f"Bearer {config.accessToken}"}
    response = requests.get(apiUrl, headers=headers)
    if response.status_code == 200:
        config.data = response.json()
        click.echo(click.style("Available Projects", fg="green", bold=True))
        tableData = tableFormater.proposedProjectFormater(config.data)
        click.echo(tableData)
    elif response.status_code == 401:
        click.echo("No Projects Available")


@click.command()
@click.option("-choice_project_id",prompt="Enter the project id", required=True)
def proposedProjectById(choice_project_id):
    if choice_project_id in config.projectIDs:
        apiUrl = f"http://127.0.0.1:8080/projects/{choice_project_id}"
        headers = {"Authorization": f"Bearer {config.accessToken}"}
        response = requests.get(apiUrl, headers=headers)
        if response.status_code == 200:
            selectedProject = response.json()
            config.selectedProject = selectedProject
            click.echo(click.style("Project: " + selectedProject.get("name"), fg="green", bold=True))
            click.echo("Project ID: " + str(selectedProject.get("id")))
            click.echo("Project Name: " + selectedProject.get("name"))
            click.echo("Project Description: " + selectedProject.get("description"))
            click.echo("Proposed BY: " + selectedProject.get("proposed_by").get("name"))
            click.echo("Students interested:")
            click.echo("")
            if not selectedProject.get("assigned_to"):
                for student in selectedProject.get("interested_students", []):
                    click.echo(str(selectedProject.get("interested_students", []).index(student) + 1) + "  " + student.get("name") + " - " + student.get("username"))
                click.echo("")
                click.echo("1. Select a student to assign")
                click.echo("2. Go Back")
                pickStudentMenu()

            else:
                click.echo("Assigned to: " + selectedProject.get("assigned_to").get("name"))
                click.echo("")
                click.echo("1. Go Back")
                studentAleardyAssignedOption()
        elif response.status_code == 401:
            click.echo("Wrong ID, Try again")
            proposedProjectsMenu()
    else:
        click.echo("Wrong ID, try again")
        proposedProjectById()


@click.command()
@click.option("-pick_student", prompt="Enter student username", help="Student username")
def pickStudent(pick_student):
    while True:
        id = str(config.selectedProject.get("id"))
        apiUrl = f"http://127.0.0.1:8080/projects/{id}/accept-student"
        headers = {"Authorization": f"Bearer {config.accessToken}"}
        requestBody = jsonBuilder.acceptStudentJson(pick_student)
        response = requests.post(apiUrl, headers=headers, json=requestBody)

        if response.status_code == 200:
            click.echo("Student Assigned Successfully")
            staffLandingPage.staffScreen()
            break
        elif response.status_code == 400:
            click.echo("Wrong name entered. Please try again.")
            pick_student = click.prompt("Enter student username", type=str, show_default=False)
        else:
            click.echo("Unexpected error occurred. Please try again.")

@click.command()
@click.option("-pick_student_option",prompt="Enter your choice (1,2)",help="choice")
def pickStudentMenu(pick_student_option):
    if pick_student_option == "1":
        pickStudent()
    elif pick_student_option == "2":
        proposed_projects_page()
    else:
        click.echo("Wrong option")
        pickStudentMenu()


@click.command()
@click.option("-pick_already_option",prompt="Enter your choice (1)",help="Declare interest")
def studentAleardyAssignedOption(pick_already_option):
    if pick_already_option == "1":
        staffLandingPage.staffScreen()
    else:
        click.echo("Wrong option")
        pickStudentMenu()


@click.command()
@click.option("-choice_available_project_menu",prompt="Select your option (1,2)", required=True)
def proposedProjectsOption(choice_available_project_menu):
    if choice_available_project_menu == "1":
        proposedProjectById()
    elif choice_available_project_menu == "2":
        staffLandingPage.staffScreen()
    else:
        click.echo("Wrong option, try again")
        proposedProjectsOption()



@click.command()
@click.option("-title",prompt="Enter the title of the project", required=True)
@click.option("-description",prompt="Enter the description", required=True)
def proposeProject(title,description):
    apiUrl = "http://127.0.0.1:8080/projects"
    headers = {"Authorization": f"Bearer {config.accessToken}"}
    requestBody = jsonBuilder.proposeProjectJson(title,description)
    response = requests.post(apiUrl, headers=headers, json=requestBody)
    if response.status_code == 201:
        click.echo("Project proposed successfully!")
        staffLandingPage.staffScreen()
    elif response.status_code == 401:
        click.echo("Error")
        proposeProject()


def proposedProjectsMenu():
    click.echo("1. Select a Project")
    click.echo("2. Go back to main menu")
    proposedProjectsOption()

def proposed_projects_page():
    proposedProjects()
    proposedProjectsMenu()

def propose_a_new_project_page():
    proposeProject()