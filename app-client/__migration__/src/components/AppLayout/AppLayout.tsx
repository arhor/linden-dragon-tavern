import Container from '@mui/material/Container';

import AppBreadcrumbs from '@/components/AppBreadcrumbs';
import AppNavBar from '@/components/AppNavBar';
import AppNotifier from '@/components/AppNotifier';
import AppRouter from '@/components/AppRouter';

const AppLayout = () => (
    <>
        <AppNavBar />
        <AppNotifier />
        <Container component="main">
            <AppBreadcrumbs />
            <AppRouter />
        </Container>
    </>
);

export default AppLayout;
