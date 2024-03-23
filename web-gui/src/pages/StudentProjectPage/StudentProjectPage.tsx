import { useApiRequest } from "@hooks/use-api-request";
import { Project, ProjectSchema } from "@model/project";
import { useEffect, useState } from "react";
import { Link, useLoaderData } from "react-router-dom";

export function StudentProjectPage() {
    const api = useApiRequest();
    const { projectId } = useLoaderData() as { projectId: string };

    const [project, setProject] = useState<Project | null>(null);

    useEffect(() => {
        api(`/projects/${projectId}`)
            .then((res) => res.json())
            .then((body) => {
                setProject(ProjectSchema.parse(body));
            });
    });

    if (!project) {
        return <></>;
    }

    return (
        <>
            <Link to="/student/available-projects">All projects</Link>
            <h1>{project.name}</h1>
            <span>Proposed by {project.proposed_by.name}</span>
            <p>{project.description}</p>
            <button type="button">Register interest</button>
        </>
    );
}
