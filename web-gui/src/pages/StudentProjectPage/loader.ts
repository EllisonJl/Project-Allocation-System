import { LoaderFunctionArgs } from "react-router-dom";

export function loader({params}: LoaderFunctionArgs) {
    return {
        projectId: params.projectId,
    }
}
