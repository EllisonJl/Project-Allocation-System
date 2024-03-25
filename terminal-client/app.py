import click
from auth import auth

def main():
    click.clear()
    click.echo(click.style("Projects Allocation System", fg="blue", bold=True))
    auth.login()
    click.pause()

def restart():
    main()

if __name__ == "__main__":
    main()
