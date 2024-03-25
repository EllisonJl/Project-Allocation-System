import { StyledLink } from "@components/Link";
import { Project } from "@model/project";
import styled from "styled-components";

type ProjectCardProps = {
    project: Project;
    linkTo: string;
    showProposedBy?: boolean;
    showInterest?: boolean;
};

export function ProjectCard({
    project,
    linkTo,
    showProposedBy,
    showInterest,
}: ProjectCardProps) {
    showProposedBy = showProposedBy ?? false;
    showInterest = showInterest ?? false;

    return (
        <Card>
            <h2>
                <StyledLink to={linkTo}>{project.name}</StyledLink>
            </h2>

            {showInterest && (
                <div>
                    {project.assigned_to !== null ? (
                        <span>Assigned to {project.assigned_to.name}</span>
                    ) : (
                        <span>
                            {project.interested_students.length} interested
                        </span>
                    )}
                </div>
            )}

            {showProposedBy && (
                <span>Proposed by {project.proposed_by.name}</span>
            )}
        </Card>
    );
}

const Card = styled.div`
    border-radius: 8px;
    border: 2px solid #ccc;
    padding: 1rem;
`;
