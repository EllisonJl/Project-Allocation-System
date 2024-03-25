import { Content } from "@components/Content";
import { StyledLink } from "@components/Link";
import { useApiRequest } from "@hooks/use-api-request";
import { Project, ProjectSchema } from "@model/project";
import { useEffect, useState } from "react";
import { Link, useLoaderData } from "react-router-dom";

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
                    <h2>Assigned student</h2>
                    <p>{project.assigned_to.name}</p>
                </>
            ) : (
                <>
                    <h2>Interested students</h2>
                    <ul>
                        {project.interested_students.map((student) => (
                            <li key={student.username}>
                                {student.name} ({student.username})
                                <button
                                    type="button"
                                    onClick={() =>
                                        acceptStudent(student.username)
                                    }>
                                    Accept
                                </button>
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
