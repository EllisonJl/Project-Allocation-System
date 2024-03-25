import { Content } from "@components/Content";
import { ProjectForm } from "@components/ProjectForm";
import { useApiRequest } from "@hooks/use-api-request";
import { ProjectSchema } from "@model/project";
import { Link, useNavigate } from "react-router-dom";

export function NewProjectPage() {
    const api = useApiRequest();
    const navigate = useNavigate();

    async function submitProject(name: string, description: string) {
        const body = await api("/projects", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name,
                description,
            }),
        }).then((res) => res.json());

        const createdProject = ProjectSchema.parse(body);

        // go to the page of the created project
        navigate(`/staff/projects/${createdProject.id}`);
    }

    return (
        <Content>
            <Link to="/staff/proposed-projects">All projects</Link>

            <h1>Propose new project</h1>

            <ProjectForm onSubmit={submitProject} />
        </Content>
    );
}
