import { Content } from "@components/Content";
import { StyledLink } from "@components/Link";
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
    }, []);

    return (
        <Content>
            <h1>Available projects</h1>

            <ul>
                {projects.map((project) => (
                    <li key={project.id}>
                        <h2>
                            <StyledLink to={`/student/projects/${project.id}`}>
                                {project.name}
                            </StyledLink>
                        </h2>
                        <span>Proposed by {project.proposed_by.name}</span>
                    </li>
                ))}
            </ul>
        </Content>
    );
}
