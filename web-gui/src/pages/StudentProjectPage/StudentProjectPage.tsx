import { Content } from "@components/Content";
import { StyledLink } from "@components/Link";
import { UserContext } from "@components/UserContext";
import { useApiRequest } from "@hooks/use-api-request";
import { Project, ProjectSchema } from "@model/project";
import { useContext, useEffect, useState } from "react";
import { useLoaderData } from "react-router-dom";
import styled from "styled-components";

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
            <Wrapper>
            <ProposedBy>Proposed by {project.proposed_by.name}</ProposedBy>
            <p>{project.description}</p>
            {hasRegisteredInterest ? (
                <InterestRegisteredText>Interest registered!</InterestRegisteredText>
            ) : (
                <RegisterInterestButton type="button" onClick={registerInterest}>
                    Register interest
                </RegisterInterestButton>
            )}
            </Wrapper>
        </Content>
    );
}

const Wrapper = styled.div`
    * + * {
        margin-top: 1em;
    }
`;

const ProposedBy = styled.div`
    font-weight: bold;
`

const RegisterInterestButton = styled.button`
    padding-inline: 0.75rem;
    padding-block: 0.25rem;

    background: var(--color-cyan-400);
    border: none;
    border-radius: 4px;

    transition: all 150ms;

    font-weight: bold;

    &:hover {
        background: var(--color-cyan-500);
    }
`;

const InterestRegisteredText = styled.div`
    width: fit-content;
    border: 2px solid var(--color-cyan-400);
    border-radius: 4px;
    padding-inline: 0.75rem;
    padding-block: 0.25rem;
    font-weight: bold;
`
