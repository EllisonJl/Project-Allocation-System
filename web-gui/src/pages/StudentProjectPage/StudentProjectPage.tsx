import { Content } from "@components/Content";
import { StyledLink } from "@components/Link";
import { UserContext } from "@components/UserContext";
import { useApiRequest } from "@hooks/use-api-request";
import { Project, ProjectSchema } from "@model/project";
import { useContext, useEffect, useState } from "react";
import { Link, useLoaderData } from "react-router-dom";

export function StudentProjectPage() {
    const api = useApiRequest();

    const user = useContext(UserContext);
    const { projectId } = useLoaderData() as { projectId: string };

    const [project, setProject] = useState<Project | null>(null);
    const [hasRegisteredInterest, setHasRegisteredInterest] = useState(false);

    useEffect(() => {
        loadProject();
    }, [projectId]);

    if (!project) {
        return <></>;
    }

    async function loadProject() {
        const body = await api(`/projects/${projectId}`).then((res) =>
            res.json(),
        );

        const parsedProject = ProjectSchema.parse(body);
        setProject(parsedProject);
        setHasRegisteredInterest(
            parsedProject.interested_students
                .map((s) => s.username)
                .includes(user!.username),
        );
    }

    async function registerInterest() {
        if (!project) {
            return;
        }

        await api("/register-interest", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                project_id: project.id,
            }),
        });

        // load the new data
        await loadProject();
    }

    return (
        <Content>
            <StyledLink to="/student/available-projects">All projects</StyledLink>
            <h1>{project.name}</h1>
            <span>Proposed by {project.proposed_by.name}</span>
            <p>{project.description}</p>
            {hasRegisteredInterest ? (
                <span>Interest registered!</span>
            ) : (
                <button type="button" onClick={registerInterest}>
                    Register interest
                </button>
            )}
        </Content>
    );
}
