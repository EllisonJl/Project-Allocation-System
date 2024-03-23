import { useApiRequest } from "@hooks/use-api-request";
import { Project, ProjectSchema } from "@model/project";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const ProjectsListSchema = ProjectSchema.array();

export function ProposedProjectsPage() {
    const api = useApiRequest();

    const [projects, setProjects] = useState<Project[]>([]);

    useEffect(() => {
        api("/proposed-projects")
            .then((res) => res.json())
            .then((body) => setProjects(ProjectsListSchema.parse(body)));
    }, []);

    return (
        <>
            <h1>Proposed projects</h1>

            <ul>
                {projects.map((project) => (
                    <li key={project.id}>
                        <h2>
                            <Link to={`/staff/projects/${project.id}`}>
                                {project.name}
                            </Link>
                        </h2>
                        <div>
                            <span>
                                {project.interested_students.length} interested
                            </span>
                            {project.assigned_to !== null && (
                                <span>Assigned to {project.assigned_to.name}</span>
                            )}
                        </div>
                    </li>
                ))}
            </ul>
        </>
    );
}
