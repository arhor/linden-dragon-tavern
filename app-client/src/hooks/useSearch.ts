import { useEffect, useState } from 'react';

import { useLocation, useNavigate } from 'react-router';

export default function useSearch() {
    const { search, pathname } = useLocation();
    const navigate = useNavigate();

    const [query, setQuery] = useState(search || '');

    let timeoutId: number | null = null;

    useEffect(() => {
        if (timeoutId != null) {
            clearTimeout(timeoutId);
        }
        timeoutId = window.setTimeout(() => {
            navigate({ pathname, search: query });
            timeoutId = null;
        }, 1000);
    }, [query]);

    return {
        get query() {
            return query;
        },
        set query(value) {
            setQuery(value);
        }
    };
}
