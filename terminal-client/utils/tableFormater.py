from prettytable import PrettyTable
from utils import config

def studentProjectTableFormater(data):
    table = PrettyTable()
    table.field_names = ["ID", "Name", "Proposed By"]

    for item in data:
        id = item.get("id")
        config.projectIDs.append(str(id))
        name = item.get("name")
        proposed_by = item.get("proposed_by").get("name")
        table.add_row([id, name, proposed_by])

    return table

def proposedProjectFormater(data):
    table = PrettyTable()
    table.field_names = ["ID", "Name", "No. of Interested Students"]

    for item in data:
        id = item.get("id")
        config.projectIDs.append(str(id))
        name = item.get("name")
        interested_students_count = len(item.get("interested_students", []))
        table.add_row([id, name, interested_students_count])

    return table

