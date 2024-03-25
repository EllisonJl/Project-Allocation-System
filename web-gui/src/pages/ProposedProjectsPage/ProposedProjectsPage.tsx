import { Content } from "@components/Content";
import { StyledLink } from "@components/Link";
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
        <Content>
            <h1>Proposed projects</h1>

            <StyledLink to="/staff/new-project">Propose new project</StyledLink>

            <ul>
                {projects.map((project) => (
                    <li key={project.id}>
                        <h2>
                            <StyledLink to={`/staff/projects/${project.id}`}>
                                {project.name}
                            </StyledLink>
                        </h2>
                        <div>
                            {project.assigned_to !== null ? (
                                <span>
                                    Assigned to {project.assigned_to.name}
                                </span>
                            ) : (
                                <span>
                                    {project.interested_students.length}{" "}
                                    interested
                                </span>
                            )}
                        </div>
                    </li>
                ))}
            </ul>
        </Content>
    );
}
