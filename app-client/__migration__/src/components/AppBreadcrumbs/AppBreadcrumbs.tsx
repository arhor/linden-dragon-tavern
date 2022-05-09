import { useTranslation } from 'react-i18next';
import { Link as RouterLink, useLocation } from 'react-router-dom';

import Breadcrumbs from '@mui/material/Breadcrumbs';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';

const AppBreadcrumbs = () => {
    const { t } = useTranslation();
    const location = useLocation();
    const pathnames = location.pathname.split('/').filter((x) => x);
    const lastPathnameIndex = pathnames.length - 1;

    return (
        <Breadcrumbs>
            <Link to="/" component={RouterLink} underline="hover" color="inherit">
                {t('home').toUpperCase()}
            </Link>
            {pathnames.map((value, index) => {
                const last = index === lastPathnameIndex;
                const to = `/${pathnames.slice(0, index + 1).join('/')}`;
                const translatedValue = t(value).toUpperCase();

                return last ? (
                    <Typography color="text.primary" key={to}>
                        {translatedValue}
                    </Typography>
                ) : (
                    <Link to={to} key={to} component={RouterLink} underline="hover" color="inherit">
                        {translatedValue}
                    </Link>
                );
            })}
        </Breadcrumbs>
    );
};

export default AppBreadcrumbs;
