
def loginJsonBuilder(username: str, password: str):
    loginData = {
        "username": username,
        "password": password
    }

    return loginData


def acceptStudentJson(student_username:str):
    acceptBody = {
        "student_username": student_username
    }

    return acceptBody


def proposeProjectJson(title:str, description:str):
    projectBody = {
        "name": title,
        "description": description
    }

    return projectBody

def declareInterestInProject(projectID: str):
    declareInterest = {
        "project_id": projectID
    }

    return declareInterest