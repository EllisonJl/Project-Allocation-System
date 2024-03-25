import { Content } from "@components/Content";
import { StyledLink } from "@components/Link";
import { useApiRequest } from "@hooks/use-api-request";
import { Project, ProjectSchema } from "@model/project";
import { useEffect, useState } from "react";
import { useLoaderData } from "react-router-dom";
import styled from "styled-components";

export function StaffProjectPage() {
    const api = useApiRequest();

    const { projectId } = useLoaderData() as { projectId: string };

    const [project, setProject] = useState<Project | null>(null);

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
    }

    async function acceptStudent(username: string) {
        if (!project) {
            return;
        }

        await api(`/projects/${project.id}/accept-student`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                student_username: username,
            }),
        });

        // load the new data
        await loadProject();
    }

    return (
        <Content>
            <StyledLink to="/staff/proposed-projects">All projects</StyledLink>
            <h1>{project.name}</h1>
            <p>{project.description}</p>

            {project.assigned_to !== null ? (
                <>
                    <h2 className="prose">Assigned student</h2>
                    <p>
                        {project.assigned_to.name} (
                        {project.assigned_to.username})
                    </p>
                </>
            ) : (
                <>
                    <h2 className="prose">Interested students</h2>
                    <ul>
                        {project.interested_students.map((student) => (
                            <li key={student.username}>
                                {student.name} ({student.username})
                                <AcceptButton
                                    type="button"
                                    onClick={() =>
                                        acceptStudent(student.username)
                                    }>
                                    Accept
                                </AcceptButton>
                            </li>
                        ))}
                    </ul>
                    {project.interested_students.length === 0 && (
                        <div>No interested students yet.</div>
                    )}
                </>
            )}
        </Content>
    );
}

const AcceptButton = styled.button`
    margin-left: 1rem;
    padding-inline: 0.75rem;

    background: var(--color-cyan-400);
    border: none;
    border-radius: 4px;

    transition: all 150ms;

    font-weight: bold;

    &:hover {
        background: var(--color-cyan-500);
    }
`;
