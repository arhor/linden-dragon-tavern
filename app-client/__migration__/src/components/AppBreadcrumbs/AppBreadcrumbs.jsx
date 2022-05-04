import React from 'react';
import Breadcrumbs  from '@mui/material/Breadcrumbs';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';
import { Link as RouterLink, useLocation } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

function MUIRouterLink(props) {
    return (
        <Link {...props} component={RouterLink} underline="hover" color="inherit" />
    );
}

function AppBreadcrumbs() {
    const { t } = useTranslation();
    const location = useLocation();
    const pathnames = location.pathname.split('/').filter((x) => x);
    const lastPathnameIndex = pathnames.length - 1;

    return (
        <Breadcrumbs>
            <MUIRouterLink to="/">
                {t('home').toUpperCase()}
            </MUIRouterLink>
            {pathnames.map((value, index) => {
                const last = index === lastPathnameIndex;
                const to = `/${pathnames.slice(0, index + 1).join('/')}`;
                const translatedValue = t(value).toUpperCase();

                return last ? (
                    <Typography color="text.primary" key={to}>
                        {translatedValue}
                    </Typography>
                ) : (
                    <MUIRouterLink to={to} key={to}>
                        {translatedValue}
                    </MUIRouterLink>
                );
            })}
        </Breadcrumbs>
    );
}

export default AppBreadcrumbs;