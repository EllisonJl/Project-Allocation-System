import click
from utils import config
from auth import auth
from staff import staffProjects
import app


@click.command()
@click.option('-choice_staff_menu', prompt="Enter your choice (1, 2, 3)", required=True)
def staffMenu(choice_staff_menu):
    if choice_staff_menu == "1":
        staffProjects.proposed_projects_page()
    elif choice_staff_menu == "2":
        staffProjects.propose_a_new_project_page()
    elif choice_staff_menu == "3":
        app.restart()
    else:
        click.echo("Invalid Input, Try again")

def staffScreen():
    click.echo(click.style("Projects Allocation System", fg="blue", bold=True))
    click.echo("Welcome " + config.name + f"({config.role})")
    click.echo("")
    click.echo("1. View proposed projects")
    click.echo("2. Propose a new project")
    click.echo("3. Login as someone else")
    staffMenu()