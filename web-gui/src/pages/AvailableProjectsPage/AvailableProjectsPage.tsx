import { Content } from "@components/Content";
import { ProjectCard } from "@components/ProjectCard";
import { useApiRequest } from "@hooks/use-api-request";
import { Project, ProjectSchema } from "@model/project";
import { useEffect, useState } from "react";
import styled from "styled-components";

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

            <Ul>
                {projects.map((project) => (
                    <li key={project.id}>
                        <ProjectCard project={project} linkTo={`/student/projects/${project.id}`} showProposedBy={true} />
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
