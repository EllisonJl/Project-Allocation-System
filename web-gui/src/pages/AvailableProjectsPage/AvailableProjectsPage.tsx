import { useApiRequest } from "@hooks/use-api-request";
import { Project, ProjectSchema } from "@model/project";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const ProjectsListSchema = ProjectSchema.array();

export function AvailableProjectsPage() {
    const api = useApiRequest();

    const [projects, setProjects] = useState<Project[]>([]);

    useEffect(() => {
        api("/available-projects")
            .then((res) => res.json())
            .then((body) => {
                setProjects(ProjectsListSchema.parse(body));
            });
    }, [api]);

    return (
        <>
            <h1>Available projects</h1>

            {projects.map((project) => (
                <div key={project.id}>
                    <div>
                        <h2>
                            <Link to={`/student/projects/${project.id}`}>
                                {project.name}
                            </Link>
                        </h2>
                        <span>Proposed by {project.proposed_by.name}</span>
                    </div>
                </div>
            ))}
        </>
    );
}
