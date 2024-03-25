import { Content } from "@components/Content";
import { StyledLink } from "@components/Link";
import { ProjectCard } from "@components/ProjectCard";
import { useApiRequest } from "@hooks/use-api-request";
import { Project, ProjectSchema } from "@model/project";
import { useEffect, useState } from "react";
import styled from "styled-components";

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

            <Ul>
                {projects.map((project) => (
                    <li key={project.id}>
                        <ProjectCard project={project} linkTo={`/staff/projects/${project.id}`} showInterest={true} />
                    </li>
                ))}
            </Ul>
        </Content>
    );
}

const Ul = styled.ul`
    list-style-type: none;
    margin: 0;
    padding: 0;
    padding-top: 2rem;

    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    row-gap: 1rem;
    column-gap: 1rem;
`
