import click
from utils import config
from student import studentProjects
import app


@click.command()
@click.option('-choice_student_menu', prompt="Enter your choice (1, 2, 3)", required=True)
def studentMenu(choice_student_menu):
    if choice_student_menu == "1":
        studentProjects.available_projects_page()
    elif choice_student_menu == "2":
        studentProjects.interested_projects_page()
    elif choice_student_menu == "3":
        app.restart()
    else:
        click.echo("Invalid Input, Try again")

def studentScreen():
    click.echo(click.style("Projects Allocation System", fg="blue", bold=True))
    click.echo("Welcome " + config.name + f"({config.role})")
    click.echo("")
    click.echo("1. View available projects")
    click.echo("2. View interested projects")
    click.echo("3. Login as someone else")
    studentMenu()